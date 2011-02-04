/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.pattern.guard;

import com.espertech.esper.client.EPException;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.pattern.MatchedEventConvertor;
import com.espertech.esper.pattern.MatchedEventMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Guard implementation that keeps a timer instance and quits when the timer expired,
 * and also keeps a count of the number of matches so far, checking both count and timer,
 * letting all {@link com.espertech.esper.pattern.MatchedEventMap} instances pass until then.
 */
public class ExpressionGuard implements Guard
{
    private static Log log = LogFactory.getLog(ExpressionGuard.class);

    private final Quitable quitable;
    private final MatchedEventConvertor convertor;
    private final ExprEvaluator expression;

    /**
     * Ctor.
     * @param quitable - to use to indicate that the gaurd quitted
     */
    public ExpressionGuard(MatchedEventConvertor convertor, ExprEvaluator expression, Quitable quitable) {
        this.quitable = quitable;
        this.convertor = convertor;
        this.expression = expression;
    }

    public void startGuard() {
    }

    public boolean inspect(MatchedEventMap matchEvent) {
        EventBean[] eventsPerStream = convertor.convert(matchEvent);

        try
        {
            Object result = expression.evaluate(eventsPerStream, true, quitable.getContext());
            if (result == null) {
                return false;
            }

            if (result.equals(Boolean.TRUE)) {
                return true;
            }

            quitable.guardQuit();
            return false;
        }
        catch (RuntimeException ex)
        {
            String message = "Failed to evaluate expression for pattern-guard for statement '" + quitable.getContext().getStatementName() + "'";
            if (ex.getMessage() != null)
            {
                message += ": " + ex.getMessage();
            }
            log.error(message, ex);
            throw new EPException(message);
        }
    }

    public void stopGuard() {
    }
}