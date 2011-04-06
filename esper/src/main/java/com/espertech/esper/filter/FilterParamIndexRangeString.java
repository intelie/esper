/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.filter;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Collection;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Index for filter parameter constants for the range operators (range open/closed/half).
 * The implementation is based on the SortedMap implementation of TreeMap and stores only expression
 * parameter values of type DoubleRange.
 */
public final class FilterParamIndexRangeString extends FilterParamIndexPropBase
{
    private final TreeMap<StringRange, EventEvaluator> ranges;
    private final ReadWriteLock rangesRWLock;

   /**
     * Constructs the index for matching ranges.
     * @param attributeName is the name of the event attribute field
    * @param filterOperator is the type of range
    * @param eventType is type of events handled
    */
    public FilterParamIndexRangeString(String attributeName, FilterOperator filterOperator, EventType eventType)
    {
        super(attributeName, filterOperator, eventType);

        ranges = new TreeMap<StringRange, EventEvaluator>(new StringRangeComparator());
        rangesRWLock = new ReentrantReadWriteLock();

        if (!(filterOperator.isRangeOperator()))
        {
            throw new IllegalArgumentException("Invalid filter operator " + filterOperator);
        }
    }

    public final EventEvaluator get(Object expressionValue)
    {
        if (!(expressionValue instanceof StringRange))
        {
            throw new IllegalArgumentException("Supplied expressionValue must be of type StringRange");
        }

        return ranges.get(expressionValue);
    }

    public final void put(Object expressionValue, EventEvaluator matcher)
    {
        if (!(expressionValue instanceof StringRange))
        {
            throw new IllegalArgumentException("Supplied expressionValue must be of type DoubleRange");
        }

        StringRange range = (StringRange) expressionValue;
        if ((range.getMax() == null) || (range.getMin() == null))
        {
            return;     // endpoints null - we don't enter
        }

        ranges.put(range, matcher);
    }

    public final boolean remove(Object filterConstant)
    {
        if (ranges.remove(filterConstant) == null)
        {
            return false;
        }

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

    public final void matchEvent(EventBean eventBean, Collection<FilterHandle> matches, ExprEvaluatorContext exprEvaluatorContext)
    {
        Object objAttributeValue = this.getGetter().get(eventBean);

        if (objAttributeValue == null)
        {
            return;
        }

        
        String attributeValue = (String) objAttributeValue;

        StringRange rangeStart = new StringRange(null, attributeValue);
        StringRange rangeEnd = new StringRange(attributeValue, null);
        SortedMap<StringRange, EventEvaluator> subMap = ranges.subMap(rangeStart, true, rangeEnd, true);

        // For not including either endpoint
        // A bit awkward to duplicate the loop code, however better than checking the boolean many times over
        // This may be a bit of an early performance optimization - the optimizer after all may do this better
        if (this.getFilterOperator() == FilterOperator.RANGE_OPEN)  // include neither endpoint
        {
            for (Map.Entry<StringRange, EventEvaluator> entry : subMap.entrySet())
            {
                if (entry.getKey().getMin().compareTo(attributeValue) < 0 && entry.getKey().getMax().compareTo(attributeValue) > 0) {
                    entry.getValue().matchEvent(eventBean, matches, exprEvaluatorContext);
                }
            }
        }
        else if (this.getFilterOperator() == FilterOperator.RANGE_CLOSED)   // include all endpoints
        {
            for (Map.Entry<StringRange, EventEvaluator> entry : subMap.entrySet())
            {
                if (entry.getKey().getMin().compareTo(attributeValue) <= 0 && entry.getKey().getMax().compareTo(attributeValue) >= 0) {
                    entry.getValue().matchEvent(eventBean, matches, exprEvaluatorContext);
                }
            }
        }
        else if (this.getFilterOperator() == FilterOperator.RANGE_HALF_CLOSED) // include high endpoint not low endpoint
        {
            for (Map.Entry<StringRange, EventEvaluator> entry : subMap.entrySet())
            {
                if (entry.getKey().getMin().compareTo(attributeValue) < 0 && entry.getKey().getMax().compareTo(attributeValue) >= 0) {
                    entry.getValue().matchEvent(eventBean, matches, exprEvaluatorContext);
                }
            }
        }
        else if (this.getFilterOperator() == FilterOperator.RANGE_HALF_OPEN) // include low endpoint not high endpoint
        {
            for (Map.Entry<StringRange, EventEvaluator> entry : subMap.entrySet())
            {
                if (entry.getKey().getMin().compareTo(attributeValue) <= 0 && entry.getKey().getMax().compareTo(attributeValue) > 0) {
                    entry.getValue().matchEvent(eventBean, matches, exprEvaluatorContext);
                }
            }
        }
        else
        {
            throw new IllegalStateException("Invalid filter operator " + this.getFilterOperator());
        }
    }

    private static final Log log = LogFactory.getLog(FilterParamIndexRangeString.class);
}
