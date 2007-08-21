package net.esper.regression.eql;

import net.esper.client.EPServiceProvider;
import net.esper.client.EPStatement;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.Configuration;
import net.esper.support.bean.*;
import net.esper.support.util.SupportUpdateListener;
import net.esper.event.EventBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import junit.framework.TestCase;

public class TestPerf5StreamJoin extends TestCase
{
    private EPServiceProvider epService;
    private EPStatement joinView;
    private SupportUpdateListener updateListener;

    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider(new Configuration());
        epService.initialize();
        updateListener = new SupportUpdateListener();
    }

    public void testPerfAllProps()
    {
        String statement = "select * from " +
            SupportBean_S0.class.getName() + ".win:length(100000) as s0," +
            SupportBean_S1.class.getName() + ".win:length(100000) as s1," +
            SupportBean_S2.class.getName() + ".win:length(100000) as s2," +
            SupportBean_S3.class.getName() + ".win:length(100000) as s3," +
            SupportBean_S4.class.getName() + ".win:length(100000) as s4" +
            " where s0.p00 = s1.p10 " +
               "and s1.p10 = s2.p20 " +
               "and s2.p20 = s3.p30 " +
               "and s3.p30 = s4.p40 ";

        joinView = epService.getEPAdministrator().createEQL(statement);
        joinView.addListener(updateListener);

        log.info(".testPerfAllProps Preloading events");
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++)
        {
            sendEvents(new int[] {0,0,0,0,0}, new String[] {"s0"+i, "s1"+i, "s2"+i, "s3"+i, "s4"+i});
        }

        long endTime = System.currentTimeMillis();
        log.info(".testPerfAllProps delta=" + (endTime - startTime));
        assertTrue((endTime - startTime) < 1500);

        // test if join returns data
        assertNull(updateListener.getLastNewData());
        String[] propertyValues = new String[] {"x", "x", "x", "x", "x"};
        int[] ids = new int[] { 1, 2, 3, 4, 5 };
        sendEvents(ids, propertyValues);
        assertEventsReceived(ids);
    }

    private void assertEventsReceived(int[] expectedIds)
    {
        assertEquals(1, updateListener.getLastNewData().length);
        assertNull(updateListener.getLastOldData());
        EventBean event = updateListener.getLastNewData()[0];
        assertEquals(expectedIds[0], ((SupportBean_S0) event.get("s0")).getId());
        assertEquals(expectedIds[1], ((SupportBean_S1) event.get("s1")).getId());
        assertEquals(expectedIds[2], ((SupportBean_S2) event.get("s2")).getId());
        assertEquals(expectedIds[3], ((SupportBean_S3) event.get("s3")).getId());
        assertEquals(expectedIds[4], ((SupportBean_S4) event.get("s4")).getId());
    }

    private void sendEvent(Object event)
    {
        epService.getEPRuntime().sendEvent(event);
    }

    private void sendEvents(int[] ids, String[] propertyValues)
    {
        sendEvent(new SupportBean_S0(ids[0], propertyValues[0]));
        sendEvent(new SupportBean_S1(ids[1], propertyValues[1]));
        sendEvent(new SupportBean_S2(ids[2], propertyValues[2]));
        sendEvent(new SupportBean_S3(ids[3], propertyValues[3]));
        sendEvent(new SupportBean_S4(ids[4], propertyValues[4]));
    }

    private static final Log log = LogFactory.getLog(TestPerf5StreamJoin.class);
}
