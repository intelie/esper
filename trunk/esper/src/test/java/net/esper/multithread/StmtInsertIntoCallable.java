package net.esper.multithread;

import net.esper.client.EPServiceProvider;
import net.esper.client.EPStatement;
import net.esper.support.util.SupportMTUpdateListener;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.util.ThreadLogUtil;
import net.esper.event.EventBean;

import java.util.concurrent.Callable;

import junit.framework.Assert;
import junit.framework.AssertionFailedError;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StmtInsertIntoCallable implements Callable
{
    private final EPServiceProvider engine;
    private final int numRepeats;
    private final String threadKey;

    public StmtInsertIntoCallable(String threadKey, EPServiceProvider engine, int numRepeats)
    {
        this.engine = engine;
        this.numRepeats = numRepeats;
        this.threadKey = threadKey;
    }

    public Object call() throws Exception
    {
        try
        {
            for (int loop = 0; loop < numRepeats; loop++)
            {
                SupportBean eventOne = new SupportBean();
                eventOne.setString("E1_" + threadKey);
                engine.getEPRuntime().sendEvent(eventOne);

                SupportMarketDataBean eventTwo = new SupportMarketDataBean("E2_" + threadKey, 0d, null, null);
                engine.getEPRuntime().sendEvent(eventTwo);
            }
        }
        catch (Exception ex)
        {
            log.fatal("Error in thread " + Thread.currentThread().getId(), ex);
            return false;
        }
        return true;
    }

    private static final Log log = LogFactory.getLog(StmtInsertIntoCallable.class);
}
