package net.esper.view.window;

import net.esper.client.EPException;
import net.esper.collection.TimeWindow;
import net.esper.collection.ViewUpdatedCollection;
import net.esper.core.EPStatementHandleCallback;
import net.esper.core.ExtensionServicesContext;
import net.esper.core.StatementContext;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.schedule.ScheduleHandleCallback;
import net.esper.schedule.ScheduleSlot;
import net.esper.view.CloneableView;
import net.esper.view.View;
import net.esper.view.ViewSupport;
import net.esper.util.ExecutionPathDebugLog;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Iterator;
import java.util.List;

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
public final class TimeWindowView extends ViewSupport implements CloneableView, DataWindowView
{
    private final TimeWindowViewFactory timeWindowViewFactory;
    private final long millisecondsBeforeExpiry;
    private final TimeWindow timeWindow = new TimeWindow();
    private final ViewUpdatedCollection viewUpdatedCollection;

    private final StatementContext statementContext;
    private final ScheduleSlot scheduleSlot;

    /**
     * Constructor.
     * @param millisecondsBeforeExpiry is the number of milliseconds before events gets pushed
     * out of the timeWindow as oldData in the update method.
     * @param viewUpdatedCollection is a collection the view must update when receiving events
     * @param statementContext is required view services
     * @param timeWindowViewFactory for copying the view in a group-by
     */
    public TimeWindowView(StatementContext statementContext, TimeWindowViewFactory timeWindowViewFactory, long millisecondsBeforeExpiry, ViewUpdatedCollection viewUpdatedCollection)
    {
        this.statementContext = statementContext;
        this.timeWindowViewFactory = timeWindowViewFactory;
        this.millisecondsBeforeExpiry = millisecondsBeforeExpiry;
        this.viewUpdatedCollection = viewUpdatedCollection;
        this.scheduleSlot = statementContext.getScheduleBucket().allocateSlot();
    }

    public View cloneView(StatementContext statementContext)
    {
        return timeWindowViewFactory.makeView(statementContext);
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
     * Returns the (optional) collection handling random access to window contents for prior or previous events.
     * @return buffer for events
     */
    public ViewUpdatedCollection getViewUpdatedCollection()
    {
        return viewUpdatedCollection;
    }

    public final EventType getEventType()
    {
        return parent.getEventType();
    }

    public final void update(EventBean[] newData, EventBean[] oldData)
    {
        if (statementContext == null)
        {
            String message = "View context has not been supplied, cannot schedule callback";
            log.fatal(".update " + message);
            throw new EPException(message);
        }

        long timestamp = statementContext.getSchedulingService().getTime();

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
        long expireBeforeTimestamp = statementContext.getSchedulingService().getTime() - millisecondsBeforeExpiry + 1;

        if ((ExecutionPathDebugLog.isEnabled()) && (log.isDebugEnabled()))
        {
            log.debug(".expire Expiring messages before " +
                    "msec=" + expireBeforeTimestamp +
                    "  date=" + statementContext.getSchedulingService().getTime());
        }

        // Remove from the timeWindow any events that have an older or timestamp then the given timestamp
        // The window extends from X to (X - millisecondsBeforeExpiry + 1)
        List<EventBean> expired = timeWindow.expireEvents(expireBeforeTimestamp);

        // If there are child views, fireStatementStopped update method
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

        if ((ExecutionPathDebugLog.isEnabled()) && (log.isDebugEnabled()))
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
        long currentTimestamp = statementContext.getSchedulingService().getTime();
        long scheduleMillisec = millisecondsBeforeExpiry - (currentTimestamp - oldestTimestamp);
        scheduleCallback(scheduleMillisec);

        if ((ExecutionPathDebugLog.isEnabled()) && (log.isDebugEnabled()))
        {
            log.debug(".expire Scheduled new callback for now plus msec=" + scheduleMillisec);
        }
    }

    private void scheduleCallback(long msecAfterCurrentTime)
    {
        ScheduleHandleCallback callback = new ScheduleHandleCallback() {
            public void scheduledTrigger(ExtensionServicesContext extensionServicesContext)
            {
                TimeWindowView.this.expire();
            }
        };
        EPStatementHandleCallback handle = new EPStatementHandleCallback(statementContext.getEpStatementHandle(), callback);
        statementContext.getSchedulingService().add(msecAfterCurrentTime, handle, scheduleSlot);
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
