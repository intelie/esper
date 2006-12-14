package net.esper.view.std;

import net.esper.view.*;
import net.esper.event.EventType;
import net.esper.eql.core.ViewResourceCallback;

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

    public boolean canProvideCapability(ViewCapability viewCapability)
    {
        return false;
    }

    public void setProvideCapability(ViewCapability viewCapability, ViewResourceCallback resourceCallback)
    {
        throw new UnsupportedOperationException("View capability " + viewCapability.getClass().getSimpleName() + " not supported");
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
