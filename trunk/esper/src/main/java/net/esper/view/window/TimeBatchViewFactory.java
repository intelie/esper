package net.esper.view.window;

import net.esper.eql.core.ViewResourceCallback;
import net.esper.eql.named.RemoveStreamViewCapability;
import net.esper.type.TimePeriodParameter;
import net.esper.event.EventType;
import net.esper.util.JavaClassHelper;
import net.esper.view.*;
import net.esper.core.StatementContext;

import java.util.List;

/**
 * Factory for {@link TimeBatchView}. 
 */
public class TimeBatchViewFactory implements ViewFactory
{
    private EventType eventType;

    /**
     * Number of msec before expiry.
     */
    protected long millisecondsBeforeExpiry;

    /**
     * The reference point, or null if none supplied.
     */
    protected Long optionalReferencePoint;

    /**
     * The access into the data window.
     */
    protected RelativeAccessByEventNIndexGetter relativeAccessGetterImpl;

    protected boolean isRemoveStreamHandling;

    public void setViewParameters(ViewFactoryContext viewFactoryContext, List<Object> viewParameters) throws ViewParameterException
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

    public void attach(EventType parentEventType, StatementContext statementContext, ViewFactory optionalParentFactory, List<ViewFactory> parentViewFactories) throws ViewAttachException
    {
        this.eventType = parentEventType;
    }

    public boolean canProvideCapability(ViewCapability viewCapability)
    {
        if (viewCapability instanceof RemoveStreamViewCapability)
        {
            return true;
        }
        return viewCapability instanceof ViewCapDataWindowAccess;
    }

    public void setProvideCapability(ViewCapability viewCapability, ViewResourceCallback resourceCallback)
    {
        if (!canProvideCapability(viewCapability))
        {
            throw new UnsupportedOperationException("View capability " + viewCapability.getClass().getSimpleName() + " not supported");
        }
        if (viewCapability instanceof RemoveStreamViewCapability)
        {
            isRemoveStreamHandling = true;
            return;
        }
        if (relativeAccessGetterImpl == null)
        {
            relativeAccessGetterImpl = new RelativeAccessByEventNIndexGetter();            
        }
        resourceCallback.setViewResource(relativeAccessGetterImpl);
    }

    public View makeView(StatementContext statementContext)
    {
        IStreamRelativeAccess relativeAccessByEvent = null;

        if (relativeAccessGetterImpl != null)
        {
            relativeAccessByEvent = new IStreamRelativeAccess(relativeAccessGetterImpl);
            relativeAccessGetterImpl.updated(relativeAccessByEvent, null);
        }

        if (isRemoveStreamHandling)
        {
            return new TimeBatchViewRStream(this, statementContext, millisecondsBeforeExpiry, optionalReferencePoint);            
        }
        else
        {
            return new TimeBatchView(this, statementContext, millisecondsBeforeExpiry, optionalReferencePoint, relativeAccessByEvent);
        }
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
            if (!myView.getInitialReferencePoint().equals(optionalReferencePoint.longValue()))
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
