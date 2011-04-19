package com.espertech.esper.epl.join.exec.composite;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.client.EventType;
import com.espertech.esper.collection.MultiKeyUntyped;
import com.espertech.esper.epl.join.table.PropertyCompositeEventTable;
import com.espertech.esper.event.EventBeanUtility;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class CompositeIndexLookupKeyed implements CompositeIndexLookup {

    private final Object[] keys;
    private CompositeIndexLookup next;

    public CompositeIndexLookupKeyed(Object[] keys) {
        this.keys = keys;
    }

    public void setNext(CompositeIndexLookup next) {
        this.next = next;
    }

    public void lookup(Map parent, Set<EventBean> result) {
        MultiKeyUntyped mk = new MultiKeyUntyped(keys);
        Map innerIndex = (Map) parent.get(mk);
        if (innerIndex == null) {
            return;
        }
        next.lookup(innerIndex, result);        
    }
}
