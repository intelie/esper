using System;
using System.Collections.Generic;
using System.Text;

using net.esper.compat;
using net.esper.events;
using net.esper.pattern;

namespace net.esper.filter
{
    /// <summary> Contains the filter criteria to sift through events. The filter criteria are the event class to look for and
    /// a set of parameters (attribute names, operators and constant/range values).
    /// </summary>

    public sealed class FilterSpec
    {
        /// <summary> Returns type of event to filter for.</summary>
        /// <returns> event type
        /// </returns>

        public EventType EventType
        {
            get { return eventType; }
        }

        private readonly EventType eventType;
        private readonly IList<FilterSpecParam> parameters;

        /// <summary> Constructor - validates parameter list against event type, throws exception if invalid
        /// property names or mismatcing filter operators are found.
        /// </summary>
        /// <param name="eventType">is the event type
        /// </param>
        /// <param name="parameters">is a list of filter parameters
        /// </param>
        /// <throws>  ArgumentException if validation invalid </throws>

        public FilterSpec(EventType eventType, IList<FilterSpecParam> parameters)
        {
            this.eventType = eventType;
            this.parameters = parameters;
        }

        /// <summary> Returns list of filter parameters.</summary>
        /// <returns> list of filter params
        /// </returns>
        public IList<FilterSpecParam> Parameters
        {
            get { return parameters; }
        }

        /// <summary> Returns the values for the filter, using the supplied result events to ask filter parameters
        /// for the value to filter for.
        /// </summary>
        /// <param name="matchedEvents">contains the result events to use for determining filter values
        /// </param>
        /// <returns> filter values
        /// </returns>
        public FilterValueSet getValueSet(MatchedEventMap matchedEvents)
        {
            IList<FilterValueSetParam> valueList = new List<FilterValueSetParam>();

            // Ask each filter specification parameter for the actual value to filter for
            foreach (FilterSpecParam specParam in parameters)
            {
                Object filterForValue = specParam.getFilterValue(matchedEvents);

                FilterValueSetParam valueParam = new FilterValueSetParamImpl(specParam.PropertyName, specParam.FilterOperator, filterForValue);
                valueList.Add(valueParam);
            }
            return new FilterValueSetImpl(eventType, valueList);
        }

        public override String ToString()
        {
            StringBuilder buffer = new StringBuilder();
            buffer.Append("FilterSpec type=" + this.eventType);
            buffer.Append(" parameters=" + CollectionHelper.Render( parameters )) ;
            return buffer.ToString();
        }

        public override bool Equals(Object obj)
        {
            if (this == obj)
            {
                return true;
            }

            if (!(obj is FilterSpec))
            {
                return false;
            }

            FilterSpec other = (FilterSpec)obj;

            if (this.eventType != other.eventType)
            {
                return false;
            }
            if (this.parameters.Count != other.parameters.Count)
            {
                return false;
            }

            IEnumerator<FilterSpecParam> iterOne = parameters.GetEnumerator();
            IEnumerator<FilterSpecParam> iterOther = other.parameters.GetEnumerator();
            
            while( iterOne.MoveNext() && iterOther.MoveNext() )
            {
            	if (! Object.Equals( iterOne.Current, iterOther.Current ) )
                {
                    return false;
                }
            }

            return true;
        }

        public override int GetHashCode()
        {
            int hashCode = eventType.GetHashCode();
            foreach (FilterSpecParam param in parameters)
            {
                hashCode = hashCode ^ param.PropertyName.GetHashCode();
            }
            return hashCode;
        }
    }
}
