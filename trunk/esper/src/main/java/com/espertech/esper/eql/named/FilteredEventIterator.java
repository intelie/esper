package com.espertech.esper.eql.named;

import com.espertech.esper.eql.expression.ExprNode;
import com.espertech.esper.event.EventBean;

import java.util.List;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An iterator that filters events suppied by another iterator,
 * using a list of one or more filter expressions as filter.
 */
public class FilteredEventIterator implements Iterator<EventBean>
{
    private final Iterator<EventBean> parent;
    private final List<ExprNode> filterList;
    private final EventBean[] eventPerStream = new EventBean[1];
    private EventBean next;

    /**
     * Ctor.
     * @param filters is a list of expression nodes for filtering
     * @param parent is the iterator supplying the events to apply the filter on
     */
    public FilteredEventIterator(List<ExprNode> filters, Iterator<EventBean> parent)
    {
        this.parent = parent;
        this.filterList = filters;
        getNext();
    }

    public boolean hasNext()
    {
        return next != null;
    }

    public EventBean next()
    {
        if (next == null)
        {
            throw new NoSuchElementException();
        }

        EventBean result = next;
        getNext();
        return result;
    }

    public void remove()
    {
        throw new UnsupportedOperationException();
    }

    private void getNext()
    {
        if ((filterList == null) || (filterList.isEmpty()))
        {
            if (parent.hasNext())
            {
                next = parent.next();
            }
            else
            {
                next = null;
            }
            return;
        }

        while(parent.hasNext())
        {
            next = parent.next();

            eventPerStream[0] = next;
            boolean pass = true;
            for (ExprNode filter : filterList)
            {
                Boolean result = (Boolean) filter.evaluate(eventPerStream, true);
                if ((result != null) && (!result))
                {
                    pass = false;
                    break;
                }
            }

            if (pass)
            {
                return;
            }
        }

        next = null;
    }
}
