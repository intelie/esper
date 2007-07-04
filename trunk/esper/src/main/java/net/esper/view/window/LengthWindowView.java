package net.esper.view.window;

import java.util.Iterator;
import java.util.LinkedList;

import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.view.ViewSupport;
import net.esper.view.CloneableView;
import net.esper.view.View;
import net.esper.core.StatementContext;
import net.esper.collection.ViewUpdatedCollection;

/**
 * This view is a moving window extending the specified number of elements into the past.
 */
public final class LengthWindowView extends ViewSupport implements DataWindowView, CloneableView
{
    private final LengthWindowViewFactory lengthWindowViewFactory;
    private final int size;
    private final ViewUpdatedCollection viewUpdatedCollection;
    private final LinkedList<EventBean> events = new LinkedList<EventBean>();

    /**
     * Constructor creates a moving window extending the specified number of elements into the past.
     * @param size is the specified number of elements into the past
     * @param viewUpdatedCollection is a collection that the view must update when receiving events  
     * @param lengthWindowViewFactory for copying this view in a group-by
     */
    public LengthWindowView(LengthWindowViewFactory lengthWindowViewFactory, int size, ViewUpdatedCollection viewUpdatedCollection)
    {
        if (size < 1)
        {
            throw new IllegalArgumentException("Illegal argument for size of length window");
        }

        this.lengthWindowViewFactory = lengthWindowViewFactory;
        this.size = size;
        this.viewUpdatedCollection = viewUpdatedCollection;
    }

    public View cloneView(StatementContext statementContext)
    {
        return lengthWindowViewFactory.makeView(statementContext);
    }

    /**
     * Returns true if the window is empty, or false if not empty.
     * @return true if empty
     */
    public boolean isEmpty()
    {
        return events.isEmpty();
    }

    /**
     * Returns the size of the length window.
     * @return size of length window
     */
    public final int getSize()
    {
        return size;
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
        // The event type is the parent view's event type
        return parent.getEventType();
    }

    public final void update(EventBean[] newData, EventBean[] oldData)
    {
        // add data points to the window
        // we don't care about removed data from a prior view
        if (newData != null)
        {
            for (int i = 0; i < newData.length; i++)
            {
                events.add(newData[i]);
            }
        }

        // Check for any events that get pushed out of the window
        int expiredCount = events.size() - size;
        EventBean[] expiredArr = null;
        if (expiredCount > 0)
        {
            expiredArr = new EventBean[expiredCount];
            for (int i = 0; i < expiredCount; i++)
            {
                expiredArr[i] = events.removeFirst();
            }
        }

        // update event buffer for access by expressions, if any
        if (viewUpdatedCollection != null)
        {
            viewUpdatedCollection.update(newData, expiredArr);
        }
        
        // If there are child views, call update method
        if (this.hasViews())
        {
            updateChildren(newData, expiredArr);
        }
    }

    public final Iterator<EventBean> iterator()
    {
        return events.iterator();
    }

    public final String toString()
    {
        return this.getClass().getName() + " size=" + size;
    }
}
