using System;

using net.esper.view;
using net.esper.filter;

namespace net.esper.view.stream
{
	/// <summary> Service on top of the filter service for reuseing filter callbacks and their associated EventStream instances.
	/// Same filter specifications (equal) do not need to be added to the filter service twice and the
	/// EventStream instance that is the stream of events for that filter can be reused.
	/// </summary>
	public interface StreamReuseService
	{
		/// <summary> Create or reuse existing EventStream instance representing that event filter.
		/// When called for some filters, should return same stream.
		/// </summary>
		/// <param name="filterSpec">event filter definition</param>
		/// <param name="filterService">filter service to activate filter if not already active</param>
		/// <returns> event stream representing active filter</returns>
		EventStream CreateStream(FilterSpec filterSpec, FilterService filterService);
		
		/// <summary> Drop the event stream associated with the filter passed in.
		/// Throws an exception if already dropped.
		/// </summary>
		/// <param name="filterSpec">is the event filter definition associated with the event stream to be dropped</param>
		/// <param name="filterService">to be used to deactivate filter when the last event stream is dropped</param>
		void DropStream(FilterSpec filterSpec, FilterService filterService);
	}
}