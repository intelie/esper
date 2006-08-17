package net.esper.support;

import net.esper.client.EPServiceProvider;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SendEventRunnable implements Runnable
{
    private Object eventToSend;
    private EPServiceProvider epService;

    public SendEventRunnable(EPServiceProvider epService, Object eventToSend)
    {
        this.epService = epService;
        this.eventToSend = eventToSend;
    }

    public void run()
    {
        try
        {
            epService.getEPRuntime().sendEvent(eventToSend);
        }
        catch (Exception ex)
        {
            log.fatal(ex);
        }
    }

    private static final Log log = LogFactory.getLog(SendEventRunnable.class);
}
