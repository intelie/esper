using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.events;
using net.esper.pattern;

namespace net.esper.filter
{
	/// <summary>
	/// This class represents a range filter parameter in an {@link FilterSpec} filter specification.
	/// </summary>
	
	public sealed class FilterSpecParamRange : FilterSpecParam
	{
		private readonly FilterSpecParamRangeValue min;
		private readonly FilterSpecParamRangeValue max;

		/// <summary> Constructor.</summary>
		/// <param name="propertyName">is the event property name
		/// </param>
		/// <param name="filterOperator">is the type of range operator
		/// </param>
		/// <param name="min">is the begin point of the range
		/// </param>
		/// <param name="max">is the end point of the range
		/// </param>
		/// <throws>  ArgumentException if an operator was supplied that does not take a double range value </throws>
		
		public FilterSpecParamRange(String propertyName, FilterOperator filterOperator, FilterSpecParamRangeValue min, FilterSpecParamRangeValue max):base(propertyName, filterOperator)
		{
			this.min = min;
			this.max = max;
			
			if (! FilterOperatorHelper.isRangeOperator( filterOperator ) )
			{
				throw new ArgumentException("Illegal filter operator " + filterOperator + " supplied to " + "range filter parameter");
			}
		}

		public override Type getFilterValueClass( EDictionary<String, EventType> taggedEventTypes )
		{
			min.checkType( taggedEventTypes );
			max.checkType( taggedEventTypes );
			return typeof( DoubleRange );
		}
		
		public override Object getFilterValue(MatchedEventMap matchedEvents)
		{
			double begin = min.getFilterValue(matchedEvents);
			double end = max.getFilterValue(matchedEvents);
			return new DoubleRange(begin, end);
		}
		
		public override String ToString()
		{
			return base.ToString() + "  range=(min=" + min.ToString() + ",max=" + max.ToString() + ")";
		}
		
		public  override bool Equals(Object obj)
		{
			if (this == obj)
			{
				return true;
			}
			
			if (!(obj is FilterSpecParamRange))
			{
				return false;
			}
			
			FilterSpecParamRange other = (FilterSpecParamRange) obj;
			if (!base.Equals(other))
			{
				return false;
			}
			
			if ((this.min.Equals(other.min) && (this.max.Equals(other.max))))
			{
				return true;
			}
			return false;
		}

		public override int GetHashCode()
		{
			return base.GetHashCode();
		}
	}
}
