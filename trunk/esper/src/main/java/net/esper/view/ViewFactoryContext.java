package net.esper.view;

import net.esper.schedule.SchedulingService;
import net.esper.schedule.ScheduleBucket;
import net.esper.event.EventAdapterService;
import net.esper.core.EPStatementHandle;
import net.esper.core.ExtensionServicesContext;
import net.esper.core.StatementContext;

/**
 * Context calss for specific views within a statement. Each view in a statement gets it's own context
 * containing the statement context.
 */
public class ViewFactoryContext
{
    private StatementContext statementContext;
    private final int streamNum;
    private final int viewNum;
    private final String namespaceName;
    private final String viewName;

    /**
     * Ctor.
     * @param statementContext is the statement-level services
     * @param streamNum is the stream number from zero to N
     * @param viewNum is the view number from zero to N
     * @param namespaceName is the view namespace
     * @param viewName is the view name
     */
    public ViewFactoryContext(StatementContext statementContext, int streamNum, int viewNum, String namespaceName, String viewName)
    {
        this.statementContext = statementContext;
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
        return statementContext.getSchedulingService();
    }

    /**
     * Returns service for generating events and handling event types.
     * @return event adapter service
     */
    public EventAdapterService getEventAdapterService()
    {
        return statementContext.getEventAdapterService();
    }

    /**
     * Returns the schedule bucket for ordering schedule callbacks within this pattern.
     * @return schedule bucket
     */
    public ScheduleBucket getScheduleBucket()
    {
        return statementContext.getScheduleBucket();
    }

    /**
     * Returns the statement's resource locks.
     * @return statement resource lock/handle
     */
    public EPStatementHandle getEpStatementHandle()
    {
        return statementContext.getEpStatementHandle();
    }

    /**
     * Returns extension svc.
     * @return svc
     */
    public ExtensionServicesContext getExtensionServicesContext()
    {
        return statementContext.getExtensionServicesContext();
    }

    /**
     * Returns statement stop svc.
     * @return snc
     */
    public StatementStopService getStatementStopService()
    {
        return statementContext.getStatementStopService();
    }

    /**
     * Returns the statement id.
     * @return statement id
     */
    public String getStatementId()
    {
        return statementContext.getStatementId();
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
        return  statementContext.toString() +
                " streamNum=" + streamNum +
                " viewNum=" + viewNum +
                " namespaceName=" + namespaceName +
                " viewName=" + viewName;
    }
}
