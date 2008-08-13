package com.espertech.esperio.opentick;

import com.opentick.OTListener;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPRuntime;

public class OTListenerEsperSender implements OTListener
{
    private final EPRuntime runtime;

    public OTListenerEsperSender(EPServiceProvider provider)
    {
        this.runtime = provider.getEPRuntime();
    }

    public void dataArrived(Object o)
    {
        runtime.sendEvent(o);
    }
}
