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

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Index for filter parameter constants to match using the equals (=) operator.
 * The implementation is based on a regular HashMap.
 */
public abstract class FilterParamIndexEqualsBase extends FilterParamIndexPropBase
{
    protected final Map<Object, EventEvaluator> constantsMap;
    protected final ReadWriteLock constantsMapRWLock;

    /**
     * Constructs the index for exact matches.
     * @param propertyName is the name of the event property
     * @param eventType describes the event type and is used to obtain a getter instance for the property
     */
    public FilterParamIndexEqualsBase(String propertyName, FilterOperator filterOperator, EventType eventType)
    {
        super(propertyName, filterOperator, eventType);

        constantsMap = new HashMap<Object, EventEvaluator>();
        constantsMapRWLock = new ReentrantReadWriteLock();
    }

    public final EventEvaluator get(Object filterConstant)
    {
        checkType(filterConstant);
        return constantsMap.get(filterConstant);
    }

    public final void put(Object filterConstant, EventEvaluator evaluator)
    {
        checkType(filterConstant);
        constantsMap.put(filterConstant, evaluator);
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

    protected void checkType(Object filterConstant)
    {
        if (filterConstant != null)
        {
            if ((this.getPropertyBoxedType() != filterConstant.getClass()) && (!this.getPropertyBoxedType().isAssignableFrom(filterConstant.getClass())))
            {
                throw new IllegalArgumentException("Invalid type of filter constant of " +
                        filterConstant.getClass().getName() + " for property " + this.getPropertyName());
            }
        }
    }
}
