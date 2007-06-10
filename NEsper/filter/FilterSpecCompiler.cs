///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

using net.esper.collection;
using net.esper.compat;
using net.esper.eql.core;
using net.esper.eql.expression;
using net.esper.events;
using net.esper.type;
using net.esper.util;

using org.apache.commons.logging;

namespace net.esper.filter
{
	/// <summary>
	/// Helper to compile (validate and optimize) filter expressions as used in pattern and filter-based streams.
	/// </summary>
	public sealed class FilterSpecCompiler
	{
	    private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);

	    /// <summary>
	    /// Assigned for filter parameters that are based on bool expression and not on
	    /// any particular property name.
	    /// <p>
	    /// Keeping this artificial property name is a simplification as optimized filter parameters
	    /// generally keep a property name.
	    /// </summary>
	    public readonly static String PROPERTY_NAME_BOOLEAN_EXPRESSION = ".boolean_expression";

	    /// <summary>
	    /// Factory method for compiling filter expressions into a filter specification
	    /// for use with filter service.
	    /// </summary>
	    /// <param name="eventType">is the filtered-out event type</param>
	    /// <param name="filterExpessions">is a list of filter expressions</param>
	    /// <param name="taggedEventTypes">
	    /// is a map of stream names (tags) and event types available
	    /// </param>
	    /// <param name="streamTypeService">is used to set rules for resolving properties</param>
	    /// <param name="methodResolutionService">
	    /// resolved imports for static methods and such
	    /// </param>
	    /// <returns>compiled filter specification</returns>
	    /// <throws>ExprValidationException if the expression or type validations failed</throws>
	    public static FilterSpecCompiled MakeFilterSpec(EventType eventType,
	                                                    List<ExprNode> filterExpessions,
	                                                    LinkedDictionary<String, EventType> taggedEventTypes,
	                                                    StreamTypeService streamTypeService,
	                                                    MethodResolutionService methodResolutionService)
	    {
	        // Validate all nodes, make sure each returns a bool and types are good;
	        // Also decompose all AND super nodes into individual expressions
	        List<ExprNode> constituents = FilterSpecCompiler.ValidateAndDecompose(filterExpessions, streamTypeService, methodResolutionService);

	        // From the constituents make a filter specification
	        FilterSpecCompiled spec = MakeFilterSpec(eventType, constituents, taggedEventTypes);
	        if (log.IsDebugEnabled)
	        {
	            log.Debug(".makeFilterSpec spec=" + spec);
	        }

	        return spec;
	    }

	    // remove duplicate propertyName + filterOperator items making a judgement to optimize or simply remove the optimized form
	    private static void Consolidate(List<FilterSpecParam> items, FilterParamExprMap filterParamExprMap)
	    {
	        FilterOperator op = items[0].FilterOperator;
	        if (op == FilterOperator.NOT_EQUAL)
	        {
	            HandleConsolidateNotEqual(items, filterParamExprMap);
	            return;
	        }
	        else
	        {
	            // for all others we simple remove the second optimized form (filter param with same prop name and filter op)
	            // and thus the bool expression that started this is included
	            for (int i = 1; i < items.Count; i++)
	            {
	                filterParamExprMap.RemoveValue(items.Get(i));
	            }
	        }
	    }

	    private static List<ExprNode> ValidateAndDecompose(List<ExprNode> exprNodes, StreamTypeService streamTypeService, MethodResolutionService methodResolutionService)
	    {
	        List<ExprNode> validatedNodes = new List<ExprNode>();
	        foreach (ExprNode node in exprNodes)
	        {
	            // Ensure there is no subselects
	            ExprNodeSubselectVisitor visitor = new ExprNodeSubselectVisitor();
	            node.Accept(visitor);
	            if (visitor.Subselects.Count > 0)
	            {
	                throw new ExprValidationException("Subselects not allowed within filters");
	            }

	            ExprNode validated = node.GetValidatedSubtree(streamTypeService, methodResolutionService, null);
	            validatedNodes.Add(validated);

                if ((validated.GetType() != typeof(bool?)) && (validated.GetType() != typeof(bool)))
	            {
	                throw new ExprValidationException("Filter expression not returning a bool value: '" + validated.ExpressionString + "'");
	            }
	        }

	        // Break a top-level AND into constituent expression nodes
	        List<ExprNode> constituents = new List<ExprNode>();
	        foreach (ExprNode validated in validatedNodes)
	        {
	            if (validated is ExprAndNode)
	            {
	                RecursiveAndConstituents(constituents, validated);
	            }
	            else
	            {
	                constituents.Add(validated);
	            }

	            // Ensure there is no aggregation nodes
	            List<ExprAggregateNode> aggregateExprNodes = new LinkedList<ExprAggregateNode>();
	            ExprAggregateNode.GetAggregatesBottomUp(validated, aggregateExprNodes);
	            if (!aggregateExprNodes.IsEmpty())
	            {
	                throw new ExprValidationException("Aggregation functions not allowed within filters");
	            }
	        }

	        return constituents;
	    }

