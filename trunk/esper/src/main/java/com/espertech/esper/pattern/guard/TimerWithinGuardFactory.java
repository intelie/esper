/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.pattern.guard;

import com.espertech.esper.type.TimePeriodParameter;
import com.espertech.esper.pattern.PatternContext;
import com.espertech.esper.util.JavaClassHelper;
import com.espertech.esper.util.MetaDefItem;

import java.util.List;

/**
 * Factory for {@link TimerWithinGuard} instances.
 */
public class TimerWithinGuardFactory implements GuardFactory, MetaDefItem
{
    /**
     * Number of milliseconds.
     */
    protected long milliseconds;

    public void setGuardParameters(List<Object> guardParameters) throws GuardParameterException
    {
        String errorMessage = "Timer-within guard requires a single numeric or time period parameter";
        if (guardParameters.size() != 1)
        {
            throw new GuardParameterException(errorMessage);
        }

        Object parameter = guardParameters.get(0);
        if (parameter instanceof TimePeriodParameter)
        {
            TimePeriodParameter param = (TimePeriodParameter) parameter;
            milliseconds = Math.round(1000d * param.getNumSeconds());
        }
        else if (!(parameter instanceof Number))
        {
            throw new GuardParameterException(errorMessage);
        }
        else
        {
            Number param = (Number) parameter;
            if (JavaClassHelper.isFloatingPointNumber(param))
            {
                milliseconds = Math.round(1000d * param.doubleValue());
            }
            else
            {
                milliseconds = 1000 * param.longValue();
            }
        }
    }

    public Guard makeGuard(PatternContext context, Quitable quitable, Object stateNodeId, Object guardState)
    {
        return new TimerWithinGuard(milliseconds, context, quitable);
    }
}
