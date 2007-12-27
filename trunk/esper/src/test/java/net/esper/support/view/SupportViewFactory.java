package net.esper.support.view;

import net.esper.view.*;
import net.esper.event.EventType;
import net.esper.eql.core.ViewResourceCallback;
import net.esper.core.StatementContext;

import java.util.List;

public abstract class SupportViewFactory implements ViewFactory
{
    public void setViewParameters(ViewFactoryContext viewFactoryContext, List<Object> viewParameters) throws ViewParameterException
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void attach(EventType parentEventType, StatementContext statementContext, ViewFactory optionalParentFactory, List<ViewFactory> parentViewFactories) throws ViewAttachException
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean canProvideCapability(ViewCapability viewCapability)
    {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setProvideCapability(ViewCapability viewCapability, ViewResourceCallback resourceCallback)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public View makeView(StatementContext statementContext)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public EventType getEventType()
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean canReuse(View view)
    {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
