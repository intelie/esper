package net.esper.view.ext;

import net.esper.core.StatementContext;
import net.esper.eql.core.ViewResourceCallback;
import net.esper.eql.named.RemoveStreamViewCapability;
import net.esper.event.EventType;
import net.esper.type.TimePeriodParameter;
import net.esper.util.JavaClassHelper;
import net.esper.view.*;
import net.esper.view.window.RandomAccessByIndexGetter;

import java.util.List;

/**
 * Factory for views for time-ordering events.
 */
public class TimeOrderViewFactory implements DataWindowViewFactory
{
    /**
     * The timestamp field name.
     */
    protected String timestampFieldName;

    /**
     * The interval to wait for newer events to arrive.
     */
    protected long intervalSize;

    /**
     * The access into the collection for use with 'previous'.
     */
    protected RandomAccessByIndexGetter randomAccessGetterImpl;

    /**
     * Indicates if the view must handle the remove stream of parent views.
     */
    protected boolean isRemoveStreamHandling;

    private EventType eventType;

    public void setViewParameters(ViewFactoryContext viewFactoryContext, List<Object> viewParameters) throws ViewParameterException
    {
        String errorMessage = "Time order view requires the property name supplying timestamp values, and a numeric or time period parameter for interval size";
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
            intervalSize = Math.round(1000d * param.getNumSeconds());
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
                intervalSize = Math.round(1000d * param.doubleValue());
            }
            else
            {
                intervalSize = 1000 * param.longValue();
            }
        }

        if (intervalSize < 100)
        {
            throw new ViewParameterException("Time order view requires a size of at least 100 msec");
        }       
    }

    public void attach(EventType parentEventType, StatementContext statementContext, ViewFactory optionalParentFactory, List<ViewFactory> parentViewFactories) throws ViewAttachException
    {
        // Attaches to parent views where the sort fields exist and implement Comparable
        String result = PropertyCheckHelper.exists(parentEventType, timestampFieldName);

        if(result != null)
        {
            throw new ViewAttachException(result);
        }

        eventType = parentEventType;
    }

    public boolean canProvideCapability(ViewCapability viewCapability)
    {
        if (viewCapability instanceof ViewCapDataWindowAccess)
        {
            return true;
        }
        else if (viewCapability instanceof RemoveStreamViewCapability)
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
        if (viewCapability instanceof RemoveStreamViewCapability)
        {
            isRemoveStreamHandling = true;
            return;
        }
        if (randomAccessGetterImpl == null)
        {
            randomAccessGetterImpl = new RandomAccessByIndexGetter();
        }
        resourceCallback.setViewResource(randomAccessGetterImpl);
    }

    public View makeView(StatementContext statementContext)
    {
        IStreamTimeOrderRandomAccess sortedRandomAccess = null;

        if (randomAccessGetterImpl != null)
        {
            sortedRandomAccess = new IStreamTimeOrderRandomAccess(randomAccessGetterImpl);
            randomAccessGetterImpl.updated(sortedRandomAccess);
        }

        return new TimeOrderView(statementContext, this, timestampFieldName, intervalSize, sortedRandomAccess, isRemoveStreamHandling);
    }

    public EventType getEventType()
    {
        return eventType;
    }

    public boolean canReuse(View view)
    {
        if (randomAccessGetterImpl != null)
        {
            return false;
        }

        if (!(view instanceof TimeOrderView))
        {
            return false;
        }

        TimeOrderView other = (TimeOrderView) view;
        if ((other.getIntervalSize() != intervalSize) ||
            (!other.getTimestampFieldName().equals(timestampFieldName)))
        {
            return false;
        }

        return other.isEmpty();
    }
}
