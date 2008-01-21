package net.esper.eql.core;

import net.esper.event.EventBean;
import net.esper.eql.agg.AggregationService;
import net.esper.collection.MultiKeyUntyped;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Iterator for group-by with aggregation.
 */
public class ResultSetAggregateGroupedIterator implements Iterator<EventBean>
{
    private final Iterator<EventBean> sourceIterator;
    private final ResultSetProcessorAggregateGrouped resultSetProcessor;
    private final AggregationService aggregationService;
    private EventBean nextResult;
    private final EventBean[] eventsPerStream;

    /**
     * Ctor.
     * @param sourceIterator is the parent iterator
     * @param resultSetProcessor for constructing result rows
     * @param aggregationService for pointing to the right aggregation row
     */
    public ResultSetAggregateGroupedIterator(Iterator<EventBean> sourceIterator, ResultSetProcessorAggregateGrouped resultSetProcessor, AggregationService aggregationService)
    {
        this.sourceIterator = sourceIterator;
        this.resultSetProcessor = resultSetProcessor;
        this.aggregationService = aggregationService;
        eventsPerStream = new EventBean[1];
    }

    public boolean hasNext()
    {
        if (nextResult != null)
        {
            return true;
        }
        findNext();
        if (nextResult != null)
        {
            return true;
        }
        return false;
    }

    public EventBean next()
    {
        if (nextResult != null)
        {
            EventBean result = nextResult;
            nextResult = null;
            return result;
        }
        findNext();
        if (nextResult != null)
        {
            EventBean result = nextResult;
            nextResult = null;
            return result;
        }
        throw new NoSuchElementException();
    }

    private void findNext()
    {
        while (sourceIterator.hasNext())
        {
            EventBean candidate = sourceIterator.next();
            eventsPerStream[0] = candidate;

            MultiKeyUntyped groupKey = resultSetProcessor.generateGroupKey(eventsPerStream, true);
            aggregationService.setCurrentRow(groupKey);

            Boolean pass = true;
            if (resultSetProcessor.getOptionalHavingNode() != null)
            {
                pass = (Boolean) resultSetProcessor.getOptionalHavingNode().evaluate(eventsPerStream, true);
            }
            if (!pass)
            {
                continue;
            }

            nextResult = resultSetProcessor.getSelectExprProcessor().process(eventsPerStream, true, true);

            break;
        }
    }

    public void remove()
    {
        throw new UnsupportedOperationException();
    }
}
