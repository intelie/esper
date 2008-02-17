package com.espertech.esper.example.rfid;

import com.espertech.esper.client.UpdateListener;
import com.espertech.esper.event.EventBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AssetGroupCountListener implements UpdateListener
{
    private static final Log log = LogFactory.getLog(AssetGroupCountListener.class);

    public void update(EventBean[] newEvents, EventBean[] oldEvents)
    {
        int groupId = (Integer) newEvents[0].get("groupId");
        int zone = (Integer) newEvents[0].get("zone");
        long cnt = (Long) newEvents[0].get("cnt");

        log.info(".update " +
                " groupId=" + groupId +
                " zone=" + zone +
                " cnt=" + cnt);
    }
}
