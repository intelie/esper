package com.espertech.esper.support.schedule;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.espertech.esper.schedule.ScheduleHandleCallback;
import com.espertech.esper.schedule.ScheduleHandle;
import com.espertech.esper.core.ExtensionServicesContext;

public class SupportScheduleCallback implements ScheduleHandle, ScheduleHandleCallback 
{
    private static int orderAllCallbacks;

    private int orderTriggered = 0;

    public void scheduledTrigger(ExtensionServicesContext extensionServicesContext)
    {
        log.debug(".scheduledTrigger");
        orderAllCallbacks++;
        orderTriggered = orderAllCallbacks;
    }

    public int clearAndGetOrderTriggered()
    {
        int result = orderTriggered;
        orderTriggered = 0;
        return result;
    }

    public static void setCallbackOrderNum(int orderAllCallbacks) {
        SupportScheduleCallback.orderAllCallbacks = orderAllCallbacks;
    }

    public String getStatementId()
    {
        return null;
    }

    private static final Log log = LogFactory.getLog(SupportScheduleCallback.class);
}
