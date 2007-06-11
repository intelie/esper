///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.eql.expression;

namespace net.esper.filter
{
	/// <summary>
	/// A two-sided map for filter parameters mapping filter expression nodes to filter parameters and
	/// back. For use in optimizing filter expressions.
	/// </summary>
	public class FilterParamExprMap
	{
	    private EDictionary<ExprNode, FilterSpecParam> exprNodes;
	    private EDictionary<FilterSpecParam, ExprNode> specParams;

	    /// <summary>Ctor.</summary>
	    public FilterParamExprMap()
	    {
	        exprNodes = new EHashDictionary<ExprNode, FilterSpecParam>();
	        specParams = new EHashDictionary<FilterSpecParam, ExprNode>();
	    }

	    /// <summary>Add a node and filter param.</summary>
	    /// <param name="exprNode">is the node to add</param>
	    /// <param name="param">is null if the expression node has not optimized form</param>
	    public void Put(ExprNode exprNode, FilterSpecParam param)
	    {
	        exprNodes[exprNode] = param;
	        if (param != null)
	        {
	            specParams.Put(param, exprNode);
	        }
	    }

	    /// <summary>Returns all expression nodes for which no filter parameter exists.</summary>
	    /// <returns>list of expression nodes</returns>
	    public List<ExprNode> UnassignedExpressions
	    {
	    	get
	    	{
		        List<ExprNode> unassigned = new List<ExprNode>();
		        foreach (ExprNode exprNode in exprNodes.Keys)
		        {
		            if (!exprNodes.ContainsKey(exprNode))
		            {
		                unassigned.Add(exprNode);
		            }
		        }
		        return unassigned;
	    	}
	    }

	    /// <summary>Returns all filter parameters.</summary>
	    /// <returns>filter parameters</returns>
	    public ICollection<FilterSpecParam> FilterParams
	    {
	    	get { return specParams.Keys; }
	    }

	    /// <summary>Removes a filter parameter and it's associated expression node</summary>
	    /// <param name="param">is the parameter to remove</param>
	    public void RemoveEntry(FilterSpecParam param)
	    {
	        ExprNode exprNode = specParams.Fetch(param);
	        if (exprNode == null)
	        {
	            throw new IllegalStateException("Not found in collection param: " + param);
	        }

	        specParams.Remove(param);
	        exprNodes.Remove(exprNode);
	    }

	    /// <summary>Remove a filter parameter leaving the expression node in place.</summary>
	    /// <param name="param">filter parameter to remove</param>
	    public void RemoveValue(FilterSpecParam param)
	    {
	        ExprNode exprNode = specParams.Fetch(param);
	        if (exprNode == null)
	        {
	            throw new IllegalStateException("Not found in collection param: " + param);
	        }

	        specParams.Remove(param);
	        exprNodes[exprNode] = null;
	    }
	}
}
