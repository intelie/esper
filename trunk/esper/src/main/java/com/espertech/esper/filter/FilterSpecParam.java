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
import com.espertech.esper.util.MetaDefItem;

/**
 * This class represents one filter parameter in an {@link FilterSpecCompiled} filter specification.
 * <p> Each filerting parameter has an attribute name and operator type.
 */
public abstract class FilterSpecParam implements MetaDefItem
{
    /**
     * The property name of the filter parameter.
     */
    protected final String propertyName;
    
    private final FilterOperator filterOperator;

    FilterSpecParam(String propertyName, FilterOperator filterOperator)
    {
        this.propertyName = propertyName;
        this.filterOperator = filterOperator;
    }

    /**
     * Return the filter parameter constant to filter for.
     * @param matchedEvents is the prior results that can be used to determine filter parameters
     * @return filter parameter constant's value
     */
    public abstract Object getFilterValue(MatchedEventMap matchedEvents);

    /**
     * Returns the property name for the filter parameter.
     * @return property name
     */
    public String getPropertyName()
    {
        return propertyName;
    }

    /**
     * Returns the filter operator type.
     * @return filter operator type
     */
    public FilterOperator getFilterOperator()
    {
        return filterOperator;
    }


    public String toString()
    {
        return "FilterSpecParam" +
               " property=" + propertyName +
               " filterOp=" + filterOperator;
    }

    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }

        if (!(obj instanceof FilterSpecParam))
        {
            return false;
        }

        FilterSpecParam other = (FilterSpecParam) obj;

        if (!(this.propertyName.equals(other.propertyName)))
        {
            return false;
        }
        if (this.filterOperator != other.filterOperator)
        {
            return false;
        }
        return true;
    }

    public int hashCode()
    {
        int result;
        result = propertyName.hashCode();
        result = 31 * result + filterOperator.hashCode();
        return result;
    }
}
