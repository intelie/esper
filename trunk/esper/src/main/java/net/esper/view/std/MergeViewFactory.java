package net.esper.view.std;

import net.esper.view.*;
import net.esper.event.EventType;
import net.esper.eql.core.ViewResourceCallback;

import java.util.List;
import java.util.Arrays;

/**
 * Factory for {@link MergeView} instances. 
 */
public class MergeViewFactory implements ViewFactory
{
    private String[] fieldNames;
    private Class[] fieldTypes;
    private EventType eventType;

    public void setViewParameters(List<Object> viewParameters) throws ViewParameterException
    {
        fieldNames = GroupByViewFactory.getFieldNameParams(viewParameters, "Group-by-merge");
    }

    public void attach(EventType parentEventType, ViewServiceContext viewServiceContext, ViewFactory optionalParentFactory, List<ViewFactory> parentViewFactories) throws ViewAttachException
    {
        // Find the group by view matching the merge view
        ViewFactory groupByViewFactory = null;
        for (ViewFactory parentView : parentViewFactories)
        {
            if (!(parentView instanceof GroupByViewFactory))
            {
                continue;
            }
            GroupByViewFactory candidateGroupByView = (GroupByViewFactory) parentView;
            if (Arrays.equals(candidateGroupByView.getGroupFieldNames(), this.fieldNames))
            {
                groupByViewFactory = candidateGroupByView;
            }
        }

        if (groupByViewFactory == null)
        {
            throw new ViewAttachException("Group by view for this merge view could not be found among parent views");
        }

        fieldTypes = new Class[fieldNames.length];
        for (int i = 0; i < fieldTypes.length; i++)
        {
            fieldTypes[i] = groupByViewFactory.getEventType().getPropertyType(fieldNames[i]);
        }
        eventType = viewServiceContext.getEventAdapterService().createAddToEventType(
                parentEventType, fieldNames, fieldTypes);
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
        return new MergeView(fieldNames, eventType);
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
