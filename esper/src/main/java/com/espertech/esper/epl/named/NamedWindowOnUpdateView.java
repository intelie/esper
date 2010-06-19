/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.named;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventPropertyDescriptor;
import com.espertech.esper.client.EventType;
import com.espertech.esper.collection.ArrayEventIterator;
import com.espertech.esper.collection.OneEventCollection;
import com.espertech.esper.core.StatementResultService;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.epl.expression.ExprValidationException;
import com.espertech.esper.epl.spec.OnTriggerSetAssignment;
import com.espertech.esper.epl.spec.OnTriggerWindowUpdateDesc;
import com.espertech.esper.event.*;
import com.espertech.esper.util.TypeWidener;
import com.espertech.esper.util.TypeWidenerFactory;
import com.espertech.esper.util.JavaClassHelper;
import com.espertech.esper.view.StatementStopService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * View for the on-delete statement that handles removing events from a named window.
 */
public class NamedWindowOnUpdateView extends NamedWindowOnExprBaseView
{
    private static final Log log = LogFactory.getLog(NamedWindowOnUpdateView.class);
    private EventBean[] lastResult;
    private final StatementResultService statementResultService;
    private final ExprNode[] expressions;
    private final String[] propertyNames;
    private final EventPropertyWriter[] writers;
    private final EventTypeSPI eventTypeSPI;
    private final EventBeanCopyMethod copyMethod;
    private final boolean[] notNullableField;
    private final TypeWidener[] wideners;

    /**
     * Ctor.
     * @param statementStopService for indicating a statement was stopped or destroyed for cleanup
     * @param lookupStrategy for handling trigger events to determine deleted events
     * @param removeStreamView to indicate which events to delete
     * @param statementResultService for coordinating on whether insert and remove stream events should be posted
     * @param exprEvaluatorContext context for expression evalauation
     * @param onTriggerDesc describes on-update
     * @throws com.espertech.esper.epl.expression.ExprValidationException when expression validation fails
     */
    public NamedWindowOnUpdateView(StatementStopService statementStopService,
                                 LookupStrategy lookupStrategy,
                                 NamedWindowRootView removeStreamView,
                                 StatementResultService statementResultService,
                                 ExprEvaluatorContext exprEvaluatorContext,
                                 OnTriggerWindowUpdateDesc onTriggerDesc)
            throws ExprValidationException
    {
        super(statementStopService, lookupStrategy, removeStreamView, exprEvaluatorContext);
        this.statementResultService = statementResultService;
        eventTypeSPI = (EventTypeSPI) removeStreamView.getEventType();

        // validate expression, obtain wideners
        wideners = new TypeWidener[onTriggerDesc.getAssignments().size()];
        List<String> properties = new ArrayList<String>();
        int len = onTriggerDesc.getAssignments().size();
        expressions = new ExprNode[len];
        writers = new EventPropertyWriter[len];
        notNullableField = new boolean[len];

        for (int i = 0; i < onTriggerDesc.getAssignments().size(); i++)
        {
            OnTriggerSetAssignment assignment = onTriggerDesc.getAssignments().get(i);
            expressions[i] = assignment.getExpression();
            EventPropertyDescriptor writableProperty = eventTypeSPI.getWritableProperty(assignment.getVariableName());

            if (writableProperty == null)
            {
                throw new ExprValidationException("Property '" + assignment.getVariableName() + "' is not available for write access");
            }
            writers[i] = eventTypeSPI.getWriter(assignment.getVariableName());
            notNullableField[i] = writableProperty.getPropertyType().isPrimitive();

            properties.add(assignment.getVariableName());
            wideners[i] = TypeWidenerFactory.getCheckPropertyAssignType(assignment.getExpression().toExpressionString(), assignment.getExpression().getType(),
                    writableProperty.getPropertyType(), assignment.getVariableName());
        }
        propertyNames = properties.toArray(new String[properties.size()]);

        // map expression index to property index
        List<String> propertiesUniqueList = new ArrayList(new HashSet(properties));
        String[] propertiesArray = propertiesUniqueList.toArray(new String[propertiesUniqueList.size()]);
        copyMethod = eventTypeSPI.getCopyMethod(propertiesArray);
        if (copyMethod == null) {
            throw new ExprValidationException("Event type does not support event bean copy");
        }
    }

    public void handleMatching(EventBean[] triggerEvents, EventBean[] matchingEvents)
    {
        if ((matchingEvents == null) || (matchingEvents.length == 0)){
            return;
        }
        EventBean[] eventsPerStream = new EventBean[2];

        OneEventCollection newData = new OneEventCollection();
        OneEventCollection oldData = new OneEventCollection();

        for (EventBean triggerEvent : triggerEvents) {
            eventsPerStream[1] = triggerEvent;
            for (EventBean matchingEvent : matchingEvents) {
                EventBean copy = copyMethod.copy(matchingEvent);
                eventsPerStream[0] = copy;

                for (int i = 0; i < expressions.length; i++) {
                    Object result = expressions[i].evaluate(eventsPerStream, true, super.getExprEvaluatorContext());

                    if (result == null && notNullableField[i]) {
                        log.warn("Null value returned by expression for assignment to property '" + propertyNames[i] + " is ignored as the property type is not nullable for expression '" + expressions[i].toExpressionString() + "'");
                        continue;
                    }

                    if (wideners[i] != null) {
                        result = wideners[i].widen(result);
                    }
                    writers[i].write(result, copy);
                }

                newData.add(copy);
                oldData.add(matchingEvent);
            }
        }

        if (!newData.isEmpty())
        {
            // Events to delete are indicated via old data
            this.rootView.update(newData.toArray(), oldData.toArray());

            // The on-delete listeners receive the events deleted, but only if there is interest
            if (statementResultService.isMakeNatural() || statementResultService.isMakeSynthetic()) {
                updateChildren(newData.toArray(), oldData.toArray());
            }
        }

        // Keep the last delete records
        lastResult = matchingEvents;
    }

    public EventType getEventType()
    {
        return namedWindowEventType;
    }

    public Iterator<EventBean> iterator()
    {
        return new ArrayEventIterator(lastResult);
    }
}