package net.esper.support.schedule;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;

import net.esper.schedule.SchedulingService;
import net.esper.schedule.ScheduleCallback;
import net.esper.schedule.ScheduleSpec;

public class SupportSchedulingServiceImpl implements SchedulingService
{
    private Map<Long, ScheduleCallback> added = new HashMap<Long, ScheduleCallback>();
    private long currentTime;

    public Map<Long, ScheduleCallback> getAdded()
    {
        return added;
    }

    public void add(long afterMSec, ScheduleCallback callback)
    {
        log.debug(".add Not implemented, afterMSec=" + afterMSec + " callback=" + callback.getClass().getName());
        added.put(afterMSec, callback);
    }

    public void add(ScheduleSpec scheduleSpecification, ScheduleCallback callback)
    {
        log.debug(".add Not implemented, scheduleSpecification=" + scheduleSpecification +
                " callback=" + callback.getClass().getName());
    }

    public void remove(ScheduleCallback callback)
    {
        log.debug(".remove Not implemented, callback=" + callback.getClass().getName());
    }

    public long getTime()
    {
        log.debug(".getTime Time is " + currentTime);
        return this.currentTime;
    }

    public void setTime(long currentTime)
    {
        log.debug(".setTime Setting new time, currentTime=" + currentTime);
        this.currentTime = currentTime;
    }

    public void evaluate()
    {
        log.debug(".evaluate Not implemented");
    }

    private static final Log log = LogFactory.getLog(SupportSchedulingServiceImpl.class);
}
