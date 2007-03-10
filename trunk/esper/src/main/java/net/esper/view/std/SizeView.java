package net.esper.view.std;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.esper.collection.SingleEventIterator;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.view.*;

/**
 * This view is a very simple view presenting the number of elements in a stream or view.
 * The view computes a single long-typed count of the number of events passed through it similar
 * to the base statistics COUNT column.
 */
public final class SizeView extends ViewSupport implements CloneableView
{
    private final StatementServiceContext statementServiceContext;
    private EventType eventType;
    private long size = 0;

    /**
     * Ctor.
     * @param statementServiceContext is services
     */
    public SizeView(StatementServiceContext statementServiceContext)
    {
        this.statementServiceContext = statementServiceContext;
        this.eventType = createEventType(statementServiceContext);
    }

    public View cloneView(StatementServiceContext statementServiceContext)
    {
        return new SizeView(statementServiceContext);
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

        // If there are child views, fireStatementStopped update method
        if ((this.hasViews()) && (priorSize != size))
        {
            Map<String, Object> postNewData = new HashMap<String, Object>();
            postNewData.put(ViewFieldEnum.SIZE_VIEW__SIZE.getName(), size);
            Map<String, Object> postOldData = new HashMap<String, Object>();
            postOldData.put(ViewFieldEnum.SIZE_VIEW__SIZE.getName(), priorSize);
            updateChildren(new EventBean[] {statementServiceContext.getEventAdapterService().createMapFromValues(postNewData, eventType)},
                    new EventBean[] {statementServiceContext.getEventAdapterService().createMapFromValues(postOldData, eventType)});
        }                
    }

    public final Iterator<EventBean> iterator()
    {
        HashMap<String, Object> current = new HashMap<String, Object>();
        current.put(ViewFieldEnum.SIZE_VIEW__SIZE.getName(), size);
        return new SingleEventIterator(statementServiceContext.getEventAdapterService().createMapFromValues(current, eventType));
    }

    public final String toString()
    {
        return this.getClass().getName();
    }

    /**
     * Creates the event type for this view
     * @param statementServiceContext is the event adapter service
     * @return event type for view
     */
    protected static EventType createEventType(StatementServiceContext statementServiceContext)
    {
        Map<String, Class> schemaMap = new HashMap<String, Class>();
        schemaMap.put(ViewFieldEnum.SIZE_VIEW__SIZE.getName(), long.class);
        return statementServiceContext.getEventAdapterService().createAnonymousMapType(schemaMap);
    }
}
