package net.esper.core;

import net.esper.dispatch.DispatchService;
import net.esper.dispatch.DispatchServiceProvider;
import net.esper.emit.EmitService;
import net.esper.emit.EmitServiceProvider;
import net.esper.eql.core.AutoImportService;
import net.esper.eql.db.DatabaseConfigService;
import net.esper.event.EventAdapterService;
import net.esper.filter.FilterService;
import net.esper.filter.FilterServiceProvider;
import net.esper.schedule.SchedulingService;
import net.esper.timer.TimerService;
import net.esper.timer.TimerServiceProvider;
import net.esper.view.ViewService;
import net.esper.view.ViewServiceProvider;
import net.esper.view.stream.StreamFactoryService;
import net.esper.view.stream.StreamFactoryServiceProvider;

/**
 * Convenience class to instantiate implementations for all services.
 */
public final class EPServicesContext
{
    private final FilterService filterService;
    private final TimerService timerService;
    private final SchedulingService schedulingService;
    private final EmitService emitService;
    private final DispatchService dispatchService;
    private final ViewService viewService;
    private final StreamFactoryService streamFactoryService;
    private final EventAdapterService eventAdapterService;
    private final AutoImportService autoImportService;
    private final DatabaseConfigService databaseConfigService;

    // Must be set
    private InternalEventRouter internalEventRouter;

    /**
     * Constructor - sets up new set of services.
     * @param schedulingService service to get time and schedule callbacks
     * @param eventAdapterService service to resolve event types
     * @param autoImportService service to resolve partial class names
     * @param databaseConfigService service to resolve a database name to database connection factory and configs
     */
    public EPServicesContext(SchedulingService schedulingService,
                             EventAdapterService eventAdapterService,
                             AutoImportService autoImportService,
                             DatabaseConfigService databaseConfigService)
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
        this.streamFactoryService = StreamFactoryServiceProvider.newService();
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
    public AutoImportService getAutoImportService()
    {
    	return autoImportService;
    }

    /**
     * Returns the database settings service.
     * @return database info service
     */
    public DatabaseConfigService getDatabaseRefService()
    {
        return databaseConfigService;
    }
}
