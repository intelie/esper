package net.esper.support.filter;

import net.esper.filter.FilterService;
import net.esper.filter.FilterHandle;
import net.esper.filter.FilterValueSet;
import net.esper.event.EventBean;
import net.esper.collection.Pair;

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

    public int getNumEventsEvaluated()
    {
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
}
