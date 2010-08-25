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
import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.agg.AggregationAccessType;
import com.espertech.esper.epl.agg.AggregationMethodFactory;
import com.espertech.esper.epl.core.MethodResolutionService;
import com.espertech.esper.epl.core.StreamTypeService;

public class ExprAccessAggNode extends ExprAggregateNode
{
    private final AggregationAccessType accessType;
    private final boolean isWildcard;
    private final String streamWildcard;

    /**
     * Ctor.
     */
    public ExprAccessAggNode(AggregationAccessType accessType, boolean wildcard, String streamWildcard)
    {
        super(false);
        this.accessType = accessType;
        this.isWildcard = wildcard;
        this.streamWildcard = streamWildcard;
    }

    public AggregationMethodFactory validateAggregationChild(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService, ExprEvaluatorContext exprEvaluatorContext) throws ExprValidationException
    {
        // TODO find stream nums
        int streamNum = 0;

        Class resultType;
        ExprEvaluator evaluator;
        if (isWildcard) {
            if (streamTypeService.getStreamNames().length > 1) {
                throw new ExprValidationException("Aggregation function wildcard operator is not allowed when multiple streams are joined");
            }
            resultType = streamTypeService.getEventTypes()[0].getUnderlyingType();
            evaluator = new ExprEvaluator() {
                public Object evaluate(EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext context)
                {
                    if ((eventsPerStream == null) || (eventsPerStream[0] == null)) {
                        return null;
                    }
                    return eventsPerStream[0].getUnderlying();
                }
            };
        }
        else if (streamWildcard != null) {
            streamNum = streamTypeService.getStreamNumForStreamName(streamWildcard);
            EventType type = streamTypeService.getEventTypes()[streamNum];
            if (type == null) {
                throw new ExprValidationException("Stream wildcard not found in designated streams");
            }
            resultType = type.getUnderlyingType();
            final int streamNumUsed = streamNum;
            evaluator = new ExprEvaluator() {
                public Object evaluate(EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext context)
                {
                    if ((eventsPerStream == null) || (eventsPerStream[streamNumUsed] == null)) {
                        return null;
                    }
                    return eventsPerStream[streamNumUsed].getUnderlying();
                }
            };
        }
        else {
            resultType = this.getChildNodes().get(0).getType();
            evaluator = this.getChildNodes().get(0);
        }
        return new ExprAccessAggNodeFactory(accessType, resultType, streamNum, evaluator);
    }

    protected String getAggregationFunctionName()
    {
        // TODO
        return "firstg";
    }

    public final boolean equalsNodeAggregate(ExprAggregateNode node)
    {
        if (!(node instanceof ExprAccessAggNode))
        {
            return false;
        }

        ExprAccessAggNode other = (ExprAccessAggNode) node;
        return other.accessType.equals(accessType);
    }
}