	    private static void Consolidate(FilterParamExprMap filterParamExprMap)
	    {
	        // consolidate or place in a bool expression (by removing filter spec param from the map)
	        // any filter parameter that feature the same property name and filter operator,
	        // i.e. we are looking for "a!=5 and a!=6"  to transform to "a not in (5,6)" which can match faster
	        // considering that "a not in (5,6) and a not in (7,8)" is "a not in (5, 6, 7, 8)" therefore
	        // we need to consolidate until there is no more work to do
	        IDictionary<Pair<String, FilterOperator>, List<FilterSpecParam>> mapOfParams =
	                new EHashDictionary<Pair<String, FilterOperator>, List<FilterSpecParam>>();

	        bool haveConsolidated;
	        do
	        {
	            haveConsolidated = false;
	            mapOfParams.Clear();

	            // sort into buckets of propertyName + filterOperator combination
	            foreach (FilterSpecParam currentParam in filterParamExprMap.FilterParams)
	            {
	                String propName = currentParam.PropertyName;
	                FilterOperator op = currentParam.FilterOperator;
	                Pair<String, FilterOperator> key = new Pair<String, FilterOperator>(propName, op);

	                List<FilterSpecParam> existingParam = mapOfParams.Get(key);
	                if (existingParam == null)
	                {
	                    existingParam = new List<FilterSpecParam>();
	                    mapOfParams.Put(key, existingParam);
	                }
	                existingParam.Add(currentParam);
	            }

	            foreach (Pair<String, FilterOperator> key in mapOfParams.Keys)
	            {
	                List<FilterSpecParam> existingParams = mapOfParams.Get(key);
	                if (existingParams.Count > 1)
	                {
	                    haveConsolidated = true;
	                    Consolidate(existingParams, filterParamExprMap);
	                }
	            }
	        }
	        while(haveConsolidated);
	    }

	    private static FilterSpecCompiled MakeFilterSpec(EventType eventType, List<ExprNode> constituents, LinkedDictionary<String, EventType> taggedEventTypes)
	    {
	        FilterParamExprMap filterParamExprMap = new FilterParamExprMap();

	        // Make filter parameter for each expression node, if it can be optimized
	        foreach (ExprNode constituent in constituents)
	        {
	            FilterSpecParam param = MakeFilterParam(constituent);
	            filterParamExprMap.Put(constituent, param); // accepts null values as the expression may not be optimized
	        }

	        // Consolidate entries as possible, i.e. (a != 5 and a != 6) is (a not in (5,6)
	        // Removes duplicates for same property and same filter operator for filter service index optimizations
	        Consolidate(filterParamExprMap);

	        // Use all filter parameter and unassigned expressions
	        List<FilterSpecParam> filterParams = new List<FilterSpecParam>();
	        filterParams.AddAll(filterParamExprMap.FilterParams);
	        List<ExprNode> remainingExprNodes = filterParamExprMap.UnassignedExpressions;

	        // any unoptimized expression nodes are put under one AND
	        ExprNode exprNode = null;
	        if (!remainingExprNodes.IsEmpty())
	        {
	            if (remainingExprNodes.Count == 1)
	            {
	                exprNode = remainingExprNodes[0];
	            }
	            else
	            {
	                ExprAndNode andNode = new ExprAndNode();
	                foreach (ExprNode unoptimized in remainingExprNodes)
	                {
	                    andNode.AddChildNode(unoptimized);
	                }
	                exprNode = andNode;
	            }
	        }

	        // if there are bool expressions, add
	        if (exprNode != null)
	        {
	            FilterSpecParamExprNode param = new FilterSpecParamExprNode(PROPERTY_NAME_BOOLEAN_EXPRESSION, FilterOperator.BOOLEAN_EXPRESSION, exprNode, taggedEventTypes);
	            filterParams.Add(param);
	        }

	        return new FilterSpecCompiled(eventType, filterParams);
	    }

