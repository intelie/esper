using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.events;
using net.esper.pattern;

namespace net.esper.filter
{
    /// <summary>
    /// This class represents one filter parameter in an <seealso cref="FilterSpec"/> filter specification.
    /// <para>Each filerting parameter has an attribute name and operator type.</para>
    /// </summary>

    public abstract class FilterSpecParam
    {
        /// <summary> Returns the property name for the filter parameter.</summary>
        /// <returns> property name
        /// </returns>
        virtual public String PropertyName
        {
            get { return propertyName; }
        }
        
        /// <summary> Returns the filter operator type.</summary>
        /// <returns> filter operator type
        /// </returns>
        virtual public FilterOperator FilterOperator
        {
            get { return filterOperator; }
        }

        private readonly String propertyName;
        private readonly FilterOperator filterOperator;

        internal FilterSpecParam(String propertyName, FilterOperator filterOperator)
        {
            this.propertyName = propertyName;
            this.filterOperator = filterOperator;
        }

        /// <summary> Return the filter parameter constant's class.</summary>
        /// <param name="optionalTaggedEventTypes">is the event types per event as-name (tag)
        /// </param>
        /// <returns> filter parameter value class
        /// </returns>
        public abstract Type GetFilterValueClass(EDictionary<String, EventType> optionalTaggedEventTypes);

        /// <summary> Return the filter parameter constant to filter for.</summary>
        /// <param name="matchedEvents">is the prior results that can be used to determine filter parameters
        /// </param>
        /// <returns> filter parameter constant's value
        /// </returns>
        public abstract Object GetFilterValue(MatchedEventMap matchedEvents);

        /// <summary>
        /// Returns a <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </returns>
        
        public override String ToString()
        {
            return "FilterSpecParam" + " property=" + propertyName + " filterOp=" + filterOperator;
        }

        /// <summary>
        /// Determines whether the specified <see cref="T:System.Object"></see> is equal to the current <see cref="T:System.Object"></see>.
        /// </summary>
        /// <param name="obj">The <see cref="T:System.Object"></see> to compare with the current <see cref="T:System.Object"></see>.</param>
        /// <returns>
        /// true if the specified <see cref="T:System.Object"></see> is equal to the current <see cref="T:System.Object"></see>; otherwise, false.
        /// </returns>
        
        public override bool Equals(Object obj)
        {
            if (this == obj)
            {
                return true;
            }

            if (!(obj is FilterSpecParam))
            {
                return false;
            }

            FilterSpecParam other = (FilterSpecParam)obj;

            if ((Object)this.propertyName != (Object)other.propertyName)
            {
                return false;
            }
            if (this.filterOperator != other.filterOperator)
            {
                return false;
            }
            return true;
        }

        /// <summary>
        /// Serves as a hash function for a particular type.
        /// </summary>
        /// <returns>
        /// A hash code for the current <see cref="T:System.Object"></see>.
        /// </returns>
		public override int GetHashCode()
		{
			return base.GetHashCode();
		}
    }
}
