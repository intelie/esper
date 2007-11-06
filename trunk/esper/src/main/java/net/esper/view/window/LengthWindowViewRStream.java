package net.esper.view.window;

import net.esper.core.StatementContext;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.view.CloneableView;
import net.esper.view.View;
import net.esper.view.ViewSupport;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * This view is a moving window extending the specified number of elements into the past,
 * allowing in addition to remove events efficiently for remove-stream events received by the view.
 */
public final class LengthWindowViewRStream extends ViewSupport implements DataWindowView, CloneableView
{
    private final LengthWindowViewFactory lengthWindowViewFactory;
    private final int size;
    private LinkedHashSet<EventBean> indexedEvents;

    /**
     * Constructor creates a moving window extending the specified number of elements into the past.
     * @param size is the specified number of elements into the past
     * @param lengthWindowViewFactory for copying this view in a group-by
     */
    public LengthWindowViewRStream(LengthWindowViewFactory lengthWindowViewFactory, int size)
    {
        if (size < 1)
        {
            throw new IllegalArgumentException("Illegal argument for size of length window");
        }

        this.lengthWindowViewFactory = lengthWindowViewFactory;
        this.size = size;
        indexedEvents = new LinkedHashSet<EventBean>();
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
        return indexedEvents.isEmpty();
    }

    /**
     * Returns the size of the length window.
     * @return size of length window
     */
    public final int getSize()
    {
        return size;
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
                indexedEvents.add(newData[i]);
            }
        }

        EventBean[] expiredArr = null;
        // Check for any events that get pushed out of the window
        int expiredCount = indexedEvents.size() - size;
        if (expiredCount > 0)
        {
            expiredArr = new EventBean[expiredCount];
            Iterator<EventBean> it = indexedEvents.iterator();
            for (int i = 0; i < expiredCount; i++)
            {
                expiredArr[i] = it.next();
            }
            for (EventBean anExpired : expiredArr)
            {
                indexedEvents.remove(anExpired);
            }
        }

        if (oldData != null)
        {
            for (EventBean anOldData : oldData)
            {
                indexedEvents.remove(anOldData);
            }

            if (expiredArr == null)
            {
                expiredArr = oldData;
            }
            else
            {
                Set<EventBean> oldDataSet = new HashSet<EventBean>();
                for (EventBean old : expiredArr)
                {
                    oldDataSet.add(old);
                }
                for (EventBean old : oldData)
                {
                    oldDataSet.add(old);
                }
                expiredArr = oldDataSet.toArray(new EventBean[0]);
            }
        }

        // If there are child views, call update method
        if (this.hasViews())
        {
            updateChildren(newData, expiredArr);
        }
    }

    public final Iterator<EventBean> iterator()
    {
        return indexedEvents.iterator();
    }

    public final String toString()
    {
        return this.getClass().getName() + " size=" + size;
    }
}
