package net.esper.regression.view;

import net.esper.client.EPServiceProvider;
import net.esper.client.EPStatement;
import net.esper.client.EPServiceProviderManager;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportBean_S0;
import net.esper.event.EventType;
import net.esper.event.EventBean;

import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import junit.framework.TestCase;

public class TestPerRowFunc extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener testListener;
    private EPStatement selectTestView;

    public void setUp()
    {
        testListener = new SupportUpdateListener();
        epService = EPServiceProviderManager.getDefaultProvider();
        epService.initialize();
    }

    public void testGetEventType()
    {
        setUpMinMax();
        EventType type = selectTestView.getEventType();
        log.debug(".testGetEventType properties=" + Arrays.toString(type.getPropertyNames()));
        assertEquals(Long.class, type.getPropertyType("myMax"));
        assertEquals(Long.class, type.getPropertyType("myMin"));
        assertEquals(Long.class, type.getPropertyType("myMinEx"));
        assertEquals(Long.class, type.getPropertyType("myMaxEx"));
    }

    public void testWindowStats()
    {
        setUpMinMax();
        testListener.reset();

        sendEvent(10, 20, (short)4);
        EventBean received = testListener.getAndResetLastNewData()[0];
        assertEquals(20L, received.get("myMax"));
        assertEquals(10L, received.get("myMin"));
        assertEquals(4L, received.get("myMinEx"));
        assertEquals(20L, received.get("myMaxEx"));

        sendEvent(-10, -20, (short)-30);
        received = testListener.getAndResetLastNewData()[0];
        assertEquals(-10L, received.get("myMax"));
        assertEquals(-20L, received.get("myMin"));
        assertEquals(-30L, received.get("myMinEx"));
        assertEquals(-10L, received.get("myMaxEx"));
    }

    public void testOperators()
    {
        String viewExpr = "select longBoxed % intBoxed as myMod " +
                          " from " + SupportBean.class.getName() + ".win:length(3) where not(longBoxed > intBoxed)";
        selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);

        sendEvent(1, 1, (short)0);
        assertEquals(0l, testListener.getLastNewData()[0].get("myMod"));
        testListener.reset();

        sendEvent(2, 1, (short)0);
        assertFalse(testListener.getAndClearIsInvoked());

        sendEvent(2, 3, (short)0);
        assertEquals(2l, testListener.getLastNewData()[0].get("myMod"));
        testListener.reset();
    }

    public void testConcat()
    {
        String viewExpr = "select p00 || p01 as c1, p00 || p01 || p02 as c2, p00 || '|' || p01 as c3" +
                          " from " + SupportBean_S0.class.getName() + ".win:length(10)";
        selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);

        epService.getEPRuntime().sendEvent(new SupportBean_S0(1, "a", "b", "c"));
        assertConcat("ab", "abc", "a|b");

        epService.getEPRuntime().sendEvent(new SupportBean_S0(1, null, "b", "c"));
        assertConcat(null, null, null);

        epService.getEPRuntime().sendEvent(new SupportBean_S0(1, "", "b", "c"));
        assertConcat("b", "bc", "|b");

        epService.getEPRuntime().sendEvent(new SupportBean_S0(1, "123", null, "c"));
        assertConcat(null, null, null);

        epService.getEPRuntime().sendEvent(new SupportBean_S0(1, "123", "456", "c"));
        assertConcat("123456", "123456c", "123|456");

        epService.getEPRuntime().sendEvent(new SupportBean_S0(1, "123", "456", null));
        assertConcat("123456", null, "123|456");
    }

    private void setUpMinMax()
    {
        String viewExpr = "select max(longBoxed, intBoxed) as myMax, " +
                                 "max(longBoxed, intBoxed, shortBoxed) as myMaxEx," +
                                 "min(longBoxed, intBoxed) as myMin," +
                                 "min(longBoxed, intBoxed, shortBoxed) as myMinEx" +
                          " from " + SupportBean.class.getName() + ".win:length(3) ";
        selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);
    }

    private void sendEvent(long longBoxed, int intBoxed, short shortBoxed)
    {
        SupportBean bean = new SupportBean();
        bean.setLongBoxed(longBoxed);
        bean.setIntBoxed(intBoxed);
        bean.setShortBoxed(shortBoxed);
        epService.getEPRuntime().sendEvent(bean);
    }

    private void assertConcat(String c1, String c2, String c3)
    {
        EventBean event = testListener.getLastNewData()[0];
        assertEquals(c1, event.get("c1"));
        assertEquals(c2, event.get("c2"));
        assertEquals(c3, event.get("c3"));
        testListener.reset();
    }

    private static final Log log = LogFactory.getLog(TestViewSelectExprClause.class);
}
