package net.esper.support.schedule;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import net.esper.schedule.ScheduleCallback;

public class SupportScheduleCallback implements ScheduleCallback
{
    private static int orderAllCallbacks;

    private int orderTriggered = 0;

    public void scheduledTrigger()
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

    private static final Log log = LogFactory.getLog(SupportScheduleCallback.class);
}
