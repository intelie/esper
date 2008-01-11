package net.esper.eql.view;

import net.esper.event.EventBean;
import net.esper.view.ViewSupport;
import net.esper.eql.spec.SelectClauseStreamSelectorEnum;
import net.esper.core.InternalEventRouter;
import net.esper.core.EPStatementHandle;

public class OutputStrategyPostProcess implements OutputStrategy
{
    private final boolean isRoute;
    private final boolean isRouteRStream;
    private final SelectClauseStreamSelectorEnum selectStreamDirEnum;
    private final InternalEventRouter internalEventRouter;
    private final EPStatementHandle epStatementHandle;

    public OutputStrategyPostProcess(boolean route, boolean routeRStream, SelectClauseStreamSelectorEnum selectStreamDirEnum, InternalEventRouter internalEventRouter, EPStatementHandle epStatementHandle)
    {
        isRoute = route;
        isRouteRStream = routeRStream;
        this.selectStreamDirEnum = selectStreamDirEnum;
        this.internalEventRouter = internalEventRouter;
        this.epStatementHandle = epStatementHandle;
    }

    public void output(boolean forceUpdate, EventBean[] newEvents, EventBean[] oldEvents, ViewSupport finalView)
    {
        // route first
        if (isRoute)
        {
            if ((newEvents != null) && (!isRouteRStream))
            {
                route(newEvents);
            }

            if ((oldEvents != null) && (isRouteRStream))
            {
                route(oldEvents);
            }
        }

        // discard one side of results
        if (selectStreamDirEnum == SelectClauseStreamSelectorEnum.RSTREAM_ONLY)
        {
            newEvents = oldEvents;
            oldEvents = null;
        }
        else if (selectStreamDirEnum == SelectClauseStreamSelectorEnum.ISTREAM_ONLY)
        {
            oldEvents = null;
        }
        else if (selectStreamDirEnum == SelectClauseStreamSelectorEnum.RSTREAM_ISTREAM_BOTH)
        {
            // no action required
        }
        else
        {
            throw new IllegalStateException("Unknown stream selector " + selectStreamDirEnum);
        }

        // dispatch
        if(newEvents != null || oldEvents != null)
        {
            finalView.updateChildren(newEvents, oldEvents);
        }
        else if(forceUpdate)
        {
            finalView.updateChildren(null, null);
        }
    }

    private void route(EventBean[] events)
    {
        internalEventRouter.route(events, epStatementHandle);
    }
}
