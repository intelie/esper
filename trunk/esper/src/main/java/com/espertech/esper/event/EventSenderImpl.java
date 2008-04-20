package com.espertech.esper.event;

import com.espertech.esper.client.EPException;
import com.espertech.esper.client.EventSender;
import com.espertech.esper.core.EPRuntimeImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

public class EventSenderImpl implements EventSender
{
    private static Log log = LogFactory.getLog(EventSenderImpl.class);
    private final List<EventSenderURIDesc> handlingFactories;
    private final EPRuntimeImpl epRuntime;

    public EventSenderImpl(List<EventSenderURIDesc> handlingFactories, EPRuntimeImpl epRuntime)
    {
        this.handlingFactories = handlingFactories;
        this.epRuntime = epRuntime;
    }

    public void sendEvent(Object event) throws EPException
    {
        // Ask each factory in turn to take care of it
        for (EventSenderURIDesc entry : handlingFactories)
        {
            EventBean eventBean = null;

            try
            {
                eventBean = entry.getBeanFactory().create(event, entry.getResolutionURI());
            }
            catch (RuntimeException ex)
            {
                log.warn("Unexpected exception thrown by plug-in event bean factory '" + entry.getBeanFactory() + "' processing event " + event, ex);
            }

            if (eventBean != null)
            {
                epRuntime.sendEvent(eventBean);
                return;
            }
        }
    }
}
