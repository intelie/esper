/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.agg;

import com.espertech.esper.epl.core.MethodResolutionService;

/**
 * Sum for integer values.
 */
public class IntegerSumFilterAggregator extends IntegerSumAggregator
{
    @Override
    public void enter(Object parameters)
    {
        Object[] paramArray = (Object[]) parameters;
        if (!AggregatorUtil.checkFilter(paramArray)) {
            return;
        }
        super.enter(paramArray[0]);
    }

    @Override
    public void leave(Object parameters)
    {
        Object[] paramArray = (Object[]) parameters;
        if (!AggregatorUtil.checkFilter(paramArray)) {
            return;
        }
        super.leave(paramArray[0]);
    }

    @Override
    public AggregationMethod newAggregator(MethodResolutionService methodResolutionService)
    {
        return methodResolutionService.makeSumAggregator(Integer.class, true);
    }
}
