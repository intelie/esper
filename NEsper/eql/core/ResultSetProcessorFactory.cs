using System;
using System.Collections.Generic;

using net.esper.collection;
using net.esper.compat;
using net.esper.eql.expression;
using net.esper.eql.spec;
using net.esper.events;

using org.apache.commons.logging;

namespace net.esper.eql.core
{
    /// <summary>
    /// Factory for output processors. Output processors process the result set of a join or of a view
    /// and apply aggregation/grouping, having and some output limiting logic.
    /// <para>
    /// The instance produced by the factory depends on the presence of aggregation functions in the select list,
    /// the presence and nature of the group-by clause.
    /// </para>
    /// <para>
    /// In case (1) and (2) there are no aggregation functions in the select clause.
    /// </para>
    /// <para>
    /// Case (3) is without group-by and with aggregation functions and without non-aggregated properties
    /// in the select list: <code>select sum(volume)</code>.
    /// Always produces one row for new and old data, aggregates without grouping.
    /// </para>
    /// <para>
    /// Case (4) is without group-by and with aggregation functions but with non-aggregated properties
    /// in the select list: <code>select price, sum(volume)</code>.
    /// Produces a row for each event, aggregates without grouping.
    /// </para>
    /// <para>
    /// Case (5) is with group-by and with aggregation functions and all selected properties are grouped-by.
    /// in the select list: <code>select customerId, sum(volume) group by customerId</code>.
    /// Produces a old and new data row for each group changed, aggregates with grouping, see
    /// <seealso cref="ResultSetProcessorRowGroup"/>
    /// </para>
    /// <para>
    /// Case (6) is with group-by and with aggregation functions and only some selected properties are grouped-by.
    /// in the select list: <code>select customerId, supplierId, sum(volume) group by customerId</code>.
    /// Produces row for each event, aggregates with grouping.
    /// </para>
    /// </summary>

