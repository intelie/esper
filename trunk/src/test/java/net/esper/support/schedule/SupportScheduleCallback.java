package net.esper.support.schedule;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import net.esper.schedule.ScheduleCallback;

public class SupportScheduleCallback implements ScheduleCallback
{
    int numTriggered = 0;

    public void scheduledTrigger()
    {
        log.debug(".scheduledTrigger");
        numTriggered++;
    }

    public int clearAndGetCount()
    {
        int result = numTriggered;
        numTriggered = 0;
        return result;
    }

    private static final Log log = LogFactory.getLog(SupportScheduleCallback.class);
}
