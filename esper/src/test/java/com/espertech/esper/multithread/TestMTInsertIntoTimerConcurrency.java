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

package com.espertech.esper.multithread;

import junit.framework.TestCase;
import com.espertech.esper.client.*;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.util.NoActionUpdateListener;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestMTInsertIntoTimerConcurrency extends TestCase
{
    private static final Log log = LogFactory.getLog(TestMTInsertIntoTimerConcurrency.class);
    private AtomicLong idCounter;
    private ExecutorService executorService;
    private EPRuntime epRuntime;
    private EPAdministrator epAdministrator;
    private NoActionUpdateListener noActionUpdateListener;

    public void testRun() throws Exception
    {
        idCounter = new AtomicLong(0);
        executorService = Executors.newCachedThreadPool();
        noActionUpdateListener = new NoActionUpdateListener();

        Configuration epConfig = new Configuration();
        epConfig.addEventType(SupportBean.class);
        epConfig.getEngineDefaults().getThreading().setInsertIntoDispatchLocking(ConfigurationEngineDefaults.Threading.Locking.SUSPEND);

        final EPServiceProvider epServiceProvider = EPServiceProviderManager.getDefaultProvider(epConfig);
        epServiceProvider.initialize();
        
        epAdministrator = epServiceProvider.getEPAdministrator();
        epRuntime = epServiceProvider.getEPRuntime();

        epAdministrator.startAllStatements();

        String epl = "insert into Stream1 select count(*) as cnt from SupportBean.win:time(7 sec)";
        createEPL(epl, noActionUpdateListener);
        epl = epl + " output every 10 seconds";
        createEPL(epl, noActionUpdateListener);

        SendEventRunnable sendTickEventRunnable = new SendEventRunnable(10000);
        start(sendTickEventRunnable, 4);

        // Adjust here for long-running test
        Thread.sleep(3000);
        
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.SECONDS);
    }

    private EPStatement createEPL(String epl, UpdateListener updateListener)
    {
        EPStatement statement = epAdministrator.createEPL(epl);
        statement.addListener(updateListener);
        return statement;
    }

    private <T> void start(Callable<T> task, int numInstances)
    {
        for (int i = 0; i < numInstances; i++)
        {
            start(task);
        }
    }

    private <T> Future<T> start(Callable<T> task)
    {
        Future<T> future = executorService.submit(task);
        return future;
    }

    private void sendEvent()
    {
        long id = idCounter.getAndIncrement();
        SupportBean event = new SupportBean();
        event.setLongPrimitive(id);
        epRuntime.sendEvent(event);
    }

    class SendEventRunnable implements Callable<Object>
    {
        private int maxSent;

        public SendEventRunnable(int maxSent)
        {
            this.maxSent = maxSent;
        }

        public Object call() throws Exception
        {
            int count = 0;
            while (true)
            {
                sendEvent();
                Thread.sleep(1);
                count++;

                if (count % 1000 == 0)
                {
                    log.info("Thread " + Thread.currentThread().getId() + " send " + count + " events");
                }

                if (count > maxSent)
                {
                    break;
                }
            }

            return null;
        }
    }
}
