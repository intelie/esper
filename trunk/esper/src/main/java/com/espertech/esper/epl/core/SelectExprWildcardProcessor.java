package com.espertech.esper.epl.core;

import com.espertech.esper.epl.expression.ExprValidationException;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.EventType;

/**
 * Processor for select-clause expressions that handles wildcards for single streams with no insert-into.
 */
public class SelectExprWildcardProcessor implements SelectExprProcessor
{
    private final EventType eventType;

    /**
     * Ctor.
     * @param eventType is the type of event this processor produces
     * @throws com.espertech.esper.epl.expression.ExprValidationException if the expression validation failed
     */
    public SelectExprWildcardProcessor(EventType eventType) throws ExprValidationException
    {
        this.eventType = eventType;
    }

    public EventBean process(EventBean[] eventsPerStream, boolean isNewData, boolean isSynthesize)
    {
        return eventsPerStream[0];
    }

    public EventType getResultEventType()
    {
        return eventType;
    }
}
