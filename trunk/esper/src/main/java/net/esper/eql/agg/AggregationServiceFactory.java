package net.esper.eql.agg;

import net.esper.eql.expression.ExprAggregateNode;
import net.esper.eql.expression.ExprEvaluator;
import net.esper.eql.expression.ExprNode;
import net.esper.eql.agg.AggregationMethod;
import net.esper.eql.core.MethodResolutionService;
import net.esper.event.EventBean;

import java.util.LinkedList;
import java.util.List;

/**
 * Factory for aggregation service instances.
 */
public class AggregationServiceFactory
{
    /**
     * Returns an instance to handle the aggregation required by the aggregation expression nodes, depending on
     * whether there are any group-by nodes.
     * @param aggregateExprNodes - aggregation nodes extracted out of the select expression
     * @param hasGroupByClause - indicator on whethere there is group-by required, or group-all
     * @param optionalHavingNode - having node if having-clause was specified, or null if no having-clause given
     * @param sortByNodes - the nodes for the sort-by clause
     * @return instance for aggregation handling
     */
    public static AggregationService getService(List<ExprAggregateNode> aggregateExprNodes,
                                                boolean hasGroupByClause,
                                                ExprNode optionalHavingNode,
                                                List<ExprNode> sortByNodes,
                                                MethodResolutionService methodResolutionService)
    {
        // No aggregates used, we do not need this service
        if (aggregateExprNodes.isEmpty())
        {
        	return new AggregationServiceNull();
        }

        // Construct a list of evaluation node for the aggregation function.
        // For example "sum(2 * 3)" would make the sum an evaluation node.
        AggregationMethod aggregators[] = new AggregationMethod[aggregateExprNodes.size()];
        ExprEvaluator[] evaluators = new ExprEvaluator[aggregateExprNodes.size()];

        int index = 0;
        for (ExprAggregateNode aggregateNode : aggregateExprNodes)
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

        AggregationService service = null;
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

        // Inspect having clause for aggregation
        List<ExprAggregateNode> aggregateNodesHaving = new LinkedList<ExprAggregateNode>();
        if (optionalHavingNode != null)
        {
            ExprAggregateNode.getAggregatesBottomUp(optionalHavingNode, aggregateNodesHaving);
        }

        // Inspect sort-by clause for aggregation
        List<ExprAggregateNode> aggregateNodesSortBy = new LinkedList<ExprAggregateNode>();
        for(ExprNode node : sortByNodes)
        {
        	ExprAggregateNode.getAggregatesBottomUp(node, aggregateNodesSortBy);
        }

        // Hand a service reference to the aggregation nodes themselves.
        // Thus on expression evaluation time each aggregate node calls back to find out what the
        // group's state is (and thus does not evaluate by asking its child node for its result).
        int column = 0;
        for (ExprAggregateNode aggregateNode : aggregateExprNodes)
        {
            aggregateNode.setAggregationResultFuture(service, column);

            // Check any same aggregation nodes in the having clause
            for (ExprAggregateNode havingNode : aggregateNodesHaving)
            {
                if (ExprNode.deepEquals(havingNode, aggregateNode))
                {
                    havingNode.setAggregationResultFuture(service, column);
                }
            }

            // Check any same aggregation nodes in the sort-by clause
            for (ExprAggregateNode sortByNode : aggregateNodesSortBy)
            {
                if (ExprNode.deepEquals(sortByNode, aggregateNode))
                {
                    sortByNode.setAggregationResultFuture(service, column);
                }
            }

            column++;
        }

        return service;
    }
}
