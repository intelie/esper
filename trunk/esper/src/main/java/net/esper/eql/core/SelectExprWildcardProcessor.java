package net.esper.eql.core;

import net.esper.eql.expression.ExprValidationException;
import net.esper.event.EventBean;
import net.esper.event.EventType;

/**
 * Processor for select-clause expressions that handles wildcards for single streams with no insert-into.
 */
public class SelectExprWildcardProcessor implements SelectExprProcessor
{
    private final EventType eventType;

    /**
     * Ctor.
     * @param eventType is the type of event this processor produces
     * @throws net.esper.eql.expression.ExprValidationException if the expression validation failed
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
