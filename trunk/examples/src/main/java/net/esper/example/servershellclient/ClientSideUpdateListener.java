package net.esper.example.servershellclient;

import net.esper.client.UpdateListener;
import net.esper.event.EventBean;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ClientSideUpdateListener implements Serializable, UpdateListener
{
    private static Log log = LogFactory.getLog(ClientSideUpdateListener.class);

    public void update(EventBean[] newEvents, EventBean[] oldEvents)
    {
        log.info("Single duration over 9.9: IPAddress: " + newEvents[0].get("ipAddress") +
             " Duration: " + newEvents[0].get("duration"));
    }

}