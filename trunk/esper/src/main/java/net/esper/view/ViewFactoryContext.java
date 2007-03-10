package net.esper.view;

import net.esper.schedule.SchedulingService;
import net.esper.schedule.ScheduleBucket;
import net.esper.event.EventAdapterService;
import net.esper.core.EPStatementHandle;
import net.esper.core.ExtensionServicesContext;

public class ViewFactoryContext
{
    private StatementServiceContext statementServiceContext;
    private final int streamNum;
    private final int viewNum;
    private final String namespaceName;
    private final String viewName;

    public ViewFactoryContext(StatementServiceContext statementServiceContext, int streamNum, int viewNum, String namespaceName, String viewName)
    {
        this.statementServiceContext = statementServiceContext;
        this.streamNum = streamNum;
        this.viewNum = viewNum;
        this.namespaceName = namespaceName;
        this.viewName = viewName;
    }

    /**
     * Returns service to use for schedule evaluation.
     * @return schedule evaluation service implemetation
     */
    public final SchedulingService getSchedulingService()
    {
        return statementServiceContext.getSchedulingService();
    }

    /**
     * Returns service for generating events and handling event types.
     * @return event adapter service
     */
    public EventAdapterService getEventAdapterService()
    {
        return statementServiceContext.getEventAdapterService();
    }

    /**
     * Returns the schedule bucket for ordering schedule callbacks within this pattern.
     * @return schedule bucket
     */
    public ScheduleBucket getScheduleBucket()
    {
        return statementServiceContext.getScheduleBucket();
    }

    /**
     * Returns the statement's resource locks.
     * @return statement resource lock/handle
     */
    public EPStatementHandle getEpStatementHandle()
    {
        return statementServiceContext.getEpStatementHandle();
    }

    public ExtensionServicesContext getExtensionServicesContext()
    {
        return statementServiceContext.getExtensionServicesContext();
    }

    public StatementStopService getStatementStopService()
    {
        return statementServiceContext.getStatementStopService();
    }

    public String getStatementId()
    {
        return statementServiceContext.getStatementId();
    }

    public int getStreamNum()
    {
        return streamNum;
    }

    public int getViewNum()
    {
        return viewNum;
    }

    public String getNamespaceName()
    {
        return namespaceName;
    }

    public String getViewName()
    {
        return viewName;
    }

    public String toString()
    {
        return  statementServiceContext.toString() +
                " streamNum=" + streamNum +
                " viewNum=" + viewNum +
                " namespaceName=" + namespaceName +
                " viewName=" + viewName;
    }
}
