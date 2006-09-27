package net.esper.filter;

import net.esper.event.EventBean;

/**
 * Interface for a callback method to be called when an event matches a filter specification.
 */
public interface FilterCallback
{
    /**
     * Indicate that an event was evaluated by the {@link FilterService}
     * which matches the filter specification {@link FilterSpec} associated with this callback.
     * @param event - the event received that matches the filter specification
     */
    public void matchFound(EventBean event);
}

