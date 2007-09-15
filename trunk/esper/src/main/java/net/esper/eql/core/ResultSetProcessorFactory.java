/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.eql.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import net.esper.collection.Pair;
import net.esper.eql.expression.*;
import net.esper.eql.spec.*;
import net.esper.eql.agg.AggregationServiceFactory;
import net.esper.eql.agg.AggregationService;
import net.esper.event.EventAdapterService;
import net.esper.schedule.TimeProvider;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Factory for output processors. Output processors process the result set of a join or of a view
 * and apply aggregation/grouping, having and some output limiting logic.
 * <p>
 * The instance produced by the factory depends on the presence of aggregation functions in the select list,
 * the presence and nature of the group-by clause.
 * <p>
 * In case (1) and (2) there are no aggregation functions in the select clause.
 * <p>
 * Case (3) is without group-by and with aggregation functions and without non-aggregated properties
 * in the select list: <pre>select sum(volume) </pre>.
 * Always produces one row for new and old data, aggregates without grouping.
 * <p>
 * Case (4) is without group-by and with aggregation functions but with non-aggregated properties
 * in the select list: <pre>select price, sum(volume) </pre>.
 * Produces a row for each event, aggregates without grouping.
 * <p>
 * Case (5) is with group-by and with aggregation functions and all selected properties are grouped-by.
 * in the select list: <pre>select customerId, sum(volume) group by customerId</pre>.
 * Produces a old and new data row for each group changed, aggregates with grouping, see
 * {@link ResultSetProcessorRowPerGroup}
 * <p>
 * Case (6) is with group-by and with aggregation functions and only some selected properties are grouped-by.
 * in the select list: <pre>select customerId, supplierId, sum(volume) group by customerId</pre>.
 * Produces row for each event, aggregates with grouping.
 */
