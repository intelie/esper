using System;
using System.Collections.Generic;
using System.Threading;

using net.esper.compat;
using net.esper.events;

using org.apache.commons.logging;

namespace net.esper.filter
{
	/// <summary> Index for filter parameter constants for the range operators (range open/closed/half).
	/// The implementation is based on the SortedDictionary implementation of SortedDictionary and stores only expression
	/// parameter values of type DoubleRange.
	/// </summary>

	public sealed class FilterParamIndexRange : FilterParamIndex
	{
		private readonly ETreeDictionary<DoubleRange, EventEvaluator> ranges;
		private readonly ReaderWriterLock rangesRWLock;
		private double largestRangeValueDouble = Double.MinValue;

		/// <summary> Constructs the index for matching ranges.</summary>
		/// <param name="attributeName">is the name of the event attribute field
		/// </param>
		/// <param name="filterOperator">is the type of range
		/// </param>
		/// <param name="eventType">is type of events handled
		/// </param>

		public FilterParamIndexRange( String attributeName, FilterOperator filterOperator, EventType eventType )
			: base( attributeName, filterOperator, eventType )
		{
			ranges = new ETreeDictionary<DoubleRange, EventEvaluator>( new DoubleRangeComparator() );
			rangesRWLock = new ReaderWriterLock();

			if ( ! FilterOperatorHelper.isRangeOperator( filterOperator ) )
			{
				throw new ArgumentException( "Invalid filter operator " + filterOperator );
			}
		}

		public override EventEvaluator this[Object expressionValue]
		{
            get
            {
            	DoubleRange doubleRange = expressionValue as DoubleRange;
            	if ( doubleRange == null )
                {
                    throw new ArgumentException("Supplied expressionValue must be of type DoubleRange");
                }

                return ranges.Fetch(doubleRange);
            }

            set
            {
            	DoubleRange range = expressionValue as DoubleRange;
            	if ( range == null )
                {
                    throw new ArgumentException("Supplied expressionValue must be of type DoubleRange");
                }

                if (Math.Abs(range.Max - range.Min) > largestRangeValueDouble)
                {
                    largestRangeValueDouble = Math.Abs(range.Max - range.Min);
                }

                ranges[range] = value;
            }
		}

		public override bool Remove( Object filterConstant )
		{
			DoubleRange doubleRange = filterConstant as DoubleRange ;
			if ( doubleRange == null )
			{
				throw new ArgumentException( "Supplied filterConstant must be of type DoubleRange" ) ;
			}
			
			return ranges.Remove( doubleRange ) ;
		}

		public override int Count
		{
            get { return ranges.Count; }
		}

		public override ReaderWriterLock ReadWriteLock
		{
            get { return rangesRWLock; }
		}

		public override void matchEvent( EventBean eventBean, IList<FilterCallback> matches )
		{
			Object objAttributeValue = this.Getter.GetValue( eventBean );

			if ( log.IsDebugEnabled )
			{
				log.Debug(
					".match Finding range matches, attribute=" + this.PropertyName + 
					"  attrValue=" + objAttributeValue );
			}

			if ( objAttributeValue == null )
			{
				return;
			}

			double attributeValue = Convert.ToDouble( objAttributeValue );

			DoubleRange rangeStart = new DoubleRange( attributeValue - largestRangeValueDouble, attributeValue );
			DoubleRange rangeEnd = new DoubleRange( attributeValue, Double.MaxValue );

			ETreeDictionary<DoubleRange, EventEvaluator> subMap = ranges.Range( rangeStart, rangeEnd );

			// For not including either endpoint
			// A bit awkward to duplicate the loop code, however better than checking the boolean many times over
			// This may be a bit of an early performance optimization - the optimizer after all may do this better
			if ( this.FilterOperator == FilterOperator.RANGE_OPEN )
			// include neither endpoint
			{
				foreach ( KeyValuePair<DoubleRange, EventEvaluator> entry in subMap )
				{
					if ( ( attributeValue > entry.Key.Min ) && ( attributeValue < entry.Key.Max ) )
					{
						entry.Value.matchEvent( eventBean, matches );
					}
				}
			}
			else if ( this.FilterOperator == FilterOperator.RANGE_CLOSED )
			// include all endpoints
			{
				foreach ( KeyValuePair<DoubleRange, EventEvaluator> entry in subMap )
				{
					if ( ( attributeValue >= entry.Key.Min ) && ( attributeValue <= entry.Key.Max ) )
					{
						entry.Value.matchEvent( eventBean, matches );
					}
				}
			}
			else if ( this.FilterOperator == FilterOperator.RANGE_HALF_CLOSED )
			// include high endpoint not low endpoint
			{
				foreach ( KeyValuePair<DoubleRange, EventEvaluator> entry in subMap )
				{
					if ( ( attributeValue > entry.Key.Min ) && ( attributeValue <= entry.Key.Max ) )
					{
						entry.Value.matchEvent( eventBean, matches );
					}
				}
			}
			else if ( this.FilterOperator == FilterOperator.RANGE_HALF_OPEN )
			// include low endpoint not high endpoint
			{
				foreach ( KeyValuePair<DoubleRange, EventEvaluator> entry in subMap )
				{
					if ( ( attributeValue >= entry.Key.Min ) && ( attributeValue < entry.Key.Max ) )
					{
						entry.Value.matchEvent( eventBean, matches );
					}
				}
			}
			else
			{
				throw new SystemException( "Invalid filter operator " + this.FilterOperator );
			}
		}

		private static readonly Log log = LogFactory.GetLog( typeof( FilterParamIndexRange ) );
	}
}
