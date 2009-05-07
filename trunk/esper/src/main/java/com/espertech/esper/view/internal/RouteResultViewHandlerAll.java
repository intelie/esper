package com.espertech.esper.view.internal;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.core.InternalEventRouter;
import com.espertech.esper.core.EPStatementHandle;
import com.espertech.esper.collection.UniformPair;
import com.espertech.esper.epl.core.ResultSetProcessor;
import com.espertech.esper.epl.expression.ExprNode;

public class RouteResultViewHandlerAll implements RouteResultViewHandler
{
    private final InternalEventRouter internalEventRouter;
    private final EPStatementHandle epStatementHandle;
    private final ResultSetProcessor[] processors;
    private final ExprNode[] whereClauses;
    private final EventBean[] eventsPerStream = new EventBean[1];

    public RouteResultViewHandlerAll(EPStatementHandle epStatementHandle, InternalEventRouter internalEventRouter, ResultSetProcessor[] processors, ExprNode[] whereClauses)
    {
        this.internalEventRouter = internalEventRouter;
        this.epStatementHandle = epStatementHandle;
        this.processors = processors;
        this.whereClauses = whereClauses;
    }

    public boolean handle(EventBean event)
    {
        eventsPerStream[0] = event;
        boolean isHandled = false;

        for (int i = 0; i < whereClauses.length; i++)
        {
            Boolean pass = true;
            if (whereClauses[i] != null)
            {
                Boolean passEvent = (Boolean) whereClauses[i].evaluate(eventsPerStream, true);
                if ((passEvent == null) || (!passEvent))
                {
                    pass = false;
                }
            }

            if (pass)
            {
                UniformPair<EventBean[]> result = processors[i].processViewResult(eventsPerStream, null, false);
                if ((result != null) && (result.getFirst() != null) && (result.getFirst().length > 0))
                {
                    isHandled = true;
                    internalEventRouter.route(result.getFirst()[0], epStatementHandle);
                }
            }
        }

        return isHandled;
    }
}
