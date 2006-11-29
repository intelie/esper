package net.esper.view.window;

import java.util.*;

import net.esper.view.ViewSupport;
import net.esper.view.Viewable;
import net.esper.event.EventPropertyGetter;
import net.esper.event.EventType;
import net.esper.event.EventBean;
import net.esper.view.PropertyCheckHelper;
import net.esper.collection.TimeWindow;
import net.esper.eql.parse.TimePeriodParameter;

/**
 * View for a moving window extending the specified amount of time into the past, driven entirely by external timing
 * supplied within long-type timestamp values in a field of the event beans that the view receives.
 *
 * The view is completely driven by timestamp values that are supplied by the events it receives,
 * and does not use the system time.
 * It requires a field name as parameter for a field that returns ascending long-type timestamp values.
 * It also requires a long-type parameter setting the time length in milliseconds of the time window.
 * Events are expected to provide long-type timestamp values in natural order. The view does
 * itself not use the current system time for keeping track of the time window, but just the
 * timestamp values supplied by the events sent in.
 *
 * The arrival of new events with a newer timestamp then past events causes the window to be re-evaluated and the oldest
 * events pushed out of the window. Ie. Assume event X1 with timestamp T1 is in the window.
 * When event Xn with timestamp Tn arrives, and the window time length in milliseconds is t, then if
 * ((Tn - T1) > t == true) then event X1 is pushed as oldData out of the window. It is assumed that
 * events are sent in in their natural order and the timestamp values are ascending.
 */
public final class ExternallyTimedWindowView extends ViewSupport implements DataWindowView
{
    private String timestampFieldName;
    private long millisecondsBeforeExpiry;
    private EventPropertyGetter timestampFieldGetter;

    private final TimeWindow timeWindow = new TimeWindow();

    /**
     * Default constructor - required by all views to adhere to the Java bean specification.
     */
    public ExternallyTimedWindowView()
    {
    }

    /**
     * Constructor.
     * @param timestampFieldName is the field name containing a long timestamp value
     * @param secondsBeforeExpiry is the number of seconds
     */
    public ExternallyTimedWindowView(String timestampFieldName, long secondsBeforeExpiry)
    {
        this(timestampFieldName, (double)secondsBeforeExpiry);
    }

    /**
     * Constructor.
     * @param timestampFieldName is the field name containing a long timestamp value
     * that should be in ascending order for the natural order of events and is intended to reflect
     * System.currentTimeInMillis but does not necessarily have to.
     * @param secondsBeforeExpiry is the number of seconds before events gets pushed
     * out of the window as oldData in the update method. The view compares
     * each events timestamp against the newest event timestamp and those with a delta
     * greater then secondsBeforeExpiry are pushed out of the window.
     */
    public ExternallyTimedWindowView(String timestampFieldName, double secondsBeforeExpiry)
    {
        this.timestampFieldName = timestampFieldName;
        this.millisecondsBeforeExpiry = Math.round(secondsBeforeExpiry * 1000d);

        if (millisecondsBeforeExpiry <= 0)
        {
            throw new IllegalArgumentException("Externally timed window does not allow a zero or negative parameter for the millisecond window length");
        }
    }

    /**
     * Constructor - implemented via (String, long) constructor.
     * @param timestampFieldName is the field name containing a long timestamp value
     * @param timePeriodParameter is the number of milliseconds before events gets pushed
     */
    public ExternallyTimedWindowView(String timestampFieldName, TimePeriodParameter timePeriodParameter)
    {
        this(timestampFieldName, timePeriodParameter.getNumSeconds());
    }

    /**
     * Returns the field name to get timestamp values from.
     * @return field name for timestamp values
     */
    public final String getTimestampFieldName()
    {
        return timestampFieldName;
    }

    /**
     * Sets the field name to get timestamp values from.
     * @param timestampFieldName is the field name for timestamp values
     */
    public final void setTimestampFieldName(String timestampFieldName)
    {
        this.timestampFieldName = timestampFieldName;
    }

    public void setParent(Viewable parent)
    {
        super.setParent(parent);
        if (parent != null)
        {
            timestampFieldGetter = parent.getEventType().getGetter(timestampFieldName);
        }
    }

    /**
     * Retuns the window size in milliseconds.
     * @return number of milliseconds before events expire from the window
     */
    public final long getMillisecondsBeforeExpiry()
    {
        return millisecondsBeforeExpiry;
    }

    /**
     * Sets the window size in milliseconds.
     * @param millisecondsBeforeExpiry number of milliseconds before events expire from the window
     */
    public final void setMillisecondsBeforeExpiry(long millisecondsBeforeExpiry)
    {
        this.millisecondsBeforeExpiry = millisecondsBeforeExpiry;
    }

    public final String attachesTo(Viewable parentView)
    {
        return PropertyCheckHelper.checkLong(parentView.getEventType(), timestampFieldName);
    }

    public final EventType getEventType()
    {
        // The schema is the parent view's schema
        return parent.getEventType();
    }

    public final void update(EventBean[] newData, EventBean[] oldData)
    {
        long timestamp = -1;

        // add data points to the window
        // we don't care about removed data from a prior view
        if (newData != null)
        {
            for (int i = 0; i < newData.length; i++)
            {
                timestamp = getLongValue(newData[i]);
                timeWindow.add(timestamp, newData[i]);
            }
        }

        // Remove from the window any events that have an older timestamp then the last event's timestamp
        List<EventBean> expired = null;
        if (timestamp != -1)
        {
            expired = timeWindow.expireEvents(timestamp - millisecondsBeforeExpiry);
        }

        // If there are child views, fire update method
        if (this.hasViews())
        {
            if ((expired != null) && (expired.size() > 0))
            {
                updateChildren(newData, expired.toArray(new EventBean[0]));
            }
            else
            {
                updateChildren(newData, null);
            }
        }
    }

    public final Iterator<EventBean> iterator()
    {
        return timeWindow.iterator();
    }

    public final String toString()
    {
        return this.getClass().getName() +
                " timestampFieldName=" + timestampFieldName +
                " millisecondsBeforeExpiry=" + millisecondsBeforeExpiry;
    }

    private long getLongValue(EventBean obj)
    {
        Number num = (Number) timestampFieldGetter.get(obj);
        return num.longValue();
    }
}
