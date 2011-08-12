/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.regression.client;

import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.view.*;
import com.espertech.esper.core.StatementContext;
import com.espertech.esper.epl.expression.ExprNode;

import java.util.List;

public class MyTrendSpotterViewFactory extends ViewFactorySupport
{
    private List<ExprNode> viewParameters;

    private ExprNode expression;
    private EventType eventType;

    public void setViewParameters(ViewFactoryContext viewFactoryContext, List<ExprNode> viewParameters) throws ViewParameterException
    {
        this.viewParameters = viewParameters;
    }

    public void attach(EventType parentEventType, StatementContext statementContext, ViewFactory optionalParentFactory, List<ViewFactory> parentViewFactories) throws ViewParameterException
    {
        ExprNode[] validated = ViewFactorySupport.validate("Trend spotter view", parentEventType, statementContext, viewParameters, false);
        String message = "Trend spotter view accepts a single integer or double value";
        if (validated.length != 1)
        {
            throw new ViewParameterException(message);
        }
        Class resultType = validated[0].getExprEvaluator().getType();
        if ((resultType != Integer.class) && (resultType != int.class) &&
            (resultType != Double.class) && (resultType != double.class))
        {
            throw new ViewParameterException(message);
        }
        expression = validated[0];
        eventType = MyTrendSpotterView.createEventType(statementContext);
    }

    public View makeView(StatementContext statementContext)
    {
        return new MyTrendSpotterView(statementContext, expression);
    }

    public EventType getEventType()
    {
        return eventType;
    }
}
