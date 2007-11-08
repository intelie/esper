package net.esper.regression.eql;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.Configuration;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.util.ArrayAssertionUtil;
import net.esper.support.client.SupportConfigFactory;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportBean_A;
import net.esper.support.bean.SupportMarketDataBean;

public class TestPerfNamedWindow extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listenerWindow;
    private SupportUpdateListener listenerStmtOne;

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);

        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listenerWindow = new SupportUpdateListener();
        listenerStmtOne = new SupportUpdateListener();
    }

    public void testDeletePerformance()
    {
        // create window
        String stmtTextCreate = "create window MyWindow.win:keepall() as select string as a, intPrimitive as b from " + SupportBean.class.getName();
        EPStatement stmtCreate = epService.getEPAdministrator().createEQL(stmtTextCreate);
        stmtCreate.addListener(listenerWindow);

        // create delete stmt
        String stmtTextDelete = "on " + SupportBean_A.class.getName() + " delete from MyWindow where id = a";
        epService.getEPAdministrator().createEQL(stmtTextDelete);

        // create insert into
        String stmtTextInsertOne = "insert into MyWindow select string as a, intPrimitive as b from " + SupportBean.class.getName();
        epService.getEPAdministrator().createEQL(stmtTextInsertOne);

        // load window
        for (int i = 0; i < 100000; i++)
        {
            sendSupportBean("S" + i, i);
        }

        // delete rows
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 100; i++)
        {
            sendSupportBean_A("S" + i);
        }
        long endTime = System.currentTimeMillis();
        long delta = endTime - startTime;
        System.out.println("Delta=" + delta);   // TODO: remove
        assertTrue("Delta=" + delta, delta < 500);

        /*
        // create consumer
        String[] fields = new String[] {"a", "b"};
        String stmtTextSelectOne = "select a, b from MyWindow";
        EPStatement stmtSelectOne = epService.getEPAdministrator().createEQL(stmtTextSelectOne);
        stmtSelectOne.addListener(listenerStmtOne);

        ArrayAssertionUtil.assertEqualsAnyOrder(stmtSelectOne.getEventType().getPropertyNames(), new String[] {"symbol", "a", "b"});
        assertEquals(String.class, stmtSelectOne.getEventType().getPropertyType("symbol"));
        assertEquals(String.class, stmtSelectOne.getEventType().getPropertyType("a"));
        assertEquals(int.class, stmtSelectOne.getEventType().getPropertyType("b"));

        sendMarketBean("S1");
        assertFalse(listenerStmtOne.isInvoked());

        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"S1", "S1", 1});

        sendSupportBean_A("S1"); // deletes from window
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetOldAndReset(), fields, new Object[] {"S1", "S1", 1});

        sendMarketBean("S1");
        assertFalse(listenerStmtOne.isInvoked());

        sendSupportBean("S2", 2);
        assertFalse(listenerStmtOne.isInvoked());

        sendMarketBean("S2");
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"S2", "S2", 2});
        */
    }

    private SupportBean_A sendSupportBean_A(String id)
    {
        SupportBean_A bean = new SupportBean_A(id);
        epService.getEPRuntime().sendEvent(bean);
        return bean;
    }

    private SupportBean sendSupportBean(String string, int intPrimitive)
    {
        SupportBean bean = new SupportBean();
        bean.setString(string);
        bean.setIntPrimitive(intPrimitive);
        epService.getEPRuntime().sendEvent(bean);
        return bean;
    }
}
