/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.filter;

/**
 * Filter parameter value defining the event property to filter, the filter operator, and the filter value.
 */
public class FilterValueSetParamImpl implements FilterValueSetParam
{
    private final String propertyName;
    private final FilterOperator filterOperator;
    private final Object filterValue;

    /**
     * Ctor.
     * @param propertyName - property to interrogate
     * @param filterOperator - operator to apply
     * @param filterValue - value to look for
     */
    public FilterValueSetParamImpl(String propertyName, FilterOperator filterOperator, Object filterValue)
    {
        this.propertyName = propertyName;
        this.filterOperator = filterOperator;
        this.filterValue = filterValue;
    }

    public String getPropertyName()
    {
        return propertyName;
    }

    public FilterOperator getFilterOperator()
    {
        return filterOperator;
    }

    public Object getFilterForValue()
    {
        return filterValue;
    }
}
