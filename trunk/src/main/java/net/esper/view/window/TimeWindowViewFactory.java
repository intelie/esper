package net.esper.view.window;

import net.esper.view.ViewFactory;
import net.esper.view.ViewParameterException;
import net.esper.view.ViewAttachException;
import net.esper.view.ViewServiceContext;
import net.esper.view.View;
import net.esper.eql.parse.TimePeriodParameter;
import net.esper.eql.core.ViewFactoryCallback;
import net.esper.event.EventType;
import net.esper.util.JavaClassHelper;
import net.esper.collection.DataWindowRandomAccess;
import net.esper.collection.DataWindowRandomAccessImpl;

import java.util.List;
import java.util.LinkedList;

public class TimeWindowViewFactory implements ViewFactory
{
    private long millisecondsBeforeExpiry;
    private boolean isRequiresRandomAccess;

    private EventType eventType;
    private List<ViewFactoryCallback> factoryCallbacks = new LinkedList<ViewFactoryCallback>();

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

    public void attach(EventType parentEventType, ViewServiceContext viewServiceContext, ViewFactory optionalParentFactory) throws ViewAttachException
    {
        this.eventType = parentEventType;
    }

    public boolean canProvideCapability(Class capabilityInterfaceClass)
    {
        if (capabilityInterfaceClass == DataWindowRandomAccess.class)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void setProvideCapability(Class capabilityInterfaceClass, ViewFactoryCallback factoryCallback)
    {
        if (!canProvideCapability(capabilityInterfaceClass))
        {
            throw new UnsupportedOperationException("View capability " + capabilityInterfaceClass.getSimpleName() + " not supported");
        }
        isRequiresRandomAccess = true;
        factoryCallbacks.add(factoryCallback);
    }

    public View makeView(ViewServiceContext viewServiceContext)
    {
        DataWindowRandomAccessImpl randomAccess = null;

        if (isRequiresRandomAccess)
        {
            randomAccess = new DataWindowRandomAccessImpl();
            for (ViewFactoryCallback factoryCallback : factoryCallbacks)
            {
                factoryCallback.setViewResource(randomAccess);
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

        return myView.isEmpty();
    }
}
