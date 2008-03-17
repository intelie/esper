/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.core;

import com.espertech.esper.dispatch.DispatchService;
import com.espertech.esper.dispatch.DispatchServiceProvider;
import com.espertech.esper.emit.EmitService;
import com.espertech.esper.emit.EmitServiceProvider;
import com.espertech.esper.epl.core.EngineImportService;
import com.espertech.esper.epl.core.EngineSettingsService;
import com.espertech.esper.epl.db.DatabaseConfigService;
import com.espertech.esper.epl.named.NamedWindowService;
import com.espertech.esper.epl.spec.PluggableObjectCollection;
import com.espertech.esper.epl.variable.VariableService;
import com.espertech.esper.epl.view.OutputConditionFactory;
import com.espertech.esper.event.EventAdapterService;
import com.espertech.esper.filter.FilterService;
import com.espertech.esper.schedule.SchedulingService;
import com.espertech.esper.timer.TimerService;
import com.espertech.esper.util.ManagedReadWriteLock;
import com.espertech.esper.view.ViewService;
import com.espertech.esper.view.ViewServiceProvider;
import com.espertech.esper.view.stream.StreamFactoryService;

/**
 * Convenience class to hold implementations for all services.
 */
public final class EPServicesContext
{
    private String engineURI;
    private String engineInstanceId;
    private FilterService filterService;
    private TimerService timerService;
    private SchedulingService schedulingService;
    private EmitService emitService;
    private DispatchService dispatchService;
    private ViewService viewService;
    private StreamFactoryService streamFactoryService;
    private EventAdapterService eventAdapterService;
    private EngineImportService engineImportService;
    private EngineSettingsService engineSettingsService;
    private DatabaseConfigService databaseConfigService;
    private PluggableObjectCollection plugInViews;
    private StatementLockFactory statementLockFactory;
    private ManagedReadWriteLock eventProcessingRWLock;
    private ExtensionServicesContext extensionServicesContext;
    private EngineEnvContext engineEnvContext;
    private StatementContextFactory statementContextFactory;
    private PluggableObjectCollection plugInPatternObjects;
    private OutputConditionFactory outputConditionFactory;
    private NamedWindowService namedWindowService;
    private VariableService variableService;

    // Supplied after construction to avoid circular dependency
    private StatementLifecycleSvc statementLifecycleSvc;
    private InternalEventRouter internalEventRouter;

    /**
     * Constructor - sets up new set of services.
     * @param engineURI is the engine URI
     * @param schedulingService service to get time and schedule callbacks
     * @param eventAdapterService service to resolve event types
     * @param databaseConfigService service to resolve a database name to database connection factory and configs
     * @param plugInViews resolves view namespace and name to view factory class
     * @param statementLockFactory creates statement-level locks
     * @param eventProcessingRWLock is the engine lock for statement management
     * @param extensionServicesContext marker interface allows adding additional services
     * @param engineImportService is engine imported static func packages and aggregation functions
     * @param engineSettingsService provides engine settings
     * @param statementContextFactory is the factory to use to create statement context objects
     * @param engineEnvContext is engine environment/directory information for use with adapters and external env
     * @param plugInPatternObjects resolves plug-in pattern objects
     * @param outputConditionFactory factory for output condition objects
     * @param timerService is the timer service
     * @param filterService the filter service
     * @param streamFactoryService is hooking up filters to streams
     * @param namedWindowService is holding information about the named windows active in the system
     * @param variableService provides access to variable values
     */
    public EPServicesContext(String engineURI,
                             SchedulingService schedulingService,
                             EventAdapterService eventAdapterService,
                             EngineImportService engineImportService,
                             EngineSettingsService engineSettingsService,
                             DatabaseConfigService databaseConfigService,
                             PluggableObjectCollection plugInViews,
                             StatementLockFactory statementLockFactory,
                             ManagedReadWriteLock eventProcessingRWLock,
                             ExtensionServicesContext extensionServicesContext,
                             EngineEnvContext engineEnvContext,
                             StatementContextFactory statementContextFactory,
                             PluggableObjectCollection plugInPatternObjects,
                             OutputConditionFactory outputConditionFactory,
                             TimerService timerService,
                             FilterService filterService,
                             StreamFactoryService streamFactoryService,
                             NamedWindowService namedWindowService,
                             VariableService variableService)
    {
        this.engineURI = engineURI;
        this.schedulingService = schedulingService;
        this.eventAdapterService = eventAdapterService;
        this.engineImportService = engineImportService;
        this.engineSettingsService = engineSettingsService;
        this.databaseConfigService = databaseConfigService;
        this.filterService = filterService;
        this.timerService = timerService;
        this.emitService = EmitServiceProvider.newService();
        this.dispatchService = DispatchServiceProvider.newService();
        this.viewService = ViewServiceProvider.newService();
        this.streamFactoryService = streamFactoryService;
        this.plugInViews = plugInViews;
        this.statementLockFactory = statementLockFactory;
        this.eventProcessingRWLock = eventProcessingRWLock;
        this.extensionServicesContext = extensionServicesContext;
        this.engineEnvContext = engineEnvContext;
        this.statementContextFactory = statementContextFactory;
        this.plugInPatternObjects = plugInPatternObjects;
        this.outputConditionFactory = outputConditionFactory;
        this.namedWindowService = namedWindowService;
        this.variableService = variableService;
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
     * Information to resolve plug-in view namespace and name.
     * @return plug-in view information
     */
    public PluggableObjectCollection getPlugInViews()
    {
        return plugInViews;
    }

    /**
     * Information to resolve plug-in pattern object namespace and name.
     * @return plug-in pattern object information
     */
    public PluggableObjectCollection getPlugInPatternObjects()
    {
        return plugInPatternObjects;
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
        if (statementLifecycleSvc != null)
        {
            statementLifecycleSvc.destroy();
        }
        if (extensionServicesContext != null)
        {
            extensionServicesContext.destroy();
        }
        if (filterService != null)
        {
            filterService.destroy();
        }
        if (schedulingService != null)
        {
            schedulingService.destroy();
        }
        if (streamFactoryService != null)
        {
            streamFactoryService.destroy();
        }
        if (namedWindowService != null)
        {
            namedWindowService.destroy();
        }
    }

    /**
     * Destroy services.
     */
    public void initialize()
    {
        this.statementLifecycleSvc = null;
        this.engineURI = null;
        this.engineInstanceId = null;
        this.schedulingService = null;
        this.eventAdapterService = null;
        this.engineImportService = null;
        this.engineSettingsService = null;
        this.databaseConfigService = null;
        this.filterService = null;
        this.timerService = null;
        this.emitService = null;
        this.dispatchService = null;
        this.viewService = null;
        this.streamFactoryService = null;
        this.plugInViews = null;
        this.statementLockFactory = null;
        this.eventProcessingRWLock = null;
        this.extensionServicesContext = null;
        this.engineEnvContext = null;
        this.statementContextFactory = null;
        this.plugInPatternObjects = null;
        this.namedWindowService = null;
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
     * Returns engine settings.
     * @return settings
     */
    public EngineSettingsService getEngineSettingsService()
    {
        return engineSettingsService;
    }

    /**
     * Returns the output condition factory
     * @return factory for output condition
     */
    public OutputConditionFactory getOutputConditionFactory()
    {
        return outputConditionFactory;
    }

    /**
     * Returns the named window management service.
     * @return service for managing named windows
     */
    public NamedWindowService getNamedWindowService()
    {
        return namedWindowService;
    }

    /**
     * Returns the variable service.
     * @return variable service
     */
    public VariableService getVariableService()
    {
        return variableService;
    }
}
