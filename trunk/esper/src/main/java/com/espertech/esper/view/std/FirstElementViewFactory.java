package com.espertech.esper.view.std;

import com.espertech.esper.view.*;
import com.espertech.esper.event.EventType;
import com.espertech.esper.epl.core.ViewResourceCallback;
import com.espertech.esper.epl.named.RemoveStreamViewCapability;
import com.espertech.esper.core.StatementContext;

import java.util.List;

/**
 * Factory for {@link com.espertech.esper.view.std.FirstElementView} instances.
 */
public class FirstElementViewFactory implements DataWindowViewFactory
{
    private EventType eventType;

    public void setViewParameters(ViewFactoryContext viewFactoryContext, List<Object> viewParameters) throws ViewParameterException
    {
        String errorMessage = "'First element' view does not take any parameters";
        if (!viewParameters.isEmpty())
        {
            throw new ViewParameterException(errorMessage);
        }
    }

    public void attach(EventType parentEventType, StatementContext statementContext, ViewFactory optionalParentFactory, List<ViewFactory> parentViewFactories) throws ViewAttachException
    {
        this.eventType = parentEventType;
    }

    public boolean canProvideCapability(ViewCapability viewCapability)
    {
        if (viewCapability instanceof RemoveStreamViewCapability)
        {
            return true;
        }
        return false;
    }

    public void setProvideCapability(ViewCapability viewCapability, ViewResourceCallback resourceCallback)
    {
        if (viewCapability instanceof RemoveStreamViewCapability)
        {
            return;
        }
        throw new UnsupportedOperationException("View capability " + viewCapability.getClass().getSimpleName() + " not supported");
    }

    public View makeView(StatementContext statementContext)
    {
        return new FirstElementView();
    }

    public EventType getEventType()
    {
        return eventType;
    }

    public boolean canReuse(View view)
    {
        if (!(view instanceof FirstElementView))
        {
            return false;
        }
        return true;
    }
}