    public class ResultSetProcessorFactory
    {
        /// <summary>
        /// Returns the result set process for the given select expression, group-by clause and
        /// having clause given a set of types describing each stream in the from-clause.
        /// </summary>
        /// <param name="selectionList">represents select clause and thus the expression nodes listed in the select, or empty if wildcard
        /// </param>
        /// <param name="groupByNodes">represents the expressions to group-by events based on event properties, or empty if no group-by was specified
        /// </param>
        /// <param name="optionalHavingNode">represents the having-clause boolean filter criteria
        /// </param>
        /// <param name="outputLimitSpec">indicates whether to output all or only the last event
        /// </param>
        /// <param name="orderByList">represent the expressions in the order-by clause
        /// </param>
        /// <param name="typeService">for information about the streams in the from clause
        /// </param>
        /// <param name="insertIntoDesc">descriptor for insert-into clause information
        /// </param>
        /// <param name="eventAdapterService">wrapping service for events
        /// </param>
        /// <param name="autoImportService">for resolving class names
        /// </param>
        /// <returns> result set processor instance
        /// </returns>
        /// <throws>  net.esper.eql.expression.ExprValidationException </throws>
        public static ResultSetProcessor GetProcessor(IList<SelectExprElementUnnamedSpec> selectionList,
                                                      InsertIntoDesc insertIntoDesc,
                                                      IList<ExprNode> groupByNodes,
                                                      ExprNode optionalHavingNode,
                                                      OutputLimitSpec outputLimitSpec,
                                                      IList<Pair<ExprNode, Boolean>> orderByList,
                                                      StreamTypeService typeService,
                                                      EventAdapterService eventAdapterService,
                                                      AutoImportService autoImportService)
        {
            if (log.IsDebugEnabled)
            {
                log.Debug(".GetProcessor Getting processor for " +
            	          " selectionList=" + CollectionHelper.Render( selectionList ) +
            	          " groupByNodes=" + CollectionHelper.Render( groupByNodes ) +
            	          " optionalHavingNode=" + optionalHavingNode);
            }

            // Expand any instances of select-clause aliases in the
            // order-by clause with the full expression
            ExpandAliases(selectionList, orderByList);

            // Validate selection expressions, if any (could be wildcard i.e. empty list)
            IList<SelectExprElementNamedSpec> namedSelectionList = new List<SelectExprElementNamedSpec>();
            for (int i = 0; i < selectionList.Count; i++)
            {
                // validate element
                SelectExprElementUnnamedSpec element = selectionList[i];
                ExprNode validatedExpression = element.SelectExpression.GetValidatedSubtree(typeService, autoImportService);

                // determine an element name if none assigned
                String asName = element.OptionalAsName;
                if (asName == null)
                {
                    asName = validatedExpression.ExpressionString;
                }

                SelectExprElementNamedSpec validatedElement = new SelectExprElementNamedSpec(validatedExpression, asName);
                namedSelectionList.Add(validatedElement);
            }
            selectionList = null;

            // Validate group-by expressions, if any (could be empty list for no group-by)
            for (int i = 0; i < groupByNodes.Count; i++)
            {
                groupByNodes[i] = groupByNodes[i].GetValidatedSubtree(typeService, autoImportService);
            }

            // Validate having clause, if present
            if (optionalHavingNode != null)
            {
                optionalHavingNode = optionalHavingNode.GetValidatedSubtree(typeService, autoImportService);
            }

            // Validate order-by expressions, if any (could be empty list for no order-by)
            for (int i = 0; i < orderByList.Count; i++)
            {
                ExprNode orderByNode = orderByList[i].First;
                Boolean isDescending = orderByList[i].Second;
                Pair<ExprNode, Boolean> validatedPair = new Pair<ExprNode, Boolean>(orderByNode.GetValidatedSubtree(typeService, autoImportService), isDescending);
                orderByList[i] = validatedPair;
            }

            // Get the select expression nodes
            IList<ExprNode> selectNodes = new List<ExprNode>();
            foreach (SelectExprElementNamedSpec element in namedSelectionList)
            {
                selectNodes.Add(element.SelectExpression);
            }

            // Get the order-by expression nodes
            IList<ExprNode> orderByNodes = new List<ExprNode>();
            foreach (Pair<ExprNode, Boolean> element in orderByList)
            {
                orderByNodes.Add(element.First);
            }

            // Determine aggregate functions used in select, if any
            IList<ExprAggregateNode> selectAggregateExprNodes = new List<ExprAggregateNode>();
            foreach (SelectExprElementNamedSpec element in namedSelectionList)
            {
                ExprAggregateNode.GetAggregatesBottomUp(element.SelectExpression, selectAggregateExprNodes);
            }

            // Construct the appropriate aggregation service
            bool hasGroupBy = groupByNodes.Count > 0;
            AggregationService aggregationService = AggregationServiceFactory.GetService(selectAggregateExprNodes, hasGroupBy, optionalHavingNode, orderByNodes);

            // Construct the processor for sorting output events
            OrderByProcessor orderByProcessor = OrderByProcessorFactory.GetProcessor(namedSelectionList, groupByNodes, orderByList, aggregationService, eventAdapterService);

            // Construct the processor for evaluating the select clause
            SelectExprProcessor selectExprProcessor = SelectExprProcessorFactory.GetProcessor(namedSelectionList, insertIntoDesc, typeService, eventAdapterService);

            // Get a list of event properties being aggregated in the select clause, if any
            ISet<Pair<Int32, String>> propertiesAggregatedSelect = GetAggregatedProperties(selectAggregateExprNodes);
            ISet<Pair<Int32, String>> propertiesGroupBy = GetGroupByProperties(groupByNodes);
            // Figure out all non-aggregated event properties in the select clause (props not under a sum/avg/max aggregation node)
            ISet<Pair<Int32, String>> nonAggregatedProps = GetNonAggregatedProps(selectNodes);

            // Determine if we have a having clause with aggregation
            IList<ExprAggregateNode> havingAggregateExprNodes = new List<ExprAggregateNode>();
            ISet<Pair<Int32, String>> propertiesAggregatedHaving = new EHashSet<Pair<Int32, String>>();
            if (optionalHavingNode != null)
            {
                ExprAggregateNode.GetAggregatesBottomUp(optionalHavingNode, havingAggregateExprNodes);
                propertiesAggregatedHaving = GetAggregatedProperties(havingAggregateExprNodes);
            }

            // Validate that group-by is filled with sensible nodes (identifiers, and not part of aggregates selected, no aggregates)
            ValidateGroupBy(groupByNodes, propertiesAggregatedSelect, propertiesGroupBy);

            // Validate the having-clause (selected aggregate nodes and all in group-by are allowed)
            if (optionalHavingNode != null)
            {
                ValidateHaving(selectAggregateExprNodes, propertiesGroupBy, optionalHavingNode);
            }

            // Determine if any output rate limiting must be performed early while processing results
            bool isOutputLimiting = outputLimitSpec != null;
            bool isOutputLimitLastOnly = outputLimitSpec != null ? outputLimitSpec.IsDisplayLastOnly : false;

            // (1)
            // There is no group-by clause and no aggregate functions with event properties in the select clause and having clause (simplest case)
            if ((groupByNodes.Count == 0) && (selectAggregateExprNodes.Count == 0) && (havingAggregateExprNodes.Count == 0))
            {
                // (1a)
                // There is no need to perform select expression processing, the single view itself (no join) generates
                // events in the desired format, therefore there is no output processor. There are no order-by expressions.
                if (selectExprProcessor == null && (orderByNodes.Count == 0) && optionalHavingNode == null)
                {
                    log.Debug(".GetProcessor Using no result processor");
                    return null;
                }

                // (1b)
                // We need to process the select expression in a simple fashion, with each event (old and new)
                // directly generating one row, and no need to update aggregate state since there is no aggregate function.
                // There might be some order-by expressions.
                log.Debug(".GetProcessor Using ResultSetProcessorSimple");
                return new ResultSetProcessorSimple(selectExprProcessor, orderByProcessor, optionalHavingNode, isOutputLimiting, isOutputLimitLastOnly);
            }

            // (2)
            // A wildcard select-clause has been specified and the group-by is ignored since no aggregation functions are used, and no having clause
            if ((namedSelectionList.Count == 0) && (propertiesAggregatedHaving.Count == 0))
            {
                log.Debug(".GetProcessor Using ResultSetProcessorSimple");
                return new ResultSetProcessorSimple(selectExprProcessor, orderByProcessor, optionalHavingNode, isOutputLimiting, isOutputLimitLastOnly);
            }

            if ((groupByNodes.Count == 0) && (selectAggregateExprNodes.Count > 0))
            {
                // (3)
                // There is no group-by clause and there are aggregate functions with event properties in the select clause (aggregation case)
                // and all event properties are aggregated (all properties are under aggregation functions).
                if (nonAggregatedProps.Count == 0)
                {
                    log.Debug(".GetProcessor Using ResultSetProcessorRowForAll");
                    return new ResultSetProcessorRowForAll(selectExprProcessor, aggregationService, optionalHavingNode);
                }

                // (4)
                // There is no group-by clause but there are aggregate functions with event properties in the select clause (aggregation case)
                // and not all event properties are aggregated (some properties are not under aggregation functions).
                log.Debug(".GetProcessor Using ResultSetProcessorAggregateAll");
                return new ResultSetProcessorAggregateAll(selectExprProcessor, orderByProcessor, aggregationService, optionalHavingNode, isOutputLimiting, isOutputLimitLastOnly);
            }

            // Handle group-by cases
            if (groupByNodes.Count == 0)
            {
                throw new SystemException("Unexpected empty group-by expression list");
            }

            // Figure out if all non-aggregated event properties in the select clause are listed in the group by
            ISet<Pair<Int32, String>> nonAggregatedPropsSelect = GetNonAggregatedProps(selectNodes);
            bool allInGroupBy = true;
            foreach (Pair<Int32, String> nonAggregatedProp in nonAggregatedPropsSelect)
            {
                if (!propertiesGroupBy.Contains(nonAggregatedProp))
                {
                    allInGroupBy = false;
                }
            }

            // Wildcard select-clause means we do not have all selected properties in the group
            if (namedSelectionList.Count == 0)
            {
                allInGroupBy = false;
            }

            // Figure out if all non-aggregated event properties in the order-by clause are listed in the select expression
            ISet<Pair<Int32, String>> nonAggregatedPropsOrderBy = GetNonAggregatedProps(orderByNodes);

            bool allInSelect = true;
            foreach (Pair<Int32, String> nonAggregatedProp in nonAggregatedPropsOrderBy)
            {
                if (!nonAggregatedPropsSelect.Contains(nonAggregatedProp))
                {
                    allInSelect = false;
                }
            }

            // Wildcard select-clause means that all order-by props in the select expression
            if (namedSelectionList.Count == 0)
            {
                allInSelect = true;
            }

            // (4)
            // There is a group-by clause, and all event properties in the select clause that are not under an aggregation
            // function are listed in the group-by clause, and if there is an order-by clause, all non-aggregated properties
            // referred to in the order-by clause also appear in the select (output one row per group, not one row per event)
            if (allInGroupBy && allInSelect)
            {
                log.Debug(".GetProcessor Using ResultSetProcessorRowPerGroup");
                return new ResultSetProcessorRowPerGroup(selectExprProcessor, orderByProcessor, aggregationService, groupByNodes, optionalHavingNode, isOutputLimiting, isOutputLimitLastOnly);
            }

            // (6)
            // There is a group-by clause, and one or more event properties in the select clause that are not under an aggregation
            // function are not listed in the group-by clause (output one row per event, not one row per group)
            log.Debug(".GetProcessor Using ResultSetProcessorAggregateGrouped");
            return new ResultSetProcessorAggregateGrouped(selectExprProcessor, orderByProcessor, aggregationService, groupByNodes, optionalHavingNode, isOutputLimiting, isOutputLimitLastOnly);
        }

