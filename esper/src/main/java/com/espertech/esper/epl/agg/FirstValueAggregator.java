/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.agg;

import com.espertech.esper.epl.agg.AggregationMethod;
import com.espertech.esper.epl.core.MethodResolutionService;

/**
 * Sum for float values.
 */
public class FirstValueAggregator implements AggregationMethod
{
    private final Class type;
    private boolean isSet;
    private Object firstValue;

    /**
     * Ctor.
     * @param type type of value returned
     */
    public FirstValueAggregator(Class type) {
        this.type = type;
    }

    public void clear()
    {
        firstValue = null;
        isSet = false;
    }

    public void enter(Object object)
    {
        if (!isSet)
        {
            isSet = true;
            firstValue = object;
        }
    }

    public void leave(Object object)
    {
    }

    public Object getValue()
    {
        return firstValue;
    }

    public Class getValueType()
    {
        return type;
    }

    public AggregationMethod newAggregator(MethodResolutionService methodResolutionService)
    {
        return methodResolutionService.makeFirstValueAggregator(type);
    }
}