package net.esper.regression.eql;

import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportBeanComplexProps;
import net.esper.event.EventBean;
import junit.framework.TestCase;

public class TestPatternEventProperties extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener updateListener;

    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider();
        epService.initialize();
        updateListener = new SupportUpdateListener();
    }

    public void testWildcardSimplePattern()
    {
        setupSimplePattern("*");

        Object event = new SupportBean();
        epService.getEPRuntime().sendEvent(event);
        EventBean eventBean = updateListener.assertOneGetNewAndReset();
        assertSame(event, eventBean.get("a"));
    }

    public void testWildcardOrPattern()
    {
        setupOrPattern("*");

        Object event = new SupportBean();
        epService.getEPRuntime().sendEvent(event);
        EventBean eventBean = updateListener.assertOneGetNewAndReset();
        assertSame(event, eventBean.get("a"));
        assertNull(eventBean.get("b"));

        event = SupportBeanComplexProps.makeDefaultBean();
        epService.getEPRuntime().sendEvent(event);
        eventBean = updateListener.assertOneGetNewAndReset();
        assertSame(event, eventBean.get("b"));
        assertNull(eventBean.get("a"));
    }

    public void testPropertiesSimplePattern()
    {
        setupSimplePattern("a, a as myEvent, a.intPrimitive as myInt, a.string");

        SupportBean event = new SupportBean();
        event.setIntPrimitive(1);
        event.setString("test");
        epService.getEPRuntime().sendEvent(event);

        EventBean eventBean = updateListener.assertOneGetNewAndReset();
        assertSame(event, eventBean.get("a"));
        assertSame(event, eventBean.get("myEvent"));
        assertEquals(1, eventBean.get("myInt"));
        assertEquals("test", eventBean.get("a.string"));
    }

    public void testPropertiesOrPattern()
    {
        setupOrPattern("a, a as myAEvent, b, b as myBEvent, a.intPrimitive as myInt, " +
                "a.string, b.simpleProperty as simple, b.indexed[0] as indexed, b.nested.nestedValue as nestedVal");

        Object event = SupportBeanComplexProps.makeDefaultBean();
        epService.getEPRuntime().sendEvent(event);
        EventBean eventBean = updateListener.assertOneGetNewAndReset();
        assertSame(event, eventBean.get("b"));
        assertEquals("simple", eventBean.get("simple"));
        assertEquals(1, eventBean.get("indexed"));
        assertEquals("nestedValue", eventBean.get("nestedVal"));
        assertNull(eventBean.get("a"));
        assertNull(eventBean.get("myAEvent"));
        assertNull(eventBean.get("myInt"));
        assertNull(eventBean.get("a.string"));

        SupportBean eventTwo = new SupportBean();
        eventTwo.setIntPrimitive(2);
        eventTwo.setString("test2");
        epService.getEPRuntime().sendEvent(eventTwo);
        eventBean = updateListener.assertOneGetNewAndReset();
        assertEquals(2, eventBean.get("myInt"));
        assertEquals("test2", eventBean.get("a.string"));
        assertNull(eventBean.get("b"));
        assertNull(eventBean.get("myBEvent"));
        assertNull(eventBean.get("simple"));
        assertNull(eventBean.get("indexed"));
        assertNull(eventBean.get("nestedVal"));
    }

    private void setupSimplePattern(String selectCriteria)
    {
        String stmtText = "select " + selectCriteria + " from pattern [a=" + SupportBean.class.getName() + "]";
        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        stmt.addListener(updateListener);
    }

    private void setupOrPattern(String selectCriteria)
    {
        String stmtText = "select " + selectCriteria + " from pattern [every(a=" + SupportBean.class.getName() +
                " or b=" + SupportBeanComplexProps.class.getName() + ")]";
        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        stmt.addListener(updateListener);
    }
}
