package com.espertech.esper.regression.client;

import com.espertech.esper.event.EventType;
import com.espertech.esper.view.*;
import com.espertech.esper.core.StatementContext;

import java.util.List;

public class MyTrendSpotterViewFactory extends ViewFactorySupport
{
    private String fieldName;
    private EventType eventType;

    public void setViewParameters(ViewFactoryContext viewFactoryContext, List<Object> viewParameters) throws ViewParameterException
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

    public void attach(EventType parentEventType, StatementContext statementContext, ViewFactory optionalParentFactory, List<ViewFactory> parentViewFactories) throws ViewAttachException
    {
        String result = PropertyCheckHelper.checkNumeric(parentEventType, fieldName);
        if (result != null)
        {
            throw new ViewAttachException(result);
        }
        eventType = MyTrendSpotterView.createEventType(statementContext);
    }

    public View makeView(StatementContext statementContext)
    {
        return new MyTrendSpotterView(statementContext, fieldName);
    }

    public EventType getEventType()
    {
        return eventType;
    }
}
