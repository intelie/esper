package net.esper.core;

import net.esper.dispatch.DispatchService;
import net.esper.dispatch.DispatchServiceProvider;
import net.esper.emit.EmitService;
import net.esper.emit.EmitServiceProvider;
import net.esper.eql.expression.AutoImportService;
import net.esper.event.EventAdapterService;
import net.esper.filter.FilterService;
import net.esper.filter.FilterServiceProvider;
import net.esper.schedule.SchedulingService;
import net.esper.schedule.SchedulingServiceProvider;
import net.esper.timer.TimerService;
import net.esper.timer.TimerServiceProvider;
import net.esper.view.ViewService;
import net.esper.view.ViewServiceProvider;
import net.esper.view.stream.StreamReuseService;
import net.esper.view.stream.StreamReuseServiceProvider;

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
    private final StreamReuseService streamReuseService;
    private final EventAdapterService eventAdapterService;
    private final AutoImportService autoImportService;

    // Must be set
    private InternalEventRouter internalEventRouter;

    /**
     * Constructor - sets up new set of services.
     * @param eventAdapterService service to resolve event types
     * @param autoImportService service to resolve partial class names
     */
    public EPServicesContext(EventAdapterService eventAdapterService, AutoImportService autoImportService)
    {
        this.filterService = FilterServiceProvider.newService();
        this.timerService = TimerServiceProvider.newService();
        this.schedulingService = SchedulingServiceProvider.newService();
        this.emitService = EmitServiceProvider.newService();
        this.dispatchService = DispatchServiceProvider.newService();
        this.viewService = ViewServiceProvider.newService();
        this.streamReuseService = StreamReuseServiceProvider.newService();
        this.eventAdapterService = eventAdapterService;
        this.autoImportService = autoImportService;
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
    public StreamReuseService getStreamService()
    {
        return streamReuseService;
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
     * Returns the import and class name resolution service
     * @return import service
     */
    public AutoImportService getAutoImportService()
    {
    	return autoImportService;
    }

}
