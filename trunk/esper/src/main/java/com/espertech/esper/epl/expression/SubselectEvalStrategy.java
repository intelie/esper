package com.espertech.esper.epl.expression;

import com.espertech.esper.client.EventBean;

import java.util.Set;

public interface SubselectEvalStrategy
{
    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData, Set<EventBean> matchingEvents);
}
