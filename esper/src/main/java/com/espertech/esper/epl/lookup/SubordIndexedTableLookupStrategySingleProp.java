/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.lookup;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;
import com.espertech.esper.epl.join.table.PropertyIndexedEventTableSingle;
import com.espertech.esper.event.EventBeanUtility;

import java.util.Collection;

/**
 * Index lookup strategy for subqueries.
 */
public class SubordIndexedTableLookupStrategySingleProp implements SubordTableLookupStrategy
{
    private final String property;

    /**
     * Stream numbers to get key values from.
     */
    protected final int keyStreamNum;

    /**
     * Index to look up in.
     */
    protected final PropertyIndexedEventTableSingle index;

    /**
     * Getters to use to get key values.
     */
    protected final EventPropertyGetter propertyGetter;

    /**
     * Ctor.
     * @param eventTypes is the event types per stream
     * @param keyStreamNum is the stream number per property
     * @param property is the key properties
     * @param index is the table carrying the data to lookup into
     */
    public SubordIndexedTableLookupStrategySingleProp(boolean isNWOnTrigger, EventType[] eventTypes, int keyStreamNum, String property, PropertyIndexedEventTableSingle index)
    {
        this.keyStreamNum = keyStreamNum + (isNWOnTrigger ? 1 : 0); // for on-trigger the key will be provided in a {1,2,...} stream and not {0,...}
        this.property = property;
        this.index = index;

        propertyGetter = EventBeanUtility.getAssertPropertyGetter(eventTypes, keyStreamNum, property);
    }

    /**
     * Returns index to look up in.
     * @return index to use
     */
    public PropertyIndexedEventTableSingle getIndex()
    {
        return index;
    }

    public Collection<EventBean> lookup(EventBean[] eventsPerStream, ExprEvaluatorContext context)
    {
        Object key = getKey(eventsPerStream);
        return index.lookup(key);
    }

    /**
     * Get the index lookup keys.
     * @param eventsPerStream is the events for each stream
     * @return key object
     */
    protected Object getKey(EventBean[] eventsPerStream)
    {
        EventBean event = eventsPerStream[keyStreamNum];
        return propertyGetter.get(event);
    }

    public String toString()
    {
        return toQueryPlan();
    }

    public String toQueryPlan() {
        return this.getClass().getSimpleName() + " property=" + property + " stream=" + keyStreamNum;
    }
}
