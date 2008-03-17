/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.core;

import com.espertech.esper.event.EventType;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Implementation that provides stream number and property type information. 
 */
public class StreamTypeServiceImpl implements StreamTypeService
{
    private final EventType[] eventTypes;
    private final String[] streamNames;
    private boolean isStreamZeroUnambigous;
    private boolean requireStreamNames;

    /**
     * Ctor.
     * @param eventTypes - array of event types, one for each stream
     * @param streamNames - array of stream names, one for each stream
     */
    public StreamTypeServiceImpl (EventType[] eventTypes, String[] streamNames)
    {
        this.eventTypes = eventTypes;
        this.streamNames = streamNames;

        if (eventTypes.length != streamNames.length)
        {
            throw new IllegalArgumentException("Number of entries for event types and stream names differs");
        }
    }

    /**
     * Ctor.
     * @param namesAndTypes is the ordered list of stream names and event types available (stream zero to N)
     * @param isStreamZeroUnambigous indicates whether when a property is found in stream zero and another stream an exception should be
     * thrown or the stream zero should be assumed
     * @param requireStreamNames is true to indicate that stream names are required for any non-zero streams (for subqueries)
     */
    public StreamTypeServiceImpl (LinkedHashMap<String, EventType> namesAndTypes, boolean isStreamZeroUnambigous, boolean requireStreamNames)
    {
        this.isStreamZeroUnambigous = isStreamZeroUnambigous;
        this.requireStreamNames = requireStreamNames;
        eventTypes = new EventType[namesAndTypes.size()] ;
        streamNames = new String[namesAndTypes.size()] ;
        int count = 0;
        for (Map.Entry<String, EventType> entry : namesAndTypes.entrySet())
        {
            streamNames[count] = entry.getKey();
            eventTypes[count] = entry.getValue();
            count++;
        }
    }

    public EventType[] getEventTypes()
    {
        return eventTypes;
    }

    public String[] getStreamNames()
    {
        return streamNames;
    }

    public PropertyResolutionDescriptor resolveByPropertyName(String propertyName)
        throws DuplicatePropertyException, PropertyNotFoundException
    {
        if (propertyName == null)
        {
            throw new IllegalArgumentException("Null property name");
        }
        PropertyResolutionDescriptor desc = findByPropertyName(propertyName);
        if ((requireStreamNames) && (desc.getStreamNum() != 0))
        {
            throw new PropertyNotFoundException("Property named '" + propertyName + "' must be prefixed by a stream name, use the as-clause to name the stream");
        }
        return desc;
    }

    public PropertyResolutionDescriptor resolveByStreamAndPropName(String streamName, String propertyName)
        throws PropertyNotFoundException, StreamNotFoundException
    {
        if (streamName == null)
        {
            throw new IllegalArgumentException("Null property name");
        }
        if (propertyName == null)
        {
            throw new IllegalArgumentException("Null property name");
        }
        return findByStreamName(propertyName, streamName);
    }

    public PropertyResolutionDescriptor resolveByStreamAndPropName(String streamAndPropertyName) throws DuplicatePropertyException, PropertyNotFoundException
    {
        if (streamAndPropertyName == null)
        {
            throw new IllegalArgumentException("Null stream and property name");
        }

        PropertyResolutionDescriptor desc = null;
        try
        {
            // first try to resolve as a property name
            desc = findByPropertyName(streamAndPropertyName);
        }
        catch (PropertyNotFoundException ex)
        {
            // Attempt to resolve by extracting a stream name
            int index = streamAndPropertyName.indexOf('.');
            if (index == -1)
            {
                throw ex;
            }
            String streamName = streamAndPropertyName.substring(0, index);
            String propertyName = streamAndPropertyName.substring(index + 1, streamAndPropertyName.length());
            try
            {
                // try to resolve a stream and property name
                desc = findByStreamName(propertyName, streamName);
            }
            catch (StreamNotFoundException e)
            {
                throw ex;       // throws PropertyNotFoundException
            }
            return desc;
        }

        return desc;
    }

    private PropertyResolutionDescriptor findByPropertyName(String propertyName)
        throws DuplicatePropertyException, PropertyNotFoundException
    {
        int index = 0;
        int foundIndex = 0;
        int foundCount = 0;
        EventType streamType = null;

        for (int i = 0; i < eventTypes.length; i++)
        {
            if (eventTypes[i].isProperty(propertyName))
            {
                streamType = eventTypes[i];
                foundCount++;
                foundIndex = index;

                // If the property could be resolved from stream 0 then we don't need to look further
                if ((i == 0) && isStreamZeroUnambigous)
                {
                    return new PropertyResolutionDescriptor(streamNames[0], eventTypes[0], propertyName, 0, streamType.getPropertyType(propertyName));
                }
            }
            index++;
        }

        if (foundCount > 1)
        {
            throw new DuplicatePropertyException("Property named '" + propertyName + "' is ambigous as is valid for more then one stream");
        }

        if (streamType == null)
        {
            throw new PropertyNotFoundException("Property named '" + propertyName + "' is not valid in any stream");
        }

        return new PropertyResolutionDescriptor(streamNames[foundIndex], eventTypes[foundIndex], propertyName, foundIndex, streamType.getPropertyType(propertyName));
    }

    private PropertyResolutionDescriptor findByStreamName(String propertyName, String streamName)
        throws PropertyNotFoundException, StreamNotFoundException
    {
        int index = 0;
        EventType streamType = null;

        for (int i = 0; i < eventTypes.length; i++)
        {
            if ((streamNames[i] != null) && (streamNames[i].equals(streamName)))
            {
                streamType = eventTypes[i];
                break;
            }
            index++;
        }

        // Stream name not found
        if (streamType == null)
        {
            throw new StreamNotFoundException("Stream named " + streamName + " is not defined");
        }

        Class propertyType = streamType.getPropertyType(propertyName);
        if (propertyType == null)
        {
            throw new PropertyNotFoundException("Property named '" + propertyName + "' is not valid in stream " + streamName);
        }

        return new PropertyResolutionDescriptor(streamName, streamType, propertyName, index, propertyType);
    }
}
