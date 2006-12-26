package net.esper.view.window;

import net.esper.event.EventBean;

/**
 * Provides access to prior events given
 */
public interface RelativeAccessByEventNIndex
{
    public EventBean getRelativeToEvent(EventBean event, int index);
}
