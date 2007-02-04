package net.esper.view.ext;

import net.esper.view.ViewFactory;
import net.esper.view.ViewParameterException;
import net.esper.view.ViewAttachException;
import net.esper.view.*;
import net.esper.event.EventType;
import net.esper.util.JavaClassHelper;
import net.esper.eql.core.ViewResourceCallback;

import java.util.List;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Factory for sort window views.
 */
public class SortWindowViewFactory implements ViewFactory
{
    private String[] sortFieldNames;
    private Boolean[] isDescendingValues;
    private int sortWindowSize;
    private EventType eventType;
    private boolean isRequiresRandomAccess;
    private List<ViewResourceCallback> resourceCallbacks = new LinkedList<ViewResourceCallback>();

    public void setViewParameters(List<Object> viewParameters) throws ViewParameterException
    {
        String errorMessage = "Sort window view requires a field name, a boolean sort order and a numeric size parameter or parameter list";
        if (viewParameters.size() == 3)
        {
            if ((viewParameters.get(0) instanceof String) &&
                (viewParameters.get(1) instanceof Boolean) &&
                (viewParameters.get(2) instanceof Number))
            {
                sortFieldNames = new String[] {(String)viewParameters.get(0)};
                isDescendingValues = new Boolean[] {(Boolean) viewParameters.get(1)};
                Number sizeParam = (Number) viewParameters.get(2);
                if (JavaClassHelper.isFloatingPointNumber(sizeParam))
                {
                    throw new ViewParameterException(errorMessage);
                }
                sortWindowSize = sizeParam.intValue();
            }
            else
            {
                throw new ViewParameterException(errorMessage);
            }
        }
        else if (viewParameters.size() == 2)
        {
            if ( (!(viewParameters.get(0) instanceof Object[]) ||
                 (!(viewParameters.get(1) instanceof Integer))) )
            {
                throw new ViewParameterException(errorMessage);
            }

            Object[] ascFieldsArr = (Object[]) viewParameters.get(0);
            try
            {
                setNamesAndIsDescendingValues(ascFieldsArr);
            }
            catch (RuntimeException ex)
            {
                throw new ViewParameterException(errorMessage + ",reason:" + ex.getMessage());
            }

            Number sizeParam = (Number) viewParameters.get(1);
            if (JavaClassHelper.isFloatingPointNumber(sizeParam))
            {
                throw new ViewParameterException(errorMessage);
            }
            sortWindowSize = sizeParam.intValue();
        }
        else
        {
            throw new ViewParameterException(errorMessage);
        }

        if (sortWindowSize < 1)
        {
            throw new ViewParameterException("Illegal argument for sort window size of sort window");
        }
    }

    public void attach(EventType parentEventType, ViewServiceContext viewServiceContext, ViewFactory optionalParentFactory, List<ViewFactory> parentViewFactories) throws ViewAttachException
    {
        // Attaches to parent views where the sort fields exist and implement Comparable
        String result = null;
        for(String name : sortFieldNames)
        {
            result = PropertyCheckHelper.exists(parentEventType, name);

            if(result != null)
            {
                throw new ViewAttachException(result);
            }
        }

        eventType = parentEventType;
    }

    public boolean canProvideCapability(ViewCapability viewCapability)
    {
        if (viewCapability instanceof ViewCapDataWindowAccess)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void setProvideCapability(ViewCapability viewCapability, ViewResourceCallback resourceCallback)
    {
        if (!canProvideCapability(viewCapability))
        {
            throw new UnsupportedOperationException("View capability " + viewCapability.getClass().getSimpleName() + " not supported");
        }
        isRequiresRandomAccess = true;
        resourceCallbacks.add(resourceCallback);
    }

    public View makeView(ViewServiceContext viewServiceContext)
    {
        IStreamSortedRandomAccess sortedRandomAccess = null;

        if (isRequiresRandomAccess)
        {
            sortedRandomAccess = new IStreamSortedRandomAccess();
            for (ViewResourceCallback resourceCallback : resourceCallbacks)
            {
                resourceCallback.setViewResource(sortedRandomAccess);
            }
        }
        return new SortWindowView(this, sortFieldNames, isDescendingValues, sortWindowSize, sortedRandomAccess);
    }

    public EventType getEventType()
    {
        return eventType;
    }

    public boolean canReuse(View view)
    {
        if (!(view instanceof SortWindowView))
        {
            return false;
        }

        SortWindowView other = (SortWindowView) view;
        if ((other.getSortWindowSize() != sortWindowSize) ||
            (!Arrays.deepEquals(other.getIsDescendingValues(), isDescendingValues)) ||
            (!Arrays.deepEquals(other.getSortFieldNames(), sortFieldNames)) )
        {
            return false;
        }

        return other.isEmpty();
    }

    @SuppressWarnings({"MultiplyOrDivideByPowerOfTwo"})
    private void setNamesAndIsDescendingValues(Object[] propertiesAndDirections)
    {
        if(propertiesAndDirections.length % 2 != 0)
        {
            throw new IllegalArgumentException("Each property to sort by must have an isDescending boolean qualifier");
        }

        int length = propertiesAndDirections.length / 2;
        sortFieldNames = new String[length];
        isDescendingValues  = new Boolean[length];

        for(int i = 0; i < length; i++)
        {
            sortFieldNames[i] = (String)propertiesAndDirections[2*i];
            isDescendingValues[i] = (Boolean)propertiesAndDirections[2*i + 1];
        }
    }
}
