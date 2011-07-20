/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.join.table;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.client.EventType;
import com.espertech.esper.collection.SuperIterator;
import com.espertech.esper.epl.join.exec.base.RangeIndexLookupValue;
import com.espertech.esper.epl.join.exec.base.RangeIndexLookupValueEquals;
import com.espertech.esper.epl.join.exec.base.RangeIndexLookupValueRange;
import com.espertech.esper.epl.join.plan.QueryGraphRangeEnum;
import com.espertech.esper.event.EventBeanUtility;
import com.espertech.esper.filter.Range;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

/**
 * Index that organizes events by the event property values into a single TreeMap sortable non-nested index
 * with Object keys that store the property values.
 */
public class PropertySortedEventTable implements EventTable
{
    protected final int streamNum;
    protected final String propertyName;

    /**
     * Getters for properties.
     */
    protected final EventPropertyGetter propertyGetter;

    /**
     * Index table.
     */
    protected final TreeMap<Object, Set<EventBean>> propertyIndex;

    protected final HashSet<EventBean> nullKeyedValues;

    // override in a subclass
    protected Object coerce(Object value) {
        return value;
    }


    /**
     * Ctor.
     * @param streamNum - the stream number that is indexed
     * @param eventType - types of events indexed
     */
    public PropertySortedEventTable(int streamNum, EventType eventType, String propertyName)
    {
        this.streamNum = streamNum;
        this.propertyName = propertyName;
        propertyGetter = EventBeanUtility.getAssertPropertyGetter(eventType, propertyName);
        propertyIndex = new TreeMap<Object, Set<EventBean>>();
        nullKeyedValues = new LinkedHashSet<EventBean>();
    }

    /**
     * Determine multikey for index access.
     * @param event to get properties from for key
     * @return multi key
     */
    protected Object getIndexedValue(EventBean event)
    {
        return propertyGetter.get(event);
    }

    /**
     * Add an array of events. Same event instance is not added twice. Event properties should be immutable.
     * Allow null passed instead of an empty array.
     * @param events to add
     * @throws IllegalArgumentException if the event was already existed in the index
     */
    public void add(EventBean[] events)
    {
        if (events == null)
        {
            return;
        }
        for (EventBean event : events)
        {
            add(event);
        }
    }

    /**
     * Remove events.
     * @param events to be removed, can be null instead of an empty array.
     * @throws IllegalArgumentException when the event could not be removed as its not in the index
     */
    public void remove(EventBean[] events)
    {
        if (events == null)
        {
            return;
        }
        for (EventBean event : events)
        {
            remove(event);
        }
    }

    /**
     * Returns the set of events that have the same property value as the given event.
     * @param keyStart to compare against
     * @param keyEnd to compare against
     * @param allowRangeReversal indicate whether "a between 60 and 50" should return no results (equivalent to a>= X and a <=Y) or should return results (equivalent to 'between' and 'in'
     * @return set of events with property value, or null if none found (never returns zero-sized set)
     */
    public Set<EventBean> lookupRange(Object keyStart, boolean includeStart, Object keyEnd, boolean includeEnd, boolean allowRangeReversal) {
        if (keyStart == null || keyEnd == null) {
            return Collections.emptySet();
        }
        keyStart = coerce(keyStart);
        keyEnd = coerce(keyEnd);
        SortedMap<Object,Set<EventBean>> submap;
        try {
            submap = propertyIndex.subMap(keyStart, includeStart, keyEnd, includeEnd);
        }
        catch (IllegalArgumentException ex) {
            if (allowRangeReversal) {
                submap = propertyIndex.subMap(keyEnd, includeStart, keyStart, includeEnd);
            }
            else {
                return Collections.emptySet();
            }
        }
        return normalize(submap);
    }

    public Collection<EventBean> lookupRangeColl(Object keyStart, boolean includeStart, Object keyEnd, boolean includeEnd, boolean allowRangeReversal) {
        if (keyStart == null || keyEnd == null) {
            return Collections.emptyList();
        }
        keyStart = coerce(keyStart);
        keyEnd = coerce(keyEnd);
        SortedMap<Object,Set<EventBean>> submap;
        try {
            submap = propertyIndex.subMap(keyStart, includeStart, keyEnd, includeEnd);
        }
        catch (IllegalArgumentException ex) {
            if (allowRangeReversal) {
                submap = propertyIndex.subMap(keyEnd, includeStart, keyStart, includeEnd);
            }
            else {
                return Collections.emptyList();
            }
        }
        return normalizeCollection(submap);
    }

    public Set<EventBean> lookupRangeInverted(Object keyStart, boolean includeStart, Object keyEnd, boolean includeEnd) {
        if (keyStart == null || keyEnd == null) {
            return Collections.emptySet();
        }
        keyStart = coerce(keyStart);
        keyEnd = coerce(keyEnd);
        SortedMap<Object,Set<EventBean>> submapOne = propertyIndex.headMap(keyStart, !includeStart);
        SortedMap<Object,Set<EventBean>> submapTwo = propertyIndex.tailMap(keyEnd, !includeEnd);
        return normalize(submapOne, submapTwo);
    }

