package net.esper.view.ext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

import net.esper.util.MultiKeyComparator;
import net.esper.view.ViewSupport;
import net.esper.view.Viewable;
import net.esper.view.window.DataWindowView;
import net.esper.view.PropertyCheckHelper;
import net.esper.collection.MultiKey;
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
    private String[] sortFieldNames;
    private EventPropertyGetter[] sortFieldGetters;
    private Boolean[] isDescendingValues;
    private int sortWindowSize = 0;

    private TreeMap<MultiKey, LinkedList<EventBean>> sortedEvents;
    private int eventCount;

    /**
     * Default constructor - required by all views to adhere to the Java bean specification.
     */
    public SortWindowView()
    {
    }

    /**
     * Constructor.
     * @param propertiesAndDirections - an array of the form [String, Boolean, ...],
     * 		  where each String represents a property name, and each Boolean indicates 
     * 	      whether to sort in descending order on that property
     * @param size is the specified number of elements to keep in the sort
     */
    public SortWindowView(Object[] propertiesAndDirections, int size)
    {
    	if(propertiesAndDirections == null || propertiesAndDirections.length < 2)
    	{
    		throw new IllegalArgumentException("The sort view must sort on at least one property");
    	}

    	if ((size < 1) || (size > Integer.MAX_VALUE))
    	{
    		throw new IllegalArgumentException("Illegal argument for sortWindowSize of length window");
    	}

    	setNamesAndIsDescendingValues(propertiesAndDirections);
    	this.sortWindowSize = size;

    	Comparator<MultiKey> comparator = new MultiKeyComparator(isDescendingValues);
    	sortedEvents = new TreeMap<MultiKey, LinkedList<EventBean>>(comparator);
    }
    
    /**
     * Ctor.
     * @param propertyName - the property to sort on
     * @param isDescending - true if the property should be sorted in descending order
     * @param size - the number of elements to keep in the sort
     */
    public SortWindowView(String propertyName, boolean isDescending, int size)
    {
    	this(new Object[] {propertyName, isDescending}, size);
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
    public final String[] getSortFieldNames()
    {
        return sortFieldNames;
    }
    
    /**
     * Returns the flags indicating whether to sort in descending order on each property
     * @return the isDescending value for each sort property
     */
    public final Boolean[] getIsDescendingValues()
    {
    	return isDescendingValues;
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
    * Set the sort order for the sort properties.
    * @param isDescendingValues - the direction to sort in for each sort property
    */
    public final void setIsDescendingValues(Boolean[] isDescendingValues) {
		this.isDescendingValues = isDescendingValues;
	}

	/**
	 * Set the names of the properties to sort on.
	 * @param sortFieldNames - the names of the properties to sort on
	 */
    public final void setSortFieldNames(String[] sortFieldNames) {
		this.sortFieldNames = sortFieldNames;
	}

    /**
     * Set the number of elements kept by the sort window.
     * param sortWindowSize - size of window
     */
    public final void setSortWindowSize(int sortWindowSize) {
		this.sortWindowSize = sortWindowSize;
	}

	public final String attachesTo(Viewable parentView)
    {
        // Attaches to parent views where the sort fields exist and implement Comparable
    	String result = null;
    	for(String name : sortFieldNames)
    	{
    		result = PropertyCheckHelper.exists(parentView.getEventType(), name);

    		if(result != null)
    		{
    			break;
    		}
    	}
    	return result;
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
                MultiKey sortValues = getSortValues(oldData[i]);
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
                MultiKey sortValues = getSortValues(newData[i]);
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
                MultiKey lastKey = sortedEvents.lastKey();
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
                " sortFieldName=" + sortFieldNames +
                " isDescending=" + isDescendingValues +
                " sortWindowSize=" + sortWindowSize;
    }

    private void add(MultiKey key, EventBean bean)
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

    private boolean remove(MultiKey key, EventBean bean)
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
    
    private void setNamesAndIsDescendingValues(Object[] propertiesAndDirections)
    {
    	if(propertiesAndDirections.length % 2 != 0)
    	{
    		throw new IllegalArgumentException("Each property to sort by must have an isDescending boolean qualifier");
    	}

    	int length = propertiesAndDirections.length / 2;
    	sortFieldNames = new String[length];
    	isDescendingValues  = new Boolean[length];
    	
    	for(int i = 0; i < length; i++)
    	{
    		sortFieldNames[i] = (String)propertiesAndDirections[2*i];
    		isDescendingValues[i] = (Boolean)propertiesAndDirections[2*i + 1];
    	}
    }
    
    private MultiKey<Object> getSortValues(EventBean event)
    {
    	Object[] result = new Object[sortFieldGetters.length];
    	int count = 0;
    	for(EventPropertyGetter getter : sortFieldGetters)
    	{
    		result[count++] = getter.get(event);
    	}
    	return new MultiKey<Object>(result);
    }

    private static final Log log = LogFactory.getLog(SortWindowView.class);
}
