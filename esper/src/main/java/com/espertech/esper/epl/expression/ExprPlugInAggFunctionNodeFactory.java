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

package com.espertech.esper.epl.expression;

import com.espertech.esper.epl.agg.*;
import com.espertech.esper.epl.core.MethodResolutionService;

public class ExprPlugInAggFunctionNodeFactory implements AggregationMethodFactory
{
    private final AggregationSupport aggregationSupport;
    private final boolean distinct;
    private final Class aggregatedValueType;

    public ExprPlugInAggFunctionNodeFactory(AggregationSupport aggregationSupport, boolean distinct, Class aggregatedValueType)
    {
        this.aggregationSupport = aggregationSupport;
        this.distinct = distinct;
        this.aggregatedValueType = aggregatedValueType;
    }

    public Class getResultType()
    {
        return aggregationSupport.getValueType();
    }

    public AggregationSpec getSpec(boolean isMatchRecognize)
    {
        return null;  // defaults apply
    }

    public AggregationMethod getPrototypeAggregator(MethodResolutionService methodResolutionService)
    {
        AggregationMethod method = aggregationSupport;
        if (!distinct) {
            return method;
        }
        return methodResolutionService.makeDistinctAggregator(method, aggregatedValueType,false);
    }

    public AggregationAccessor getAccessor()
    {
        return null;  // no accessor
    }
}
