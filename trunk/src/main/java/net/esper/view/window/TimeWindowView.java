package net.esper.view.window;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;
import java.text.SimpleDateFormat;

import net.esper.view.ViewSupport;
import net.esper.view.Viewable;
import net.esper.view.ContextAwareView;
import net.esper.view.ViewServiceContext;
import net.esper.event.EventType;
import net.esper.event.EventBean;
import net.esper.schedule.ScheduleCallback;
import net.esper.schedule.ScheduleSlot;
import net.esper.collection.TimeWindow;
import net.esper.client.EPException;

/**
 * This view is a moving timeWindow extending the specified amount of milliseconds into the past.
 * The view bases the timeWindow on the time obtained from the scheduling service.
 * All incoming events receive a timestamp and are placed in a sorted map by timestamp.
 * The view does not care about old data published by the parent view to this view.
 *
 * Events leave or expire from the time timeWindow by means of a scheduled callback registered with the
 * scheduling service. Thus child views receive updates containing old data only asynchronously
 * as the system-time-based timeWindow moves on. However child views receive updates containing new data
 * as soon as the new data arrives.
 */
public final class TimeWindowView extends ViewSupport implements ContextAwareView, DataWindowView
{
    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    private long millisecondsBeforeExpiry;

    private final TimeWindow timeWindow = new TimeWindow();
    private ViewServiceContext viewServiceContext;
    private ScheduleSlot scheduleSlot;

    /**
     * Default constructor - required by all views to adhere to the Java bean specification.
     */
    public TimeWindowView()
    {
    }

    /**
     * Constructor.
     * @param secondsBeforeExpiry is the number of seconds before events gets pushed
     * out of the timeWindow as oldData in the update method.
     */
    public TimeWindowView(int secondsBeforeExpiry)
    {
        if (secondsBeforeExpiry < 1)
        {
            throw new IllegalArgumentException("Time window view requires a millisecond size of at least 100 msec");
        }

        this.millisecondsBeforeExpiry = 1000 * secondsBeforeExpiry;
    }

    /**
     * Constructor.
     * @param secondsBeforeExpiry is the number of seconds before events gets pushed
     * out of the timeWindow as oldData in the update method.
     */
    public TimeWindowView(double secondsBeforeExpiry)
    {
        if (secondsBeforeExpiry <= 0.1)
        {
            throw new IllegalArgumentException("Time window view requires a millisecond size of at least 100 msec");
        }

        this.millisecondsBeforeExpiry = Math.round(1000d * secondsBeforeExpiry);
    }

    /**
     * Returns the size of the time window in millisecond.
     * @return size of window
     */
    public final long getMillisecondsBeforeExpiry()
    {
        return millisecondsBeforeExpiry;
    }

    /**
     * Sets the size of the time window in millisecond.
     * @param millisecondsBeforeExpiry size of window
     */
    public final void setMillisecondsBeforeExpiry(long millisecondsBeforeExpiry)
    {
        this.millisecondsBeforeExpiry = millisecondsBeforeExpiry;
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

    public final void update(EventBean[] newData, EventBean[] oldData)
    {
        if (viewServiceContext == null)
        {
            String message = "View context has not been supplied, cannot schedule callback";
            log.fatal(".update " + message);
            throw new EPException(message);
        }

        long timestamp = viewServiceContext.getSchedulingService().getTime();

        // we don't care about removed data from a prior view
        if ((newData == null) || (newData.length == 0))
        {
            return;
        }

        // If we have an empty window about to be filled for the first time, schedule a callback
        // for now plus millisecondsBeforeExpiry
        if (timeWindow.isEmpty())
        {
            scheduleCallback(millisecondsBeforeExpiry);
        }

        // add data points to the timeWindow
        for (int i = 0; i < newData.length; i++)
        {
            timeWindow.add(timestamp, newData[i]);
        }

        // update child views
        if (this.hasViews())
        {
            updateChildren(newData, null);
        }
    }

    /**
     * This method removes (expires) objects from the window and schedules a new callback for the
     * time when the next oldest message would expire from the window.
     */
    protected final void expire()
    {
        long expireBeforeTimestamp = viewServiceContext.getSchedulingService().getTime() - millisecondsBeforeExpiry + 1;

        if (log.isDebugEnabled())
        {
            log.debug(".expire Expiring messages before " +
                    "msec=" + expireBeforeTimestamp +
                    "  date=" + dateFormat.format(expireBeforeTimestamp));
        }

        // Remove from the timeWindow any events that have an older or timestamp then the given timestamp
        // The window extends from X to (X - millisecondsBeforeExpiry + 1)
        List<EventBean> expired = timeWindow.expireEvents(expireBeforeTimestamp);

        // If there are child views, fire update method
        if (this.hasViews())
        {
            if ((expired != null) && (expired.size() > 0))
            {
                updateChildren(null, expired.toArray(new EventBean[0]));
            }
        }

        if (log.isDebugEnabled())
        {
            log.debug(".expire Expired messages....size=" + expired.size());
            for (Object object : expired)
            {
                log.debug(".expire object=" + object);
            }
        }

        // If we still have events in the window, schedule new callback
        if (timeWindow.isEmpty())
        {
            return;
        }
        Long oldestTimestamp = timeWindow.getOldestTimestamp();
        long currentTimestamp = viewServiceContext.getSchedulingService().getTime();
        long scheduleMillisec = millisecondsBeforeExpiry - (currentTimestamp - oldestTimestamp);
        scheduleCallback(scheduleMillisec);

        if (log.isDebugEnabled())
        {
            log.debug(".expire Scheduled new callback for now plus msec=" + scheduleMillisec);
        }
    }

    private void scheduleCallback(long msecAfterCurrentTime)
    {
        ScheduleCallback callback = new ScheduleCallback() {
            public void scheduledTrigger()
            {
                TimeWindowView.this.expire();
            }
        };
        viewServiceContext.getSchedulingService().add(msecAfterCurrentTime, callback, scheduleSlot);
    }

    public final Iterator<EventBean> iterator()
    {
        return timeWindow.iterator();
    }

    public final String toString()
    {
        return this.getClass().getName() +
                " millisecondsBeforeExpiry=" + millisecondsBeforeExpiry;
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

    private static final Log log = LogFactory.getLog(TimeWindowView.class);
}
