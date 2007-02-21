package net.esper.regression.client;

import net.esper.view.*;
import net.esper.view.stat.UnivariateStatisticsView;
import net.esper.event.EventType;
import net.esper.eql.core.ViewResourceCallback;

import java.util.List;

public class MyTrendSpotterViewFactory implements ViewFactory
{
    private String fieldName;
    private EventType eventType;

    public void setViewParameters(List<Object> viewParameters) throws ViewParameterException
    {
        String errorMessage = "'Trend spotter' view require a single field name as a parameter";
        if (viewParameters.size() != 1)
        {
            throw new ViewParameterException(errorMessage);
        }

        if (!(viewParameters.get(0) instanceof String))
        {
            throw new ViewParameterException(errorMessage);
        }

        fieldName = (String) viewParameters.get(0);
    }

    public void attach(EventType parentEventType, ViewServiceContext viewServiceContext, ViewFactory optionalParentFactory, List<ViewFactory> parentViewFactories) throws ViewAttachException
    {
        String result = PropertyCheckHelper.checkNumeric(parentEventType, fieldName);
        if (result != null)
        {
            throw new ViewAttachException(result);
        }
        eventType = MyTrendSpotterView.createEventType(viewServiceContext);
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
        return new MyTrendSpotterView(viewServiceContext, fieldName);
    }

    public EventType getEventType()
    {
        return eventType;
    }

    public boolean canReuse(View view)
    {
        if (!(view instanceof MyTrendSpotterView))
        {
            return false;
        }

        MyTrendSpotterView other = (MyTrendSpotterView) view;
        if (!other.getFieldName().equals(fieldName))
        {
            return false;
        }

        return true;
    }
}
