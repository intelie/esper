package net.esper.eql.view;

import net.esper.event.EventBean;
import net.esper.view.ViewSupport;

public interface OutputStrategy
{
    public void output(boolean forceUpdate, EventBean[] newEvents, EventBean[] oldEvents, ViewSupport finalView);
}
