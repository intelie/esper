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
	    private readonly IDictionary<ExprNodeAdapter, EventEvaluator> evaluatorsMap;
	    private readonly ReadWriteLock constantsMapRWLock;

	    /// <summary>Constructs the index for multiple-exact matches.</summary>
	    /// <param name="eventType">
	    /// describes the event type and is used to obtain a getter instance for the property
	    /// </param>
	    public FilterParamIndexBooleanExpr(EventType eventType)
	        : base(FilterOperator.BOOLEAN_EXPRESSION)
	    {
	        evaluatorsMap = new EHashDictionary<ExprNodeAdapter, EventEvaluator>();
	        constantsMapRWLock = new ReentrantReadWriteLock();
	    }

	    public EventEvaluator Get(Object filterConstant)
	    {
	        ExprNodeAdapter keyValues = (ExprNodeAdapter) filterConstant;
	        return evaluatorsMap.Get(keyValues);
	    }

	    public void Put(Object filterConstant, EventEvaluator evaluator)
	    {
	        ExprNodeAdapter keys = (ExprNodeAdapter) filterConstant;
	        evaluatorsMap.Put(keys, evaluator);
	    }

	    public bool Remove(Object filterConstant)
	    {
	        ExprNodeAdapter keys = (ExprNodeAdapter) filterConstant;
	        return evaluatorsMap.Remove(keys) != null;
	    }

	    public int Count
	    {
            get { return evaluatorsMap.Count; }
	    }

	    public ReaderWriterLock ReadWriteLock
	    {
            get { return constantsMapRWLock; }
	    }

	    public void MatchEvent(EventBean eventBean, Collection<FilterHandle> matches)
	    {
	        if (log.IsDebugEnabled())
	        {
	            log.Debug(".match (" + Thread.CurrentThread().Id + ")");
	        }

	        List<EventEvaluator> evaluators = new ArrayList<EventEvaluator>();
	        constantsMapRWLock.ReadLock().Lock();
	        foreach (ExprNodeAdapter exprNodeAdapter in evaluatorsMap.KeySet())
	        {
	            if (exprNodeAdapter.Evaluate(eventBean))
	            {
	                evaluators.Add(evaluatorsMap.Get(exprNodeAdapter));
	            }
	        }
	        constantsMapRWLock.ReadLock().Unlock();

	        foreach (EventEvaluator evaluator in evaluators)
	        {
	            evaluator.MatchEvent(eventBean, matches);
	        }
	    }

	    private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
}
