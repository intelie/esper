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
	    private readonly EDictionary<Object, IList<EventEvaluator>> constantsMap;
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

	        constantsMap = new HashDictionary<Object, IList<EventEvaluator>>();
	        evaluatorsMap = new HashDictionary<MultiKeyUntyped, EventEvaluator>();
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
		            IList<EventEvaluator> evaluators = constantsMap.Fetch(keyValues[i]);
		            if (evaluators == null)
		            {
		                evaluators = new List<EventEvaluator>();
		                constantsMap[keyValues[i]] = evaluators;
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
	        EventEvaluator eval ;
            bool isRemoved = evaluatorsMap.Remove(keys, out eval);

	        Object[] keyValues = keys.Keys;
	        for (int i = 0; i < keyValues.Length; i++)
	        {
	            IList<EventEvaluator> evaluators = constantsMap.Fetch(keyValues[i]);
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
            Object attributeValue = this.Getter.GetValue(eventBean);

	        if (log.IsDebugEnabled)
	        {
	            log.Debug(".MatchEvent (" + Thread.CurrentThread.ManagedThreadId + ") attributeValue=" + attributeValue);
	        }

	        if (attributeValue == null)
	        {
	            return;
	        }

	        // Look up in hashtable
            using (new ReaderLock(constantsMapRWLock))
            {
                IList<EventEvaluator> evaluators = constantsMap.Fetch(attributeValue);

                // No listener found for the value, return
                if (evaluators == null)
                {
                    return;
                }

                foreach (EventEvaluator evaluator in evaluators)
                {
                    evaluator.MatchEvent(eventBean, matches);
                }
            }
	    }

        private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
} // End of namespace
