package com.espertech.esper.regression.db;

import com.espertech.esper.client.*;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.epl.SupportDataSourceFactory;
import com.espertech.esper.support.epl.SupportDatabaseService;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

import java.util.Properties;

public class TestDatabaseDataSourceFactory extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void testDBCP() throws Exception
    {
        Properties props = new Properties();
        props.put("driverClassName", SupportDatabaseService.DRIVER);
        props.put("url", SupportDatabaseService.FULLURL);
        props.put("username", SupportDatabaseService.DBUSER);
        props.put("password", SupportDatabaseService.DBPWD);

        ConfigurationDBRef configDB = new ConfigurationDBRef();
        // for DBCP, use setDataSourceFactoryDBCP
        configDB.setDataSourceFactory(props, SupportDataSourceFactory.class.getName());
        configDB.setConnectionLifecycleEnum(ConfigurationDBRef.ConnectionLifecycleEnum.POOLED);

        Configuration configuration = SupportConfigFactory.getConfiguration();
        configuration.addDatabaseReference("MyDB", configDB);
        configuration.getEngineDefaults().getThreading().setInternalTimerEnabled(false);
    
        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();

        runAssertion();
    }

    private void runAssertion()
    {
        String stmtText = "select istream myint from " +
                " sql:MyDB ['select myint from mytesttable where ${intPrimitive} = mytesttable.mybigint'] as s0," +
                SupportBean.class.getName() + " as s1";
        EPStatement statement = epService.getEPAdministrator().createEPL(stmtText);

        String[] fields = new String[] {"myint"};
        listener = new SupportUpdateListener();
        statement.addListener(listener);

        sendSupportBeanEvent(10);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {100});

        sendSupportBeanEvent(6);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {60});
    }

    private void sendSupportBeanEvent(int intPrimitive)
    {
        SupportBean bean = new SupportBean();
        bean.setIntPrimitive(intPrimitive);
        epService.getEPRuntime().sendEvent(bean);
    }
}
