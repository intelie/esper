package com.espertech.esper.epl.join.exec.sorted;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.epl.join.table.PropertySortedEventTable;

import java.util.Collection;
import java.util.Set;

public class SortedAccessStrategyRangeInverted extends SortedAccessStrategyRangeBase implements SortedAccessStrategy {

    public SortedAccessStrategyRangeInverted(EventPropertyGetter start, boolean includeStart, EventPropertyGetter end, boolean includeEnd, int streamNumStart, int streamNumEnd) {
        super(start, includeStart, end, includeEnd, streamNumStart, streamNumEnd);
    }

    public Set<EventBean> lookup(EventBean event, PropertySortedEventTable index) {
        return index.lookupRangeInverted(start.get(event), includeStart, end.get(event), includeEnd);
    }

    public Collection<EventBean> lookup(EventBean[] eventsPerStream, PropertySortedEventTable index) {
        return index.lookupRangeInvertedColl(start.get(eventsPerStream[this.streamNumStart]), includeStart, end.get(eventsPerStream[this.streamNumEnd]), includeEnd);
    }
}

