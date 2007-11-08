package net.esper.view.window;

import net.esper.view.ViewSupport;
import net.esper.view.CloneableView;
import net.esper.view.View;
import net.esper.view.DataWindowView;
import net.esper.core.StatementContext;
import net.esper.core.ExtensionServicesContext;
import net.esper.core.EPStatementHandleCallback;
import net.esper.collection.ViewUpdatedCollection;
import net.esper.schedule.ScheduleSlot;
import net.esper.schedule.ScheduleHandleCallback;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.util.ExecutionPathDebugLog;
import net.esper.client.EPException;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

/**
 * A data window view that holds events in a stream and only removes events from a stream (rstream) if
 * no more events arrive for a given time interval.
 * <p>
 * No batch version of the view exists as the batch version is simply the remove stream of this view, which removes
 * in batches.
 * <p>
 * The view is continuous, the insert stream consists of arriving events. The remove stream
 * only posts current window contents when no more events arrive for a given timer interval.
 */
public final class TimeAccumView extends ViewSupport implements CloneableView, DataWindowView
{
    // View parameters
    private final TimeAccumViewFactory factory;
    private final StatementContext statementContext;
    private final long msecIntervalSize;
    private final ViewUpdatedCollection viewUpdatedCollection;
    private final ScheduleSlot scheduleSlot;

    // Current running parameters
    private ArrayList<EventBean> currentBatch = new ArrayList<EventBean>();
    private long callbackScheduledTime;
    private EPStatementHandleCallback handle;

    /**
     * Constructor.
     * @param msecIntervalSize is the number of milliseconds to batch events for
     * @param viewUpdatedCollection is a collection that the view must update when receiving events
     * @param timeBatchViewFactory fr copying this view in a group-by
     * @param statementContext is required view services
     */
    public TimeAccumView(TimeAccumViewFactory timeBatchViewFactory,
                         StatementContext statementContext,
                         long msecIntervalSize,
                         ViewUpdatedCollection viewUpdatedCollection)
    {
        this.statementContext = statementContext;
        this.factory = timeBatchViewFactory;
        this.msecIntervalSize = msecIntervalSize;
        this.viewUpdatedCollection = viewUpdatedCollection;

        this.scheduleSlot = statementContext.getScheduleBucket().allocateSlot();

        ScheduleHandleCallback callback = new ScheduleHandleCallback() {
            public void scheduledTrigger(ExtensionServicesContext extensionServicesContext)
            {
                TimeAccumView.this.sendRemoveStream();
            }
        };
        handle = new EPStatementHandleCallback(statementContext.getEpStatementHandle(), callback);
    }

    public View cloneView(StatementContext statementContext)
    {
        return factory.makeView(statementContext);
    }

    /**
     * Returns the interval size in milliseconds.
     * @return batch size
     */
    public final long getMsecIntervalSize()
    {
        return msecIntervalSize;
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

        if (statementContext == null)
        {
            String message = "View context has not been supplied, cannot addSchedule callback";
            log.fatal(".update " + message);
            throw new EPException(message);
        }

        // we don't care about removed data from a prior view
        if ((newData == null) || (newData.length == 0))
        {
            return;
        }

        // If we have an empty window about to be filled for the first time, addSchedule a callback
        boolean removeSchedule = false;
        boolean addSchedule = false;
        long timestamp = statementContext.getSchedulingService().getTime();

        if (!currentBatch.isEmpty())
        {
            // check if we need to reschedule
            long callbackTime = timestamp + msecIntervalSize;
            if (callbackTime != callbackScheduledTime)
            {
                removeSchedule = true;
                addSchedule = true;
            }
        }
        else
        {
            addSchedule = true;
        }

        if (removeSchedule)
        {
            statementContext.getSchedulingService().remove(handle, scheduleSlot);
        }
        if (addSchedule)
        {
            statementContext.getSchedulingService().add(msecIntervalSize, handle, scheduleSlot);
            callbackScheduledTime = msecIntervalSize + timestamp;
        }

        // add data points to the window
        for (int i = 0; i < newData.length; i++)
        {
            currentBatch.add(newData[i]);
        }

        // forward insert stream to child views
        if (viewUpdatedCollection != null)
        {
            viewUpdatedCollection.update(newData, null);
        }

        // update child views
        if (this.hasViews())
        {
            updateChildren(newData, null);
        }
    }

    /**
     * This method sends the remove stream for all accumulated events.
     */
    protected final void sendRemoveStream()
    {
        callbackScheduledTime = -1;

        if ((ExecutionPathDebugLog.isEnabled()) && (log.isDebugEnabled()))
        {
            log.debug(".sendRemoveStream Update child views, " +
                    "  time=" + statementContext.getSchedulingService().getTime());
        }

        // If there are child views and the batch was filled, fireStatementStopped update method
        if (this.hasViews())
        {
            // Convert to object arrays
            EventBean[] oldData = null;
            if (!currentBatch.isEmpty())
            {
                oldData = currentBatch.toArray(new EventBean[0]);
            }

            // Post old data
            if (viewUpdatedCollection != null)
            {
                viewUpdatedCollection.update(null, oldData);
            }

            if (oldData != null)
            {
                updateChildren(null, oldData);
            }
        }

        currentBatch.clear();
    }

    /**
     * Returns true if the window is empty, or false if not empty.
     * @return true if empty
     */
    public boolean isEmpty()
    {
        return currentBatch.isEmpty();
    }

    public final Iterator<EventBean> iterator()
    {
        return currentBatch.iterator();
    }

    public final String toString()
    {
        return this.getClass().getName() + " msecIntervalSize=" + msecIntervalSize;
    }

    private static final Log log = LogFactory.getLog(TimeAccumView.class);
}
