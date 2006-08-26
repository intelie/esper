package net.esper.regression.eql;

import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.bean.SupportBean;
import junit.framework.TestCase;

public class TestConsumePatternEvent extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener updateListener;

    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider();
        epService.initialize();
        updateListener = new SupportUpdateListener();
    }

    public void testJustSimplePattern()
    {
        setupStatement();

        Object event = new SupportBean();
        epService.getEPRuntime().sendEvent(event);
        assertSame(event, updateListener.assertOneGetNewAndReset().getUnderlying());
    }

    private void setupStatement()
    {
        String stmtText = "select * from pattern [a=" + SupportBean.class.getName() + "]";
        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        stmt.addListener(updateListener);
    }

}
