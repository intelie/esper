package com.espertech.esper.view.stat;

import com.espertech.esper.view.ViewFactory;
import com.espertech.esper.view.ViewParameterException;
import com.espertech.esper.view.ViewAttachException;
import com.espertech.esper.view.*;
import com.espertech.esper.event.EventType;
import com.espertech.esper.epl.core.ViewResourceCallback;
import com.espertech.esper.core.StatementContext;

import java.util.List;

/**
 * Factory for {@link UnivariateStatisticsView} instances. 
 */
public class UnivariateStatisticsViewFactory implements ViewFactory
{
    /**
     * Property name of data field.
     */
    protected String fieldName;
    private EventType eventType;

    public void setViewParameters(ViewFactoryContext viewFactoryContext, List<Object> viewParameters) throws ViewParameterException
    {
        String errorMessage = "'Univariate statistics' view require a single field name as a parameter";
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
        eventType = UnivariateStatisticsView.createEventType(statementContext);
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
        return new UnivariateStatisticsView(statementContext, fieldName);
    }

    public EventType getEventType()
    {
        return eventType;
    }

    public boolean canReuse(View view)
    {
        if (!(view instanceof UnivariateStatisticsView))
        {
            return false;
        }

        UnivariateStatisticsView other = (UnivariateStatisticsView) view;
        if (!other.getFieldName().equals(fieldName))
        {
            return false;
        }

        return true;
    }    
}
