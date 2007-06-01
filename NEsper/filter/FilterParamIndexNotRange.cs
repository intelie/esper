///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

using net.esper.events;

using org.apache.commons.logging;

namespace net.esper.filter
{
	/// <summary>
	/// Index for filter parameter constants for the not range operators (range open/closed/half).
	/// The implementation is based on the SortedMap implementation of TreeMap and stores only expression
	/// parameter values of type DoubleRange.
	/// </summary>
	public sealed class FilterParamIndexNotRange : FilterParamIndexPropBase
	{
	    private readonly TreeIDictionary<DoubleRange, EventEvaluator> ranges;
	    private readonly Set<EventEvaluator> evaluators;
	    private readonly ReadWriteLock rangesRWLock;

	    private double largestRangeValueDouble = Double.MIN_VALUE;

	   /// <summary>Constructs the index for matching ranges.</summary>
	   /// <param name="attributeName">is the name of the event attribute field</param>
	   /// <param name="filterOperator">is the type of range</param>
	   /// <param name="eventType">is type of events handled</param>
	    public FilterParamIndexNotRange(String attributeName, FilterOperator filterOperator, EventType eventType)
	        : base(attributeName, filterOperator, eventType)
	    {
	        ranges = new TreeIDictionary<DoubleRange, EventEvaluator>(new DoubleRangeComparator());
	        evaluators = new HashSet<EventEvaluator>();
	        rangesRWLock = new ReentrantReadWriteLock();

	        if (!(filterOperator.IsInvertedRangeOperator()))
	        {
	            throw new IllegalArgumentException("Invalid filter operator " + filterOperator);
	        }
	    }

	    public EventEvaluator Get(Object expressionValue)
	    {
	        if (!(expressionValue is DoubleRange))
	        {
	            throw new IllegalArgumentException("Supplied expressionValue must be of type DoubleRange");
	        }

	        return ranges.Get(expressionValue);
	    }

	    public void Put(Object expressionValue, EventEvaluator matcher)
	    {
	        if (!(expressionValue is DoubleRange))
	        {
	            throw new IllegalArgumentException("Supplied expressionValue must be of type DoubleRange");
	        }

	        DoubleRange range = (DoubleRange) expressionValue;

	        if ((range.Max == null) || (range.Min == null))
	        {
	            return; // null endpoints are ignored
	        }

	        if ( Math.Abs(range.Max - range.Min) > largestRangeValueDouble)
	        {
	            largestRangeValueDouble = Math.Abs(range.Max - range.Min);
	        }

	        ranges.Put(range, matcher);
	        evaluators.Add(matcher);
	    }

	    public bool Remove(Object filterConstant)
	    {
	        EventEvaluator eval = ranges.Remove(filterConstant);
	        if (eval == null)
	        {
	            return false;
	        }
	        evaluators.Remove(eval);
	        return true;
	    }

	    public int Count
	    {
            get { return ranges.Count; }
	    }

	    public ReaderWriterLock ReadWriteLock
	    {
            get { return rangesRWLock; }
	    }

	    public void MatchEvent(EventBean eventBean, Collection<FilterHandle> matches)
	    {
	        Object objAttributeValue = this.Getter.Get(eventBean);

	        if (log.IsDebugEnabled())
	        {
	            log.Debug(".match Finding range matches, attribute=" + this.PropertyName +
	                      "  attrValue=" + objAttributeValue);
	        }

	        if (objAttributeValue == null)
	        {
	            return;
	        }

	        double attributeValue = ((Number) objAttributeValue).DoubleValue();

	        DoubleRange rangeStart = new DoubleRange(attributeValue - largestRangeValueDouble, attributeValue);
	        DoubleRange rangeEnd = new DoubleRange(attributeValue, Double.MAX_VALUE);

	        SortedIDictionary<DoubleRange, EventEvaluator> subMap = ranges.SubMap(rangeStart, rangeEnd);

	        // For not including either endpoint
	        // A bit awkward to duplicate the loop code, however better than checking the bool many times over
	        // This may be a bit of an early performance optimization - the optimizer after all may do this better
	        Set<EventEvaluator> matchingEvals = new HashSet<EventEvaluator>();
	        if (this.FilterOperator == FilterOperator.NOT_RANGE_OPEN)  // include neither endpoint
	        {
	            foreach (Map.Entry<DoubleRange, EventEvaluator> entry in subMap.EntrySet())
	            {
	                if ((attributeValue > entry.Key.Min) &&
	                    (attributeValue < entry.Key.Max))
	                {
	                    matchingEvals.Add(entry.Value);
	                }
	            }
	        }
	        else if (this.FilterOperator == FilterOperator.NOT_RANGE_CLOSED)   // include all endpoints
	        {
	            foreach (Map.Entry<DoubleRange, EventEvaluator> entry in subMap.EntrySet())
	            {
	                if ((attributeValue >= entry.Key.Min) &&
	                    (attributeValue <= entry.Key.Max))
	                {
	                    matchingEvals.Add(entry.Value);
	                }
	            }
	        }
	        else if (this.FilterOperator == FilterOperator.NOT_RANGE_HALF_CLOSED) // include high endpoint not low endpoint
	        {
	            foreach (Map.Entry<DoubleRange, EventEvaluator> entry in subMap.EntrySet())
	            {
	                if ((attributeValue > entry.Key.Min) &&
	                    (attributeValue <= entry.Key.Max))
	                {
	                    matchingEvals.Add(entry.Value);
	                }
	            }
	        }
	        else if (this.FilterOperator == FilterOperator.NOT_RANGE_HALF_OPEN) // include low endpoint not high endpoint
	        {
	            foreach (Map.Entry<DoubleRange, EventEvaluator> entry in subMap.EntrySet())
	            {
	                if ((attributeValue >= entry.Key.Min) &&
	                    (attributeValue < entry.Key.Max))
	                {
	                    matchingEvals.Add(entry.Value);
	                }
	            }
	        }
	        else
	        {
	            throw new IllegalStateException("Invalid filter operator " + this.FilterOperator);
	        }

	        // Now we have all the matching evaluators, invoke all that don't match
	        foreach (EventEvaluator eval in evaluators)
	        {
	            if (!matchingEvals.Contains(eval))
	            {
	                eval.MatchEvent(eventBean, matches);
	            }
	        }
	    }

	    private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
}
