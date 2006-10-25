package net.esper.eql.core;

import net.esper.collection.MultiKey;
import net.esper.event.EventBean;
import net.esper.eql.expression.ExprEvaluator;
import net.esper.persist.LogContextNode;
import net.esper.client.logstate.LogEntryType;

import java.util.Map;
import java.util.HashMap;

/**
 * Implementation for handling aggregation with grouping by group-keys.
 */
public class AggregationServiceGroupByImpl extends AggregationServiceBase
{
    // maintain for each group a row of aggregator states that the expression node canb pull the data from via index
    private LogContextNode<Map<MultiKey, Aggregator[]>> aggregatorsPerGroup;

    // maintain a current row for random access into the aggregator state table
    // (row=groups, columns=expression nodes that have aggregation functions)
    private Aggregator[] currentAggregatorRow;

    /**
     * Ctor.
     * @param evaluators - evaluate the sub-expression within the aggregate function (ie. sum(4*myNum))
     * @param aggregationState - collect the aggregation state that evaluators evaluate to, act as factories for new
     * aggregation states for each group
     */
    public AggregationServiceGroupByImpl(ExprEvaluator evaluators[], LogContextNode<Aggregator[]> aggregationState, LogContextNode<String> statementLogContext)
    {
        super(evaluators, aggregationState);

        Map<MultiKey, Aggregator[]> aggPerGroup = new HashMap<MultiKey, Aggregator[]>();
        this.aggregatorsPerGroup = statementLogContext.createChild(LogEntryType.GROUP_KEY_AGG_STATE, aggPerGroup);
    }

    public void preState()
    {
        // no action required
    }

    public void postState()
    {
        aggregatorsPerGroup.update();
    }

    public void applyEnter(EventBean[] eventsPerStream, MultiKey groupByKey)
    {
        Aggregator[] genericAggregators = aggregationState.getState();
        Aggregator[] groupAggregators = aggregatorsPerGroup.getState().get(groupByKey);

        // The aggregators for this group do not exist, need to create them from the prototypes
        if (groupAggregators == null)
        {
            groupAggregators = new Aggregator[genericAggregators.length];
            for (int j = 0; j < groupAggregators.length; j++)
            {
                groupAggregators[j] = genericAggregators[j].newAggregator();
            }
            aggregatorsPerGroup.getState().put(groupByKey, groupAggregators);
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
        Aggregator[] genericAggregators = aggregationState.getState();
        Aggregator[] groupAggregators = aggregatorsPerGroup.getState().get(groupByKey);

        // The aggregators for this group do not exist, need to create them from the prototypes
        if (groupAggregators == null)
        {
            groupAggregators = new Aggregator[genericAggregators.length];
            for (int j = 0; j < groupAggregators.length; j++)
            {
                groupAggregators[j] = genericAggregators[j].newAggregator();
            }
            aggregatorsPerGroup.getState().put(groupByKey, groupAggregators);
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
        Aggregator[] genericAggregators = aggregationState.getState();
        currentAggregatorRow = aggregatorsPerGroup.getState().get(groupByKey);

        if (currentAggregatorRow == null)
        {
            currentAggregatorRow = new Aggregator[genericAggregators.length];
            for (int j = 0; j < currentAggregatorRow.length; j++)
            {
                currentAggregatorRow[j] = genericAggregators[j].newAggregator();
            }
            aggregatorsPerGroup.getState().put(groupByKey, currentAggregatorRow);
        }
    }

    public Object getValue(int column)
    {
        return currentAggregatorRow[column].getValue();
    }
}
