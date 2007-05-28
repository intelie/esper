package net.esper.regression.eql;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.bean.SupportBean;
import net.esper.event.EventBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestPerf3StreamOuterJoinCoercion extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider();
        epService.initialize();
        listener = new SupportUpdateListener();
    }

    public void testPerfCoercion3waySceneOne()
    {
        String stmtText = "select s1.intBoxed as v1, s2.longBoxed as v2, s3.doubleBoxed as v3 from " +
                SupportBean.class.getName() + "(string='A').win:length(1000000) s1 " +
                " left outer join " +
                SupportBean.class.getName() + "(string='B').win:length(1000000) s2 on s1.intBoxed=s2.longBoxed " +
                " left outer join " +
                SupportBean.class.getName() + "(string='C').win:length(1000000) s3 on s1.intBoxed=s3.doubleBoxed";

        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        stmt.addListener(listener);

        // preload
        for (int i = 0; i < 10000; i++)
        {
            sendEvent("B", 0, i, 0);
            sendEvent("C", 0, 0, i);
        }

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 5000; i++)
        {
            int index = 5000 + i % 1000;
            sendEvent("A", index, 0, 0);
            EventBean event = listener.assertOneGetNewAndReset();
            assertEquals(index, event.get("v1"));
            assertEquals((long)index, event.get("v2"));
            assertEquals((double)index, event.get("v3"));
        }
        long endTime = System.currentTimeMillis();
        long delta = endTime - startTime;

        assertTrue("Failed perf test, delta=" + delta, delta < 1000);
    }

    public void testPerfCoercion3waySceneTwo()
    {
        String stmtText = "select s1.intBoxed as v1, s2.longBoxed as v2, s3.doubleBoxed as v3 from " +
                SupportBean.class.getName() + "(string='A').win:length(1000000) s1 " +
                " left outer join " +
                SupportBean.class.getName() + "(string='B').win:length(1000000) s2 on s1.intBoxed=s2.longBoxed " +
                " left outer join " +
                SupportBean.class.getName() + "(string='C').win:length(1000000) s3 on s1.intBoxed=s3.doubleBoxed";

        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        stmt.addListener(listener);

        // preload
        for (int i = 0; i < 10000; i++)
        {
            sendEvent("B", 0, i, 0);
            sendEvent("A", i, 0, 0);
        }

        listener.reset();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 5000; i++)
        {
            int index = 5000 + i % 1000;
            sendEvent("C", 0, 0, index);
            EventBean event = listener.assertOneGetNewAndReset();
            assertEquals(index, event.get("v1"));
            assertEquals((long)index, event.get("v2"));
            assertEquals((double)index, event.get("v3"));
        }
        long endTime = System.currentTimeMillis();
        long delta = endTime - startTime;

        assertTrue("Failed perf test, delta=" + delta, delta < 1000);
    }

    public void testPerfCoercion3waySceneThree()
    {
        String stmtText = "select s1.intBoxed as v1, s2.longBoxed as v2, s3.doubleBoxed as v3 from " +
                SupportBean.class.getName() + "(string='A').win:length(1000000) s1 " +
                " left outer join " +
                SupportBean.class.getName() + "(string='B').win:length(1000000) s2 on s1.intBoxed=s2.longBoxed " +
                " left outer join " +
                SupportBean.class.getName() + "(string='C').win:length(1000000) s3 on s1.intBoxed=s3.doubleBoxed";

        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        stmt.addListener(listener);

        // preload
        for (int i = 0; i < 10000; i++)
        {
            sendEvent("A", i, 0, 0);
            sendEvent("C", 0, 0, i);
        }

        listener.reset();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 5000; i++)
        {
            int index = 5000 + i % 1000;
            sendEvent("B", 0, index, 0);
            EventBean event = listener.assertOneGetNewAndReset();
            assertEquals(index, event.get("v1"));
            assertEquals((long)index, event.get("v2"));
            assertEquals((double)index, event.get("v3"));
        }
        long endTime = System.currentTimeMillis();
        long delta = endTime - startTime;

        assertTrue("Failed perf test, delta=" + delta, delta < 1000);
    }

    private void sendEvent(String string, int intBoxed, long longBoxed, double doubleBoxed)
    {
        SupportBean bean = new SupportBean();
        bean.setString(string);
        bean.setIntBoxed(intBoxed);
        bean.setLongBoxed(longBoxed);
        bean.setDoubleBoxed(doubleBoxed);
        epService.getEPRuntime().sendEvent(bean);
    }

    private static final Log log = LogFactory.getLog(TestPerf3StreamOuterJoinCoercion.class);
}
