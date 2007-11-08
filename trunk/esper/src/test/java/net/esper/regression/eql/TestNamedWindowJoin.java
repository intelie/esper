package net.esper.regression.eql;

import junit.framework.TestCase;
import net.esper.client.Configuration;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.bean.SupportBean_A;
import net.esper.support.client.SupportConfigFactory;
import net.esper.support.util.ArrayAssertionUtil;
import net.esper.support.util.SupportUpdateListener;

public class TestNamedWindowJoin extends TestCase
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

    public void testJoinNamedAndStream()
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

        // create consumer
        String[] fields = new String[] {"symbol", "a", "b"};
        String stmtTextSelectOne = "select symbol, a, b " +
                                   " from " + SupportMarketDataBean.class.getName() + ".win:length(10) as s0," +
                                             "MyWindow as s1 where s1.a = symbol";
        EPStatement stmtSelectOne = epService.getEPAdministrator().createEQL(stmtTextSelectOne);
        stmtSelectOne.addListener(listenerStmtOne);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmtSelectOne.getEventType().getPropertyNames(), new String[] {"symbol", "a", "b"});
        assertEquals(String.class, stmtSelectOne.getEventType().getPropertyType("symbol"));
        assertEquals(String.class, stmtSelectOne.getEventType().getPropertyType("a"));
        assertEquals(int.class, stmtSelectOne.getEventType().getPropertyType("b"));

        sendMarketBean("S1");
        assertFalse(listenerStmtOne.isInvoked());

        sendSupportBean("S1", 1);
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"S1", "S1", 1});

        sendSupportBean_A("S1"); // deletes from window
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetOldAndReset(), fields, new Object[] {"S1", "S1", 1});

        sendMarketBean("S1");
        assertFalse(listenerStmtOne.isInvoked());

        sendSupportBean("S2", 2);
        assertFalse(listenerStmtOne.isInvoked());

        sendMarketBean("S2");
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"S2", "S2", 2});

        sendSupportBean("S3", 3);
        sendSupportBean("S3", 4);
        assertFalse(listenerStmtOne.isInvoked());

        sendMarketBean("S3");
        assertEquals(2, listenerStmtOne.getLastNewData().length);
        listenerStmtOne.reset();

        sendSupportBean_A("S3"); // deletes from window
        assertEquals(2, listenerStmtOne.getLastOldData().length);
        listenerStmtOne.reset();

        sendMarketBean("S3");
        assertFalse(listenerStmtOne.isInvoked());
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

    private void sendMarketBean(String symbol)
    {
        SupportMarketDataBean bean = new SupportMarketDataBean(symbol, 0, 0l, "");
        epService.getEPRuntime().sendEvent(bean);
    }
}
