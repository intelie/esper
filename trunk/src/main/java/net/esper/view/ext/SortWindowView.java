package net.esper.view.ext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

import net.esper.view.ViewSupport;
import net.esper.view.Viewable;
import net.esper.view.window.DataWindowView;
import net.esper.view.PropertyCheckHelper;
import net.esper.event.EventPropertyGetter;
import net.esper.event.EventType;
import net.esper.event.EventBean;

/**
 * Window sorting by values in the specified field extending a specified number of elements
 * from the lowest value up or the highest value down.
 * The view accepts 3 parameters. The first parameter is the field name to get the values to sort for,
 * the second parameter defines whether to sort ascending or descending, the third parameter
 * is the number of elements to keep in the sorted list.
 *
 * The type of the field to be sorted in the event must implement the Comparable interface.
 *
 * The natural order in which events arrived is used as the second sorting criteria. Thus should events arrive
 * with equal sort values the oldest event leaves the sort window first.
 *
 * Old values removed from a prior view are removed from the sort view.
 */
public final class SortWindowView extends ViewSupport implements DataWindowView
{
    private String sortFieldName;
    private EventPropertyGetter sortFieldGetter;
    private boolean isDescending;
    private int sortWindowSize = 0;

    private TreeMap<Object, LinkedList<EventBean>> sortedEvents;
    private int eventCount;

    /**
     * Default constructor - required by all views to adhere to the Java bean specification.
     */
    public SortWindowView()
    {
    }

    /**
     * Constructor.
     * @param sortFieldName is the name of the field in the event to get the values to sort
     * @param isDescending true if the sort should be descending, false if ascending
     * @param size is the specified number of elements to keep in the sort
     */
    public SortWindowView(String sortFieldName, boolean isDescending, int size)
    {
        if ((size < 1) || (size > Integer.MAX_VALUE))
        {
            throw new IllegalArgumentException("Illegal argument for sortWindowSize of length window");
        }

        this.sortFieldName = sortFieldName;
        this.isDescending = isDescending;
        this.sortWindowSize = size;

        if (isDescending)
        {
            SortWindowDescending comparator = new SortWindowDescending();
            sortedEvents = new TreeMap<Object, LinkedList<EventBean>>(comparator);
        }
        else
        {
            sortedEvents = new TreeMap<Object, LinkedList<EventBean>>();
        }
    }

    public void setParent(Viewable parent)
    {
        super.setParent(parent);
        if (parent != null)
        {
            sortFieldGetter = parent.getEventType().getGetter(sortFieldName);
        }
    }

    /**
     * Returns the field name supplying the values to sort by.
     * @return field name to sort by
     */
    public final String getSortFieldName()
    {
        return sortFieldName;
    }

    /**
     * Sets the field name supplying the values to sort by.
     * @param sortFieldName field name to sort by
     */
    public final void setSortFieldName(String sortFieldName)
    {
        this.sortFieldName = sortFieldName;
    }

    /**
     * Returns an indication of sort order.
     * @return true for descending sorts, false for ascending sort
     */
    public final boolean isDescending()
    {
        return isDescending;
    }

    /**
     * Set the sort order for the sort window.
     * @param descending is true to set the descending and false for ascending sorting.
     */
    public final void setDescending(boolean descending)
    {
        isDescending = descending;

        if (isDescending)
        {
            SortWindowDescending comparator = new SortWindowDescending();
            sortedEvents = new TreeMap<Object, LinkedList<EventBean>>(comparator);
        }
        else
        {
            sortedEvents = new TreeMap<Object, LinkedList<EventBean>>();
        }
    }

    /**
     * Returns the number of elements kept by the sort window.
     * @return size of window
     */
    public final int getSortWindowSize()
    {
        return sortWindowSize;
    }

    /**
     * Sets the number of elements kept by the sort window.
     * @param sortWindowSize size of window
     */
    public final void setSortWindowSize(int sortWindowSize)
    {
        this.sortWindowSize = sortWindowSize;
    }

    public final String attachesTo(Viewable parentView)
    {
        // Attaches to parent views where the field exists and implements Comparable
        return PropertyCheckHelper.exists(parentView.getEventType(), sortFieldName);
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
            dumpUpdateParams("SortWindowView", newData, oldData);
        }

        List<Object> removedEvents = new LinkedList<Object>();

        // Remove old data
        if (oldData != null)
        {
            for (int i = 0; i < oldData.length; i++)
            {
                Object sortValue = sortFieldGetter.get(oldData[i]);
                boolean result = remove(sortValue, oldData[i]);
                if (result)
                {
                    eventCount--;
                    removedEvents.add(oldData[i]);
                }
            }
        }

        // Add new data
        if (newData != null)
        {
            for (int i = 0; i < newData.length; i++)
            {
                Object sortValue = sortFieldGetter.get(newData[i]);
                add(sortValue, newData[i]);
                eventCount++;
            }
        }

        // Remove data that sorts to the bottom of the window
        if (eventCount > sortWindowSize)
        {
            int removeCount = eventCount - sortWindowSize;
            for (int i = 0; i < removeCount; i++)
            {
                // Remove the last element of the last key - sort order is key and then natural order of arrival
                Object lastKey = sortedEvents.lastKey();
                LinkedList<EventBean> events = sortedEvents.get(lastKey);
                EventBean event = events.removeLast();
                eventCount--;

                // Clear out entry if not used
                if (events.size() == 0)
                {
                    sortedEvents.remove(lastKey);
                }

                removedEvents.add(event);

                if (log.isDebugEnabled())
                {
                    log.debug(".update Pushing out event event=" + event);
                }
            }
        }

        // If there are child views, fire update method
        if (this.hasViews())
        {
            EventBean[] expiredArr = null;
            if (removedEvents.size() > 0)
            {
                expiredArr = removedEvents.toArray(new EventBean[0]);
            }

            updateChildren(newData, expiredArr);
        }
    }

    public final Iterator<EventBean> iterator()
    {
        return new SortWindowIterator(sortedEvents);
    }

    public final String toString()
    {
        return this.getClass().getName() +
                " sortFieldName=" + sortFieldName +
                " isDescending=" + isDescending +
                " sortWindowSize=" + sortWindowSize;
    }

    private void add(Object key, EventBean bean)
    {
        LinkedList<EventBean> listOfBeans = sortedEvents.get(key);
        if (listOfBeans != null)
        {
            listOfBeans.addFirst(bean); // Add to the front of the list as the second sort critertial is ascending arrival order
            return;
        }

        listOfBeans = new LinkedList<EventBean>();
        listOfBeans.add(bean);
        sortedEvents.put(key, listOfBeans);
    }

    private boolean remove(Object key, EventBean bean)
    {
        LinkedList<EventBean> listOfBeans = sortedEvents.get(key);
        if (listOfBeans == null)
        {
            return false;
        }

        boolean result = listOfBeans.remove(bean);
        if (listOfBeans.size() == 0)
        {
            sortedEvents.remove(key);
        }
        return result;
    }

    private static final Log log = LogFactory.getLog(SortWindowView.class);
}
