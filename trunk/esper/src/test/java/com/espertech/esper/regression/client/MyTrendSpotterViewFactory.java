package com.espertech.esper.regression.client;

import com.espertech.esper.event.EventType;
import com.espertech.esper.view.*;
import com.espertech.esper.core.StatementContext;
import com.espertech.esper.epl.expression.ExprNode;

import java.util.List;

public class MyTrendSpotterViewFactory extends ViewFactorySupport
{
    private String fieldName;
    private EventType eventType;

    public void setViewParameters(ViewFactoryContext viewFactoryContext, List<ExprNode> expressionParameters) throws ViewParameterException
    {
        List<Object> viewParameters = ViewFactorySupport.validateAndEvaluate("'Trend spotter' view", viewFactoryContext, expressionParameters);
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

    public void attach(EventType parentEventType, StatementContext statementContext, ViewFactory optionalParentFactory, List<ViewFactory> parentViewFactories) throws ViewParameterException
    {
        String result = PropertyCheckHelper.checkNumeric(parentEventType, fieldName);
        if (result != null)
        {
            throw new ViewParameterException(result);
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
