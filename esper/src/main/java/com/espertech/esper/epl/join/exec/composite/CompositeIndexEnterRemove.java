package com.espertech.esper.epl.join.exec.composite;

import com.espertech.esper.client.EventBean;

import java.util.HashSet;
import java.util.Map;

public interface CompositeIndexEnterRemove {

    public void enter(EventBean event, Map parent);
    public void setNext(CompositeIndexEnterRemove next);
    public void remove(EventBean event, Map parent);
    public void getAll(HashSet<EventBean> result, Map parent);
}
