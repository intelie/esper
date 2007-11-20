package net.esper.regression.eql;

import junit.framework.TestCase;
import net.esper.client.Configuration;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportBean_A;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.client.SupportConfigFactory;
import net.esper.support.util.ArrayAssertionUtil;
import net.esper.support.util.SupportUpdateListener;

public class TestNamedWindowJoin extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listenerWindow;
    private SupportUpdateListener listenerWindowTwo;
    private SupportUpdateListener listenerStmtOne;

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);

        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listenerWindow = new SupportUpdateListener();
        listenerWindowTwo = new SupportUpdateListener();
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

    public void testJoinBetweenNamed()
    {
        String[] fields = new String[] {"a1", "b1", "a2", "b2"};

        // create window
        String stmtTextCreateOne = "create window MyWindowOne.win:keepall() as select string as a1, intPrimitive as b1 from " + SupportBean.class.getName();
        EPStatement stmtCreateOne = epService.getEPAdministrator().createEQL(stmtTextCreateOne);
        stmtCreateOne.addListener(listenerWindow);

        // create window
        String stmtTextCreateTwo = "create window MyWindowTwo.win:keepall() as select string as a2, intPrimitive as b2 from " + SupportBean.class.getName();
        EPStatement stmtCreateTwo = epService.getEPAdministrator().createEQL(stmtTextCreateTwo);
        stmtCreateTwo.addListener(listenerWindowTwo);

        // create delete stmt
        String stmtTextDelete = "on " + SupportMarketDataBean.class.getName() + "(volume=1) delete from MyWindowOne where symbol = a1";
        epService.getEPAdministrator().createEQL(stmtTextDelete);

        stmtTextDelete = "on " + SupportMarketDataBean.class.getName() + "(volume=0) delete from MyWindowTwo where symbol = a2";
        epService.getEPAdministrator().createEQL(stmtTextDelete);

        // create insert into
        String stmtTextInsert = "insert into MyWindowOne select string as a1, intPrimitive as b1 from " + SupportBean.class.getName() + "(boolPrimitive = true)";
        epService.getEPAdministrator().createEQL(stmtTextInsert);

        stmtTextInsert = "insert into MyWindowTwo select string as a2, intPrimitive as b2 from " + SupportBean.class.getName() + "(boolPrimitive = false)";
        epService.getEPAdministrator().createEQL(stmtTextInsert);

        // create consumers
        String stmtTextSelectOne = "select a1, b1, a2, b2 " +
                                   " from MyWindowOne as s0," +
                                         "MyWindowTwo as s1 where s0.a1 = s1.a2";
        EPStatement stmtSelectOne = epService.getEPAdministrator().createEQL(stmtTextSelectOne);
        stmtSelectOne.addListener(listenerStmtOne);

        sendSupportBean(true, "S0", 1);
        assertFalse(listenerStmtOne.isInvoked());

        sendSupportBean(false, "S0", 2);
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"S0", 1, "S0", 2});

        sendSupportBean(false, "S1", 3);
        assertFalse(listenerStmtOne.isInvoked());

        sendSupportBean(true, "S1", 4);
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"S1", 4, "S1", 3});

        sendSupportBean(true, "S1", 5);
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"S1", 5, "S1", 3});

        sendSupportBean(false, "S1", 6);
        assertEquals(2, listenerStmtOne.getLastNewData().length);
        listenerStmtOne.reset();

        // delete and insert back in
        sendMarketBean("S0", 0);
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetOldAndReset(), fields, new Object[] {"S0", 1, "S0", 2});

        sendSupportBean(false, "S0", 7);
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"S0", 1, "S0", 7});

        // delete and insert back in
        sendMarketBean("S0", 1);
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetOldAndReset(), fields, new Object[] {"S0", 1, "S0", 7});

        sendSupportBean(true, "S0", 8);
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"S0", 8, "S0", 7});
    }

    public void testJoinBetweenSameNamed()
    {
        String[] fields = new String[] {"a0", "b0", "a1", "b1"};

        // create window
        String stmtTextCreateOne = "create window MyWindow.win:keepall() as select string as a, intPrimitive as b from " + SupportBean.class.getName();
        EPStatement stmtCreateOne = epService.getEPAdministrator().createEQL(stmtTextCreateOne);
        stmtCreateOne.addListener(listenerWindow);

        // create delete stmt
        String stmtTextDelete = "on " + SupportMarketDataBean.class.getName() + " delete from MyWindow where symbol = a";
        epService.getEPAdministrator().createEQL(stmtTextDelete);

        // create insert into
        String stmtTextInsert = "insert into MyWindow select string as a, intPrimitive as b from " + SupportBean.class.getName();
        epService.getEPAdministrator().createEQL(stmtTextInsert);

        // create consumers
        String stmtTextSelectOne = "select s0.a as a0, s0.b as b0, s1.a as a1, s1.b as b1 " +
                                   " from MyWindow as s0," +
                                         "MyWindow as s1 where s0.a = s1.a";
        EPStatement stmtSelectOne = epService.getEPAdministrator().createEQL(stmtTextSelectOne);
        stmtSelectOne.addListener(listenerStmtOne);

        sendSupportBean("E1", 1);
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"E1", 1, "E1", 1});

        sendSupportBean("E2", 2);
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"E2", 2, "E2", 2});

        sendMarketBean("E1", 1);
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetOldAndReset(), fields, new Object[] {"E1", 1, "E1", 1});

        sendMarketBean("E0", 0);
        assertFalse(listenerStmtOne.isInvoked());
    }

    public void testJoinSingleInsertOneWindow()
    {
        String[] fields = new String[] {"a1", "b1", "a2", "b2"};

        // create window
        String stmtTextCreateOne = "create window MyWindowOne.win:keepall() as select string as a1, intPrimitive as b1 from " + SupportBean.class.getName();
        EPStatement stmtCreateOne = epService.getEPAdministrator().createEQL(stmtTextCreateOne);
        stmtCreateOne.addListener(listenerWindow);

        // create window
        String stmtTextCreateTwo = "create window MyWindowTwo.win:keepall() as select string as a2, intPrimitive as b2 from " + SupportBean.class.getName();
        EPStatement stmtCreateTwo = epService.getEPAdministrator().createEQL(stmtTextCreateTwo);
        stmtCreateTwo.addListener(listenerWindowTwo);

        // create delete stmt
        String stmtTextDelete = "on " + SupportMarketDataBean.class.getName() + "(volume=1) delete from MyWindowOne where symbol = a1";
        epService.getEPAdministrator().createEQL(stmtTextDelete);

        stmtTextDelete = "on " + SupportMarketDataBean.class.getName() + "(volume=0) delete from MyWindowTwo where symbol = a2";
        epService.getEPAdministrator().createEQL(stmtTextDelete);

        // create insert into
        String stmtTextInsert = "insert into MyWindowOne select string as a1, intPrimitive as b1 from " + SupportBean.class.getName() + "(boolPrimitive = true)";
        epService.getEPAdministrator().createEQL(stmtTextInsert);

        stmtTextInsert = "insert into MyWindowTwo select string as a2, intPrimitive as b2 from " + SupportBean.class.getName() + "(boolPrimitive = false)";
        epService.getEPAdministrator().createEQL(stmtTextInsert);

        // create consumers
        String stmtTextSelectOne = "select a1, b1, a2, b2 " +
                                   " from MyWindowOne as s0," +
                                         "MyWindowTwo as s1 where s0.a1 = s1.a2";
        EPStatement stmtSelectOne = epService.getEPAdministrator().createEQL(stmtTextSelectOne);
        stmtSelectOne.addListener(listenerStmtOne);

        sendSupportBean(true, "S0", 1);
        assertFalse(listenerStmtOne.isInvoked());

        sendSupportBean(false, "S0", 2);
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"S0", 1, "S0", 2});

        sendSupportBean(false, "S1", 3);
        assertFalse(listenerStmtOne.isInvoked());

        sendSupportBean(true, "S1", 4);
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"S1", 4, "S1", 3});

        sendSupportBean(true, "S1", 5);
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"S1", 5, "S1", 3});

        sendSupportBean(false, "S1", 6);
        assertEquals(2, listenerStmtOne.getLastNewData().length);
        listenerStmtOne.reset();

        // delete and insert back in
        sendMarketBean("S0", 0);
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetOldAndReset(), fields, new Object[] {"S0", 1, "S0", 2});

        sendSupportBean(false, "S0", 7);
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"S0", 1, "S0", 7});

        // delete and insert back in
        sendMarketBean("S0", 1);
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetOldAndReset(), fields, new Object[] {"S0", 1, "S0", 7});

        sendSupportBean(true, "S0", 8);
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"S0", 8, "S0", 7});
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

    private SupportBean sendSupportBean(boolean boolPrimitive, String string, int intPrimitive)
    {
        SupportBean bean = new SupportBean();
        bean.setBoolPrimitive(boolPrimitive);
        bean.setString(string);
        bean.setIntPrimitive(intPrimitive);
        epService.getEPRuntime().sendEvent(bean);
        return bean;
    }

    private void sendMarketBean(String symbol)
    {
        SupportMarketDataBean bean = new SupportMarketDataBean(symbol, 0, 0L, "");
        epService.getEPRuntime().sendEvent(bean);
    }

    private void sendMarketBean(String symbol, long volume)
    {
        SupportMarketDataBean bean = new SupportMarketDataBean(symbol, 0, volume, "");
        epService.getEPRuntime().sendEvent(bean);
    }
}
