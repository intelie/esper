/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.eql.parse;

import java.util.List;
import java.util.LinkedList;
import java.util.Set;
import java.util.HashSet;

/**
 * Represents a list of values in a set of numeric parameters.
 */
public class ListParameter implements NumberSetParameter
{
    private List<NumberSetParameter> parameters;

    /**
     * Ctor.
     */
    public ListParameter()
    {
        this.parameters = new LinkedList<NumberSetParameter>();
    }

    /**
     * Add to the list a further parameter.
     * @param numberSetParameter is the parameter to add
     */
    protected void add(NumberSetParameter numberSetParameter)
    {
        parameters.add(numberSetParameter);
    }

    /**
     * Returns list of parameters.
     * @return list of parameters
     */
    protected List<NumberSetParameter> getParameters()
    {
        return parameters;
    }

    public boolean isWildcard(int min, int max)
    {
        for (NumberSetParameter param : parameters)
        {
            if (param.isWildcard(min, max))
            {
                return true;
            }
        }
        return false;
    }

    public Set<Integer> getValuesInRange(int min, int max)
    {
        Set<Integer> result = new HashSet<Integer>();

        for (NumberSetParameter param : parameters)
        {
            result.addAll(param.getValuesInRange(min, max));
        }

        return result;
    }
}