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
import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.join.exec.composite.CompositeIndexEnterRemove;
import com.espertech.esper.epl.join.exec.composite.CompositeIndexEnterRemoveKeyed;
import com.espertech.esper.epl.join.exec.composite.CompositeIndexEnterRemoveRange;
import com.espertech.esper.filter.FilterOperator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

/**
 * For use when the index comprises of either two or more ranges or a unique key in combination with a range.
 * Organizes into a TreeMap<key, TreeMap<key2, Set<EventBean>>, for short. The top level can also be just Map<MultiKeyUntyped, TreeMap...>.
 * Expected at least either (A) one key and one range or (B) zero keys and 2 ranges.
 * <p>
 * An alternative implementatation could have been based on "TreeMap<ComparableMultiKey, Set<EventBean>>>", however the following implication arrive
 * - not applicable for range-only lookups (since there the key can be the value itself
 * - not applicable for multiple nested range as ordering not nested
 * - each add/remove and lookup would also need to construct a key object.
 */
public class PropertyCompositeEventTable implements EventTable
{
    private final int streamNum;
    private final String[] optionalKeyedProps;
    private final String[] rangeProps;
    private final CompositeIndexEnterRemove chain;
    private final Class[] optKeyCoercedTypes;
    private final Class[] optRangeCoercedTypes;

    /**
     * Index table (sorted and/or keyed, always nested).
     */
    protected final Map<Object, Object> index;

    /**
     * Ctor.
     * @param streamNum - the stream number that is indexed
     * @param eventType - types of events indexed
     * @param optRangeCoercedTypes - property types
     */
    public PropertyCompositeEventTable(int streamNum, EventType eventType, String[] optionalKeyedProps, Class[] optKeyCoercedTypes, String[] rangeProps, Class[] optRangeCoercedTypes)
    {
        this.streamNum = streamNum;
        this.rangeProps = rangeProps;
        this.optionalKeyedProps = optionalKeyedProps;
        this.optKeyCoercedTypes = optKeyCoercedTypes;
        this.optRangeCoercedTypes = optRangeCoercedTypes;

        if (optionalKeyedProps != null && optionalKeyedProps.length > 0) {
            index = new HashMap<Object, Object>();
        }
        else {
            index = new TreeMap<Object, Object>();
        }

        // construct chain
        List<CompositeIndexEnterRemove> enterRemoves = new ArrayList<CompositeIndexEnterRemove>();
        if (optionalKeyedProps != null && optionalKeyedProps.length > 0) {
            enterRemoves.add(new CompositeIndexEnterRemoveKeyed(eventType, optionalKeyedProps, optKeyCoercedTypes));
        }
        int count = 0;
        for (String rangeProp : rangeProps) {
            Class coercionType = optRangeCoercedTypes == null ? null : optRangeCoercedTypes[count];
            enterRemoves.add(new CompositeIndexEnterRemoveRange(eventType, rangeProp, coercionType));
            count++;
        }

        // Hook up as chain for remove
        CompositeIndexEnterRemove last = null;
        for (CompositeIndexEnterRemove action : enterRemoves) {
            if (last != null) {
                last.setNext(action);
            }
            last = action;
        }
        chain = enterRemoves.get(0);
    }

    public Map<Object, Object> getIndex() {
        return index;
    }

    public Class[] getOptKeyCoercedTypes() {
        return optKeyCoercedTypes;
    }

    public Class[] getOptRangeCoercedTypes() {
        return optRangeCoercedTypes;
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

    private void add(EventBean event)
    {
        chain.enter(event, index);
    }

    private void remove(EventBean event)
    {
        chain.remove(event, index);
    }

    public boolean isEmpty()
    {
        return index.isEmpty();
    }

    public Iterator<EventBean> iterator()
    {
        HashSet<EventBean> result = new HashSet<EventBean>();
        chain.getAll(result, index);
        return result.iterator();
    }

    public void clear()
    {
        index.clear();
    }

    public String toString() {
        return toQueryPlan();
    }

    public String toQueryPlan()
    {
        return this.getClass().getName() +
                " streamNum=" + streamNum +
                " keys=" + Arrays.toString(optionalKeyedProps) +
                " ranges=" + Arrays.toString(rangeProps);
    }

    private static Log log = LogFactory.getLog(PropertyCompositeEventTable.class);
}
