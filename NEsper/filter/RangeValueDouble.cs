using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.events;
using net.esper.pattern;

namespace net.esper.filter
{
	/// <summary>
    /// A Double-typed value as a filter parameter representing a range.
    /// </summary>
	
    public class RangeValueDouble : FilterSpecParamRangeValue
	{
        private readonly double doubleValue;
		
		/// <summary> Ctor.</summary>
		/// <param name="doubleValue">is the value of the range endpoint
		/// </param>

        public RangeValueDouble(double doubleValue)
		{
			this.doubleValue = doubleValue;
		}

        public void checkType(EDictionary<String, EventType> taggedEventTypes)
        {
            return;
        }
		
		public double getFilterValue(MatchedEventMap matchedEvents)
		{
			return doubleValue;
		}
		
		public override String ToString()
		{
			return doubleValue.ToString();
		}
		
		public  override bool Equals(Object obj)
		{
			if (this == obj)
			{
				return true;
			}
			
			if (!(obj is RangeValueDouble))
			{
				return false;
			}
			
			RangeValueDouble other = (RangeValueDouble) obj;
			return other.doubleValue == this.doubleValue;
		}

		public override int GetHashCode()
		{
			return base.GetHashCode();
		}
	}
}