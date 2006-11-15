package net.esper.eql.core;

import net.esper.collection.MultiKey;
import net.esper.event.EventBean;
import net.esper.eql.core.AggregationServiceBase;
import net.esper.eql.expression.ExprEvaluator;

/**
 * Implementation for handling aggregation without any grouping (no group-by).
 */
public class AggregationServiceGroupAllImpl extends AggregationServiceBase
{
    /**
     * Ctor.
     * @param evaluators - evaluate the sub-expression within the aggregate function (ie. sum(4*myNum))
     * @param aggregators - collect the aggregation state that evaluators evaluate to
     */
    public AggregationServiceGroupAllImpl(ExprEvaluator evaluators[], Aggregator aggregators[])
    {
        super(evaluators, aggregators);
    }

    public void applyEnter(EventBean[] eventsPerStream, MultiKey optionalGroupKeyPerRow)
    {
        for (int j = 0; j < evaluators.length; j++)
        {
            Object columnResult = evaluators[j].evaluate(eventsPerStream);
            aggregators[j].enter(columnResult);
        }
    }

    public void applyLeave(EventBean[] eventsPerStream, MultiKey optionalGroupKeyPerRow)
    {
        for (int j = 0; j < evaluators.length; j++)
        {
            Object columnResult = evaluators[j].evaluate(eventsPerStream);
            aggregators[j].leave(columnResult);
        }
    }

    public void setCurrentRow(MultiKey groupKey)
    {
        // no action needed - this implementation does not group and the current row is the single group
    }

    public Object getValue(int column)
    {
        return aggregators[column].getValue();
    }
}
