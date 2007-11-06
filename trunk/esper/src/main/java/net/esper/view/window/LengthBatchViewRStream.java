package net.esper.view.window;

import net.esper.core.StatementContext;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.util.ExecutionPathDebugLog;
import net.esper.view.CloneableView;
import net.esper.view.View;
import net.esper.view.ViewSupport;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Iterator;
import java.util.LinkedHashSet;

/**
* Same as the {@link LengthBatchView}, this view also supports fast-remove from the batch for remove stream events.
*/
public final class LengthBatchViewRStream extends ViewSupport implements CloneableView, DataWindowView
{
    // View parameters
    private final LengthBatchViewFactory lengthBatchViewFactory;
    private final int size;

    // Current running windows
    private LinkedHashSet<EventBean> lastBatch = null;
    private LinkedHashSet<EventBean> currentBatch = new LinkedHashSet<EventBean>();

    /**
     * Constructor.
     * @param size is the number of events to batch
     * @param lengthBatchViewFactory for copying this view in a group-by
     */
    public LengthBatchViewRStream(LengthBatchViewFactory lengthBatchViewFactory,
                         int size)
    {
        this.lengthBatchViewFactory = lengthBatchViewFactory;
        this.size = size;

        if (size <= 0)
        {
            throw new IllegalArgumentException("Invalid size parameter, size=" + size);
        }
    }

    public View cloneView(StatementContext statementContext)
    {
        return lengthBatchViewFactory.makeView(statementContext);
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
        if ((ExecutionPathDebugLog.isEnabled()) && (log.isDebugEnabled()))
        {
            log.debug(".update Received update, " +
                    "  newData.length==" + ((newData == null) ? 0 : newData.length) +
                    "  oldData.length==" + ((oldData == null) ? 0 : oldData.length));
        }

        if (oldData != null)
        {
            for (int i = 0; i < oldData.length; i++)
            {
                currentBatch.remove(oldData[i]);
            }
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
        if ((ExecutionPathDebugLog.isEnabled()) && (log.isDebugEnabled()))
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
            if ((newData != null) || (oldData != null))
            {
                updateChildren(newData, oldData);
            }
        }

        if ((ExecutionPathDebugLog.isEnabled()) && (log.isDebugEnabled()))
        {
            log.debug(".sendBatch Published updated data, ....newData size=" + currentBatch.size());
            for (Object object : currentBatch)
            {
                log.debug(".sendBatch object=" + object);
            }
        }

        lastBatch = currentBatch;
        currentBatch = new LinkedHashSet<EventBean>();
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

    private static final Log log = LogFactory.getLog(LengthBatchViewRStream.class);
}
