package net.esper.filter;

import net.esper.event.EventBean;

/**
 * Interface for a callback method to be called when an event matches a filter specification. Provided
 * as a convenience for use as a filter handle for registering with the {@link FilterService}.
 */
public interface FilterHandleCallback extends FilterHandle
{
    /**
     * Indicate that an event was evaluated by the {@link net.esper.filter.FilterService}
     * which matches the filter specification {@link net.esper.filter.FilterSpec} associated with this callback.
     * @param event - the event received that matches the filter specification
     */
    public void matchFound(EventBean event);
}
