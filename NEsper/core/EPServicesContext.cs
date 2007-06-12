using System;

using net.esper.dispatch;
using net.esper.emit;
using net.esper.eql.core;
using net.esper.eql.db;
using net.esper.events;
using net.esper.filter;
using net.esper.pattern;
using net.esper.schedule;
using net.esper.timer;
using net.esper.util;
using net.esper.view;
using net.esper.view.stream;

namespace net.esper.core
{
    /// <summary>
    /// Convenience class to hold implementations for all services.
    /// </summary>

    public sealed class EPServicesContext
    {
        private readonly String engineURI;
        private readonly String engineInstanceId;
        private readonly FilterService filterService;
        private readonly TimerService timerService;
        private readonly SchedulingService schedulingService;
        private readonly EmitService emitService;
        private readonly DispatchService dispatchService;
        private readonly ViewService viewService;
        private readonly StreamFactoryService streamFactoryService;
        private readonly EventAdapterService eventAdapterService;
        private readonly EngineImportService engineImportService;
        private readonly DatabaseConfigService databaseConfigService;
        private readonly ViewResolutionService viewResolutionService;
        private readonly StatementLockFactory statementLockFactory;
        private readonly ManagedReadWriteLock eventProcessingRWLock;
        private readonly ExtensionServicesContext extensionServicesContext;
        private readonly Directory engineDirectory;
        private readonly StatementContextFactory statementContextFactory;
        private readonly PatternObjectResolutionService patternObjectResolutionService;

        // Supplied after construction to avoid circular dependency
        private StatementLifecycleSvc statementLifecycleSvc;
        private InternalEventRouter internalEventRouter;

        /// <summary>
        /// Gets or sets router for internal event processing.
        /// </summary>
        /// <value>The internal event router.</value>
        public InternalEventRouter InternalEventRouter
        {
            get { return internalEventRouter; }
            set { this.internalEventRouter = value; }
        }
        /// <summary> Returns filter evaluation service implementation.</summary>
        /// <returns> filter evaluation service
        /// </returns>
        public FilterService FilterService
        {
            get { return filterService; }
        }
        /// <summary> Returns time provider service implementation.</summary>
        /// <returns> time provider service
        /// </returns>
        public TimerService TimerService
        {
            get { return timerService; }
        }
        /// <summary> Returns scheduling service implementation.</summary>
        /// <returns> scheduling service
        /// </returns>
        public SchedulingService SchedulingService
        {
            get { return schedulingService; }
        }
        /// <summary> Returns service for emitting events.</summary>
        /// <returns> emit event service
        /// </returns>
        public EmitService EmitService
        {
            get { return emitService; }
        }
        /// <summary> Returns dispatch service responsible for dispatching events to listeners.</summary>
        /// <returns> dispatch service.
        /// </returns>
        public DispatchService DispatchService
        {
            get { return dispatchService; }
        }
        /// <summary> Returns services for view creation, sharing and removal.</summary>
        /// <returns> view service
        /// </returns>
        public ViewService ViewService
        {
            get { return viewService; }
        }
        /// <summary> Returns stream service.</summary>
        /// <returns> stream service
        /// </returns>
        public StreamFactoryService StreamService
        {
            get { return streamFactoryService; }
        }
        /// <summary> Returns event type resolution service.</summary>
        /// <returns> service resolving event type
        /// </returns>
        public EventAdapterService EventAdapterService
        {
            get { return eventAdapterService; }
        }
        /// <summary> Returns the import and class name resolution service.</summary>
        /// <returns> import service
        /// </returns>
        public EngineImportService EngineImportService
        {
            get { return engineImportService; }
        }
        /// <summary> Returns the database settings service.</summary>
        /// <returns> database info service
        /// </returns>
        public DatabaseConfigService DatabaseRefService
        {
            get { return databaseConfigService; }
        }

        /// <summary>
        /// Service for resolving view namespace and name.
        /// </summary>
        /// <returns> view resolution svc</returns>
        public ViewResolutionService ViewResolutionService
        {
            get { return viewResolutionService; }
        }

        /// <summary>
        /// Factory for statement-level locks.
        /// </summary>
        /// <returns> factory</returns>
        public StatementLockFactory StatementLockFactory
        {
            get { return statementLockFactory; }
        }

        /// <summary>
        /// Returns the event processing lock for coordinating statement administration with event processing.
        /// </summary>
        /// <returns> lock</returns>
        public ManagedReadWriteLock EventProcessingRWLock
        {
            get { return eventProcessingRWLock; }
        }

