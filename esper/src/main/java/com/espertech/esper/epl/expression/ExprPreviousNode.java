/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.expression;

import com.espertech.esper.epl.core.StreamTypeService;
import com.espertech.esper.epl.core.MethodResolutionService;
import com.espertech.esper.epl.core.ViewResourceDelegate;
import com.espertech.esper.epl.core.ViewResourceCallback;
import com.espertech.esper.epl.variable.VariableService;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.view.window.RandomAccessByIndex;
import com.espertech.esper.view.window.RelativeAccessByEventNIndex;
import com.espertech.esper.view.window.RandomAccessByIndexGetter;
import com.espertech.esper.view.window.RelativeAccessByEventNIndexMap;
import com.espertech.esper.view.ViewCapDataWindowAccess;
import com.espertech.esper.util.JavaClassHelper;
import com.espertech.esper.schedule.TimeProvider;

import java.util.Collection;
import java.util.Iterator;

/**
 * Represents the 'prev' previous event function in an expression node tree.
 */
public class ExprPreviousNode extends ExprNode implements ViewResourceCallback
{
    private static final long serialVersionUID = 0L;

    private final PreviousType previousType;

    private Class resultType;
    private int streamNumber;
    private Integer constantIndexNumber;
    private boolean isConstantIndex;

    private transient RandomAccessByIndexGetter randomAccessGetter;
    private transient RelativeAccessByEventNIndexMap relativeAccessGetter;

    public ExprPreviousNode(PreviousType previousType)
    {
        this.previousType = previousType;
    }

    public void validate(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService, ViewResourceDelegate viewResourceDelegate, TimeProvider timeProvider, VariableService variableService, ExprEvaluatorContext exprEvaluatorContext) throws ExprValidationException
    {
        if ((this.getChildNodes().size() > 2) || (this.getChildNodes().isEmpty()))
        {
            throw new ExprValidationException("Previous node must have 1 or 2 child nodes");
        }

        // add constant of 1 for previous index
        if (this.getChildNodes().size() == 1)
        {
            if (previousType == PreviousType.PREV) {
                this.getChildNodes().add(0, new ExprConstantNode(1));
            }
            else {
                this.getChildNodes().add(0, new ExprConstantNode(0));
            }
        }

        // the row recognition patterns allows "prev(prop, index)", we switch index the first position
        if (this.getChildNodes().get(1) instanceof ExprConstantNode)
        {
            ExprNode first = this.getChildNodes().get(0);
            ExprNode second = this.getChildNodes().get(1);
            this.getChildNodes().clear();
            this.getChildNodes().add(second);
            this.getChildNodes().add(first);
        }
        
        // Determine if the index is a constant value or an expression to evaluate
        if (this.getChildNodes().get(0).isConstantResult())
        {
            ExprNode constantNode = this.getChildNodes().get(0);
            Object value = constantNode.evaluate(null, false, exprEvaluatorContext);
            if (!(value instanceof Number))
            {
                throw new ExprValidationException("Previous function requires an integer index parameter or expression");
            }

            Number valueNumber = (Number) value;
            if (JavaClassHelper.isFloatingPointNumber(valueNumber))
            {
                throw new ExprValidationException("Previous function requires an integer index parameter or expression");
            }

            constantIndexNumber = valueNumber.intValue();
            isConstantIndex = true;
        }

        // Determine stream number
        if (this.getChildNodes().get(1) instanceof ExprIdentNode) {
            ExprIdentNode identNode = (ExprIdentNode) this.getChildNodes().get(1);
            streamNumber = identNode.getStreamId();
            resultType = this.getChildNodes().get(1).getType();
        }
        else if (this.getChildNodes().get(1) instanceof ExprStreamUnderlyingNode) {
            ExprStreamUnderlyingNode streamNode = (ExprStreamUnderlyingNode) this.getChildNodes().get(1);
            streamNumber = streamNode.getStreamId();
            resultType = this.getChildNodes().get(1).getType();
        }
        else
        {
            throw new ExprValidationException("Previous function requires an event property as parameter");
        }

        if (previousType == PreviousType.COUNT) {
            resultType = Long.class;
        }
        if (previousType == PreviousType.WINDOW) {
            resultType = Object[].class;
        }

        if (viewResourceDelegate == null)
        {
            throw new ExprValidationException("Previous function cannot be used in this context");
        }

        // Request a callback that provides the required access
        if (!viewResourceDelegate.requestCapability(streamNumber, new ViewCapDataWindowAccess(), this))
        {
            throw new ExprValidationException("Previous function requires a single data window view onto the stream");
        }
    }

