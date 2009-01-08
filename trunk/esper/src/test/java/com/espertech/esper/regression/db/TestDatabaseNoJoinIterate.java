package com.espertech.esper.regression.db;

import com.espertech.esper.client.*;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.epl.SupportDatabaseService;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

import java.util.Properties;

public class TestDatabaseNoJoinIterate extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        ConfigurationDBRef configDB = new ConfigurationDBRef();
        configDB.setDriverManagerConnection(SupportDatabaseService.DRIVER, SupportDatabaseService.FULLURL, new Properties());
        configDB.setConnectionLifecycleEnum(ConfigurationDBRef.ConnectionLifecycleEnum.RETAIN);
        configDB.setConnectionCatalog("test");
        configDB.setConnectionReadOnly(true);
        configDB.setConnectionTransactionIsolation(1);
        configDB.setConnectionAutoCommit(true);

        Configuration configuration = SupportConfigFactory.getConfiguration();
        configuration.addDatabaseReference("MyDB", configDB);

        epService = EPServiceProviderManager.getProvider("TestDatabaseJoinRetained", configuration);
        epService.initialize();
    }

    public void testVariablesPoll()
    {
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);
        epService.getEPAdministrator().createEPL("create variable boolean queryvar_bool");
        epService.getEPAdministrator().createEPL("create variable int queryvar_int");
        epService.getEPAdministrator().createEPL("create variable int lower");
        epService.getEPAdministrator().createEPL("create variable int upper");
        epService.getEPAdministrator().createEPL("on SupportBean set queryvar_int=intPrimitive, queryvar_bool=boolPrimitive, lower=intPrimitive,upper=intBoxed");

        // Test int and singlerow
        String stmtText = "select myint from sql:MyDB ['select myint from mytesttable where ${queryvar_int} = mytesttable.mybigint']";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        listener = new SupportUpdateListener();
        stmt.addListener(listener);

        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), new String[] {"myint"}, null);

        sendSupportBeanEvent(5);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), new String[] {"myint"}, new Object[][] {{50}});

        stmt.destroy();
        assertFalse(listener.isInvoked());

        // Test boolean and multirow
        stmtText = "select * from sql:MyDB ['select mybigint, mybool from mytesttable where ${queryvar_bool} = mytesttable.mybool and myint between ${lower} and ${upper} order by mybigint']";
        stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        String[] fields = new String[] {"mybigint", "mybool"};
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, null);

        sendSupportBeanEvent(true, 10, 40);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{1L, true}, {4L, true}});

        sendSupportBeanEvent(false, 30, 80);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{3L, false}, {5L, false}, {6L, false}});

        sendSupportBeanEvent(true, 20, 30);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, null);

        sendSupportBeanEvent(true, 20, 60);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{4L, true}});
    }

    private void sendSupportBeanEvent(int intPrimitive)
    {
        SupportBean bean = new SupportBean();
        bean.setIntPrimitive(intPrimitive);
        epService.getEPRuntime().sendEvent(bean);
    }

    private void sendSupportBeanEvent(boolean boolPrimitive, int intPrimitive, int intBoxed)
    {
        SupportBean bean = new SupportBean();
        bean.setBoolPrimitive(boolPrimitive);
        bean.setIntPrimitive(intPrimitive);
        bean.setIntBoxed(intBoxed);
        epService.getEPRuntime().sendEvent(bean);
    }
}
