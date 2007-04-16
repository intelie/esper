using System;
using System.Collections.Generic;

using net.esper.collection;
using net.esper.compat;
using net.esper.events;
using net.esper.eql;
using net.esper.eql.spec;
using net.esper.eql.expression;

using Log = org.apache.commons.logging.Log;
using LogFactory = org.apache.commons.logging.LogFactory;

namespace net.esper.eql.core
{
    /// <summary>
    /// Factory for <seealso cref="net.esper.eql.core.OrderByProcessor"/> processors.
    /// </summary>

    public class OrderByProcessorFactory
    {
        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);

        /// <summary>Returns processor for order-by clauses.</summary>
        /// <param name="selectionList">is a list of select expressions</param>
        /// <param name="groupByNodes">is a list of group-by expressions</param>
        /// <param name="orderByList">is a list of order-by expressions</param>
        /// <param name="aggregationService">is the service for aggregation, ie. building sums and averages per group or overall</param>
        /// <param name="eventAdapterService">provides event adapters</param>
        /// <returns>ordering processor instance</returns>
        /// <throws>net.esper.eql.expression.ExprValidationException when validation of expressions fails</throws>

        public static OrderByProcessor GetProcessor(IList<SelectExprElementNamedSpec> selectionList,
                                                   IList<ExprNode> groupByNodes,
                                                   IList<Pair<ExprNode, Boolean>> orderByList,
                                                   AggregationService aggregationService,
                                                   EventAdapterService eventAdapterService)
        {
            // Get the order by expression nodes
            IList<ExprNode> orderByNodes = new List<ExprNode>();
            foreach (Pair<ExprNode, Boolean> element in orderByList)
            {
                orderByNodes.Add(element.First);
            }

            // No order-by clause
            if (orderByList.Count == 0)
            {
                log.Debug(".GetProcessor Using no OrderByProcessor");
                return null;
            }

            // Determine aggregate functions used in select, if any
            IList<ExprAggregateNode> selectAggNodes = new List<ExprAggregateNode>();
            foreach (SelectExprElementNamedSpec element in selectionList)
            {
                ExprAggregateNode.GetAggregatesBottomUp(element.SelectExpression, selectAggNodes);
            }

            // Get all the aggregate functions occuring in the order-by clause
            IList<ExprAggregateNode> orderAggNodes = new List<ExprAggregateNode>();
            foreach (ExprNode orderByNode in orderByNodes)
            {
                ExprAggregateNode.GetAggregatesBottomUp(orderByNode, orderAggNodes);
            }

            ValidateOrderByAggregates(selectAggNodes, orderAggNodes);

            // Create the type of the order-by event
            EDictionary<String, Type> propertyNamesAndTypes = new EHashDictionary<String, Type>();
            foreach (ExprNode orderByNode in orderByNodes)
            {
                propertyNamesAndTypes[orderByNode.ExpressionString] = orderByNode.GetType();
            }
            EventType orderType = eventAdapterService.CreateAnonymousMapType(propertyNamesAndTypes);

            // Tell the order-by processor whether to compute group-by
            // keys if they are not present
            Boolean needsGroupByKeys = (selectionList.Count != 0) && (orderAggNodes.Count != 0);

            log.Debug(".GetProcessor Using OrderByProcessorSimple");
            return new OrderByProcessorSimple(orderByList, groupByNodes, needsGroupByKeys, aggregationService);
        }

        private static void ValidateOrderByAggregates(
            IList<ExprAggregateNode> selectAggNodes,
            IList<ExprAggregateNode> orderAggNodes)
        {
            // Check that the order-by clause doesn't contain 
            // any aggregate functions not in the select expression
            foreach (ExprAggregateNode orderAgg in orderAggNodes)
            {
                Boolean inSelect = false;
                foreach (ExprAggregateNode selectAgg in selectAggNodes)
                {
                    if (ExprNode.DeepEquals(selectAgg, orderAgg))
                    {
                        inSelect = true;
                        break;
                    }
                }
                if (!inSelect)
                {
                    throw new ExprValidationException("Aggregate functions in the order-by clause must also occur in the select expression");
                }
            }
        }
    }
}