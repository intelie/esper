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
 * Factory for {@link WeightedAverageView} instances.
 */
public class WeightedAverageViewFactory implements ViewFactory
{
    private List<ExprNode> viewParameters;

    /**
     * Property name of X field.
     */
    protected String fieldNameX;
    /**
     * Property name of weight field.
     */
    protected String fieldNameWeight;

    private EventType eventType;

    public void setViewParameters(ViewFactoryContext viewFactoryContext, List<ExprNode> expressionParameters) throws ViewParameterException
    {
        this.viewParameters = expressionParameters;

        List<Object> viewParameters = ViewFactorySupport.validateAndEvaluate("'Weighted average' view", viewFactoryContext, expressionParameters);
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

    public void attach(EventType parentEventType, StatementContext statementContext, ViewFactory optionalParentFactory, List<ViewFactory> parentViewFactories) throws ViewParameterException
    {
        String result = PropertyCheckHelper.checkNumeric(parentEventType, fieldNameX, fieldNameWeight);
        if (result != null)
        {
            throw new ViewParameterException(result);
        }
        eventType = WeightedAverageView.createEventType(statementContext);
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
        return new WeightedAverageView(statementContext, fieldNameX, fieldNameWeight);
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
