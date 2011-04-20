package com.espertech.esper.epl.core.eval;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.core.SelectExprProcessor;
import com.espertech.esper.event.MappedEventBean;

public class EvalSelectStreamWUnderlyingRecast implements SelectExprProcessor {

    private final SelectExprContext selectExprContext;
    private final int underlyingStreamNumber;
    private final EventType resultType;

    public EvalSelectStreamWUnderlyingRecast(SelectExprContext selectExprContext, int underlyingStreamNumber, EventType resultType) {
        this.selectExprContext = selectExprContext;
        this.underlyingStreamNumber = underlyingStreamNumber;
        this.resultType = resultType;
    }

    public EventType getResultEventType() {
        return resultType;
    }

    public EventBean process(EventBean[] eventsPerStream, boolean isNewData, boolean isSynthesize) {
        MappedEventBean event = (MappedEventBean) eventsPerStream[underlyingStreamNumber];
        return selectExprContext.getEventAdapterService().adaptorForTypedMap(event.getProperties(), resultType);
    }
}
