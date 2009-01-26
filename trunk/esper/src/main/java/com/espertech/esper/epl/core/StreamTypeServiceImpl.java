/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.core;

import com.espertech.esper.event.EventType;
import com.espertech.esper.collection.Pair;
import com.espertech.esper.core.EPServiceProviderSPI;
import com.espertech.esper.epl.parse.ASTFilterSpecHelper;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Implementation that provides stream number and property type information. 
 */
public class StreamTypeServiceImpl implements StreamTypeService
{
    private final EventType[] eventTypes;
    private final String[] streamNames;
    private final String engineURIQualifier;
    private final String[] eventTypeAlias;
    private boolean isStreamZeroUnambigous;
    private boolean requireStreamNames;

    /**
     * Ctor.
     * @param eventType a single event type for a single stream
     * @param streamName the stream name of the single stream
     * @param engineURI engine URI
     * @param eventTypeAlias alias of the event type of the single stream
     */
    public StreamTypeServiceImpl (EventType eventType, String streamName, String engineURI, String eventTypeAlias)
    {
        this(new EventType[] {eventType}, new String[] {streamName}, engineURI, new String[] {eventTypeAlias});
    }

    /**
     * Ctor.
     * @param eventTypes - array of event types, one for each stream
     * @param streamNames - array of stream names, one for each stream
     * @param engineURI - engine URI
     * @param eventTypeAlias - alias name of the event type
     */
    public StreamTypeServiceImpl (EventType[] eventTypes, String[] streamNames, String engineURI, String[] eventTypeAlias)
    {
        this.eventTypes = eventTypes;
        this.streamNames = streamNames;
        this.eventTypeAlias = eventTypeAlias;

        if (engineURI == null)
        {
            engineURIQualifier = EPServiceProviderSPI.DEFAULT_ENGINE_URI__QUALIFIER;
        }
        else
        {
            engineURIQualifier = engineURI;
        }

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
     * @param engineURI uri of the engine
     * @param requireStreamNames is true to indicate that stream names are required for any non-zero streams (for subqueries)
     */
    public StreamTypeServiceImpl (LinkedHashMap<String, Pair<EventType, String>> namesAndTypes, String engineURI, boolean isStreamZeroUnambigous, boolean requireStreamNames)
    {
        this.isStreamZeroUnambigous = isStreamZeroUnambigous;
        this.requireStreamNames = requireStreamNames;
        this.engineURIQualifier = engineURI;
        eventTypes = new EventType[namesAndTypes.size()] ;
        streamNames = new String[namesAndTypes.size()] ;
        eventTypeAlias = new String[namesAndTypes.size()] ;
        int count = 0;
        for (Map.Entry<String, Pair<EventType, String>> entry : namesAndTypes.entrySet())
        {
            streamNames[count] = entry.getKey();
            eventTypes[count] = entry.getValue().getFirst();
            eventTypeAlias[count] = entry.getValue().getSecond();
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
        return findByStreamAndEngineName(propertyName, streamName);
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
            int index = ASTFilterSpecHelper.unescapedIndexOfDot(streamAndPropertyName);
            if (index == -1)
            {
                throw ex;
            }
            String streamName = streamAndPropertyName.substring(0, index);
            String propertyName = streamAndPropertyName.substring(index + 1, streamAndPropertyName.length());
            try
            {
                // try to resolve a stream and property name
                desc = findByStreamAndEngineName(propertyName, streamName);
            }
            catch (StreamNotFoundException e)
            {
                // Consider the engine URI as a further prefix
                Pair<String, String> propertyNoEnginePair = getIsEngineQualified(propertyName, streamName);
                if (propertyNoEnginePair == null)
                {
                    throw ex;
                }
                try
                {
                    return findByStreamNameOnly(propertyNoEnginePair.getFirst(), propertyNoEnginePair.getSecond());
                }
                catch (StreamNotFoundException e1)
                {
                    throw ex;
                }
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

    private PropertyResolutionDescriptor findByStreamAndEngineName(String propertyName, String streamName)
        throws PropertyNotFoundException, StreamNotFoundException
    {
        PropertyResolutionDescriptor desc;
        try
        {
            desc = findByStreamNameOnly(propertyName, streamName);
        }
        catch (PropertyNotFoundException ex)
        {
            Pair<String, String> propertyNoEnginePair = getIsEngineQualified(propertyName, streamName);
            if (propertyNoEnginePair == null)
            {
                throw ex;
            }
            return findByStreamNameOnly(propertyNoEnginePair.getFirst(), propertyNoEnginePair.getSecond());
        }
        catch (StreamNotFoundException ex)
        {
            Pair<String, String> propertyNoEnginePair = getIsEngineQualified(propertyName, streamName);
            if (propertyNoEnginePair == null)
            {
                throw ex;
            }
            return findByStreamNameOnly(propertyNoEnginePair.getFirst(), propertyNoEnginePair.getSecond());
        }
        return desc;
    }

    private Pair<String, String> getIsEngineQualified(String propertyName, String streamName) {

        // If still not found, test for the stream name to contain the engine URI
        if (!streamName.equals(engineURIQualifier))
        {
            return null;
        }

        int index = ASTFilterSpecHelper.unescapedIndexOfDot(propertyName);
        if (index == -1)
        {
            return null;
        }

        String streamNameNoEngine = propertyName.substring(0, index);
        String propertyNameNoEngine = propertyName.substring(index + 1, propertyName.length());
        return new Pair<String, String>(propertyNameNoEngine, streamNameNoEngine);
    }

    private PropertyResolutionDescriptor findByStreamNameOnly(String propertyName, String streamName)
        throws PropertyNotFoundException, StreamNotFoundException
    {
        int index = 0;
        EventType streamType = null;

        // Stream name resultion examples:
        // A)  select A1.price from Event.price as A2  => mismatch stream alias, cannot resolve
        // B)  select Event1.price from Event2.price   => mismatch event type alias, cannot resolve
        // C)  select default.Event2.price from Event2.price   => possible prefix of engine name
        for (int i = 0; i < eventTypes.length; i++)
        {
            if ((streamNames[i] != null) && (streamNames[i].equals(streamName)))
            {
                streamType = eventTypes[i];
                break;
            }

            // If the stream name is the event type alias, that is also acceptable
            if ((eventTypeAlias[i] != null) && (eventTypeAlias[i].equals(streamName)))
            {
                streamType = eventTypes[i];
                break;
            }

            index++;
        }

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
