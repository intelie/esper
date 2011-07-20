/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.filter;

import com.espertech.esper.client.EventType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.IdentityHashMap;
import java.util.TreeMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Index for filter parameter constants for the range operators (range open/closed/half).
 * The implementation is based on the SortedMap implementation of TreeMap and stores only expression
 * parameter values of type DoubleRange.
 */
public abstract class FilterParamIndexDoubleRangeBase extends FilterParamIndexPropBase
{
    protected final TreeMap<DoubleRange, EventEvaluator> ranges;
    private final IdentityHashMap<DoubleRange, EventEvaluator> rangesNullEndpoints;
    private final ReadWriteLock rangesRWLock;

    protected double largestRangeValueDouble = Double.MIN_VALUE;

   /**
     * Constructs the index for matching ranges.
     * @param attributeName is the name of the event attribute field
    * @param filterOperator is the type of range
    * @param eventType is type of events handled
    */
    public FilterParamIndexDoubleRangeBase(String attributeName, FilterOperator filterOperator, EventType eventType)
    {
        super(attributeName, filterOperator, eventType);

        ranges = new TreeMap<DoubleRange, EventEvaluator>(new DoubleRangeComparator());
        rangesNullEndpoints = new IdentityHashMap<DoubleRange, EventEvaluator>();
        rangesRWLock = new ReentrantReadWriteLock();
    }

    public final EventEvaluator get(Object expressionValue)
    {
        if (!(expressionValue instanceof DoubleRange))
        {
            throw new IllegalArgumentException("Supplied expressionValue must be of type DoubleRange");
        }

        DoubleRange range = (DoubleRange) expressionValue;
        if ((range.getMax() == null) || (range.getMin() == null))
        {
            return rangesNullEndpoints.get(range);
        }

        return ranges.get(range);
    }

    public final void put(Object expressionValue, EventEvaluator matcher)
    {
        if (!(expressionValue instanceof DoubleRange))
        {
            throw new IllegalArgumentException("Supplied expressionValue must be of type DoubleRange");
        }

        DoubleRange range = (DoubleRange) expressionValue;
        if ((range.getMax() == null) || (range.getMin() == null))
        {
            rangesNullEndpoints.put(range, matcher);     // endpoints null - we don't enter
            return;
        }

        if ( Math.abs(range.getMax() - range.getMin()) > largestRangeValueDouble)
        {
            largestRangeValueDouble = Math.abs(range.getMax() - range.getMin());
        }

        ranges.put(range, matcher);
    }

    public final boolean remove(Object filterConstant)
    {
        DoubleRange range = (DoubleRange) filterConstant;

        if ((range.getMax() == null) || (range.getMin() == null))
        {
            return rangesNullEndpoints.remove(range) != null;
        }

        return ranges.remove(range) != null;
    }

    public final int size()
    {
        return ranges.size();
    }

    public final ReadWriteLock getReadWriteLock()
    {
        return rangesRWLock;
    }

    private static final Log log = LogFactory.getLog(FilterParamIndexDoubleRangeBase.class);
}
