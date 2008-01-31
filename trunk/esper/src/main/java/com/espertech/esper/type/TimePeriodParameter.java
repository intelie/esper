/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.type;

import com.espertech.esper.util.MetaDefItem;

import java.io.StringWriter;

/**
 * Parameter for views that accept time period information such as "5 sec 100 msec".
 */
public class TimePeriodParameter implements MetaDefItem, EQLParameterType
{
    private double numSeconds;

    /**
     * Ctor.
     * @param numSeconds is the number of seconds represented by time period string
     */
    public TimePeriodParameter(double numSeconds)
    {
        this.numSeconds = numSeconds;
    }

    /**
     * Returns the number of seconds.
     * @return the number of seconds specified by time period string
     */
    public double getNumSeconds()
    {
        return numSeconds;
    }

    public boolean equals(Object object)
    {
        if (!(object instanceof TimePeriodParameter))
        {
            return false;
        }
        TimePeriodParameter other = (TimePeriodParameter) object;
        return other.numSeconds == this.numSeconds;
    }

    public int hashCode()
    {
        long temp = numSeconds != +0.0d ? Double.doubleToLongBits(numSeconds) : 0L;
        return (int) (temp ^ (temp >>> 32));
    }

    public void toEQL(StringWriter writer)
    {
        writer.write(Double.toString(numSeconds));
        writer.write(" sec");
    }
}
