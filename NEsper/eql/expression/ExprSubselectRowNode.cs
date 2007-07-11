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

        /// <summary>
        /// Return true to indicate that wildcard selects are acceptable, or false to indicate wildcard is not acceptable
        /// </summary>
        /// <value></value>
        /// <returns>true for yes-wildcards, false for no-wildcards</returns>
	    public override bool IsAllowWildcardSelect
	    {
            get { return false; }
	    }

        /// <summary>
        /// Returns the type that the node's evaluate method returns an instance of.
        /// </summary>
        /// <value>The type.</value>
        /// <returns> type returned when evaluated
        /// </returns>
        /// <throws>ExprValidationException thrown when validation failed </throws>
	    public override Type ReturnType
	    {
            get { return selectClause.ReturnType; }
	    }

        /// <summary>
        /// Validate node.
        /// </summary>
        /// <param name="streamTypeService">serves stream event type info</param>
        /// <param name="methodResolutionService">for resolving class names in library method invocations</param>
        /// <param name="viewResourceDelegate"></param>
        /// <throws>ExprValidationException thrown when validation failed </throws>
	    public override void Validate(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService, ViewResourceDelegate viewResourceDelegate)
	    {
	    }

        /// <summary>
        /// Evaluate the subquery expression returning an evaluation result object.
        /// </summary>
        /// <param name="eventsPerStream">is the events for each stream in a join</param>
        /// <param name="isNewData">is true for new data, or false for old data</param>
        /// <param name="matchingEvents">is filtered results from the table of stored subquery events</param>
        /// <returns>evaluation result</returns>
	    public override Object Evaluate(EventBean[] eventsPerStream, bool isNewData, Set<EventBean> matchingEvents)
	    {
	        if (matchingEvents == null)
	        {
	            return null;
	        }
	        if (matchingEvents.Count == 0)
	        {
	            return null;
	        }
	        if ((filterExpr == null) && (matchingEvents.Count > 1))
	        {
	            log.Warn("Subselect returned more then one row in subselect '" + ExpressionString + "', returning null result");
	            return null;
	        }

	        // Evaluate filter
	        EventBean subSelectResult = null;
	        EventBean[] events = new EventBean[eventsPerStream.Length + 1];
	        Array.Copy(eventsPerStream, 0, events, 1, eventsPerStream.Length);

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
	                        log.Warn("Subselect returned more then one row in subselect '" + ExpressionString + "', returning null result");
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
                
	            IEnumerator<EventBean> tempEnum = matchingEvents.GetEnumerator();
	            tempEnum.MoveNext();
	            subSelectResult = tempEnum.Current;
	        }

	        events[0] = subSelectResult;
	        Object result = selectClause.Evaluate(events, true);
	        return result;
	    }
	}
}
