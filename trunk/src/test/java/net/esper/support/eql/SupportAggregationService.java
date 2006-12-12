package net.esper.support.eql;

import net.esper.eql.core.AggregationService;
import net.esper.event.EventBean;
import net.esper.collection.Pair;
import net.esper.collection.MultiKeyUntyped;

import java.util.List;
import java.util.LinkedList;

public class SupportAggregationService implements AggregationService
{
    private List<Pair<EventBean[], MultiKeyUntyped>> leaveList = new LinkedList<Pair<EventBean[], MultiKeyUntyped>>();
    private List<Pair<EventBean[], MultiKeyUntyped>> enterList = new LinkedList<Pair<EventBean[], MultiKeyUntyped>>();

    public void applyLeave(EventBean[] eventsPerStream, MultiKeyUntyped optionalGroupKeyPerRow)
    {
        leaveList.add(new Pair<EventBean[], MultiKeyUntyped>(eventsPerStream, optionalGroupKeyPerRow));
    }

    public void applyEnter(EventBean[] eventsPerStream, MultiKeyUntyped optionalGroupKeyPerRow)
    {
        enterList.add(new Pair<EventBean[], MultiKeyUntyped>(eventsPerStream, optionalGroupKeyPerRow));
    }

    public List<Pair<EventBean[], MultiKeyUntyped>> getLeaveList()
    {
        return leaveList;
    }

    public List<Pair<EventBean[], MultiKeyUntyped>> getEnterList()
    {
        return enterList;
    }

    public void setCurrentRow(MultiKeyUntyped groupKey)
    {
    }

    public Object getValue(int column)
    {
        return null;
    }
}
