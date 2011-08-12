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

import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Index for filter parameter constants for the range operators (range open/closed/half).
 * The implementation is based on the SortedMap implementation of TreeMap and stores only expression
 * parameter values of type DoubleRange.
 */
public final class FilterParamIndexDoubleRange extends FilterParamIndexDoubleRangeBase
{
    public FilterParamIndexDoubleRange(String attributeName, FilterOperator filterOperator, EventType eventType) {
        super(attributeName, filterOperator, eventType);
        if (!(filterOperator.isRangeOperator()))
        {
            throw new IllegalArgumentException("Invalid filter operator " + filterOperator);
        }
    }
    
    public final void matchEvent(EventBean eventBean, Collection<FilterHandle> matches, ExprEvaluatorContext exprEvaluatorContext)
    {
        Object objAttributeValue = this.getGetter().get(eventBean);

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
        if (this.getFilterOperator() == FilterOperator.RANGE_OPEN)  // include neither endpoint
        {
            for (Map.Entry<DoubleRange, EventEvaluator> entry : subMap.entrySet())
            {
                if ((attributeValue > entry.getKey().getMin()) &&
                    (attributeValue < entry.getKey().getMax()))
                {
                    entry.getValue().matchEvent(eventBean, matches, exprEvaluatorContext);
                }
            }
        }
        else if (this.getFilterOperator() == FilterOperator.RANGE_CLOSED)   // include all endpoints
        {
            for (Map.Entry<DoubleRange, EventEvaluator> entry : subMap.entrySet())
            {
                if ((attributeValue >= entry.getKey().getMin()) &&
                    (attributeValue <= entry.getKey().getMax()))
                {
                    entry.getValue().matchEvent(eventBean, matches, exprEvaluatorContext);
                }
            }
        }
        else if (this.getFilterOperator() == FilterOperator.RANGE_HALF_CLOSED) // include high endpoint not low endpoint
        {
            for (Map.Entry<DoubleRange, EventEvaluator> entry : subMap.entrySet())
            {
                if ((attributeValue > entry.getKey().getMin()) &&
                    (attributeValue <= entry.getKey().getMax()))
                {
                    entry.getValue().matchEvent(eventBean, matches, exprEvaluatorContext);
                }
            }
        }
        else if (this.getFilterOperator() == FilterOperator.RANGE_HALF_OPEN) // include low endpoint not high endpoint
        {
            for (Map.Entry<DoubleRange, EventEvaluator> entry : subMap.entrySet())
            {
                if ((attributeValue >= entry.getKey().getMin()) &&
                    (attributeValue < entry.getKey().getMax()))
                {
                    entry.getValue().matchEvent(eventBean, matches, exprEvaluatorContext);
                }
            }
        }
        else
        {
            throw new IllegalStateException("Invalid filter operator " + this.getFilterOperator());
        }
    }

    private static final Log log = LogFactory.getLog(FilterParamIndexDoubleRange.class);
}
