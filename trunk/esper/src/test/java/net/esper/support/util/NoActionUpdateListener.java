package net.esper.support.util;

import net.esper.client.UpdateListener;
import net.esper.event.EventBean;
import net.esper.util.ThreadLogUtil;

public class NoActionUpdateListener implements UpdateListener
{
    public void update(EventBean[] newEvents, EventBean[] oldEvents)
    {
    }
}
