/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.expression;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.view.window.RandomAccessByIndex;
import com.espertech.esper.view.window.RandomAccessByIndexGetter;
import com.espertech.esper.view.window.RelativeAccessByEventNIndex;
import com.espertech.esper.view.window.RelativeAccessByEventNIndexMap;

import java.lang.reflect.Array;
import java.util.Iterator;

public class ExprPreviousEvalPrev implements ExprPreviousEval
{
    private final int streamNumber;
    private final ExprNode indexNode;
    private final ExprNode evalNode;
    private final RandomAccessByIndexGetter randomAccessGetter;
    private final RelativeAccessByEventNIndexMap relativeAccessGetter;
    private final boolean isConstantIndex;
    private final Integer constantIndexNumber;
    private final boolean isTail;

    public ExprPreviousEvalPrev(int streamNumber, ExprNode indexNode, ExprNode evalNode, RandomAccessByIndexGetter randomAccessGetter, RelativeAccessByEventNIndexMap relativeAccessGetter, boolean constantIndex, Integer constantIndexNumber, boolean tail)
    {
        this.streamNumber = streamNumber;
        this.indexNode = indexNode;
        this.evalNode = evalNode;
        this.randomAccessGetter = randomAccessGetter;
        this.relativeAccessGetter = relativeAccessGetter;
        isConstantIndex = constantIndex;
        this.constantIndexNumber = constantIndexNumber;
        isTail = tail;
    }

    public Object evaluate(EventBean[] eventsPerStream, ExprEvaluatorContext exprEvaluatorContext)
    {
        // Use constant if supplied
        Integer index;
        if (isConstantIndex)
        {
            index = constantIndexNumber;
        }
        else
        {
            // evaluate first child, which returns the index
            Object indexResult = indexNode.evaluate(eventsPerStream, true, exprEvaluatorContext);
            if (indexResult == null)
            {
                return null;
            }
            index = ((Number) indexResult).intValue();
        }

        // access based on index returned
        EventBean substituteEvent = null;
        if (randomAccessGetter != null)
        {
            RandomAccessByIndex randomAccess = randomAccessGetter.getAccessor();
            if (!isTail) {
                substituteEvent = randomAccess.getNewData(index);
            }
            else {
                substituteEvent = randomAccess.getNewDataTail(index);
            }
        }
        else
        {
            EventBean evalEvent = eventsPerStream[streamNumber];
            RelativeAccessByEventNIndex relativeAccess = relativeAccessGetter.getAccessor(evalEvent);
            if (!isTail) {
                substituteEvent = relativeAccess.getRelativeToEvent(evalEvent, index);
            }
            else {
                substituteEvent = relativeAccess.getRelativeToEnd(evalEvent, index);
            }
        }
        if (substituteEvent == null)
        {
            return null;
        }

        // Substitute original event with prior event, evaluate inner expression
        EventBean originalEvent = eventsPerStream[streamNumber];
        eventsPerStream[streamNumber] = substituteEvent;
        Object evalResult = evalNode.evaluate(eventsPerStream, true, exprEvaluatorContext);
        eventsPerStream[streamNumber] = originalEvent;

        return evalResult;
    }
}