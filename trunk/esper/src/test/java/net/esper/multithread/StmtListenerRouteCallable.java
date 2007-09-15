package net.esper.multithread;

import net.esper.client.EPServiceProvider;
import net.esper.client.EPStatement;
import net.esper.client.UpdateListener;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.util.SupportMTUpdateListener;
import net.esper.event.EventBean;

import java.util.concurrent.Callable;

import junit.framework.AssertionFailedError;
import junit.framework.Assert;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StmtListenerRouteCallable implements Callable
{
    private final int numThread;
    private final EPServiceProvider engine;
    private final EPStatement statement;
    private final int numRepeats;

    public StmtListenerRouteCallable(int numThread, EPServiceProvider engine, EPStatement statement, int numRepeats)
    {
        this.numThread = numThread;
        this.engine = engine;
        this.numRepeats = numRepeats;
        this.statement = statement;
    }

    public Object call() throws Exception
    {
        try
        {
            for (int loop = 0; loop < numRepeats; loop++)
            {
                StmtListenerRouteCallable.MyUpdateListener listener = new StmtListenerRouteCallable.MyUpdateListener(engine, numThread);
                statement.addListener(listener);
                engine.getEPRuntime().sendEvent(new SupportBean());
                statement.removeListener(listener);
                listener.assertCalled();
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

    private class MyUpdateListener implements UpdateListener
    {
        private final EPServiceProvider engine;
        private final int numThread;
        private boolean isCalled;

        public MyUpdateListener(EPServiceProvider engine, int numThread)
        {
            this.engine = engine;
            this.numThread = numThread;
        }

        public void update(EventBean[] newEvents, EventBean[] oldEvents)
        {
            isCalled = true;

            // create statement for thread - this can be called multiple times as other threads send SupportBean
            EPStatement stmt = engine.getEPAdministrator().createEQL(
                    "select * from " + SupportMarketDataBean.class.getName() + " where volume=" + numThread);
            SupportMTUpdateListener listener = new SupportMTUpdateListener();
            stmt.addListener(listener);

            Object event = new SupportMarketDataBean("", 0, (long) numThread, null);
            engine.getEPRuntime().sendEvent(event);
            stmt.stop();

            EventBean[] eventsReceived = listener.getNewDataListFlattened();

            boolean found = false;
            for (int i = 0; i < eventsReceived.length; i++)
            {
                if (eventsReceived[i].getUnderlying() == event)
                {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }

        public void assertCalled()
        {
            Assert.assertTrue(isCalled);
        }
    }

    private static final Log log = LogFactory.getLog(StmtListenerRouteCallable.class);
}
