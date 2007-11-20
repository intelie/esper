package net.esper.multithread;

import net.esper.client.EPServiceProvider;
import net.esper.client.EPStatement;
import net.esper.support.util.SupportMTUpdateListener;
import net.esper.support.bean.SupportBean;
import net.esper.util.ThreadLogUtil;
import net.esper.event.EventBean;

import java.util.concurrent.Callable;
import java.util.Map;

import junit.framework.Assert;
import junit.framework.AssertionFailedError;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StmtDatabaseJoinCallable implements Callable
{
    private final EPServiceProvider engine;
    private final EPStatement stmt;
    private final int numRepeats;
    private final String[] MYVARCHAR_VALUES = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"}; 

    public StmtDatabaseJoinCallable(EPServiceProvider engine, EPStatement stmt, int numRepeats)
    {
        this.engine = engine;
        this.stmt = stmt;
        this.numRepeats = numRepeats;
    }

    public Object call() throws Exception
    {
        try
        {
            // Add assertListener
            SupportMTUpdateListener assertListener = new SupportMTUpdateListener();
            ThreadLogUtil.trace("adding listeners ", assertListener);
            stmt.addListener(assertListener);

            for (int loop = 0; loop < numRepeats; loop++)
            {
                int intPrimitive = loop % 10 + 1;
                Object eventS0 = makeEvent(intPrimitive);

                engine.getEPRuntime().sendEvent(eventS0);

                // Should have received one that's mine, possible multiple since the statement is used by other threads
                boolean found = false;
                EventBean[] events = assertListener.getNewDataListFlattened();
                for (EventBean event : events)
                {
                    Object s0Received = event.get("s0");
                    Map s1Received = (Map) event.get("s1");
                    if ((s0Received == eventS0) || (s1Received.get("myvarchar").equals(MYVARCHAR_VALUES[intPrimitive - 1])))
                    {
                        found = true;
                    }
                }
                if (!found)
                {
                }
                Assert.assertTrue(found);
                assertListener.reset();
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

    private SupportBean makeEvent(int intPrimitive)
    {
        SupportBean event = new SupportBean();
        event.setIntPrimitive(intPrimitive);
        return event;
    }

    private static final Log log = LogFactory.getLog(StmtDatabaseJoinCallable.class);
}
