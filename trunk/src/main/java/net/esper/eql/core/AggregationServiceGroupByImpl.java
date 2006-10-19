package net.esper.eql.core;

import net.esper.collection.MultiKey;
import net.esper.event.EventBean;
import net.esper.eql.core.AggregationServiceBase;
import net.esper.eql.expression.ExprEvaluator;

import java.util.Map;
import java.util.HashMap;

/**
 * Implementation for handling aggregation with grouping by group-keys.
 */
public class AggregationServiceGroupByImpl extends AggregationServiceBase
{
    // maintain for each group a row of aggregator states that the expression node canb pull the data from via index
    private Map<MultiKey, Aggregator[]> aggregatorsPerGroup;

    // maintain a current row for random access into the aggregator state table
    // (row=groups, columns=expression nodes that have aggregation functions)
    private Aggregator[] currentAggregatorRow;

    /**
     * Ctor.
     * @param evaluators - evaluate the sub-expression within the aggregate function (ie. sum(4*myNum))
     * @param aggregators - collect the aggregation state that evaluators evaluate to, act as factories for new
     * aggregation states for each group
     */
    public AggregationServiceGroupByImpl(ExprEvaluator evaluators[], Aggregator aggregators[])
    {
        super(evaluators, aggregators);

        this.aggregatorsPerGroup = new HashMap<MultiKey, Aggregator[]>();
    }

    public void applyEnter(EventBean[] eventsPerStream, MultiKey groupByKey)
    {
        Aggregator[] groupAggregators = aggregatorsPerGroup.get(groupByKey);

        // The aggregators for this group do not exist, need to create them from the prototypes
        if (groupAggregators == null)
        {
            groupAggregators = new Aggregator[this.aggregators.length];
            for (int j = 0; j < groupAggregators.length; j++)
            {
                groupAggregators[j] = aggregators[j].newAggregator();
            }
            aggregatorsPerGroup.put(groupByKey, groupAggregators);
        }

        // For this row, evaluate sub-expressions, enter result
        for (int j = 0; j < evaluators.length; j++)
        {
            Object columnResult = evaluators[j].evaluate(eventsPerStream);
            groupAggregators[j].enter(columnResult);
        }
    }

    public void applyLeave(EventBean[] eventsPerStream, MultiKey groupByKey)
    {
        Aggregator[] groupAggregators = aggregatorsPerGroup.get(groupByKey);

        // The aggregators for this group do not exist, need to create them from the prototypes
        if (groupAggregators == null)
        {
            groupAggregators = new Aggregator[this.aggregators.length];
            for (int j = 0; j < groupAggregators.length; j++)
            {
                groupAggregators[j] = aggregators[j].newAggregator();
            }
            aggregatorsPerGroup.put(groupByKey, groupAggregators);
        }

        // For this row, evaluate sub-expressions, enter result
        for (int j = 0; j < evaluators.length; j++)
        {
            Object columnResult = evaluators[j].evaluate(eventsPerStream);
            groupAggregators[j].leave(columnResult);
        }
    }

    public void setCurrentRow(MultiKey groupByKey)
    {
        currentAggregatorRow = aggregatorsPerGroup.get(groupByKey);

        if (currentAggregatorRow == null)
        {
            currentAggregatorRow = new Aggregator[this.aggregators.length];
            for (int j = 0; j < currentAggregatorRow.length; j++)
            {
                currentAggregatorRow[j] = aggregators[j].newAggregator();
            }
            aggregatorsPerGroup.put(groupByKey, currentAggregatorRow);
        }
    }

    public Object getValue(int column)
    {
        return currentAggregatorRow[column].getValue();
    }
}
