using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.events;
using net.esper.pattern;
using net.esper.util;

namespace net.esper.filter
{
    /// <summary>
    /// An event property as a filter parameter representing a range.
    /// </summary>

    public class RangeValueEventProp : FilterSpecParamRangeValue
    {
        private readonly String resultEventAsName;
        private readonly String resultEventProperty;

        /// <summary>
        /// Constructor
        /// </summary>
        /// <param name="resultEventAsName">the event tag</param>
        /// <param name="resultEventProperty">the event property name</param>

        public RangeValueEventProp(String resultEventAsName, String resultEventProperty)
        {
            this.resultEventAsName = resultEventAsName;
            this.resultEventProperty = resultEventProperty;
        }

        public void CheckType(EDictionary<String, EventType> taggedEventTypes)
        {
            EventType type = taggedEventTypes.Fetch(resultEventAsName, null);
            if (type == null)
            {
                throw new IllegalStateException("Matching event type named " +
                        "'" + resultEventAsName + "' not found in event result set");
            }

            Type propertyClass = type.GetPropertyType(resultEventProperty);
            if (propertyClass == null)
            {
                throw new IllegalStateException("Property " + resultEventProperty + " of event type " +
                        "'" + resultEventAsName + "' not found");
            }
            if (!TypeHelper.IsNumeric(propertyClass))
            {
                throw new IllegalStateException("Property " + resultEventProperty + " of event type " +
                        "'" + resultEventAsName + "' is not numeric");
            }
        }

        public double GetFilterValue(MatchedEventMap matchedEvents)
        {
            EventBean ev = matchedEvents.getMatchingEvent(resultEventAsName);
            if (ev == null)
            {
                throw new IllegalStateException("Matching event named " +
                        "'" + resultEventAsName + "' not found in event result set");
            }

            Object value = ev[resultEventProperty];
            if (value == null)
            {
                throw new IllegalStateException(
            		"Event property named " +
                    "'" + resultEventAsName +
                    "." + resultEventProperty + 
                    "' returned null value");
            }
            
            return Convert.ToDouble( value ) ;
        }

        public override String ToString()
        {
            return "resultEventProp=" + resultEventAsName + "." + resultEventProperty;
        }

        public override Boolean Equals(Object obj)
        {
            if (this == obj)
            {
                return true;
            }

            if (!(obj is RangeValueEventProp))
            {
                return false;
            }

            RangeValueEventProp other = (RangeValueEventProp)obj;
            if ((other.resultEventAsName.Equals(this.resultEventAsName)) &&
                 (other.resultEventProperty.Equals(this.resultEventProperty)))
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
