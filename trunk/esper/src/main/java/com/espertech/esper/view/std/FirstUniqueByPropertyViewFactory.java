/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.view.std;

import com.espertech.esper.view.ViewFactory;
import com.espertech.esper.view.ViewParameterException;
import com.espertech.esper.view.ViewAttachException;
import com.espertech.esper.view.*;
import com.espertech.esper.event.EventType;
import com.espertech.esper.epl.core.ViewResourceCallback;
import com.espertech.esper.epl.named.RemoveStreamViewCapability;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.core.StatementContext;

import java.util.List;
import java.util.Arrays;

/**
 * Factory for {@link FirstUniqueByPropertyView} instances.
 */
public class FirstUniqueByPropertyViewFactory implements DataWindowViewFactory
{
    /**
     * Property name to evaluate unique values.
     */
    protected String[] propertyNames;

    private EventType eventType;

    public void setViewParameters(ViewFactoryContext viewFactoryContext, List<ExprNode> expressionParameters) throws ViewParameterException
    {
        List<Object> viewParameters = ViewFactorySupport.evaluate("First unique view", viewFactoryContext, expressionParameters);
        propertyNames = GroupByViewFactory.getFieldNameParams(viewParameters, "First unique");
    }

    public void attach(EventType parentEventType, StatementContext statementContext, ViewFactory optionalParentFactory, List<ViewFactory> parentViewFactories) throws ViewAttachException
    {
        // Attaches to just about anything as long as all the fields exists
        for (int i = 0; i < propertyNames.length; i++)
        {
            String message = PropertyCheckHelper.exists(parentEventType, propertyNames[i]);
            if (message != null)
            {
                throw new ViewAttachException(message);
            }
        }
        this.eventType = parentEventType;
    }

    public boolean canProvideCapability(ViewCapability viewCapability)
    {
        if (viewCapability instanceof RemoveStreamViewCapability)
        {
            return true;
        }
        return false;
    }

    public void setProvideCapability(ViewCapability viewCapability, ViewResourceCallback resourceCallback)
    {
        if (viewCapability instanceof RemoveStreamViewCapability)
        {
            return;
        }
        throw new UnsupportedOperationException("View capability " + viewCapability.getClass().getSimpleName() + " not supported");
    }

    public View makeView(StatementContext statementContext)
    {
        return new FirstUniqueByPropertyView(propertyNames);
    }

    public EventType getEventType()
    {
        return eventType;
    }

    public boolean canReuse(View view)
    {
        if (!(view instanceof FirstUniqueByPropertyView))
        {
            return false;
        }

        FirstUniqueByPropertyView myView = (FirstUniqueByPropertyView) view;
        if (!Arrays.deepEquals(myView.getUniqueFieldNames(), propertyNames))
        {
            return false;
        }

        if (!myView.isEmpty())
        {
            return false;
        }

        return true;
    }
}
