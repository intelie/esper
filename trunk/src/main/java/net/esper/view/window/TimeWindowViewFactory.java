package net.esper.view.window;

import net.esper.view.*;
import net.esper.eql.parse.TimePeriodParameter;
import net.esper.eql.core.ViewResourceCallback;
import net.esper.event.EventType;
import net.esper.util.JavaClassHelper;

import java.util.List;
import java.util.LinkedList;

public class TimeWindowViewFactory implements ViewFactory
{
    private long millisecondsBeforeExpiry;
    private boolean isRequiresRandomAccess;

    private EventType eventType;
    private List<ViewResourceCallback> resourceCallbacks = new LinkedList<ViewResourceCallback>();

    public void setViewParameters(List<Object> viewParameters) throws ViewParameterException
    {
        String errorMessage = "Time window view requires a single numeric or time period parameter";
        if (viewParameters.size() != 1)
        {
            throw new ViewParameterException(errorMessage);
        }

        Object parameter = viewParameters.get(0);
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

        if (millisecondsBeforeExpiry < 100)
        {
            throw new ViewParameterException("Time window view requires a size of at least 100 msec");
        }
    }

    public void attach(EventType parentEventType, ViewServiceContext viewServiceContext, ViewFactory optionalParentFactory, List<ViewFactory> parentViewFactories) throws ViewAttachException
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
        resourceCallbacks.add(resourceCallback);
    }

    public View makeView(ViewServiceContext viewServiceContext)
    {
        IStreamRandomAccess randomAccess = null;

        if (isRequiresRandomAccess)
        {
            randomAccess = new IStreamRandomAccess();
            for (ViewResourceCallback resourceCallback : resourceCallbacks)
            {
                resourceCallback.setViewResource(randomAccess);
            }
        }
        
        return new TimeWindowView(millisecondsBeforeExpiry, randomAccess);
    }

    public EventType getEventType()
    {
        return eventType;
    }

    public boolean canReuse(View view)
    {
        if (!(view instanceof TimeWindowView))
        {
            return false;
        }

        TimeWindowView myView = (TimeWindowView) view;
        if (myView.getMillisecondsBeforeExpiry() != millisecondsBeforeExpiry)
        {
            return false;
        }

        // For reuse of the time window it doesn't matter if it provides random access or not
        return myView.isEmpty();
    }
}
