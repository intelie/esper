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
	    private readonly ETreeDictionary<DoubleRange, EventEvaluator> ranges;
	    private readonly Set<EventEvaluator> evaluators;
	    private readonly ReaderWriterLock rangesRWLock;

	    private double largestRangeValueDouble = Double.MinValue;

	   /// <summary>Constructs the index for matching ranges.</summary>
	   /// <param name="attributeName">is the name of the event attribute field</param>
	   /// <param name="filterOperator">is the type of range</param>
	   /// <param name="eventType">is type of events handled</param>
	    public FilterParamIndexNotRange(String attributeName, FilterOperator filterOperator, EventType eventType)
	        : base(attributeName, filterOperator, eventType)
	    {
	        ranges = new ETreeDictionary<DoubleRange, EventEvaluator>(new DoubleRangeComparator());
	        evaluators = new EHashSet<EventEvaluator>();
	        rangesRWLock = new ReaderWriterLock();

	        if (!FilterOperatorHelper.IsInvertedRangeOperator(filterOperator))
	        {
	            throw new ArgumentException("Invalid filter operator " + filterOperator);
	        }
	    }

	    public override EventEvaluator this[Object expressionValue]
	    {
	    	get
	    	{
	    		DoubleRange doubleRange = expressionValue as DoubleRange ;
	    		if ( doubleRange == null )
		        {
		            throw new ArgumentException("Supplied expressionValue must be of type DoubleRange");
		        }
	
		        return ranges.Fetch(doubleRange);
	    	}

	    	set
	    	{
		        if (!(expressionValue is DoubleRange))
		        {
		            throw new ArgumentException("Supplied expressionValue must be of type DoubleRange");
		        }
	
		        DoubleRange range = (DoubleRange) expressionValue;
	
		        double? max = range.Max;
		        double? min = range.Min;
		        
		        if ((max == null) || (min == null))
		        {
		            return; // null endpoints are ignored
		        }
	
		        double delta = Math.Abs( max.Value - min.Value ) ;

		        if ( delta > largestRangeValueDouble)
		        {
		            largestRangeValueDouble = delta;
		        }
	
		        ranges[range] = value;
		        evaluators.Add(value);
		    }
	    }

	    public override bool Remove(Object filterConstant)
	    {
    		DoubleRange doubleRange = expressionValue as DoubleRange ;
    		if ( doubleRange == null )
    		{
    			throw new ArgumentException("Supplied expressionValue must be of type DoubleRange");
    		}

	        EventEvaluator eval;
	        if (!ranges.Remove(doubleRange, out eval))
	        {
	            return false;
	        }
	        evaluators.Remove(eval);
	        return true;
	    }

	    public override int Count
	    {
            get { return ranges.Count; }
	    }

	    public override ReaderWriterLock ReadWriteLock
	    {
            get { return rangesRWLock; }
	    }

	    public override void MatchEvent(EventBean eventBean, IList<FilterHandle> matches)
	    {
            Object objAttributeValue = this.Getter.GetValue(eventBean);

	        if (log.IsDebugEnabled)
	        {
	            log.Debug(".match Finding range matches, attribute=" + this.PropertyName +
	                      "  attrValue=" + objAttributeValue);
	        }

	        if (objAttributeValue == null)
	        {
	            return;
	        }

	        double attributeValue = Convert.ToDouble(objAttributeValue);

	        DoubleRange rangeStart = new DoubleRange(attributeValue - largestRangeValueDouble, attributeValue);
	        DoubleRange rangeEnd = new DoubleRange(attributeValue, Double.MaxValue);

	        IEnumerator<KeyValuePair<DoubleRange, EventEvaluator>> subMapEnum = ranges.RangeFast(rangeStart, rangeEnd);

	        // For not including either endpoint
	        // A bit awkward to duplicate the loop code, however better than checking the bool many times over
	        // This may be a bit of an early performance optimization - the optimizer after all may do this better
	        Set<EventEvaluator> matchingEvals = new EHashSet<EventEvaluator>();
	        if (this.FilterOperator == FilterOperator.NOT_RANGE_OPEN)  // include neither endpoint
	        {
	        	while( subMapEnum.MoveNext() )	        		
	            {
	        		KeyValuePair<DoubleRange, EventEvaluator> entry = subMapEnum.Current;
	                if ((attributeValue > entry.Key.Min) &&
	                    (attributeValue < entry.Key.Max))
	                {
	                    matchingEvals.Add(entry.Value);
	                }
	            }
	        }
	        else if (this.FilterOperator == FilterOperator.NOT_RANGE_CLOSED)   // include all endpoints
	        {
	        	while( subMapEnum.MoveNext() )	        		
	            {
	        		KeyValuePair<DoubleRange, EventEvaluator> entry = subMapEnum.Current;
	                if ((attributeValue >= entry.Key.Min) &&
	                    (attributeValue <= entry.Key.Max))
	                {
	                    matchingEvals.Add(entry.Value);
	                }
	            }
	        }
	        else if (this.FilterOperator == FilterOperator.NOT_RANGE_HALF_CLOSED) // include high endpoint not low endpoint
	        {
	        	while( subMapEnum.MoveNext() )	        		
	            {
	        		KeyValuePair<DoubleRange, EventEvaluator> entry = subMapEnum.Current;
	                if ((attributeValue > entry.Key.Min) &&
	                    (attributeValue <= entry.Key.Max))
	                {
	                    matchingEvals.Add(entry.Value);
	                }
	            }
	        }
	        else if (this.FilterOperator == FilterOperator.NOT_RANGE_HALF_OPEN) // include low endpoint not high endpoint
	        {
	        	while( subMapEnum.MoveNext() )	        		
	            {
	        		KeyValuePair<DoubleRange, EventEvaluator> entry = subMapEnum.Current;
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

	        // Dispose of the temporary enumerator
	        subMapEnum.Dispose() ;
	        subMapEnum = null ;
	        
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
