using System;

using net.esper.events;
using net.esper.filter;
using net.esper.schedule;

namespace net.esper.pattern
{
	/// <summary>
	/// Contains handles to implementations of services needed by
	/// evaluation nodes.
	/// </summary>
	
	public sealed class PatternContext
	{
		private readonly FilterService filterService;
		private readonly SchedulingService schedulingService;
		private readonly ScheduleBucket scheduleBucket;
		private readonly EventAdapterService eventAdapterService;

		/// <summary> Returns service to use for filter evaluation.</summary>
		/// <returns> filter evaluation service implemetation
		/// </returns>
		public FilterService FilterService
		{
			get
			{
				return filterService;
			}
			
		}
		/// <summary> Returns service to use for schedule evaluation.</summary>
		/// <returns> schedule evaluation service implemetation
		/// </returns>
		public SchedulingService SchedulingService
		{
			get
			{
				return schedulingService;
			}
			
		}
		/// <summary> Returns the schedule bucket for ordering schedule callbacks within this pattern.</summary>
		/// <returns> schedule bucket
		/// </returns>
		public ScheduleBucket ScheduleBucket
		{
			get
			{
				return scheduleBucket;
			}
			
		}
		/// <summary> Returns teh service providing event adaptering or wrapping.</summary>
		/// <returns> event adapter service
		/// </returns>
		public EventAdapterService EventAdapterService
		{
			get
			{
				return eventAdapterService;
			}
		}
		
		/// <summary> Constructor.</summary>
		/// <param name="filterService">implementation for filtering service
		/// </param>
		/// <param name="scheduleBucket">schedule buckets for use by scheduling service for ordering scheduling callbacks for pattern statements 
		/// </param>
		/// <param name="schedulingService">implementation for schedule evaluation
		/// </param>
		/// <param name="eventAdapterService">service for event adapters or wrappers
		/// </param>
		public PatternContext(FilterService filterService, SchedulingService schedulingService, ScheduleBucket scheduleBucket, EventAdapterService eventAdapterService)
		{
			this.filterService = filterService;
			this.schedulingService = schedulingService;
			this.scheduleBucket = scheduleBucket;
			this.eventAdapterService = eventAdapterService;
		}
	}
}
