package net.esper.eql.core;

import net.esper.eql.expression.ExprValidationException;
import net.esper.event.EventType;
import net.esper.event.EventBean;
import net.esper.core.StatementResultService;

public class SelectExprResultProcessor implements SelectExprProcessor
{
    private final StatementResultService statementResultService;
    private final SelectExprProcessor syntheticProcessor;
    private final BindProcessor bindProcessor;

    public SelectExprResultProcessor(StatementResultService statementResultService,
                                     SelectExprProcessor syntheticProcessor,
                                     BindProcessor bindProcessor)
            throws ExprValidationException
    {
        this.statementResultService = statementResultService;
        this.syntheticProcessor = syntheticProcessor;
        this.bindProcessor = bindProcessor;
    }

    public EventType getResultEventType()
    {
        return syntheticProcessor.getResultEventType();
    }

    public EventBean process(EventBean[] eventsPerStream, boolean isNewData, boolean isSynthesize)
    {
        if (isSynthesize)
        {
            return syntheticProcessor.process(eventsPerStream, isNewData, isSynthesize);
        }

        // TODO: can remove isSynthesize parameter from other select expr processors
        EventBean syntheticEvent = null;
        EventType syntheticEventType = null;
        if (statementResultService.isMakeSynthetic())
        {
            syntheticEvent = syntheticProcessor.process(eventsPerStream, isNewData, isSynthesize);

            if (!statementResultService.isMakeNatural())
            {
                return syntheticEvent;
            }

            syntheticEventType = syntheticProcessor.getResultEventType();
        }

        if (!statementResultService.isMakeNatural())
        {
            return null; // neither synthetic nor natural required, be cheap and generate no output event
        }

        Object[] parameters = bindProcessor.process(eventsPerStream, isNewData);
        return new NaturalEventBean(syntheticEventType, parameters, syntheticEvent);
    }
}
