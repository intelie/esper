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

package com.espertech.esper.support.view;

import com.espertech.esper.view.*;
import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.core.ViewResourceCallback;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.core.StatementContext;

import java.util.List;

public abstract class SupportViewFactory implements ViewFactory
{
    public void setViewParameters(ViewFactoryContext viewFactoryContext, List<ExprNode> viewParameters) throws ViewParameterException
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void attach(EventType parentEventType, StatementContext statementContext, ViewFactory optionalParentFactory, List<ViewFactory> parentViewFactories) throws ViewParameterException
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean canProvideCapability(ViewCapability viewCapability)
    {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setProvideCapability(ViewCapability viewCapability, ViewResourceCallback resourceCallback)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public View makeView(StatementContext statementContext)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public EventType getEventType()
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean canReuse(View view)
    {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