	    // consolidate "val != 3 and val != 4 and val != 5"
	    // to "val not in (3, 4, 5)"
	    private static void HandleConsolidateNotEqual(List<FilterSpecParam> paramList, FilterParamExprMap filterParamExprMap)
	    {
	        List<FilterSpecParamInValue> values = new List<FilterSpecParamInValue>();

	        foreach (FilterSpecParam param in paramList)
	        {
	            if (param is FilterSpecParamConstant)
	            {
	                FilterSpecParamConstant constant = (FilterSpecParamConstant) param;
	                values.Add(new InSetOfValuesConstant(constant));
	            }
	            else if (param is FilterSpecParamEventProp)
	            {
	                FilterSpecParamEventProp eventProp = (FilterSpecParamEventProp) param;
	                values.Add(new InSetOfValuesEventProp(eventProp.ResultEventAsName, eventProp.ResultEventProperty,
	                        eventProp.IsMustCoerce(), eventProp.CoercionType));
	            }
	            else
	            {
	                throw new ArgumentException("Unknown filter parameter:" + param.ToString());
	            }

	            filterParamExprMap.RemoveValue(param);
	        }
	    }

	    /// <summary>
	    /// For a given expression determine if this is optimizable and create the filter parameter
	    /// representing the expression, or null if not optimizable.
	    /// </summary>
	    /// <param name="constituent">is the expression to look at</param>
	    /// <returns>filter parameter representing the expression, or null</returns>
	    /// <throws>ExprValidationException if the expression is invalid</throws>
	    public static FilterSpecParam MakeFilterParam(ExprNode constituent)
	    {
	        // Is this expresson node a simple compare, i.e. a=5 or b<4; these can be indexed
	        if ((constituent is ExprEqualsNode) ||
	            (constituent is ExprRelationalOpNode))
	        {
	            FilterSpecParam param = HandleEqualsAndRelOp(constituent);
	            if (param != null)
	            {
	                return param;
	            }
	        }

	        // Is this expresson node a simple compare, i.e. a=5 or b<4; these can be indexed
	        if (constituent is ExprInNode)
	        {
	            FilterSpecParam param = HandleInSetNode((ExprInNode)constituent);
	            if (param != null)
	            {
	                return param;
	            }
	        }

	        if (constituent is ExprBetweenNode)
	        {
	            FilterSpecParam param = HandleRangeNode((ExprBetweenNode)constituent);
	            if (param != null)
	            {
	                return param;
	            }
	        }

	        return null;
	    }

	    private static FilterSpecParam HandleRangeNode(ExprBetweenNode betweenNode)
	    {
	        ExprNode left = betweenNode.ChildNodes[0];
	        if (left is ExprIdentNode)
	        {
	            ExprIdentNode identNode = (ExprIdentNode) left;
	            String propertyName = identNode.ResolvedPropertyName;
	            FilterOperator op = FilterOperator.ParseRangeOperator(
	            	betweenNode.IsLowEndpointIncluded,
	            	betweenNode.IsHighEndpointIncluded,
					betweenNode.IsNotBetween);

	            FilterSpecParamRangeValue low = HandleRangeNodeEndpoint(betweenNode.ChildNodes[1]);
	            FilterSpecParamRangeValue high = HandleRangeNodeEndpoint(betweenNode.ChildNodes[2]);

	            if ((low != null) && (high != null))
	            {
	                return new FilterSpecParamRange(propertyName, op, low, high);
	            }
	        }
	        return null;
	    }

	    private static FilterSpecParamRangeValue HandleRangeNodeEndpoint(ExprNode endpoint)
	    {
	        // constant
	        if (endpoint is ExprConstantNode)
	        {
	            ExprConstantNode node = (ExprConstantNode) endpoint;
	            Number result = (Number) node.Evaluate(null, true);
	            return new RangeValueDouble(result.DoubleValue());
	        }

	        // or property
	        if (endpoint is ExprIdentNode)
	        {
	            ExprIdentNode identNodeInner = (ExprIdentNode) endpoint;
	            if (identNodeInner.StreamId == 0)
	            {
	                return null;
	            }
	            return new RangeValueEventProp(identNodeInner.ResolvedStreamName, identNodeInner.ResolvedPropertyName);
	        }

	        return null;
	    }