public class ResultSetProcessorFactory
{
    /**
     * Returns the result set process for the given select expression, group-by clause and
     * having clause given a set of types describing each stream in the from-clause.
     * @param selectClauseSpec - represents select clause and thus the expression nodes listed in the select, or empty if wildcard
     * @param groupByNodes - represents the expressions to group-by events based on event properties, or empty if no group-by was specified
     * @param optionalHavingNode - represents the having-clause boolean filter criteria
     * @param outputLimitSpec - indicates whether to output all or only the last event
     * @param orderByList - represent the expressions in the order-by clause
     * @param typeService - for information about the streams in the from clause
     * @param insertIntoDesc - descriptor for insert-into clause information
     * @param eventAdapterService - wrapping service for events
     * @param methodResolutionService - for resolving class names
     * @param viewResourceDelegate - delegates views resource factory to expression resources requirements
     * @param timeProvider - provides engine current time for selection on of filtering and grouping 
     * @return result set processor instance
     * @throws ExprValidationException when any of the expressions is invalid
     */
    public static ResultSetProcessor getProcessor(SelectClauseSpec selectClauseSpec,
                                                  InsertIntoDesc insertIntoDesc,
                                               	  List<ExprNode> groupByNodes,
                                               	  ExprNode optionalHavingNode,
                                               	  OutputLimitSpec outputLimitSpec,
                                               	  List<OrderByItem> orderByList,
                                                  StreamTypeService typeService,
                                                  EventAdapterService eventAdapterService,
                                                  MethodResolutionService methodResolutionService,
                                                  ViewResourceDelegate viewResourceDelegate,
                                                  TimeProvider timeProvider)
            throws ExprValidationException
    {
        if (log.isDebugEnabled())
        {
            log.debug(".getProcessor Getting processor for " +
                    " selectionList=" + selectClauseSpec.getSelectList() +
                    " isUsingWildcard=" + selectClauseSpec.isUsingWildcard() + 
                    " groupByNodes=" + Arrays.toString(groupByNodes.toArray()) +
                    " optionalHavingNode=" + optionalHavingNode);
        }

        // Expand any instances of select-clause aliases in the
        // order-by clause with the full expression
        expandAliases(selectClauseSpec.getSelectList(), orderByList);

        // Validate selection expressions, if any (could be wildcard i.e. empty list)
        List<SelectExprElementCompiledSpec> namedSelectionList = new LinkedList<SelectExprElementCompiledSpec>();
        for (int i = 0; i < selectClauseSpec.getSelectList().size(); i++)
        {
            // validate element
            SelectExprElementRawSpec element = selectClauseSpec.getSelectList().get(i);
            ExprNode validatedExpression = element.getSelectExpression().getValidatedSubtree(typeService, methodResolutionService, viewResourceDelegate, timeProvider);

            // determine an element name if none assigned
            String asName = element.getOptionalAsName();
            if (asName == null)
            {
                asName = validatedExpression.toExpressionString();
            }

            SelectExprElementCompiledSpec validatedElement = new SelectExprElementCompiledSpec(validatedExpression, asName);
            namedSelectionList.add(validatedElement);
        }
        boolean isUsingWildcard = selectClauseSpec.isUsingWildcard();
        selectClauseSpec = null;

        // Validate group-by expressions, if any (could be empty list for no group-by)
        for (int i = 0; i < groupByNodes.size(); i++)
        {
            // Ensure there is no subselects
            ExprNodeSubselectVisitor visitor = new ExprNodeSubselectVisitor();
            groupByNodes.get(i).accept(visitor);
            if (visitor.getSubselects().size() > 0)
            {
                throw new ExprValidationException("Subselects not allowed within group-by");
            }

            groupByNodes.set(i, groupByNodes.get(i).getValidatedSubtree(typeService, methodResolutionService, viewResourceDelegate, timeProvider));
        }

        // Validate having clause, if present
        if (optionalHavingNode != null)
        {
            // Ensure there is no subselects
            ExprNodeSubselectVisitor visitor = new ExprNodeSubselectVisitor();
            optionalHavingNode.accept(visitor);
            if (visitor.getSubselects().size() > 0)
            {
                throw new ExprValidationException("Subselects not allowed within having-clause");
            }

            optionalHavingNode = optionalHavingNode.getValidatedSubtree(typeService, methodResolutionService, viewResourceDelegate, timeProvider);
        }

        // Validate order-by expressions, if any (could be empty list for no order-by)
        for (int i = 0; i < orderByList.size(); i++)
        {
        	ExprNode orderByNode = orderByList.get(i).getExprNode();

            // Ensure there is no subselects
            ExprNodeSubselectVisitor visitor = new ExprNodeSubselectVisitor();
            orderByNode.accept(visitor);
            if (visitor.getSubselects().size() > 0)
            {
                throw new ExprValidationException("Subselects not allowed within order-by clause");
            }

            Boolean isDescending = orderByList.get(i).isDescending();
        	OrderByItem validatedOrderBy = new OrderByItem(orderByNode.getValidatedSubtree(typeService, methodResolutionService, viewResourceDelegate, timeProvider), isDescending);
        	orderByList.set(i, validatedOrderBy);
        }

        // Get the select expression nodes
        List<ExprNode> selectNodes = new ArrayList<ExprNode>();
        for(SelectExprElementCompiledSpec element : namedSelectionList)
        {
        	selectNodes.add(element.getSelectExpression());
        }

        // Get the order-by expression nodes
        List<ExprNode> orderByNodes = new ArrayList<ExprNode>();
        for(OrderByItem element : orderByList)
        {
        	orderByNodes.add(element.getExprNode());
        }

        // Determine aggregate functions used in select, if any
        List<ExprAggregateNode> selectAggregateExprNodes = new LinkedList<ExprAggregateNode>();
        for (SelectExprElementCompiledSpec element : namedSelectionList)
        {
            ExprAggregateNode.getAggregatesBottomUp(element.getSelectExpression(), selectAggregateExprNodes);
        }

        // Determine if we have a having clause with aggregation
        List<ExprAggregateNode> havingAggregateExprNodes = new LinkedList<ExprAggregateNode>();
        Set<Pair<Integer, String>> propertiesAggregatedHaving = new HashSet<Pair<Integer, String>>();
        if (optionalHavingNode != null)
        {
            ExprAggregateNode.getAggregatesBottomUp(optionalHavingNode, havingAggregateExprNodes);
            propertiesAggregatedHaving = getAggregatedProperties(havingAggregateExprNodes);
        }

        // Determine if we have a order-by clause with aggregation
        List<ExprAggregateNode> orderByAggregateExprNodes = new LinkedList<ExprAggregateNode>();
        if (orderByNodes != null)
        {
            for (ExprNode orderByNode : orderByNodes)
            {
                ExprAggregateNode.getAggregatesBottomUp(orderByNode, orderByAggregateExprNodes);
            }
        }

        // Construct the appropriate aggregation service
        boolean hasGroupBy = !groupByNodes.isEmpty();
        AggregationService aggregationService = AggregationServiceFactory.getService(selectAggregateExprNodes, havingAggregateExprNodes, orderByAggregateExprNodes, hasGroupBy, methodResolutionService);

        // Construct the processor for sorting output events
        OrderByProcessor orderByProcessor = OrderByProcessorFactory.getProcessor(namedSelectionList,
                groupByNodes, orderByList, aggregationService, eventAdapterService);

        // Construct the processor for evaluating the select clause
        SelectExprProcessor selectExprProcessor = SelectExprProcessorFactory.getProcessor(namedSelectionList, isUsingWildcard, insertIntoDesc, typeService, eventAdapterService);

        // Get a list of event properties being aggregated in the select clause, if any
        Set<Pair<Integer, String>> propertiesAggregatedSelect = getAggregatedProperties(selectAggregateExprNodes);
        Set<Pair<Integer, String>> propertiesGroupBy = getGroupByProperties(groupByNodes);
        // Figure out all non-aggregated event properties in the select clause (props not under a sum/avg/max aggregation node)
        Set<Pair<Integer, String>> nonAggregatedProps = getNonAggregatedProps(selectNodes);

        // Validate that group-by is filled with sensible nodes (identifiers, and not part of aggregates selected, no aggregates)
        validateGroupBy(groupByNodes, propertiesAggregatedSelect, propertiesGroupBy);

        // Validate the having-clause (selected aggregate nodes and all in group-by are allowed)
        if (optionalHavingNode != null)
        {
            validateHaving(selectAggregateExprNodes, propertiesGroupBy, optionalHavingNode);
        }

        // Determine if any output rate limiting must be performed early while processing results
        boolean isOutputLimiting = outputLimitSpec != null;
        boolean isOutputLimitLastOnly = outputLimitSpec != null ? outputLimitSpec.isDisplayLastOnly() : false;

        // (1)
        // There is no group-by clause and no aggregate functions with event properties in the select clause and having clause (simplest case)
        if ((groupByNodes.isEmpty()) && (selectAggregateExprNodes.isEmpty()) && (havingAggregateExprNodes.isEmpty()))
        {
            // (1a)
            // There is no need to perform select expression processing, the single view itself (no join) generates
            // events in the desired format, therefore there is no output processor. There are no order-by expressions.
            if (selectExprProcessor == null && orderByNodes.isEmpty() && optionalHavingNode == null)
            {
                log.debug(".getProcessor Using no result processor");
                return null;
            }

            // (1b)
            // We need to process the select expression in a simple fashion, with each event (old and new)
            // directly generating one row, and no need to update aggregate state since there is no aggregate function.
            // There might be some order-by expressions.
            log.debug(".getProcessor Using ResultSetProcessorSimple");
            return new ResultSetProcessorSimple(selectExprProcessor, orderByProcessor, optionalHavingNode, isOutputLimiting, isOutputLimitLastOnly);
        }

        // (2)
        // A wildcard select-clause has been specified and the group-by is ignored since no aggregation functions are used, and no having clause
        if ((namedSelectionList.isEmpty()) && (propertiesAggregatedHaving.isEmpty()))
        {
            log.debug(".getProcessor Using ResultSetProcessorSimple");
            return new ResultSetProcessorSimple(selectExprProcessor, orderByProcessor, optionalHavingNode, isOutputLimiting, isOutputLimitLastOnly);
        }

        boolean hasAggregation = (!selectAggregateExprNodes.isEmpty()) || (!propertiesAggregatedHaving.isEmpty());
        if ((groupByNodes.isEmpty()) && hasAggregation)
        {
            // (3)
            // There is no group-by clause and there are aggregate functions with event properties in the select clause (aggregation case)
            // or having class, and all event properties are aggregated (all properties are under aggregation functions).
            if ((nonAggregatedProps.isEmpty()) && (!isUsingWildcard))
            {
                log.debug(".getProcessor Using ResultSetProcessorRowForAll");
                return new ResultSetProcessorRowForAll(selectExprProcessor, aggregationService, optionalHavingNode);
            }

            // (4)
            // There is no group-by clause but there are aggregate functions with event properties in the select clause (aggregation case)
            // or having clause and not all event properties are aggregated (some properties are not under aggregation functions).
            log.debug(".getProcessor Using ResultSetProcessorAggregateAll");
            return new ResultSetProcessorAggregateAll(selectExprProcessor, orderByProcessor, aggregationService, optionalHavingNode, isOutputLimiting, isOutputLimitLastOnly);
        }

        // Handle group-by cases
        if (groupByNodes.isEmpty())
        {
            throw new IllegalStateException("Unexpected empty group-by expression list");
        }

        // Figure out if all non-aggregated event properties in the select clause are listed in the group by
        Set<Pair<Integer, String>> nonAggregatedPropsSelect = getNonAggregatedProps(selectNodes);
        boolean allInGroupBy = true;
        for (Pair<Integer, String> nonAggregatedProp : nonAggregatedPropsSelect)
        {
            if (!propertiesGroupBy.contains(nonAggregatedProp))
            {
                allInGroupBy = false;
            }
        }

        // Wildcard select-clause means we do not have all selected properties in the group
        if (namedSelectionList.isEmpty())
        {
            allInGroupBy = false;
        }

        // Figure out if all non-aggregated event properties in the order-by clause are listed in the select expression
        Set<Pair<Integer, String>> nonAggregatedPropsOrderBy = getNonAggregatedProps(orderByNodes);

        boolean allInSelect = true;
        for (Pair<Integer, String> nonAggregatedProp : nonAggregatedPropsOrderBy)
        {
            if (!nonAggregatedPropsSelect.contains(nonAggregatedProp))
            {
                allInSelect = false;
            }
        }

        // Wildcard select-clause means that all order-by props in the select expression
        if (namedSelectionList.isEmpty())
        {
            allInSelect = true;
        }

        // (4)
        // There is a group-by clause, and all event properties in the select clause that are not under an aggregation
        // function are listed in the group-by clause, and if there is an order-by clause, all non-aggregated properties
        // referred to in the order-by clause also appear in the select (output one row per group, not one row per event)
        if (allInGroupBy && allInSelect)
        {
            log.debug(".getProcessor Using ResultSetProcessorRowPerGroup");
            return new ResultSetProcessorRowPerGroup(selectExprProcessor, orderByProcessor, aggregationService, groupByNodes, optionalHavingNode, isOutputLimiting, isOutputLimitLastOnly);
        }

        // (6)
        // There is a group-by clause, and one or more event properties in the select clause that are not under an aggregation
        // function are not listed in the group-by clause (output one row per event, not one row per group)
        log.debug(".getProcessor Using ResultSetProcessorAggregateGrouped");
        return new ResultSetProcessorAggregateGrouped(selectExprProcessor, orderByProcessor, aggregationService, groupByNodes, optionalHavingNode, isOutputLimiting, isOutputLimitLastOnly);
    }

