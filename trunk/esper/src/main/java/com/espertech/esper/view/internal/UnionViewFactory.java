package com.espertech.esper.view.internal;

import com.espertech.esper.view.*;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.epl.core.ViewResourceCallback;
import com.espertech.esper.event.EventType;
import com.espertech.esper.core.StatementContext;

import java.util.List;
import java.util.ArrayList;

public class UnionViewFactory implements ViewFactory
{
    private final EventType parentEventType;
    private final List<ViewFactory> viewFactories;

    public UnionViewFactory(EventType parentEventType, List<ViewFactory> viewFactories)
    {
        this.parentEventType = parentEventType;
        this.viewFactories = new ArrayList(viewFactories);
    }

    public void setViewParameters(ViewFactoryContext viewFactoryContext, List<ExprNode> viewParameters) throws ViewParameterException
    {
    }

    public void attach(EventType parentEventType, StatementContext statementContext, ViewFactory optionalParentFactory, List<ViewFactory> parentViewFactories) throws ViewParameterException
    {
    }

    public boolean canProvideCapability(ViewCapability viewCapability)
    {
        return false;
    }

    public void setProvideCapability(ViewCapability viewCapability, ViewResourceCallback resourceCallback)
    {
    }

    public View makeView(StatementContext statementContext)
    {
        List<View> views = new ArrayList<View>();
        for (ViewFactory viewFactory : viewFactories)
        {
            views.add(viewFactory.makeView(statementContext));
        }
        return new UnionView(parentEventType, views);
    }

    public EventType getEventType()
    {
        return parentEventType;
    }

    public boolean canReuse(View view)
    {
        return false;
    }
}
