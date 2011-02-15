package com.espertech.esper.epl.join.exec.sorted;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.epl.join.table.PropertySortedEventTable;

import java.util.Collection;
import java.util.Set;

public class SortedAccessStrategyRange extends SortedAccessStrategyRangeBase implements SortedAccessStrategy {

    // indicate whether "a between 60 and 50" should return no results (false, equivalent to a>= X and a <=Y) or should return results (true, equivalent to 'between' and 'in')  
    private final boolean allowRangeReversal;

    public SortedAccessStrategyRange(EventPropertyGetter start, boolean includeStart, EventPropertyGetter end, boolean includeEnd, int streamNumStart, int streamNumEnd, boolean allowRangeReversal) {
        super(start, includeStart, end, includeEnd, streamNumStart, streamNumEnd);
        this.allowRangeReversal = allowRangeReversal;
    }

    public Set<EventBean> lookup(EventBean event, PropertySortedEventTable index) {
        return index.lookupRange(start.get(event), includeStart, end.get(event), includeEnd, allowRangeReversal);
    }

    public Collection<EventBean> lookup(EventBean[] eventsPerStream, PropertySortedEventTable index) {
        return index.lookupRangeColl(start.get(eventsPerStream[streamNumStart]), includeStart, end.get(eventsPerStream[streamNumEnd]), includeEnd, allowRangeReversal);
    }
}
