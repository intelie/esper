package com.espertech.esper.view.internal;

import com.espertech.esper.view.*;
import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.epl.named.RemoveStreamViewCapability;
import com.espertech.esper.epl.core.ViewResourceCallback;
import com.espertech.esper.core.StatementContext;

import java.util.List;
import java.util.ArrayList;

/**
 * Factory for union-views.
 */
public class IntersectViewFactory implements ViewFactory, DataWindowViewFactory
{
    private final EventType parentEventType;
    private final List<ViewFactory> viewFactories;

    /**
     * Ctor.
     * @param parentEventType the event type
     * @param viewFactories the view factories
     */
    public IntersectViewFactory(EventType parentEventType, List<ViewFactory> viewFactories)
    {
        this.parentEventType = parentEventType;
        this.viewFactories = viewFactories;
    }

    public void setViewParameters(ViewFactoryContext viewFactoryContext, List<ExprNode> viewParameters) throws ViewParameterException
    {
    }

    public void attach(EventType parentEventType, StatementContext statementContext, ViewFactory optionalParentFactory, List<ViewFactory> parentViewFactories) throws ViewParameterException
    {
    }

    public boolean canProvideCapability(ViewCapability viewCapability)
    {
        for (ViewFactory viewFactory : viewFactories)
        {
            if (!viewFactory.canProvideCapability(viewCapability))
            {
                return false;
            }
        }
        return viewCapability instanceof RemoveStreamViewCapability;
    }

    public void setProvideCapability(ViewCapability viewCapability, ViewResourceCallback resourceCallback)
    {
        if (!canProvideCapability(viewCapability))
        {
            throw new UnsupportedOperationException("View capability " + viewCapability.getClass().getSimpleName() + " not supported");
        }

        if (viewCapability instanceof RemoveStreamViewCapability)
        {
            for (ViewFactory viewFactory : viewFactories)
            {
                viewFactory.setProvideCapability(viewCapability, resourceCallback);
            }
        }
    }

    public View makeView(StatementContext statementContext)
    {
        List<View> views = new ArrayList<View>();
        for (ViewFactory viewFactory : viewFactories)
        {
            viewFactory.setProvideCapability(new RemoveStreamViewCapability(true), null);   // require remove stream support for all views
            views.add(viewFactory.makeView(statementContext));
        }
        return new IntersectView(this, parentEventType, views);
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
