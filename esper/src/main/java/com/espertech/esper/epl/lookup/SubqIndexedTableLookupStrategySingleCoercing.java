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
import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.join.table.PropertyIndexedEventTable;
import com.espertech.esper.epl.join.table.PropertyIndexedEventTableSingle;
import com.espertech.esper.event.EventBeanUtility;
import com.espertech.esper.util.JavaClassHelper;

/**
 * Index lookup strategy that coerces the key values before performing a lookup.
 */
public class SubqIndexedTableLookupStrategySingleCoercing extends SubqIndexedTableLookupStrategySingle
{
    private Class coercionType;

    /**
     * Ctor.
     * @param eventTypes is the event type per stream
     * @param index is the table to look into
     */
    public SubqIndexedTableLookupStrategySingleCoercing(EventType[] eventTypes, int streamNum, String property, PropertyIndexedEventTableSingle index, Class coercionType)
    {
        super(eventTypes, streamNum, property, index);
        this.coercionType = coercionType;
    }

    protected Object getKeys(EventBean[] eventsPerStream)
    {
        Object key = super.getKey(eventsPerStream);
        return EventBeanUtility.coerce(key, coercionType);
    }
}