    private static void validateHaving(List<ExprAggregateNode> selectAggregateExprNodes,
                                       Set<Pair<Integer, String>> propertiesGroupedBy,
                                       ExprNode havingNode)
        throws ExprValidationException
    {
        List<ExprAggregateNode> aggregateNodesHaving = new LinkedList<ExprAggregateNode>();
        if (aggregateNodesHaving != null)
        {
            ExprAggregateNode.getAggregatesBottomUp(havingNode, aggregateNodesHaving);
        }

        // Any non-aggregated properties must occur in the group-by clause (if there is one)
        if (!propertiesGroupedBy.isEmpty())
        {
            ExprNodeIdentifierVisitor visitor = new ExprNodeIdentifierVisitor(true);
            havingNode.accept(visitor);
            List<Pair<Integer, String>> allPropertiesHaving = visitor.getExprProperties();
            Set<Pair<Integer, String>> aggPropertiesHaving = getAggregatedProperties(aggregateNodesHaving);
            allPropertiesHaving.removeAll(aggPropertiesHaving);
            allPropertiesHaving.removeAll(propertiesGroupedBy);

            if (!allPropertiesHaving.isEmpty())
            {
                String name = allPropertiesHaving.iterator().next().getSecond();
                throw new ExprValidationException("Non-aggregated property '" + name + "' in the HAVING clause must occur in the group-by clause");
            }
        }
    }

