package com.espertech.esper.eql.agg;

import com.espertech.esper.eql.core.MethodResolutionService;
import com.espertech.esper.eql.expression.ExprAggregateNode;
import com.espertech.esper.eql.expression.ExprEvaluator;
import com.espertech.esper.eql.expression.ExprNode;
import com.espertech.esper.event.EventBean;

import java.util.*;

/**
 * Factory for aggregation service instances.
 * <p>
 * Consolidates aggregation nodes such that result futures point to a single instance and
 * no re-evaluation of the same result occurs.
 */
public class AggregationServiceFactory
{
    /**
     * Returns an instance to handle the aggregation required by the aggregation expression nodes, depending on
     * whether there are any group-by nodes.
     * @param selectAggregateExprNodes - aggregation nodes extracted out of the select expression
     * @param havingAggregateExprNodes - aggregation nodes extracted out of the select expression
     * @param orderByAggregateExprNodes - aggregation nodes extracted out of the select expression
     * @param hasGroupByClause - indicator on whethere there is group-by required, or group-all
     * @param methodResolutionService - is required to resolve aggregation methods
     * @return instance for aggregation handling
     */
    public static AggregationService getService(List<ExprAggregateNode> selectAggregateExprNodes,
                                                List<ExprAggregateNode> havingAggregateExprNodes,
                                                List<ExprAggregateNode> orderByAggregateExprNodes,
                                                boolean hasGroupByClause,
                                                MethodResolutionService methodResolutionService)
    {
        // No aggregates used, we do not need this service
        if ((selectAggregateExprNodes.isEmpty()) && (havingAggregateExprNodes.isEmpty()))
        {
        	return new AggregationServiceNull();
        }

        // Compile a map of aggregation nodes and equivalent-to aggregation nodes.
        // Equivalent-to functions are for example "select sum(a*b), 5*sum(a*b)".
        // Reducing the total number of aggregation functions.
        Map<ExprAggregateNode, List<ExprAggregateNode>> equivalencyList = new LinkedHashMap<ExprAggregateNode, List<ExprAggregateNode>>();
        for (ExprAggregateNode selectAggNode : selectAggregateExprNodes)
        {
            addEquivalent(selectAggNode, equivalencyList);
        }
        for (ExprAggregateNode havingAggNode : havingAggregateExprNodes)
        {
            addEquivalent(havingAggNode, equivalencyList);
        }
        for (ExprAggregateNode orderByAggNode : orderByAggregateExprNodes)
        {
            addEquivalent(orderByAggNode, equivalencyList);
        }

        // Construct a list of evaluation node for the aggregation function.
        // For example "sum(2 * 3)" would make the sum an evaluation node.
        AggregationMethod aggregators[] = new AggregationMethod[equivalencyList.size()];
        ExprEvaluator[] evaluators = new ExprEvaluator[equivalencyList.size()];

        int index = 0;
        for (ExprAggregateNode aggregateNode : equivalencyList.keySet())
        {
            if (aggregateNode.getChildNodes().size() > 1)
            {
                throw new IllegalStateException("Aggregate node is expected to have at most a single child node");
            }

            // Use the evaluation node under the aggregation node to obtain the aggregation value
            if (!aggregateNode.getChildNodes().isEmpty())
            {
                evaluators[index] = aggregateNode.getChildNodes().get(0);
            }
            // For aggregation that doesn't evaluate any particular sub-expression, return null on evaluation
            else
            {
                evaluators[index] = new ExprEvaluator() {
                    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData)
                    {
                        return null;
                    }
                };
            }

            aggregators[index] = aggregateNode.getPrototypeAggregator();
            index++;
        }

        AggregationService service;
        if (hasGroupByClause)
        {
            // If there is a group-by clause, then we need to keep aggregators as prototypes
            service = new AggregationServiceGroupByImpl(evaluators, aggregators, methodResolutionService);
        }
        else
        {
            // Without a group-by clause we group all into the same pot, using one set of aggregators
            service = new AggregationServiceGroupAllImpl(evaluators, aggregators);
        }

        // Hand a service reference to the aggregation nodes themselves.
        // Thus on expression evaluation time each aggregate node calls back to find out what the
        // group's state is (and thus does not evaluate by asking its child node for its result).
        int column = 0;
        for (ExprAggregateNode aggregateNode : equivalencyList.keySet())
        {
            aggregateNode.setAggregationResultFuture(service, column);

            // hand to all equivalent-to 
            List<ExprAggregateNode> equivalentAggregators = equivalencyList.get(aggregateNode);
            if (equivalentAggregators != null)
            {
                for (ExprAggregateNode equivalentAggNode : equivalentAggregators)
                {
                    equivalentAggNode.setAggregationResultFuture(service, column);
                }
            }

            column++;
        }

        return service;
    }

    private static void addEquivalent(ExprAggregateNode aggNodeToAdd, Map<ExprAggregateNode, List<ExprAggregateNode>> equivalencyList)
    {
        // Check any same aggregation nodes among all aggregation clauses
        boolean foundEquivalent = false;
        for (Map.Entry<ExprAggregateNode, List<ExprAggregateNode>> entry : equivalencyList.entrySet())
        {
            ExprAggregateNode aggNode = entry.getKey();
            if (ExprNode.deepEquals(aggNode, aggNodeToAdd))
            {
                List<ExprAggregateNode> equivalentAggregators = entry.getValue();
                if (equivalentAggregators == null)
                {
                    equivalentAggregators = new ArrayList<ExprAggregateNode>();
                }
                equivalentAggregators.add(aggNodeToAdd);
                equivalencyList.put(aggNode, equivalentAggregators);
                foundEquivalent = true;
                break;
            }
        }

        if (!foundEquivalent)
        {
            equivalencyList.put(aggNodeToAdd, null);
        }        
    }

}