        private static void ValidateHaving(
            IList<ExprAggregateNode> selectAggregateExprNodes,
            ISet<Pair<Int32, String>> propertiesGroupedBy,
            ExprNode havingNode)
        {
            // All aggregation functions in the having node must match with an aggregation function in the select
            IList<ExprAggregateNode> aggregateNodesHaving = new List<ExprAggregateNode>();
            if (aggregateNodesHaving != null)
            {
                ExprAggregateNode.GetAggregatesBottomUp(havingNode, aggregateNodesHaving);
            }

            foreach (ExprAggregateNode aggregateNodeHaving in aggregateNodesHaving)
            {
                bool foundMatch = false;
                foreach (ExprAggregateNode aggregateNodeSelect in selectAggregateExprNodes)
                {
                    if (ExprNode.DeepEquals(aggregateNodeSelect, aggregateNodeHaving))
                    {
                        foundMatch = true;
                        break;
                    }
                }

                if (!foundMatch)
                {
                    throw new ExprValidationException("Aggregate functions in the HAVING clause must match aggregate functions in the select clause");
                }
            }

            // Any non-aggregated properties must occur in the group-by clause (if there is one)
            if (propertiesGroupedBy.Count > 0)
            {
                ExprNodeIdentifierVisitor visitor = new ExprNodeIdentifierVisitor(true);
                havingNode.Accept(visitor);
                IList<Pair<Int32, String>> allPropertiesHaving = visitor.ExprProperties;
                ISet<Pair<Int32, String>> aggPropertiesHaving = GetAggregatedProperties(aggregateNodesHaving);
                CollectionHelper.RemoveAll(allPropertiesHaving, aggPropertiesHaving);
                CollectionHelper.RemoveAll(allPropertiesHaving, propertiesGroupedBy);

                if (allPropertiesHaving.Count > 0)
                {
                    String name = allPropertiesHaving[0].Second;
                    throw new ExprValidationException("Non-aggregated property '" + name + "' in the HAVING clause must occur in the group-by clause");
                }
            }
        }

