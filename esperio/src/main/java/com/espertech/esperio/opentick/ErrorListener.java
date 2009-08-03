package com.espertech.esperio.opentick;

import com.opentick.OTError;
import com.opentick.OTListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ErrorListener implements OTListener<OTError>
{
    private static final Log log = LogFactory.getLog(ErrorListener.class);

    public void dataArrived(OTError data)
    {
        log.error("Error reported: " + data);
    }
}
