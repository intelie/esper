/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.eql.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.esper.collection.Pair;
import net.esper.event.EventType;
import net.esper.event.EventAdapterService;
import net.esper.eql.spec.SelectExprElementCompiledSpec;
import net.esper.eql.expression.ExprNode;
import net.esper.eql.expression.ExprValidationException;
import net.esper.eql.expression.ExprAggregateNode;
import net.esper.eql.agg.AggregationService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Factory for {@link net.esper.eql.core.OrderByProcessor} processors.
 */
public class OrderByProcessorFactory {
	private static final Log log = LogFactory.getLog(OrderByProcessorFactory.class);

    /**
     * Returns processor for order-by clauses.
     * @param selectionList is a list of select expressions
     * @param groupByNodes is a list of group-by expressions
     * @param orderByList is a list of order-by expressions
     * @param aggregationService is the service for aggregation, ie. building sums and averages per group or overall
     * @param eventAdapterService provides event adapters
     * @return ordering processor instance
     * @throws net.esper.eql.expression.ExprValidationException when validation of expressions fails
     */
    public static OrderByProcessor getProcessor(List<SelectExprElementCompiledSpec> selectionList,
											   List<ExprNode> groupByNodes,
											   List<Pair<ExprNode, Boolean>> orderByList, 
											   AggregationService aggregationService,
                                               EventAdapterService eventAdapterService)
	throws ExprValidationException
	{
		// Get the order by expression nodes
		List<ExprNode> orderByNodes = new ArrayList<ExprNode>();
		for(Pair<ExprNode, Boolean> element : orderByList)
		{
			orderByNodes.add(element.getFirst());
		}

		// No order-by clause
		if(orderByList.isEmpty())
		{
			log.debug(".getProcessor Using no OrderByProcessor");
			return null;
		}
		
        // Determine aggregate functions used in select, if any
        List<ExprAggregateNode> selectAggNodes = new LinkedList<ExprAggregateNode>();
        for (SelectExprElementCompiledSpec element : selectionList)
        {
            ExprAggregateNode.getAggregatesBottomUp(element.getSelectExpression(), selectAggNodes);
        }
        
		// Get all the aggregate functions occuring in the order-by clause
        List<ExprAggregateNode> orderAggNodes = new LinkedList<ExprAggregateNode>();
        for (ExprNode orderByNode : orderByNodes)
        {
            ExprAggregateNode.getAggregatesBottomUp(orderByNode, orderAggNodes);
        }
		
		validateOrderByAggregates(selectAggNodes, orderAggNodes);
		
		// Create the type of the order-by event
		Map<String, Class> propertyNamesAndTypes = new HashMap<String, Class>();
		for(ExprNode orderByNode : orderByNodes)
		{
			propertyNamesAndTypes.put(orderByNode.toExpressionString(), orderByNode.getType());
		}
		EventType orderType = eventAdapterService.createAnonymousMapType(propertyNamesAndTypes);
		
        // Tell the order-by processor whether to compute group-by
        // keys if they are not present
    	boolean needsGroupByKeys = !selectionList.isEmpty() && !orderAggNodes.isEmpty();
        
    	log.debug(".getProcessor Using OrderByProcessorSimple");
    	return new OrderByProcessorSimple(orderByList, groupByNodes, needsGroupByKeys, aggregationService);
	}
	

	
	private static void validateOrderByAggregates(List<ExprAggregateNode> selectAggNodes,
									   List<ExprAggregateNode> orderAggNodes)
	throws ExprValidationException
	{
		// Check that the order-by clause doesn't contain 
		// any aggregate functions not in the select expression
		for(ExprAggregateNode orderAgg : orderAggNodes)
		{
			boolean inSelect = false;
			for(ExprAggregateNode selectAgg : selectAggNodes)
			{
				if(ExprNode.deepEquals(selectAgg, orderAgg))
				{
					inSelect = true;
					break;
				}
			}
			if(!inSelect)
			{
				throw new ExprValidationException("Aggregate functions in the order-by clause must also occur in the select expression");
			}
		}
	}
}
