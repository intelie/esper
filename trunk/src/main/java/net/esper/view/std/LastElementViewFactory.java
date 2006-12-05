package net.esper.view.std;

import net.esper.view.factory.ViewFactory;
import net.esper.view.factory.ViewParameterException;
import net.esper.view.factory.ViewAttachException;
import net.esper.view.ViewServiceContext;
import net.esper.view.View;
import net.esper.event.EventType;

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

    public boolean canProvideCapability(Class capabilityInterfaceClass)
    {
        return false;
    }

    public void setProvideCapability(Class capabilityInterfaceClass)
    {
        throw new UnsupportedOperationException("View capability " + capabilityInterfaceClass.getSimpleName() + " not supported");
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
