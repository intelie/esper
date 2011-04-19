package com.espertech.esper.multithread;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.util.ThreadLogUtil;
import com.espertech.esper.support.util.SupportMTUpdateListener;
import com.espertech.esper.support.util.LogUpdateListener;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.client.EventBean;

import java.util.concurrent.Callable;

import junit.framework.Assert;
import junit.framework.AssertionFailedError;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StmtListenerAddRemoveCallable implements Callable
{
    private final EPServiceProvider engine;
    private final EPStatement stmt;
    private final boolean isEPL;
    private final int numRepeats;

    public StmtListenerAddRemoveCallable(EPServiceProvider engine, EPStatement stmt, boolean isEPL, int numRepeats)
    {
        this.engine = engine;
        this.stmt = stmt;
        this.isEPL = isEPL;
        this.numRepeats = numRepeats;
    }

    public Object call() throws Exception
    {
        try
        {
            for (int loop = 0; loop < numRepeats; loop++)
            {
                // Add assertListener
                SupportMTUpdateListener assertListener = new SupportMTUpdateListener();
                LogUpdateListener logListener;
                if (isEPL)
                {
                    logListener = new LogUpdateListener(null);
                }
                else
                {
                    logListener = new LogUpdateListener("a");
                }
                ThreadLogUtil.trace("adding listeners ", assertListener, logListener);
                stmt.addListener(assertListener);
                stmt.addListener(logListener);

                // send event
                Object event = makeEvent();
                ThreadLogUtil.trace("sending event ", event);
                engine.getEPRuntime().sendEvent(event);

                // Should have received one or more events, one of them must be mine
                EventBean[] newEvents = assertListener.getNewDataListFlattened();
                Assert.assertTrue("No event received", newEvents.length >= 1);
                ThreadLogUtil.trace("assert received, size is", newEvents.length);
                boolean found = false;
                for (int i = 0; i < newEvents.length; i++)
                {
                    Object underlying = newEvents[i].getUnderlying();
                    if (!isEPL)
                    {
                        underlying = newEvents[i].get("a");
                    }
                    if (underlying == event)
                    {
                        found = true;
                    }
                }
                Assert.assertTrue(found);
                assertListener.reset();

                // Remove assertListener
                ThreadLogUtil.trace("removing assertListener");
                stmt.removeListener(assertListener);
                stmt.removeListener(logListener);

                // Send another event
                event = makeEvent();
                ThreadLogUtil.trace("send non-matching event ", event);
                engine.getEPRuntime().sendEvent(event);

                // Make sure the event was not received
                newEvents = assertListener.getNewDataListFlattened();
                found = false;
                for (int i = 0; i < newEvents.length; i++)
                {
                    Object underlying = newEvents[i].getUnderlying();
                    if (!isEPL)
                    {
                        underlying = newEvents[i].get("a");
                    }
                    if (underlying == event)
                    {
                        found = true;
                    }
                }
                Assert.assertFalse(found);
            }
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

    private SupportMarketDataBean makeEvent()
    {
        SupportMarketDataBean event = new SupportMarketDataBean("IBM", 50, 1000L, "RT");
        return event;
    }

    private static final Log log = LogFactory.getLog(StmtListenerAddRemoveCallable.class);
}
