package net.esper.eql.variable;

import net.esper.eql.expression.ExprValidationException;
import net.esper.eql.spec.OnTriggerSetAssignment;
import net.esper.eql.spec.OnTriggerSetDesc;
import net.esper.event.EventAdapterService;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.util.ExecutionPathDebugLog;
import net.esper.util.JavaClassHelper;
import net.esper.view.ViewSupport;
import net.esper.collection.SingleEventIterator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class OnSetVariableView extends ViewSupport
{
    private static final Log log = LogFactory.getLog(OnSetVariableView.class);
    private final OnTriggerSetDesc desc;
    private final EventAdapterService eventAdapterService;
    private final EventType eventType;
    private final VariableWriter[] writers;
    private final VariableReader[] readers;
    private final EventBean[] eventsPerStream = new EventBean[1];
    private final boolean[] mustCoerce;

    public OnSetVariableView(OnTriggerSetDesc desc, EventAdapterService eventAdapterService, VariableService variableService)
            throws ExprValidationException
    {
        this.desc = desc;
        this.eventAdapterService = eventAdapterService;

        Map<String, Class> variableTypes = new HashMap<String, Class>();
        writers = new VariableWriter[desc.getAssignments().size()];
        readers = new VariableReader[desc.getAssignments().size()];
        mustCoerce = new boolean[desc.getAssignments().size()];

        int count = 0;
        for (OnTriggerSetAssignment assignment : desc.getAssignments())
        {
            String variableName = assignment.getVariableName();
            writers[count] = variableService.getWriter(variableName);
            if (writers[count] == null)
            {
                throw new ExprValidationException("Variable by name '" + variableName + "' has not been created or configured");
            }
            readers[count] = variableService.getReader(variableName);

            // determine types
            Class variableType = writers[count].getType();
            Class expressionType = assignment.getExpression().getType();
            variableTypes.put(variableName, variableType);

            // determine if the expression type can be assigned
            if ((JavaClassHelper.getBoxedType(expressionType) != variableType) &&
                (expressionType != null))
            {
                if ((!JavaClassHelper.isNumeric(variableType)) ||
                    (!JavaClassHelper.isNumeric(expressionType)))
                {
                    throw new ExprValidationException("Variable '" + variableName
                        + "' of declared type '" + variableType.getName() +
                            "' cannot be assigned a value of type '" + expressionType.getName() + "'");
                }

                if (!(JavaClassHelper.canCoerce(expressionType, variableType)))
                {
                    throw new ExprValidationException("Variable '" + variableName
                        + "' of declared type '" + variableType.getName() +
                            "' cannot be assigned a value of type '" + expressionType.getName() + "'");
                }

                mustCoerce[count] = true;
            }
            count++;
        }
        eventType = eventAdapterService.createAnonymousMapType(variableTypes);
    }

    public void update(EventBean[] newData, EventBean[] oldData)
    {
        if ((ExecutionPathDebugLog.isDebugEnabled) && (log.isDebugEnabled()))
        {
            log.debug(".update Received update, " +
                    "  newData.length==" + ((newData == null) ? 0 : newData.length) +
                    "  oldData.length==" + ((oldData == null) ? 0 : oldData.length));
        }

        if ((newData == null) || (newData.length == 0))
        {
            return;
        }

        Map<String, Object> values = null;
        if (this.hasViews())
        {
            values = new HashMap<String, Object>();
        }

        eventsPerStream[0] = newData[newData.length - 1];
        int count = 0;
        for (OnTriggerSetAssignment assignment : desc.getAssignments())
        {
            Object value = assignment.getExpression().evaluate(eventsPerStream, true);
            if (mustCoerce[count])
            {
                value = JavaClassHelper.coerceBoxed((Number) value, writers[count].getType());
            }

            writers[count].write(value);
            count++;

            if (values != null)
            {
                values.put(assignment.getVariableName(), value);
            }
        }

        if (values != null)
        {
            EventBean newDataOut[] = new EventBean[1];
            newDataOut[0] = eventAdapterService.createMapFromValues(values, eventType);
            this.updateChildren(newDataOut, null);
        }
    }

    public EventType getEventType()
    {
        return eventType;
    }

    public Iterator<EventBean> iterator()
    {
        Map<String, Object> values = new HashMap<String, Object>();

        int count = 0;
        for (OnTriggerSetAssignment assignment : desc.getAssignments())
        {
            Object value = readers[count].getValue();
            values.put(assignment.getVariableName(), value);
            count++;
        }

        EventBean event = eventAdapterService.createMapFromValues(values, eventType);
        return new SingleEventIterator(event);
    }
}
