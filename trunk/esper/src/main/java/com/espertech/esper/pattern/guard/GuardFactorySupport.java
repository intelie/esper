/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.pattern.guard;

import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.epl.expression.ExprIdentNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class for applications to extend to implement pattern guard objects.
 */
public abstract class GuardFactorySupport implements GuardFactory
{
    public static List<Object> evaluate(String guardName, List<ExprNode> parameters)
            throws GuardParameterException
    {
        List<Object> results = new ArrayList<Object>();
        int count = 0;
        for (ExprNode expr : parameters)
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
                throw new GuardParameterException(guardName + " reports failed parameter evaluation in parameter expression " + count + ": " + ex.getMessage());
            }
        }
        return results;
    }
}
