/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.view;

import com.espertech.esper.epl.core.ViewResourceCallback;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.epl.expression.ExprIdentNode;

import java.util.List;
import java.util.ArrayList;

/**
 * Abstract base class for view factories that do not make re-useable views and that do
 * not share view resources with expression nodes.
 */
public abstract class ViewFactorySupport implements ViewFactory
{
    public boolean canProvideCapability(ViewCapability viewCapability)
    {
        return false;
    }

    public void setProvideCapability(ViewCapability viewCapability, ViewResourceCallback resourceCallback)
    {
        throw new UnsupportedOperationException("View capability " + viewCapability.getClass().getSimpleName() + " not supported");
    }

    public boolean canReuse(View view)
    {
        return false;
    }

    public static List<Object> evaluate(String viewName, ViewFactoryContext viewFactoryContext, List<ExprNode> viewParameters)
            throws ViewParameterException
    {
        List<Object> results = new ArrayList<Object>();
        int count = 0;
        for (ExprNode expr : viewParameters)
        {
            try
            {
                if (expr instanceof ExprIdentNode)
                {
                    results.add(((ExprIdentNode)expr).getFullUnresolvedName());
                }
                else
                {
                    results.add(expr.evaluate(null, true));
                }
                count++;
            }
            catch (RuntimeException ex)
            {
                throw new ViewParameterException(viewName + " reports failed parameter evaluation in parameter expression " + count + ": " + ex.getMessage());
            }
        }
        return results;
    }
}
