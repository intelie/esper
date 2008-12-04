package com.espertech.esper.support.filter;

import com.espertech.esper.filter.FilterService;
import com.espertech.esper.filter.FilterHandle;
import com.espertech.esper.filter.FilterValueSet;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.collection.Pair;

import java.util.List;
import java.util.LinkedList;
import java.util.Collection;

public class SupportFilterServiceImpl implements FilterService
{
    private List<Pair<FilterValueSet, FilterHandle>> added = new LinkedList<Pair<FilterValueSet, FilterHandle>>();
    private List<FilterHandle> removed = new LinkedList<FilterHandle>();

    public void evaluate(EventBean event, Collection<FilterHandle> matchList)
    {
        throw new UnsupportedOperationException();
    }

    public void add(FilterValueSet filterValueSet, FilterHandle callback)
    {
        added.add(new Pair<FilterValueSet, FilterHandle>(filterValueSet, callback));
    }

    public void remove(FilterHandle callback)
    {
        removed.add(callback);
    }

    public long getNumEventsEvaluated()
    {
        throw new UnsupportedOperationException();
    }

    public void resetStats() {
        throw new UnsupportedOperationException();        
    }

    public List<Pair<FilterValueSet, FilterHandle>> getAdded()
    {
        return added;
    }

    public List<FilterHandle> getRemoved()
    {
        return removed;
    }

    public void destroy()
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
