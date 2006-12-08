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

public class WeightedAverageViewFactory implements ViewFactory
{
    private String fieldNameX;
    private String fieldNameWeight;
    private EventType eventType;

    public void setViewParameters(List<Object> viewParameters) throws ViewParameterException
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

    public void attach(EventType parentEventType, ViewServiceContext viewServiceContext, ViewFactory optionalParentFactory) throws ViewAttachException
    {
        String result = PropertyCheckHelper.checkNumeric(parentEventType, fieldNameX, fieldNameWeight);
        if (result != null)
        {
            throw new ViewAttachException(result);
        }

        Map<String, Class> schemaMap = new HashMap<String, Class>();
        schemaMap.put(ViewFieldEnum.WEIGHTED_AVERAGE__AVERAGE.getName(), double.class);
        eventType = viewServiceContext.getEventAdapterService().createAnonymousMapType(schemaMap);
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
        return new WeightedAverageView(fieldNameX, fieldNameWeight);
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