        private static void ValidateGroupBy(
        	IList<ExprNode> groupByNodes,
        	ISet<Pair<Int32, String>> propertiesAggregated,
        	ISet<Pair<Int32, String>> propertiesGroupedBy)
        {
            // Make sure there is no aggregate function in group-by
            IList<ExprAggregateNode> aggNodes = new List<ExprAggregateNode>();
            foreach (ExprNode groupByNode in groupByNodes)
            {
                ExprAggregateNode.GetAggregatesBottomUp(groupByNode, aggNodes);
                if (aggNodes.Count > 0)
                {
                    throw new ExprValidationException("Group-by expressions cannot contain aggregate functions");
                }
            }

            // If any group-by properties occur in select-aggregates, throw exception
            foreach (Pair<Int32, String> propertyAggregated in propertiesAggregated)
            {
                if (propertiesGroupedBy.Contains(propertyAggregated))
                {
                    throw new ExprValidationException("Group-by property '" + propertyAggregated.Second + "' cannot also occur in an aggregate function in the select clause");
                }
            }
        }

        private static ISet<Pair<Int32, String>> GetNonAggregatedProps(IList<ExprNode> exprNodes)
        {
            // Determine all event properties in the clause
            ISet<Pair<Int32, String>> nonAggProps = new EHashSet<Pair<Int32, String>>();
            foreach (ExprNode node in exprNodes)
            {
                ExprNodeIdentifierVisitor visitor = new ExprNodeIdentifierVisitor(false);
                node.Accept(visitor);
                IList<Pair<Int32, String>> propertiesNode = visitor.ExprProperties;
                nonAggProps.AddAll(propertiesNode);
            }

            return nonAggProps;
        }

