package net.esper.regression.eql;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.support.bean.SupportBean;
import net.esper.support.client.SupportConfigFactory;
import net.esper.support.util.SupportUpdateListener;

import java.util.Iterator;

public class TestSelectClauseJoin extends TestCase
{
    private EPServiceProvider epService;
    private EPStatement joinView;
    private SupportUpdateListener updateListener;

    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
        updateListener = new SupportUpdateListener();

        String eventA = SupportBean.class.getName();
        String eventB = SupportBean.class.getName();

        String joinStatement = "select s0.doubleBoxed, s1.intPrimitive*s1.intBoxed/2.0 as div from " +
            eventA + "(string='s0').win:length(3) as s0," +
            eventB + "(string='s1').win:length(3) as s1" +
            " where s0.doubleBoxed = s1.doubleBoxed";

        joinView = epService.getEPAdministrator().createEQL(joinStatement);
        joinView.addListener(updateListener);
    }

    public void testJoinSelect()
    {
        assertNull(updateListener.getLastNewData());

        sendEvent("s0", 1, 4, 5);
        sendEvent("s1", 1, 3, 2);

        EventBean[] newEvents = updateListener.getLastNewData();
        assertEquals(1d, newEvents[0].get("s0.doubleBoxed"));
        assertEquals(3d, newEvents[0].get("div"));

        Iterator<EventBean> iterator = joinView.iterator();
        EventBean event = iterator.next();
        assertEquals(1d, event.get("s0.doubleBoxed"));
        assertEquals(3d, event.get("div"));
    }

    public void testEventType()
    {
        EventType result = joinView.getEventType();
        assertEquals(Double.class, result.getPropertyType("s0.doubleBoxed"));
        assertEquals(Double.class, result.getPropertyType("div"));
        assertEquals(2, joinView.getEventType().getPropertyNames().length);
    }

    private void sendEvent(String s, double doubleBoxed, int intPrimitive, int intBoxed)
    {
        SupportBean bean = new SupportBean();
        bean.setString(s);
        bean.setDoubleBoxed(doubleBoxed);
        bean.setIntPrimitive(intPrimitive);
        bean.setIntBoxed(intBoxed);
        epService.getEPRuntime().sendEvent(bean);
    }
}
