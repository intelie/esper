using System;

using net.esper.events;
using net.esper.schedule;

namespace net.esper.view
{
	
	/// <summary>
    /// Contains handles to the implementation of the the scheduling service for use in view evaluation.
    /// </summary>
	
    public sealed class ViewServiceContext
	{
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
		/// <summary> Returns service for generating events and handling event types.</summary>
		/// <returns> event adapter service
		/// </returns>
		public EventAdapterService EventAdapterService
		{
			get
			{
				return eventAdapterService;
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

		private readonly SchedulingService schedulingService;
		private readonly ScheduleBucket scheduleBucket;
		private readonly EventAdapterService eventAdapterService;
		
		/// <summary> Constructor.</summary>
		/// <param name="schedulingService">implementation for schedule registration
		/// </param>
		/// <param name="scheduleBucket">is for ordering scheduled callbacks within the view statements
		/// </param>
		/// <param name="eventAdapterService">service for generating events and handling event types
		/// </param>
		
        public ViewServiceContext(SchedulingService schedulingService, ScheduleBucket scheduleBucket, EventAdapterService eventAdapterService)
		{
			this.schedulingService = schedulingService;
			this.eventAdapterService = eventAdapterService;
			this.scheduleBucket = scheduleBucket;
		}
	}
}