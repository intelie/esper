package com.espertech.esper.support.util;

import com.espertech.esper.client.UpdateListener;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.util.ThreadLogUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PrintUpdateListener implements UpdateListener
{
    private static final Log log = LogFactory.getLog(PrintUpdateListener.class);

    public void update(EventBean[] newEvents, EventBean[] oldEvents)
    {
        for (int i = 0; i < newEvents.length; i++)
        {
            log.debug(".update Event#" + i + " : " + newEvents[i]);
            dumpProperties(newEvents[i]);
        }
    }

    private void dumpProperties(EventBean newEvent)
    {
        for (String name : newEvent.getEventType().getPropertyNames())
        {            
            log.debug(" " + name + " = " + newEvent.get(name));
        }

    }
}
