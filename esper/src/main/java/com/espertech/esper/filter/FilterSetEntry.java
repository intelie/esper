package com.espertech.esper.filter;

import com.espertech.esper.client.EventType;
import com.espertech.esper.collection.Pair;

import java.util.Map;
import java.util.List;

public class FilterSetEntry
{
    private FilterHandle handle;
    private FilterValueSet filterValueSet;

    public FilterSetEntry(FilterHandle handle, FilterValueSet filterValueSet)
    {
        this.handle = handle;
        this.filterValueSet = filterValueSet;
    }

    public FilterHandle getHandle()
    {
        return handle;
    }

    public FilterValueSet getFilterValueSet()
    {
        return filterValueSet;
    }
}