package net.esper.view.std;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.esper.collection.SingleEventIterator;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.view.*;
import net.esper.view.stat.CorrelationBean;

/**
 * This view is a very simple view presenting the number of elements in a stream or view.
 * The view computes a single long-typed count of the number of events passed through it similar
 * to the base statistics COUNT column.
 */
public final class SizeView extends ViewSupport implements ContextAwareView
{
    private ViewServiceContext viewServiceContext;
    private EventType eventType;
    private long size = 0;

    /**
     * Constructor.
     */
    public SizeView()
    {
    }

    public ViewServiceContext getViewServiceContext()
    {
        return viewServiceContext;
    }

    public void setViewServiceContext(ViewServiceContext viewServiceContext)
    {
        this.viewServiceContext = viewServiceContext;
        this.eventType = createEventType(viewServiceContext);
    }

    public final EventType getEventType()
    {
        return eventType;
    }

    public final void update(EventBean[] newData, EventBean[] oldData)
    {
        long priorSize = size;

        // add data points to the window
        if (newData != null)
        {
            size += newData.length;
        }

        if (oldData != null)
        {
            size -= oldData.length;
        }

        // If there are child views, fire update method
        if ((this.hasViews()) && (priorSize != size))
        {
            Map<String, Object> postNewData = new HashMap<String, Object>();
            postNewData.put(ViewFieldEnum.SIZE_VIEW__SIZE.getName(), size);
            Map<String, Object> postOldData = new HashMap<String, Object>();
            postOldData.put(ViewFieldEnum.SIZE_VIEW__SIZE.getName(), priorSize);
            updateChildren(new EventBean[] {viewServiceContext.getEventAdapterService().createMapFromValues(postNewData, eventType)},
                    new EventBean[] {viewServiceContext.getEventAdapterService().createMapFromValues(postOldData, eventType)});
        }                
    }

    public final Iterator<EventBean> iterator()
    {
        HashMap<String, Object> current = new HashMap<String, Object>();
        current.put(ViewFieldEnum.SIZE_VIEW__SIZE.getName(), size);
        return new SingleEventIterator(viewServiceContext.getEventAdapterService().createMapFromValues(current, eventType));
    }

    public final String toString()
    {
        return this.getClass().getName();
    }

    /**
     * Creates the event type for this view
     * @param viewServiceContext is the event adapter service
     * @return event type for view
     */
    protected static EventType createEventType(ViewServiceContext viewServiceContext)
    {
        Map<String, Class> schemaMap = new HashMap<String, Class>();
        schemaMap.put(ViewFieldEnum.SIZE_VIEW__SIZE.getName(), long.class);
        return viewServiceContext.getEventAdapterService().createAnonymousMapType(schemaMap);
    }
}
