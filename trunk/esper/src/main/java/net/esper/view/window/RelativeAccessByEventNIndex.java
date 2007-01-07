package net.esper.view.window;

import net.esper.event.EventBean;

/**
 * Provides access to prior events given an event from which to count back, and an index to look at.
 */
public interface RelativeAccessByEventNIndex
{
    /**
     * Returns the prior event to the given event counting back the number of events as supplied by index.
     * @param event is the event to count back from
     * @param index is the number of events to go back
     * @return event
     */
    public EventBean getRelativeToEvent(EventBean event, int index);
}
