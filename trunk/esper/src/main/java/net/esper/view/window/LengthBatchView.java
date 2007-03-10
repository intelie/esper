package net.esper.view.window;

import net.esper.collection.ViewUpdatedCollection;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.view.CloneableView;
import net.esper.view.View;
import net.esper.view.StatementServiceContext;
import net.esper.view.ViewSupport;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * A data view that aggregates events in a stream and releases them in one batch when a maximum number of events has
 * been collected.
 * <p>
 * The view works similar to a length_window but is not continuous, and similar to a time_batch however is not time-based
 * but reacts to the number of events.
 * <p>
 * The view releases the batched events, when a certain number of batched events has been reached or exceeded,
 * as new data to child views. The prior batch if
 * not empty is released as old data to any child views. The view doesn't release intervals with no old or new data.
 * It also does not collect old data published by a parent view.
 * <p>
 * If there are no events in the current and prior batch, the view will not invoke the update method of child views.
 */
public final class LengthBatchView extends ViewSupport implements CloneableView, DataWindowView
{
    // View parameters
    private final LengthBatchViewFactory lengthBatchViewFactory;
    private final int size;
    private final ViewUpdatedCollection viewUpdatedCollection;

    // Current running windows
    private LinkedList<EventBean> lastBatch = null;
    private LinkedList<EventBean> currentBatch = new LinkedList<EventBean>();

    /**
     * Constructor.
     * @param size is the number of events to batch
     * @param viewUpdatedCollection is a collection that the view must update when receiving events
     * @param lengthBatchViewFactory for copying this view in a group-by
     */
    public LengthBatchView(LengthBatchViewFactory lengthBatchViewFactory,
                         int size,
                         ViewUpdatedCollection viewUpdatedCollection)
    {
        this.lengthBatchViewFactory = lengthBatchViewFactory;
        this.size = size;
        this.viewUpdatedCollection = viewUpdatedCollection;

        if (size <= 0)
        {
            throw new IllegalArgumentException("Invalid size parameter, size=" + size);
        }
    }

    public View cloneView(StatementServiceContext statementServiceContext)
    {
        return lengthBatchViewFactory.makeView(statementServiceContext);
    }

    /**
     * Returns the number of events to batch (data window size).
     * @return batch size
     */
    public final int getSize()
    {
        return size;
    }

    public final EventType getEventType()
    {
        return parent.getEventType();
    }

    public final void update(EventBean[] newData, EventBean[] oldData)
    {
        if (log.isDebugEnabled())
        {
            log.debug(".update Received update, " +
                    "  newData.length==" + ((newData == null) ? 0 : newData.length) +
                    "  oldData.length==" + ((oldData == null) ? 0 : oldData.length));
        }

        // we don't care about removed data from a prior view
        if ((newData == null) || (newData.length == 0))
        {
            return;
        }

        // add data points to the current batch
        for (int i = 0; i < newData.length; i++)
        {
            currentBatch.add(newData[i]);
        }

        // check if we reached the minimum size
        if (currentBatch.size() < size)
        {
            // done if no overflow
            return;
        }

        sendBatch();
    }

    /**
     * This method updates child views and clears the batch of events.
     */
    protected final void sendBatch()
    {
        if (log.isDebugEnabled())
        {
            log.debug(".sendBatch Update child views");
        }

        // If there are child views and the batch was filled, fireStatementStopped update method
        if (this.hasViews())
        {
            // Convert to object arrays
            EventBean[] newData = null;
            EventBean[] oldData = null;
            if (!currentBatch.isEmpty())
            {
                newData = currentBatch.toArray(new EventBean[0]);
            }
            if ((lastBatch != null) && (!lastBatch.isEmpty()))
            {
                oldData = lastBatch.toArray(new EventBean[0]);
            }

            // Post new data (current batch) and old data (prior batch)
            if (viewUpdatedCollection != null)
            {
                viewUpdatedCollection.update(newData, oldData);
            }
            if ((newData != null) || (oldData != null))
            {
                updateChildren(newData, oldData);
            }
        }

        if (log.isDebugEnabled())
        {
            log.debug(".sendBatch Published updated data, ....newData size=" + currentBatch.size());
            for (Object object : currentBatch)
            {
                log.debug(".sendBatch object=" + object);
            }
        }

        lastBatch = currentBatch;
        currentBatch = new LinkedList<EventBean>();
    }

    /**
     * Returns true if the window is empty, or false if not empty.
     * @return true if empty
     */
    public boolean isEmpty()
    {
        if (lastBatch != null)
        {
            if (!lastBatch.isEmpty())
            {
                return false;
            }
        }
        return currentBatch.isEmpty();
    }

    public final Iterator<EventBean> iterator()
    {
        return currentBatch.iterator();
    }

    public final String toString()
    {
        return this.getClass().getName() +
                " size=" + size;
    }

    private static final Log log = LogFactory.getLog(LengthBatchView.class);
}
