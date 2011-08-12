/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */


package com.espertech.esper.filter;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class FilterParamIndexStringRangeBase extends FilterParamIndexPropBase
{
    protected final TreeMap<StringRange, EventEvaluator> ranges;
    private final IdentityHashMap<StringRange, EventEvaluator> rangesNullEndpoints;
    private final ReadWriteLock rangesRWLock;

   /**
     * Constructs the index for matching ranges.
     * @param attributeName is the name of the event attribute field
    * @param filterOperator is the type of range
    * @param eventType is type of events handled
    */
    public FilterParamIndexStringRangeBase(String attributeName, FilterOperator filterOperator, EventType eventType)
    {
        super(attributeName, filterOperator, eventType);

        ranges = new TreeMap<StringRange, EventEvaluator>(new StringRangeComparator());
        rangesNullEndpoints = new IdentityHashMap<StringRange, EventEvaluator>();
        rangesRWLock = new ReentrantReadWriteLock();
    }

    public final EventEvaluator get(Object expressionValue)
    {
        if (!(expressionValue instanceof StringRange))
        {
            throw new IllegalArgumentException("Supplied expressionValue must be of type StringRange");
        }

        StringRange range = (StringRange) expressionValue;

        if ((range.getMax() == null) || (range.getMin() == null))
        {
            return rangesNullEndpoints.get(range);
        }

        return ranges.get(range);
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
            rangesNullEndpoints.put(range, matcher);     // endpoints null - we don't enter
            return;
        }

        ranges.put(range, matcher);
    }

    public final boolean remove(Object filterConstant)
    {
        StringRange range = (StringRange) filterConstant;

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

    private static final Log log = LogFactory.getLog(FilterParamIndexStringRangeBase.class);
}
