package com.espertech.esper.epl.join.exec.composite;

import com.espertech.esper.client.EventBean;

import java.util.Map;
import java.util.Set;

public interface InnerIndexQuery {
    public void add(EventBean event, Map value, Set<EventBean> result);
    public Set<EventBean> get(EventBean event, Map parent);
    public void setNext(InnerIndexQuery next);

}
