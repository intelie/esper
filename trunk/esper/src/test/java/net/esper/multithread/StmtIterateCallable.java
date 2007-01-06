package net.esper.multithread;

import net.esper.client.EPServiceProvider;
import net.esper.client.EPStatement;
import net.esper.support.bean.SupportBean;
import net.esper.event.EventBean;

import java.util.concurrent.Callable;
import java.util.Iterator;

import junit.framework.Assert;
import junit.framework.AssertionFailedError;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StmtIterateCallable implements Callable
{
    private final int threadNum;
    private final EPServiceProvider engine;
    private final EPStatement stmt;
    private final int numRepeats;

    public StmtIterateCallable(int threadNum, EPServiceProvider engine, EPStatement stmt, int numRepeats)
    {
        this.threadNum = threadNum;
        this.engine = engine;
        this.stmt = stmt;
        this.numRepeats = numRepeats;
    }

    public Object call() throws Exception
    {
        try
        {
            for (int loop = 0; loop < numRepeats; loop++)
            {
                String id = Long.toString(threadNum * 100000000 + loop);
                SupportBean bean = new SupportBean(id, 0);
                engine.getEPRuntime().sendEvent(bean);

                Iterator<EventBean> it = stmt.iterator();
                boolean found = false;
                for (;it.hasNext();)
                {
                    EventBean event = it.next();
                    if (event.get("string").equals(id))
                    {
                        found = true;
                    }
                }
                Assert.assertTrue(found);
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

    private static final Log log = LogFactory.getLog(StmtIterateCallable.class);
}
