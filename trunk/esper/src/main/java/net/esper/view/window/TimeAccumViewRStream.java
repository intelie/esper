package net.esper.view.window;

import net.esper.client.EPException;
import net.esper.core.EPStatementHandleCallback;
import net.esper.core.ExtensionServicesContext;
import net.esper.core.StatementContext;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.schedule.ScheduleHandleCallback;
import net.esper.schedule.ScheduleSlot;
import net.esper.util.ExecutionPathDebugLog;
import net.esper.view.CloneableView;
import net.esper.view.View;
import net.esper.view.ViewSupport;
import net.esper.view.DataWindowView;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * A data window view that holds events in a stream and only removes events from a stream (rstream) if
 * no more events arrive for a given time interval, also handling the remove stream
 * by keeping set-like semantics. See {@link TimeAccumView} for the same behavior without
 * remove stream handling. 
 */
public final class TimeAccumViewRStream extends ViewSupport implements CloneableView, DataWindowView
{
    // View parameters
    private final TimeAccumViewFactory factory;
    private final StatementContext statementContext;
    private final long msecIntervalSize;
    private final ScheduleSlot scheduleSlot;

    // Current running parameters
    private LinkedHashMap<EventBean, Long> currentBatch = new LinkedHashMap<EventBean, Long>();
    private EventBean lastEvent;
    private long callbackScheduledTime;
    private EPStatementHandleCallback handle;

    /**
     * Constructor.
     * @param msecIntervalSize is the number of milliseconds to batch events for
     * @param timeBatchViewFactory fr copying this view in a group-by
     * @param statementContext is required view services
     */
    public TimeAccumViewRStream(TimeAccumViewFactory timeBatchViewFactory,
                         StatementContext statementContext,
                         long msecIntervalSize)
    {
        this.statementContext = statementContext;
        this.factory = timeBatchViewFactory;
        this.msecIntervalSize = msecIntervalSize;

        this.scheduleSlot = statementContext.getScheduleBucket().allocateSlot();

        ScheduleHandleCallback callback = new ScheduleHandleCallback() {
            public void scheduledTrigger(ExtensionServicesContext extensionServicesContext)
            {
                TimeAccumViewRStream.this.sendRemoveStream();
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
        if ((newData != null) && (newData.length > 0))
        {
            // If we have an empty window about to be filled for the first time, add a callback
            boolean removeSchedule = false;
            boolean addSchedule = false;
            long timestamp = statementContext.getSchedulingService().getTime();

            // if the window is already filled, then we may need to reschedule
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
                callbackScheduledTime = -1;
            }
            if (addSchedule)
            {
                statementContext.getSchedulingService().add(msecIntervalSize, handle, scheduleSlot);
                callbackScheduledTime = msecIntervalSize + timestamp;
            }

            // add data points to the window
            for (int i = 0; i < newData.length; i++)
            {
                currentBatch.put(newData[i], timestamp);
                lastEvent = newData[i];
            }
        }

        if ((oldData != null) && (oldData.length > 0))
        {
            boolean removedLastEvent = false;
            for (EventBean anOldData : oldData)
            {
                currentBatch.remove(anOldData);
                if (anOldData == lastEvent)
                {
                    removedLastEvent = true;
                }
            }

            // we may need to reschedule as the newest event may have been deleted
            if (currentBatch.size() == 0)
            {
                statementContext.getSchedulingService().remove(handle, scheduleSlot);
                callbackScheduledTime = -1;
                lastEvent = null;
            }
            else
            {
                // reschedule if the last event was removed
                if (removedLastEvent)
                {
                    EventBean[] events = currentBatch.keySet().toArray(new EventBean[0]);
                    lastEvent = events[events.length - 1];
                    long lastTimestamp = currentBatch.get(lastEvent);

                    // reschedule, newest event deleted
                    long timestamp = statementContext.getSchedulingService().getTime();
                    long callbackTime = lastTimestamp + msecIntervalSize;
                    long deltaFromNow = callbackTime - timestamp;
                    if (callbackTime != callbackScheduledTime)
                    {
                        statementContext.getSchedulingService().remove(handle, scheduleSlot);
                        statementContext.getSchedulingService().add(deltaFromNow, handle, scheduleSlot);
                        callbackScheduledTime = callbackTime;
                    }
                }
            }
        }

        // update child views
        if (this.hasViews())
        {
            updateChildren(newData, oldData);
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
                oldData = currentBatch.keySet().toArray(new EventBean[0]);
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
        return currentBatch.keySet().iterator();
    }

    public final String toString()
    {
        return this.getClass().getName() + " msecIntervalSize=" + msecIntervalSize;
    }

    private static final Log log = LogFactory.getLog(TimeAccumViewRStream.class);
}
