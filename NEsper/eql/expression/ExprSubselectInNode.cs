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
using net.esper.util;

using org.apache.commons.logging;

namespace net.esper.eql.expression
{
	/// <summary>
    /// Represents a subselect in an expression tree.
    /// </summary>
	public class ExprSubselectInNode : ExprSubselectNode
	{
	    private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	    
	    private bool isNotIn;
	    private bool mustCoerce;
	    private Type coercionType;

	    /// <summary>Ctor.</summary>
	    /// <param name="statementSpec">
	    /// is the subquery statement spec from the parser, unvalidated
	    /// </param>
	    public ExprSubselectInNode(StatementSpecRaw statementSpec)
		    : base(statementSpec)
		{
	    }

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
            get { return typeof(bool?); }
        }

	    /// <summary>Indicate that this is a not-in subquery.</summary>
	    /// <param name="notIn">is true for not-in, or false for regular 'in'</param>
	    public void SetNotIn(bool notIn)
	    {
	        isNotIn = notIn;
	    }

	    public override void Validate(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService, ViewResourceDelegate viewResourceDelegate)
	    {
	        if (this.ChildNodes.Count != 1)
	        {
	            throw new ExprValidationException("The Subselect-IN requires 1 child expression");
	        }

	        // Must be the same boxed type returned by expressions under this
	        Type typeOne = TypeHelper.GetBoxedType(this.ChildNodes[0].ReturnType);
	        Type typeTwo = selectClause.ReturnType;

	        // Null constants can be compared for any type
	        if ((typeOne == null) || (typeTwo == null))
	        {
	            return;
	        }

	        // Get the common type such as Bool, String or Double and Long
	        try
	        {
	            coercionType = TypeHelper.GetCompareToCoercionType(typeOne, typeTwo);
	        }
	        catch (ArgumentException ex)
	        {
	            throw new ExprValidationException("Implicit conversion from datatype '" +
	                    typeTwo.FullName +
	                    "' to '" +
	                    typeOne.FullName +
	                    "' is not allowed");
	        }

	        // Check if we need to coerce
	        if ((coercionType == TypeHelper.GetBoxedType(typeOne)) &&
	            (coercionType == TypeHelper.GetBoxedType(typeTwo)))
	        {
	            mustCoerce = false;
	        }
	        else
	        {
	            if (!TypeHelper.IsNumeric(coercionType))
	            {
	                throw new IllegalStateException("Coercion type " + coercionType + " not numeric");
	            }
	            mustCoerce = true;
	        }

	    }

	    public override Object Evaluate(EventBean[] eventsPerStream, bool isNewData, Set<EventBean> matchingEvents)
	    {
	        if (matchingEvents == null)
	        {
	            return isNotIn;
	        }
	        if (matchingEvents.Count == 0)
	        {
	            return isNotIn;
	        }

	        // Filter according to the filter expression
	        // Evaluate the select expression for each remaining row
	        // Check if any of the results match the child expression, using coercion
	        Set<EventBean> matchedFilteredEvents = matchingEvents;

	        // Evaluate filter
	        EventBean[] events = new EventBean[eventsPerStream.Length + 1];
            Array.Copy(eventsPerStream, 0, events, 1, eventsPerStream.Length);

	        if (filterExpr != null)
	        {
	            matchedFilteredEvents = new HashSet<EventBean>();
	            foreach (EventBean subselectEvent in matchingEvents)
	            {
	                // Prepare filter expression event list
	                events[0] = subselectEvent;

	                // Eval filter expression
	                bool? pass = (bool?) filterExpr.Evaluate(events, true);
	                if (pass ?? false)
	                {
	                    matchedFilteredEvents.Add(subselectEvent);
	                }
	            }
	        }
	        if (matchedFilteredEvents.Count == 0)
	        {
	            return isNotIn;
	        }

	        // Evaluate the child expression
	        Object leftResult = this.ChildNodes[0].Evaluate(eventsPerStream, isNewData);

	        // Evaluate each select until we have a match
	        foreach (EventBean _event in matchedFilteredEvents)
	        {
	            events[0] = _event;
	            Object rightResult = selectClause.Evaluate(events, true);

	            if (leftResult == null)
	            {
	                if (rightResult == null)
	                {
	                    return !isNotIn;
	                }
	                continue;
	            }
	            if (rightResult == null)
	            {
	                continue;
	            }

	            if (!mustCoerce)
	            {
	                if (leftResult.Equals(rightResult))
	                {
	                    return !isNotIn;
	                }
	            }
	            else
	            {
	                Object left = TypeHelper.CoerceBoxed(leftResult, coercionType);
	                Object right = TypeHelper.CoerceBoxed(rightResult, coercionType);
	                if (Object.Equals( left, right ))
	                {
	                    return !isNotIn;
	                }
	            }
	        }

	        return isNotIn;
	    }
	}
}
