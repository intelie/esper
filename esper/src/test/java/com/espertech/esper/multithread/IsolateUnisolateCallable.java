package com.espertech.esper.multithread;

import com.espertech.esper.client.*;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.util.SupportMTUpdateListener;
import junit.framework.Assert;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.concurrent.Callable;
import java.util.List;

public class IsolateUnisolateCallable implements Callable
{
    private final int threadNum;
    private final EPServiceProvider engine;
    private final int loopCount;

    public IsolateUnisolateCallable(int threadNum, EPServiceProvider engine, int loopCount)
    {
        this.threadNum = threadNum;
        this.engine = engine;
        this.loopCount = loopCount;
    }

    public Object call() throws Exception
    {
        SupportMTUpdateListener listenerIsolated = new SupportMTUpdateListener();
        SupportMTUpdateListener listenerUnisolated = new SupportMTUpdateListener();
        EPStatement stmt = engine.getEPAdministrator().createEPL("select * from SupportBean");

        try
        {
            for (int i = 0; i < loopCount; i++)
            {
                EPServiceProviderIsolated isolated = engine.getEPServiceIsolated("i1");
                isolated.getEPAdministrator().addStatement(stmt);

                listenerIsolated.reset();
                stmt.addListener(listenerIsolated);
                Object event = new SupportBean();
                //System.out.println("Sensing event : " + event + " by thread " + Thread.currentThread().getId());
                isolated.getEPRuntime().sendEvent(event);
                findEvent(listenerIsolated, i, event);
                stmt.removeAllListeners();

                isolated.getEPAdministrator().removeStatement(stmt);

                stmt.addListener(listenerUnisolated);
                event = new SupportBean();
                engine.getEPRuntime().sendEvent(event);
                findEvent(listenerUnisolated, i, event);
                stmt.removeAllListeners();
            }
        }
        catch (Exception ex)
        {
            log.fatal("Error in thread " + threadNum, ex);
            return false;
        }
        return true;
    }

    private void findEvent(SupportMTUpdateListener listener, int loop, Object event)
    {
        String message = "Failed in loop " + loop + " threads " + Thread.currentThread();
        Assert.assertTrue(message, listener.isInvoked());
        List<EventBean[]> eventBeans = listener.getNewDataListCopy();
        boolean found = false;
        for (EventBean[] events : eventBeans)
        {
            Assert.assertEquals(message, 1, events.length);
            if (events[0].getUnderlying() == event)
            {
                found = true;
            }
        }
        Assert.assertTrue(message, found);
        listener.reset();
    }

    private static final Log log = LogFactory.getLog(IsolateUnisolateCallable.class);
}