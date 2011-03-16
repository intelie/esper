package com.espertech.esper.epl.join.exec.composite;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface CompositeAccessStrategy {
    public Set<EventBean> lookup(EventBean event, Map parent, Set<EventBean> result, CompositeIndexQuery next, ExprEvaluatorContext context);
    public Collection<EventBean> lookup(EventBean[] eventPerStream, Map parent, Collection<EventBean> result, CompositeIndexQuery next, ExprEvaluatorContext context);
}
