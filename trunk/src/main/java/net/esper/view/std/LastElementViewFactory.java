package net.esper.view.std;

import net.esper.view.*;
import net.esper.event.EventType;
import net.esper.eql.core.ViewResourceCallback;

import java.util.List;

public class LastElementViewFactory implements ViewFactory
{
    private EventType eventType;

    public void setViewParameters(List<Object> viewParameters) throws ViewParameterException
    {
        String errorMessage = "'Last element' view does not take any parameters";
        if (viewParameters.size() != 0)
        {
            throw new ViewParameterException(errorMessage);
        }
    }

    public void attach(EventType parentEventType, ViewServiceContext viewServiceContext, ViewFactory optionalParentFactory) throws ViewAttachException
    {
        this.eventType = parentEventType;
    }

    public boolean canProvideCapability(ViewCapability viewCapability)
    {
        return false;
    }

    public void setProvideCapability(ViewCapability viewCapability, ViewResourceCallback resourceCallback)
    {
        throw new UnsupportedOperationException("View capability " + viewCapability.getClass().getSimpleName() + " not supported");
    }

    public View makeView(ViewServiceContext viewServiceContext)
    {
        return new LastElementView();
    }

    public EventType getEventType()
    {
        return eventType;
    }

    public boolean canReuse(View view)
    {
        if (!(view instanceof LastElementView))
        {
            return false;
        }
        return true;
    }
}
