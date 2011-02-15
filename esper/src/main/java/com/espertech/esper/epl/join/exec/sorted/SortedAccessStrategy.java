package com.espertech.esper.epl.join.exec.sorted;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.join.table.PropertySortedEventTable;

import java.util.Collection;
import java.util.Set;

public interface SortedAccessStrategy {
    public Set<EventBean> lookup(EventBean event, PropertySortedEventTable index);
    public Collection<EventBean> lookup(EventBean[] eventsPerStream, PropertySortedEventTable index);
}
