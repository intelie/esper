package net.esper.multithread;

import net.esper.client.EPServiceProvider;
import net.esper.support.bean.SupportBean_A;
import net.esper.support.bean.SupportMarketDataBean;

import java.util.concurrent.Callable;
import java.util.List;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StmtNamedWindowConsumeCallable implements Callable
{
    private final EPServiceProvider engine;
    private final int numRepeats;
    private final String threadKey;

    public StmtNamedWindowConsumeCallable(String threadKey, EPServiceProvider engine, int numRepeats)
    {
        this.engine = engine;
        this.numRepeats = numRepeats;
        this.threadKey = threadKey;
    }

    public Object call() throws Exception
    {
        List<String> eventKeys = new ArrayList<String>(numRepeats);
        try
        {
            for (int loop = 0; loop < numRepeats; loop++)
            {
                // Insert event into named window
                String event = "E" + threadKey + "_" + loop;
                eventKeys.add(event);
                sendMarketBean(event, 0);
            }
        }
        catch (Exception ex)
        {
            log.fatal("Error in thread " + Thread.currentThread().getId(), ex);
            return null;
        }
        return eventKeys;
    }

    private void sendMarketBean(String symbol, long volume)
    {
        SupportMarketDataBean bean = new SupportMarketDataBean(symbol, 0, volume, "");
        engine.getEPRuntime().sendEvent(bean);
    }

    private static final Log log = LogFactory.getLog(StmtNamedWindowConsumeCallable.class);
}