        /// <summary>
        /// Gets or sets the service dealing with starting and stopping statements.
        /// </summary>
        /// <returns> service for statement start and stop</returns>
        public StatementLifecycleSvc StatementLifecycleSvc
        {
            get { return statementLifecycleSvc; }
            set { this.statementLifecycleSvc = value; }
        }

        /// <summary>
        /// Returns extension service for adding custom the services.
        /// </summary>
        /// <returns> extension service context</returns>
        public ExtensionServicesContext ExtensionServicesContext
        {
            get { return extensionServicesContext; }
        }

        /// <summary>
        /// Returns the engine directory for getting access to engine-external resources, such as adapters
        /// </summary>
        /// <returns> engine directory</returns>
        public Directory EngineDirectory
        {
            get { return engineDirectory; }
        }

        /// <summary>
        /// Destroy services.
        /// </summary>
        public void Destroy()
        {
            if (extensionServicesContext != null)
            {
                extensionServicesContext.Destroy();
            }
        }

        /// <summary>
        /// Returns the factory to use for creating a statement context.
        /// </summary>
        /// <returns> statement context factory</returns>
        public StatementContextFactory StatementContextFactory
        {
            get { return statementContextFactory; }
        }

        /// <summary>
        /// Returns the engine URI.
        /// </summary>
        /// <returns> engine URI</returns>
        public String EngineURI
        {
            get { return engineURI; }
        }

        /// <summary>
        /// Returns the engine instance ID.
        /// </summary>
        /// <returns> instance id</returns>
        public String EngineInstanceId
        {
            get { return engineInstanceId; }
        }

        /// <summary>
        /// Returns the pattern object resolver.
        /// </summary>
        /// <returns> resolver for plug-in pattern objects.</returns>
        public net.esper.pattern.PatternObjectResolutionService PatternObjectResolutionService
        {
            get { return patternObjectResolutionService; }
        }

       /// <summary>Constructor - sets up new set of services.</summary>
       /// <param name="engineURI">is the engine URI</param>
       /// <param name="engineInstanceId">is the name of the engine instance</param>
       /// <param name="schedulingService">service to get time and schedule callbacks</param>
       /// <param name="eventAdapterService">service to resolve event types</param>
       /// <param name="databaseConfigService">
       /// service to resolve a database name to database connection factory and configs
       /// </param>
       /// <param name="viewResolutionService">
       /// resolves view namespace and name to view factory class
       /// </param>
       /// <param name="statementLockFactory">creates statement-level locks</param>
       /// <param name="eventProcessingRWLock">
       /// is the engine lock for statement management
       /// </param>
       /// <param name="extensionServicesContext">
       /// marker interface allows adding additional services
       /// </param>
       /// <param name="engineImportService">
       /// is engine imported static func packages and aggregation functions
       /// </param>
       /// <param name="statementContextFactory">
       /// is the factory to use to create statement context objects
       /// </param>
       /// <param name="engineEnvContext">
       /// is engine environment/directory information for use with adapters and external env
       /// </param>
       /// <param name="patternObjectResolutionService">resolves plug-in pattern objects</param>
        public EPServicesContext(String engineURI,
                                 String engineInstanceId,
                                 SchedulingService schedulingService,
                                 EventAdapterService eventAdapterService,
                                 EngineImportService engineImportService,
                                 DatabaseConfigService databaseConfigService,
                                 ViewResolutionService viewResolutionService,
                                 StatementLockFactory statementLockFactory,
                                 ManagedReadWriteLock eventProcessingRWLock,
                                 ExtensionServicesContext extensionServicesContext,
                                 Directory engineDirectory,
                                 net.esper.core.StatementContextFactory statementContextFactory,
                                 net.esper.pattern.PatternObjectResolutionService patternObjectResolutionService)
        {
            this.engineURI = engineURI;
            this.engineInstanceId = engineInstanceId;
            this.schedulingService = schedulingService;
            this.eventAdapterService = eventAdapterService;
            this.engineImportService = engineImportService;
            this.databaseConfigService = databaseConfigService;
            this.filterService = FilterServiceProvider.NewService();
            this.timerService = TimerServiceProvider.NewService();
            this.emitService = EmitServiceProvider.NewService();
            this.dispatchService = DispatchServiceProvider.NewService();
            this.viewService = ViewServiceProvider.NewService();
            this.streamFactoryService = StreamFactoryServiceProvider.NewService(eventAdapterService);
            this.viewResolutionService = viewResolutionService;
            this.statementLockFactory = statementLockFactory;
            this.eventProcessingRWLock = eventProcessingRWLock;
            this.extensionServicesContext = extensionServicesContext;
            this.engineDirectory = engineDirectory;
            this.statementContextFactory = statementContextFactory;
            this.patternObjectResolutionService = patternObjectResolutionService;
        }
    }
}
