package com.espertech.esper.epl.join.exec.composite;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.join.table.PropertyCompositeEventTable;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface CompositeIndexLookup {
    public void lookup(Map parent, Set<EventBean> result);
    public void setNext(CompositeIndexLookup action);
}
