package com.espertech.esper.regression.client;

import com.espertech.esper.view.*;
import com.espertech.esper.client.EventType;
import com.espertech.esper.core.StatementContext;
import com.espertech.esper.epl.expression.ExprNode;

import java.util.List;

public class MyFlushedSimpleViewFactory extends ViewFactorySupport
{
    private EventType eventType;

    public void setViewParameters(ViewFactoryContext viewFactoryContext, List<ExprNode> viewParameters) throws ViewParameterException
    {
    }

    public void attach(EventType parentEventType, StatementContext statementContext, ViewFactory optionalParentFactory, List<ViewFactory> parentViewFactories) throws ViewParameterException
    {
        eventType = parentEventType;
    }

    public View makeView(StatementContext statementContext)
    {
        return new MyFlushedSimpleView(statementContext);
    }

    public EventType getEventType()
    {
        return eventType;
    }
}
