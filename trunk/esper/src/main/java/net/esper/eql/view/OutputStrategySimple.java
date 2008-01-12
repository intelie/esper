package net.esper.eql.view;

import net.esper.event.EventBean;
import net.esper.view.View;

public class OutputStrategySimple implements OutputStrategy
{
    public void output(boolean forceUpdate, EventBean[] newEvents, EventBean[] oldEvents, View finalView)
    {
        if(newEvents != null || oldEvents != null)
        {
            finalView.update(newEvents, oldEvents);
        }
        else if(forceUpdate)
        {
            finalView.update(null, null);
        }
    }
}
