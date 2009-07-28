package com.espertech.esper.support.core;

import com.espertech.esper.core.InternalEventRouter;
import com.espertech.esper.core.EPStatementHandle;
import com.espertech.esper.core.InternalRoutePreprocessView;
import com.espertech.esper.core.InternalEventRouteDest;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.spec.UpdateDesc;
import com.espertech.esper.epl.expression.ExprValidationException;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;

import java.util.List;
import java.util.LinkedList;
import java.lang.annotation.Annotation;

public class SupportInternalEventRouter implements InternalEventRouter
{
    private List<EventBean> routed  = new LinkedList<EventBean>();

    public void route(EventBean event, EPStatementHandle statementHandle, InternalEventRouteDest routeDest, ExprEvaluatorContext exprEvaluatorContext)
    {
        routed.add(event);
    }

    public List<EventBean> getRouted()
    {
        return routed;
    }

    public void reset()
    {
        routed.clear();
    }

    public void addPreprocessing(EventType eventType, UpdateDesc desc, Annotation[] annotations, InternalRoutePreprocessView outputView) throws ExprValidationException {
    }

    public void removePreprocessing(EventType eventType, UpdateDesc desc)
    {
    }

    public void route(EventBean event, EPStatementHandle statementHandle, InternalEventRouteDest routeDest)
    {
    }
}
