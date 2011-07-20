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

package com.espertech.esper.epl.agg;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.collection.IteratorCollectionRO;
import com.espertech.esper.epl.expression.ExprEvaluator;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;

/**
 * Represents the aggregation accessor that provides the result for the "window" aggregation function.
 */
public class AggregationAccessorAll implements AggregationAccessor
{
    private final int streamNum;
    private final ExprEvaluator childNode;
    private final EventBean[] eventsPerStream;
    private final Class componentType;

    /**
     * Ctor.
     * @param streamNum stream id
     * @param childNode expression
     * @param componentType type
     */
    public AggregationAccessorAll(int streamNum, ExprEvaluator childNode, Class componentType)
    {
        this.streamNum = streamNum;
        this.childNode = childNode;
        this.eventsPerStream = new EventBean[streamNum + 1];
        this.componentType = componentType;
    }

    public Object getValue(AggregationAccess access) {
        if (access.size() == 0) {
            return null;
        }
        Object array = Array.newInstance(componentType, access.size());
        Iterator<EventBean> it = access.iterator();
        int count = 0;
        for (;it.hasNext();) {
            EventBean bean = it.next();
            eventsPerStream[streamNum] = bean;
            Object value = childNode.evaluate(eventsPerStream, true, null);
            Array.set(array, count++, value);
        }

        return array;
    }

    public Collection<EventBean> getCollectionReadOnly(AggregationAccess access) {
        if (access.size() == 0) {
            return null;
        }
        return access.collectionReadOnly();
    }

    public EventBean getEventBean(AggregationAccess currentAcces) {
        return null;
    }
}