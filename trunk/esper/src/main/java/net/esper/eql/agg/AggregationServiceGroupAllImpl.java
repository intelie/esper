package net.esper.eql.agg;

import net.esper.collection.MultiKeyUntyped;
import net.esper.event.EventBean;
import net.esper.eql.expression.ExprEvaluator;
import net.esper.eql.agg.AggregationMethod;

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
    public AggregationServiceGroupAllImpl(ExprEvaluator evaluators[], AggregationMethod aggregators[])
    {
        super(evaluators, aggregators);
    }

    public void applyEnter(EventBean[] eventsPerStream, MultiKeyUntyped optionalGroupKeyPerRow)
    {
        for (int j = 0; j < evaluators.length; j++)
        {
            Object columnResult = evaluators[j].evaluate(eventsPerStream, true);
            aggregators[j].enter(columnResult);
        }
    }

    public void applyLeave(EventBean[] eventsPerStream, MultiKeyUntyped optionalGroupKeyPerRow)
    {
        for (int j = 0; j < evaluators.length; j++)
        {
            Object columnResult = evaluators[j].evaluate(eventsPerStream, false);
            aggregators[j].leave(columnResult);
        }
    }

    public void setCurrentRow(MultiKeyUntyped groupKey)
    {
        // no action needed - this implementation does not group and the current row is the single group
    }

    public Object getValue(int column)
    {
        return aggregators[column].getValue();
    }
}
