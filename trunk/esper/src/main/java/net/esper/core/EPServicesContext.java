/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.core;

import net.esper.dispatch.DispatchService;
import net.esper.dispatch.DispatchServiceProvider;
import net.esper.emit.EmitService;
import net.esper.emit.EmitServiceProvider;
import net.esper.eql.core.EngineImportService;
import net.esper.eql.db.DatabaseConfigService;
import net.esper.event.EventAdapterService;
import net.esper.filter.FilterService;
import net.esper.filter.FilterServiceProvider;
import net.esper.schedule.SchedulingService;
import net.esper.timer.TimerService;
import net.esper.timer.TimerServiceProvider;
import net.esper.util.ManagedReadWriteLock;
import net.esper.view.ViewResolutionService;
import net.esper.view.ViewService;
import net.esper.view.ViewServiceProvider;
import net.esper.view.stream.StreamFactoryService;
import net.esper.view.stream.StreamFactoryServiceProvider;
import net.esper.pattern.PatternObjectResolutionService;

/**
 * Convenience class to hold implementations for all services.
 */
public final class EPServicesContext
{
    private final String engineURI;
    private final String engineInstanceId;
    private final FilterService filterService;
    private final TimerService timerService;
    private final SchedulingService schedulingService;
    private final EmitService emitService;
    private final DispatchService dispatchService;
    private final ViewService viewService;
    private final StreamFactoryService streamFactoryService;
    private final EventAdapterService eventAdapterService;
    private final EngineImportService engineImportService;
    private final DatabaseConfigService databaseConfigService;
    private final ViewResolutionService viewResolutionService;
    private final StatementLockFactory statementLockFactory;
    private final ManagedReadWriteLock eventProcessingRWLock;
    private final ExtensionServicesContext extensionServicesContext;
    private final EngineEnvContext engineEnvContext;
    private final StatementContextFactory statementContextFactory;
    private final PatternObjectResolutionService patternObjectResolutionService;

    // Supplied after construction to avoid circular dependency
    private StatementLifecycleSvc statementLifecycleSvc;
    private InternalEventRouter internalEventRouter;

