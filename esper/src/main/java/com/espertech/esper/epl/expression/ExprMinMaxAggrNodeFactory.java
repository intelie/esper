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
import com.espertech.esper.type.MinMaxTypeEnum;

public class ExprMinMaxAggrNodeFactory implements AggregationMethodFactory
{
    private final MinMaxTypeEnum minMaxTypeEnum;
    private final Class type;
    private final boolean hasDataWindows;
    private final boolean distinct;
    private final boolean hasFilter;

    public ExprMinMaxAggrNodeFactory(MinMaxTypeEnum minMaxTypeEnum, Class type, boolean hasDataWindows, boolean distinct, boolean hasFilter)
    {
        this.minMaxTypeEnum = minMaxTypeEnum;
        this.type = type;
        this.hasDataWindows = hasDataWindows;
        this.distinct = distinct;
        this.hasFilter = hasFilter;
    }

    public AggregationAccessor getAccessor()
    {
        return null;
    }

    public AggregationMethod getPrototypeAggregator(MethodResolutionService methodResolutionService)
    {
        AggregationMethod method = methodResolutionService.makeMinMaxAggregator(minMaxTypeEnum, type, hasDataWindows, hasFilter);
        if (!distinct) {
            return method;
        }
        return methodResolutionService.makeDistinctAggregator(method, type, hasFilter);
    }

    public AggregationSpec getSpec(boolean isMatchRecognize)
    {
        return null;  // defaults apply
    }

    public Class getResultType()
    {
        return type;
    }
}
