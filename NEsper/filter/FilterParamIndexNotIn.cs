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

using net.esper.compat;
using net.esper.collection;
using net.esper.events;

using org.apache.commons.logging;

namespace net.esper.filter
{
	/// <summary>
	/// Index for filter parameter constants to match using the 'not in' operator to match against a
	/// all other values then the supplied set of values.
	/// </summary>
	public sealed class FilterParamIndexNotIn : FilterParamIndexPropBase
	{
	    private readonly IDictionary<Object, Set<EventEvaluator>> constantsMap;
	    private readonly IDictionary<MultiKeyUntyped, EventEvaluator> filterValueEvaluators;
	    private readonly ISet<EventEvaluator> evaluatorsSet;
	    private readonly ReaderWriterLock constantsMapRWLock;

	    /// <summary>Constructs the index for multiple-exact matches.</summary>
	    /// <param name="propertyName">is the name of the event property</param>
	    /// <param name="eventType">
	    /// describes the event type and is used to obtain a getter instance for the property
	    /// </param>
	    public FilterParamIndexNotIn(String propertyName, EventType eventType)
	        : base(propertyName, FilterOperator.NOT_IN_LIST_OF_VALUES, eventType)
	    {
	        constantsMap = new EHashDictionary<Object, ISet<EventEvaluator>>();
	        filterValueEvaluators = new EHashDictionary<MultiKeyUntyped, EventEvaluator>();
	        evaluatorsSet = new EHashSet<EventEvaluator>();
	        constantsMapRWLock = new ReaderWriterLock();
	    }

	    public EventEvaluator Get(Object filterConstant)
	    {
	        MultiKeyUntyped keyValues = (MultiKeyUntyped) filterConstant;
	        return filterValueEvaluators.Get(keyValues);
	    }

	    public void Put(Object filterConstant, EventEvaluator evaluator)
	    {
	        // Store evaluator keyed to set of values
	        MultiKeyUntyped keys = (MultiKeyUntyped) filterConstant;
	        filterValueEvaluators.Put(keys, evaluator);
	        evaluatorsSet.Add(evaluator);

	        // Store each value to match against in Map with it's evaluator as a list
	        Object[] keyValues = keys.Keys;
	        foreach (Object keyValue in keyValues)
	        {
	            ISet<EventEvaluator> evaluators = constantsMap.Get(keyValue);
	            if (evaluators == null)
	            {
	                evaluators = new EHashSet<EventEvaluator>();
	                constantsMap.Put(keyValue, evaluators);
	            }
	            evaluators.Add(evaluator);
	        }
	    }

	    public bool Remove(Object filterConstant)
	    {
	        MultiKeyUntyped keys = (MultiKeyUntyped) filterConstant;

	        // remove the mapping of value set to evaluator
	        EventEvaluator eval = filterValueEvaluators.Remove(keys);
	        evaluatorsSet.Remove(eval);
	        bool isRemoved = false;
	        if (eval != null)
	        {
	            isRemoved = true;
	        }

	        Object[] keyValues = keys.Keys;
	        foreach (Object keyValue in keyValues)
	        {
	            Set<EventEvaluator> evaluators = constantsMap.Get(keyValue);
	            if (evaluators != null) // could already be removed as constants may be the same
	            {
	                evaluators.Remove(eval);
	                if (evaluators.IsEmpty())
	                {
	                    constantsMap.Remove(keyValue);
	                }
	            }
	        }
	        return isRemoved;
	    }

	    public int Count
	    {
            get { return constantsMap.Count; }
	    }

	    public ReaderWriterLock ReadWriteLock
	    {
            get { return constantsMapRWLock; }
	    }

	    public void MatchEvent(EventBean eventBean, ICollection<FilterHandle> matches)
	    {
	        Object attributeValue = this.Getter.Get(eventBean);

	        if (log.IsDebugEnabled())
	        {
	            log.Debug(".match (" + Thread.CurrentThread().Id + ") attributeValue=" + attributeValue);
	        }

	        if (attributeValue == null)
	        {
	            return;
	        }

	        // Look up in hashtable the set of not-in evaluators
	        constantsMapRWLock.ReadLock().Lock();
	        ISet<EventEvaluator> evalNotMatching = constantsMap.Get(attributeValue);

	        // if all known evaluators are matching, invoke all
	        if (evalNotMatching == null)
	        {
	            foreach (EventEvaluator eval in evaluatorsSet)
	            {
	                eval.MatchEvent(eventBean, matches);
	            }
	            constantsMapRWLock.ReadLock().Unlock();
	            return;
	        }

	        // if none are matching, we are done
	        if (evalNotMatching.Size() == evaluatorsSet.Size())
	        {
	            constantsMapRWLock.ReadLock().Unlock();
	            return;
	        }

	        // handle partial matches: loop through all evaluators and see which one should not be matching, match all else
	        foreach (EventEvaluator eval in evaluatorsSet)
	        {
	            if (!(evalNotMatching.Contains(eval)))
	            {
	                eval.MatchEvent(eventBean, matches);
	            }
	        }

	        constantsMapRWLock.ReadLock().Unlock();
	    }

	    private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
}