    /**
     * Constructor - sets up new set of services.
     * @param engineURI is the engine URI
     * @param engineInstanceId is the name of the engine instance
     * @param schedulingService service to get time and schedule callbacks
     * @param eventAdapterService service to resolve event types
     * @param databaseConfigService service to resolve a database name to database connection factory and configs
     * @param viewResolutionService resolves view namespace and name to view factory class
     * @param statementLockFactory creates statement-level locks
     * @param eventProcessingRWLock is the engine lock for statement management
     * @param extensionServicesContext marker interface allows adding additional services
     * @param engineImportService is engine imported static func packages and aggregation functions
     * @param statementContextFactory is the factory to use to create statement context objects
     * @param engineEnvContext is engine environment/directory information for use with adapters and external env
     * @param patternObjectResolutionService resolves plug-in pattern objects 
     */
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
                             EngineEnvContext engineEnvContext,
                             StatementContextFactory statementContextFactory,
                             PatternObjectResolutionService patternObjectResolutionService)
    {
        this.engineURI = engineURI;
        this.engineInstanceId = engineInstanceId;
        this.schedulingService = schedulingService;
        this.eventAdapterService = eventAdapterService;
        this.engineImportService = engineImportService;
        this.databaseConfigService = databaseConfigService;
        this.filterService = FilterServiceProvider.newService();
        this.timerService = TimerServiceProvider.newService();
        this.emitService = EmitServiceProvider.newService();
        this.dispatchService = DispatchServiceProvider.newService();
        this.viewService = ViewServiceProvider.newService();
        this.streamFactoryService = StreamFactoryServiceProvider.newService(eventAdapterService);
        this.viewResolutionService = viewResolutionService;
        this.statementLockFactory = statementLockFactory;
        this.eventProcessingRWLock = eventProcessingRWLock;
        this.extensionServicesContext = extensionServicesContext;
        this.engineEnvContext = engineEnvContext;
        this.statementContextFactory = statementContextFactory;
        this.patternObjectResolutionService = patternObjectResolutionService;
    }

    /**
     * Sets the service dealing with starting and stopping statements.
     * @param statementLifecycleSvc statement lifycycle svc
     */
    public void setStatementLifecycleSvc(StatementLifecycleSvc statementLifecycleSvc)
    {
        this.statementLifecycleSvc = statementLifecycleSvc;
    }

    /**
     * Returns router for internal event processing.
     * @return router for internal event processing
     */
    public InternalEventRouter getInternalEventRouter()
    {
        return internalEventRouter;
    }

    /**
     * Set the router for internal event processing.
     * @param internalEventRouter router to use
     */
    public void setInternalEventRouter(InternalEventRouter internalEventRouter)
    {
        this.internalEventRouter = internalEventRouter;
    }

    /**
     * Returns filter evaluation service implementation.
     * @return filter evaluation service
     */
    public final FilterService getFilterService()
    {
        return filterService;
    }

    /**
     * Returns time provider service implementation.
     * @return time provider service
     */
    public final TimerService getTimerService()
    {
        return timerService;
    }

    /**
     * Returns scheduling service implementation.
     * @return scheduling service
     */
    public final SchedulingService getSchedulingService()
    {
        return schedulingService;
    }

    /**
     * Returns service for emitting events.
     * @return emit event service
     */
    public final EmitService getEmitService()
    {
        return emitService;
    }

    /**
     * Returns dispatch service responsible for dispatching events to listeners.
     * @return dispatch service.
     */
    public DispatchService getDispatchService()
    {
        return dispatchService;
    }

    /**
     * Returns services for view creation, sharing and removal.
     * @return view service
     */
    public ViewService getViewService()
    {
        return viewService;
    }

    /**
     * Returns stream service.
     * @return stream service
     */
    public StreamFactoryService getStreamService()
    {
        return streamFactoryService;
    }

    /**
     * Returns event type resolution service.
     * @return service resolving event type
     */
    public EventAdapterService getEventAdapterService()
    {
        return eventAdapterService;
    }

    /**
     * Returns the import and class name resolution service.
     * @return import service
     */
    public EngineImportService getEngineImportService()
    {
    	return engineImportService;
    }

    /**
     * Returns the database settings service.
     * @return database info service
     */
    public DatabaseConfigService getDatabaseRefService()
    {
        return databaseConfigService;
    }

    /**
     * Service for resolving view namespace and name.
     * @return view resolution svc
     */
    public ViewResolutionService getViewResolutionService()
    {
        return viewResolutionService;
    }

    /**
     * Factory for statement-level locks.
     * @return factory
     */
    public StatementLockFactory getStatementLockFactory()
    {
        return statementLockFactory;
    }

    /**
     * Returns the event processing lock for coordinating statement administration with event processing.
     * @return lock
     */
    public ManagedReadWriteLock getEventProcessingRWLock()
    {
        return eventProcessingRWLock;
    }

    /**
     * Returns statement lifecycle svc
     * @return service for statement start and stop
     */
    public StatementLifecycleSvc getStatementLifecycleSvc()
    {
        return statementLifecycleSvc;
    }

    /**
     * Returns extension service for adding custom the services.
     * @return extension service context
     */
    public ExtensionServicesContext getExtensionServicesContext()
    {
        return extensionServicesContext;
    }

    /**
     * Returns the engine environment context for getting access to engine-external resources, such as adapters
     * @return engine environment context
     */
    public EngineEnvContext getEngineEnvContext()
    {
        return engineEnvContext;
    }

    /**
     * Destroy services.
     */
    public void destroy()
    {
        if (extensionServicesContext != null)
        {
            extensionServicesContext.destroy();
        }
    }

    /**
     * Returns the factory to use for creating a statement context.
     * @return statement context factory
     */
    public StatementContextFactory getStatementContextFactory()
    {
        return statementContextFactory;
    }

    /**
     * Returns the engine URI.
     * @return engine URI
     */
    public String getEngineURI()
    {
        return engineURI;
    }

    /**
     * Returns the engine instance ID.
     * @return instance id
     */
    public String getEngineInstanceId()
    {
        return engineInstanceId;
    }

    /**
     * Returns the pattern object resolver.
     * @return resolver for plug-in pattern objects.
     */
    public PatternObjectResolutionService getPatternObjectResolutionService()
    {
        return patternObjectResolutionService;
    }
}
