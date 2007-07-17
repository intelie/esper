package net.esper.regression.eql;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPStatement;
import net.esper.client.EPServiceProviderManager;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.bean.SupportBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestPerf3StreamCoercion extends TestCase
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
        String stmtText = "select s1.intBoxed as value from " +
                SupportBean.class.getName() + "(string='A').win:length(1000000) s1," +
                SupportBean.class.getName() + "(string='B').win:length(1000000) s2," +
                SupportBean.class.getName() + "(string='C').win:length(1000000) s3" +
            " where s1.intBoxed=s2.longBoxed and s1.intBoxed=s3.doubleBoxed";

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
            assertEquals(index, listener.assertOneGetNewAndReset().get("value"));
        }
        long endTime = System.currentTimeMillis();
        long delta = endTime - startTime;

        assertTrue("Failed perf test, delta=" + delta, delta < 1000);
    }

    public void testPerfCoercion3waySceneTwo()
    {
        String stmtText = "select s1.intBoxed as value from " +
                SupportBean.class.getName() + "(string='A').win:length(1000000) s1," +
                SupportBean.class.getName() + "(string='B').win:length(1000000) s2," +
                SupportBean.class.getName() + "(string='C').win:length(1000000) s3" +
            " where s1.intBoxed=s2.longBoxed and s1.intBoxed=s3.doubleBoxed";

        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        stmt.addListener(listener);

        // preload
        for (int i = 0; i < 10000; i++)
        {
            sendEvent("A", i, 0, 0);
            sendEvent("B", 0, i, 0);
        }

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 5000; i++)
        {
            int index = 5000 + i % 1000;
            sendEvent("C", 0, 0, index);
            assertEquals(index, listener.assertOneGetNewAndReset().get("value"));
        }
        long endTime = System.currentTimeMillis();
        long delta = endTime - startTime;

        assertTrue("Failed perf test, delta=" + delta, delta < 1000);
    }

    public void testPerfCoercion3waySceneThree()
    {
        String stmtText = "select s1.intBoxed as value from " +
                SupportBean.class.getName() + "(string='A').win:length(1000000) s1," +
                SupportBean.class.getName() + "(string='B').win:length(1000000) s2," +
                SupportBean.class.getName() + "(string='C').win:length(1000000) s3" +
            " where s1.intBoxed=s2.longBoxed and s1.intBoxed=s3.doubleBoxed";

        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        stmt.addListener(listener);

        // preload
        for (int i = 0; i < 10000; i++)
        {
            sendEvent("A", i, 0, 0);
            sendEvent("C", 0, 0, i);
        }

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 5000; i++)
        {
            int index = 5000 + i % 1000;
            sendEvent("B", 0, index, 0);
            assertEquals(index, listener.assertOneGetNewAndReset().get("value"));
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

    private static final Log log = LogFactory.getLog(TestPerf3StreamCoercion.class);
}
