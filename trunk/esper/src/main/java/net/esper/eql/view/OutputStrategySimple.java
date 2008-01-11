package net.esper.eql.view;

import net.esper.event.EventBean;
import net.esper.view.View;
import net.esper.view.ViewSupport;

public class OutputStrategySimple implements OutputStrategy
{
    public void output(boolean forceUpdate, EventBean[] newEvents, EventBean[] oldEvents, ViewSupport finalView)
    {
        if(newEvents != null || oldEvents != null)
        {
            finalView.updateChildren(newEvents, oldEvents);
        }
        else if(forceUpdate)
        {
            finalView.updateChildren(null, null);
        }
    }
}
