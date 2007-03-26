using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.events;
using net.esper.pattern;

namespace net.esper.filter
{
    /// <summary>
    /// This class represents a filter parameter containing a reference to another event's property
    /// in the event pattern result, for use to describe a filter parameter in a {@link FilterSpec} filter specification.
    /// </summary>

    public sealed class FilterSpecParamEventProp : FilterSpecParam
    {
        /// <summary> Returns tag for result event.</summary>
        /// <returns> tag
        /// </returns>

        public String ResultEventAsName
        {
            get { return resultEventAsName; }
        }

        /// <summary> Returns the property of the result event.</summary>
        /// <returns> property name
        /// </returns>

        public String ResultEventProperty
        {
            get { return resultEventProperty; }
        }

        private readonly String resultEventAsName;
        private readonly String resultEventProperty;

        /// <summary> Constructor.</summary>
        /// <param name="propertyName">is the event property name
        /// </param>
        /// <param name="filterOperator">is the type of compare
        /// </param>
        /// <param name="resultEventAsName">is the name of the result event from which to get a property value to compare
        /// </param>
        /// <param name="resultEventProperty">is the name of the property to get from the named result event
        /// </param>
        /// <throws>  ArgumentException if an operator was supplied that does not take a single constant value </throws>

        public FilterSpecParamEventProp(String propertyName, FilterOperator filterOperator, String resultEventAsName, String resultEventProperty)
            : base(propertyName, filterOperator)
        {
            this.resultEventAsName = resultEventAsName;
            this.resultEventProperty = resultEventProperty;

            if (FilterOperatorHelper.IsRangeOperator( filterOperator ))
            {
                throw new ArgumentException("Illegal filter operator " + filterOperator + " supplied to " + "event property filter parameter");
            }
        }

        /// <summary>
        /// Gets the filter value class.
        /// </summary>
        /// <param name="taggedEventTypes">The tagged event types.</param>
        /// <returns></returns>
        public override Type GetFilterValueClass(EDictionary<String, EventType> taggedEventTypes)
        {
            EventType type = taggedEventTypes.Fetch(resultEventAsName, null);
            if (type == null)
            {
                throw new SystemException("Event named '" + "'" + resultEventAsName + "' not found in event pattern result set");
            }
            return type.GetPropertyType(resultEventProperty);
        }

        /// <summary>
        /// Return the filter parameter constant to filter for.
        /// </summary>
        /// <param name="matchedEvents">is the prior results that can be used to determine filter parameters</param>
        /// <returns>filter parameter constant's value</returns>
        public override Object GetFilterValue(MatchedEventMap matchedEvents)
        {
            EventBean _event = matchedEvents.getMatchingEvent(resultEventAsName);
            if (_event == null)
            {
                throw new SystemException("Event named '" + "'" + resultEventAsName + "' not found in event pattern result set");
            }

            Object value = _event[resultEventProperty];
            return value;
        }

        /// <summary>
        /// Returns a <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </returns>
        public override String ToString()
        {
            return base.ToString() + " resultEventAsName=" + resultEventAsName + " resultEventProperty=" + resultEventProperty;
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

            if (!(obj is FilterSpecParamEventProp))
            {
                return false;
            }

            FilterSpecParamEventProp other = (FilterSpecParamEventProp)obj;
            if (!base.Equals(other))
            {
                return false;
            }

            if ((!this.resultEventAsName.Equals(other.resultEventAsName)) || (!this.resultEventProperty.Equals(other.resultEventProperty)))
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
