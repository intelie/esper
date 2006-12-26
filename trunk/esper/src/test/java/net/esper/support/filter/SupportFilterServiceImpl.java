package net.esper.support.filter;

import net.esper.filter.FilterService;
import net.esper.filter.FilterCallback;
import net.esper.filter.FilterValueSet;
import net.esper.event.EventBean;
import net.esper.collection.Pair;

import java.util.List;
import java.util.LinkedList;

public class SupportFilterServiceImpl implements FilterService
{
    private List<Pair<FilterValueSet,FilterCallback>> added = new LinkedList<Pair<FilterValueSet,FilterCallback>>();
    private List<FilterCallback> removed = new LinkedList<FilterCallback>();

    public void evaluate(EventBean event)
    {
        throw new UnsupportedOperationException();
    }

    public void add(FilterValueSet filterValueSet, FilterCallback callback)
    {
        added.add(new Pair<FilterValueSet, FilterCallback>(filterValueSet, callback));
    }

    public void remove(FilterCallback callback)
    {
        removed.add(callback);
    }

    public int getNumEventsEvaluated()
    {
        throw new UnsupportedOperationException();
    }

    public List<Pair<FilterValueSet, FilterCallback>> getAdded()
    {
        return added;
    }

    public List<FilterCallback> getRemoved()
    {
        return removed;
    }
}
