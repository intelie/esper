package com.espertech.esper.epl.join.exec.sorted;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.epl.join.table.PropertySortedEventTable;

import java.util.Collection;
import java.util.Set;

public class SortedAccessStrategyGE extends SortedAccessStrategyRelOpBase implements SortedAccessStrategy {

    public SortedAccessStrategyGE(EventPropertyGetter key, int keyStreamNum) {
        super(key, keyStreamNum);
    }

    public Set<EventBean> lookup(EventBean event, PropertySortedEventTable index) {
        return index.lookupGreaterEqual(keyGetter.get(event));
    }

    public Collection<EventBean> lookup(EventBean[] eventsPerStream, PropertySortedEventTable index) {
        return index.lookupGreaterEqualColl(keyGetter.get(eventsPerStream[keyStreamNum]));
    }
}
