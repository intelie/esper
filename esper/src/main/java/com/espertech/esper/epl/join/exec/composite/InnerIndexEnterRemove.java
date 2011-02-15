package com.espertech.esper.epl.join.exec.composite;

import com.espertech.esper.client.EventBean;

import java.util.HashSet;
import java.util.Map;

public interface InnerIndexEnterRemove {

    public void enter(EventBean event, Map parent);
    public void setNext(InnerIndexEnterRemove next);
    public void remove(EventBean event, Map parent);
    public void getAll(HashSet<EventBean> result, Map parent);
}
