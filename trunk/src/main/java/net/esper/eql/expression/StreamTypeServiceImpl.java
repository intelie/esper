package net.esper.eql.expression;

import net.esper.event.EventType;

/**
 * Implementation that provides stream number and property type information. 
 */
public class StreamTypeServiceImpl implements StreamTypeService
{
    private EventType[] eventTypes;
    private String[] streamNames;

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
        return findByPropertyName(propertyName);
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
