///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

using net.esper.eql.core;
using net.esper.eql.expression;
using net.esper.events;

namespace net.esper.eql.agg
{
	/// <summary>
	/// Factory for aggregation service instances.
	/// &lt;p&gt;
	/// Consolidates aggregation nodes such that result futures point to a single instance and
	/// no re-evaluation of the same result occurs.
	/// </summary>
	public class AggregationServiceFactory
	{
	    /// <summary>
	    /// Returns an instance to handle the aggregation required by the aggregation expression nodes, depending on
	    /// whether there are any group-by nodes.
	    /// </summary>
	    /// <param name="selectAggregateExprNodes">
	    /// aggregation nodes extracted out of the select expression
	    /// </param>
	    /// <param name="havingAggregateExprNodes">
	    /// aggregation nodes extracted out of the select expression
	    /// </param>
	    /// <param name="orderByAggregateExprNodes">
	    /// aggregation nodes extracted out of the select expression
	    /// </param>
	    /// <param name="hasGroupByClause">
	    /// indicator on whethere there is group-by required, or group-all
	    /// </param>
	    /// <param name="methodResolutionService">
	    /// is required to resolve aggregation methods
	    /// </param>
	    /// <returns>instance for aggregation handling</returns>
	    public static AggregationService GetService(List<ExprAggregateNode> selectAggregateExprNodes,
	                                                List<ExprAggregateNode> havingAggregateExprNodes,
	                                                List<ExprAggregateNode> orderByAggregateExprNodes,
	                                                bool hasGroupByClause,
	                                                MethodResolutionService methodResolutionService)
	    {
	        // No aggregates used, we do not need this service
	        if ((selectAggregateExprNodes.IsEmpty()) && (havingAggregateExprNodes.IsEmpty()))
	        {
	        	return new AggregationServiceNull();
	        }

	        // Compile a map of aggregation nodes and equivalent-to aggregation nodes.
	        // Equivalent-to functions are for example "select Sum(a*b), 5*Sum(a*b)".
	        // Reducing the total number of aggregation functions.
	        Map<ExprAggregateNode, List<ExprAggregateNode>> equivalencyList = new LinkedHashMap<ExprAggregateNode, List<ExprAggregateNode>>();
	        foreach (ExprAggregateNode selectAggNode in selectAggregateExprNodes)
	        {
	            AddEquivalent(selectAggNode, equivalencyList);
	        }
	        foreach (ExprAggregateNode havingAggNode in havingAggregateExprNodes)
	        {
	            AddEquivalent(havingAggNode, equivalencyList);
	        }
	        foreach (ExprAggregateNode orderByAggNode in orderByAggregateExprNodes)
	        {
	            AddEquivalent(orderByAggNode, equivalencyList);
	        }

	        // Construct a list of evaluation node for the aggregation function.
	        // For example "sum(2 * 3)" would make the sum an evaluation node.
	        AggregationMethod[] aggregators = new AggregationMethod[equivalencyList.Size()];
	        ExprEvaluator[] evaluators = new ExprEvaluator[equivalencyList.Size()];

	        int index = 0;
	        foreach (ExprAggregateNode aggregateNode in equivalencyList.KeySet())
	        {
	            if (aggregateNode.GetChildNodes().Size() > 1)
	            {
	                throw new IllegalStateException("Aggregate node is expected to have at most a single child node");
	            }

	            // Use the evaluation node under the aggregation node to obtain the aggregation value
	            if (!aggregateNode.GetChildNodes().IsEmpty())
	            {
	                evaluators[index] = aggregateNode.GetChildNodes().Get(0);
	            }
	            // For aggregation that doesn't evaluate any particular sub-expression, return null on evaluation
	            else
	            {
	                evaluators[index] = new ExprEvaluatorImpl(
                        delegate(EventBean[] eventsPerStream, bool isNewData)
	                    {
	                        return null;
	                    }) ;
	            }

	            aggregators[index] = aggregateNode.GetPrototypeAggregator();
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
	        foreach (ExprAggregateNode aggregateNode in equivalencyList.KeySet())
	        {
	            aggregateNode.SetAggregationResultFuture(service, column);

	            // hand to all equivalent-to
	            List<ExprAggregateNode> equivalentAggregators = equivalencyList.Get(aggregateNode);
	            if (equivalentAggregators != null)
	            {
	                foreach (ExprAggregateNode equivalentAggNode in equivalentAggregators)
	                {
	                    equivalentAggNode.SetAggregationResultFuture(service, column);
	                }
	            }

	            column++;
	        }

	        return service;
	    }

	    private static void AddEquivalent(ExprAggregateNode aggNodeToAdd, Map<ExprAggregateNode, List<ExprAggregateNode>> equivalencyList)
	    {
	        // Check any same aggregation nodes among all aggregation clauses
	        bool foundEquivalent = false;
	        foreach (ExprAggregateNode aggNode in equivalencyList.KeySet())
	        {
	            if (ExprNode.DeepEquals(aggNode, aggNodeToAdd))
	            {
	                List<ExprAggregateNode> equivalentAggregators = equivalencyList.Get(aggNode);
	                if (equivalentAggregators == null)
	                {
	                    equivalentAggregators = new ArrayList<ExprAggregateNode>();
	                }
	                equivalentAggregators.Add(aggNodeToAdd);
	                equivalencyList.Put(aggNode, equivalentAggregators);
	                foundEquivalent = true;
	                break;
	            }
	        }

	        if (!foundEquivalent)
	        {
	            equivalencyList.Put(aggNodeToAdd, null);
	        }
	    }

	}
} // End of namespace
