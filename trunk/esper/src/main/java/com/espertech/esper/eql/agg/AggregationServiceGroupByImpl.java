package com.espertech.esper.eql.agg;

import com.espertech.esper.collection.MultiKeyUntyped;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.eql.expression.ExprEvaluator;
import com.espertech.esper.eql.core.MethodResolutionService;

import java.util.Map;
import java.util.HashMap;

/**
 * Implementation for handling aggregation with grouping by group-keys.
 */
public class AggregationServiceGroupByImpl extends AggregationServiceBase
{
    // maintain for each group a row of aggregator states that the expression node canb pull the data from via index
    private Map<MultiKeyUntyped, AggregationMethod[]> aggregatorsPerGroup;

    // maintain a current row for random access into the aggregator state table
    // (row=groups, columns=expression nodes that have aggregation functions)
    private AggregationMethod[] currentAggregatorRow;

    private MethodResolutionService methodResolutionService;

    /**
     * Ctor.
     * @param evaluators - evaluate the sub-expression within the aggregate function (ie. sum(4*myNum))
     * @param prototypes - collect the aggregation state that evaluators evaluate to, act as prototypes for new aggregations
     * aggregation states for each group
     * @param methodResolutionService - factory for creating additional aggregation method instances per group key
     */
    public AggregationServiceGroupByImpl(ExprEvaluator evaluators[], AggregationMethod prototypes[], MethodResolutionService methodResolutionService)
    {
        super(evaluators, prototypes);
        this.methodResolutionService = methodResolutionService;
        this.aggregatorsPerGroup = new HashMap<MultiKeyUntyped, AggregationMethod[]>();
    }

    public void clearResults()
    {
        aggregatorsPerGroup.clear();
    }

    public void applyEnter(EventBean[] eventsPerStream, MultiKeyUntyped groupByKey)
    {
        AggregationMethod[] groupAggregators = aggregatorsPerGroup.get(groupByKey);

        // The aggregators for this group do not exist, need to create them from the prototypes
        if (groupAggregators == null)
        {
            groupAggregators = methodResolutionService.newAggregators(aggregators, groupByKey);
            aggregatorsPerGroup.put(groupByKey, groupAggregators);
        }

        // For this row, evaluate sub-expressions, enter result
        for (int j = 0; j < evaluators.length; j++)
        {
            Object columnResult = evaluators[j].evaluate(eventsPerStream, true);
            groupAggregators[j].enter(columnResult);
        }
    }

    public void applyLeave(EventBean[] eventsPerStream, MultiKeyUntyped groupByKey)
    {
        AggregationMethod[] groupAggregators = aggregatorsPerGroup.get(groupByKey);

        // The aggregators for this group do not exist, need to create them from the prototypes
        if (groupAggregators == null)
        {
            groupAggregators = methodResolutionService.newAggregators(aggregators, groupByKey);
            aggregatorsPerGroup.put(groupByKey, groupAggregators);
        }

        // For this row, evaluate sub-expressions, enter result
        for (int j = 0; j < evaluators.length; j++)
        {
            Object columnResult = evaluators[j].evaluate(eventsPerStream, false);
            groupAggregators[j].leave(columnResult);
        }
    }

    public void setCurrentRow(MultiKeyUntyped groupByKey)
    {
        currentAggregatorRow = aggregatorsPerGroup.get(groupByKey);

        if (currentAggregatorRow == null)
        {
            currentAggregatorRow = methodResolutionService.newAggregators(aggregators, groupByKey);
            aggregatorsPerGroup.put(groupByKey, currentAggregatorRow);
        }
    }

    public Object getValue(int column)
    {
        return currentAggregatorRow[column].getValue();
    }
}
