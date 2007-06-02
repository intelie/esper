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
using net.esper.eql.core;
using net.esper.eql.spec;
using net.esper.events;

using org.apache.commons.logging;

namespace net.esper.eql.expression
{
	/// <summary>
    /// Represents a subselect in an expression tree.
    /// </summary>
	public class ExprSubselectRowNode : ExprSubselectNode
	{
	    private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);

	    /// <summary>Ctor.</summary>
	    /// <param name="statementSpec">
	    /// is the subquery statement spec from the parser, unvalidated
	    /// </param>
	    public ExprSubselectRowNode(StatementSpecRaw statementSpec)
			: base(statementSpec)
	    {
	    }

	    public override bool IsAllowWildcardSelect
	    {
            get { return false; }
	    }

	    public Type ResultType
	    {
	    	get { return selectClause.Type; }
	    }

	    public void Validate(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService, ViewResourceDelegate viewResourceDelegate)
	    {
	    }

	    public Object Evaluate(EventBean[] eventsPerStream, bool isNewData, ISet<EventBean> matchingEvents)
	    {
	        if (matchingEvents == null)
	        {
	            return null;
	        }
	        if (matchingEvents.Count == 0)
	        {
	            return null;
	        }
	        if ((filterExpr == null) && (matchingEvents.Size() > 1))
	        {
	            log.Warn("Subselect returned more then one row in subselect '" + ToExpressionString() + "', returning null result");
	            return null;
	        }

	        // Evaluate filter
	        EventBean subSelectResult = null;
	        EventBean[] events = new EventBean[eventsPerStream.length + 1];
	        System.Arraycopy(eventsPerStream, 0, events, 1, eventsPerStream.length);

	        if (filterExpr != null)
	        {
	            foreach (EventBean subselectEvent in matchingEvents)
	            {
	                // Prepare filter expression event list
	                events[0] = subselectEvent;

	                bool? pass = (bool?) filterExpr.Evaluate(events, true);
	                if (pass ?? false)
	                {
	                    if (subSelectResult != null)
	                    {
	                        log.Warn("Subselect returned more then one row in subselect '" + ToExpressionString() + "', returning null result");
	                        return null;
	                    }
	                    subSelectResult = subselectEvent;
	                }
	            }

	            if (subSelectResult == null)
	            {
	                return null;
	            }
	        }
	        else
	        {
	            subSelectResult = matchingEvents.Iterator().Next();
	        }

	        events[0] = subSelectResult;
	        Object result = selectClause.Evaluate(events, true);
	        return result;
	    }
	}
}
