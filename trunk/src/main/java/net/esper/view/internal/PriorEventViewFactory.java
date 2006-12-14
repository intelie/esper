package net.esper.view.internal;

import net.esper.event.EventType;
import net.esper.eql.core.ViewResourceCallback;
import net.esper.view.*;

import java.util.List;
import java.util.LinkedList;

public class PriorEventViewFactory implements ViewFactory
{
    private int maxPriorEventSize = -1;
    private EventType eventType;
    private List<ViewResourceCallback> factoryCallbacks = new LinkedList<ViewResourceCallback>();

    public void setViewParameters(List<Object> viewParameters) throws ViewParameterException
    {
        throw new UnsupportedOperationException("View not available through EQL");
    }

    public void attach(EventType parentEventType, ViewServiceContext viewServiceContext, ViewFactory optionalParentFactory) throws ViewAttachException
    {
        eventType = parentEventType;
    }

    public boolean canProvideCapability(ViewCapability viewCapability)
    {
        if (viewCapability instanceof ViewCapPriorEventAccess)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void setProvideCapability(ViewCapability viewCapability, ViewResourceCallback resourceCallback)
    {
        if (!canProvideCapability(viewCapability))
        {
            throw new UnsupportedOperationException("View capability " + viewCapability.getClass().getSimpleName() + " not supported");
        }
        factoryCallbacks.add(resourceCallback);

        ViewCapPriorEventAccess requested = (ViewCapPriorEventAccess) viewCapability;
        if (requested.getIndexConstant() > maxPriorEventSize)
        {
            maxPriorEventSize = requested.getIndexConstant();
        }
    }

    public View makeView(ViewServiceContext viewServiceContext)
    {
        return new PriorEventView(maxPriorEventSize);
    }

    public EventType getEventType()
    {
        return eventType;
    }

    public boolean canReuse(View view)
    {
        return false;
    }
}
