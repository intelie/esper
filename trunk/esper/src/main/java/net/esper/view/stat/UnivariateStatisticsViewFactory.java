package net.esper.view.stat;

import net.esper.view.ViewFactory;
import net.esper.view.ViewParameterException;
import net.esper.view.ViewAttachException;
import net.esper.view.*;
import net.esper.event.EventType;
import net.esper.eql.core.ViewResourceCallback;

import java.util.List;

/**
 * Factory for {@link UnivariateStatisticsView} instances. 
 */
public class UnivariateStatisticsViewFactory implements ViewFactory
{
    private String fieldName;
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

    public void attach(EventType parentEventType, StatementServiceContext statementServiceContext, ViewFactory optionalParentFactory, List<ViewFactory> parentViewFactories) throws ViewAttachException
    {
        String result = PropertyCheckHelper.checkNumeric(parentEventType, fieldName);
        if (result != null)
        {
            throw new ViewAttachException(result);
        }
        eventType = UnivariateStatisticsView.createEventType(statementServiceContext);
    }

    public boolean canProvideCapability(ViewCapability viewCapability)
    {
        return false;
    }

    public void setProvideCapability(ViewCapability viewCapability, ViewResourceCallback resourceCallback)
    {
        throw new UnsupportedOperationException("View capability " + viewCapability.getClass().getSimpleName() + " not supported");
    }

    public View makeView(StatementServiceContext statementServiceContext)
    {
        return new UnivariateStatisticsView(statementServiceContext, fieldName);
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
