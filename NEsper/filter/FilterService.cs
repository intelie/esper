using System;
using System.Collections.Generic;

using EventBean = net.esper.events.EventBean;

namespace net.esper.filter
{
	/// <summary> Interface for filtering events by event type and event property values. Allows adding and removing filters.
	/// <para>
	/// Filters are defined by a <seealso cref="FilterSpecCompiled"/> and are associated with a <seealso cref="FilterCallback"/>
	/// callback. Implementations may decide if the same filter callback can be registered twice for different
    /// or some filter specifications.
    /// </para>
	/// <para>
	/// The performance of an implementation of this service is crucial in achieving a high overall event throughput.
    /// </para>
	/// </summary>
	public interface FilterService
	{
		/// <summary> Return a count of the number of events evaluated by this service.</summary>
		/// <returns> count of invocations of evaluate method
		/// </returns>
		long NumEventsEvaluated
		{
			get;			
		}

		/**
		 * Finds matching filters to the event passed in and invokes their associated callback method.
		 * @param _event is the event to be matched against filters
		 * @param matches is a collection that is populated via add method with any handles for matching filters
		 */
		void Evaluate(EventBean _event, ICollection<FilterHandle> matches);
		
		/// <summary> Add a filter for events as defined by the filter specification, and register a
		/// callback to be invoked upon evaluation of an event that matches the filter spec.
		/// </summary>
		/// <param name="filterValueSet">is a specification of filter parameters, contains
		/// event type information, event property values and operators
		/// </param>
		/// <param name="callback">is the callback to be invoked when the filter matches an event
		/// </param>
		void  Add(FilterValueSet filterValueSet, FilterHandle callback);
		
		/// <summary> Remove a filter callback.</summary>
		/// <param name="callback">is the callback to be removed
		/// </param>
		void  Remove(FilterHandle callback);
	}
}
