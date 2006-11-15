package net.esper.view.std;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import net.esper.event.EventBean;
import net.esper.event.EventPropertyGetter;
import net.esper.event.EventType;
import net.esper.view.PropertyCheckHelper;
import net.esper.view.ViewSupport;
import net.esper.view.Viewable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This view includes only the most recent among events having the same value for the specified field.
 * The view accepts the field name as parameter from which the unique values are obtained.
 * For example, a trade's symbol could be used as a unique value.
 * In this example, the first trade for symbol IBM would be posted as new data to child views.
 * When the second trade for symbol IBM arrives the second trade is posted as new data to child views,
 * and the first trade is posted as old data.
 * Should more than one trades for symbol IBM arrive at the same time (like when batched)
 * then the child view will get all new events in newData and all new events in oldData minus the most recent event.
 * When the current new event arrives as old data, the the current unique event gets thrown away and
 * posted as old data to child views.
 * Iteration through the views data shows only the most recent events received for the unique value in the order
 * that events arrived in.
 * The type of the field returning the unique value can be any type but should override equals and hashCode()
 * as the type plays the role of a key in a map storing unique values.
 */
public final class UniqueByPropertyView extends ViewSupport
{
    private String uniqueFieldName;
    private EventPropertyGetter uniqueFieldGetter;
    private final Map<Object, EventBean> mostRecentEvents = new LinkedHashMap<Object, EventBean>();;

    /**
     * Default constructor - required by all views to adhere to the Java bean specification.
     */
    public UniqueByPropertyView()
    {
    }

    /**
     * Constructor.
     * @param uniqueFieldName is the field from which to pull the unique value
     */
    public UniqueByPropertyView(String uniqueFieldName)
    {
        this.uniqueFieldName = uniqueFieldName;
    }

    public void setParent(Viewable parent)
    {
        super.setParent(parent);
        if (parent != null)
        {
            uniqueFieldGetter = parent.getEventType().getGetter(uniqueFieldName);
        }
    }

    /**
     * Returns the name of the field supplying the unique value to keep the most recent record for.
     * @return field name for unique value
     */
    public final String getUniqueFieldName()
    {
        return uniqueFieldName;
    }

    /**
     * Sets the name of the field supplying the unique value to keep the most recent record for.
     * @param uniqueFieldName field name for unique value
     */
    public final void setUniqueFieldName(String uniqueFieldName)
    {
        this.uniqueFieldName = uniqueFieldName;
    }

    public final String attachesTo(Viewable parentView)
    {
        // Attaches to just about anything as long as the field exists
        return PropertyCheckHelper.exists(parentView.getEventType(), uniqueFieldName);
    }

    public final EventType getEventType()
    {
        // The schema is the parent view's schema
        return parent.getEventType();
    }

    public final void update(EventBean[] newData, EventBean[] oldData)
    {
        if (log.isDebugEnabled())
        {
            log.debug(".update Updating view");
            dumpUpdateParams("UniqueByPropertyView", newData, oldData);
        }

        LinkedList<EventBean> postOldData = null;

        if (this.hasViews())
        {
            postOldData = new LinkedList<EventBean>();
        }

        if (newData != null)
        {
            for (int i = 0; i < newData.length; i++)
            {
                // Obtain unique value
                Object uniqueValue = uniqueFieldGetter.get(newData[i]);

                // If there are no child views, just update the own collection
                if (!this.hasViews())
                {
                    mostRecentEvents.put(uniqueValue, newData[i]);
                    continue;
                }

                // Post the last value as old data
                EventBean lastValue = mostRecentEvents.get(uniqueValue);
                if (lastValue != null)
                {
                    postOldData.add(lastValue);
                }

                // Override with recent event
                mostRecentEvents.put(uniqueValue, newData[i]);
            }
        }

        if (oldData != null)
        {
            for (int i = 0; i < oldData.length; i++)
            {
                // Obtain unique value
                Object uniqueValue = uniqueFieldGetter.get(oldData[i]);

                // If the old event is the current unique event, remove and post as old data
                EventBean lastValue = mostRecentEvents.get(uniqueValue);
                if (lastValue != oldData[i])
                {
                    continue;
                }

                postOldData.add(lastValue);
                mostRecentEvents.remove(uniqueValue);
            }
        }


        // If there are child views, fire update method
        if (this.hasViews())
        {
            if (postOldData.size() == 0)
            {
                updateChildren(newData, null);
            }
            else
            {
                updateChildren(newData, postOldData.toArray(new EventBean[0]));
            }
        }
    }

    public final Iterator<EventBean> iterator()
    {
        return mostRecentEvents.values().iterator();
    }

    public final String toString()
    {
        return this.getClass().getName() + " uniqueFieldName=" + uniqueFieldName;
    }

    private static final Log log = LogFactory.getLog(UniqueByPropertyView.class);
}
