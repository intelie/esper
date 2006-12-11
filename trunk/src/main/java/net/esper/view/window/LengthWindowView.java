package net.esper.view.window;

import java.util.Iterator;
import java.util.LinkedList;

import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.view.ViewSupport;
import net.esper.collection.RandomAccessIStreamImpl;

/**
 * This view is a moving window extending the specified number of elements into the past.
 */
public final class LengthWindowView extends ViewSupport implements DataWindowView
{
    private int size = 0;
    private RandomAccessIStreamImpl optionalRandomAccess;
    private final LinkedList<EventBean> events = new LinkedList<EventBean>();

    /**
     * Default constructor - required by all views to adhere to the Java bean specification.
     */
    public LengthWindowView()
    {
    }

    /**
     * Constructor creates a moving window extending the specified number of elements into the past.
     * @param size is the specified number of elements into the past
     */
    public LengthWindowView(int size, RandomAccessIStreamImpl optionalRandomAccess)
    {
        if (size < 1)
        {
            throw new IllegalArgumentException("Illegal argument for size of length window");
        }

        this.size = size;
        this.optionalRandomAccess = optionalRandomAccess;
    }

    public boolean isEmpty()
    {
        return events.size() == 0;
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
     * Sets the size of the length window.
     * @param size size of length window
     */
    public final void setSize(int size)
    {
        this.size = size;
    }

    public RandomAccessIStreamImpl getOptionalRandomAccess()
    {
        return optionalRandomAccess;
    }

    public void setOptionalRandomAccess(RandomAccessIStreamImpl optionalRandomAccess)
    {
        this.optionalRandomAccess = optionalRandomAccess;
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

        // If there are child views, fire update method
        if (optionalRandomAccess != null)
        {
            optionalRandomAccess.update(newData, oldData);
        }
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
