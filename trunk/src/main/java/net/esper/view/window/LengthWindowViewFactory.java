package net.esper.view.window;

import net.esper.view.factory.ViewFactory;
import net.esper.view.factory.ViewParameterException;
import net.esper.view.factory.ViewAttachException;
import net.esper.view.ViewServiceContext;
import net.esper.view.View;
import net.esper.event.EventType;
import net.esper.util.JavaClassHelper;

import java.util.List;

public class LengthWindowViewFactory implements ViewFactory
{
    private int size;
    private EventType eventType;

    public void setViewParameters(List<Object> viewParameters) throws ViewParameterException
    {
        String errorMessage = "Length window view requires a single integer-type parameter";
        if (viewParameters.size() != 1)
        {
            throw new ViewParameterException(errorMessage);
        }

        Object parameter = viewParameters.get(0);
        if (!(parameter instanceof Number))
        {
            throw new ViewParameterException(errorMessage);
        }
        Number numParam = (Number) parameter;
        if ( (JavaClassHelper.isFloatingPointNumber(numParam)) ||
             (numParam instanceof Long))
        {
            throw new ViewParameterException(errorMessage);
        }

        size =  numParam.intValue();
        if (size <= 0)
        {
            throw new ViewParameterException("Length window requires a positive number");
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
        return new LengthWindowView(size);
    }

    public EventType getEventType()
    {
        return eventType;
    }

    public boolean canReuse(View view)
    {
        if (!(view instanceof LengthWindowView))
        {
            return false;
        }

        LengthWindowView myView = (LengthWindowView) view;
        if (myView.getSize() != size)
        {
            return false;
        }
        return myView.isEmpty();
    }
}
