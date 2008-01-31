package com.espertech.esper.multithread;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.SafeIterator;
import com.espertech.esper.support.bean.SupportBean_A;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.event.EventBean;

import java.util.concurrent.Callable;
import java.util.List;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import junit.framework.Assert;

public class StmtNamedWindowIterateCallable implements Callable
{
    private final EPServiceProvider engine;
    private final int numRepeats;
    private final String threadKey;
    private EPStatement statement;

    public StmtNamedWindowIterateCallable(String threadKey, EPServiceProvider engine, int numRepeats)
    {
        this.engine = engine;
        this.numRepeats = numRepeats;
        this.threadKey = threadKey;

        statement = engine.getEPAdministrator().createEQL("select string, sum(longPrimitive) as sumLong from MyWindow group by string");
    }

    public Object call() throws Exception
    {
        try
        {
            long total = 0;
            for (int loop = 0; loop < numRepeats; loop++)
            {
                // Insert event into named window
                sendMarketBean(threadKey, loop + 1);
                total += loop + 1;

                // iterate over private statement
                SafeIterator safeIter = statement.safeIterator();
                EventBean[] received = ArrayAssertionUtil.iteratorToArray(safeIter);
                safeIter.close();

                for (int i = 0; i < received.length; i++)
                {
                    if (received[i].get("string").equals(threadKey))
                    {
                        long sum = (Long) received[i].get("sumLong");
                        Assert.assertEquals(total, sum);
                    }
                }
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
        engine.getEPRuntime().sendEvent(bean);
    }

    private static final Log log = LogFactory.getLog(StmtNamedWindowIterateCallable.class);
}
