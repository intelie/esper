package net.esper.view.window;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;
import java.text.SimpleDateFormat;

import net.esper.event.EventType;
import net.esper.event.EventBean;
import net.esper.view.Viewable;
import net.esper.view.ViewSupport;
import net.esper.view.ContextAwareView;
import net.esper.view.ViewServiceContext;
import net.esper.schedule.ScheduleCallback;
import net.esper.schedule.ScheduleSlot;
import net.esper.client.EPException;
import net.esper.eql.parse.TimePeriodParameter;

/**
 * A data view that aggregates events in a stream and releases them in one batch at every specified time interval.
 * The view works similar to a time_window but in not continuous.
 * The view releases the batched events after the interval as new data to child views. The prior batch if
 * not empty is released as old data to child view. The view doesn't release intervals with no old or new data.
 * It also does not getSelectListEvents old data published by a parent view.
 *
 * For example, we want to calculate the average of IBM stock every hour, for the last hour.
 * The view accepts 2 parameter combinations.
 * (1) A time interval is supplied with a reference point - based on this point the intervals are set.
 * (1) A time interval is supplied but no reference point - the reference point is set when the first event arrives.
 *
 * If there are no events in the current and prior batch, the view will not invoke the update method of child views.
 * In that case also, no next callback is scheduled with the scheduling service until the next event arrives.
 */
public final class TimeBatchView extends ViewSupport implements ContextAwareView
{
    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    // View parameters
    private long msecIntervalSize;
    private Long initialReferencePoint;

    // Current running parameters
    private Long currentReferencePoint;
    private ViewServiceContext viewServiceContext;
    private LinkedList<EventBean> lastBatch = null;
    private LinkedList<EventBean> currentBatch = new LinkedList<EventBean>();
    private boolean isCallbackScheduled;
    private ScheduleSlot scheduleSlot;

    /**
     * Default constructor - required by all views to adhere to the Java bean specification.
     */
    public TimeBatchView()
    {
    }

    /**
     * Constructor.
     * @param secIntervalSize is the number of seconds to batch events for.
     */
    public TimeBatchView(int secIntervalSize)
    {
        if (secIntervalSize < 1)
        {
            throw new IllegalArgumentException("Time batch view requires a millisecond interval size of at least 100 msec");
        }
        this.msecIntervalSize = 1000 * secIntervalSize;
    }

    /**
     * Constructor.
     * @param secIntervalSize is the number of milliseconds to batch events for
     * @param referencePoint is the reference point onto which to base intervals.
     */
    public TimeBatchView(int secIntervalSize, Long referencePoint)
    {
        this(secIntervalSize);
        this.initialReferencePoint = referencePoint;
    }

    /**
     * Constructor.
     * @param secIntervalSize is the number of seconds to batch events for.
     */
    public TimeBatchView(double secIntervalSize)
    {
        if (secIntervalSize < 0.1)
        {
            throw new IllegalArgumentException("Time batch view requires a millisecond interval size of at least 100 msec");
        }
        this.msecIntervalSize = Math.round(1000 * secIntervalSize);
    }

    /**
     * Constructor.
     * @param timeTimePeriod is the number of seconds to batch events for.
     */
    public TimeBatchView(TimePeriodParameter timeTimePeriod)
    {
        this(timeTimePeriod.getNumSeconds());
    }

    /**
     * Constructor.
     * @param secIntervalSize is the number of seconds to batch events for
     * @param referencePoint is the reference point onto which to base intervals.
     */
    public TimeBatchView(double secIntervalSize, Long referencePoint)
    {
        this(secIntervalSize);
        this.initialReferencePoint = referencePoint;
    }

