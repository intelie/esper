package net.esper.core;

import net.esper.event.EventAdapterService;
import net.esper.schedule.ScheduleBucket;
import net.esper.schedule.SchedulingService;
import net.esper.view.StatementStopService;
import net.esper.view.ViewResolutionService;
import net.esper.eql.core.MethodResolutionService;

/**
 * Contains handles to the implementation of the the scheduling service for use in view evaluation.
 */
public final class StatementContext
{
    private final String statementId;
    private final String statementName;
    private final String expression;
    private final SchedulingService schedulingService;
    private final ScheduleBucket scheduleBucket;
    private final EventAdapterService eventAdapterService;
    private final EPStatementHandle epStatementHandle;
    private final ViewResolutionService viewResultionService;
    private final ExtensionServicesContext extensionServicesContext;
    private final StatementStopService statementStopService;
    private final MethodResolutionService methodResolutionService;

    /**
     * Constructor.
     * @param statementId is the statement is assigned for the statement for which this context exists
     * @param statementName is the statement name
     * @param schedulingService implementation for schedule registration
     * @param scheduleBucket is for ordering scheduled callbacks within the view statements
     * @param eventAdapterService service for generating events and handling event types
     * @param epStatementHandle is the statements-own handle for use in registering callbacks with services
     * @param viewResultionService is a service for resolving view namespace and name to a view factory
     * @param extensionServicesContext provide extension points for custom statement resources
     * @param statementStopService for registering a callback invoked when a statement is stopped
     */
    public StatementContext(String statementId,
                                   String statementName,
                                   String expression,
                              SchedulingService schedulingService,
                              ScheduleBucket scheduleBucket,
                              EventAdapterService eventAdapterService,
                              EPStatementHandle epStatementHandle,
                              ViewResolutionService viewResultionService,
                              ExtensionServicesContext extensionServicesContext,
                              StatementStopService statementStopService,
                              MethodResolutionService methodResolutionService)
    {
        this.statementId = statementId;
        this.statementName = statementName;
        this.expression = expression;
        this.schedulingService = schedulingService;
        this.eventAdapterService = eventAdapterService;
        this.scheduleBucket = scheduleBucket;
        this.epStatementHandle = epStatementHandle;
        this.viewResultionService = viewResultionService;
        this.extensionServicesContext = extensionServicesContext;
        this.statementStopService = statementStopService;
        this.methodResolutionService = methodResolutionService;
    }

    /**
     * Returns the statement id.
     * @return statement id
     */
    public String getStatementId()
    {
        return statementId;
    }

    /**
     * Returns the statement name
     * @return statement name
     */
    public String getStatementName()
    {
        return statementName;
    }

    /**
     * Returns service to use for schedule evaluation.
     * @return schedule evaluation service implemetation
     */
    public final SchedulingService getSchedulingService()
    {
        return schedulingService;
    }

    /**
     * Returns service for generating events and handling event types.
     * @return event adapter service
     */
    public EventAdapterService getEventAdapterService()
    {
        return eventAdapterService;
    }

    /**
     * Returns the schedule bucket for ordering schedule callbacks within this pattern.
     * @return schedule bucket
     */
    public ScheduleBucket getScheduleBucket()
    {
        return scheduleBucket;
    }

    /**
     * Returns the statement's resource locks.
     * @return statement resource lock/handle
     */
    public EPStatementHandle getEpStatementHandle()
    {
        return epStatementHandle;
    }

    /**
     * Returns view resolution svc.
     * @return view resolution
     */
    public ViewResolutionService getViewResultionService()
    {
        return viewResultionService;
    }

    /**
     * Returns extension context.
     * @return context
     */
    public ExtensionServicesContext getExtensionServicesContext()
    {
        return extensionServicesContext;
    }

    /**
     * Returns statement stop subscription taker.
     * @return stop service
     */
    public StatementStopService getStatementStopService()
    {
        return statementStopService;
    }

    public MethodResolutionService getMethodResolutionService()
    {
        return methodResolutionService;
    }

    public String getExpression()
    {
        return expression;
    }

    public String toString()
    {
        return  " statementId=" + statementId +
                " statementName=" + statementName;
    }
}