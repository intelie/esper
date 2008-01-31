package com.espertech.esper.regression.eql;

import junit.framework.TestCase;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.support.bean.SupportBeanComplexProps;
import com.espertech.esper.support.bean.SupportBeanCombinedProps;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.event.EventBean;

public class TestJoinPropertyAccess extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener testListener;

    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
    }
    
    public void testRegularJoin()
    {
        SupportBeanCombinedProps combined = SupportBeanCombinedProps.makeDefaultBean();
        SupportBeanComplexProps complex = SupportBeanComplexProps.makeDefaultBean();
        assertEquals("0ma0", combined.getIndexed(0).getMapped("0ma").getValue());

        String viewExpr = "select nested.nested, s1.indexed[0], nested.indexed[1] from " +
                SupportBeanComplexProps.class.getName() + ".win:length(3) nested, " +
                SupportBeanCombinedProps.class.getName() + ".win:length(3) s1" +
                " where mapped('keyOne') = indexed[2].mapped('2ma').value and" +
                " indexed[0].mapped('0ma').value = '0ma0'";

        EPStatement testView = epService.getEPAdministrator().createEQL(viewExpr);
        testListener = new SupportUpdateListener();
        testView.addListener(testListener);

        epService.getEPRuntime().sendEvent(combined);
        epService.getEPRuntime().sendEvent(complex);

        EventBean event = testListener.getAndResetLastNewData()[0];
        assertSame(complex.getNested(), event.get("nested.nested"));
        assertSame(combined.getIndexed(0), event.get("s1.indexed[0]"));
        assertEquals(complex.getIndexed(1), event.get("nested.indexed[1]"));
    }

    public void testOuterJoin()
    {
        String viewExpr = "select * from " +
                SupportBeanComplexProps.class.getName() + ".win:length(3) s0" +
                " left outer join " +
                SupportBeanCombinedProps.class.getName() + ".win:length(3) s1" +
                " on mapped('keyOne') = indexed[2].mapped('2ma').value";

        EPStatement testView = epService.getEPAdministrator().createEQL(viewExpr);
        testListener = new SupportUpdateListener();
        testView.addListener(testListener);

        SupportBeanCombinedProps combined = SupportBeanCombinedProps.makeDefaultBean();
        epService.getEPRuntime().sendEvent(combined);
        SupportBeanComplexProps complex = SupportBeanComplexProps.makeDefaultBean();
        epService.getEPRuntime().sendEvent(complex);

        // double check that outer join criteria match
        assertEquals(complex.getMapped("keyOne"), combined.getIndexed(2).getMapped("2ma").getValue());

        EventBean event = testListener.getAndResetLastNewData()[0];
        assertEquals("simple", event.get("s0.simpleProperty"));
        assertSame(complex, event.get("s0"));
        assertSame(combined, event.get("s1"));
    }
}
