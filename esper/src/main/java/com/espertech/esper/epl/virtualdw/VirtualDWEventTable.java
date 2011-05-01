package com.espertech.esper.epl.virtualdw;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.hook.VirtualDataWindowLookupFieldDesc;
import com.espertech.esper.epl.join.table.EventTable;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class VirtualDWEventTable implements EventTable
{
    private List<VirtualDataWindowLookupFieldDesc> hashAccess;
    private List<VirtualDataWindowLookupFieldDesc> btreeAccess;

    public VirtualDWEventTable(List<VirtualDataWindowLookupFieldDesc> hashAccess, List<VirtualDataWindowLookupFieldDesc> btreeAccess) {
        this.hashAccess = Collections.unmodifiableList(hashAccess);
        this.btreeAccess = Collections.unmodifiableList(btreeAccess);
    }

    public void add(EventBean[] events) {
    }

    public void remove(EventBean[] events) {
    }

    public Iterator<EventBean> iterator() {
        return Collections.<EventBean>emptyList().iterator();
    }

    public boolean isEmpty() {
        return true;
    }

    public void clear() {
    }

    public String toQueryPlan() {
        return "(external event table)";
    }

    public List<VirtualDataWindowLookupFieldDesc> getHashAccess() {
        return hashAccess;
    }

    public List<VirtualDataWindowLookupFieldDesc> getBtreeAccess() {
        return btreeAccess;
    }
}
