package com.espertech.esper.view.internal;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.core.InternalEventRouter;
import com.espertech.esper.core.EPStatementHandle;
import com.espertech.esper.collection.UniformPair;
import com.espertech.esper.epl.core.ResultSetProcessor;
import com.espertech.esper.epl.expression.ExprNode;

public class RouteResultViewHandlerFirst implements RouteResultViewHandler
{
    private final InternalEventRouter internalEventRouter;
    private final EPStatementHandle epStatementHandle;
    private final ResultSetProcessor[] processors;
    private final ExprNode[] whereClauses;
    private final EventBean[] eventsPerStream = new EventBean[1];

    public RouteResultViewHandlerFirst(EPStatementHandle epStatementHandle, InternalEventRouter internalEventRouter, ResultSetProcessor[] processors, ExprNode[] whereClauses)
    {
        this.internalEventRouter = internalEventRouter;
        this.epStatementHandle = epStatementHandle;
        this.processors = processors;
        this.whereClauses = whereClauses;
    }

    public boolean handle(EventBean event)
    {
        int index = -1;
        eventsPerStream[0] = event;

        for (int i = 0; i < whereClauses.length; i++)
        {
            if (whereClauses[i] == null)
            {
                index = i;
                break;
            }

            Boolean pass = (Boolean) whereClauses[i].evaluate(eventsPerStream, true);
            if ((pass != null) && (pass))
            {
                index = i;
                break;
            }
        }

        if (index != -1)
        {
            UniformPair<EventBean[]> result = processors[index].processViewResult(eventsPerStream, null, false);
            if ((result != null) && (result.getFirst() != null) && (result.getFirst().length > 0))
            {
                internalEventRouter.route(result.getFirst()[0], epStatementHandle);
            }
        }
        
        return index != -1;
    }
}
