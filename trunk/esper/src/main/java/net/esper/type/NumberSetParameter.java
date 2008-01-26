/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.type;

import net.esper.util.MetaDefItem;

import java.util.Set;
import java.io.StringWriter;

/**
 * Interface to generate a set of integers from parameters that include ranges, lists and frequencies.
 */
public interface NumberSetParameter extends MetaDefItem, EQLParameterType
{
    /**
     * Returns true if all values between and including min and max are supplied by the parameter.
     * @param min - lower end of range
     * @param max - upper end of range
     * @return true if parameter specifies all int values between min and max, false if not
     */
    public boolean isWildcard(int min, int max);

    /**
     * Return a set of int values representing the value of the parameter for the given range.
     * @param min - lower end of range
     * @param max - upper end of range
     * @return set of integer
     */
    public Set<Integer> getValuesInRange(int min, int max);

    public void toEQL(StringWriter writer);
}
