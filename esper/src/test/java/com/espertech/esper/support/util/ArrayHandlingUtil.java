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

package com.espertech.esper.support.util;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.collection.MultiKeyUntyped;
import com.espertech.esper.util.MultiKeyComparator;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.LinkedList;

public class ArrayHandlingUtil
{
    public static EventBean[] reorder(final String key, EventBean[] events) {
        return reorder(new String[] {key}, events);
    }

    public static EventBean[] reorder(final String[] keys, EventBean[] events) {
        EventBean[] result = new EventBean[events.length];
        System.arraycopy(events, 0, result, 0, result.length);
        final MultiKeyComparator mkcomparator = new MultiKeyComparator(new boolean[keys.length]);
        Arrays.sort(result, new Comparator<EventBean>() {
            @Override
            public int compare(EventBean o1, EventBean o2) {
                MultiKeyUntyped mk1 = getMultiKey(o1, keys);
                MultiKeyUntyped mk2 = getMultiKey(o2, keys);
                return mkcomparator.compare(mk1, mk2);
            }
        });
        return result;
    }

    public static MultiKeyUntyped getMultiKey(EventBean event, String[] keys) {
        Object[] mk = new Object[keys.length];
        for (int i = 0; i < keys.length; i++) {
            mk[i] = event.get(keys[i]);
        }
        return new MultiKeyUntyped(mk);
    }

    public static Object[][] getUnderlyingEvents(EventBean[] events, String[] keys)
    {
        List<Object[]> resultList = new LinkedList<Object[]>();

        for (int i = 0; i < events.length; i++)
        {
            Object[] row = new Object[keys.length];
            for (int j = 0; j < keys.length; j++)
            {
                row[j] = events[i].get(keys[j]);
            }
            resultList.add(row);
        }

        Object[][] results = new Object[resultList.size()][];
        int count = 0;
        for (Object[] row : resultList)
        {
            results[count++] = row;
        }
        return results;
    }

}
