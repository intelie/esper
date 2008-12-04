package com.espertech.esper.support.util;

import com.espertech.esper.client.UpdateListener;
import com.espertech.esper.client.EventBean;

public class NoActionUpdateListener implements UpdateListener
{
    public void update(EventBean[] newEvents, EventBean[] oldEvents)
    {
    }
}
