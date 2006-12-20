package net.esper.pattern;

import net.esper.event.EventBean;

import java.util.*;

/**
 * Collection for internal use similar to the MatchedEventMap class in the client package
 * that holds the one or more events that could match any defined event expressions.
 * The optional tag value supplied when an event expression is created is used as a key for placing
 * matching event objects into this collection.
 */
public final class MatchedEventMap
{
    private Hashtable<String, EventBean> events = new Hashtable<String, EventBean>();

    /**
     * Constructor creates an empty collection of events.
     */
    public MatchedEventMap()
    {
    }

    /**
     * Add an event to the collection identified by the given tag.
     * @param tag is an identifier to retrieve the event from
     * @param event is the event object to be added
     */
    public void add(final String tag, final EventBean event)
    {
        events.put(tag, event);
    }

    /**
     * Returns a Hashtable containing the events where the key is the event tag string and the value is the event
     * instance.
     * @return Hashtable containing event instances
     */
    public Hashtable<String, EventBean> getMatchingEvents()
    {
        return events;
    }

    /**
     * Returns a single event instance given the tag identifier, or null if the tag could not be located.
     * @param tag is the identifier to look for
     * @return event instances for the tag
     */
    public EventBean getMatchingEvent(final String tag)
    {
        return events.get(tag);
    }

    public boolean equals(final Object otherObject)
    {
        if (otherObject == this)
        {
            return true;
        }

        if (otherObject == null)
        {
            return false;
        }

        if (getClass() != otherObject.getClass())
        {
            return false;
        }

        final MatchedEventMap other = (MatchedEventMap) otherObject;

        if (events.size() != other.events.size())
        {
            return false;
        }

        // Compare entry by entry
        for (Map.Entry<String, EventBean> entry : events.entrySet())
        {
            final String tag = entry.getKey();
            final Object event = entry.getValue();

            if (other.getMatchingEvent(tag) != event)
            {
                return false;
            }
        }

        return true;
    }

    public String toString()
    {
        final StringBuffer buffer = new StringBuffer();
        int count = 0;

        for (Map.Entry<String, EventBean> entry : events.entrySet())
        {
            buffer.append(" (" + (count++) + ") ");
            buffer.append("tag=" + entry.getKey());
            buffer.append("  event=" + entry.getValue());
        }

        return buffer.toString();
    }

    public int hashCode()
    {
        return events.hashCode();
    }

    /**
     * Make a shallow copy of this collection.
     * @return shallow copy
     */
    public MatchedEventMap shallowCopy()
    {
        final MatchedEventMap copy = new MatchedEventMap();
        copy.events = (Hashtable<String, EventBean>) events.clone();
        return copy;
    }

    /**
     * Merge the state of an other match event structure into this one by adding all entries
     * within the MatchedEventMap to this match event.
     * @param other is the other instance to merge in.
     */
    public void merge(final MatchedEventMap other)
    {
        for (Map.Entry<String, EventBean> entry : other.events.entrySet())
        {
            events.put(entry.getKey(), entry.getValue());
        }
    }
}
