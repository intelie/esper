package com.espertech.esper.view.internal;

import com.espertech.esper.collection.ViewUpdatedCollection;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.EventType;
import com.espertech.esper.view.ViewSupport;
import com.espertech.esper.view.Viewable;

import java.util.Iterator;

/**
 * View that provides access to prior events posted by the parent view for use by 'prior' expression nodes.
 */
public class PriorEventView extends ViewSupport
{
    private Viewable parent;
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

    public void setParent(Viewable parent)
    {
        this.parent = parent;
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
