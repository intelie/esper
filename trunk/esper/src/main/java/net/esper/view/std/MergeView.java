package net.esper.view.std;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

import java.util.*;
import net.esper.view.ViewSupport;
import net.esper.view.*;
import net.esper.event.EventType;
import net.esper.event.EventBean;
import net.esper.collection.IterablesListIterator;

/**
 * The merge view works together with a group view that splits the data in a stream to multiple subviews, based on
 * a key index. Every group view requires a merge view to merge the many subviews back into a single view.
 * Typically the last view in a chain containing a group view is a merge view.
 * The merge view has no other responsibility then becoming the single last instance in the chain
 * to which external listeners for updates can be attached to receive updates for the many subviews
 * that have this merge view as common child views.
 * The parent view of this view is generally the AddPropertyValueView that adds the grouped-by information
 * back into the data.
 */
public final class MergeView extends ViewSupport implements ContextAwareView
{
    private final LinkedList<View> parentViews = new LinkedList<View>();
    private String[] groupFieldNames;
    private EventType eventType;
    private ViewServiceContext viewServiceContext;

    /**
     * Default constructor - required by all views to adhere to the Java bean specification.
     */
    public MergeView()
    {
    }

    /**
     * Constructor.
     * @param groupFieldNames is the fields from which to pull the value to group by
     * @param resultEventType is passed by the factory as the factory adds the merged fields to an event type
     */
    public MergeView(String groupFieldNames[], EventType resultEventType)
    {
        this.groupFieldNames = groupFieldNames;
        this.eventType = resultEventType;
    }

    /**
     * Returns the field name that contains the values to group by.
     * @return field name providing group key value
     */
    public final String[] getGroupFieldNames()
    {
        return groupFieldNames;
    }

    public ViewServiceContext getViewServiceContext()
    {
        return viewServiceContext;
    }

    public void setViewServiceContext(ViewServiceContext viewServiceContext)
    {
        this.viewServiceContext = viewServiceContext;
    }

    /**
     * Sets the field name that contains the values to group by.
     * @param groupFieldNames is the field names providing group key values
     */
    public final void setGroupFieldNames(String groupFieldNames[])
    {
        this.groupFieldNames = groupFieldNames;
    }

    /**
     * Sets event type - required for successful view copy.
     * @param eventType is the event type
     */
    public void setEventType(EventType eventType)
    {
        this.eventType = eventType;
    }

    /**
     * Add a parent data merge view.
     * @param parentView is the parent data merge view to add
     */
    public final void addParentView(AddPropertyValueView parentView)
    {
        parentViews.add(parentView);
    }

    public final EventType getEventType()
    {
        // The schema is the parent view's schema plus the added field
        return eventType;
    }

    public final void update(EventBean[] newData, EventBean[] oldData)
    {
        if (log.isDebugEnabled())
        {
            log.debug(".update Updating view");
            dumpUpdateParams("MergeView", newData, oldData);
        }

        updateChildren(newData, oldData);
    }

    public final Iterator<EventBean> iterator()
    {
        // The merge data view has multiple parent views which are AddPropertyValueView
        List<Iterable<EventBean>> iterables = new LinkedList<Iterable<EventBean>>();

        for (View dataView : parentViews)
        {
            iterables.add(dataView);
        }

        return new IterablesListIterator(iterables);
    }

    public final String toString()
    {
        return this.getClass().getName() + " groupFieldName=" + Arrays.toString(groupFieldNames);
    }

    private static final Log log = LogFactory.getLog(MergeView.class);
}
