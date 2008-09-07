package com.espertech.esper.multithread;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.core.EPQueryResult;
import com.espertech.esper.core.EPRuntimeSPI;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import junit.framework.Assert;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.concurrent.Callable;

public class StmtNamedWindowQueryCallable implements Callable
{
    private final EPRuntimeSPI engine;
    private final int numRepeats;
    private final String threadKey;

    public StmtNamedWindowQueryCallable(String threadKey, EPServiceProvider engine, int numRepeats)
    {
        this.engine = (EPRuntimeSPI) engine.getEPRuntime();
        this.numRepeats = numRepeats;
        this.threadKey = threadKey;
    }

    public Object call() throws Exception
    {
        try
        {
            long total = 0;
            for (int loop = 0; loop < numRepeats; loop++)
            {
                // Insert event into named window
                sendMarketBean(threadKey, loop);
                total++;

                String selectQuery = "select * from MyWindow where string='" + threadKey + "' and longPrimitive=" + loop;
                EPQueryResult queryResult = engine.executeQuery(selectQuery);
                Assert.assertEquals(1, queryResult.getArray().length);
                Assert.assertEquals(threadKey, queryResult.getArray()[0].get("string"));
                Assert.assertEquals((long)loop, queryResult.getArray()[0].get("longPrimitive"));
            }
        }
        catch (Exception ex)
        {
            log.fatal("Error in thread " + Thread.currentThread().getId(), ex);
            return false;
        }
        return true;
    }

    private void sendMarketBean(String symbol, long volume)
    {
        SupportMarketDataBean bean = new SupportMarketDataBean(symbol, 0, volume, "");
        engine.sendEvent(bean);
    }

    private static final Log log = LogFactory.getLog(StmtNamedWindowQueryCallable.class);
}
