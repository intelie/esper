package com.espertech.esperio.opentick;

import com.opentick.OTListener;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPRuntime;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class OTListenerEsperSender implements OTListener
{
    private static final Log log = LogFactory.getLog(OpentickInputAdapter.class);

    private final EPRuntime runtime;

    public OTListenerEsperSender(EPServiceProvider provider)
    {
        this.runtime = provider.getEPRuntime();
    }

    public void dataArrived(Object o)
    {
        if (log.isDebugEnabled())
        {
            log.debug("Sending event " + o);
        }
        runtime.sendEvent(o);
    }
}
