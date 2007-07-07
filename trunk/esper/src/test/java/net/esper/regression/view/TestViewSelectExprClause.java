package net.esper.regression.view;

import java.util.Arrays;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPStatement;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.time.TimerControlEvent;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.bean.SupportBean;
import net.esper.support.client.SupportConfigFactory;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestViewSelectExprClause extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener testListener;
    private EPStatement selectTestView;

    public void setUp()
    {
        testListener = new SupportUpdateListener();
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
        epService.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));

        String viewExpr = "select string, boolBoxed as aBool, 3*intPrimitive, floatBoxed+floatPrimitive as result" +
                          " from " + SupportBean.class.getName() + ".win:length(3) " +
                          " where boolBoxed = true";
        selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        selectTestView.addListener(testListener);
    }
    
    public void testGetEventType()
    {
        EventType type = selectTestView.getEventType();
        log.debug(".testGetEventType properties=" + Arrays.toString(type.getPropertyNames()));
        assertTrue(Arrays.equals(type.getPropertyNames(), new String[] {"(3*intPrimitive)", "string", "result", "aBool"}));
        assertEquals(String.class, type.getPropertyType("string"));
        assertEquals(Boolean.class, type.getPropertyType("aBool"));
        assertEquals(Float.class, type.getPropertyType("result"));
        assertEquals(Integer.class, type.getPropertyType("(3*intPrimitive)"));
    }

    public void testWindowStats()
    {
        testListener.reset();

        sendEvent("a", false, 0, 0, 0);
        sendEvent("b", false, 0, 0, 0);
        assertTrue(testListener.getLastNewData() == null);
        sendEvent("c", true, 3, 10, 20);

        EventBean received = testListener.getAndResetLastNewData()[0];
        assertEquals("c", received.get("string"));
        assertEquals(true, received.get("aBool"));
        assertEquals(30f, received.get("result"));
    }

    private void sendEvent(String s, boolean b, int i, float f1, float f2)
    {
        SupportBean bean = new SupportBean();
        bean.setString(s);
        bean.setBoolBoxed(b);
        bean.setIntPrimitive(i);
        bean.setFloatPrimitive(f1);
        bean.setFloatBoxed(f2);
        epService.getEPRuntime().sendEvent(bean);
    }

    private static final Log log = LogFactory.getLog(TestViewSelectExprClause.class);
}