    private static void validateGroupBy(List<ExprNode> groupByNodes,
                                        Set<Pair<Integer, String>> propertiesAggregated,
                                        Set<Pair<Integer, String>> propertiesGroupedBy)
        throws ExprValidationException
    {
        // Make sure there is no aggregate function in group-by
        List<ExprAggregateNode> aggNodes = new LinkedList<ExprAggregateNode>();
        for (ExprNode groupByNode : groupByNodes)
        {
            ExprAggregateNode.getAggregatesBottomUp(groupByNode, aggNodes);
            if (!aggNodes.isEmpty())
            {
                throw new ExprValidationException("Group-by expressions cannot contain aggregate functions");
            }
        }

        // If any group-by properties occur in select-aggregates, throw exception
        for (Pair<Integer, String> propertyAggregated : propertiesAggregated)
        {
            if (propertiesGroupedBy.contains(propertyAggregated))
            {
                throw new ExprValidationException("Group-by property '" +
                        propertyAggregated.getSecond() + "' cannot also occur in an aggregate function in the select clause");
            }
        }
    }

    private static Set<Pair<Integer, String>> getNonAggregatedProps(List<ExprNode> exprNodes)
    {
        // Determine all event properties in the clause
        Set<Pair<Integer, String>> nonAggProps = new HashSet<Pair<Integer, String>>();
        for (ExprNode node : exprNodes)
        {
            ExprNodeIdentifierVisitor visitor = new ExprNodeIdentifierVisitor(false);
            node.accept(visitor);
            List<Pair<Integer, String>> propertiesNode = visitor.getExprProperties();
            nonAggProps.addAll(propertiesNode);
        }

        return nonAggProps;
    }

