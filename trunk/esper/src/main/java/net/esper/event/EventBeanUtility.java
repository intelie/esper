package net.esper.event;

import net.esper.collection.Pair;
import net.esper.collection.MultiKeyUntyped;

import java.util.*;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Method to getSelectListEvents events in collections to other collections or other event types.
 */
public class EventBeanUtility
{
    /**
     * Flatten the vector of arrays to an array. Return null if an empty vector was passed, else
     * return an array containing all the events.
     * @param eventVector vector
     * @return array with all events
     */
    public static EventBean[] flatten(Vector<EventBean[]> eventVector)
    {
        if (eventVector.size() == 0)
        {
            return null;
        }

        if (eventVector.size() == 1)
        {
            return eventVector.get(0);
        }

        int totalElements = 0;
        for (int i = 0; i < eventVector.size(); i++)
        {
            totalElements += eventVector.get(i).length;
        }

        EventBean[] result = new EventBean[totalElements];
        int destPos = 0;
        for (int i = 0; i < eventVector.size(); i++)
        {
            EventBean[] src = eventVector.get(i);
            System.arraycopy(src, 0, result, destPos, src.length);
            destPos += eventVector.get(i).length;
        }

        return result;
    }

     /**
     * Append arrays.
     * @param source array
     * @param append array
     * @return appended array
     */
    protected static EventBean[] append(EventBean[] source, EventBean[] append)
    {
        EventBean[] result = new EventBean[source.length + append.length];
        System.arraycopy(source, 0, result, 0, source.length);
        System.arraycopy(append, 0, result, source.length, append.length);
        return result;
    }

    /**
     * Convert list of events to array, returning null for empty or null lists.
     * @param eventList
     * @return array of events
     */
    public static EventBean[] toArray(List<EventBean> eventList)
    {
        if ((eventList == null) || (eventList.isEmpty()))
        {
            return null;
        }
        return eventList.toArray(new EventBean[0]);
    }

    /**
     * Returns object array containing property values of given properties, retrieved via EventPropertyGetter
     * instances.
     * @param event - event to get property values from
     * @param propertyGetters - getters to use for getting property values
     * @return object array with property values
     */
    public static Object[] getPropertyArray(EventBean event, EventPropertyGetter[] propertyGetters)
    {
        Object[] keyValues = new Object[propertyGetters.length];
        for (int i = 0; i < propertyGetters.length; i++)
        {
            keyValues[i] = propertyGetters[i].get(event);
        }
        return keyValues;
    }

    /**
     * Returns Multikey instance for given event and getters.
     * @param event - event to get property values from
     * @param propertyGetters - getters for access to properties
     * @return MultiKey with property values
     */
    public static MultiKeyUntyped getMultiKey(EventBean event, EventPropertyGetter[] propertyGetters)
    {
        Object[] keyValues = getPropertyArray(event, propertyGetters);
        return new MultiKeyUntyped(keyValues);
    }

    /**
     * Format the event and return a string representation.
     * @param event is the event to format.
     * @return string representation of event
     */
    public static String printEvent(EventBean event)
    {
        StringWriter writer = new StringWriter();
        PrintWriter buf = new PrintWriter(writer);
        printEvent(buf, event);
        return writer.toString();
    }

    private static void printEvent(PrintWriter writer, EventBean event)
    {
        String[] properties = event.getEventType().getPropertyNames();
        for (int i = 0; i < properties.length; i++)
        {
            writer.println( "#" + i + "  " + properties[i] + " = " + event.get(properties[i]));
        }
    }
}
