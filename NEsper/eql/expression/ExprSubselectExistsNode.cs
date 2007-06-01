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
using net.esper.eql.spec;
using net.esper.events;
using org.apache.commons.logging;

namespace net.esper.eql.expression
{
	/// <summary>
    /// Represents an exists-subselect in an expression tree.
    /// </summary>
	
    public class ExprSubselectExistsNode : ExprSubselectNode
	{
	    private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);

	    /// <summary>Ctor.</summary>
	    /// <param name="statementSpec">
	    /// is the subquery statement spec from the parser, unvalidated
	    /// </param>
	    public ExprSubselectExistsNode(StatementSpecRaw statementSpec)
			: base(statementSpec)
	    {
	    }

	    public bool IsAllowWildcardSelect
	    {
            get { return true; }
	    }

        public Type ReturnType
	    {
            get { return typeof(bool?); }
	    }

	    public void Validate(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService, ViewResourceDelegate viewResourceDelegate)
	    {
	    }

	    public Object Evaluate(EventBean[] eventsPerStream, bool isNewData, Set<EventBean> matchingEvents)
	    {
	        if (matchingEvents == null)
	        {
	            return false;
	        }
	        if (matchingEvents.Size() == 0)
	        {
	            return false;
	        }

	        if (filterExpr == null)
	        {
	            return true;
	        }

	        // Evaluate filter
	        EventBean[] events = new EventBean[eventsPerStream.length + 1];
	        System.Arraycopy(eventsPerStream, 0, events, 1, eventsPerStream.length);

	        foreach (EventBean subselectEvent in matchingEvents)
	        {
	            // Prepare filter expression event list
	            events[0] = subselectEvent;

	            bool? pass = (bool?) filterExpr.Evaluate(events, true);
	            if ((pass != null) && (pass))
	            {
	                return true;
	            }
	        }

	        return false;
	    }
	}
} // End of namespace
