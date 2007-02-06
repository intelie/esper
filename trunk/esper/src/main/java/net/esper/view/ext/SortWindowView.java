package net.esper.view.ext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

import net.esper.util.MultiKeyComparator;
import net.esper.view.*;
import net.esper.view.window.DataWindowView;
import net.esper.collection.MultiKeyUntyped;
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
public final class SortWindowView extends ViewSupport implements DataWindowView, CloneableView
{
    private final SortWindowViewFactory sortWindowViewFactory;
    private final String[] sortFieldNames;
    private final Boolean[] isDescendingValues;
    private final int sortWindowSize;
    private final IStreamSortedRandomAccess optionalSortedRandomAccess;

    private EventPropertyGetter[] sortFieldGetters;
    private TreeMap<MultiKeyUntyped, LinkedList<EventBean>> sortedEvents;
    private int eventCount;

    /**
     * Ctor.
     * @param sortFieldNames is the event property names to sort
     * @param descendingValues indicates whether to sort ascending or descending for each field
     * @param sortWindowSize is the window size
     * @param optionalSortedRandomAccess is the friend class handling the random access, if required by
     * expressions
     * @param sortWindowViewFactory for copying this view in a group-by
     */
    public SortWindowView(SortWindowViewFactory sortWindowViewFactory,
                          String[] sortFieldNames,
                          Boolean[] descendingValues,
                          int sortWindowSize,
                          IStreamSortedRandomAccess optionalSortedRandomAccess)
    {
        this.sortWindowViewFactory = sortWindowViewFactory;
        this.sortFieldNames = sortFieldNames;
        this.isDescendingValues = descendingValues;
        this.sortWindowSize = sortWindowSize;
        this.optionalSortedRandomAccess = optionalSortedRandomAccess;

        Comparator<MultiKeyUntyped> comparator = new MultiKeyComparator(isDescendingValues);
        sortedEvents = new TreeMap<MultiKeyUntyped, LinkedList<EventBean>>(comparator);
    }
    
    public void setParent(Viewable parent)
    {
        super.setParent(parent);
        if (parent != null)
        {
        	int count = 0;
        	sortFieldGetters = new EventPropertyGetter[sortFieldNames.length];
        	for(String name : sortFieldNames)
        	{
        		sortFieldGetters[count++] = parent.getEventType().getGetter(name);
        	}
        }
    }

    /**
     * Returns the field names supplying the values to sort by.
     * @return field names to sort by
     */
    protected final String[] getSortFieldNames()
    {
        return sortFieldNames;
    }
    
    /**
     * Returns the flags indicating whether to sort in descending order on each property.
     * @return the isDescending value for each sort property
     */
    protected final Boolean[] getIsDescendingValues()
    {
    	return isDescendingValues;
    }

    /**
     * Returns the number of elements kept by the sort window.
     * @return size of window
     */
    protected final int getSortWindowSize()
    {
        return sortWindowSize;
    }

    /**
     * Returns the friend handling the random access, cal be null if not required.
     * @return random accessor to sort window contents
     */
    protected IStreamSortedRandomAccess getOptionalSortedRandomAccess()
    {
        return optionalSortedRandomAccess;
    }

    public View cloneView(ViewServiceContext viewServiceContext)
    {
        return sortWindowViewFactory.makeView(viewServiceContext);
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
                MultiKeyUntyped sortValues = getSortValues(oldData[i]);
                boolean result = remove(sortValues, oldData[i]);
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
                MultiKeyUntyped sortValues = getSortValues(newData[i]);
                add(sortValues, newData[i]);
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
                MultiKeyUntyped lastKey = sortedEvents.lastKey();
                LinkedList<EventBean> events = sortedEvents.get(lastKey);
                EventBean event = events.removeLast();
                eventCount--;

                // Clear out entry if not used
                if (events.isEmpty())
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
        if (optionalSortedRandomAccess != null)
        {
            optionalSortedRandomAccess.refresh(sortedEvents, eventCount, sortWindowSize);
        }
        if (this.hasViews())
        {
            EventBean[] expiredArr = null;
            if (!removedEvents.isEmpty())
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
                " sortFieldName=" + sortFieldNames +
                " isDescending=" + isDescendingValues +
                " sortWindowSize=" + sortWindowSize;
    }

    private void add(MultiKeyUntyped key, EventBean bean)
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

    private boolean remove(MultiKeyUntyped key, EventBean bean)
    {
        LinkedList<EventBean> listOfBeans = sortedEvents.get(key);
        if (listOfBeans == null)
        {
            return false;
        }

        boolean result = listOfBeans.remove(bean);
        if (listOfBeans.isEmpty())
        {
            sortedEvents.remove(key);
        }
        return result;
    }

    private MultiKeyUntyped getSortValues(EventBean event)
    {
    	Object[] result = new Object[sortFieldGetters.length];
    	int count = 0;
    	for(EventPropertyGetter getter : sortFieldGetters)
    	{
    		result[count++] = getter.get(event);
    	}
    	return new MultiKeyUntyped(result);
    }

    /**
     * True to indicate the sort window is empty, or false if not empty.
     * @return true if empty sort window
     */
    public boolean isEmpty()
    {
        return sortedEvents.isEmpty();
    }

    private static final Log log = LogFactory.getLog(SortWindowView.class);
}
