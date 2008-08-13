package com.espertech.esperio.opentick;

import com.opentick.OTEventBasedClient;
import com.opentick.OTListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EsperOpenTickClient extends OTEventBasedClient implements OTListener
{
    private static final Log log = LogFactory.getLog(EsperOpenTickClient.class);


    public void dataArrived(Object o)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
