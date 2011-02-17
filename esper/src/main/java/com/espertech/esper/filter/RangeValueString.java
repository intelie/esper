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
 * A String-typed value as a filter parameter representing a range.
 */
public class RangeValueString implements FilterSpecParamRangeValue
{
    private final String string;

    /**
     * Ctor.
     * @param string is the value of the range endpoint
     */
    public RangeValueString(String string)
    {
        this.string = string;
    }

    public final String getFilterValue(MatchedEventMap matchedEvents)
    {
        return string;
    }

    public int getFilterHash()
    {
        return string.hashCode();
    }

    public final String toString()
    {
        return string;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RangeValueString that = (RangeValueString) o;

        if (string != null ? !string.equals(that.string) : that.string != null) return false;

        return true;
    }

    public int hashCode() {
        return string != null ? string.hashCode() : 0;
    }
}
