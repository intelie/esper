package net.esper.view.std;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import java.util.*;

import net.esper.view.ViewSupport;
import net.esper.view.Viewable;
import net.esper.event.EventPropertyGetter;
import net.esper.event.EventType;
import net.esper.event.EventBean;
import net.esper.event.EventBeanUtility;
import net.esper.view.View;
import net.esper.view.*;
import net.esper.collection.Pair;
import net.esper.collection.MultiKey;
import net.esper.client.EPException;

/**
 * The group view splits the data in a stream to multiple subviews, based on a key index.
 * The key is one or more fields in the stream. Any view that follows the GROUP view will be executed
 * separately on each subview, one per unique key.
 *
 * The view takes a single parameter which is the field name returning the key value to group.
 *
 * This view can, for example, be used to calculate the average price per symbol for a list of symbols.
 *
 * The view treats its child views and their child views as prototypes. It dynamically instantiates copies
 * of each child view and their child views, and the child view's child views as so on. When there are
 * no more child views or the special merge view is encountered, it ends. The view installs a special merge
 * view unto each leaf child view that merges the value key that was grouped by back into the stream
 * using the group-by field name.
 */
public final class GroupByView extends ViewSupport implements ContextAwareView
{
    private String[] groupFieldNames;
    private EventPropertyGetter[] groupFieldGetters;
    private ViewServiceContext viewServiceContext;

    private final Map<MultiKey<Object>, List<View>> subViewsPerKey = new HashMap<MultiKey<Object>, List<View>>();;

    private HashMap<List<View>, Pair<List<EventBean>, List<EventBean>>> groupedEvents = new HashMap<List<View>, Pair<List<EventBean>, List<EventBean>>>();

    /**
     * Default constructor - required by all views to adhere to the Java bean specification.
     */
    public GroupByView()
    {
    }

    /**
     * Constructor.
     * @param groupFieldNames is the fields from which to pull the values to group by
     */
    public GroupByView(String[] groupFieldNames)
    {
        this.groupFieldNames = groupFieldNames;
    }

    public ViewServiceContext getViewServiceContext()
    {
        return viewServiceContext;
    }

    public void setViewServiceContext(ViewServiceContext viewServiceContext)
    {
        this.viewServiceContext = viewServiceContext;
    }

    public void setParent(Viewable parent)
    {
        super.setParent(parent);

        if (parent == null) // Since we may dis-associate view
        {
            return;
        }

        groupFieldGetters = new EventPropertyGetter[groupFieldNames.length];
        for (int i = 0; i < groupFieldNames.length; i++)
        {
            groupFieldGetters[i] = parent.getEventType().getGetter(groupFieldNames[i]);
        }
    }

    /**
     * Returns the field name that provides the key valie by which to group by.
     * @return field name providing group-by key.
     */
    public String[] getGroupFieldNames()
    {
        return groupFieldNames;
    }

    /**
     * Sets the field name that provides the key valie by which to group by.
     * @param groupFieldNames the the field names providing the group-by key values.
     */
    public final void setGroupFieldNames(String[] groupFieldNames)
    {
        this.groupFieldNames = groupFieldNames;
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
            dumpUpdateParams("GroupByView", newData, oldData);
        }

        if (newData != null)
        {
            for (EventBean newValue : newData)
            {
                handleEvent(newValue, true);
            }
        }

        if (oldData != null)
        {
            for (EventBean oldValue : oldData)
            {
                handleEvent(oldValue, false);
            }
        }

        // Update child views
        for (Map.Entry<List<View>, Pair<List<EventBean>, List<EventBean>>> entry : groupedEvents.entrySet())
        {
            EventBean[] newEvents = EventBeanUtility.toArray(entry.getValue().getFirst());
            EventBean[] oldEvents = EventBeanUtility.toArray(entry.getValue().getSecond());
            ViewSupport.updateChildren(entry.getKey(), newEvents, oldEvents);
        }

