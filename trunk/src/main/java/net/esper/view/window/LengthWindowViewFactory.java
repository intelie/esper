package net.esper.view.window;

import net.esper.view.ViewParameterException;
import net.esper.view.ViewAttachException;
import net.esper.view.*;
import net.esper.event.EventType;
import net.esper.util.JavaClassHelper;
import net.esper.eql.core.ViewResourceCallback;
import net.esper.collection.RandomAccessIStreamImpl;

import java.util.List;
import java.util.LinkedList;

public class LengthWindowViewFactory implements ViewFactory
{
    private int size;
    private boolean isRequiresRandomAccess;
    private EventType eventType;
    private List<ViewResourceCallback> factoryCallbacks = new LinkedList<ViewResourceCallback>();

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

    public boolean canProvideCapability(ViewCapability viewCapability)
    {
        if (viewCapability instanceof ViewCapDataWindowAccess)
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
        isRequiresRandomAccess = true;
        factoryCallbacks.add(resourceCallback);
    }

    public View makeView(ViewServiceContext viewServiceContext)
    {
        RandomAccessIStreamImpl randomAccess = null;

        if (isRequiresRandomAccess)
        {
            randomAccess = new RandomAccessIStreamImpl();
            for (ViewResourceCallback resourceCallback : factoryCallbacks)
            {
                resourceCallback.setViewResource(randomAccess);
            }
        }

        return new LengthWindowView(size, randomAccess);
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
