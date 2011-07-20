/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.filter;

import java.util.List;

/**
 * Holder object for a set of filters for one or more statements.
 */
public class FilterSet
{
    private List<FilterSetEntry> filters;

    /**
     * Ctor.
     * @param filters set of filters
     */
    public FilterSet(List<FilterSetEntry> filters)
    {
        this.filters = filters;
    }

    /**
     * Returns the filters.
     * @return filters
     */
    public List<FilterSetEntry> getFilters()
    {
        return filters;
    }
}
