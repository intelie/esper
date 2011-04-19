package com.espertech.esper.epl.join.exec.composite;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface CompositeIndexQuery {
    public void add(EventBean event, Map value, Set<EventBean> result);
    public void add(EventBean[] eventsPerStream, Map value, Collection<EventBean> result);
    public Set<EventBean> get(EventBean event, Map parent, ExprEvaluatorContext context);
    public Collection<EventBean> get(EventBean eventsPerStream[], Map parent, ExprEvaluatorContext context);
    public void setNext(CompositeIndexQuery next);
}
