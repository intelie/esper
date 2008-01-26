package net.esper.multithread;

import net.esper.client.EPServiceProvider;
import net.esper.support.emit.SupportMTEmittedListener;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportBean_A;
import net.esper.event.EventBean;

import java.util.concurrent.Callable;

import junit.framework.Assert;
import junit.framework.AssertionFailedError;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class VariableReadWriteCallable implements Callable
{
    private final EPServiceProvider engine;
    private final int numRepeats;
    private final int threadNum;
    private final SupportUpdateListener selectListener;

    public VariableReadWriteCallable(int threadNum, EPServiceProvider engine, int numRepeats)
    {
        this.engine = engine;
        this.numRepeats = numRepeats;
        this.threadNum = threadNum;

        selectListener = new SupportUpdateListener();
        String stmtText = "select var1, var2, var3 from " + SupportBean_A.class.getName() + "(id='" + threadNum + "')";
        engine.getEPAdministrator().createEQL(stmtText).addListener(selectListener);
    }

    public Object call() throws Exception
    {
        try
        {
            for (int loop = 0; loop < numRepeats; loop++)
            {
                long newValue = threadNum * 1000000 + loop;
                Object event;

                if (loop % 2 == 0)
                {
                    event = new SupportMarketDataBean("", 0, newValue, "");
                }
                else
                {
                    SupportBean bean = new SupportBean();
                    bean.setLongPrimitive(newValue);
                    event = bean;
                }

                // Changes the variable values through either of the set-statements
                engine.getEPRuntime().sendEvent(event);

                // Select the variable value back, another thread may have changed it, we are only
                // determining if the set operation is atomic
                engine.getEPRuntime().sendEvent(new SupportBean_A(Integer.toString(threadNum)));
                EventBean received = selectListener.assertOneGetNewAndReset();
                Assert.assertEquals(received.get("var1"), received.get("var2"));
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

    private static final Log log = LogFactory.getLog(VariableReadWriteCallable.class);
}
