package net.esper.support.eql;

import net.esper.eql.core.AggregationService;
import net.esper.event.EventBean;
import net.esper.collection.MultiKey;
import net.esper.collection.Pair;

import java.util.List;
import java.util.LinkedList;

public class SupportAggregationService implements AggregationService
{
    private List<Pair<EventBean[], MultiKey>> leaveList = new LinkedList<Pair<EventBean[], MultiKey>>();
    private List<Pair<EventBean[], MultiKey>> enterList = new LinkedList<Pair<EventBean[], MultiKey>>();

    public void applyLeave(EventBean[] eventsPerStream, MultiKey optionalGroupKeyPerRow)
    {
        leaveList.add(new Pair<EventBean[], MultiKey>(eventsPerStream, optionalGroupKeyPerRow));
    }

    public void applyEnter(EventBean[] eventsPerStream, MultiKey optionalGroupKeyPerRow)
    {
        enterList.add(new Pair<EventBean[], MultiKey>(eventsPerStream, optionalGroupKeyPerRow));
    }

    public List<Pair<EventBean[], MultiKey>> getLeaveList()
    {
        return leaveList;
    }

    public List<Pair<EventBean[], MultiKey>> getEnterList()
    {
        return enterList;
    }

    public void setCurrentRow(MultiKey groupKey)
    {
    }

    public Object getValue(int column)
    {
        return null;
    }

}
