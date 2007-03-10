package net.esper.view.stat;

import net.esper.view.ViewFactory;
import net.esper.view.ViewParameterException;
import net.esper.view.ViewAttachException;
import net.esper.view.*;
import net.esper.event.EventType;
import net.esper.eql.core.ViewResourceCallback;

import java.util.List;

/**
 * Factory for {@link RegressionLinestView} instances.
 */
public class RegressionLinestViewFactory implements ViewFactory
{
    private String fieldNameX;
    private String fieldNameY;
    private EventType eventType;

    public void setViewParameters(ViewFactoryContext viewFactoryContext, List<Object> viewParameters) throws ViewParameterException
    {
        String errorMessage = "'Regression line' view requires two field names as parameters";
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
        fieldNameY = (String) viewParameters.get(1);
    }

    public void attach(EventType parentEventType, StatementServiceContext statementServiceContext, ViewFactory optionalParentFactory, List<ViewFactory> parentViewFactories) throws ViewAttachException
    {
        String result = PropertyCheckHelper.checkNumeric(parentEventType, fieldNameX, fieldNameY);
        if (result != null)
        {
            throw new ViewAttachException(result);
        }

        eventType = RegressionLinestView.createEventType(statementServiceContext);
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
        return new RegressionLinestView(statementServiceContext, fieldNameX, fieldNameY);
    }

    public boolean canReuse(View view)
    {
        if (!(view instanceof RegressionLinestView))
        {
            return false;
        }

        RegressionLinestView myView = (RegressionLinestView) view;
        if ((!myView.getFieldNameX().equals(fieldNameX)) ||
            (!myView.getFieldNameY().equals(fieldNameY)))
        {
            return false;
        }
        return true;
    }

    public EventType getEventType()
    {
        return eventType;
    }
}
