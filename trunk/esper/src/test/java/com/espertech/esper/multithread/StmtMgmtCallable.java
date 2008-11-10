package com.espertech.esper.multithread;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.support.util.SupportMTUpdateListener;
import com.espertech.esper.support.util.LogUpdateListener;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.util.ThreadLogUtil;
import com.espertech.esper.event.EventBean;

import java.util.concurrent.Callable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import junit.framework.Assert;
import junit.framework.AssertionFailedError;

public class StmtMgmtCallable implements Callable
{
    private final EPServiceProvider engine;
    private final Object[][] statements;
    private final int numRepeats;

    public StmtMgmtCallable(EPServiceProvider engine, Object[][] statements, int numRepeats)
    {
        this.engine = engine;
        this.statements = statements;
        this.numRepeats = numRepeats;
    }

    public Object call() throws Exception
    {
        try
        {
            for (int loop = 0; loop < numRepeats; loop++)
            {
                for (Object[] statement : statements)
                {
                    boolean isEPL = (Boolean) statement[0];
                    String statementText = (String) statement[1];

                    // Create EPL or pattern statement
                    EPStatement stmt;
                    ThreadLogUtil.trace("stmt create,", statementText);
                    if (isEPL)
                    {
                        stmt = engine.getEPAdministrator().createEPL(statementText);
                    }
                    else
                    {
                        stmt = engine.getEPAdministrator().createPattern(statementText);
                    }
                    ThreadLogUtil.trace("stmt done,", stmt);

                    // Add listener
                    SupportMTUpdateListener listener = new SupportMTUpdateListener();
                    LogUpdateListener logListener;
                    if (isEPL)
                    {
                        logListener = new LogUpdateListener(null);
                    }
                    else
                    {
                        logListener = new LogUpdateListener("a");
                    }
                    ThreadLogUtil.trace("adding listeners ", listener, logListener);
                    stmt.addListener(listener);
                    stmt.addListener(logListener);

                    Object event = makeEvent();
                    ThreadLogUtil.trace("sending event ", event);
                    engine.getEPRuntime().sendEvent(event);

                    // Should have received one or more events, one of them must be mine
                    EventBean[] newEvents = listener.getNewDataListFlattened();
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
                    listener.reset();

                    // Stopping statement, the event should not be received, another event may however
                    ThreadLogUtil.trace("stop statement");
                    stmt.stop();
                    event = makeEvent();
                    ThreadLogUtil.trace("send non-matching event ", event);
                    engine.getEPRuntime().sendEvent(event);

                    // Make sure the event was not received
                    newEvents = listener.getNewDataListFlattened();
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

    private static final Log log = LogFactory.getLog(StmtMgmtCallable.class);
}