	    private static FilterSpecParam HandleInSetNode(ExprInNode constituent)
	    {
	        ExprNode left = constituent.ChildNodes[0];
	        if (left is ExprIdentNode)
	        {
	            ExprIdentNode identNodeInSet = (ExprIdentNode) left;
	            String propertyName = identNodeInSet.ResolvedPropertyName;
	            FilterOperator op = FilterOperator.IN_LIST_OF_VALUES;
	            if (constituent.IsNotIn)
	            {
	                op = FilterOperator.NOT_IN_LIST_OF_VALUES;
	            }

	            List<FilterSpecParamInValue> listofValues = new List<FilterSpecParamInValue>();
	            IEnumerator<ExprNode> it = constituent.ChildNodes.GetEnumerator();
                it.MoveNext(); // ignore the first node as it's the identifier
	            while (it.MoveNext())
	            {
	                ExprNode subNode = it.Current;
	                if (subNode is ExprConstantNode)
	                {
	                    ExprConstantNode constantNode = (ExprConstantNode) subNode;
	                    Object constant = constantNode.Evaluate(null, true);
	                    constant = HandleConstantsCoercion(identNodeInSet, constant);
	                    listofValues.Add(new InSetOfValuesConstant(constant));
	                }
	                if (subNode is ExprIdentNode)
	                {
	                    ExprIdentNode identNodeInner = (ExprIdentNode) subNode;
	                    if (identNodeInner.StreamId == 0)
	                    {
	                        break; // for same event evals use the bool expression, via count compare failing below
	                    }

	                    bool isMustCoerce = false;
	                    Type numericCoercionType = identNodeInSet.GetType();
	                    if (identNodeInner.GetType() != identNodeInSet.GetType())
	                    {
	                        if (TypeHelper.IsNumeric(identNodeInSet.GetType()))
	                        {
	                            if (!TypeHelper.CanCoerce(identNodeInner.GetType(), identNodeInSet.GetType()))
	                            {
	                                ThrowConversionError(identNodeInner.GetType(), identNodeInSet.GetType(), identNodeInSet.ResolvedPropertyName);
	                            }
	                            isMustCoerce = true;
	                        }
	                    }
	                    listofValues.Add(new InSetOfValuesEventProp(identNodeInner.ResolvedStreamName, identNodeInner.ResolvedPropertyName, isMustCoerce, numericCoercionType));
	                }
	            }

	            // Fallback if not all values in the in-node can be resolved to properties or constants
	            if (listofValues.Count == constituent.ChildNodes.Count - 1)
	            {
	                return new FilterSpecParamIn(propertyName, op, listofValues);
	            }
	        }
	        return null;
	    }

