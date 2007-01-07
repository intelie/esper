package net.esper.view.window;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;
import java.text.SimpleDateFormat;

import net.esper.view.ViewSupport;
import net.esper.view.ContextAwareView;
import net.esper.view.ViewServiceContext;
import net.esper.event.EventType;
import net.esper.event.EventBean;
import net.esper.schedule.ScheduleHandleCallback;
import net.esper.schedule.ScheduleSlot;
import net.esper.collection.TimeWindow;
import net.esper.collection.ViewUpdatedCollection;
import net.esper.client.EPException;
import net.esper.core.EPStatementHandleCallback;

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
    private TimeWindow timeWindow = new TimeWindow();
    private ViewUpdatedCollection viewUpdatedCollection;

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
     * @param millisecondsBeforeExpiry is the number of milliseconds before events gets pushed
     * out of the timeWindow as oldData in the update method.
     * @param viewUpdatedCollection is a collection the view must update when receiving events
     */
    public TimeWindowView(long millisecondsBeforeExpiry, ViewUpdatedCollection viewUpdatedCollection)
    {
        this.millisecondsBeforeExpiry = millisecondsBeforeExpiry;
        this.viewUpdatedCollection = viewUpdatedCollection;
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

    /**
     * Returns the (optional) collection handling random access to window contents for prior or previous events.
     * @return buffer for events
     */
    public ViewUpdatedCollection getViewUpdatedCollection()
    {
        return viewUpdatedCollection;
    }

    /**
     * Sets the buffer for keeping a reference to prior or previous events.
     * @param viewUpdatedCollection buffer
     */
    public void setViewUpdatedCollection(IStreamRandomAccess viewUpdatedCollection)
    {
        this.viewUpdatedCollection = viewUpdatedCollection;
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
            if ((expired != null) && (!expired.isEmpty()))
            {
                EventBean[] oldEvents = expired.toArray(new EventBean[0]);
                if (viewUpdatedCollection != null)
                {
                    viewUpdatedCollection.update(null, oldEvents);
                }
                updateChildren(null, oldEvents);
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
        ScheduleHandleCallback callback = new ScheduleHandleCallback() {
            public void scheduledTrigger()
            {
                TimeWindowView.this.expire();
            }
        };
        EPStatementHandleCallback handle = new EPStatementHandleCallback(viewServiceContext.getEpStatementHandle(), callback);
        viewServiceContext.getSchedulingService().add(msecAfterCurrentTime, handle, scheduleSlot);
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

    /**
     * Returns true if the window is empty, or false if not empty.
     * @return true if empty
     */
    public boolean isEmpty()
    {
        return timeWindow.isEmpty();
    }    

    private static final Log log = LogFactory.getLog(TimeWindowView.class);
}
