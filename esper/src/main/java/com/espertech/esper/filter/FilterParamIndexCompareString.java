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
import java.util.TreeMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Index for filter parameter constants for the comparison operators (less, greater, etc).
 * The implementation is based on the SortedMap implementation of TreeMap.
 * The index only accepts String constants. It keeps a lower and upper bounds of all constants in the index
 * for fast range checking, since the assumption is that frequently values fall within a range.
 */
public final class FilterParamIndexCompareString extends FilterParamIndexPropBase
{
    private final TreeMap<Object, EventEvaluator> constantsMap;
    private final ReadWriteLock constantsMapRWLock;

    /**
     * Constructs the index for matching comparison operators (<, >, <=, >=).
     * @param propertyName is the name of the event attribute field
     * @param filterOperator is the type of relational comparison operator
     * @param eventType describes the event type and is used to obtain a getter instance for the property
     * for fast get value access.
     */
    public FilterParamIndexCompareString(String propertyName, FilterOperator filterOperator, EventType eventType)
    {
        super(propertyName, filterOperator, eventType);
        constantsMap = new TreeMap<Object, EventEvaluator>();
        constantsMapRWLock = new ReentrantReadWriteLock();

        if ((filterOperator != FilterOperator.GREATER) &&
            (filterOperator != FilterOperator.GREATER_OR_EQUAL) &&
            (filterOperator != FilterOperator.LESS) &&
            (filterOperator != FilterOperator.LESS_OR_EQUAL))
        {
            throw new IllegalArgumentException("Invalid filter operator for index of " + filterOperator);
        }

        if (this.getPropertyBoxedType() != String.class)
        {
            throw new IllegalArgumentException("Property named '" + propertyName + "' is not of String type");
        }
    }

    public final EventEvaluator get(Object filterConstant)
    {
        checkType(filterConstant);
        return constantsMap.get(filterConstant);
    }

    public final void put(Object filterConstant, EventEvaluator matcher)
    {
        checkType(filterConstant);
        constantsMap.put(filterConstant, matcher);
    }

    public final boolean remove(Object filterConstant)
    {
        if (constantsMap.remove(filterConstant) == null)
        {
            return false;
        }

        return true;
    }

    public final int size()
    {
        return constantsMap.size();
    }

    public final ReadWriteLock getReadWriteLock()
    {
        return constantsMapRWLock;
    }

    public final void matchEvent(EventBean eventBean, Collection<FilterHandle> matches, ExprEvaluatorContext exprEvaluatorContext)
    {
        Object propertyValue = this.getGetter().get(eventBean);

        if (propertyValue == null)
        {
            return;
        }

        FilterOperator filterOperator = this.getFilterOperator();

        // Look up in table
        constantsMapRWLock.readLock().lock();

        // Get the head or tail end of the map depending on comparison type
        Map<Object, EventEvaluator> subMap;

        if ((filterOperator == FilterOperator.GREATER) ||
            (filterOperator == FilterOperator.GREATER_OR_EQUAL))
        {
            // At the head of the map are those with a lower numeric constants
            subMap = constantsMap.headMap(propertyValue);
        }
        else
        {
            subMap = constantsMap.tailMap(propertyValue);
        }

        // All entries in the subMap are elgibile, with an exception
        EventEvaluator exactEquals = null;
        if (filterOperator == FilterOperator.LESS)
        {
            exactEquals = constantsMap.get(propertyValue);
        }

        for (EventEvaluator matcher : subMap.values())
        {
            // For the LESS comparison type we ignore the exactly equal case
            // The subMap is sorted ascending, thus the exactly equals case is the first
            if (exactEquals != null)
            {
                exactEquals = null;
                continue;
            }

            matcher.matchEvent(eventBean, matches, exprEvaluatorContext);
        }

        if (filterOperator == FilterOperator.GREATER_OR_EQUAL)
        {
            EventEvaluator matcher = constantsMap.get(propertyValue);
            if (matcher != null)
            {
                matcher.matchEvent(eventBean, matches, exprEvaluatorContext);
            }
        }

        constantsMapRWLock.readLock().unlock();
    }

    private void checkType(Object filterConstant)
    {
        if (this.getPropertyBoxedType() != filterConstant.getClass())
        {
            throw new IllegalArgumentException("Invalid type of filter constant of " +
                    filterConstant.getClass().getName() + " for property " + this.getPropertyName());
        }
    }

    private static final Log log = LogFactory.getLog(FilterParamIndexCompareString.class);
}