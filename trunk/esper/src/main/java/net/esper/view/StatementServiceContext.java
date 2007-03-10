package net.esper.view;

import net.esper.core.EPStatementHandle;
import net.esper.core.ExtensionServicesContext;
import net.esper.event.EventAdapterService;
import net.esper.schedule.ScheduleBucket;
import net.esper.schedule.SchedulingService;

/**
 * Contains handles to the implementation of the the scheduling service for use in view evaluation.
 */
public final class StatementServiceContext
{
    private final String statementId;
    private final String statementName;
    private final SchedulingService schedulingService;
    private final ScheduleBucket scheduleBucket;
    private final EventAdapterService eventAdapterService;
    private final EPStatementHandle epStatementHandle;
    private final ViewResolutionService viewResultionService;
    private final ExtensionServicesContext extensionServicesContext;
    private final StatementStopService statementStopService;

    /**
     * Constructor.
     * @param schedulingService implementation for schedule registration
     * @param scheduleBucket is for ordering scheduled callbacks within the view statements
     * @param eventAdapterService service for generating events and handling event types
     * @param epStatementHandle is the statements-own handle for use in registering callbacks with services
     * @param viewResultionService is a service for resolving view namespace and name to a view factory
     */
    public StatementServiceContext(String statementId,
                                   String statementName,
                              SchedulingService schedulingService,
                              ScheduleBucket scheduleBucket,
                              EventAdapterService eventAdapterService,
                              EPStatementHandle epStatementHandle,
                              ViewResolutionService viewResultionService,
                              ExtensionServicesContext extensionServicesContext,
                              StatementStopService statementStopService)
    {
        this.statementId = statementId;
        this.statementName = statementName;
        this.schedulingService = schedulingService;
        this.eventAdapterService = eventAdapterService;
        this.scheduleBucket = scheduleBucket;
        this.epStatementHandle = epStatementHandle;
        this.viewResultionService = viewResultionService;
        this.extensionServicesContext = extensionServicesContext;
        this.statementStopService = statementStopService;
    }

    public String getStatementId()
    {
        return statementId;
    }

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

    public ViewResolutionService getViewResultionService()
    {
        return viewResultionService;
    }

    public ExtensionServicesContext getExtensionServicesContext()
    {
        return extensionServicesContext;
    }

    public StatementStopService getStatementStopService()
    {
        return statementStopService;
    }

    public String toString()
    {
        return  " statementId=" + statementId +
                " statementName=" + statementName;
    }

}