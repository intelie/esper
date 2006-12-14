package net.esper.view.std;

import net.esper.view.*;
import net.esper.event.EventType;
import net.esper.eql.core.ViewResourceCallback;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class GroupByViewFactory implements ViewFactory
{
    private String[] groupFieldNames;
    private EventType eventType;

    public void setViewParameters(List<Object> viewParameters) throws ViewParameterException
    {
        groupFieldNames = getFieldNameParams(viewParameters, "Group-by");
    }

    public void attach(EventType parentEventType, ViewServiceContext viewServiceContext, ViewFactory optionalParentFactory) throws ViewAttachException
    {
        // Attaches to just about anything as long as all the fields exists
        for (int i = 0; i < groupFieldNames.length; i++)
        {
            String message = PropertyCheckHelper.exists(parentEventType, groupFieldNames[i]);
            if (message != null)
            {
                throw new ViewAttachException(message);
            }
        }

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
        return new GroupByView(groupFieldNames);
    }

    public EventType getEventType()
    {
        return eventType;
    }

    protected static String[] getFieldNameParams(List<Object> viewParameters, String viewName) throws ViewParameterException
    {
        String[] fieldNames = null;

        String errorMessage = "'" + viewName + "' view requires a list of String values or a String-array as parameter";
        if (viewParameters.size() == 0)
        {
            throw new ViewParameterException(errorMessage);
        }

        if (viewParameters.size() > 1)
        {
            List<String> fields = new ArrayList<String>();
            for (Object param : viewParameters)
            {
                if (!(param instanceof String))
                {
                    throw new ViewParameterException(errorMessage);
                }
                fields.add((String) param);
            }
            fieldNames = fields.toArray(new String[0]);
        }
        else
        {
            Object param = viewParameters.get(0);
            if (param instanceof String[])
            {
                String[] arr = (String[]) param;
                if (arr.length == 0)
                {
                    throw new ViewParameterException(errorMessage);
                }
                fieldNames = arr;
            }
            else if (param instanceof String)
            {
                fieldNames = new String[] {(String)param};
            }
            else
            {
                throw new ViewParameterException(errorMessage);
            }
        }

        return fieldNames;
    }

    public boolean canReuse(View view)
    {
        if (!(view instanceof GroupByView))
        {
            return false;
        }

        GroupByView myView = (GroupByView) view;
        if (!Arrays.deepEquals(myView.getGroupFieldNames(), groupFieldNames))
        {
            return false;
        }

        return true;
    }
}
