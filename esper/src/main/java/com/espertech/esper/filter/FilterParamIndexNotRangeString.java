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
 * Index for filter parameter constants for the not range operators (range open/closed/half).
 * The implementation is based on the SortedMap implementation of TreeMap and stores only expression
 * parameter values of type StringRange.
 */
public final class FilterParamIndexNotRangeString extends FilterParamIndexPropBase
{
    private final TreeMap<StringRange, EventEvaluator> ranges;
    private final Set<EventEvaluator> evaluators;
    private final ReadWriteLock rangesRWLock;

    private double largestRangeValueDouble = Double.MIN_VALUE;

   /**
     * Constructs the index for matching ranges.
     * @param attributeName is the name of the event attribute field
    * @param filterOperator is the type of range
    * @param eventType is type of events handled
    */
    public FilterParamIndexNotRangeString(String attributeName, FilterOperator filterOperator, EventType eventType)
    {
        super(attributeName, filterOperator, eventType);

        ranges = new TreeMap<StringRange, EventEvaluator>(new StringRangeComparator());
        evaluators = new HashSet<EventEvaluator>();
        rangesRWLock = new ReentrantReadWriteLock();

        if (!(filterOperator.isInvertedRangeOperator()))
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
            throw new IllegalArgumentException("Supplied expressionValue must be of type StringRange");
        }

        StringRange range = (StringRange) expressionValue;

        if ((range.getMax() == null) || (range.getMin() == null))
        {
            return; // null endpoints are ignored
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

    public final void matchEvent(EventBean eventBean, Collection<FilterHandle> matches, ExprEvaluatorContext exprEvaluatorContext)
    {
        Object objAttributeValue = this.getGetter().get(eventBean);

        if (objAttributeValue == null)
        {
            return;
        }

        String attributeValue = (String) objAttributeValue;

        if (this.getFilterOperator() == FilterOperator.NOT_RANGE_CLOSED)   // include all endpoints
        {
            for (Map.Entry<StringRange, EventEvaluator> entry : ranges.entrySet()) {
                if (entry.getKey().getMin().compareTo(attributeValue) > 0 || entry.getKey().getMax().compareTo(attributeValue) < 0) {
                    entry.getValue().matchEvent(eventBean, matches, exprEvaluatorContext);
                }
            }
        }
        else if (this.getFilterOperator() == FilterOperator.NOT_RANGE_OPEN) {  // include neither endpoint
            for (Map.Entry<StringRange, EventEvaluator> entry : ranges.entrySet()) {
                if (entry.getKey().getMin().compareTo(attributeValue) >= 0 || entry.getKey().getMax().compareTo(attributeValue) <= 0) {
                    entry.getValue().matchEvent(eventBean, matches, exprEvaluatorContext);
                }
            }
        }
        else if (this.getFilterOperator() == FilterOperator.NOT_RANGE_HALF_CLOSED) // include high endpoint not low endpoint
        {
            for (Map.Entry<StringRange, EventEvaluator> entry : ranges.entrySet()) {
                if (entry.getKey().getMin().compareTo(attributeValue) >= 0 || entry.getKey().getMax().compareTo(attributeValue) < 0) {
                    entry.getValue().matchEvent(eventBean, matches, exprEvaluatorContext);
                }
            }
        }
        else if (this.getFilterOperator() == FilterOperator.NOT_RANGE_HALF_OPEN) // include low endpoint not high endpoint
        {
            for (Map.Entry<StringRange, EventEvaluator> entry : ranges.entrySet()) {
                if (entry.getKey().getMin().compareTo(attributeValue) > 0 || entry.getKey().getMax().compareTo(attributeValue) <= 0) {
                    entry.getValue().matchEvent(eventBean, matches, exprEvaluatorContext);
                }
            }
        }
        else
        {
            throw new IllegalStateException("Invalid filter operator " + this.getFilterOperator());
        }
    }

    private static final Log log = LogFactory.getLog(FilterParamIndexNotRangeString.class);
}
