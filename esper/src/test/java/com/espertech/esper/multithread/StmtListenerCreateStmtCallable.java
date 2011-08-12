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

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.UpdateListener;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.bean.SupportBean;

import java.util.concurrent.Callable;
import java.util.Set;

import junit.framework.AssertionFailedError;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StmtListenerCreateStmtCallable implements Callable
{
    private final int numThread;
    private final EPServiceProvider engine;
    private final EPStatement statement;
    private final int numRoutes;
    private final Set<SupportMarketDataBean> routed;

    public StmtListenerCreateStmtCallable(int numThread, EPServiceProvider engine, EPStatement statement, int numRoutes,
                                          Set<SupportMarketDataBean> routed)
    {
        this.numThread = numThread;
        this.engine = engine;
        this.numRoutes = numRoutes;
        this.statement = statement;
        this.routed = routed;
    }

    public Object call() throws Exception
    {
        try
        {
            // add listener to triggering statement
            MyUpdateListener listener = new MyUpdateListener(engine, numRoutes, routed);
            statement.addListener(listener);
            Thread.sleep(100);      // wait to send trigger event, other threads receive all other's events

            engine.getEPRuntime().sendEvent(new SupportBean());            

        }
        catch (AssertionFailedError ex)
        {
            log.fatal("Assertion error in thread " + Thread.currentThread().getId(), ex);
            return false;
        }
        catch (Exception ex)
        {
            log.fatal("Error in thread " + Thread.currentThread().getId(), ex);
            return false;
        }
        return true;
    }

    private class MyUpdateListener implements UpdateListener
    {
        private final EPServiceProvider engine;
        private final int numRepeats;
        private final Set<SupportMarketDataBean> routed;

        public MyUpdateListener(EPServiceProvider engine, int numRepeats, Set<SupportMarketDataBean> routed)
        {
            this.engine = engine;
            this.numRepeats = numRepeats;
            this.routed = routed;
        }

        public void update(EventBean[] newEvents, EventBean[] oldEvents)
        {
            for (int i = 0; i < numRepeats; i++)
            {
                SupportMarketDataBean event = new SupportMarketDataBean("", 0, (long) numThread, null);
                engine.getEPRuntime().route(event);
                routed.add(event);
            }
        }
    }

    private static final Log log = LogFactory.getLog(StmtListenerCreateStmtCallable.class);
}
