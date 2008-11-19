/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.view.stat;

import com.espertech.esper.view.ViewFactory;
import com.espertech.esper.view.ViewParameterException;
import com.espertech.esper.view.ViewParameterException;
import com.espertech.esper.view.*;
import com.espertech.esper.event.EventType;
import com.espertech.esper.epl.core.ViewResourceCallback;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.core.StatementContext;

import java.util.List;

/**
 * Factory for {@link UnivariateStatisticsView} instances.
 */
public class UnivariateStatisticsViewFactory implements ViewFactory
{
    private List<ExprNode> viewParameters;

    /**
     * Property name of data field.
     */
    protected String fieldName;
    private EventType eventType;

    public void setViewParameters(ViewFactoryContext viewFactoryContext, List<ExprNode> expressionParameters) throws ViewParameterException
    {
        this.viewParameters = expressionParameters;

        List<Object> viewParameters = ViewFactorySupport.validateAndEvaluate("'Univariate statistics' view", viewFactoryContext, expressionParameters);
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

    public void attach(EventType parentEventType, StatementContext statementContext, ViewFactory optionalParentFactory, List<ViewFactory> parentViewFactories) throws ViewParameterException
    {
        String result = PropertyCheckHelper.checkNumeric(parentEventType, fieldName);
        if (result != null)
        {
            throw new ViewParameterException(result);
        }
        eventType = UnivariateStatisticsView.createEventType(statementContext);
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
        return new UnivariateStatisticsView(statementContext, fieldName);
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
