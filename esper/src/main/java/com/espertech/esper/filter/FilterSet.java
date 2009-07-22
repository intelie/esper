package com.espertech.esper.filter;

import java.util.List;

public class FilterSet
{
    private List<FilterSetEntry> filters;

    public FilterSet(List<FilterSetEntry> filters)
    {
        this.filters = filters;
    }

    public List<FilterSetEntry> getFilters()
    {
        return filters;
    }
}
