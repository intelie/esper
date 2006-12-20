package net.esper.regression.view;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.support.bean.SupportBeanComplexProps;
import net.esper.support.util.SupportUpdateListener;
import net.esper.event.EventBean;

public class TestViewPropertyAccess extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener testListener;

    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider();
        epService.initialize();
    }
    
    public void testWhereAndSelect()
    {
        String viewExpr = "select mapped('keyOne') as a," +
                                 "indexed[1] as b, nested.nestedNested.nestedNestedValue as c, mapProperty, " +
                                 "arrayProperty[0] " +
                "  from " + SupportBeanComplexProps.class.getName() + ".win:length(3) " +
                " where mapped('keyOne') = 'valueOne' and " +
                      " indexed[1] = 2 and " +
                      " nested.nestedNested.nestedNestedValue = 'nestedNestedValue'";

        EPStatement testView = epService.getEPAdministrator().createEQL(viewExpr);
        testListener = new SupportUpdateListener();
        testView.addListener(testListener);

        SupportBeanComplexProps eventObject = SupportBeanComplexProps.makeDefaultBean();
        epService.getEPRuntime().sendEvent(eventObject);
        EventBean event = testListener.getAndResetLastNewData()[0];
        assertEquals(eventObject.getMapped("keyOne"), event.get("a"));
        assertEquals(eventObject.getIndexed(1), event.get("b"));
        assertEquals(eventObject.getNested().getNestedNested().getNestedNestedValue(), event.get("c"));
        assertEquals(eventObject.getMapProperty(), event.get("mapProperty"));
        assertEquals(eventObject.getArrayProperty()[0], event.get("arrayProperty[0]"));

        eventObject.setIndexed(1, Integer.MIN_VALUE);
        assertFalse(testListener.isInvoked());
        epService.getEPRuntime().sendEvent(eventObject);
        assertFalse(testListener.isInvoked());

        eventObject.setIndexed(1, 2);
        epService.getEPRuntime().sendEvent(eventObject);
        assertTrue(testListener.isInvoked());
    }
}
