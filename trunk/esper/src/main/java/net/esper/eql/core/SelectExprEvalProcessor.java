/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.eql.core;

import net.esper.event.*;
import net.esper.eql.spec.InsertIntoDesc;
import net.esper.eql.spec.SelectExprElementCompiledSpec;
import net.esper.eql.expression.ExprNode;
import net.esper.eql.expression.ExprValidationException;
import net.esper.util.ExecutionPathDebugLog;
import net.esper.util.JavaClassHelper;

import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Processor for select-clause expressions that handles a list of selection items represented by
 * expression nodes. Computes results based on matching events.
 */
public class SelectExprEvalProcessor implements SelectExprProcessor
{
	private static final Log log = LogFactory.getLog(SelectExprEvalProcessor.class);
	
    private ExprNode[] expressionNodes;
    private String[] columnNames;
    private EventType resultEventType;
    private final EventAdapterService eventAdapterService;
    private final boolean isUsingWildcard;
    private boolean singleStreamWrapper;
    private boolean singleColumnCoercion;
    private SelectExprJoinWildcardProcessor joinWildcardProcessor;

    /**
     * Ctor.
     * @param selectionList - list of select-clause items
     * @param insertIntoDesc - descriptor for insert-into clause contains column names overriding select clause names
     * @param isUsingWildcard - true if the wildcard (*) appears in the select clause
     * @param typeService -service for information about streams
     * @param eventAdapterService - service for generating events and handling event types
     * @throws net.esper.eql.expression.ExprValidationException thrown if any of the expressions don't validate
     */
    public SelectExprEvalProcessor(List<SelectExprElementCompiledSpec> selectionList,
                                   InsertIntoDesc insertIntoDesc,
                                   boolean isUsingWildcard, 
                                   StreamTypeService typeService, 
                                   EventAdapterService eventAdapterService) throws ExprValidationException
    {
        this.eventAdapterService = eventAdapterService;
        this.isUsingWildcard = isUsingWildcard;

        if (selectionList.size() == 0 && !isUsingWildcard)
        {
            throw new IllegalArgumentException("Empty selection list not supported");
        }

        for (SelectExprElementCompiledSpec entry : selectionList)
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
        if(typeService.getStreamNames().length > 1 && isUsingWildcard)
        {
        	joinWildcardProcessor = new SelectExprJoinWildcardProcessor(typeService.getStreamNames(), typeService.getEventTypes(), eventAdapterService, null);
        }
        
        // Resolve underlying event type in the case of wildcard select
        EventType underlyingType = null;
        if(isUsingWildcard)
        {
        	if(joinWildcardProcessor != null)
        	{
        		underlyingType = joinWildcardProcessor.getResultEventType();
        	}
        	else
        	{
        		underlyingType = typeService.getEventTypes()[0];
        		if(underlyingType instanceof WrapperEventType)
        		{
        			singleStreamWrapper = true;
        		}
        	}
        }
        
        init(selectionList, insertIntoDesc, underlyingType, eventAdapterService);
    }

