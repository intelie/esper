package net.esper.regression.view;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPStatement;
import net.esper.client.EPServiceProviderManager;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.bean.SupportBean;
import net.esper.event.EventBean;

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
    }

    public void testCaseNumericType()
    {
        String caseExpr = "select case " +
                " intPrimitive when longPrimitive then (intPrimitive + longPrimitive) " +
                " when doublePrimitive then intPrimitive * doublePrimitive" +
                " when floatPrimitive then floatPrimitive / doublePrimitive end as p1" +
                " from " + SupportBean.class.getName() + ".win:length(3)";

        selectTestCase = epService.getEPAdministrator().createEQL(caseExpr);
        testListener = new SupportUpdateListener();
        selectTestCase.addListener(testListener);

        sendSupportBeanEvent(2, 2, 1, 1);
        EventBean event = testListener.getAndResetLastNewData()[0];
        assertEquals(4l, event.get("p1"));
        sendSupportBeanEvent(5, 1, 12, 5);
        event = testListener.getAndResetLastNewData()[0];
        assertEquals(25.0, event.get("p1"));        
        sendSupportBeanEvent(12, 1, 12, 4);
        event = testListener.getAndResetLastNewData()[0];
        assertEquals(3.0, event.get("p1"));

    }

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
