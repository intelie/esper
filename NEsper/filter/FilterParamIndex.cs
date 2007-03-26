using System;
using System.Collections.Generic;
using System.Threading;

using net.esper.events;
using net.esper.util;

namespace net.esper.filter
{
    /// <summary>
    /// Each implementation of this abstract class represents an index of filter parameter constants supplied in filter
    /// parameters in filter specifications that feature the same event property and operator.
    /// 
    /// For example, a filter with a parameter of "count EQUALS 10" would be represented as index
    /// for a property named "count" and for a filter operator typed "EQUALS". The index
    /// would store a value of "10" in its internal structure.
    /// 
    /// Implementations make sure that the type of the Object constant in get and put calls matches the event property type.
    /// </summary>

    public abstract class FilterParamIndex : EventEvaluator
    {
        /// <summary> Returns the name of the property to get the value for to match against the values
        /// contained in the index.
        /// </summary>
        /// <returns> event property name
        /// </returns>

        virtual public String PropertyName
        {
            get { return propertyName; }
        }

        /// <summary> Returns the filter operator that the index matches for.</summary>
        /// <returns> filter operator
        /// </returns>

        virtual public FilterOperator FilterOperator
        {
            get { return filterOperator; }
        }

        /// <summary> Returns getter for property.</summary>
        /// <returns> property value getter
        /// </returns>

        virtual public EventPropertyGetter Getter
        {
            get { return getter; }
        }

        /// <summary> Returns boxed property type.</summary>
        /// <returns> boxed property type
        /// </returns>

        virtual public Type PropertyBoxedType
        {
            get { return propertyBoxedType; }
        }

        private readonly String propertyName;
        private readonly FilterOperator filterOperator;
        private readonly EventPropertyGetter getter;
        private readonly Type propertyBoxedType;

        /// <summary> Constructor.</summary>
        /// <param name="propertyName">is the name of the event property the index goes against
        /// </param>
        /// <param name="filterOperator">is the type of comparison performed.
        /// </param>
        /// <param name="eventType">is the event type the index will handle.
        /// </param>

        protected FilterParamIndex(String propertyName, FilterOperator filterOperator, EventType eventType)
        {
            this.propertyName = propertyName;
            this.filterOperator = filterOperator;

            getter = eventType.GetGetter(propertyName);
            propertyBoxedType = TypeHelper.GetBoxedType(eventType.GetPropertyType(propertyName));
            if (getter == null)
            {
                throw new ArgumentException("Property named '" + propertyName + "' not valid for event type ");
            }
        }

        /// <summary>
        /// Gets or sets the <see cref="net.esper.filter.EventEvaluator"/> with the specified filter constant.
        /// Returns null if no entry found for the constant.
        /// The calling class must make sure that access to the underlying resource is protected
        /// for multi-threaded access, the ReadWriteLock method must supply a lock for this purpose.
        /// </summary>
        /// <value></value>

        public abstract EventEvaluator this[Object filterConstant]
        {
            get;
            set;
        }

        /// <summary>
        /// Convenience method added for portability.  Sets the value of
        /// the event evaluator for the given filterConstant.
        /// </summary>
        /// <param name="filterConstant">The filter constant.</param>
        /// <param name="eventEvaluator">The event evaluator.</param>

        public virtual void Put(Object filterConstant, EventEvaluator eventEvaluator)
        {
            this[filterConstant] = eventEvaluator;
        }

        /// <summary> Remove the event evaluation instance for the given constant. Returns true if
        /// the constant was found, or false if not.
        /// The calling class must make sure that access to the underlying resource is protected
        /// for multi-threaded writes, the ReadWriteLock method must supply a lock for this purpose.
        /// </summary>
        /// <param name="filterConstant">is the value supplied in the filter paremeter
        /// </param>
        /// <returns> true if found and removed, false if not found
        /// </returns>

        public abstract bool Remove(Object filterConstant);

        /// <summary> Return the number of distinct filter parameter constants stored.
        /// The calling class must make sure that access to the underlying resource is protected
        /// for multi-threaded writes, the ReadWriteLock method must supply a lock for this purpose.
        /// </summary>
        /// <returns> Number of entries in index
        /// </returns>

        public abstract int Count
        {
            get;
        }

        /// <summary> Supplies the lock for protected access.</summary>
        /// <returns> lock
        /// </returns>

        public abstract ReaderWriterLock ReadWriteLock
        {
            get;
        }

        /// <summary>
        /// Returns a <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </returns>

        public override String ToString()
        {
            return "propName=" + propertyName + " filterOperator=" + filterOperator + " propertyBoxedType=" + propertyBoxedType.FullName;
        }

        /// <summary> Perform the matching of an event based on the event property values,
        /// adding any callbacks for matches found to the matches list.
        /// </summary>
        /// <param name="_event">is the event object wrapper to obtain event property values from
        /// </param>
        /// <param name="matches">accumulates the matching filter callbacks
        /// </param>

        public abstract void MatchEvent(EventBean _event, IList<FilterCallback> matches);
    }
}
