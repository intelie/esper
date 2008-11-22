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
import com.espertech.esper.util.JavaClassHelper;

import java.util.Arrays;
import java.util.List;

/**
 * Factory for {@link MultiDimStatsView} instances.
 */
public class MultiDimStatsViewFactory implements ViewFactory
{
    private List<ExprNode> viewParameters;

    /**
     * Derived fields.
     */
    protected String[] derivedMeasures;

    /**
     * Property name supplying measures.
     */
    protected ExprNode measureExpression;

    /**
     * Property name supplying columns.
     */
    protected ExprNode columnExpression;

    /**
     * Property name supplying rows.
     */
    protected ExprNode rowExpression;

    /**
     * Property name supplying pages.
     */
    protected ExprNode pageExpression;
    private EventType eventType;

    public void setViewParameters(ViewFactoryContext viewFactoryContext, List<ExprNode> expressionParameters) throws ViewParameterException
    {
        this.viewParameters = expressionParameters;
    }

    public void attach(EventType parentEventType, StatementContext statementContext, ViewFactory optionalParentFactory, List<ViewFactory> parentViewFactories) throws ViewParameterException
    {
        ExprNode[] validated = ViewFactorySupport.validate("Multi-dimensional stats view", parentEventType, statementContext, viewParameters, true);
        String errorMessage = "Multi-dimensional stats view requires a String-array of measure names and 2 or more numeric value expressions as parameters";
        if (viewParameters.size() < 3)
        {
            throw new ViewParameterException(errorMessage);
        }

        if (!(validated[0].getType() == String[].class))
        {
            throw new ViewParameterException(errorMessage);
        }
        derivedMeasures = (String[]) ViewFactorySupport.assertNoProperties("Multi-dimensional stats view", validated[0], 0);
        measureExpression = validated[1];
        columnExpression = validated[2];

        if (viewParameters.size() > 3)
        {
            rowExpression = validated[3];
            if (!JavaClassHelper.isNumeric(rowExpression.getType()))
            {
                throw new ViewParameterException(errorMessage);
            }
        }

        if (viewParameters.size() > 4)
        {
            pageExpression = validated[4];
            if (!JavaClassHelper.isNumeric(pageExpression.getType()))
            {
                throw new ViewParameterException(errorMessage);
            }
        }

        for (String measureName : derivedMeasures)
        {
            if (Arrays.binarySearch(ViewFieldEnum.MULTIDIM_OLAP__MEASURES, measureName) < 0)
            {
                throw new ViewParameterException("Derived measure named '" + measureName + "' is not a valid measure");
            }
        }

        if ( (!JavaClassHelper.isNumeric(measureExpression.getType())) ||
             (!JavaClassHelper.isNumeric(columnExpression.getType())))
        {
            throw new ViewParameterException(errorMessage);
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
        return new MultiDimStatsView(statementContext, derivedMeasures, measureExpression, columnExpression, rowExpression, pageExpression);
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
        if (!ExprNode.deepEquals(other.getMeasureField(), measureExpression))
        {
            return false;
        }
        if (!ExprNode.deepEquals(other.getColumnField(), columnExpression))
        {
            return false;
        }
        if ((other.getRowField() != null) && (rowExpression != null))
        {
            if (!ExprNode.deepEquals(other.getRowField(), rowExpression))
            {
                return false;
            }
        }
        if ( ((other.getRowField() == null) && (rowExpression != null)) ||
             ((other.getRowField() != null) && (rowExpression == null)) )
        {
            return false;
        }
        if ((other.getPageField() != null) && (pageExpression != null))
        {
            if (!ExprNode.deepEquals(other.getPageField(), pageExpression))
            {
                return false;
            }
        }
        if ( ((other.getPageField() == null) && (pageExpression != null)) ||
             ((other.getPageField() != null) && (pageExpression == null)) )
        {
            return false;
        }

        return true;
    }
}
