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
import java.util.LinkedHashSet;

/**
 * Same as the {@link TimeBatchView}, this view also supports fast-remove from the batch for remove stream events.
 */
public final class TimeBatchViewRStream extends ViewSupport implements CloneableView, DataWindowView
{
    // View parameters
    private final TimeBatchViewFactory timeBatchViewFactory;
    private final StatementContext statementContext;
    private final long msecIntervalSize;
    private final Long initialReferencePoint;
    private final ScheduleSlot scheduleSlot;

    // Current running parameters
    private Long currentReferencePoint;
    private LinkedHashSet<EventBean> lastBatch = null;
    private LinkedHashSet<EventBean> currentBatch = new LinkedHashSet<EventBean>();
    private boolean isCallbackScheduled;

    /**
     * Constructor.
     * @param msecIntervalSize is the number of milliseconds to batch events for
     * @param referencePoint is the reference point onto which to base intervals, or null if
     * there is no such reference point supplied
     * @param timeBatchViewFactory fr copying this view in a group-by
     * @param statementContext is required view services
     */
    public TimeBatchViewRStream(TimeBatchViewFactory timeBatchViewFactory,
                         StatementContext statementContext,
                         long msecIntervalSize,
                         Long referencePoint)
    {
        this.statementContext = statementContext;
        this.timeBatchViewFactory = timeBatchViewFactory;
        this.msecIntervalSize = msecIntervalSize;
        this.initialReferencePoint = referencePoint;

        this.scheduleSlot = statementContext.getScheduleBucket().allocateSlot();
    }

    public View cloneView(StatementContext statementContext)
    {
        return timeBatchViewFactory.makeView(statementContext);
    }

    /**
     * Returns the interval size in milliseconds.
     * @return batch size
     */
    public final long getMsecIntervalSize()
    {
        return msecIntervalSize;
    }

    /**
     * Gets the reference point to use to anchor interval start and end dates to.
     * @return is the millisecond reference point.
     */
    public final Long getInitialReferencePoint()
    {
        return initialReferencePoint;
    }

    public final EventType getEventType()
    {
        return parent.getEventType();
    }

    public final void update(EventBean[] newData, EventBean[] oldData)
    {
        if ((ExecutionPathDebugLog.isDebugEnabled) && (log.isDebugEnabled()))
        {
            log.debug(".update Received update, " +
                    "  newData.length==" + ((newData == null) ? 0 : newData.length) +
                    "  oldData.length==" + ((oldData == null) ? 0 : oldData.length));
        }

        if (statementContext == null)
        {
            String message = "View context has not been supplied, cannot schedule callback";
            log.fatal(".update " + message);
            throw new EPException(message);
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

        // If we have an empty window about to be filled for the first time, schedule a callback
        if (currentBatch.isEmpty())
        {
            if (currentReferencePoint == null)
            {
                currentReferencePoint = initialReferencePoint;
                if (currentReferencePoint == null)
                {
                    currentReferencePoint = statementContext.getSchedulingService().getTime();
                }
            }

            // Schedule the next callback if there is none currently scheduled
            if (!isCallbackScheduled)
            {
                scheduleCallback();
                isCallbackScheduled = true;
            }
        }

        // add data points to the timeWindow
        for (int i = 0; i < newData.length; i++)
        {
            currentBatch.add(newData[i]);
        }

        // We do not update child views, since we batch the events.
    }

    /**
     * This method updates child views and clears the batch of events.
     * We schedule a new callback at this time if there were events in the batch.
     */
    protected final void sendBatch()
    {
        isCallbackScheduled = false;

        if ((ExecutionPathDebugLog.isDebugEnabled) && (log.isDebugEnabled()))
        {
            log.debug(".sendBatch Update child views, " +
                    "  time=" + statementContext.getSchedulingService().getTime());
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

            if ((newData != null) || (oldData != null))
            {
                updateChildren(newData, oldData);
            }
        }

        if ((ExecutionPathDebugLog.isDebugEnabled) && (log.isDebugEnabled()))
        {
            log.debug(".sendBatch Published updated data, ....newData size=" + currentBatch.size());
            for (Object object : currentBatch)
            {
                log.debug(".sendBatch object=" + object);
            }
        }

        // Only if there have been any events in this or the last interval do we schedule a callback,
        // such as to not waste resources when no events arrive.
        if ((!currentBatch.isEmpty()) || ((lastBatch != null) && (!lastBatch.isEmpty())))
        {
            scheduleCallback();
            isCallbackScheduled = true;
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
                " msecIntervalSize=" + msecIntervalSize +
                " initialReferencePoint=" + initialReferencePoint;
    }

    private void scheduleCallback()
    {
        long current = statementContext.getSchedulingService().getTime();
        long afterMSec = TimeBatchView.computeWaitMSec(current, this.currentReferencePoint, this.msecIntervalSize);

        if ((ExecutionPathDebugLog.isDebugEnabled) && (log.isDebugEnabled()))
        {
            log.debug(".scheduleCallback Scheduled new callback for " +
                    " afterMsec=" + afterMSec +
                    " now=" + current +
                    " currentReferencePoint=" + currentReferencePoint +
                    " initialReferencePoint=" + initialReferencePoint +
                    " msecIntervalSize=" + msecIntervalSize);
        }

        ScheduleHandleCallback callback = new ScheduleHandleCallback() {
            public void scheduledTrigger(ExtensionServicesContext extensionServicesContext)
            {
                TimeBatchViewRStream.this.sendBatch();
            }
        };
        EPStatementHandleCallback handle = new EPStatementHandleCallback(statementContext.getEpStatementHandle(), callback);
        statementContext.getSchedulingService().add(afterMSec, handle, scheduleSlot);
    }

    private static final Log log = LogFactory.getLog(TimeBatchViewRStream.class);
}
