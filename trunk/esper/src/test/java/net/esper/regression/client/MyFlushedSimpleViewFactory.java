package net.esper.regression.client;

import net.esper.view.*;
import net.esper.event.EventType;
import net.esper.core.StatementContext;

import java.util.List;

public class MyFlushedSimpleViewFactory extends ViewFactorySupport
{
    private EventType eventType;

    public void setViewParameters(ViewFactoryContext viewFactoryContext, List<Object> viewParameters) throws ViewParameterException
    {
    }

    public void attach(EventType parentEventType, StatementContext statementContext, ViewFactory optionalParentFactory, List<ViewFactory> parentViewFactories) throws ViewAttachException
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
