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

package com.espertech.esper.support.timer;

import com.espertech.esper.timer.TimerCallback;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.concurrent.atomic.AtomicInteger;

public class SupportTimerCallback implements TimerCallback
{
    private AtomicInteger numInvoked = new AtomicInteger();

    public void timerCallback()
    {
        int current = numInvoked.incrementAndGet();
        log.debug(".timerCallback numInvoked=" + current + " thread=" + Thread.currentThread());
    }

    public int getCount()
    {
        return numInvoked.get();
    }

    public int getAndResetCount()
    {
        int count = numInvoked.getAndSet(0);
        return count;
    }

    private static final Log log = LogFactory.getLog(SupportTimerCallback.class);
}
