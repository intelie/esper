/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.pattern.observer;

import com.espertech.esper.epl.expression.ExprIdentNode;
import com.espertech.esper.epl.expression.ExprNode;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class for applications to extend to implement a pattern observer.
 */
public abstract class EventObserverSupport implements EventObserver
{
    private static Log log = LogFactory.getLog(EventObserverSupport.class);
    
    public static List<Object> evaluate(String observerName, List<ExprNode> parameters)
            throws ObserverParameterException
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
                String message = observerName + " invalid parameter in expression " + count;
                if (ex.getMessage() != null)
                {
                    message += ": " + ex.getMessage();
                }
                log.error(message, ex);
                throw new ObserverParameterException(message);
            }
        }
        return results;
    }
}
