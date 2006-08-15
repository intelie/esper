package net.esper.regression.view;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPStatement;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.time.TimerControlEvent;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.bean.SupportBean;
import net.esper.event.EventType;
import net.esper.event.EventBean;

import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by IntelliJ IDEA.
 * User: MYSELF
 * Date: Aug 13, 2006
 * Time: 1:50:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestCaseExprClause extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener testListener;
    private EPStatement selectTestCase;

    public void setUp()
    {
        testListener = new SupportUpdateListener();
        epService = EPServiceProviderManager.getDefaultProvider();
        epService.initialize();
        //epService.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));

        //String viewExpr = "select string, boolBoxed as aBool, 3*intPrimitive, floatBoxed+floatPrimitive as result" +
          //                " from " + SupportBean.class.getName() + ".win:length(3) " +
            //              " where boolBoxed = true";
        //selectTestView = epService.getEPAdministrator().createEQL(viewExpr);
        //selectTestCase.addListener(testListener);
    }

    public void testCaseNumericType()
    {
        String viewExpr = "select " +
                " intPrimitive + longPrimitive as p1," +
                " intPrimitive * doublePrimitive as p2," +
                " floatPrimitive / doublePrimitive as p3" +
                " from " + SupportBean.class.getName() + ".win:length(3) where " +
                "intPrimitive=longPrimitive and intPrimitive=doublePrimitive and floatPrimitive=doublePrimitive";

        String caseExpr = "select case " +
                " intPrimitive when longPrimitive then (intPrimitive + longPrimitive) end as p1" +
                " from " + SupportBean.class.getName() + ".win:length(3)";

        selectTestCase = epService.getEPAdministrator().createEQL(caseExpr);
        testListener = new SupportUpdateListener();
        selectTestCase.addListener(testListener);

        sendSupportBeanEvent(2, 2, 2, 2);
        EventBean event = testListener.getAndResetLastNewData()[0];
        //assertEquals(Long.class, event.getEventType().getPropertyType("p1"));
        assertEquals(4l, event.get("p1"));

    }

    /* public void testGetEventType()
    {
        EventType type = selectTestView.getEventType();
        log.debug(".testGetEventType properties=" + Arrays.toString(type.getPropertyNames()));
        assertTrue(Arrays.equals(type.getPropertyNames(), new String[] {"(3*intPrimitive)", "string", "result", "aBool"}));
        assertEquals(String.class, type.getPropertyType("string"));
        assertEquals(Boolean.class, type.getPropertyType("aBool"));
        assertEquals(Float.class, type.getPropertyType("result"));
        assertEquals(Integer.class, type.getPropertyType("(3*intPrimitive)"));
    }  */

    private void sendSupportBeanEvent(int i, long l, float f, double d)
    {
        SupportBean event = new SupportBean();
        event.setIntPrimitive(i);
        event.setLongPrimitive(l);
        event.setFloatPrimitive(f);
        event.setDoublePrimitive(d);
        epService.getEPRuntime().sendEvent(event);
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

    private static final Log log = LogFactory.getLog(TestCaseExprClause.class);
}
