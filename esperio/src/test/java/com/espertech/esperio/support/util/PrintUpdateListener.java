package com.espertech.esperio.support.util;

import com.espertech.esper.client.UpdateListener;
import com.espertech.esper.client.EventBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PrintUpdateListener implements UpdateListener
{
    private static final Log log = LogFactory.getLog(PrintUpdateListener.class);

    public void update(EventBean[] newEvents, EventBean[] oldEvents)
    {
        for (int i = 0; i < newEvents.length; i++)
        {
            log.info(".update Event#" + i + " : " + dumpProperties(newEvents[i]));
        }
    }

    private String dumpProperties(EventBean newEvent)
    {
        StringBuilder buf = new StringBuilder();
        for (String name : newEvent.getEventType().getPropertyNames())
        {
            buf.append(' ');
            buf.append(name);
            buf.append("=");
            buf.append(newEvent.get(name));
        }
        return buf.toString();
    }
}

