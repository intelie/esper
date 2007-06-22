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
	    private readonly EDictionary<Object, Set<EventEvaluator>> constantsMap;
	    private readonly EDictionary<MultiKeyUntyped, EventEvaluator> filterValueEvaluators;
	    private readonly Set<EventEvaluator> evaluatorsSet;
	    private readonly ReaderWriterLock constantsMapRWLock;

	    /// <summary>Constructs the index for multiple-exact matches.</summary>
	    /// <param name="propertyName">is the name of the event property</param>
	    /// <param name="eventType">
	    /// describes the event type and is used to obtain a getter instance for the property
	    /// </param>
	    public FilterParamIndexNotIn(String propertyName, EventType eventType)
	        : base(propertyName, FilterOperator.NOT_IN_LIST_OF_VALUES, eventType)
	    {
	        constantsMap = new HashDictionary<Object, Set<EventEvaluator>>();
	        filterValueEvaluators = new HashDictionary<MultiKeyUntyped, EventEvaluator>();
	        evaluatorsSet = new HashSet<EventEvaluator>();
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
        /// <returns>
        /// event evaluator stored for the filter constant, or null if not found
        /// </returns>
	    public override EventEvaluator this[Object filterConstant]
	    {
	    	get
		    {
		        MultiKeyUntyped keyValues = (MultiKeyUntyped) filterConstant;
		        return filterValueEvaluators.Fetch(keyValues);
		    }

	    	set
		    {
		        // Store evaluator keyed to set of values
		        MultiKeyUntyped keys = (MultiKeyUntyped) filterConstant;
		        filterValueEvaluators.Put(keys, value);
		        evaluatorsSet.Add(value);
	
		        // Store each value to match against in Map with it's evaluator as a list
		        Object[] keyValues = keys.Keys;
		        foreach (Object keyValue in keyValues)
		        {
		            Set<EventEvaluator> evaluators = constantsMap.Fetch(keyValue);
		            if (evaluators == null)
		            {
		                evaluators = new HashSet<EventEvaluator>();
		                constantsMap.Put(keyValue, evaluators);
		            }
		            evaluators.Add(value);
		        }
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
	        MultiKeyUntyped keys = (MultiKeyUntyped) filterConstant;

	        // remove the mapping of value set to evaluator
	        EventEvaluator eval;
            bool isRemoved = filterValueEvaluators.Remove(keys, out eval);
            if (isRemoved)
            {
                evaluatorsSet.Remove(eval);
            }

	        Object[] keyValues = keys.Keys;
	        foreach (Object keyValue in keyValues)
	        {
	            Set<EventEvaluator> evaluators = constantsMap.Fetch(keyValue);
	            if (evaluators != null) // could already be removed as constants may be the same
	            {
	                evaluators.Remove(eval);
	                if (evaluators.Count == 0)
	                {
	                    constantsMap.Remove(keyValue);
	                }
	            }
	        }
	        return isRemoved;
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
            get { return constantsMap.Count; }
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
	        Object attributeValue = Getter.GetValue(eventBean);

	        if (log.IsDebugEnabled)
	        {
	            log.Debug(".match (" + Thread.CurrentThread.ManagedThreadId + ") attributeValue=" + attributeValue);
	        }

	        if (attributeValue == null)
	        {
	            return;
	        }

	        // Look up in hashtable the set of not-in evaluators
            using (new ReaderLock(constantsMapRWLock))
            {
                Set<EventEvaluator> evalNotMatching = constantsMap.Fetch(attributeValue);

                // if all known evaluators are matching, invoke all
                if (evalNotMatching == null)
                {
                    foreach (EventEvaluator eval in evaluatorsSet)
                    {
                        eval.MatchEvent(eventBean, matches);
                    }
                    return;
                }

                // if none are matching, we are done
                if (evalNotMatching.Count == evaluatorsSet.Count)
                {
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
            }
	    }

	    private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
}