	    private static FilterSpecParam HandleEqualsAndRelOp(ExprNode constituent)
	    {
	        FilterOperator op;
	        if (constituent is ExprEqualsNode)
	        {
	            op = FilterOperator.EQUAL;
	            if (((ExprEqualsNode) constituent).IsNotEquals)
	            {
	                op = FilterOperator.NOT_EQUAL;
	            }
	        }
	        else
	        {
	            ExprRelationalOpNode relNode = (ExprRelationalOpNode) constituent;
	            if (relNode.RelationalOpEnum == RelationalOpEnum.GT)
	            {
	                op = FilterOperator.GREATER;
	            }
	            else if (relNode.RelationalOpEnum == RelationalOpEnum.LT)
	            {
	                op = FilterOperator.LESS;
	            }
	            else if (relNode.RelationalOpEnum == RelationalOpEnum.LE)
	            {
	                op = FilterOperator.LESS_OR_EQUAL;
	            }
	            else if (relNode.RelationalOpEnum == RelationalOpEnum.GE)
	            {
	                op = FilterOperator.GREATER_OR_EQUAL;
	            }
	            else
	            {
	                throw new IllegalStateException("Opertor '" + relNode.RelationalOpEnum + "' not mapped");
	            }
	        }

	        ExprNode left = constituent.ChildNodes[0];
	        ExprNode right = constituent.ChildNodes[1];

	        // check identifier and constant combination
	        if ((right is ExprConstantNode) && (left is ExprIdentNode))
	        {
	            ExprIdentNode identNode = (ExprIdentNode) left;
	            ExprConstantNode constantNode = (ExprConstantNode) right;
	            String propertyName = identNode.ResolvedPropertyName;
	            Object constant = constantNode.Evaluate(null, true);
	            constant = HandleConstantsCoercion(identNode, constant);

	            return new FilterSpecParamConstant(propertyName, op, constant);
	        }
	        if ((left is ExprConstantNode) && (right is ExprIdentNode))
	        {
	            ExprIdentNode identNode = (ExprIdentNode) right;
	            ExprConstantNode constantNode = (ExprConstantNode) left;
	            String propertyName = identNode.ResolvedPropertyName;
	            Object constant = constantNode.Evaluate(null, true);
	            constant = HandleConstantsCoercion(identNode, constant);
	            return new FilterSpecParamConstant(propertyName, op, constant);
	        }
	        // check identifier and expression containing other streams
	        if ((left is ExprIdentNode) && (right is ExprIdentNode))
	        {
	            ExprIdentNode identNodeLeft = (ExprIdentNode) left;
	            ExprIdentNode identNodeRight = (ExprIdentNode) right;

	            if ((identNodeLeft.StreamId == 0) && (identNodeRight.StreamId != 0))
	            {
	                return HandleProperty(op, identNodeLeft, identNodeRight);
	            }
	            if ((identNodeRight.StreamId == 0) && (identNodeLeft.StreamId != 0))
	            {
	                return HandleProperty(op, identNodeRight, identNodeLeft);
	            }
	        }
	        return null;
	    }

	    private static FilterSpecParamEventProp HandleProperty(FilterOperator op, ExprIdentNode identNodeLeft, ExprIdentNode identNodeRight)
	    {
	        String propertyName = identNodeLeft.ResolvedPropertyName;

	        bool isMustCoerce = false;
	        Type numericCoercionType = identNodeLeft.GetType();
	        if (identNodeRight.GetType() != identNodeLeft.GetType())
	        {
	            if (TypeHelper.IsNumeric(identNodeRight.GetType()))
	            {
	                if (!TypeHelper.CanCoerce(identNodeRight.GetType(), identNodeLeft.GetType()))
	                {
	                    ThrowConversionError(identNodeRight.GetType(), identNodeLeft.GetType(), identNodeLeft.ResolvedPropertyName);
	                }
	                isMustCoerce = true;
	            }
	        }

	        return new FilterSpecParamEventProp(propertyName, op, identNodeRight.ResolvedStreamName, identNodeRight.ResolvedPropertyName,
	                isMustCoerce, numericCoercionType);
	    }

	    private static void ThrowConversionError(Type fromType, Type toType, String propertyName)
	    {
	        String text = "Implicit conversion from datatype '" +
	                fromType.FullName +
	                "' to '" +
	                toType.FullName +
	                "' for property '" +
	                propertyName +
	                "' is not allowed (strict filter type coercion)";
	        throw new ExprValidationException(text);
	    }

	    // expressions automatically coerce to the most upwards type
	    // filters require the same type
	    private static Object HandleConstantsCoercion(ExprIdentNode identNode, Object constant)
	    {
	    	Type identNodeType = identNode.GetType();
	        if (!TypeHelper.IsNumeric(identNodeType))
	        {
	            return constant;    // no coercion required, other type checking performed by expression this comes from
	        }

	        if (constant == null)  // null constant type
	        {
	            return null;
	        }

	        if (!TypeHelper.CanCoerce(constant.GetType(), identNodeType))
	        {
	        	ThrowConversionError(constant.GetType(), identNodeType, identNode.ResolvedPropertyName);
	        }

	        Type identNodeTypeBoxed = TypeHelper.GetBoxedType(identNodeType);
	        return TypeHelper.CoerceBoxed((Number) constant, identNodeTypeBoxed);
	    }

	    private static void RecursiveAndConstituents(List<ExprNode> constituents, ExprNode exprNode)
	    {
	        foreach (ExprNode inner in exprNode.ChildNodes)
	        {
	            if (inner is ExprAndNode)
	            {
	                RecursiveAndConstituents(constituents, inner);
	            }
	            else
	            {
	                constituents.Add(inner);
	            }
	        }
	    }
	}
}