    public Collection<EventBean> lookupRangeInvertedColl(Object keyStart, boolean includeStart, Object keyEnd, boolean includeEnd) {
        if (keyStart == null || keyEnd == null) {
            return Collections.emptySet();
        }
        keyStart = coerce(keyStart);
        keyEnd = coerce(keyEnd);
        SortedMap<Object,Set<EventBean>> submapOne = propertyIndex.headMap(keyStart, !includeStart);
        SortedMap<Object,Set<EventBean>> submapTwo = propertyIndex.tailMap(keyEnd, !includeEnd);
        return normalizeCollection(submapOne, submapTwo);
    }

    public Set<EventBean> lookupLess(Object keyStart) {
        if (keyStart == null) {
            return Collections.emptySet();
        }
        keyStart = coerce(keyStart);
        return normalize(propertyIndex.headMap(keyStart));
    }

    public Collection<EventBean> lookupLessThenColl(Object keyStart) {
        if (keyStart == null) {
            return Collections.emptyList();
        }
        keyStart = coerce(keyStart);
        return normalizeCollection(propertyIndex.headMap(keyStart));
    }

    public Set<EventBean> lookupLessEqual(Object keyStart) {
        if (keyStart == null) {
            return Collections.emptySet();
        }
        keyStart = coerce(keyStart);
        return normalize(propertyIndex.headMap(keyStart, true));
    }

    public Collection<EventBean> lookupLessEqualColl(Object keyStart) {
        if (keyStart == null) {
            return Collections.emptyList();
        }
        keyStart = coerce(keyStart);
        return normalizeCollection(propertyIndex.headMap(keyStart, true));
    }

    public Set<EventBean> lookupGreaterEqual(Object keyStart) {
        if (keyStart == null) {
            return Collections.emptySet();
        }
        keyStart = coerce(keyStart);
        return normalize(propertyIndex.tailMap(keyStart));
    }

    public Collection<EventBean> lookupGreaterEqualColl(Object keyStart) {
        if (keyStart == null) {
            return Collections.emptyList();
        }
        keyStart = coerce(keyStart);
        return normalizeCollection(propertyIndex.tailMap(keyStart));
    }

    public Set<EventBean> lookupGreater(Object keyStart) {
        if (keyStart == null) {
            return Collections.emptySet();
        }
        keyStart = coerce(keyStart);
        return normalize(propertyIndex.tailMap(keyStart, false));
    }

    public Collection<EventBean> lookupGreaterColl(Object keyStart) {
        if (keyStart == null) {
            return Collections.emptyList();
        }
        keyStart = coerce(keyStart);
        return normalizeCollection(propertyIndex.tailMap(keyStart, false));
    }

    private Set<EventBean> normalize(SortedMap<Object,Set<EventBean>> submap) {
        if (submap.size() == 0) {
            return null;
        }
        if (submap.size() == 1) {
            return submap.get(submap.firstKey());
        }
        Set<EventBean> result = new LinkedHashSet<EventBean>();
        for (Map.Entry<Object,Set<EventBean>> entry : submap.entrySet()) {
            result.addAll(entry.getValue());
        }
        return result;
    }

    private Collection<EventBean> normalizeCollection(SortedMap<Object,Set<EventBean>> submap) {
        if (submap.size() == 0) {
            return null;
        }
        if (submap.size() == 1) {
            return submap.get(submap.firstKey());
        }
        Deque<EventBean> result = new ArrayDeque<EventBean>();
        for (Map.Entry<Object,Set<EventBean>> entry : submap.entrySet()) {
            result.addAll(entry.getValue());
        }
        return result;
    }

    private Collection<EventBean> normalizeCollection(SortedMap<Object,Set<EventBean>> submapOne, SortedMap<Object,Set<EventBean>> submapTwo) {
        if (submapOne.size() == 0) {
            return normalizeCollection(submapTwo);
        }
        if (submapTwo.size() == 0) {
            return normalizeCollection(submapOne);
        }
        ArrayDeque<EventBean> result = new ArrayDeque<EventBean>();
        for (Map.Entry<Object,Set<EventBean>> entry : submapOne.entrySet()) {
            result.addAll(entry.getValue());
        }
        for (Map.Entry<Object,Set<EventBean>> entry : submapTwo.entrySet()) {
            result.addAll(entry.getValue());
        }
        return result;
    }

    private Set<EventBean> normalize(SortedMap<Object,Set<EventBean>> submapOne, SortedMap<Object,Set<EventBean>> submapTwo) {
        if (submapOne.size() == 0) {
            return normalize(submapTwo);
        }
        if (submapTwo.size() == 0) {
            return normalize(submapOne);
        }
        Set<EventBean> result = new LinkedHashSet<EventBean>();
        for (Map.Entry<Object,Set<EventBean>> entry : submapOne.entrySet()) {
            result.addAll(entry.getValue());
        }
        for (Map.Entry<Object,Set<EventBean>> entry : submapTwo.entrySet()) {
            result.addAll(entry.getValue());
        }
        return result;
    }

