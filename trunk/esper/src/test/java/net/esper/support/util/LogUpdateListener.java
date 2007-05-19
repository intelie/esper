package net.esper.support.util;

import net.esper.client.UpdateListener;
import net.esper.event.EventBean;
import net.esper.util.ThreadLogUtil;

public class LogUpdateListener implements UpdateListener
{
    private String fieldNameLogged;

    public LogUpdateListener(String fieldNameLogged)
    {
        this.fieldNameLogged = fieldNameLogged;
    }

    public void update(EventBean[] newEvents, EventBean[] oldEvents)
    {
        EventBean event = newEvents[0];
        if (fieldNameLogged == null)
        {
            ThreadLogUtil.trace("listener received, " + " listener=" + this + " eventUnderlying=" + Integer.toHexString(event.getUnderlying().hashCode()));
        }
        else
        {
            ThreadLogUtil.trace("listener received, " + " listener=" + this + " eventUnderlying=" + Integer.toHexString(event.get("a").hashCode()));
        }
    }
}
