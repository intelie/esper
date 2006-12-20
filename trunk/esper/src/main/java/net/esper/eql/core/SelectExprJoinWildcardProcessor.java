package net.esper.eql.core;

import net.esper.event.*;
import net.esper.util.AssertionFacility;

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
     */
    public SelectExprJoinWildcardProcessor(String[] streamNames, EventType[] streamTypes, EventAdapterService eventAdapterService)
    {
        if ((streamNames.length < 2) || (streamTypes.length < 2) || (streamNames.length != streamTypes.length))
        {
            throw new IllegalArgumentException("Stream names and types parameter length is invalid, expected use of this class is for join statements");
        }

        this.streamNames = streamNames;
        this.eventAdapterService = eventAdapterService;

        // Create EventType of result join events
        Map<String, Class> eventTypeMap = new HashMap<String, Class>();
        for (int i = 0; i < streamTypes.length; i++)
        {
            eventTypeMap.put(streamNames[i], streamTypes[i].getUnderlyingType());
        }
        resultEventType = eventAdapterService.createAnonymousMapType(eventTypeMap);
    }

    public EventBean process(EventBean[] eventsPerStream)
    {
        Map<String, Object> tuple = new HashMap<String, Object>();
        for (int i = 0; i < streamNames.length; i++)
        {
            AssertionFacility.assertTrue(streamNames[i] != null, "Event name for stream " + i + " is null");

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
