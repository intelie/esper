package net.esper.view.window;

import net.esper.view.*;
import net.esper.event.EventType;
import net.esper.type.TimePeriodParameter;
import net.esper.util.JavaClassHelper;
import net.esper.core.StatementContext;
import net.esper.eql.core.ViewResourceCallback;

import java.util.List;

/**
 * Factory for {@link net.esper.view.window.TimeAccumView}.
 */
public class TimeAccumViewFactory implements ViewFactory
{
    private EventType eventType;

    /**
     * Number of msec of quiet time before results are flushed.
     */
    protected long millisecondsQuietTime;

    /**
     * Access into the data window.
     */
    protected RandomAccessByIndexGetter randomAccessGetterImpl;

    public void setViewParameters(ViewFactoryContext viewFactoryContext, List<Object> viewParameters) throws ViewParameterException
    {
        String errorMessage = "Time accumulative batch view requires a single numeric parameter or time period parameter";
        if (viewParameters.size() != 1)
        {
            throw new ViewParameterException(errorMessage);
        }

        Object parameter = viewParameters.get(0);
        if (parameter instanceof TimePeriodParameter)
        {
            TimePeriodParameter param = (TimePeriodParameter) parameter;
            millisecondsQuietTime = Math.round(1000d * param.getNumSeconds());
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
                millisecondsQuietTime = Math.round(1000d * param.doubleValue());
            }
            else
            {
                millisecondsQuietTime = 1000 * param.longValue();
            }
        }

        if (millisecondsQuietTime < 100)
        {
            throw new ViewParameterException("Time accumulative batch view requires a size of at least 100 msec");
        }
    }

    public void attach(EventType parentEventType, StatementContext statementContext, ViewFactory optionalParentFactory, List<ViewFactory> parentViewFactories) throws ViewAttachException
    {
        this.eventType = parentEventType;
    }

    public boolean canProvideCapability(ViewCapability viewCapability)
    {
        return viewCapability instanceof ViewCapDataWindowAccess;
    }

    public void setProvideCapability(ViewCapability viewCapability, ViewResourceCallback resourceCallback)
    {
        if (!canProvideCapability(viewCapability))
        {
            throw new UnsupportedOperationException("View capability " + viewCapability.getClass().getSimpleName() + " not supported");
        }
        if (randomAccessGetterImpl == null)
        {
            randomAccessGetterImpl = new RandomAccessByIndexGetter();
        }
        resourceCallback.setViewResource(randomAccessGetterImpl);
    }

    public View makeView(StatementContext statementContext)
    {
        IStreamRandomAccess randomAccess = null;

        if (randomAccessGetterImpl != null)
        {
            randomAccess = new IStreamRandomAccess(randomAccessGetterImpl);
            randomAccessGetterImpl.updated(randomAccess);
        }

        return new TimeAccumView(this, statementContext, millisecondsQuietTime, randomAccess);
    }

    public EventType getEventType()
    {
        return eventType;
    }

    public boolean canReuse(View view)
    {
        if (!(view instanceof TimeAccumView))
        {
            return false;
        }

        TimeAccumView myView = (TimeAccumView) view;
        if (myView.getMsecIntervalSize() != millisecondsQuietTime)
        {
            return false;
        }

        return myView.isEmpty();
    }
}
