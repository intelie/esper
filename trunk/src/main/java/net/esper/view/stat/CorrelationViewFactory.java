package net.esper.view.stat;

import net.esper.view.ViewFactory;
import net.esper.view.ViewParameterException;
import net.esper.view.*;
import net.esper.event.EventType;
import net.esper.eql.core.ViewFactoryCallback;

import java.util.List;

public class CorrelationViewFactory implements ViewFactory
{
    private String fieldNameX;
    private String fieldNameY;
    private EventType eventType;

    public void setViewParameters(List<Object> viewParameters) throws ViewParameterException
    {
        String errorMessage = "Correlation view requires two field names as parameters";
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

        eventType = viewServiceContext.getEventAdapterService().addBeanType(CorrelationBean.class.getName(), CorrelationBean.class);
    }

    public boolean canProvideCapability(Class capabilityInterfaceClass)
    {
        return false;
    }

    public void setProvideCapability(Class capabilityInterfaceClass, ViewFactoryCallback factoryCallback)
    {
        throw new UnsupportedOperationException("View capability " + capabilityInterfaceClass.getSimpleName() + " not supported");
    }

    public View makeView(ViewServiceContext viewServiceContext)
    {
        return new CorrelationView(fieldNameX, fieldNameY);
    }

    public EventType getEventType()
    {
        return eventType;
    }

    public boolean canReuse(View view)
    {
        if (!(view instanceof CorrelationView))
        {
            return false;
        }

        CorrelationView other = (CorrelationView) view;
        if ((!other.getFieldNameX().equals(fieldNameX)) ||
            (!other.getFieldNameY().equals(fieldNameY)))
        {
            return false;
        }

        return true;
    }
}
