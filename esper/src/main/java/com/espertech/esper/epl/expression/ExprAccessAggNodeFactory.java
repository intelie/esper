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
import com.espertech.esper.epl.agg.*;
import com.espertech.esper.epl.core.MethodResolutionService;

public class ExprAccessAggNodeFactory implements AggregationMethodFactory
{
    private final AggregationAccessType accessType;
    private final Class resultType;
    private final int streamNum;
    private final ExprEvaluator childNode;

    public ExprAccessAggNodeFactory(AggregationAccessType accessType, Class resultType, int streamNum, ExprEvaluator childNode)
    {
        this.accessType = accessType;
        this.resultType = resultType;
        this.streamNum = streamNum;
        this.childNode = childNode;
    }

    public Class getResultType()
    {
        return resultType;
    }

    public AggregationSpec getSpec()
    {
        return new AggregationSpec(streamNum);
    }

    public AggregationMethod getPrototypeAggregator(MethodResolutionService methodResolutionService)
    {
        throw new UnsupportedOperationException();
    }

    public AggregationAccessor getAccessor()
    {
        if (accessType == AggregationAccessType.FIRST) {
            return new AggregationAccessorFirst(streamNum, childNode);
        }
        else if (accessType == AggregationAccessType.LAST) {
            return new AggregationAccessorLast(streamNum, childNode);
        }
        else if (accessType == AggregationAccessType.ALL) {
            return new AggregationAccessorAll(streamNum, childNode);
        }
        throw new IllegalStateException("Access type is undefined or not known as code '" + accessType + "'");
    }
}