package net.esper.eql.join.table;

import java.util.*;

import net.esper.collection.MultiKeyUntyped;
import net.esper.event.EventBean;
import net.esper.event.EventBeanUtility;
import net.esper.event.EventPropertyGetter;
import net.esper.event.EventType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Index that organizes events by the event property values into hash buckets. Based on a HashMap
 * with {@link net.esper.collection.MultiKeyUntyped} keys that store the property values.
 *
 * Takes a list of property names as parameter. Doesn't care which event type the events have as long as the properties
 * exist. If the same event is added twice, the class throws an exception on add.
 */
public class PropertyIndexedEventTable implements EventTable
{
    private final int streamNum;
    private final String[] propertyNames;
    private final EventPropertyGetter[] propertyGetters;
    private final Map<MultiKeyUntyped, Set<EventBean>> propertyIndex;

    /**
     * Ctor.
     * @param streamNum - the stream number that is indexed
     * @param eventType - types of events indexed
     * @param propertyNames - property names to use for indexing
     */
    public PropertyIndexedEventTable(int streamNum, EventType eventType, String[] propertyNames)
    {
        this.streamNum = streamNum;
        this.propertyNames = propertyNames;

        // Init getters
        propertyGetters = new EventPropertyGetter[propertyNames.length];
        for (int i = 0; i < propertyNames.length; i++)
        {
            propertyGetters[i] = eventType.getGetter(propertyNames[i]);
        }

        propertyIndex = new HashMap<MultiKeyUntyped, Set<EventBean>>();
    }

    /**
     * Add an array of events. Same event instance is not added twice. Event properties should be immutable.
     * Allow null passed instead of an empty array.
     * @param events to add
     * @throws IllegalArgumentException if the event was already existed in the index
     */
    public void add(EventBean[] events)
    {
        if (events == null)
        {
            return;
        }
        for (EventBean event : events)
        {
            add(event);
        }
    }

    /**
     * Remove events.
     * @param events to be removed, can be null instead of an empty array.
     * @throws IllegalArgumentException when the event could not be removed as its not in the index
     */
    public void remove(EventBean[] events)
    {
        if (events == null)
        {
            return;
        }
        for (EventBean event : events)
        {
            remove(event);
        }
    }

    /**
     * Returns the set of events that have the same property value as the given event.
     * @param keys to compare against
     * @return set of events with property value, or null if none found (never returns zero-sized set)
     */
    public Set<EventBean> lookup(Object[] keys)
    {
        MultiKeyUntyped key = new MultiKeyUntyped(keys);
        Set<EventBean> events = propertyIndex.get(key);
        return events;
    }

    private void add(EventBean event)
    {
        MultiKeyUntyped key = EventBeanUtility.getMultiKey(event, propertyGetters);

        Set<EventBean> events = propertyIndex.get(key);
        if (events == null)
        {
            events = new HashSet<EventBean>();
            propertyIndex.put(key, events);
        }

        if (events.contains(event))
        {
            throw new IllegalArgumentException("Event already in index, event=" + event);
        }

        events.add(event);
    }

    private void remove(EventBean event)
    {
        MultiKeyUntyped key = EventBeanUtility.getMultiKey(event, propertyGetters);

        Set<EventBean> events = propertyIndex.get(key);
        if (events == null)
        {
            log.debug(".remove Event could not be located in index, event " + event);
            return;
        }

        if (!events.remove(event))
        {
            // Not an error, its possible that an old-data event is artificial (such as for statistics) and
            // thus did not correspond to a new-data event raised earlier.
            log.debug(".remove Event could not be located in index, event " + event);
            return;
        }

        if (events.isEmpty())
        {
            propertyIndex.remove(key);
        }
    }

    public String toString()
    {
        return "PropertyIndexedEventTable" +
                " streamNum=" + streamNum +
                " propertyNames=" + Arrays.toString(propertyNames);
    }

    private static Log log = LogFactory.getLog(PropertyIndexedEventTable.class);
}
