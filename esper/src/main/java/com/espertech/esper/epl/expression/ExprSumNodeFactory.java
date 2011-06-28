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

public class ExprSumNodeFactory implements AggregationMethodFactory
{
    private final Class resultType;
    private final Class inputValueType;
    private final boolean isDistinct;
    private final boolean hasFilter;

    public ExprSumNodeFactory(MethodResolutionService methodResolutionService, Class inputValueType, boolean isDistinct, boolean hasFilter)
    {
        this.inputValueType = inputValueType;
        this.isDistinct = isDistinct;
        this.resultType = methodResolutionService.getSumAggregatorType(inputValueType);
        this.hasFilter = hasFilter;
    }

    public AggregationSpec getSpec(boolean isMatchRecognize)
    {
        return null;    // default spec
    }

    public Class getResultType()
    {
        return resultType;
    }

    public AggregationMethod getPrototypeAggregator(MethodResolutionService methodResolutionService)
    {
        AggregationMethod method = methodResolutionService.makeSumAggregator(inputValueType, hasFilter);
        if (!isDistinct) {
            return method;
        }
        return methodResolutionService.makeDistinctAggregator(method, inputValueType, hasFilter);
    }

    public AggregationAccessor getAccessor()
    {
        throw new UnsupportedOperationException();
    }
}