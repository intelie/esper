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

using net.esper.collection;
using net.esper.compat;
using net.esper.events;

using org.apache.commons.logging;

namespace net.esper.filter
{
	/// <summary>
	/// Index for filter parameter constants to match using the 'in' operator to match against a supplied set of values
	/// (i.e. multiple possible exact matches).
	/// The implementation is based on a regular HashMap.
	/// </summary>
	public sealed class FilterParamIndexIn : FilterParamIndexPropBase
	{
	    private readonly EDictionary<Object, List<EventEvaluator>> constantsMap;
	    private readonly EDictionary<MultiKeyUntyped, EventEvaluator> evaluatorsMap;
	    private readonly ReaderWriterLock constantsMapRWLock;

	    /// <summary>Constructs the index for multiple-exact matches.</summary>
	    /// <param name="propertyName">is the name of the event property</param>
	    /// <param name="eventType">
	    /// describes the event type and is used to obtain a getter instance for the property
	    /// </param>
	    public FilterParamIndexIn(String propertyName, EventType eventType)
	    	: base(propertyName, FilterOperator.IN_LIST_OF_VALUES, eventType)
	    {

	        constantsMap = new EHashDictionary<Object, List<EventEvaluator>>();
	        evaluatorsMap = new EHashDictionary<MultiKeyUntyped, EventEvaluator>();
	        constantsMapRWLock = new ReaderWriterLock();
	    }

	    public override EventEvaluator this[Object filterConstant]
	    {
	    	get
	    	{
		        MultiKeyUntyped keyValues = (MultiKeyUntyped) filterConstant;
		        return evaluatorsMap.Fetch(keyValues);
	    	}
	    	set
		    {
		        // Store evaluator keyed to set of values
		        MultiKeyUntyped keys = (MultiKeyUntyped) filterConstant;
		        evaluatorsMap[keys] = value;
	
		        // Store each value to match against in Map with it's evaluator as a list
		        Object[] keyValues = keys.Keys;
		        for (int i = 0; i < keyValues.Length; i++)
		        {
		            List<EventEvaluator> evaluators = constantsMap.Fetch(keyValues[i]);
		            if (evaluators == null)
		            {
		                evaluators = new LinkedList<EventEvaluator>();
		                constantsMap[keyValues[i]] = evaluators;
		            }
		            evaluators.Add(value);
		        }
		    }
	    }

	    public override bool Remove(Object filterConstant)
	    {
	        MultiKeyUntyped keys = (MultiKeyUntyped) filterConstant;

	        // remove the mapping of value set to evaluator
	        EventEvaluator eval = evaluatorsMap.Remove(keys);
	        bool isRemoved = false;
	        if (eval != null)
	        {
	            isRemoved = true;
	        }

	        Object[] keyValues = keys.Keys;
	        for (int i = 0; i < keyValues.Length; i++)
	        {
	            List<EventEvaluator> evaluators = constantsMap.Fetch(keyValues[i]);
	            if (evaluators != null) // could be removed already as same-value constants existed
	            {
	                evaluators.Remove(eval);
	                if (evaluators.Count == 0)
	                {
	                    constantsMap.Remove(keyValues[i]);
	                }
	            }
	        }
	        return isRemoved;
	    }

	    public override int Count
	    {
	        get { return constantsMap.Count; }
	    }

        public override ReaderWriterLock ReadWriteLock
	    {
            get { return constantsMapRWLock; }
	    }

	    public override void MatchEvent(EventBean eventBean, IList<FilterHandle> matches)
	    {
	        Object attributeValue = this.Getter.Get(eventBean);

	        if (log.IsDebugEnabled)
	        {
	            log.Debug(".match (" + Thread.CurrentThread().Id + ") attributeValue=" + attributeValue);
	        }

	        if (attributeValue == null)
	        {
	            return;
	        }

	        // Look up in hashtable
	        constantsMapRWLock.ReadLock().Lock();
	        List<EventEvaluator> evaluators = constantsMap.Fetch(attributeValue);

	        // No listener found for the value, return
	        if (evaluators == null)
	        {
	            constantsMapRWLock.ReadLock().Unlock();
	            return;
	        }

	        foreach (EventEvaluator evaluator in evaluators)
	        {
	            evaluator.MatchEvent(eventBean, matches);
	        }

	        constantsMapRWLock.ReadLock().Unlock();
	    }

        private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
} // End of namespace
