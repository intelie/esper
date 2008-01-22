package net.esper.eql.view;

import net.esper.event.EventBean;
import net.esper.view.View;
import net.esper.collection.Pair;
import net.esper.core.UpdateDispatchView;

public class OutputStrategySimple implements OutputStrategy
{
    public void output(boolean forceUpdate, Pair<EventBean[], EventBean[]> result, UpdateDispatchView finalView)
    {
        EventBean[] newEvents = result != null ? result.getFirst() : null;
        EventBean[] oldEvents = result != null ? result.getSecond() : null;
        if(newEvents != null || oldEvents != null)
        {
            finalView.newResult(result);
        }
        else if(forceUpdate)
        {
            finalView.newResult(result);
        }
    }
}
