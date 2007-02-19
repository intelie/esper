package net.esper.eql.expression;

import net.esper.event.EventBean;

/**
 * Interface for evaluating of an event tuple.
 */
public interface ExprEvaluator
{
    /**
     * Evaluate event tuple and return result.
     * @param eventsPerStream - event tuple
     * @param isNewData - indicates whether we are dealing with new data (istream) or old data (rstream)
     * @return evaluation result, a boolean value for OR/AND-type evalution nodes.
     */
    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData);
}
