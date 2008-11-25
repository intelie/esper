/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.pattern;

import com.espertech.esper.client.EPException;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.event.EventBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

public class PatternExpressionUtil
{
    private static Log log = LogFactory.getLog(PatternExpressionUtil.class);

    public static List<Object> evaluate(String objectName, MatchedEventMap beginState, List<ExprNode> parameters, MatchedEventConvertor convertor)
            throws EPException
    {
        List<Object> results = new ArrayList<Object>();
        int count = 0;
        EventBean[] eventsPerStream = convertor.convert(beginState);
        for (ExprNode expr : parameters)
        {
            try
            {
                Object result = evaluate(objectName, expr, eventsPerStream);
                results.add(result);
                count++;
            }
            catch (RuntimeException ex)
            {
                String message = objectName + " invalid parameter in expression " + count;
                if (ex.getMessage() != null)
                {
                    message += ": " + ex.getMessage();
                }
                log.error(message, ex);
                throw new EPException(message);
            }
        }
        return results;
    }

    public static Object evaluate(String objectName, MatchedEventMap beginState, ExprNode parameter, MatchedEventConvertor convertor)
            throws EPException
    {
        EventBean[] eventsPerStream = convertor.convert(beginState);
        return evaluate(objectName, parameter, eventsPerStream);
    }

    private static Object evaluate(String objectName, ExprNode expression, EventBean[] eventsPerStream) throws EPException
    {
        try
        {
            return expression.evaluate(eventsPerStream, true);
        }
        catch (RuntimeException ex)
        {
            String message = objectName + " failed to evaluate expression";
            if (ex.getMessage() != null)
            {
                message += ": " + ex.getMessage();
            }
            log.error(message, ex);
            throw new EPException(message);
        }
    }
}