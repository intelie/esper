using System;
using System.Collections.Generic;

using net.esper.filter;
using net.esper.view;

namespace net.esper.eql.spec
{
	/// <summary> Specification for building an event stream out of a filter for events (supplying type and basic filter criteria)
	/// and views onto these events which are staggered onto each other to supply a final stream of events.
	/// </summary>
	
	public class FilterStreamSpec : StreamSpec
	{
		private FilterSpec filterSpec;

		/// <summary> Returns filter specification for which events the stream will getSelectListEvents.</summary>
		/// <returns> filter spec
		/// </returns>

		virtual public FilterSpec FilterSpec
		{
			get { return filterSpec; }
		}

		/// <summary> Ctor.</summary>
		/// <param name="filterSpec">- specifies what events we are interested in.
		/// </param>
		/// <param name="viewSpecs">- specifies what view to use to derive data
		/// </param>
		/// <param name="optionalStreamName">- stream name, or null if none supplied
		/// </param>
		
		public FilterStreamSpec( FilterSpec filterSpec, IList<ViewSpec> viewSpecs, String optionalStreamName )
			: base( optionalStreamName, viewSpecs )
		{
			this.filterSpec = filterSpec;
		}
	}
}