package net.esper.regression.eql;

import junit.framework.TestCase;
import net.esper.client.Configuration;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.client.soda.*;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.client.SupportConfigFactory;
import net.esper.support.util.ArrayAssertionUtil;
import net.esper.support.util.SupportUpdateListener;

public class TestNamedWindowOM extends TestCase
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

    public void testCompile()
    {
        String[] fields = new String[] {"key", "value"};
        String stmtTextCreate = "create window MyWindow.win:keepall() as select string as key, longBoxed as value from " + SupportBean.class.getName();
        EPStatementObjectModel modelCreate = epService.getEPAdministrator().compileEQL(stmtTextCreate);
        EPStatement stmtCreate = epService.getEPAdministrator().create(modelCreate);
        stmtCreate.addListener(listenerWindow);
        assertEquals("create window MyWindow.win:keepall() as select string as key, longBoxed as value from net.esper.support.bean.SupportBean", modelCreate.toEQL());

        String stmtTextInsert = "insert into MyWindow select string as key, longBoxed as value from " + SupportBean.class.getName();
        EPStatementObjectModel modelInsert = epService.getEPAdministrator().compileEQL(stmtTextInsert);
        EPStatement stmtInsert = epService.getEPAdministrator().create(modelInsert);

        String stmtTextSelectOne = "select key, value*2 as value from MyWindow";
        EPStatementObjectModel modelSelect = epService.getEPAdministrator().compileEQL(stmtTextSelectOne);
        EPStatement stmtSelectOne = epService.getEPAdministrator().create(modelSelect);
        stmtSelectOne.addListener(listenerStmtOne);
        assertEquals("select key, (value * 2) as value from MyWindow", modelSelect.toEQL());

        // send events
        sendSupportBean("E1", 10L);
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"E1", 20L});
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E1", 10L});

        sendSupportBean("E2", 20L);
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"E2", 40L});
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E2", 20L});

        // create delete stmt
        String stmtTextDelete = "on " + SupportMarketDataBean.class.getName() + " as s0 delete from MyWindow as s1 where s0.symbol = s1.key";
        EPStatementObjectModel modelDelete = epService.getEPAdministrator().compileEQL(stmtTextDelete);
        epService.getEPAdministrator().create(modelDelete);
        assertEquals("on net.esper.support.bean.SupportMarketDataBean as s0 delete from MyWindow as s1 where (s0.symbol = s1.key)", modelDelete.toEQL());

        // send delete event
        sendMarketBean("E1");
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetOldAndReset(), fields, new Object[] {"E1", 20L});
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetOldAndReset(), fields, new Object[] {"E1", 10L});

        // send delete event again, none deleted now
        sendMarketBean("E1");
        assertFalse(listenerStmtOne.isInvoked());
        assertFalse(listenerWindow.isInvoked());

        // send delete event
        sendMarketBean("E2");
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetOldAndReset(), fields, new Object[] {"E2", 40L});
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetOldAndReset(), fields, new Object[] {"E2", 20L});

        stmtSelectOne.destroy();
        stmtInsert.destroy();
        stmtCreate.destroy();
    }

    public void testOM()
    {
        String[] fields = new String[] {"key", "value"};
        EPStatementObjectModel model = new EPStatementObjectModel();
        model.setCreateWindow(CreateWindowClause.create("MyWindow").addView("win", "keepall"));
        model.setSelectClause(SelectClause.create()
                .addWithAlias("string", "key")
                .addWithAlias("longBoxed", "value"));
        model.setFromClause(FromClause.create(FilterStream.create(SupportBean.class.getName())));
        
        EPStatement stmtCreate = epService.getEPAdministrator().create(model);
        stmtCreate.addListener(listenerWindow);

        String stmtTextCreate = "create window MyWindow.win:keepall() as select string as key, longBoxed as value from " + SupportBean.class.getName();
        assertEquals(stmtTextCreate, model.toEQL());

        String stmtTextInsert = "insert into MyWindow select string as key, longBoxed as value from " + SupportBean.class.getName();
        EPStatementObjectModel modelInsert = epService.getEPAdministrator().compileEQL(stmtTextInsert);
        EPStatement stmtInsert = epService.getEPAdministrator().create(modelInsert);

        model = new EPStatementObjectModel();
        Expression multi = Expressions.multiply(Expressions.property("value"), Expressions.constant(2));
        model.setSelectClause(SelectClause.create()
                .add("key")
                .add(multi, "value"));
        model.setFromClause(FromClause.create(FilterStream.create("MyWindow")));

        EPStatement stmtSelectOne = epService.getEPAdministrator().create(model);
        stmtSelectOne.addListener(listenerStmtOne);
        String stmtTextSelectOne = "select key, (value * 2) as value from MyWindow";
        assertEquals(stmtTextSelectOne, model.toEQL());

        // send events
        sendSupportBean("E1", 10L);
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"E1", 20L});
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E1", 10L});

        sendSupportBean("E2", 20L);
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"E2", 40L});
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E2", 20L});

        // create delete stmt
        model = new EPStatementObjectModel();        
        model.setOnDelete(OnDeleteClause.create("MyWindow", "s1", Expressions.eqProperty("s0.symbol", "s1.key")));
        model.setFromClause(FromClause.create(FilterStream.create(SupportMarketDataBean.class.getName(), "s0")));
        epService.getEPAdministrator().create(model);
        String stmtTextDelete = "on " + SupportMarketDataBean.class.getName() + " as s0 delete from MyWindow as s1 where (s0.symbol = s1.key)";
        assertEquals(stmtTextDelete, model.toEQL());

        // send delete event
        sendMarketBean("E1");
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetOldAndReset(), fields, new Object[] {"E1", 20L});
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetOldAndReset(), fields, new Object[] {"E1", 10L});

        // send delete event again, none deleted now
        sendMarketBean("E1");
        assertFalse(listenerStmtOne.isInvoked());
        assertFalse(listenerWindow.isInvoked());

        // send delete event
        sendMarketBean("E2");
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetOldAndReset(), fields, new Object[] {"E2", 40L});
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetOldAndReset(), fields, new Object[] {"E2", 20L});

        stmtSelectOne.destroy();
        stmtInsert.destroy();
        stmtCreate.destroy();
    }

    private SupportBean sendSupportBean(String string, Long longBoxed)
    {
        SupportBean bean = new SupportBean();
        bean.setString(string);
        bean.setLongBoxed(longBoxed);
        epService.getEPRuntime().sendEvent(bean);
        return bean;
    }

    private void sendMarketBean(String symbol)
    {
        SupportMarketDataBean bean = new SupportMarketDataBean(symbol, 0, 0l, "");
        epService.getEPRuntime().sendEvent(bean);
    }
}
