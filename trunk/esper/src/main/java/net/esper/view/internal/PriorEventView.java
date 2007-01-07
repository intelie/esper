package net.esper.view.internal;

import net.esper.view.ViewSupport;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.collection.ViewUpdatedCollection;

import java.util.Iterator;

/**
 * View that provides access to prior events posted by the parent view for use by 'prior' expression nodes.
 */
public class PriorEventView extends ViewSupport
{
    private ViewUpdatedCollection buffer;

    /**
     * Ctor.
     * @param buffer is handling the actual storage of events for use in the 'prior' expression
     */
    public PriorEventView(ViewUpdatedCollection buffer)
    {
        this.buffer = buffer;
    }

    public void update(EventBean[] newData, EventBean[] oldData)
    {
        buffer.update(newData, oldData);
        this.updateChildren(newData, oldData);
    }

    /**
     * Returns the underlying buffer used for access to prior events.
     * @return buffer
     */
    protected ViewUpdatedCollection getBuffer()
    {
        return buffer;
    }

    public EventType getEventType()
    {
        return parent.getEventType();
    }

    public Iterator<EventBean> iterator()
    {
        return parent.iterator();
    }
}
