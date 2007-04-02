using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.events;
using net.esper.eql.expression;

namespace net.esper.eql.core
{
	/// <summary>
    /// Factory for aggregation service instances.
    /// </summary>

    public class AggregationServiceFactory
    {
        /// <summary>
        /// Returns an instance to handle the aggregation required by the aggregation expression nodes, depending on
        /// whether there are any group-by nodes.
        /// </summary>
        /// <param name="aggregateExprNodes">aggregation nodes extracted out of the select expression</param>
        /// <param name="hasGroupByClause">indicator on whethere there is group-by required, or group-all</param>
        /// <param name="optionalHavingNode">having node if having-clause was specified, or null if no having-clause given</param>
        /// <param name="sortByNodes">the nodes for the sort-by clause</param>
        /// <returns>instance for aggregation handling</returns>

        public static AggregationService GetService(IList<ExprAggregateNode> aggregateExprNodes,
                                                    Boolean hasGroupByClause,
                                                    ExprNode optionalHavingNode,
                                                    IList<ExprNode> sortByNodes)
        {
            // No aggregates used, we do not need this service
            if (aggregateExprNodes.Count == 0)
            {
                return new AggregationServiceNull();
            }

            // Construct a list of evaluation node for the aggregation function.
            // For example "sum(2 * 3)" would make the sum an evaluation node.
            Aggregator[] aggregators = new Aggregator[aggregateExprNodes.Count];
            ExprEvaluator[] evaluators = new ExprEvaluator[aggregateExprNodes.Count];

            int index = 0;
            foreach (ExprAggregateNode aggregateNode in aggregateExprNodes)
            {
                if (aggregateNode.ChildNodes.Count > 1)
                {
                    throw new IllegalStateException("Aggregate node is expected to have at most a single child node");
                }

                // Use the evaluation node under the aggregation node to obtain the aggregation value
                if (aggregateNode.ChildNodes.Count > 0)
                {
                	evaluators[index] = aggregateNode.ChildNodes[0];
                }
                // For aggregation that doesn't evaluate any particular sub-expression, return null on evaluation
                else
                {
                	evaluators[index] = new ExprEvaluatorNull() ;
                }

                aggregators[index] = aggregateNode.PrototypeAggregator;
                index++;
            }

            AggregationService service = null;
            if (hasGroupByClause)
            {
                // If there is a group-by clause, then we need to keep aggregators as prototypes
                service = new AggregationServiceGroupByImpl(evaluators, aggregators);
            }
            else
            {
                // Without a group-by clause we group all into the same pot, using one set of aggregators
                service = new AggregationServiceGroupAllImpl(evaluators, aggregators);
            }

            // Inspect having clause for aggregation
            IList<ExprAggregateNode> aggregateNodesHaving = new List<ExprAggregateNode>();
            if (optionalHavingNode != null)
            {
                ExprAggregateNode.GetAggregatesBottomUp(optionalHavingNode, aggregateNodesHaving);
            }

            // Inspect sort-by clause for aggregation
            IList<ExprAggregateNode> aggregateNodesSortBy = new List<ExprAggregateNode>();
            foreach (ExprNode node in sortByNodes)
            {
                ExprAggregateNode.GetAggregatesBottomUp(node, aggregateNodesSortBy);
            }

            // Hand a service reference to the aggregation nodes themselves.
            // Thus on expression evaluation time each aggregate node calls back to find out what the
            // group's state is (and thus does not evaluate by asking its child node for its result).
            int column = 0;
            foreach (ExprAggregateNode aggregateNode in aggregateExprNodes)
            {
                aggregateNode.SetAggregationResultFuture(service, column);

                // Check any same aggregation nodes in the having clause
                foreach (ExprAggregateNode havingNode in aggregateNodesHaving)
                {
                    if (ExprNode.DeepEquals(havingNode, aggregateNode))
                    {
                        havingNode.SetAggregationResultFuture(service, column);
                    }
                }

                // Check any same aggregation nodes in the sort-by clause
                foreach (ExprAggregateNode sortByNode in aggregateNodesSortBy)
                {
                    if (ExprNode.DeepEquals(sortByNode, aggregateNode))
                    {
                        sortByNode.SetAggregationResultFuture(service, column);
                    }
                }

                column++;
            }

            return service;
        }
    }
}
