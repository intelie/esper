package net.esper.view;

import net.esper.schedule.SchedulingService;
import net.esper.schedule.ScheduleBucket;
import net.esper.event.EventAdapterService;
import net.esper.core.EPStatementHandle;
import net.esper.core.ExtensionServicesContext;

/**
 * Context calss for specific views within a statement. Each view in a statement gets it's own context
 * containing the statement context.
 */
public class ViewFactoryContext
{
    private StatementServiceContext statementServiceContext;
    private final int streamNum;
    private final int viewNum;
    private final String namespaceName;
    private final String viewName;

    /**
     * Ctor.
     * @param statementServiceContext is the statement-level services
     * @param streamNum is the stream number from zero to N
     * @param viewNum is the view number from zero to N
     * @param namespaceName is the view namespace
     * @param viewName is the view name
     */
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

    /**
     * Returns extension svc.
     * @return svc
     */
    public ExtensionServicesContext getExtensionServicesContext()
    {
        return statementServiceContext.getExtensionServicesContext();
    }

    /**
     * Returns statement stop svc.
     * @return snc
     */
    public StatementStopService getStatementStopService()
    {
        return statementServiceContext.getStatementStopService();
    }

    /**
     * Returns the statement id.
     * @return statement id
     */
    public String getStatementId()
    {
        return statementServiceContext.getStatementId();
    }

    /**
     * Returns the stream number.
     * @return stream number
     */
    public int getStreamNum()
    {
        return streamNum;
    }

    /**
     * Returns the view number
     * @return view number
     */
    public int getViewNum()
    {
        return viewNum;
    }

    /**
     * Returns the view namespace name.
     * @return namespace name
     */
    public String getNamespaceName()
    {
        return namespaceName;
    }

    /**
     * Returns the view name.
     * @return view name
     */
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