        groupedEvents.clear();
    }

    private final void handleEvent(EventBean event, boolean isNew)
    {
        // Get values for group-by, construct MultiKey
        Object[] groupByValues = new Object[groupFieldGetters.length];
        for (int i = 0; i < groupFieldGetters.length; i++)
        {
            groupByValues[i] = groupFieldGetters[i].get(event);
        }
        MultiKey<Object> groupByValuesKey = new MultiKey<Object>(groupByValues);

        // Get child views that belong to this group-by value combination
        List<View> subViews = this.subViewsPerKey.get(groupByValuesKey);

        // If this is a new group-by value, the list of subviews is null and we need to make clone sub-views
        if (subViews == null)
        {
            subViews = makeSubViews(this, groupByValuesKey.getArray(), viewServiceContext);
            subViewsPerKey.put(groupByValuesKey, subViews);
        }

        // Construct a pair of lists to hold the events for the grouped value if not already there
        Pair<List<EventBean>, List<EventBean>> pair = groupedEvents.get(subViews);
        if (pair == null)
        {
            LinkedList<EventBean> listNew = new LinkedList<EventBean>();
            LinkedList<EventBean> listOld = new LinkedList<EventBean>();
            pair = new Pair<List<EventBean>, List<EventBean>>(listNew, listOld);
            groupedEvents.put(subViews, pair);
        }

        // Add event to a child view event list for later child update that includes new and old events
        if (isNew)
        {
            pair.getFirst().add(event);
        }
        else
        {
            pair.getSecond().add(event);
        }
    }

    public final Iterator<EventBean> iterator()
    {
        throw new UnsupportedOperationException("Cannot iterate over group view, this operation is not supported");
    }

    public final String toString()
    {
        return this.getClass().getName() + " groupFieldNames=" + Arrays.toString(groupFieldNames);
    }

    /**
     * Instantiate subviews for the given group view and the given key value to group-by.
     * Makes shallow copies of each child view and its subviews up to the merge point.
     * Sets up merge data views for merging the group-by key value back in.
     * @param groupView is the parent view for which to copy subviews for
     * @param groupByValues is the key values to group-by
     * @param viewServiceContext is the view services that sub-views may need
     * @return a list of views that are copies of the original list, with copied children, with
     * data merge views added to the copied child leaf views.
     */
    protected static List<View> makeSubViews(GroupByView groupView, Object[] groupByValues,
                                             ViewServiceContext viewServiceContext)
    {
        if (!groupView.hasViews())
        {
            String message = "Unexpected empty list of child nodes for group view";
            log.fatal(".copySubViews " + message);
            throw new EPException(message);
        }

        LinkedList<View> subViewList = new LinkedList<View>();

        // For each child node
        for (View originalChildView : groupView.getViews())
        {
            if (originalChildView instanceof MergeView)
            {
                String message = "Unexpected merge view as child of group-by view";
                log.fatal(".copySubViews " + message);
                throw new EPException(message);
            }

            // Shallow copy child node
            View copyChildView = ViewSupport.shallowCopyView(originalChildView);
            copyChildView.setParent(groupView);
            subViewList.add(copyChildView);

            // Make the sub views for child copying from the original to the child
            copySubViews(groupView.getGroupFieldNames(), groupByValues, originalChildView, copyChildView,
                    viewServiceContext);
        }

        return subViewList;
    }

    private static void copySubViews(String[] groupFieldNames, Object[] groupByValues, View originalView, View copyView,
                                     ViewServiceContext viewServiceContext)
    {
        for (View subView : originalView.getViews())
        {
            // Determine if view is our merge view
            if (subView instanceof MergeView)
            {
                MergeView mergeView = (MergeView) subView;
                if (Arrays.equals(mergeView.getGroupFieldNames(), groupFieldNames))
                {
                    // We found our merge view - install a new data merge view on top of it
                    AddPropertyValueView mergeDataView = new AddPropertyValueView(groupFieldNames, groupByValues);                    
                    mergeDataView.setViewServiceContext(viewServiceContext);

                    // Add to the copied parent subview the view merge data view
                    copyView.addView(mergeDataView);

                    // Add to the new merge data view the actual single merge view instance that clients may attached to
                    mergeDataView.addView(mergeView);

                    // Add a parent view to the single merge view instance
                    mergeView.addParentView(mergeDataView);

                    continue;
                }
            }

            View copiedChild = ViewSupport.shallowCopyView(subView);
            copyView.addView(copiedChild);

            // Make the sub views for child
            copySubViews(groupFieldNames, groupByValues, subView, copiedChild, viewServiceContext);
        }
    }

    private static final Log log = LogFactory.getLog(GroupByView.class);
}