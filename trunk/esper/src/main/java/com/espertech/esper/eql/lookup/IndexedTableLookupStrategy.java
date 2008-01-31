package com.espertech.esper.eql.lookup;

import com.espertech.esper.eql.join.table.PropertyIndexedEventTable;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.EventPropertyGetter;
import com.espertech.esper.event.EventType;

import java.util.Arrays;
import java.util.Set;

/**
 * Index lookup strategy for subqueries.
 */
public class IndexedTableLookupStrategy implements TableLookupStrategy
{
    private final String[] properties;

    /**
     * Stream numbers to get key values from.
     */
    protected final int[] streamNumbers;

    /**
     * Index to look up in.
     */
    protected final PropertyIndexedEventTable index;

    /**
     * Getters to use to get key values.
     */
    protected final EventPropertyGetter[] propertyGetters;

    /**
     * Ctor.
     * @param eventTypes is the event types per stream
     * @param streamNumbers is the stream number per property
     * @param properties is the key properties
     * @param index is the table carrying the data to lookup into
     */
    public IndexedTableLookupStrategy(EventType[] eventTypes, int[] streamNumbers, String[] properties, PropertyIndexedEventTable index)
    {
        this.streamNumbers = streamNumbers;
        this.properties = properties;
        this.index = index;

        propertyGetters = new EventPropertyGetter[properties.length];
        for (int i = 0; i < streamNumbers.length; i++)
        {
            int streamNumber = streamNumbers[i];
            String property = properties[i];
            EventType eventType = eventTypes[streamNumber];
            propertyGetters[i] = eventType.getGetter(property);

            if (propertyGetters[i] == null)
            {
                throw new IllegalArgumentException("Property named '" + properties[i] + "' is invalid for type " + eventType);
            }
        }
    }

    /**
     * Returns properties to use from lookup event to look up in index.
     * @return properties to use from lookup event
     */
    public String[] getProperties()
    {
        return properties;
    }

    /**
     * Returns index to look up in.
     * @return index to use
     */
    public PropertyIndexedEventTable getIndex()
    {
        return index;
    }

    public Set<EventBean> lookup(EventBean[] eventsPerStream)
    {
        Object[] keys = getKeys(eventsPerStream);
        return index.lookup(keys);
    }

    /**
     * Get the index lookup keys.
     * @param eventsPerStream is the events for each stream
     * @return key object
     */
    protected Object[] getKeys(EventBean[] eventsPerStream)
    {
        Object[] keyValues = new Object[propertyGetters.length];
        for (int i = 0; i < propertyGetters.length; i++)
        {
            int streamNum = streamNumbers[i];
            EventBean event = eventsPerStream[streamNum];
            keyValues[i] = propertyGetters[i].get(event);
        }
        return keyValues;
    }

    public String toString()
    {
        return "IndexedTableLookupStrategy indexProps=" + Arrays.toString(properties) +
                " index=(" + index + ')';
    }
}
