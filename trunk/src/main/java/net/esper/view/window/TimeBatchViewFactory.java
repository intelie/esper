package net.esper.view.window;

import net.esper.view.factory.ViewFactory;
import net.esper.view.factory.ViewParameterException;
import net.esper.view.factory.ViewAttachException;
import net.esper.view.ViewServiceContext;
import net.esper.view.View;
import net.esper.event.EventType;
import net.esper.eql.parse.TimePeriodParameter;
import net.esper.util.JavaClassHelper;

import java.util.List;

public class TimeBatchViewFactory implements ViewFactory
{
    private long millisecondsBeforeExpiry;
    private Long optionalReferencePoint;
    private EventType eventType;

    public void setViewParameters(List<Object> viewParameters) throws ViewParameterException
    {
        String errorMessage = "Time batch view requires a single numeric or time period parameter, and an optional long-typed reference point in msec";
        if ((viewParameters.size() < 1) || (viewParameters.size() > 2))
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
            throw new ViewParameterException("Time batch view requires a size of at least 100 msec");
        }

        if (viewParameters.size() == 2)
        {
            Object paramRef = viewParameters.get(1);
            if ((!(paramRef instanceof Number)) || (JavaClassHelper.isFloatingPointNumber((Number)paramRef))) 
            {
                throw new ViewParameterException("Time batch view requires a Long-typed reference point in msec as a second parameter");
            }
            optionalReferencePoint = ((Number) paramRef).longValue();
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
        return new TimeBatchView(millisecondsBeforeExpiry, optionalReferencePoint);
    }

    public EventType getEventType()
    {
        return eventType;
    }

    public boolean canReuse(View view)
    {
        if (!(view instanceof TimeBatchView))
        {
            return false;
        }

        TimeBatchView myView = (TimeBatchView) view;
        if (myView.getMsecIntervalSize() != millisecondsBeforeExpiry)
        {
            return false;
        }

        if ((myView.getInitialReferencePoint() != null) && (optionalReferencePoint != null))
        {
            if (myView.getInitialReferencePoint() != optionalReferencePoint)
            {
                return false;
            }
        }
        if ( ((myView.getInitialReferencePoint() == null) && (optionalReferencePoint != null)) ||
             ((myView.getInitialReferencePoint() != null) && (optionalReferencePoint == null)) )
        {
            return false;
        }

        return myView.isEmpty();
    }
}
