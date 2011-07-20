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

import com.espertech.esper.client.EPException;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.pattern.*;
import com.espertech.esper.pattern.guard.Guard;
import com.espertech.esper.pattern.guard.GuardFactorySupport;
import com.espertech.esper.pattern.guard.GuardParameterException;
import com.espertech.esper.pattern.guard.Quitable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

public class MyCountToPatternGuardFactory extends GuardFactorySupport
{
    private static final Log log = LogFactory.getLog(MyCountToPatternGuardFactory.class);

    private ExprNode numCountToExpr;
    private MatchedEventConvertor convertor;

    public void setGuardParameters(List<ExprNode> guardParameters, MatchedEventConvertor convertor) throws GuardParameterException
    {
        String message = "Count-to guard takes a single integer-value expression as parameter";
        if (guardParameters.size() != 1)
        {
            throw new GuardParameterException(message);
        }

        if (guardParameters.get(0).getExprEvaluator().getType() != Integer.class)
        {
            throw new GuardParameterException(message);
        }

        this.numCountToExpr = guardParameters.get(0);
        this.convertor = convertor;
    }

    public Guard makeGuard(PatternContext context, MatchedEventMap beginState, Quitable quitable, EvalStateNodeNumber stateNodeId, Object guardState)
    {
        Object parameter = PatternExpressionUtil.evaluate("Count-to guard", beginState, numCountToExpr, convertor, null);
        if (parameter == null)
        {
            throw new EPException("Count-to guard parameter evaluated to a null value");
        }

        Integer numCountTo = (Integer) parameter;
        return new MyCountToPatternGuard(numCountTo, quitable);
    }
}
