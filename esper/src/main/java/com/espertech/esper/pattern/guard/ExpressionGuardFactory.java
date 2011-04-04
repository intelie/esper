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
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.pattern.EvalStateNodeNumber;
import com.espertech.esper.pattern.MatchedEventConvertor;
import com.espertech.esper.pattern.MatchedEventMap;
import com.espertech.esper.pattern.PatternContext;
import com.espertech.esper.util.JavaClassHelper;
import com.espertech.esper.util.MetaDefItem;

import java.io.Serializable;
import java.util.List;

/**
 * Factory for {@link com.espertech.esper.pattern.guard.TimerWithinGuard} instances.
 */
public class ExpressionGuardFactory implements GuardFactory, MetaDefItem, Serializable
{
    private static final long serialVersionUID = -5107582730824731419L;

    protected ExprNode expression;

    /**
     * For converting matched-events maps to events-per-stream.
     */
    protected transient MatchedEventConvertor convertor;

    public void setGuardParameters(List<ExprNode> params, MatchedEventConvertor convertor) throws GuardParameterException
    {
        String errorMessage = "Expression pattern guard requires a single expression as a parameter returning a true or false (boolean) value";
        if (params.size() != 1)
        {
            throw new GuardParameterException(errorMessage);
        }
        expression = params.get(0);

        if (JavaClassHelper.getBoxedType(params.get(0).getExprEvaluator().getType()) != Boolean.class)
        {
            throw new GuardParameterException(errorMessage);
        }

        this.convertor = convertor;
    }

    public Guard makeGuard(PatternContext context, MatchedEventMap matchedEventMap, Quitable quitable, EvalStateNodeNumber stateNodeId, Object guardState)
    {
        return new ExpressionGuard(convertor, expression.getExprEvaluator(), quitable);
    }
}