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

import java.util.Map;
import java.util.HashMap;

public class TestNamedWindowTypes extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listenerWindow;
    private SupportUpdateListener listenerStmtOne;
    private SupportUpdateListener listenerStmtDelete;

    public void setUp()
    {
        Map<String, Class> types = new HashMap<String, Class>();
        types.put("key", String.class);
        types.put("primitive", long.class);
        types.put("boxed", Long.class);

        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);
        config.addEventTypeAlias("MyMap", types);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();

        listenerWindow = new SupportUpdateListener();
        listenerStmtOne = new SupportUpdateListener();
        listenerStmtDelete = new SupportUpdateListener();
    }

    public void testNoWildcardWithAs()
    {
        // create window
        String stmtTextCreate = "create window MyWindow.win:keepall() as select string as a, longPrimitive as b, longBoxed as c from " + SupportBean.class.getName();
        EPStatement stmtCreate = epService.getEPAdministrator().createEQL(stmtTextCreate);
        stmtCreate.addListener(listenerWindow);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmtCreate.getEventType().getPropertyNames(), new String[] {"a", "b", "c"});
        assertEquals(String.class, stmtCreate.getEventType().getPropertyType("a"));
        assertEquals(long.class, stmtCreate.getEventType().getPropertyType("b"));
        assertEquals(Long.class, stmtCreate.getEventType().getPropertyType("c"));

        // create insert into
        String stmtTextInsertOne = "insert into MyWindow select string as a, longPrimitive as b, longBoxed as c from " + SupportBean.class.getName();
        epService.getEPAdministrator().createEQL(stmtTextInsertOne);

        String stmtTextInsertTwo = "insert into MyWindow select symbol as a, volume as b, volume as c from " + SupportMarketDataBean.class.getName();
        epService.getEPAdministrator().createEQL(stmtTextInsertTwo);

        String stmtTextInsertThree = "insert into MyWindow select key as a, boxed as b, primitive as c from MyMap";
        epService.getEPAdministrator().createEQL(stmtTextInsertThree);

        // create consumer
        String stmtTextSelectOne = "select a, b, c from MyWindow";
        EPStatement stmtSelectOne = epService.getEPAdministrator().createEQL(stmtTextSelectOne);
        stmtSelectOne.addListener(listenerStmtOne);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmtSelectOne.getEventType().getPropertyNames(), new String[] {"a", "b", "c"});
        assertEquals(String.class, stmtCreate.getEventType().getPropertyType("a"));
        assertEquals(long.class, stmtCreate.getEventType().getPropertyType("b"));
        assertEquals(Long.class, stmtCreate.getEventType().getPropertyType("c"));

        // create delete stmt
        String stmtTextDelete = "on " + SupportMarketDataBean.class.getName() + " as s0 delete from MyWindow as s1 where s0.symbol = s1.a";
        EPStatement stmtDelete = epService.getEPAdministrator().createEQL(stmtTextDelete);
        stmtDelete.addListener(listenerStmtDelete);

        sendSupportBean("E1", 1L, 10L);
        String[] fields = new String[] {"a", "b", "c"};
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E1", 1L, 10L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"E1", 1L, 10L});

        sendMarketBean("S1", 99L);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"S1", 99L, 99L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"S1", 99L, 99L});

        sendMap("M1", 100L, 101L);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"M1", 101L, 100L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"M1", 101L, 100L});
    }

    public void testNoWildcardNoAs()
    {
        // create window
        String stmtTextCreate = "create window MyWindow.win:keepall() as select string, longPrimitive, longBoxed from " + SupportBean.class.getName();
        EPStatement stmtCreate = epService.getEPAdministrator().createEQL(stmtTextCreate);
        stmtCreate.addListener(listenerWindow);

        // create insert into
        String stmtTextInsertOne = "insert into MyWindow select string, longPrimitive, longBoxed from " + SupportBean.class.getName();
        epService.getEPAdministrator().createEQL(stmtTextInsertOne);

        String stmtTextInsertTwo = "insert into MyWindow select symbol as string, volume as longPrimitive, volume as longBoxed from " + SupportMarketDataBean.class.getName();
        epService.getEPAdministrator().createEQL(stmtTextInsertTwo);

        String stmtTextInsertThree = "insert into MyWindow select key as string, boxed as longPrimitive, primitive as longBoxed from MyMap";
        epService.getEPAdministrator().createEQL(stmtTextInsertThree);

        // create consumer
        String stmtTextSelectOne = "select string, longPrimitive, longBoxed from MyWindow";
        EPStatement stmtSelectOne = epService.getEPAdministrator().createEQL(stmtTextSelectOne);
        stmtSelectOne.addListener(listenerStmtOne);

        sendSupportBean("E1", 1L, 10L);
        String[] fields = new String[] {"string", "longPrimitive", "longBoxed"};
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E1", 1L, 10L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"E1", 1L, 10L});

        sendMarketBean("S1", 99L);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"S1", 99L, 99L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"S1", 99L, 99L});

        sendMap("M1", 100L, 101L);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"M1", 101L, 100L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"M1", 101L, 100L});
    }

    public void testWildcardNoFields()
    {
        // create window
        String stmtTextCreate = "create window MyWindow.win:keepall() as select * from " + SupportBean_A.class.getName();
        EPStatement stmtCreate = epService.getEPAdministrator().createEQL(stmtTextCreate);
        stmtCreate.addListener(listenerWindow);

        // create insert into
        String stmtTextInsertOne = "insert into MyWindow select * from " + SupportBean_A.class.getName();
        epService.getEPAdministrator().createEQL(stmtTextInsertOne);

        // create consumer
        String stmtTextSelectOne = "select id from MyWindow";
        EPStatement stmtSelectOne = epService.getEPAdministrator().createEQL(stmtTextSelectOne);
        stmtSelectOne.addListener(listenerStmtOne);

        epService.getEPRuntime().sendEvent(new SupportBean_A("E1"));
        String[] fields = new String[] {"id"};
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E1"});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"E1"});
    }

    public void testNoSpecificationBean()
    {
        // create window
        String stmtTextCreate = "create window MyWindow.win:keepall() as " + SupportBean_A.class.getName();
        EPStatement stmtCreate = epService.getEPAdministrator().createEQL(stmtTextCreate);
        stmtCreate.addListener(listenerWindow);

        // create insert into
        String stmtTextInsertOne = "insert into MyWindow select * from " + SupportBean_A.class.getName();
        epService.getEPAdministrator().createEQL(stmtTextInsertOne);

        // create consumer
        String stmtTextSelectOne = "select id from MyWindow";
        EPStatement stmtSelectOne = epService.getEPAdministrator().createEQL(stmtTextSelectOne);
        stmtSelectOne.addListener(listenerStmtOne);

        epService.getEPRuntime().sendEvent(new SupportBean_A("E1"));
        String[] fields = new String[] {"id"};
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E1"});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"E1"});
    }

    public void testWildcardWithFields()
    {
        // create window
        String stmtTextCreate = "create window MyWindow.win:keepall() as select *, id as myid from " + SupportBean_A.class.getName();
        EPStatement stmtCreate = epService.getEPAdministrator().createEQL(stmtTextCreate);
        stmtCreate.addListener(listenerWindow);

        // create insert into
        String stmtTextInsertOne = "insert into MyWindow select *, id || 'A' as myid from " + SupportBean_A.class.getName();
        epService.getEPAdministrator().createEQL(stmtTextInsertOne);

        // create consumer
        String stmtTextSelectOne = "select id, myid from MyWindow";
        EPStatement stmtSelectOne = epService.getEPAdministrator().createEQL(stmtTextSelectOne);
        stmtSelectOne.addListener(listenerStmtOne);

        epService.getEPRuntime().sendEvent(new SupportBean_A("E1"));
        String[] fields = new String[] {"id", "myid"};
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E1", "E1A"});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"E1", "E1A"});
    }

    private SupportBean sendSupportBean(String string, long longPrimitive, Long longBoxed)
    {
        SupportBean bean = new SupportBean();
        bean.setString(string);
        bean.setLongPrimitive(longPrimitive);
        bean.setLongBoxed(longBoxed);
        epService.getEPRuntime().sendEvent(bean);
        return bean;
    }

    private void sendMarketBean(String symbol, long volume)
    {
        SupportMarketDataBean bean = new SupportMarketDataBean(symbol, 0, volume, "");
        epService.getEPRuntime().sendEvent(bean);
    }

    private void sendMap(String key, long primitive, Long boxed)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("key", key);
        map.put("primitive", primitive);
        map.put("boxed", boxed);        
        epService.getEPRuntime().sendEvent(map, "MyMap");
    }
}
