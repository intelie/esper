package net.esper.view;

import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;

import net.esper.event.EventType;
import net.esper.event.EventBean;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

/**
 * Event stream implementation that does not keep any window by itself of the events coming into the stream.
 */
public final class ZeroDepthStream implements EventStream
{
    private final LinkedList<View> children = new LinkedList<View>();
    private final EventType eventType;

    /**
     * Ctor.
     * @param eventType - type of event
     */
    public ZeroDepthStream(EventType eventType)
    {
        this.eventType = eventType;
    }

    public final void insert(EventBean event)
    {
        if (log.isDebugEnabled())
        {
            log.debug(".insert Received event, updating child views, event=" + event);
        }

        // Get a new array created rather then re-use the old one since some client listeners
        // to this view may keep reference to the new data
        EventBean[] row = new EventBean[]{event};
        for (View childView : children)
        {
            childView.update(row, null);
        }
    }

    public final EventType getEventType()
    {
        return eventType;
    }

    public final Iterator<EventBean> iterator()
    {
        return null;
    }

    public final View addView(View view)
    {
        children.add(view);
        view.setParent(this);
        return view;
    }

    public final List<View> getViews()
    {
        return children;
    }

    public final boolean removeView(View view)
    {
        boolean isRemoved = children.remove(view);
        view.setParent(null);
        return isRemoved;
    }

    public final boolean hasViews()
    {
        return (!children.isEmpty());
    }

    private static final Log log = LogFactory.getLog(ZeroDepthStream.class);
}

