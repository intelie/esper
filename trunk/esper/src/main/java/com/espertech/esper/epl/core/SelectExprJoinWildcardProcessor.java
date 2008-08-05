/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.core;

import com.espertech.esper.epl.expression.ExprValidationException;
import com.espertech.esper.epl.spec.InsertIntoDesc;
import com.espertech.esper.event.*;

import java.util.*;

/**
 * Processor for select-clause expressions that handles wildcards. Computes results based on matching events.
 */
public class SelectExprJoinWildcardProcessor implements SelectExprProcessor
{
    private final String[] streamNames;
    private final EventType resultEventType;
    private final EventAdapterService eventAdapterService;

    /**
     * Ctor.
     * @param streamNames - name of each stream
     * @param streamTypes - type of each stream
     * @param eventAdapterService - service for generating events and handling event types
     * @param insertIntoDesc - describes the insert-into clause
     * @throws ExprValidationException if the expression validation failed 
     */
    public SelectExprJoinWildcardProcessor(String[] streamNames, EventType[] streamTypes, EventAdapterService eventAdapterService, InsertIntoDesc insertIntoDesc) throws ExprValidationException
    {
        if ((streamNames.length < 2) || (streamTypes.length < 2) || (streamNames.length != streamTypes.length))
        {
            throw new IllegalArgumentException("Stream names and types parameter length is invalid, expected use of this class is for join statements");
        }

        this.streamNames = streamNames;
        this.eventAdapterService = eventAdapterService;

        // Create EventType of result join events
        Map<String, Object> eventTypeMap = new HashMap<String, Object>();
        for (int i = 0; i < streamTypes.length; i++)
        {
            eventTypeMap.put(streamNames[i], streamTypes[i].getUnderlyingType());
        }

        // If we have an alias for this type, add it
        if (insertIntoDesc != null)
        {
        	try
            {
                resultEventType = eventAdapterService.addNestableMapType(insertIntoDesc.getEventTypeAlias(), eventTypeMap, null);
            }
            catch (EventAdapterException ex)
            {
                throw new ExprValidationException(ex.getMessage());
            }
        }
        else
        {
            resultEventType = eventAdapterService.createAnonymousMapType(eventTypeMap);
        }
    }

    public EventBean process(EventBean[] eventsPerStream, boolean isNewData, boolean isSynthesize)
    {
        Map<String, Object> tuple = new HashMap<String, Object>();
        for (int i = 0; i < streamNames.length; i++)
        {
            if (streamNames[i] == null)
            {
                throw new IllegalStateException("Event name for stream " + i + " is null");
            }

            if (eventsPerStream[i] != null)
            {
                tuple.put(streamNames[i], eventsPerStream[i].getUnderlying());
            }
            else
            {
                tuple.put(streamNames[i], null);
            }
        }

        return eventAdapterService.createMapFromValues(tuple, resultEventType);
    }

    public EventType getResultEventType()
    {
        return resultEventType;
    }
}