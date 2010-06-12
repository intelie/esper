/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.core;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.client.EventType;
import com.espertech.esper.client.FragmentEventType;
import com.espertech.esper.epl.core.eval.*;
import com.espertech.esper.epl.expression.*;
import com.espertech.esper.epl.spec.InsertIntoDesc;
import com.espertech.esper.epl.spec.SelectClauseExprCompiledSpec;
import com.espertech.esper.event.EventAdapterException;
import com.espertech.esper.event.EventAdapterService;
import com.espertech.esper.event.NativeEventType;
import com.espertech.esper.event.WrapperEventType;
import com.espertech.esper.event.map.MapEventType;
import com.espertech.esper.event.vaevent.ValueAddEventProcessor;
import com.espertech.esper.event.vaevent.ValueAddEventService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

/**
 * Processor for select-clause expressions that handles a list of selection items represented by
 * expression nodes. Computes results based on matching events.
 */
public class SelectExprEvalProcessor
{
	private static final Log log = LogFactory.getLog(SelectExprEvalProcessor.class);

    private final List<SelectClauseExprCompiledSpec> selectionList;
    private final InsertIntoDesc insertIntoDesc;
    private final boolean isUsingWildcard;
    private final StreamTypeService typeService;
    private final EventAdapterService eventAdapterService;
    private final ValueAddEventService valueAddEventService;
    private final SelectExprEventTypeRegistry selectExprEventTypeRegistry;
    private final MethodResolutionService methodResolutionService;
    private final ExprEvaluatorContext exprEvaluatorContext;

    /**
     * Ctor.
     * @param selectionList - list of select-clause items
     * @param insertIntoDesc - descriptor for insert-into clause contains column names overriding select clause names
     * @param isUsingWildcard - true if the wildcard (*) appears in the select clause
     * @param typeService -service for information about streams
     * @param eventAdapterService - service for generating events and handling event types
     * @param valueAddEventService - service that handles update events
     * @param selectExprEventTypeRegistry - service for statement to type registry
     * @param methodResolutionService - for resolving methods
     * @param exprEvaluatorContext context for expression evalauation
     * @throws com.espertech.esper.epl.expression.ExprValidationException thrown if any of the expressions don't validate
     */
    public SelectExprEvalProcessor(List<SelectClauseExprCompiledSpec> selectionList,
                                   InsertIntoDesc insertIntoDesc,
                                   boolean isUsingWildcard,
                                   StreamTypeService typeService,
                                   EventAdapterService eventAdapterService,
                                   ValueAddEventService valueAddEventService,
                                   SelectExprEventTypeRegistry selectExprEventTypeRegistry,
                                     MethodResolutionService methodResolutionService,
                                   ExprEvaluatorContext exprEvaluatorContext) throws ExprValidationException
    {
        this.selectionList = selectionList;
        this.insertIntoDesc = insertIntoDesc;
        this.eventAdapterService = eventAdapterService;
        this.isUsingWildcard = isUsingWildcard;
        this.typeService = typeService;
        this.exprEvaluatorContext = exprEvaluatorContext;
        this.valueAddEventService = valueAddEventService;
        this.selectExprEventTypeRegistry = selectExprEventTypeRegistry;
        this.methodResolutionService = methodResolutionService;
    }