    private void init(List<SelectExprElementCompiledSpec> selectionList,
                      InsertIntoDesc insertIntoDesc,
                      EventType eventType, 
                      EventAdapterService eventAdapterService)
        throws ExprValidationException
    {
        // Get expression nodes
        expressionNodes = new ExprNode[selectionList.size()];
        for (int i = 0; i < selectionList.size(); i++)
        {
            expressionNodes[i] = selectionList.get(i).getSelectExpression();
        }

        // Get column names
        if ((insertIntoDesc != null) && (!insertIntoDesc.getColumnNames().isEmpty()))
        {
            columnNames = insertIntoDesc.getColumnNames().toArray(new String[0]);
        }
        else
        {
            columnNames = new String[selectionList.size()];
            for (int i = 0; i < selectionList.size(); i++)
            {
                columnNames[i] = selectionList.get(i).getAssignedName();
            }
        }

        // Build event type
        Map<String, Class> selPropertyTypes = new HashMap<String, Class>();
        for (int i = 0; i < expressionNodes.length; i++)
        {
            Class expressionReturnType = expressionNodes[i].getType();
            selPropertyTypes.put(columnNames[i], expressionReturnType);
        }

        // If we have an alias for this type, add it
        if (insertIntoDesc != null)
        {
            try
            {
                if (isUsingWildcard)
                {
                    resultEventType = eventAdapterService.addWrapperType(insertIntoDesc.getEventTypeAlias(), eventType, selPropertyTypes);
                }
                else
                {
                    resultEventType = null;
                    if ((columnNames.length == 1) && (insertIntoDesc.getColumnNames().size() == 0))
                    {
                        EventType existingType = eventAdapterService.getExistsTypeByAlias(insertIntoDesc.getEventTypeAlias());
                        if (existingType != null)
                        {
                            // check if the existing type and new type are compatible
                            Class columnOneType = expressionNodes[0].getType();
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
                        resultEventType = eventAdapterService.addMapType(insertIntoDesc.getEventTypeAlias(), selPropertyTypes);
                    }
                }
            }
            catch (EventAdapterException ex)
            {
                throw new ExprValidationException(ex.getMessage());
            }
        }
        else
        {
            if (isUsingWildcard)
            {
        	    resultEventType = eventAdapterService.createAnonymousWrapperType(eventType, selPropertyTypes);
            }
            else
            {
                resultEventType = eventAdapterService.createAnonymousMapType(selPropertyTypes);
            }
        }

        if (log.isDebugEnabled())
        {
            log.debug(".init resultEventType=" + resultEventType);
        }
    }

    public EventBean process(EventBean[] eventsPerStream, boolean isNewData)
    {
        // Evaluate all expressions and build a map of name-value pairs
        Map<String, Object> props = new HashMap<String, Object>();
        for (int i = 0; i < expressionNodes.length; i++)
        {
            Object evalResult = expressionNodes[i].evaluate(eventsPerStream, isNewData);
            props.put(columnNames[i], evalResult);
        }

        if(isUsingWildcard)
        {
        	// In case of a wildcard and single stream that is itself a 
        	// wrapper bean, we also need to add the map properties
        	if(singleStreamWrapper)
        	{
        		DecoratingEventBean wrapper = (DecoratingEventBean)eventsPerStream[0];
        		if(wrapper != null)
        		{
        			Map<String, Object> map = wrapper.getDecoratingProperties();
                    if ((ExecutionPathDebugLog.isDebugEnabled) && (log.isDebugEnabled()))
                    {
        			    log.debug(".process additional properties=" + map);
                    }
                    props.putAll(map);
        		}
        	}
            
            EventBean event;
            if(joinWildcardProcessor != null)
            {
                event = joinWildcardProcessor.process(eventsPerStream, isNewData);
            }
            else
            {
                event = eventsPerStream[0];
            }            

            // Using a wrapper bean since we cannot use the same event type else same-type filters match.
            // Wrapping it even when not adding properties is very inexpensive.
            return eventAdapterService.createWrapper(event, props, resultEventType);
        }
        else
        {
            if (singleColumnCoercion)
            {
                Object result = props.get(columnNames[0]);
                EventBean wrappedEvent;
                if (result instanceof Map)
                {
                    wrappedEvent = eventAdapterService.createMapFromValues((Map)result, resultEventType);
                }
                else
                {
                    wrappedEvent = eventAdapterService.adapterForBean(result);
                }
                props.clear();
                return eventAdapterService.createWrapper(wrappedEvent, props, resultEventType);
            }
            else
            {
                return eventAdapterService.createMapFromValues(props, resultEventType);
            }
        }
    }

    public EventType getResultEventType()
    {
        return resultEventType;
    }

    private static void verifyInsertInto(InsertIntoDesc insertIntoDesc,
                                         List<SelectExprElementCompiledSpec> selectionList)
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
