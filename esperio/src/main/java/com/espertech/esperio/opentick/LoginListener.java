package com.espertech.esperio.opentick;

import com.opentick.OTListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LoginListener implements OTListener
{
    private static final Log log = LogFactory.getLog(LoginListener.class);

    public void dataArrived(Object data)
    {
        log.info("Logged in: " + data);
    }
}
