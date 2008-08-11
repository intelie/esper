package com.espertech.esper.regression.view;

import junit.framework.TestCase;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.support.bean.*;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.event.EventBean;

public class TestViewInheritAndInterface extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener testListener;

    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
    }
    
    public void testOverridingSubclass()
    {
        String viewExpr = "select val as value from " +
                SupportOverrideOne.class.getName() + ".win:length(10)";

        EPStatement testView = epService.getEPAdministrator().createEPL(viewExpr);
        testListener = new SupportUpdateListener();
        testView.addListener(testListener);

        epService.getEPRuntime().sendEvent(new SupportOverrideOneA("valA", "valOne", "valBase"));
        EventBean event = testListener.getAndResetLastNewData()[0];
        assertEquals("valA", event.get("value"));

        epService.getEPRuntime().sendEvent(new SupportOverrideBase("x"));
        assertFalse(testListener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportOverrideOneB("valB", "valTwo", "valBase2"));
        event = testListener.getAndResetLastNewData()[0];
        assertEquals("valB", event.get("value"));

        epService.getEPRuntime().sendEvent(new SupportOverrideOne("valThree", "valBase3"));
        event = testListener.getAndResetLastNewData()[0];
        assertEquals("valThree", event.get("value"));
    }

    public void testImplementationClass()
    {
        String[] viewExpr = {
            "select baseAB from " + ISupportBaseAB.class.getName() + ".win:length(10)",
            "select baseAB, a from " + ISupportA.class.getName() + ".win:length(10)",
            "select baseAB, b from " + ISupportB.class.getName() + ".win:length(10)",
            "select c from " + ISupportC.class.getName() + ".win:length(10)",
            "select baseAB, a, g from " + ISupportAImplSuperG.class.getName() + ".win:length(10)",
            "select baseAB, a, b, g, c from " + ISupportAImplSuperGImplPlus.class.getName() + ".win:length(10)",
        };

        String[][] expected = {
                             {"baseAB"},
                             {"baseAB", "a"},
                             {"baseAB", "b"},
                             {"c"},
                             {"baseAB", "a", "g"},
                             {"baseAB", "a", "b", "g", "c"}
                            };

        EPStatement testViews[] = new EPStatement[viewExpr.length];
        SupportUpdateListener listeners[] = new SupportUpdateListener[viewExpr.length];
        for (int i = 0; i < viewExpr.length; i++)
        {
            testViews[i] = epService.getEPAdministrator().createEPL(viewExpr[i]);
            listeners[i] = new SupportUpdateListener();
            testViews[i].addListener(listeners[i]);
        }

        epService.getEPRuntime().sendEvent(new ISupportAImplSuperGImplPlus("g", "a", "baseAB", "b", "c"));
        for (int i = 0; i < listeners.length; i++)
        {
            assertTrue(listeners[i].isInvoked());
            EventBean event = listeners[i].getAndResetLastNewData()[0];

            for (int j = 0; j < expected[i].length; j++)
            {
                assertTrue("failed property valid check for stmt=" + viewExpr[i], event.getEventType().isProperty(expected[i][j]));
                assertEquals("failed property check for stmt=" + viewExpr[i], expected[i][j], event.get(expected[i][j]));
            }
        }
    }
}
