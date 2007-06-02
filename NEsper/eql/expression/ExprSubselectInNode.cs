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
using net.esper.eql.compat;
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

	    public bool IsAllowWildcardSelect
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

        public abstract Type ReturnType
        {
            get { return typeof(bool?); }
        }

	    /// <summary>Indicate that this is a not-in subquery.</summary>
	    /// <param name="notIn">is true for not-in, or false for regular 'in'</param>
	    public void SetNotIn(bool notIn)
	    {
	        isNotIn = notIn;
	    }

	    public void Validate(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService, ViewResourceDelegate viewResourceDelegate)
	    {
	        if (this.ChildNodes.Size() != 1)
	        {
	            throw new ExprValidationException("The Subselect-IN requires 1 child expression");
	        }

	        // Must be the same boxed type returned by expressions under this
	        Class typeOne = TypeHelper.GetBoxedType(this.ChildNodes.Get(0).Type);
	        Class typeTwo = selectClause.Type;

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
	        catch (IllegalArgumentException ex)
	        {
	            throw new ExprValidationException("Implicit conversion from datatype '" +
	                    typeTwo.SimpleName +
	                    "' to '" +
	                    typeOne.SimpleName +
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

	    public Object Evaluate(EventBean[] eventsPerStream, bool isNewData, ISet<EventBean> matchingEvents)
	    {
	        if (matchingEvents == null)
	        {
	            return isNotIn;
	        }
	        if (matchingEvents.Size() == 0)
	        {
	            return isNotIn;
	        }

	        // Filter according to the filter expression
	        // Evaluate the select expression for each remaining row
	        // Check if any of the results match the child expression, using coercion
	        Collection<EventBean> matchedFilteredEvents = matchingEvents;

	        // Evaluate filter
	        EventBean[] events = new EventBean[eventsPerStream.length + 1];
	        System.Arraycopy(eventsPerStream, 0, events, 1, eventsPerStream.length);

	        if (filterExpr != null)
	        {
	            matchedFilteredEvents = new ArrayList<EventBean>();
	            foreach (EventBean subselectEvent in matchingEvents)
	            {
	                // Prepare filter expression event list
	                events[0] = subselectEvent;

	                // Eval filter expression
	                Boolean pass = (Boolean) filterExpr.Evaluate(events, true);
	                if ((pass != null) && (pass))
	                {
	                    matchedFilteredEvents.Add(subselectEvent);
	                }
	            }
	        }
	        if (matchedFilteredEvents.Size() == 0)
	        {
	            return isNotIn;
	        }

	        // Evaluate the child expression
	        Object leftResult = this.ChildNodes.Get(0).Evaluate(eventsPerStream, isNewData);

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
	                Number left = TypeHelper.CoerceBoxed((Number) leftResult, coercionType);
	                Number right = TypeHelper.CoerceBoxed((Number) rightResult, coercionType);
	                if (left.Equals(right))
	                {
	                    return !isNotIn;
	                }
	            }
	        }

	        return isNotIn;
	    }
	}
}
