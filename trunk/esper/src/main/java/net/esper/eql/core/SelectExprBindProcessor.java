package net.esper.eql.core;

import net.esper.eql.expression.ExprValidationException;
import net.esper.event.EventBean;
import net.esper.event.EventType;

public class SelectExprBindProcessor implements SelectExprProcessor
{
    private final SelectExprProcessor syntheticProcessor;
    private final BindStrategy bindStrategy;

    public SelectExprBindProcessor(SelectExprProcessor syntheticProcessor,
                                   BindStrategy bindStrategy)
            throws ExprValidationException
    {
        this.syntheticProcessor = syntheticProcessor;
        this.bindStrategy = bindStrategy;
    }

    public EventType getResultEventType()
    {
        return syntheticProcessor.getResultEventType();
    }

    public EventBean process(EventBean[] eventsPerStream, boolean isNewData, boolean isSynthesize)
    {
        Object[] parameters = bindStrategy.process(eventsPerStream, isNewData);

        EventBean syntheticEvent = null;
        EventType syntheticEventType = null;
        if (isSynthesize)
        {
            syntheticEvent = syntheticProcessor.process(eventsPerStream, isNewData, isSynthesize);
            syntheticEventType = syntheticEvent.getEventType();
        }

        return new NaturalEventBean(syntheticEventType, parameters, syntheticEvent);
    }
}
