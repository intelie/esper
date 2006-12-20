package net.esper.view.stat;

import net.esper.view.ViewFactory;
import net.esper.view.ViewParameterException;
import net.esper.view.ViewAttachException;
import net.esper.view.*;
import net.esper.view.stat.olap.Cube;
import net.esper.event.EventType;
import net.esper.eql.core.ViewResourceCallback;

import java.util.List;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

public class MultiDimStatsViewFactory implements ViewFactory
{
    private String[] derivedMeasures;
    private String measureField;
    private String columnField;
    private String rowField;
    private String pageField;
    private EventType eventType;

    public void setViewParameters(List<Object> viewParameters) throws ViewParameterException
    {
        String errorMessage = "'Multi-dimensional stats' view requires a String-array and 2 or more field names as parameters";
        if (viewParameters.size() < 3)
        {
            throw new ViewParameterException(errorMessage);
        }

        if ( (!(viewParameters.get(0) instanceof String[])) ||
             (!(viewParameters.get(1) instanceof String)) ||
             (!(viewParameters.get(2) instanceof String)) )
        {
            throw new ViewParameterException(errorMessage);
        }

        derivedMeasures = (String[]) viewParameters.get(0);
        measureField = (String) viewParameters.get(1);
        columnField = (String) viewParameters.get(2);

        if (viewParameters.size() > 3)
        {
            if (!(viewParameters.get(3) instanceof String))
            {
                throw new ViewParameterException(errorMessage);
            }
            rowField = (String) viewParameters.get(3);
        }

        if (viewParameters.size() > 4)
        {
            if (!(viewParameters.get(4) instanceof String))
            {
                throw new ViewParameterException(errorMessage);
            }
            pageField = (String) viewParameters.get(4);
        }

        for (String measureName : derivedMeasures)
        {
            if (Arrays.binarySearch(ViewFieldEnum.MULTIDIM_OLAP__MEASURES, measureName) < 0)
            {
                throw new ViewParameterException("Derived measure named '" + measureName + "' is not a valid measure");
            }
        }
    }

    public void attach(EventType parentEventType, ViewServiceContext viewServiceContext, ViewFactory optionalParentFactory, List<ViewFactory> parentViewFactories) throws ViewAttachException
    {
        String message = PropertyCheckHelper.checkNumeric(parentEventType, measureField);
        if (message != null)
        {
            throw new ViewAttachException(message);
        }

        message = PropertyCheckHelper.exists(parentEventType, columnField);
        if (message != null)
        {
            throw new ViewAttachException(message);
        }

        if (rowField != null)
        {
            message = PropertyCheckHelper.exists(parentEventType, rowField);
            if (message != null)
            {
                throw new ViewAttachException(message);
            }
        }

        if (pageField != null)
        {
            message = PropertyCheckHelper.exists(parentEventType, pageField);
            if (message != null)
            {
                throw new ViewAttachException(message);
            }
        }

        eventType = MultiDimStatsView.createEventType(viewServiceContext);
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
        return new MultiDimStatsView(derivedMeasures, measureField, columnField, rowField, pageField);
    }

    public EventType getEventType()
    {
        return eventType;
    }

    public boolean canReuse(View view)
    {
        if (!(view instanceof MultiDimStatsView))
        {
            return false;
        }

        MultiDimStatsView other = (MultiDimStatsView) view;
        if (!Arrays.deepEquals(other.getDerivedMeasures(), derivedMeasures))
        {
            return false;
        }
        if (!other.getMeasureField().equals(measureField))
        {
            return false;
        }
        if (!other.getColumnField().equals(columnField))
        {
            return false;
        }
        if ((other.getRowField() != null) && (rowField != null))
        {
            if (!other.getRowField().equals(rowField))
            {
                return false;
            }
        }
        if ( ((other.getRowField() == null) && (rowField != null)) ||
             ((other.getRowField() != null) && (rowField == null)) )
        {
            return false;
        }
        if ((other.getPageField() != null) && (pageField != null))
        {
            if (!other.getPageField().equals(pageField))
            {
                return false;
            }
        }
        if ( ((other.getPageField() == null) && (pageField != null)) ||
             ((other.getPageField() != null) && (pageField == null)) )
        {
            return false;
        }

        return true;
    }
}
