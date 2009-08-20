package com.espertech.esperio.opentick;

import com.opentick.OTEquityInit;
import com.opentick.OTListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EquityInitListener implements OTListener<OTEquityInit>
{
    private static final Log log = LogFactory.getLog(EquityInitListener.class);

    public void dataArrived(OTEquityInit data)
    {
        log.info("EquityInit: " + data);
    }
}
