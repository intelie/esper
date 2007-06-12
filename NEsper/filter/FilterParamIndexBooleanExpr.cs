///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;
using System.Threading;

using net.esper.events;
using net.esper.compat;

using org.apache.commons.logging;

namespace net.esper.filter
{
	/// <summary>Index that simply maintains a list of bool expressions.</summary>
	public sealed class FilterParamIndexBooleanExpr : FilterParamIndexBase
	{
	    private readonly EDictionary<ExprNodeAdapter, EventEvaluator> evaluatorsMap;
	    private readonly ReaderWriterLock constantsMapRWLock;

	    /// <summary>Constructs the index for multiple-exact matches.</summary>
	    /// <param name="eventType">
	    /// describes the event type and is used to obtain a getter instance for the property
	    /// </param>
	    public FilterParamIndexBooleanExpr(EventType eventType)
	        : base(FilterOperator.BOOLEAN_EXPRESSION)
	    {
	        evaluatorsMap = new EHashDictionary<ExprNodeAdapter, EventEvaluator>();
	        constantsMapRWLock = new ReaderWriterLock();
	    }

        /// <summary>
        /// Get the event evaluation instance associated with the constant. Returns null if no entry found for the constant.
        /// The calling class must make sure that access to the underlying resource is protected
        /// for multi-threaded access, the GetReadWriteLock() method must supply a lock for this purpose.
        /// Store the event evaluation instance for the given constant. Can override an existing value
        /// for the same constant.
        /// The calling class must make sure that access to the underlying resource is protected
        /// for multi-threaded access, the GetReadWriteLock() method must supply a lock for this purpose.
        /// </summary>
        /// <value></value>
        /// <returns>
        /// event evaluator stored for the filter constant, or null if not found
        /// </returns>
	    public override EventEvaluator this[Object filterConstant]
	    {
	    	get
	    	{
		        ExprNodeAdapter keyValues = (ExprNodeAdapter) filterConstant;
		        return evaluatorsMap.Fetch(keyValues);
	    	}
			set
		    {
		        ExprNodeAdapter keys = (ExprNodeAdapter) filterConstant;
		        evaluatorsMap[keys] = value;
		    }
	    }

        /// <summary>
        /// Remove the event evaluation instance for the given constant. Returns true if
        /// the constant was found, or false if not.
        /// The calling class must make sure that access to the underlying resource is protected
        /// for multi-threaded writes, the GetReadWriteLock() method must supply a lock for this purpose.
        /// </summary>
        /// <param name="filterConstant">is the value supplied in the filter paremeter</param>
        /// <returns>
        /// true if found and removed, false if not found
        /// </returns>
	    public override bool Remove(Object filterConstant)
	    {
	        ExprNodeAdapter keys = (ExprNodeAdapter) filterConstant;
	        return evaluatorsMap.Remove(keys);
	    }

        /// <summary>
        /// Return the number of distinct filter parameter constants stored.
        /// The calling class must make sure that access to the underlying resource is protected
        /// for multi-threaded writes, the GetReadWriteLock() method must supply a lock for this purpose.
        /// </summary>
        /// <value></value>
        /// <returns>Number of entries in index</returns>
	    public override int Count
	    {
            get { return evaluatorsMap.Count; }
	    }

        /// <summary>
        /// Supplies the lock for protected access.
        /// </summary>
        /// <value></value>
        /// <returns>lock</returns>
	    public override ReaderWriterLock ReadWriteLock
	    {
            get { return constantsMapRWLock; }
	    }

        /// <summary>
        /// Matches the event.
        /// </summary>
        /// <param name="eventBean">The event bean.</param>
        /// <param name="matches">The matches.</param>
	    public override void MatchEvent(EventBean eventBean, IList<FilterHandle> matches)
	    {
	        if (log.IsDebugEnabled)
	        {
	            log.Debug(".match (" + Thread.CurrentThread.ManagedThreadId + ")");
	        }

	        List<EventEvaluator> evaluators = new List<EventEvaluator>();
            using (new ReaderLock(constantsMapRWLock))
            {
                foreach (KeyValuePair<ExprNodeAdapter, EventEvaluator> pair in evaluatorsMap)
                {
                    ExprNodeAdapter exprNodeAdapter = pair.Key;
                    if (exprNodeAdapter.Evaluate(eventBean))
                    {
                        evaluators.Add(pair.Value);
                    }
                }
            }

	        foreach (EventEvaluator evaluator in evaluators)
	        {
	            evaluator.MatchEvent(eventBean, matches);
	        }
	    }

	    private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
}
