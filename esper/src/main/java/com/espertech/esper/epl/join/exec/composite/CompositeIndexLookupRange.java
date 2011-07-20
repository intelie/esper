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

package com.espertech.esper.epl.join.exec.composite;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.join.exec.base.RangeIndexLookupValue;
import com.espertech.esper.epl.join.exec.base.RangeIndexLookupValueEquals;
import com.espertech.esper.epl.join.exec.base.RangeIndexLookupValueRange;
import com.espertech.esper.epl.join.plan.QueryGraphRangeEnum;
import com.espertech.esper.event.EventBeanUtility;
import com.espertech.esper.filter.Range;

import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class CompositeIndexLookupRange implements CompositeIndexLookup {

    private final RangeIndexLookupValue lookupValue;
    private final Class coercionType;
    private CompositeIndexLookup next;

    public CompositeIndexLookupRange(RangeIndexLookupValue lookupValue, Class coercionType) {
        this.lookupValue = lookupValue;
        this.coercionType = coercionType;
    }

    public void lookup(Map parent, Set<EventBean> result) {
        if (lookupValue instanceof RangeIndexLookupValueEquals) {
            RangeIndexLookupValueEquals equals = (RangeIndexLookupValueEquals) lookupValue;
            Object inner = parent.get(equals.getValue());
            if (next == null) {
                result.addAll((Set<EventBean>) inner);
            }
            else {
                Map innerMap = (Map) inner;
                next.lookup(innerMap, result);
            }
            return;
        }

        RangeIndexLookupValueRange lookup = (RangeIndexLookupValueRange) lookupValue;

        TreeMap treeMap = (TreeMap) parent;
        Object rangeValue = lookup.getValue();
        if (lookup.getOperator() == QueryGraphRangeEnum.RANGE_CLOSED) {
            Range range = (Range) rangeValue;
            lookupRange(result, treeMap, range.getLowEndpoint(), true, range.getHighEndpoint(), true, true);
        }
        else if (lookup.getOperator() == QueryGraphRangeEnum.RANGE_HALF_OPEN) {
            Range range = (Range) rangeValue;
            lookupRange(result, treeMap, range.getLowEndpoint(), true, range.getHighEndpoint(), false, true);
        }
        else if (lookup.getOperator() == QueryGraphRangeEnum.RANGE_HALF_CLOSED) {
            Range range = (Range) rangeValue;
            lookupRange(result, treeMap, range.getLowEndpoint(), false, range.getHighEndpoint(), true, true);
        }
        else if (lookup.getOperator() == QueryGraphRangeEnum.RANGE_OPEN) {
            Range range = (Range) rangeValue;
            lookupRange(result, treeMap, range.getLowEndpoint(), false, range.getHighEndpoint(), false, true);
        }
        else if (lookup.getOperator() == QueryGraphRangeEnum.NOT_RANGE_CLOSED) {
            Range range = (Range) rangeValue;
            lookupRangeInverted(result, treeMap, range.getLowEndpoint(), true, range.getHighEndpoint(), true);
        }
        else if (lookup.getOperator() == QueryGraphRangeEnum.NOT_RANGE_HALF_OPEN) {
            Range range = (Range) rangeValue;
            lookupRangeInverted(result, treeMap, range.getLowEndpoint(), true, range.getHighEndpoint(), false);
        }
        else if (lookup.getOperator() == QueryGraphRangeEnum.NOT_RANGE_HALF_CLOSED) {
            Range range = (Range) rangeValue;
            lookupRangeInverted(result, treeMap, range.getLowEndpoint(), false, range.getHighEndpoint(), true);
        }
        else if (lookup.getOperator() == QueryGraphRangeEnum.NOT_RANGE_OPEN) {
            Range range = (Range) rangeValue;
            lookupRangeInverted(result, treeMap, range.getLowEndpoint(), false, range.getHighEndpoint(), false);
        }
        else if (lookup.getOperator() == QueryGraphRangeEnum.GREATER) {
            lookupGreater(result, treeMap, rangeValue);
        }
        else if (lookup.getOperator() == QueryGraphRangeEnum.GREATER_OR_EQUAL) {
            lookupGreaterEqual(result, treeMap, rangeValue);
        }
        else if (lookup.getOperator() == QueryGraphRangeEnum.LESS) {
            lookupLess(result, treeMap, rangeValue);
        }
        else if (lookup.getOperator() == QueryGraphRangeEnum.LESS_OR_EQUAL) {
            lookupLessEqual(result, treeMap, rangeValue);
        }
        else {
            throw new IllegalArgumentException("Unrecognized operator '" + lookup.getOperator() + "'");
        }
    }

    public void lookupRange(Set<EventBean> result, TreeMap propertyIndex, Object keyStart, boolean includeStart, Object keyEnd, boolean includeEnd, boolean allowRangeReversal) {
        if (keyStart == null || keyEnd == null) {
            return;
        }
        keyStart = coerce(keyStart);
        keyEnd = coerce(keyEnd);
        SortedMap<Object,Object> submap;
        try {
            submap = propertyIndex.subMap(keyStart, includeStart, keyEnd, includeEnd);
        }
        catch (IllegalArgumentException ex) {
            if (allowRangeReversal) {
                submap = propertyIndex.subMap(keyEnd, includeStart, keyStart, includeEnd);
            }
            else {
                return;
            }
        }
        normalize(result, submap);
    }

    public void lookupRangeInverted(Set<EventBean> result, TreeMap propertyIndex, Object keyStart, boolean includeStart, Object keyEnd, boolean includeEnd) {
        if (keyStart == null || keyEnd == null) {
            return;
        }
        keyStart = coerce(keyStart);
        keyEnd = coerce(keyEnd);
        SortedMap<Object,Object> submapOne = propertyIndex.headMap(keyStart, !includeStart);
        SortedMap<Object,Object> submapTwo = propertyIndex.tailMap(keyEnd, !includeEnd);
        normalize(result, submapOne, submapTwo);
    }

    public void lookupLess(Set<EventBean> result, TreeMap propertyIndex, Object keyStart) {
        if (keyStart == null) {
            return;
        }
        keyStart = coerce(keyStart);
        normalize(result, propertyIndex.headMap(keyStart));
    }

    public void lookupLessEqual(Set<EventBean> result, TreeMap propertyIndex, Object keyStart) {
        if (keyStart == null) {
            return;
        }
        keyStart = coerce(keyStart);
        normalize(result, propertyIndex.headMap(keyStart, true));
    }

    public void lookupGreaterEqual(Set<EventBean> result, TreeMap propertyIndex, Object keyStart) {
        if (keyStart == null) {
            return;
        }
        keyStart = coerce(keyStart);
        normalize(result, propertyIndex.tailMap(keyStart));
    }

    public void lookupGreater(Set<EventBean> result, TreeMap propertyIndex, Object keyStart) {
        if (keyStart == null) {
            return;
        }
        keyStart = coerce(keyStart);
        normalize(result, propertyIndex.tailMap(keyStart, false));
    }

    private Object coerce(Object key) {
        return EventBeanUtility.coerce(key, coercionType);
    }

    private void normalize(Set<EventBean> result, Map<Object, Object> submap) {
        if (submap.size() == 0) {
            return;
        }
        if (next == null) {
            for (Map.Entry<Object, Object> entry : submap.entrySet()) {
                Set<EventBean> set = (Set<EventBean>) entry.getValue();
                result.addAll(set);
            }
        }
        else {
            for (Map.Entry<Object,Object> entry : submap.entrySet()) {
                TreeMap index = (TreeMap) entry.getValue();
                next.lookup(index, result);
            }
        }
    }

    private void normalize(Set<EventBean> result, SortedMap<Object,Object> submapOne, SortedMap<Object,Object> submapTwo) {
        normalize(result, submapTwo);
        normalize(result, submapOne);
    }

    public void setNext(CompositeIndexLookup next) {
        this.next = next;
    }
}