    private static Set<Pair<Integer, String>> getAggregatedProperties(List<ExprAggregateNode> aggregateNodes)
    {
        // Get a list of properties being aggregated in the clause.
        Set<Pair<Integer, String>> propertiesAggregated = new HashSet<Pair<Integer, String>>();
        for (ExprNode selectAggExprNode : aggregateNodes)
        {
            ExprNodeIdentifierVisitor visitor = new ExprNodeIdentifierVisitor(true);
            selectAggExprNode.accept(visitor);
            List<Pair<Integer, String>> properties = visitor.getExprProperties();
            propertiesAggregated.addAll(properties);
        }

        return propertiesAggregated;
    }

    private static Set<Pair<Integer, String>> getGroupByProperties(List<ExprNode> groupByNodes)
        throws ExprValidationException
    {
        // Get the set of properties refered to by all group-by expression nodes.
        Set<Pair<Integer, String>> propertiesGroupBy = new HashSet<Pair<Integer, String>>();

        for (ExprNode groupByNode : groupByNodes)
        {
            ExprNodeIdentifierVisitor visitor = new ExprNodeIdentifierVisitor(true);
            groupByNode.accept(visitor);
            List<Pair<Integer, String>> propertiesNode = visitor.getExprProperties();
            propertiesGroupBy.addAll(propertiesNode);

            // For each group-by expression node, require at least one property.
            if (propertiesNode.isEmpty())
            {
                throw new ExprValidationException("Group-by expressions must refer to property names");
            }
        }

        return propertiesGroupBy;
    }

    private static void expandAliases(List<SelectExprElementRawSpec> selectionList, List<OrderByItem> orderByList)
    {
    	for(SelectExprElementRawSpec selectElement : selectionList)
    	{
    		String alias = selectElement.getOptionalAsName();
    		if(alias != null)
    		{
    			ExprNode fullExpr = selectElement.getSelectExpression();
    			for(ListIterator<OrderByItem> iterator = orderByList.listIterator(); iterator.hasNext(); )
    			{
    				OrderByItem orderByElement = iterator.next();
    				ExprNode swapped = AliasNodeSwapper.swap(orderByElement.getExprNode(), alias, fullExpr);
    				OrderByItem newOrderByElement = new OrderByItem(swapped, orderByElement.isDescending());
    				iterator.set(newOrderByElement);
    			}
    		}
    	}
    }

    private static final Log log = LogFactory.getLog(ResultSetProcessorFactory.class);
}
