package net.esper.filter;

import net.esper.event.EventType;
import net.esper.event.EventBean;
import net.esper.pattern.Evaluator;

import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Index for filter parameter constants for the not range operators (range open/closed/half).
 * The implementation is based on the SortedMap implementation of TreeMap and stores only expression
 * parameter values of type DoubleRange.
 */
public final class FilterParamIndexNotRange extends FilterParamIndex
{
    private final TreeMap<DoubleRange, EventEvaluator> ranges;
    private final Set<EventEvaluator> evaluators;
    private final ReadWriteLock rangesRWLock;

    private double largestRangeValueDouble = Double.MIN_VALUE;

   /**
     * Constructs the index for matching ranges.
     * @param attributeName is the name of the event attribute field
    * @param filterOperator is the type of range
    * @param eventType is type of events handled
    */
    public FilterParamIndexNotRange(String attributeName, FilterOperator filterOperator, EventType eventType)
    {
        super(attributeName, filterOperator, eventType);

        ranges = new TreeMap<DoubleRange, EventEvaluator>(new DoubleRangeComparator());
        evaluators = new HashSet<EventEvaluator>();
        rangesRWLock = new ReentrantReadWriteLock();

        if (!(filterOperator.isInvertedRangeOperator()))
        {
            throw new IllegalArgumentException("Invalid filter operator " + filterOperator);
        }
    }

    public final EventEvaluator get(Object expressionValue)
    {
        if (!(expressionValue instanceof DoubleRange))
        {
            throw new IllegalArgumentException("Supplied expressionValue must be of type DoubleRange");
        }

        return ranges.get(expressionValue);
    }

    public final void put(Object expressionValue, EventEvaluator matcher)
    {
        if (!(expressionValue instanceof DoubleRange))
        {
            throw new IllegalArgumentException("Supplied expressionValue must be of type DoubleRange");
        }

        DoubleRange range = (DoubleRange) expressionValue;

        if ( Math.abs(range.getMax() - range.getMin()) > largestRangeValueDouble)
        {
            largestRangeValueDouble = Math.abs(range.getMax() - range.getMin());
        }

        ranges.put(range, matcher);
        evaluators.add(matcher);
    }

    public final boolean remove(Object filterConstant)
    {
        EventEvaluator eval = ranges.remove(filterConstant);
        if (eval == null)
        {
            return false;
        }
        evaluators.remove(eval);
        return true;
    }

    public final int size()
    {
        return ranges.size();
    }

    public final ReadWriteLock getReadWriteLock()
    {
        return rangesRWLock;
    }

    public final void matchEvent(EventBean eventBean, List<FilterCallback> matches)
    {
        Object objAttributeValue = this.getGetter().get(eventBean);

        if (log.isDebugEnabled())
        {
            log.debug(".match Finding range matches, attribute=" + this.getPropertyName() +
                      "  attrValue=" + objAttributeValue);
        }

        if (objAttributeValue == null)
        {
            return;
        }

        double attributeValue = ((Number) objAttributeValue).doubleValue();

        DoubleRange rangeStart = new DoubleRange(attributeValue - largestRangeValueDouble, attributeValue);
        DoubleRange rangeEnd = new DoubleRange(attributeValue, Double.MAX_VALUE);

        SortedMap<DoubleRange, EventEvaluator> subMap = ranges.subMap(rangeStart, rangeEnd);

        // For not including either endpoint
        // A bit awkward to duplicate the loop code, however better than checking the boolean many times over
        // This may be a bit of an early performance optimization - the optimizer after all may do this better
        Set<EventEvaluator> matchingEvals = new HashSet<EventEvaluator>();
        if (this.getFilterOperator() == FilterOperator.NOT_RANGE_OPEN)  // include neither endpoint
        {
            for (Map.Entry<DoubleRange, EventEvaluator> entry : subMap.entrySet())
            {
                if ((attributeValue > entry.getKey().getMin()) &&
                    (attributeValue < entry.getKey().getMax()))
                {
                    matchingEvals.add(entry.getValue());
                }
            }
        }
        else if (this.getFilterOperator() == FilterOperator.NOT_RANGE_CLOSED)   // include all endpoints
        {
            for (Map.Entry<DoubleRange, EventEvaluator> entry : subMap.entrySet())
            {
                if ((attributeValue >= entry.getKey().getMin()) &&
                    (attributeValue <= entry.getKey().getMax()))
                {
                    matchingEvals.add(entry.getValue());
                }
            }
        }
        else if (this.getFilterOperator() == FilterOperator.NOT_RANGE_HALF_CLOSED) // include high endpoint not low endpoint
        {
            for (Map.Entry<DoubleRange, EventEvaluator> entry : subMap.entrySet())
            {
                if ((attributeValue > entry.getKey().getMin()) &&
                    (attributeValue <= entry.getKey().getMax()))
                {
                    matchingEvals.add(entry.getValue());
                }
            }
        }
        else if (this.getFilterOperator() == FilterOperator.NOT_RANGE_HALF_OPEN) // include low endpoint not high endpoint
        {
            for (Map.Entry<DoubleRange, EventEvaluator> entry : subMap.entrySet())
            {
                if ((attributeValue >= entry.getKey().getMin()) &&
                    (attributeValue < entry.getKey().getMax()))
                {
                    matchingEvals.add(entry.getValue());
                }
            }
        }
        else
        {
            throw new IllegalStateException("Invalid filter operator " + this.getFilterOperator());
        }

        // Now we have all the matching evaluators, invoke all that don't match
        for (EventEvaluator eval : evaluators)
        {
            if (!matchingEvals.contains(eval))
            {
                eval.matchEvent(eventBean, matches);
            }
        }
    }

    private static final Log log = LogFactory.getLog(FilterParamIndexNotRange.class);
}