    public SelectExprProcessor getEvaluator() throws ExprValidationException {

        if (selectionList.size() == 0 && !isUsingWildcard)
        {
            throw new IllegalArgumentException("Empty selection list not supported");
        }

        for (SelectClauseExprCompiledSpec entry : selectionList)
        {
            if (entry.getAssignedName() == null)
            {
                throw new IllegalArgumentException("Expected name for each expression has not been supplied");
            }
        }

        // Verify insert into clause
        if (insertIntoDesc != null)
        {
            verifyInsertInto(insertIntoDesc, selectionList);
        }

        // Build a subordinate wildcard processor for joins
        SelectExprJoinWildcardProcessor joinWildcardProcessor = null;
        if(typeService.getStreamNames().length > 1 && isUsingWildcard)
        {
        	joinWildcardProcessor = new SelectExprJoinWildcardProcessor(typeService.getStreamNames(), typeService.getEventTypes(), eventAdapterService, null, selectExprEventTypeRegistry, methodResolutionService, exprEvaluatorContext);
        }

        // Resolve underlying event type in the case of wildcard select
        EventType eventType = null;
        boolean singleStreamWrapper = false;
        if(isUsingWildcard)
        {
        	if(joinWildcardProcessor != null)
        	{
        		eventType = joinWildcardProcessor.getResultEventType();
        	}
        	else
        	{
        		eventType = typeService.getEventTypes()[0];
        		if(eventType instanceof WrapperEventType)
        		{
        			singleStreamWrapper = true;
        		}
        	}
        }

        // Get expression nodes
        ExprEvaluator[] expressionNodes = new ExprEvaluator[selectionList.size()];
        Object[] expressionReturnTypes = new Object[selectionList.size()];
        for (int i = 0; i < selectionList.size(); i++)
        {
            ExprNode expr = selectionList.get(i).getSelectExpression();
            expressionNodes[i] = expr;
            expressionReturnTypes[i] = expr.getType();
        }

        // Get column names
        String[] columnNames;
        if ((insertIntoDesc != null) && (!insertIntoDesc.getColumnNames().isEmpty()))
        {
            columnNames = insertIntoDesc.getColumnNames().toArray(new String[insertIntoDesc.getColumnNames().size()]);
        }
        else
        {
            columnNames = new String[selectionList.size()];
            for (int i = 0; i < selectionList.size(); i++)
            {
                columnNames[i] = selectionList.get(i).getAssignedName();
            }
        }

        // Find if there is any fragments selected
        EventType targetType= null;
        if (insertIntoDesc != null)
        {
            targetType = eventAdapterService.getExistsTypeByName(insertIntoDesc.getEventTypeName());
        }

        // Find if there is any fragment event types:
        // This is a special case for fragments: select a, b from pattern [a=A -> b=B]
        // We'd like to maintain 'A' and 'B' EventType in the Map type, and 'a' and 'b' EventBeans in the event bean
        for (int i = 0; i < selectionList.size(); i++)
        {
            if (!(expressionNodes[i] instanceof ExprIdentNode))
            {
                continue;
            }

            ExprIdentNode identNode = (ExprIdentNode) expressionNodes[i];
            String propertyName = identNode.getResolvedPropertyName();
            final int streamNum = identNode.getStreamId();

            EventType eventTypeStream = typeService.getEventTypes()[streamNum];
            if (eventTypeStream instanceof NativeEventType)
            {
                continue;   // we do not transpose the native type for performance reasons
            }
            
            FragmentEventType fragmentType = eventTypeStream.getFragmentType(propertyName);
            if ((fragmentType == null) || (fragmentType.isNative()))
            {
                continue;   // we also ignore native Java classes as fragments for performance reasons
            }

            // may need to unwrap the fragment if the target type has this underlying type
            FragmentEventType targetFragment = null;
            if (targetType != null)
            {
                targetFragment = targetType.getFragmentType(columnNames[i]);
            }
            if ((targetType != null) &&
                (fragmentType.getFragmentType().getUnderlyingType() == expressionReturnTypes[i]) &&
                ((targetFragment == null) || (targetFragment != null && targetFragment.isNative())) )
            {
                ExprEvaluator evaluatorFragment;

                // A match was found, we replace the expression
                final EventPropertyGetter getter = eventTypeStream.getGetter(propertyName);
                evaluatorFragment = new ExprEvaluator() {
                    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext exprEvaluatorContext)
                    {
                        EventBean streamEvent = eventsPerStream[streamNum];
                        if (streamEvent == null)
                        {
                            return null;
                        }
                        return getter.get(streamEvent);
                    }
                };
                expressionNodes[i] = evaluatorFragment;
            }
            else
            {
                ExprEvaluator evaluatorFragment;
                final EventPropertyGetter getter =  eventTypeStream.getGetter(propertyName);

                // A match was found, we replace the expression
                evaluatorFragment = new ExprEvaluator() {

                    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext exprEvaluatorContext)
                    {
                        EventBean streamEvent = eventsPerStream[streamNum];
                        if (streamEvent == null)
                        {
                            return null;
                        }
                        return getter.getFragment(streamEvent);
                    }
                };

                expressionNodes[i] = evaluatorFragment;
                if (!fragmentType.isIndexed())
                {
                    expressionReturnTypes[i] = fragmentType.getFragmentType();
                }
                else
                {
                    expressionReturnTypes[i] = new EventType[] {fragmentType.getFragmentType()};
                }
            }
        }

        // Find if there is any stream expression (ExprStreamNode) :
        // This is a special case for stream selection: select a, b from A as a, B as b
        // We'd like to maintain 'A' and 'B' EventType in the Map type, and 'a' and 'b' EventBeans in the event bean
        for (int i = 0; i < selectionList.size(); i++)
        {
            if (!(expressionNodes[i] instanceof ExprStreamUnderlyingNode))
            {
                continue;
            }

            ExprStreamUnderlyingNode undNode = (ExprStreamUnderlyingNode) expressionNodes[i];
            final int streamNum = undNode.getStreamId();
            EventType eventTypeStream = typeService.getEventTypes()[streamNum];

            // A match was found, we replace the expression
            ExprEvaluator evaluator = new ExprEvaluator() {

                public Object evaluate(EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext exprEvaluatorContext)
                {
                    return eventsPerStream[streamNum];
                }
            };

            expressionNodes[i] = evaluator;
            expressionReturnTypes[i] = eventTypeStream;
        }

        // Build event type
        Map<String, Object> selPropertyTypes = new LinkedHashMap<String, Object>();
        for (int i = 0; i < expressionNodes.length; i++)
        {
            Object expressionReturnType = expressionReturnTypes[i];
            selPropertyTypes.put(columnNames[i], expressionReturnType);
        }

        SelectExprContext selectExprContext = new SelectExprContext(expressionNodes, columnNames, exprEvaluatorContext, eventAdapterService);

        if (insertIntoDesc == null)
        {
            if (isUsingWildcard)
            {
        	    EventType resultEventType = eventAdapterService.createAnonymousWrapperType(eventType, selPropertyTypes);
                if (singleStreamWrapper) {
                    return new EvalSelectWildcardSSWrapper(selectExprContext, resultEventType);
                }
                if (joinWildcardProcessor == null) {
                    return new EvalSelectWildcard(selectExprContext, resultEventType);
                }
                return new EvalSelectWildcardJoin(selectExprContext, resultEventType, joinWildcardProcessor);
            }

            EventType resultEventType = eventAdapterService.createAnonymousMapType(selPropertyTypes);
            return new EvalSelectNoWildcard(selectExprContext, resultEventType);
        }

        EventType vaeInnerEventType = null;
        boolean singleColumnCoercion = false;
        boolean isRevisionEvent = false;
        EventType resultEventType;
        
        try
        {
            ValueAddEventProcessor vaeProcessor = valueAddEventService.getValueAddProcessor(insertIntoDesc.getEventTypeName());
            if (isUsingWildcard)
            {
                if (vaeProcessor != null)
                {
                    resultEventType = vaeProcessor.getValueAddEventType();
                    isRevisionEvent = true;
                    vaeProcessor.validateEventType(eventType);
                }
                else
                {
                    EventType existingType = eventAdapterService.getExistsTypeByName(insertIntoDesc.getEventTypeName());
                    SelectExprInsertEventBean selectExprInsertEventBean = null;
                    if (existingType != null)
                    {
                        selectExprInsertEventBean = SelectExprInsertEventBean.getInsertUnderlying(eventAdapterService, existingType);
                    }
                    if ((existingType != null) && (selectExprInsertEventBean != null))
                    {
                        selectExprInsertEventBean.initialize(isUsingWildcard, typeService, expressionNodes, columnNames, expressionReturnTypes, methodResolutionService, eventAdapterService);
                        resultEventType = existingType;
                        return new EvalInsertNative(resultEventType, selectExprInsertEventBean, exprEvaluatorContext);
                    }
                    else if (existingType != null && selPropertyTypes.isEmpty() && existingType instanceof MapEventType) {
                        resultEventType = existingType;
                        return new EvalInsertMapTypeCoercion(resultEventType, eventAdapterService);
                    }
                    else
                    {
                        resultEventType = eventAdapterService.addWrapperType(insertIntoDesc.getEventTypeName(), eventType, selPropertyTypes, false, true);
                    }
                }

                if (singleStreamWrapper) {
                    if (!isRevisionEvent) {
                        return new EvalInsertWildcardSSWrapper(selectExprContext, resultEventType);
                    }
                    else {
                        return new EvalInsertWildcardSSWrapperRevision(selectExprContext, resultEventType, vaeProcessor);
                    }
                }
                if (joinWildcardProcessor == null) {
                    if (!isRevisionEvent) {
                        return new EvalInsertWildcard(selectExprContext, resultEventType);
                    }
                    else {
                        return new EvalInsertWildcardRevision(selectExprContext, resultEventType, vaeProcessor);
                    }
                }
                else {
                    if (!isRevisionEvent) {
                        return new EvalInsertWildcardJoin(selectExprContext, resultEventType, joinWildcardProcessor);
                    }
                    else {
                        return new EvalInsertWildcardJoinRevision(selectExprContext, resultEventType, joinWildcardProcessor, vaeProcessor);
                    }
                }
            }

            // not using wildcard
            resultEventType = null;
            if ((columnNames.length == 1) && (insertIntoDesc.getColumnNames().size() == 0))
            {
                EventType existingType = eventAdapterService.getExistsTypeByName(insertIntoDesc.getEventTypeName());
                if (existingType != null)
                {
                    // check if the existing type and new type are compatible
                    Object columnOneType = expressionReturnTypes[0];
                    if (existingType instanceof WrapperEventType)
                    {
                        WrapperEventType wrapperType = (WrapperEventType) existingType;
                        // Map and Object both supported
                        if (wrapperType.getUnderlyingEventType().getUnderlyingType() == columnOneType)
                        {
                            singleColumnCoercion = true;
                            resultEventType = existingType;
                        }
                    }
                }
            }
            if (resultEventType == null)
            {
                if (vaeProcessor != null)
                {
                    resultEventType = eventAdapterService.createAnonymousMapType(selPropertyTypes);
                }
                else
                {
                    EventType existingType = eventAdapterService.getExistsTypeByName(insertIntoDesc.getEventTypeName());
                    SelectExprInsertEventBean selectExprInsertEventBean = null;
                    if (existingType != null)
                    {
                        selectExprInsertEventBean = SelectExprInsertEventBean.getInsertUnderlying(eventAdapterService, existingType);
                    }
                    if ((existingType != null) && (selectExprInsertEventBean != null))
                    {
                        selectExprInsertEventBean.initialize(isUsingWildcard, typeService, expressionNodes, columnNames, expressionReturnTypes, methodResolutionService, eventAdapterService);
                        resultEventType = existingType;
                        return new EvalInsertNative(resultEventType, selectExprInsertEventBean, exprEvaluatorContext);
                    }
                    else
                    {
                        resultEventType = eventAdapterService.addNestableMapType(insertIntoDesc.getEventTypeName(), selPropertyTypes, null, false, false, true);
                    }
                }
            }
            if (vaeProcessor != null)
            {
                vaeProcessor.validateEventType(resultEventType);
                vaeInnerEventType = resultEventType;
                resultEventType = vaeProcessor.getValueAddEventType();
                isRevisionEvent = true;
            }

            if (singleColumnCoercion) {
                if (!isRevisionEvent) {
                    if (resultEventType instanceof MapEventType) {
                        return new EvalInsertNoWildcardSingleColCoercionMap(selectExprContext, resultEventType);
                    }
                    else {
                        return new EvalInsertNoWildcardSingleColCoercionBean(selectExprContext, resultEventType);
                    }
                }
                else {
                    if (resultEventType instanceof MapEventType) {
                        return new EvalInsertNoWildcardSingleColCoercionRevisionMap(selectExprContext, resultEventType, vaeProcessor, vaeInnerEventType);
                    }
                    else {
                        return new EvalInsertNoWildcardSingleColCoercionRevisionBean(selectExprContext, resultEventType, vaeProcessor, vaeInnerEventType);
                    }
                }
            }
            if (!isRevisionEvent) {
                return new EvalInsertNoWildcard(selectExprContext, resultEventType);
            }
            else {
                return new EvalInsertNoWildcardRevision(selectExprContext, resultEventType, vaeProcessor, vaeInnerEventType);
            }
        }
        catch (EventAdapterException ex)
        {
            throw new ExprValidationException(ex.getMessage());
        }
    }

    private static void verifyInsertInto(InsertIntoDesc insertIntoDesc,
                                         List<SelectClauseExprCompiledSpec> selectionList)
        throws ExprValidationException
    {
        // Verify all column names are unique
        Set<String> names = new HashSet<String>();
        for (String element : insertIntoDesc.getColumnNames())
        {
            if (names.contains(element))
            {
                throw new ExprValidationException("Property name '" + element + "' appears more then once in insert-into clause");
            }
            names.add(element);
        }

        // Verify number of columns matches the select clause
        if ( (!insertIntoDesc.getColumnNames().isEmpty()) &&
             (insertIntoDesc.getColumnNames().size() != selectionList.size()) )
        {
            throw new ExprValidationException("Number of supplied values in the select clause does not match insert-into clause");
        }
    }
}
