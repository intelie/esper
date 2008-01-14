package net.esper.eql.core;

import net.esper.event.EventType;
import net.esper.event.EventAdapterService;
import net.esper.event.EventAdapterException;
import net.esper.event.EventBean;
import net.esper.eql.spec.InsertIntoDesc;
import net.esper.eql.expression.ExprValidationException;

import java.util.Map;
import java.util.HashMap;

/**
 * Processor for select-clause expressions that handles wildcards for single streams with no insert-into.
 */
public class SelectExprWildcardProcessor implements SelectExprProcessor
{
    private final EventType eventType;

    /**
     * Ctor.
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
