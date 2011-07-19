/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.join.table;

import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.join.plan.QueryPlanIndexItem;

public class EventTableFactory
{
    /**
     * Build an index/table instance using the event properties for the event type.
     * @param indexedStreamNum - number of stream indexed
     * @param eventType - type of event to expect
     * @return table build
     */
    public static EventTable buildIndex(int indexedStreamNum, QueryPlanIndexItem item, EventType eventType, boolean coerceOnAddOnly)
    {
        String[] indexProps = item.getIndexProps();
        Class[] indexCoercionTypes = item.getOptIndexCoercionTypes();
        String[] rangeProps = item.getRangeProps();
        Class[] rangeCoercionTypes = item.getOptRangeCoercionTypes();

        EventTable table;
        if (rangeProps == null || rangeProps.length == 0) {
            if (indexProps == null || indexProps.length == 0)
            {
                table = new UnindexedEventTable(indexedStreamNum);
            }
            else
            {
                // single index key
                if (indexProps.length == 1) {
                    if (indexCoercionTypes == null || indexCoercionTypes.length == 0)
                    {
                        table = new PropertyIndexedEventTableSingle(indexedStreamNum, eventType, indexProps[0]);
                    }
                    else
                    {
                        if (coerceOnAddOnly) {
                            table = new PropertyIndexedEventTableSingleCoerceAdd(indexedStreamNum, eventType, indexProps[0], indexCoercionTypes[0]);
                        }
                        else {
                            table = new PropertyIndexedEventTableSingleCoerceAll(indexedStreamNum, eventType, indexProps[0], indexCoercionTypes[0]);
                        }
                    }
                }
                // Multiple index keys
                else {
                    if (indexCoercionTypes == null || indexCoercionTypes.length == 0)
                    {
                        table = new PropertyIndexedEventTable(indexedStreamNum, eventType, indexProps);
                    }
                    else
                    {
                        if (coerceOnAddOnly) {
                            table = new PropertyIndexedEventTableCoerceAdd(indexedStreamNum, eventType, indexProps, indexCoercionTypes);
                        }
                        else {
                            table = new PropertyIndexedEventTableCoerceAll(indexedStreamNum, eventType, indexProps, indexCoercionTypes);
                        }
                    }
                }
            }
        }
        else {
            if ((rangeProps.length == 1) && (indexProps == null || indexProps.length == 0)) {
                if (rangeCoercionTypes == null) {
                    return new PropertySortedEventTable(indexedStreamNum, eventType, rangeProps[0]);
                }
                else {
                    return new PropertySortedEventTableCoerced(indexedStreamNum, eventType, rangeProps[0], rangeCoercionTypes[0]);
                }
            }
            else {
                return new PropertyCompositeEventTable(indexedStreamNum, eventType, indexProps, indexCoercionTypes, rangeProps, rangeCoercionTypes);
            }
        }
        return table;
    }    
}
