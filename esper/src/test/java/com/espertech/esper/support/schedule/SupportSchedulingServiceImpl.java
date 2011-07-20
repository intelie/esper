/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.support.schedule;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Collection;
import java.util.LinkedList;

import com.espertech.esper.schedule.*;
import com.espertech.esper.core.EPStatementHandleCallback;

public class SupportSchedulingServiceImpl implements SchedulingService
{
    private Map<Long, ScheduleHandle> added = new HashMap<Long, ScheduleHandle>();
    private long currentTime;

    public Map<Long, ScheduleHandle> getAdded()
    {
        return added;
    }

    public void evaluateLock()
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void evaluateUnLock()
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void add(long afterMSec, ScheduleHandle callback, ScheduleSlot slot)
    {
        log.debug(".add Not implemented, afterMSec=" + afterMSec + " callback=" + callback.getClass().getName());
        added.put(afterMSec, callback);
    }

    public void add(ScheduleSpec scheduleSpecification, ScheduleHandle callback, ScheduleSlot slot)
    {
        log.debug(".add Not implemented, scheduleSpecification=" + scheduleSpecification +
                " callback=" + callback.getClass().getName());
    }

    public void remove(ScheduleHandle callback, ScheduleSlot slot)
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

    public void evaluate(Collection<ScheduleHandle> handles)
    {
        log.debug(".evaluate Not implemented");
    }

    public ScheduleBucket allocateBucket()
    {
        return new ScheduleBucket(0);
    }

    public static void evaluateSchedule(SchedulingService service)
    {
        Collection<ScheduleHandle> handles = new LinkedList<ScheduleHandle>();
        service.evaluate(handles);

        for (ScheduleHandle handle : handles)
        {
            if (handle instanceof EPStatementHandleCallback)
            {
                EPStatementHandleCallback callback = (EPStatementHandleCallback) handle;
                callback.getScheduleCallback().scheduledTrigger(null);
            }
            else
            {
                ScheduleHandleCallback cb = (ScheduleHandleCallback) handle;
                cb.scheduledTrigger(null);
            }
        }
    }

    public void destroy()
    {
        ;
    }

    public int getTimeHandleCount() {
        throw new RuntimeException("not implemented");
    }

    public Long getFurthestTimeHandle() {
        throw new RuntimeException("not implemented");
    }

    public int getScheduleHandleCount() {
        throw new RuntimeException("not implemented");
    }

    public boolean isScheduled(ScheduleHandle scheduleHandle)
    {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    private static final Log log = LogFactory.getLog(SupportSchedulingServiceImpl.class);
}
