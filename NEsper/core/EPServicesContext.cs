using System;

using net.esper.dispatch;
using net.esper.emit;
using net.esper.eql.core;
using net.esper.eql.db;
using net.esper.events;
using net.esper.filter;
using net.esper.schedule;
using net.esper.timer;
using net.esper.view;
using net.esper.view.stream;

namespace net.esper.core
{
	/// <summary>
	/// Convenience class to instantiate implementations for all services.
	/// </summary>
	
	public sealed class EPServicesContext
	{
		private readonly FilterService filterService;
		private readonly TimerService timerService;
		private readonly SchedulingService schedulingService;
		private readonly EmitService emitService;
		private readonly DispatchService dispatchService;
		private readonly ViewService viewService;
		private readonly StreamReuseService streamReuseService;
		private readonly EventAdapterService eventAdapterService;
		private readonly AutoImportService autoImportService;
		private readonly DatabaseConfigService databaseConfigService;

        /// <summary>
        /// Gets or sets router for internal event processing.
        /// </summary>
        /// <value>The internal event router.</value>
		public InternalEventRouter InternalEventRouter
		{
			get
			{
				return internalEventRouter;
			}
			
			set
			{
				this.internalEventRouter = value;
			}
			
		}
		/// <summary> Returns filter evaluation service implementation.</summary>
		/// <returns> filter evaluation service
		/// </returns>
		public FilterService FilterService
		{
			get
			{
				return filterService;
			}
			
		}
		/// <summary> Returns time provider service implementation.</summary>
		/// <returns> time provider service
		/// </returns>
		public TimerService TimerService
		{
			get
			{
				return timerService;
			}
			
		}
		/// <summary> Returns scheduling service implementation.</summary>
		/// <returns> scheduling service
		/// </returns>
		public SchedulingService SchedulingService
		{
			get
			{
				return schedulingService;
			}
			
		}
		/// <summary> Returns service for emitting events.</summary>
		/// <returns> emit event service
		/// </returns>
		public EmitService EmitService
		{
			get
			{
				return emitService;
			}
			
		}
		/// <summary> Returns dispatch service responsible for dispatching events to listeners.</summary>
		/// <returns> dispatch service.
		/// </returns>
		public DispatchService DispatchService
		{
			get
			{
				return dispatchService;
			}
			
		}
		/// <summary> Returns services for view creation, sharing and removal.</summary>
		/// <returns> view service
		/// </returns>
		public ViewService ViewService
		{
			get
			{
				return viewService;
			}
			
		}
		/// <summary> Returns stream service.</summary>
		/// <returns> stream service
		/// </returns>
		public StreamReuseService StreamService
		{
			get
			{
				return streamReuseService;
			}
			
		}
		/// <summary> Returns event type resolution service.</summary>
		/// <returns> service resolving event type
		/// </returns>
		public EventAdapterService EventAdapterService
		{
			get
			{
				return eventAdapterService;
			}
			
		}
		/// <summary> Returns the import and class name resolution service.</summary>
		/// <returns> import service
		/// </returns>
		public AutoImportService AutoImportService
		{
			get
			{
				return autoImportService;
			}
			
		}
		/// <summary> Returns the database settings service.</summary>
		/// <returns> database info service
		/// </returns>
		public DatabaseConfigService DatabaseRefService
		{
			get
			{
				return databaseConfigService;
			}
			
		}
		
		// Must be set
		private InternalEventRouter internalEventRouter;

        /// <summary>
        /// Constructor - sets up new set of services.
        /// </summary>
        /// <param name="schedulingService">service to get time and schedule callbacks</param>
        /// <param name="eventAdapterService">service to resolve event types</param>
        /// <param name="autoImportService">service to resolve partial class names</param>
        /// <param name="databaseConfigService">service to resolve a database name to database connection factory and configs</param>
		public EPServicesContext(SchedulingService schedulingService, EventAdapterService eventAdapterService, AutoImportService autoImportService, DatabaseConfigService databaseConfigService)
		{
			this.schedulingService = schedulingService;
			this.eventAdapterService = eventAdapterService;
			this.autoImportService = autoImportService;
			this.databaseConfigService = databaseConfigService;
			
			this.filterService = FilterServiceProvider.newService();
			this.timerService = TimerServiceProvider.newService();
			this.emitService = EmitServiceProvider.newService();
			this.dispatchService = DispatchServiceProvider.newService();
			this.viewService = ViewServiceProvider.newService();
			this.streamReuseService = StreamReuseServiceProvider.newService();
		}
	}
}
