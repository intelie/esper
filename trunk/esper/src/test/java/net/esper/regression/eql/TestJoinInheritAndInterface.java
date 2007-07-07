package net.esper.regression.eql;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.support.bean.*;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.client.SupportConfigFactory;
import net.esper.event.EventBean;

public class TestJoinInheritAndInterface extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener testListener;

    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
    }
    
    public void testInterfaceJoin()
    {
        String viewExpr = "select a, b from " +
                ISupportA.class.getName() + ".win:length(10), " +
                ISupportB.class.getName() + ".win:length(10)" +
                " where a = b";

        EPStatement testView = epService.getEPAdministrator().createEQL(viewExpr);
        testListener = new SupportUpdateListener();
        testView.addListener(testListener);

        epService.getEPRuntime().sendEvent(new ISupportAImpl("1", "ab1"));
        epService.getEPRuntime().sendEvent(new ISupportBImpl("2", "ab2"));
        assertFalse(testListener.isInvoked());

        epService.getEPRuntime().sendEvent(new ISupportBImpl("1", "ab3"));
        assertTrue(testListener.isInvoked());
        EventBean event = testListener.getAndResetLastNewData()[0];
        assertEquals("1", event.get("a"));
        assertEquals("1", event.get("b"));
    }
}
