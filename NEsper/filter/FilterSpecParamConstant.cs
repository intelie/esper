using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.events;
using net.esper.pattern;

namespace net.esper.filter
{
	/// <summary>
    /// This class represents a single, constant value filter parameter in an {@link FilterSpec} filter specification.
    /// </summary>
	
    public sealed class FilterSpecParamConstant : FilterSpecParam
	{
		private readonly Object filterConstant;
		
		/// <summary> Constructor.</summary>
		/// <param name="propertyName">is the event property name
		/// </param>
		/// <param name="filterOperator">is the type of compare
		/// </param>
		/// <param name="filterConstant">contains the value to match against the event's property value
		/// </param>
		/// <throws>  ArgumentException if an operator was supplied that does not take a single constant value </throws>
		
        public FilterSpecParamConstant(String propertyName, FilterOperator filterOperator, Object filterConstant) :
            base(propertyName, filterOperator)
		{
			this.filterConstant = filterConstant;
			
			if (FilterOperatorHelper.IsRangeOperator( filterOperator ))
			{
				throw new ArgumentException("Illegal filter operator " + filterOperator + " supplied to " + "constant filter parameter");
			}
		}

        public override Type GetFilterValueClass(EDictionary<String, EventType> taggedEventTypes)
        {
            if (filterConstant == null)
            {
                return null;
            }
            return filterConstant.GetType();
        }
		
		public override Object GetFilterValue(MatchedEventMap matchedEvents)
		{
			return filterConstant;
		}
		
		public override String ToString()
		{
			return base.ToString() + " filterConstant=" + filterConstant;
		}
		
		public override bool Equals(Object obj)
		{
			if (this == obj)
			{
				return true;
			}
			
			if (!(obj is FilterSpecParamConstant))
			{
				return false;
			}
			
			FilterSpecParamConstant other = (FilterSpecParamConstant) obj;
			if (!base.Equals(other))
			{
				return false;
			}

            bool result = Object.Equals(this.filterConstant, other.filterConstant);
            return result;
		}

        public override int GetHashCode()
		{
			return base.GetHashCode();
		}
	}
}
