package com.espertech.esper.example.rfid;

import com.espertech.esper.client.UpdateListener;
import com.espertech.esper.event.EventBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;
import java.util.ArrayList;

public class AssetZoneSplitListener implements UpdateListener
{
    private static final Log log = LogFactory.getLog(AssetZoneSplitListener.class);

    private List<Integer> callbacks = new ArrayList<Integer>();

    public void update(EventBean[] newEvents, EventBean[] oldEvents)
    {
        int groupId = (Integer) newEvents[0].get("a.groupId");
        callbacks.add(groupId);
        log.info(".update Received event from group id " + groupId);
    }

    public List<Integer> getCallbacks()
    {
        return callbacks;
    }

    public void reset()
    {
        callbacks.clear();
    }
}