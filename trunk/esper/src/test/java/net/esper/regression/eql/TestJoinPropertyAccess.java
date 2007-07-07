package net.esper.regression.eql;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.support.bean.SupportBeanComplexProps;
import net.esper.support.bean.SupportBeanCombinedProps;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.client.SupportConfigFactory;
import net.esper.event.EventBean;

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
        assertSame(complex, event.get("s0"));
        assertSame(combined, event.get("s1"));
    }
}
