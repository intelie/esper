package net.esper.view.stat;

import net.esper.view.ViewFactory;
import net.esper.view.ViewParameterException;
import net.esper.view.ViewAttachException;
import net.esper.view.*;
import net.esper.event.EventType;
import net.esper.eql.core.ViewFactoryCallback;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class UnivariateStatisticsViewFactory implements ViewFactory
{
    private String fieldName;
    private EventType eventType;

    public void setViewParameters(List<Object> viewParameters) throws ViewParameterException
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

    public void attach(EventType parentEventType, ViewServiceContext viewServiceContext, ViewFactory optionalParentFactory) throws ViewAttachException
    {
        String result = PropertyCheckHelper.checkNumeric(parentEventType, fieldName);
        if (result != null)
        {
            throw new ViewAttachException(result);
        }

        Map<String, Class> eventTypeMap = new HashMap<String, Class>();
        eventTypeMap.put(ViewFieldEnum.UNIVARIATE_STATISTICS__COUNT.getName(), long.class);
        eventTypeMap.put(ViewFieldEnum.UNIVARIATE_STATISTICS__SUM.getName(), double.class);
        eventTypeMap.put(ViewFieldEnum.UNIVARIATE_STATISTICS__STDDEV.getName(), double.class);
        eventTypeMap.put(ViewFieldEnum.UNIVARIATE_STATISTICS__STDDEVPA.getName(), double.class);
        eventTypeMap.put(ViewFieldEnum.UNIVARIATE_STATISTICS__VARIANCE.getName(), double.class);
        eventTypeMap.put(ViewFieldEnum.UNIVARIATE_STATISTICS__AVERAGE.getName(), double.class);
        eventType = viewServiceContext.getEventAdapterService().createAnonymousMapType(eventTypeMap);
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
        return new UnivariateStatisticsView(fieldName);
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