    public Class getType()
    {
        return resultType;
    }

    public boolean isConstantResult()
    {
        return false;
    }

    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext exprEvaluatorContext)
    {
        if (!isNewData) {
            return null;
        }

        if (previousType == PreviousType.WINDOW) {
            Iterator<EventBean> events;
            int size;
            if (randomAccessGetter != null)
            {
                RandomAccessByIndex randomAccess = randomAccessGetter.getAccessor();
                events = randomAccess.getWindowIterator();
                size = randomAccess.getWindowCount();
            }
            else
            {
                EventBean evalEvent = eventsPerStream[streamNumber];
                RelativeAccessByEventNIndex relativeAccess = relativeAccessGetter.getAccessor(evalEvent);
                size = relativeAccess.getWindowToEventCount(evalEvent);
                events = relativeAccess.getWindowToEvent(evalEvent);
            }

            if (size <= 0) {
                return null;
            }

            EventBean originalEvent = eventsPerStream[streamNumber];
            Object[] result = new Object[size];

            for (int i = 0; i < size; i++) {
                eventsPerStream[streamNumber] = events.next();
                Object evalResult = this.getChildNodes().get(1).evaluate(eventsPerStream, isNewData, exprEvaluatorContext);
                result[i] = evalResult;
            }

            eventsPerStream[streamNumber] = originalEvent;
            return result;
        }

        if (previousType == PreviousType.COUNT) {
            long count;
            if (randomAccessGetter != null)
            {
                RandomAccessByIndex randomAccess = randomAccessGetter.getAccessor();
                count = randomAccess.getWindowCount();
            }
            else
            {
                EventBean evalEvent = eventsPerStream[streamNumber];
                RelativeAccessByEventNIndex relativeAccess = relativeAccessGetter.getAccessor(evalEvent);
                count = relativeAccess.getWindowToEventCount(evalEvent);
            }
            return count;
        }

        // Use constant if supplied
        Integer index;
        if (isConstantIndex)
        {
            index = constantIndexNumber;
        }
        else
        {
            // evaluate first child, which returns the index
            Object indexResult = this.getChildNodes().get(0).evaluate(eventsPerStream, isNewData, exprEvaluatorContext);
            if (indexResult == null)
            {
                return null;
            }
            index = ((Number) indexResult).intValue();
        }

        // TODO - convert to factory implementation

        // access based on index returned
        EventBean substituteEvent = null;
        if (randomAccessGetter != null)
        {
            RandomAccessByIndex randomAccess = randomAccessGetter.getAccessor();
            if (previousType != PreviousType.TAIL) {
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
            if (previousType != PreviousType.TAIL) {
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
        Object evalResult = this.getChildNodes().get(1).evaluate(eventsPerStream, isNewData, exprEvaluatorContext);
        eventsPerStream[streamNumber] = originalEvent;

        return evalResult;
    }

    public String toExpressionString()
    {
        // TODO
        StringBuilder buffer = new StringBuilder();
        buffer.append("prev(");
        buffer.append(this.getChildNodes().get(0).toExpressionString());
        buffer.append(',');
        buffer.append(this.getChildNodes().get(1).toExpressionString());
        buffer.append(')');
        return buffer.toString();
    }

    public boolean equalsNode(ExprNode node)
    {
        // TODO
        if (!(node instanceof ExprPreviousNode))
        {
            return false;
        }

        return true;
    }

    public void setViewResource(Object resource)
    {
        if (resource instanceof RandomAccessByIndexGetter)
        {
            randomAccessGetter = (RandomAccessByIndexGetter) resource;
        }
        else if (resource instanceof RelativeAccessByEventNIndexMap)
        {
            relativeAccessGetter = (RelativeAccessByEventNIndexMap) resource;
        }
        else
        {
            throw new IllegalArgumentException("View resource " + resource.getClass() + " not recognized by expression node");
        }
    }
}
