package net.esper.multithread;

import net.esper.client.EPServiceProvider;
import net.esper.support.emit.SupportMTEmittedListener;

import java.util.concurrent.Callable;

import junit.framework.Assert;
import junit.framework.AssertionFailedError;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EmitListenerCallable implements Callable
{
    private final EPServiceProvider engine;
    private final int numRepeats;
    private final int threadNum;

    public EmitListenerCallable(int threadNum, EPServiceProvider engine, int numRepeats)
    {
        this.engine = engine;
        this.numRepeats = numRepeats;
        this.threadNum = threadNum;
    }

    public Object call() throws Exception
    {
        try
        {
            String channelName = Integer.toString(threadNum);
            SupportMTEmittedListener listener = new SupportMTEmittedListener();
            engine.getEPRuntime().addEmittedListener(listener, channelName);

            for (int loop = 0; loop < numRepeats; loop++)
            {
                Object event = new Object();
                engine.getEPRuntime().emit(event, channelName);

                // Should have received event exactly one
                Object[] received = listener.getEmittedObjects();
                Assert.assertEquals(1, received.length);
                Assert.assertSame(event, received[0]);
                listener.reset();
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

    private static final Log log = LogFactory.getLog(EmitListenerCallable.class);
}
