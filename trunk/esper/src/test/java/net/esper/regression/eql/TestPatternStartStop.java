package net.esper.regression.eql;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportBeanComplexProps;
import net.esper.support.client.SupportConfigFactory;
import net.esper.event.EventBean;

public class TestPatternStartStop extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener updateListener;
    private EPStatement statement;

    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
        updateListener = new SupportUpdateListener();

        String stmtText = "select * from pattern [every(a=" + SupportBean.class.getName() +
                " or b=" + SupportBeanComplexProps.class.getName() + ")]";
        statement = epService.getEPAdministrator().createEQL(stmtText);
        statement.addListener(updateListener);
    }

    public void testStartStop()
    {
        for (int i = 0; i < 100; i++)
        {
            sendAndAssert();

            statement.stop();

            epService.getEPRuntime().sendEvent(new SupportBean());
            epService.getEPRuntime().sendEvent(SupportBeanComplexProps.makeDefaultBean());
            assertFalse(updateListener.isInvoked());

            statement.start();
        }
    }

    private void sendAndAssert()
    {
        for (int i = 0; i < 1000; i++)
        {
            Object event = null;
            if (i % 3 == 0)
            {
                event = new SupportBean();
            }
            else
            {
                event = SupportBeanComplexProps.makeDefaultBean();
            }

            epService.getEPRuntime().sendEvent(event);

            EventBean eventBean = updateListener.assertOneGetNewAndReset();
            if (event instanceof SupportBean)
            {
                assertSame(event, eventBean.get("a"));
                assertNull(eventBean.get("b"));
            }
            else
            {
                assertSame(event, eventBean.get("b"));
                assertNull(eventBean.get("a"));
            }
        }
    }
}
