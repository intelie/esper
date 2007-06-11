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
using net.esper.events;
using net.esper.pattern;

namespace net.esper.filter
{
	/// <summary>
	/// This class represents an arbitrary expression node returning a bool value as a filter parameter in an {@link FilterSpecCompiled} filter specification.
	/// </summary>
	public sealed class FilterSpecParamExprNode : FilterSpecParam
	{
	    private readonly ExprNode exprNode;
	    private readonly ExprNodeAdapter adapter;
	    private readonly LinkedDictionary<String, EventType> taggedEventTypes;

	    /// <summary>Ctor.</summary>
	    /// <param name="propertyName">is the event property name</param>
	    /// <param name="filterOperator">is expected to be the BOOLEAN_EXPR operator</param>
	    /// <param name="exprNode">represents the bool expression</param>
	    /// <param name="taggedEventTypes">
	    /// is null if the expression doesn't need other streams, or is filled with a ordered list of stream names and types
	    /// </param>
	    /// <throws>ArgumentException for illegal args</throws>
	    public FilterSpecParamExprNode(String propertyName,
	                             FilterOperator filterOperator,
	                             ExprNode exprNode,
	                             LinkedDictionary<String, EventType> taggedEventTypes)
	        : base(propertyName, filterOperator)
	    {
	        if (filterOperator != FilterOperator.BOOLEAN_EXPRESSION)
	        {
	            throw new ArgumentException("Invalid filter operator for filter expression node");
	        }
	        this.exprNode = exprNode;
	        this.taggedEventTypes = taggedEventTypes;

	        adapter = new ExprNodeAdapter(exprNode);
	    }

	    /// <summary>
	    /// Returns the expression node of the bool expression this filter parameter represents.
	    /// </summary>
	    /// <returns>expression node</returns>
	    public ExprNode ExprNode
	    {
	    	get { return exprNode; }
	    }

	    /// <summary>
	    /// Returns the map of tag/stream names to event types that the filter expressions map use (for patterns)
	    /// </summary>
	    /// <returns>IDictionary</returns>
	    public LinkedDictionary<String, EventType> GetTaggedEventTypes()
	    {
	        return taggedEventTypes;
	    }

	    public override Object GetFilterValue(MatchedEventMap matchedEvents)
	    {
	        if (taggedEventTypes != null)
	        {
	            EventBean[] events = new EventBean[taggedEventTypes.Count + 1];
	            int count = 1;
	            foreach (String tag in taggedEventTypes.Keys)
	            {
	                events[count] = matchedEvents.GetMatchingEvent(tag);
	                count++;
	            }
	            adapter.Prototype = events;
	        }
	        return adapter;
	    }

	    public override String ToString()
	    {
	        return base.ToString() + "  exprNode=" + exprNode.ToString();
	    }

	    public override bool Equals(Object obj)
	    {
	        if (this == obj)
	        {
	            return true;
	        }

	        if (!(obj is FilterSpecParamExprNode))
	        {
	            return false;
	        }

	        FilterSpecParamExprNode other = (FilterSpecParamExprNode) obj;
	        if (!base.Equals(other))
	        {
	            return false;
	        }

	        if (exprNode != other.exprNode)
	        {
	            return false;
	        }

	        return true;
	    }
	    
		public override int GetHashCode()
		{
			return
				base.GetHashCode() * 31 +
				exprNode.GetHashCode() ;
		}
	}
} // End of namespace