        private static ISet<Pair<Int32, String>> GetAggregatedProperties(IList<ExprAggregateNode> aggregateNodes)
        {
            // Get a list of properties being aggregated in the clause.
            ISet<Pair<Int32, String>> propertiesAggregated = new EHashSet<Pair<Int32, String>>();
            foreach (ExprNode selectAggExprNode in aggregateNodes)
            {
                ExprNodeIdentifierVisitor visitor = new ExprNodeIdentifierVisitor(true);
                selectAggExprNode.Accept(visitor);
                IList<Pair<Int32, String>> properties = visitor.ExprProperties;
                propertiesAggregated.AddAll(properties);
            }

            return propertiesAggregated;
        }

        private static ISet<Pair<Int32, String>> GetGroupByProperties(IList<ExprNode> groupByNodes)
        {
            // Get the set of properties refered to by all group-by expression nodes.
            ISet<Pair<Int32, String>> propertiesGroupBy = new EHashSet<Pair<Int32, String>>();
            foreach (ExprNode groupByNode in groupByNodes)
            {
                ExprNodeIdentifierVisitor visitor = new ExprNodeIdentifierVisitor(true);
                groupByNode.Accept(visitor);
                IList<Pair<Int32, String>> propertiesNode = visitor.ExprProperties;
                propertiesGroupBy.AddAll(propertiesNode);

                // For each group-by expression node, require at least one property.
                if (propertiesNode.Count == 0)
                {
                    throw new ExprValidationException("Group-by expressions must refer to property names");
                }
            }

            return propertiesGroupBy;
        }

        private static void ExpandAliases(IList<SelectExprElementUnnamedSpec> selectionList, IList<Pair<ExprNode, Boolean>> orderByList)
        {
            foreach (SelectExprElementUnnamedSpec selectElement in selectionList)
            {
                String alias = selectElement.OptionalAsName;
                if (alias != null)
                {
                    ExprNode fullExpr = selectElement.SelectExpression;
                    
                    int orderByListLength = orderByList.Count ;
                    for( int ii = 0 ; ii < orderByListLength ; ii++ )
                    {
                    	Pair<ExprNode, Boolean> orderByElement = orderByList[ii] ;
                        ExprNode swapped = AliasNodeSwapper.Swap(orderByElement.First, alias, fullExpr);
                        Pair<ExprNode, Boolean> newOrderByElement = new Pair<ExprNode, Boolean>(swapped, orderByElement.Second);
                        orderByList[ii] = newOrderByElement;
                    }
                }
            }
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}