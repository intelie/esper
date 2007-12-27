package net.esper.eql.core;

import net.esper.event.EventBean;
import net.esper.eql.agg.AggregationService;
import net.esper.collection.MultiKeyUntyped;

import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;
import java.util.NoSuchElementException;

/**
 * Iterator for the group-by case with a row per group.
 */
public class ResultSetRowPerGroupIterator implements Iterator<EventBean>
{
    private final Iterator<EventBean> sourceIterator;
    private final ResultSetProcessorRowPerGroup resultSetProcessor;
    private final AggregationService aggregationService;
    private EventBean nextResult;
    private final EventBean[] eventsPerStream;
    private final Set<MultiKeyUntyped> priorSeenGroups;

    /**
     * Ctor.
     * @param sourceIterator is the parent view iterator
     * @param resultSetProcessor for providing results
     * @param aggregationService for pointing to the right aggregation row
     */
    public ResultSetRowPerGroupIterator(Iterator<EventBean> sourceIterator, ResultSetProcessorRowPerGroup resultSetProcessor, AggregationService aggregationService)
    {
        this.sourceIterator = sourceIterator;
        this.resultSetProcessor = resultSetProcessor;
        this.aggregationService = aggregationService;
        eventsPerStream = new EventBean[1];
        priorSeenGroups = new HashSet<MultiKeyUntyped>();
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
            if (priorSeenGroups.contains(groupKey))
            {
                continue;
            }
            priorSeenGroups.add(groupKey);

            if (resultSetProcessor.getSelectExprProcessor() == null)
            {
                nextResult = candidate;
            }
            else
            {
                nextResult = resultSetProcessor.getSelectExprProcessor().process(eventsPerStream, true);
            }

            break;
        }
    }

    public void remove()
    {
        throw new UnsupportedOperationException();
    }
}
