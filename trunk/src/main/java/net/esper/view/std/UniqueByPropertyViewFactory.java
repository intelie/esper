package net.esper.view.std;

import net.esper.view.factory.ViewFactory;
import net.esper.view.factory.ViewParameterException;
import net.esper.view.factory.ViewAttachException;
import net.esper.view.ViewServiceContext;
import net.esper.view.PropertyCheckHelper;
import net.esper.view.View;
import net.esper.event.EventType;

import java.util.List;

public class UniqueByPropertyViewFactory implements ViewFactory
{
    private String propertyName;
    private EventType eventType;

    public void setViewParameters(List<Object> viewParameters) throws ViewParameterException
    {
        String errorMessage = "'Unique' view requires a single string parameter";
        if (viewParameters.size() != 1)
        {
            throw new ViewParameterException(errorMessage);
        }

        Object parameter = viewParameters.get(0);
        if (!(parameter instanceof String))
        {
            throw new ViewParameterException(errorMessage);
        }
        propertyName = (String) parameter;
    }

    public void attach(EventType parentEventType, ViewServiceContext viewServiceContext, ViewFactory optionalParentFactory) throws ViewAttachException
    {
        // Attaches to just about anything as long as the field exists
        String message = PropertyCheckHelper.exists(parentEventType, propertyName);
        if (message != null)
        {
            throw new ViewAttachException(message);
        }
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
        return new UniqueByPropertyView(propertyName);
    }

    public EventType getEventType()
    {
        return eventType;
    }

    public boolean canReuse(View view)
    {
        if (!(view instanceof UniqueByPropertyView))
        {
            return false;
        }

        UniqueByPropertyView myView = (UniqueByPropertyView) view;
        if (!myView.getUniqueFieldName().equals(propertyName))
        {
            return false;
        }

        return true;
    }
}
