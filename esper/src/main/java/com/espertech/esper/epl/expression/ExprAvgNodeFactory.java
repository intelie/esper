/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.expression;

import com.espertech.esper.epl.agg.*;
import com.espertech.esper.epl.core.MethodResolutionService;

public class ExprAvgNodeFactory implements AggregationMethodFactory
{
    private final Class childType;
    private final Class resultType;
    private final boolean isDistinct;
    private final boolean hasFilter;

    public ExprAvgNodeFactory(Class childType, boolean isDistinct, MethodResolutionService methodResolutionService, boolean hasFilter)
    {
        this.childType = childType;
        this.isDistinct = isDistinct;
        this.resultType = methodResolutionService.getAvgAggregatorType(childType);
        this.hasFilter = hasFilter;
    }

    public Class getResultType()
    {
        return resultType;
    }

    public AggregationSpec getSpec(boolean isMatchRecognize)
    {
        return null;
    }

    public AggregationMethod getPrototypeAggregator(MethodResolutionService methodResolutionService)
    {
        AggregationMethod method = methodResolutionService.makeAvgAggregator(childType, hasFilter);
        if (!isDistinct) {
            return method;
        }
        return methodResolutionService.makeDistinctAggregator(method, childType, hasFilter);
    }

    public AggregationAccessor getAccessor()
    {
        throw new UnsupportedOperationException();
    }
}