package com.espertech.esper.view.stat;

import com.espertech.esper.view.ViewFactory;
import com.espertech.esper.view.ViewParameterException;
import com.espertech.esper.view.ViewAttachException;
import com.espertech.esper.view.*;
import com.espertech.esper.event.EventType;
import com.espertech.esper.eql.core.ViewResourceCallback;
import com.espertech.esper.core.StatementContext;

import java.util.List;

/**
 * Factory for {@link WeightedAverageView} instances. 
 */
public class WeightedAverageViewFactory implements ViewFactory
{
    /**
     * Property name of X field.
     */
    protected String fieldNameX;
    /**
     * Property name of weight field.
     */
    protected String fieldNameWeight;
    
    private EventType eventType;

    public void setViewParameters(ViewFactoryContext viewFactoryContext, List<Object> viewParameters) throws ViewParameterException
    {
        String errorMessage = "'Weighted average' view requires two field names as parameters";
        if (viewParameters.size() != 2)
        {
            throw new ViewParameterException(errorMessage);
        }

        if ( (!(viewParameters.get(0) instanceof String)) ||
             (!(viewParameters.get(1) instanceof String)) )
        {
            throw new ViewParameterException(errorMessage);
        }

        fieldNameX = (String) viewParameters.get(0);
        fieldNameWeight = (String) viewParameters.get(1);
    }

    public void attach(EventType parentEventType, StatementContext statementContext, ViewFactory optionalParentFactory, List<ViewFactory> parentViewFactories) throws ViewAttachException
    {
        String result = PropertyCheckHelper.checkNumeric(parentEventType, fieldNameX, fieldNameWeight);
        if (result != null)
        {
            throw new ViewAttachException(result);
        }
        eventType = WeightedAverageView.createEventType(statementContext);
    }

    public boolean canProvideCapability(ViewCapability viewCapability)
    {
        return false;
    }

    public void setProvideCapability(ViewCapability viewCapability, ViewResourceCallback resourceCallback)
    {
        throw new UnsupportedOperationException("View capability " + viewCapability.getClass().getSimpleName() + " not supported");
    }

    public View makeView(StatementContext statementContext)
    {
        return new WeightedAverageView(statementContext, fieldNameX, fieldNameWeight);
    }

    public EventType getEventType()
    {
        return eventType;
    }

    public boolean canReuse(View view)
    {
        if (!(view instanceof WeightedAverageView))
        {
            return false;
        }

        WeightedAverageView myView = (WeightedAverageView) view;
        if ((!myView.getFieldNameWeight().equals(fieldNameWeight)) ||
            (!myView.getFieldNameX().equals(fieldNameX)) )
        {
            return false;
        }
        return true;
    }
}