    private void add(EventBean event)
    {
        Object key = getIndexedValue(event);

        key = coerce(key);

        if (key == null) {
            nullKeyedValues.add(event);
            return;
        }

        Set<EventBean> events = propertyIndex.get(key);
        if (events == null)
        {
            events = new LinkedHashSet<EventBean>();
            propertyIndex.put(key, events);
        }

        events.add(event);
    }

    private void remove(EventBean event)
    {
        Object key = getIndexedValue(event);

        if (key == null) {
            nullKeyedValues.remove(event);
            return;
        }

        key = coerce(key);

        Set<EventBean> events = propertyIndex.get(key);
        if (events == null)
        {
            return;
        }

        if (!events.remove(event))
        {
            // Not an error, its possible that an old-data event is artificial (such as for statistics) and
            // thus did not correspond to a new-data event raised earlier.
            return;
        }

        if (events.isEmpty())
        {
            propertyIndex.remove(key);
        }
    }

    public boolean isEmpty()
    {
        return propertyIndex.isEmpty();
    }

    public Iterator<EventBean> iterator()
    {
        if (nullKeyedValues.isEmpty()) {
            return new PropertySortedEventTableIterator(propertyIndex);
        }
        return new SuperIterator<EventBean>(new PropertySortedEventTableIterator(propertyIndex), nullKeyedValues.iterator());
    }

    public void clear()
    {
        propertyIndex.clear();
    }

    public String toString()
    {
        return toQueryPlan();
    }

    public String toQueryPlan() {
        return this.getClass().getSimpleName() +
                " streamNum=" + streamNum +
                " propertyName=" + propertyName;
    }

    private static Log log = LogFactory.getLog(PropertySortedEventTable.class);

    public Set<EventBean> lookupConstants(RangeIndexLookupValue lookupValueBase) {

        if (lookupValueBase instanceof RangeIndexLookupValueEquals) {
            RangeIndexLookupValueEquals equals = (RangeIndexLookupValueEquals) lookupValueBase;
            return propertyIndex.get(equals.getValue());    
        }

        RangeIndexLookupValueRange lookupValue = (RangeIndexLookupValueRange) lookupValueBase;
        if (lookupValue.getOperator() == QueryGraphRangeEnum.RANGE_CLOSED) {
            Range range = (Range) lookupValue.getValue();
            return lookupRange(range.getLowEndpoint(), true, range.getHighEndpoint(), true, lookupValue.isAllowRangeReverse());
        }
        else if (lookupValue.getOperator() == QueryGraphRangeEnum.RANGE_HALF_OPEN) {
            Range range = (Range) lookupValue.getValue();
            return lookupRange(range.getLowEndpoint(), true, range.getHighEndpoint(), false, lookupValue.isAllowRangeReverse());
        }
        else if (lookupValue.getOperator() == QueryGraphRangeEnum.RANGE_HALF_CLOSED) {
            Range range = (Range) lookupValue.getValue();
            return lookupRange(range.getLowEndpoint(), false, range.getHighEndpoint(), true, lookupValue.isAllowRangeReverse());
        }
        else if (lookupValue.getOperator() == QueryGraphRangeEnum.RANGE_OPEN) {
            Range range = (Range) lookupValue.getValue();
            return lookupRange(range.getLowEndpoint(), false, range.getHighEndpoint(), false, lookupValue.isAllowRangeReverse());
        }
        else if (lookupValue.getOperator() == QueryGraphRangeEnum.NOT_RANGE_CLOSED) {
            Range range = (Range) lookupValue.getValue();
            return lookupRangeInverted(range.getLowEndpoint(), true, range.getHighEndpoint(), true);
        }
        else if (lookupValue.getOperator() == QueryGraphRangeEnum.NOT_RANGE_HALF_OPEN) {
            Range range = (Range) lookupValue.getValue();
            return lookupRangeInverted(range.getLowEndpoint(), true, range.getHighEndpoint(), false);
        }
        else if (lookupValue.getOperator() == QueryGraphRangeEnum.NOT_RANGE_HALF_CLOSED) {
            Range range = (Range) lookupValue.getValue();
            return lookupRangeInverted(range.getLowEndpoint(), false, range.getHighEndpoint(), true);
        }
        else if (lookupValue.getOperator() == QueryGraphRangeEnum.NOT_RANGE_OPEN) {
            Range range = (Range) lookupValue.getValue();
            return lookupRangeInverted(range.getLowEndpoint(), false, range.getHighEndpoint(), false);
        }
        else if (lookupValue.getOperator() == QueryGraphRangeEnum.GREATER) {
            return lookupGreater(lookupValue.getValue());
        }
        else if (lookupValue.getOperator() == QueryGraphRangeEnum.GREATER_OR_EQUAL) {
            return lookupGreaterEqual(lookupValue.getValue());
        }
        else if (lookupValue.getOperator() == QueryGraphRangeEnum.LESS) {
            return lookupLess(lookupValue.getValue());
        }
        else if (lookupValue.getOperator() == QueryGraphRangeEnum.LESS_OR_EQUAL) {
            return lookupLessEqual(lookupValue.getValue());
        }
        else {
            throw new IllegalArgumentException("Unrecognized operator '" + lookupValue.getOperator() + "'");
        }
    }
}
