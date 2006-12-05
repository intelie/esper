package net.esper.view.stat;

import net.esper.view.factory.ViewFactory;
import net.esper.view.factory.ViewParameterException;
import net.esper.view.factory.ViewAttachException;
import net.esper.view.*;
import net.esper.event.EventType;

import java.util.List;

public class RegressionLinestViewFactory implements ViewFactory
{
    private String fieldNameX;
    private String fieldNameY;
    private EventType eventType;

    public void setViewParameters(List<Object> viewParameters) throws ViewParameterException
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

    public void attach(EventType parentEventType, ViewServiceContext viewServiceContext, ViewFactory optionalParentFactory) throws ViewAttachException
    {
        String result = PropertyCheckHelper.checkNumeric(parentEventType, fieldNameX, fieldNameY);
        if (result != null)
        {
            throw new ViewAttachException(result);
        }

        eventType = viewServiceContext.getEventAdapterService().addBeanType(RegressionBean.class.getName(), RegressionBean.class);
    }

    public boolean canProvideCapability(Class capabilityInterfaceClass)
    {
        return false;
    }

    public void setProvideCapability(Class capabilityInterfaceClass)
    {
        throw new UnsupportedOperationException("View capability " + capabilityInterfaceClass.getSimpleName() + " not supported");
    }

    public View makeView(ViewServiceContext viewServiceContext)
    {
        return new RegressionLinestView(fieldNameX, fieldNameY);
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
