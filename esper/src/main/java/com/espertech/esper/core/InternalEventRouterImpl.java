package com.espertech.esper.core;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;
import com.espertech.esper.client.EventPropertyDescriptor;
import com.espertech.esper.client.annotation.Priority;
import com.espertech.esper.client.annotation.Drop;
import com.espertech.esper.epl.spec.OnTriggerInsertIntoUpdDesc;
import com.espertech.esper.epl.spec.OnTriggerSetAssignment;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.epl.expression.ExprValidationException;
import com.espertech.esper.util.NullableObject;
import com.espertech.esper.util.TypeWidenerFactory;
import com.espertech.esper.util.TypeWidener;
import com.espertech.esper.event.EventPropertyWriter;
import com.espertech.esper.event.EventTypeSPI;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InternalEventRouterImpl implements InternalEventRouter
{
    private static final Log log = LogFactory.getLog(InternalEventRouterImpl.class);

    private final EPRuntimeImpl runtime;
    private boolean hasPreprocessing = false;
    private final ConcurrentHashMap<EventType, NullableObject<InternalEventRouterPreprocessor>> preprocessors;
    private final Map<OnTriggerInsertIntoUpdDesc, IRDescEntry> descriptors;

    public InternalEventRouterImpl(EPRuntimeImpl runtime)
    {
        this.runtime = runtime;
        this.preprocessors = new ConcurrentHashMap<EventType, NullableObject<InternalEventRouterPreprocessor>>();
        this.descriptors = new HashMap<OnTriggerInsertIntoUpdDesc, IRDescEntry>();
    }

    public void route(EventBean event, EPStatementHandle statementHandle)
    {
        if (!hasPreprocessing)
        {
            runtime.route(event, statementHandle);
            return;
        }

        NullableObject<InternalEventRouterPreprocessor> processor = preprocessors.get(event.getEventType());
        if (processor == null)
        {
            synchronized (this)
            {
                processor = initialize(event.getEventType());
                preprocessors.put(event.getEventType(), processor);

                if (processor.getObject() == null)
                {
                    runtime.route(event, statementHandle);
                }
                else
                {
                    EventBean processed = processor.getObject().process(event);
                    if (processed == null)
                    {
                        return;
                    }
                    runtime.route(processed, statementHandle);
                }
            }
            return;
        }

        if (processor.getObject() == null)
        {
            runtime.route(event, statementHandle);
        }
        else
        {
            EventBean processed = processor.getObject().process(event);
            if (processed == null)
            {
                return;
            }
            runtime.route(processed, statementHandle);
        }
    }

    public void addPreprocessing(EventType eventType, OnTriggerInsertIntoUpdDesc desc, Annotation[] annotations)
            throws ExprValidationException
    {
        if (log.isInfoEnabled())
        {
            log.info("Adding route preprocessing for type '" + eventType.getName());
        }

        if (!(eventType instanceof EventTypeSPI))
        {
            throw new ExprValidationException("Update statements require the event type to implement the " + EventTypeSPI.class + " interface");
        }
        EventTypeSPI eventTypeSPI = (EventTypeSPI) eventType;

        TypeWidener[] wideners = new TypeWidener[desc.getAssignments().size()];
        for (int i = 0; i < desc.getAssignments().size(); i++)
        {
            OnTriggerSetAssignment assignment = desc.getAssignments().get(i);
            EventPropertyDescriptor writableProperty = eventTypeSPI.getWritableProperty(assignment.getVariableName());

            if (writableProperty == null)
            {
                throw new ExprValidationException("Property '" + assignment.getVariableName() + "' is not available for write access");
            }

            wideners[i] = TypeWidenerFactory.getCheckPropertyAssignType(assignment.getExpression().toExpressionString(), assignment.getExpression().getType(),
                    writableProperty.getPropertyType(), assignment.getVariableName());
        }

        descriptors.put(desc, new IRDescEntry(eventType, annotations, wideners));

        // remove all preprocessors for this type as well as any known child types, forcing re-init on next use
        removePreprocessors(eventType);

        hasPreprocessing = true;
    }

    public void removePreprocessing(EventType eventType, OnTriggerInsertIntoUpdDesc desc)
    {
        if (log.isInfoEnabled())
        {
            log.info("Removing route preprocessing for type '" + eventType.getName());
        }

        // remove all preprocessors for this type as well as any known child types
        removePreprocessors(eventType);

        descriptors.remove(desc);
        if (descriptors.isEmpty())
        {
            hasPreprocessing = false;
            preprocessors.clear();
        }
    }

    private void removePreprocessors(EventType eventType)
    {
        preprocessors.remove(eventType);

        // find each child type entry
        for (EventType type : preprocessors.keySet())
        {
            if (type.getDeepSuperTypes() != null)
            {
                for (Iterator<EventType> it = type.getDeepSuperTypes(); it.hasNext();)
                {
                    if (it.next() == eventType)
                    {
                        preprocessors.remove(type);
                    }
                }
            }
        }
    }

    private NullableObject<InternalEventRouterPreprocessor> initialize(EventType eventType)
    {
        List<InternalEventRouterEntry> desc = new ArrayList<InternalEventRouterEntry>();

        // determine which ones to process for this types, and what priority and drop
        for (Map.Entry<OnTriggerInsertIntoUpdDesc, IRDescEntry> entry : descriptors.entrySet())
        {
            boolean applicable = entry.getValue().getEventType() == eventType;
            if (!applicable)
            {
                if (eventType.getDeepSuperTypes() != null)
                {
                    for (Iterator<EventType> it = eventType.getDeepSuperTypes(); it.hasNext();)
                    {
                        if (it.next() == entry.getValue().getEventType())
                        {
                            applicable = true;
                            break;
                        }
                    }
                }
            }

            if (!applicable)
            {
                continue;
            }

            int priority = 0;
            boolean isDrop = false;
            Annotation[] annotations = entry.getValue().getAnnotations();
            for (int i = 0; i < annotations.length; i++)
            {
                if (annotations[i] instanceof Priority)
                {
                    priority = ((Priority) annotations[i]).value();
                }
                if (annotations[i] instanceof Drop)
                {
                    isDrop = true;
                }
            }

            EventTypeSPI eventTypeSPI = (EventTypeSPI) eventType;
            EventPropertyWriter writers[] = new EventPropertyWriter[entry.getKey().getAssignments().size()];
            ExprNode[] expressions = new ExprNode[writers.length];
            for (int i = 0; i < writers.length; i++)
            {
                OnTriggerSetAssignment assignment = entry.getKey().getAssignments().get(i);
                expressions[i] = assignment.getExpression();
                writers[i] = eventTypeSPI.getWriter(assignment.getVariableName());
            }
            desc.add(new InternalEventRouterEntry(priority, isDrop, entry.getKey().getOptionalWhereClause(), expressions, writers, entry.getValue().getWideners()));
        }

        return new NullableObject<InternalEventRouterPreprocessor>(new InternalEventRouterPreprocessor(desc));
    }

    private class IRDescEntry
    {
        private EventType eventType;
        private Annotation[] annotations;
        private TypeWidener[] wideners;

        public IRDescEntry(EventType eventType, Annotation[] annotations, TypeWidener[] wideners)
        {
            this.eventType = eventType;
            this.annotations = annotations;
            this.wideners = wideners;
        }

        public EventType getEventType()
        {
            return eventType;
        }

        public Annotation[] getAnnotations()
        {
            return annotations;
        }

        public TypeWidener[] getWideners()
        {
            return wideners;
        }
    }
}
