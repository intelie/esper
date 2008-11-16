/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.view.stat;

import com.espertech.esper.epl.core.ViewResourceCallback;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.event.EventType;
import com.espertech.esper.view.*;
import com.espertech.esper.core.StatementContext;

import java.util.Arrays;
import java.util.List;

/**
 * Factory for {@link MultiDimStatsView} instances.
 */
public class MultiDimStatsViewFactory implements ViewFactory
{
    /**
     * Derived fields.
     */
    protected String[] derivedMeasures;

    /**
     * Property name supplying measures.
     */
    protected String measureField;

    /**
     * Property name supplying columns.
     */
    protected String columnField;

    /**
     * Property name supplying rows.
     */
    protected String rowField;

    /**
     * Property name supplying pages.
     */
    protected String pageField;
    private EventType eventType;

    public void setViewParameters(ViewFactoryContext viewFactoryContext, List<ExprNode> expressionParameters) throws ViewParameterException
    {
        List<Object> viewParameters = ViewFactorySupport.evaluate("'Multi-dimensional stats' view", viewFactoryContext, expressionParameters);
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

    public void attach(EventType parentEventType, StatementContext statementContext, ViewFactory optionalParentFactory, List<ViewFactory> parentViewFactories) throws ViewAttachException
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

        eventType = MultiDimStatsView.createEventType(statementContext);
    }

    public boolean canProvideCapability(ViewCapability viewCapability)
    {
        return false;
    }

    public void setProvideCapability(ViewCapability viewCapability, ViewResourceCallback resourceCallback)
    {
        throw new UnsupportedOperationException("View capability " + viewCapability.getClass().getSimpleName() + " not supported");
    }

    public View makeView(StatementContext statementContext)
    {
        return new MultiDimStatsView(statementContext, derivedMeasures, measureField, columnField, rowField, pageField);
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
