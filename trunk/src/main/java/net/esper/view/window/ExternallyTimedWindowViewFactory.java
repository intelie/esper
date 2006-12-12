package net.esper.view.window;

import net.esper.view.*;
import net.esper.event.EventType;
import net.esper.eql.parse.TimePeriodParameter;
import net.esper.eql.core.ViewFactoryCallback;
import net.esper.util.JavaClassHelper;
import net.esper.collection.RandomAccessIStreamImpl;

import java.util.List;
import java.util.LinkedList;

public class ExternallyTimedWindowViewFactory implements ViewFactory
{
    private String timestampFieldName;
    private long millisecondsBeforeExpiry;
    private EventType eventType;
    private boolean isRequiresRandomAccess;
    private List<ViewFactoryCallback> factoryCallbacks = new LinkedList<ViewFactoryCallback>();

    public void setViewParameters(List<Object> viewParameters) throws ViewParameterException
    {
        String errorMessage = "Externally-timed window view requires a timestamp field name and a numeric or time period parameter";
        if (viewParameters.size() != 2)
        {
            throw new ViewParameterException(errorMessage);
        }

        if (!(viewParameters.get(0) instanceof String))
        {
            throw new ViewParameterException(errorMessage);
        }
        timestampFieldName = (String) viewParameters.get(0);

        Object parameter = viewParameters.get(1);
        if (parameter instanceof TimePeriodParameter)
        {
            TimePeriodParameter param = (TimePeriodParameter) parameter;
            millisecondsBeforeExpiry = Math.round(1000d * param.getNumSeconds());
        }
        else if (!(parameter instanceof Number))
        {
            throw new ViewParameterException(errorMessage);
        }
        else
        {
            Number param = (Number) parameter;
            if (JavaClassHelper.isFloatingPointNumber(param))
            {
                millisecondsBeforeExpiry = Math.round(1000d * param.doubleValue());
            }
            else
            {
                millisecondsBeforeExpiry = 1000 * param.longValue();
            }
        }
    }

    public void attach(EventType parentEventType, ViewServiceContext viewServiceContext, ViewFactory optionalParentFactory) throws ViewAttachException
    {
        String message = PropertyCheckHelper.checkLong(parentEventType, timestampFieldName);
        if (message != null)
        {
            throw new ViewAttachException(message);
        }
        this.eventType = parentEventType;
    }

    public boolean canProvideCapability(ViewCapability viewCapability)
    {
        if (viewCapability instanceof ViewCapabilityRandomAccess)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void setProvideCapability(ViewCapability viewCapability, ViewFactoryCallback factoryCallback)
    {
        if (!canProvideCapability(viewCapability))
        {
            throw new UnsupportedOperationException("View capability " + viewCapability.getClass().getSimpleName() + " not supported");
        }
        isRequiresRandomAccess = true;
        factoryCallbacks.add(factoryCallback);
    }

    public View makeView(ViewServiceContext viewServiceContext)
    {
        RandomAccessIStreamImpl randomAccess = null;

        if (isRequiresRandomAccess)
        {
            randomAccess = new RandomAccessIStreamImpl();
            for (ViewFactoryCallback factoryCallback : factoryCallbacks)
            {
                factoryCallback.setViewResource(randomAccess);
            }
        }

        return new ExternallyTimedWindowView(timestampFieldName, millisecondsBeforeExpiry, randomAccess);
    }

    public EventType getEventType()
    {
        return eventType;
    }

    public boolean canReuse(View view)
    {
        if (!(view instanceof ExternallyTimedWindowView))
        {
            return false;
        }

        ExternallyTimedWindowView myView = (ExternallyTimedWindowView) view;
        if ((myView.getMillisecondsBeforeExpiry() != millisecondsBeforeExpiry) ||
            (!myView.getTimestampFieldName().equals(timestampFieldName)))
        {
            return false;
        }
        return myView.isEmpty();
    }
}
