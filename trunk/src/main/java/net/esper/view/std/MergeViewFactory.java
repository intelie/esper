package net.esper.view.std;

import net.esper.view.factory.ViewFactory;
import net.esper.view.factory.ViewParameterException;
import net.esper.view.factory.ViewAttachException;
import net.esper.view.ViewServiceContext;
import net.esper.view.View;
import net.esper.event.EventType;

import java.util.List;
import java.util.Arrays;

public class MergeViewFactory implements ViewFactory
{
    private String[] fieldNames;
    private EventType eventType;

    public void setViewParameters(List<Object> viewParameters) throws ViewParameterException
    {
        fieldNames = GroupByViewFactory.getFieldNameParams(viewParameters, "Group-by-merge");
    }

    public void attach(EventType parentEventType, ViewServiceContext viewServiceContext, ViewFactory optionalParentFactory) throws ViewAttachException
    {
        this.eventType = parentEventType;
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
        return new MergeView(fieldNames);
    }

    public EventType getEventType()
    {
        return eventType;
    }

    public boolean canReuse(View view)
    {
        if (!(view instanceof MergeView))
        {
            return false;
        }

        MergeView myView = (MergeView) view;
        if (!Arrays.deepEquals(myView.getGroupFieldNames(), fieldNames))
        {
            return false;
        }
        return true;
    }
}
