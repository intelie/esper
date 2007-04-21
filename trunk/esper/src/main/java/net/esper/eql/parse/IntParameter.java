/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.eql.parse;

import java.util.Set;
import java.util.HashSet;

/**
 * Parameter supplying a single int value is a set of numbers.
 */
public class IntParameter implements NumberSetParameter
{
    private int intValue;

    /**
     * Ctor.
     * @param intValue - single in value
     */
    public IntParameter(int intValue)
    {
        this.intValue = intValue;
    }

    /**
     * Returns int value.
     * @return int value
     */
    public int getIntValue()
    {
        return intValue;
    }

    public boolean isWildcard(int min, int max)
    {
        if ((intValue == min) && (intValue == max))
        {
            return true;
        }
        return false;
    }

    public Set<Integer> getValuesInRange(int min, int max)
    {
        Set<Integer> values = new HashSet<Integer>();

        if ((intValue >= min) && (intValue <= max))
        {
            values.add(intValue);
        }

        return values;
    }
}
