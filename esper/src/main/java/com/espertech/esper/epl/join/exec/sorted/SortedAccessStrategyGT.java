package com.espertech.esper.epl.join.exec.sorted;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.epl.join.table.PropertySortedEventTable;

import java.util.Collection;
import java.util.Set;

public class SortedAccessStrategyGT extends SortedAccessStrategyRelOpBase implements SortedAccessStrategy {

    public SortedAccessStrategyGT(EventPropertyGetter keyGetter, int keyStreamNum) {
        super(keyGetter, keyStreamNum);
    }

    public Set<EventBean> lookup(EventBean event, PropertySortedEventTable index) {
        return index.lookupGreater(keyGetter.get(event));
    }

    public Collection<EventBean> lookup(EventBean[] eventsPerStream, PropertySortedEventTable index) {
        return index.lookupGreaterColl(keyGetter.get(eventsPerStream[keyStreamNum]));
    }
}

