package com.espertech.esper.multithread;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.util.SupportMTUpdateListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class StmtNamedWindowSubqueryAggCallable implements Callable<Boolean>
{
    private final EPServiceProvider engine;
    private final int numRepeats;
    private final int threadNum;
    private final EPStatement targetStatement;

    public StmtNamedWindowSubqueryAggCallable(int threadNum, EPServiceProvider engine, int numRepeats, EPStatement targetStatement)
    {
        this.numRepeats = numRepeats;
        this.threadNum = threadNum;
        this.engine = engine;
        this.targetStatement = targetStatement;
    }

    public Boolean call() throws Exception
    {
        try
        {
            SupportMTUpdateListener listener = new SupportMTUpdateListener();
            targetStatement.addListener(listener);

            for (int loop = 0; loop < numRepeats; loop++)
            {
                String generalKey = "Key";
                int valueExpected = threadNum * 1000000000 + loop + 1;

                // send insert event with string-value NOT specific to thread
                sendEvent(generalKey, valueExpected);

                // send subquery trigger event
                engine.getEPRuntime().sendEvent(new SupportBean(generalKey, -1));

                // assert trigger event received
                List<EventBean[]> events = listener.getNewDataListCopy();
                boolean found = false;
                for (EventBean[] arr : events) {
                    for (EventBean item : arr) {
                        List<Integer> value = (List<Integer>) item.get("val");
                        for (Integer valueReceived : value) {
                            if (valueReceived == valueExpected) {
                                found = true;
                                break;
                            }
                        }
                        if (found) {
                            break;
                        }
                    }
                    if (found) {
                        break;
                    }
                }
                listener.reset();

                if (!found) {
                    return false;
                }

                // send delete event with string-value specific to thread
                sendEvent(generalKey, valueExpected);
            }
        }
        catch (Exception ex)
        {
            log.fatal("Error in thread " + Thread.currentThread().getId(), ex);
            return false;
        }
        return true;
    }

    private void sendEvent(String key, int intupd) {
        Map<String,Object> event = new HashMap<String,Object>();
        event.put("uekey", key);
        event.put("ueint", intupd);
        engine.getEPRuntime().sendEvent(event, "UpdateEvent");
    }

    private static final Log log = LogFactory.getLog(StmtNamedWindowSubqueryAggCallable.class);
}