    /**
     * Constructor.
     * @param timeTimePeriod is the number of seconds to batch events for
     * @param referencePoint is the reference point onto which to base intervals.
     */
    public TimeBatchView(TimePeriodParameter timeTimePeriod, Long referencePoint)
    {
        this(timeTimePeriod.getNumSeconds(), referencePoint);
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
     * Sets the interval size in milliseconds.
     * @param msecIntervalSize batch size
     */
    public final void setMsecIntervalSize(long msecIntervalSize)
    {
        this.msecIntervalSize = msecIntervalSize;
    }

    /**
     * Gets the reference point to use to anchor interval start and end dates to.
     * @return is the millisecond reference point.
     */
    public final Long getInitialReferencePoint()
    {
        return initialReferencePoint;
    }

    /**
     * Sets the reference point to use to anchor interval start and end dates to.
     * @param initialReferencePoint is the millisecond reference point.
     */
    public final void setInitialReferencePoint(Long initialReferencePoint)
    {
        this.initialReferencePoint = initialReferencePoint;
    }

    public final String attachesTo(Viewable parentView)
    {
        // Attaches to any parent view
        return null;
    }

    public final EventType getEventType()
    {
        return parent.getEventType();
    }

    public ViewServiceContext getViewServiceContext()
    {
        return viewServiceContext;
    }

    public void setViewServiceContext(ViewServiceContext viewServiceContext)
    {
        this.viewServiceContext = viewServiceContext;
        this.scheduleSlot = viewServiceContext.getScheduleBucket().allocateSlot();
    }

    public final void update(EventBean[] newData, EventBean[] oldData)
    {
        if (log.isDebugEnabled())
        {
            log.debug(".update Received update, " +
                    "  newData.length==" + ((newData == null) ? 0 : newData.length) +
                    "  oldData.length==" + ((oldData == null) ? 0 : oldData.length));
        }

        if (viewServiceContext == null)
        {
            String message = "View context has not been supplied, cannot schedule callback";
            log.fatal(".update " + message);
            throw new EPException(message);
        }

        // we don't care about removed data from a prior view
        if ((newData == null) || (newData.length == 0))
        {
            return;
        }

        // If we have an empty window about to be filled for the first time, schedule a callback
        if (currentBatch.size() == 0)
        {
            if (currentReferencePoint == null)
            {
                currentReferencePoint = initialReferencePoint;
                if (currentReferencePoint == null)
                {
                    currentReferencePoint = viewServiceContext.getSchedulingService().getTime();
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

        if (log.isDebugEnabled())
        {
            log.debug(".sendBatch Update child views, " +
                    "  time=" + dateFormat.format(viewServiceContext.getSchedulingService().getTime()));
        }

        // If there are child views and the batch was filled, fire update method
        if (this.hasViews())
        {
            // Convert to object arrays
            EventBean[] newData = null;
            EventBean[] oldData = null;
            if (currentBatch.size() > 0)
            {
                newData = currentBatch.toArray(new EventBean[0]);
            }
            if ((lastBatch != null) && (lastBatch.size() > 0))
            {
                oldData = lastBatch.toArray(new EventBean[0]);
            }

            // Post new data (current batch) and old data (prior batch)
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

        // Only if there have been any events in this or the last interval do we schedule a callback,
        // such as to not waste resources when no events arrive.
        if ((currentBatch.size() > 0) || ((lastBatch != null) && (lastBatch.size() > 0)))
        {
            scheduleCallback();
            isCallbackScheduled = true;
        }

        lastBatch = currentBatch;
        currentBatch = new LinkedList<EventBean>();
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
        long current = viewServiceContext.getSchedulingService().getTime();
        long afterMSec = computeWaitMSec(current, this.currentReferencePoint, this.msecIntervalSize);

        if (log.isDebugEnabled())
        {
            log.debug(".scheduleCallback Scheduled new callback for " +
                    " afterMsec=" + afterMSec +
                    " now=" + current +
                    " currentReferencePoint=" + currentReferencePoint +
                    " initialReferencePoint=" + initialReferencePoint +
                    " msecIntervalSize=" + msecIntervalSize);
        }

        ScheduleCallback callback = new ScheduleCallback() {
            public void scheduledTrigger()
            {
                TimeBatchView.this.sendBatch();
            }
        };
        viewServiceContext.getSchedulingService().add(afterMSec, callback, scheduleSlot);
    }

    /**
     * Given a current time and a reference time and an interval size, compute the amount of
     * milliseconds till the next interval.
     * @param current is the current time
     * @param reference is the reference point
     * @param interval is the interval size
     * @return milliseconds after current time that marks the end of the current interval
     */
    protected static long computeWaitMSec(long current, long reference, long interval)
    {
        // Example:  current c=2300, reference r=1000, interval i=500, solution s=200
        //
        // int n = ((2300 - 1000) / 500) = 2
        // r + (n + 1) * i - c = 200
        //
        // Negative example:  current c=2300, reference r=4200, interval i=500, solution s=400
        // int n = ((2300 - 4200) / 500) = -3
        // r + (n + 1) * i - c = 4200 - 3*500 - 2300 = 400
        //
        long n = (long) ( (current - reference) / (interval * 1f));
        if (reference > current)        // References in the future need to deduct one window
        {
            n = n - 1;
        }
        long solution = reference + (n + 1) * interval - current;

        if (solution == 0)
        {
            return interval;
        }
        return solution;
    }

    private static final Log log = LogFactory.getLog(TimeBatchView.class);
}
