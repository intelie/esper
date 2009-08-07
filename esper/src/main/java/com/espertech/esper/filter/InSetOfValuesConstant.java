/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.filter;

import com.espertech.esper.pattern.MatchedEventMap;

/**
 * Constant value in a list of values following an in-keyword.
 */
public class InSetOfValuesConstant implements FilterSpecParamInValue
{
    private Object constant;
    private static final long serialVersionUID = 575037486475447197L;

    /**
     * Ctor.
     * @param constant is the constant value
     */
    public InSetOfValuesConstant(Object constant)
    {
        this.constant = constant;
    }

    public final Object getFilterValue(MatchedEventMap matchedEvents)
    {
        return constant;
    }

    /**
     * Returns the constant value.
     * @return constant
     */
    public Object getConstant()
    {
        return constant;
    }

    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        InSetOfValuesConstant that = (InSetOfValuesConstant) o;

        if (constant != null ? !constant.equals(that.constant) : that.constant != null)
        {
            return false;
        }

        return true;
    }

    public int hashCode()
    {
        return constant != null ? constant.hashCode() : 0;
    }
}
