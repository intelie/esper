package com.espertech.esper.epl.join.table.comp;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.collection.MultiKeyUntyped;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

public interface InnerIndexEnterRemove {

    public void enter(EventBean event, Map parent);
    public void setNext(InnerIndexEnterRemove next);
    public void remove(EventBean event, Map parent);
    public void getAll(HashSet<EventBean> result, Map parent);
}
