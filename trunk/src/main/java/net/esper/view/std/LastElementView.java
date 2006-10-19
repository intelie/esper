package net.esper.view.std;

import java.util.Iterator;
import java.util.Vector;

import net.esper.event.EventType;
import net.esper.event.EventBean;
import net.esper.view.Viewable;
import net.esper.view.ViewSupport;
import net.esper.collection.SingleEventIterator;

/**
 * This view is a very simple view presenting the last event posted by the parent view to any subviews.
 * Only the very last event object is kept by this view. The update method invoked by the parent view supplies
 * new data in an object array, of which the view keeps the very last instance as the 'last' or newest event.
 * The view always has the same schema as the parent view and attaches to anything, and accepts no parameters.
 *
 * Useful is the last view for example for "stocks.time_window(100).last()".
 *
 * Notice that "stocks.last().size()" and
 *             "stocks.win:length(10).std:lastevent().std:size()" must always return 0 or 1.
 *
 * Thus if 5 pieces of new data arrive, the child view receives 5 elements of new data
 * and also 4 pieces of old data which is the first 4 elements of new data.
 * I.e. New data elements immediatly gets to be old data elements.
 *
 *  Old data received from parent is not handled, it is ignored.
  * We thus post old data as follows:
 *      last event is not null +
 *      new data from index zero to N-1, where N is the index of the last element in new data
 */
public class LastElementView extends ViewSupport
{
    /**
     * The last new element posted from a parent view.
     */
    protected EventBean lastEvent;

    /**
     * Constructor.
     */
    public LastElementView()
    {
    }

    public final String attachesTo(Viewable parentView)
    {
        // Attaches to just about anything
        return null;
    }

    public final EventType getEventType()
    {
        // The schema is the parent view's schema
        return parent.getEventType();
    }

    public void update(EventBean[] newData, EventBean[] oldData)
    {
        Vector<EventBean> oldDataToPost = new Vector<EventBean>();

        if ((newData != null) && (newData.length != 0))
        {
            if (lastEvent != null)
            {
                oldDataToPost.add(lastEvent);
            }
            if (newData.length > 1)
            {
                for (int i = 0; i < newData.length - 1; i++)
                {
                    oldDataToPost.add(newData[i]);
                }
            }
            lastEvent = newData[newData.length - 1];
        }

        // If there are child views, fire update method
        if (this.hasViews())
        {
            if (oldDataToPost.size() > 0)
            {
                updateChildren(newData, oldDataToPost.toArray(new EventBean[0]));
            }
            else
            {
                updateChildren(newData, null);
            }
        }
    }

    public final Iterator<EventBean> iterator()
    {
        return new SingleEventIterator(lastEvent);
    }

    public final String toString()
    {
        return this.getClass().getName();
    }
}
