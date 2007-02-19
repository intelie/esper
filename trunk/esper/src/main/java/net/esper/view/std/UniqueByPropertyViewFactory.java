package net.esper.view.std;

import net.esper.view.ViewFactory;
import net.esper.view.ViewParameterException;
import net.esper.view.ViewAttachException;
import net.esper.view.*;
import net.esper.event.EventType;
import net.esper.eql.core.ViewResourceCallback;

import java.util.List;

/**
 * Factory for {@link UniqueByPropertyView} instances. 
 */
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

    public void attach(EventType parentEventType, ViewServiceContext viewServiceContext, ViewFactory optionalParentFactory, List<ViewFactory> parentViewFactories) throws ViewAttachException
    {
        // Attaches to just about anything as long as the field exists
        String message = PropertyCheckHelper.exists(parentEventType, propertyName);
        if (message != null)
        {
            throw new ViewAttachException(message);
        }
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
