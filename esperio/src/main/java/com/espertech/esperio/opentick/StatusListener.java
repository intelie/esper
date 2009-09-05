package com.espertech.esperio.opentick;

import com.opentick.OTListener;
import com.opentick.OTConstants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StatusListener implements OTListener<Integer>
{
    private static final Log log = LogFactory.getLog(StatusListener.class);

    public void dataArrived(Integer data)
    {
        String status = data.toString();
        if (data == OTConstants.OT_STATUS_INACTIVE)
        {
            status = "unactive";
        }
        else if (data == OTConstants.OT_STATUS_CONNECTING)
        {
            status = "connecting";
        }
        else if (data == OTConstants.OT_STATUS_CONNECTED)
        {
            status = "connected";
        }
        else if (data == OTConstants.OT_STATUS_LOGGED_IN)
        {
            status = "logged in";
        }

        log.info("Status changed: new status is " + status + " (" + data + ")");
    }
}
