package com.espertech.esper.regression.db;

import junit.framework.TestCase;
import com.espertech.esper.client.*;
import com.espertech.esper.client.time.CurrentTimeEvent;
import com.espertech.esper.client.soda.*;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.epl.SupportDatabaseService;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBean_A;
import com.espertech.esper.support.bean.SupportBeanComplexProps;
import com.espertech.esper.support.bean.SupportBean_S0;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.EventType;
import com.espertech.esper.util.SerializableObjectCopier;

import java.util.Properties;
import java.math.BigDecimal;
import java.sql.*;

public class TestDatabaseDataSourceFactory extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void testDBCP()
    {
        Properties props = new Properties();
        props.put("driverClassName", SupportDatabaseService.DRIVER);
        props.put("url", SupportDatabaseService.FULLURL);
        props.put("username", SupportDatabaseService.DBUSER);
        props.put("password", SupportDatabaseService.DBPWD);

        ConfigurationDBRef configDB = new ConfigurationDBRef();
        configDB.setDataSourceFactoryDBCP(props);
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
        String stmtText = "select myInt from " +
                " sql:MyDB ['select myInt from mytesttable where ${intPrimitive} = mytesttable.mybigint'] as s0," +
                SupportBean.class.getName() + ".win:keepall() as s1";
        EPStatement statement = epService.getEPAdministrator().createEPL(stmtText);

        String[] fields = new String[] {"myint"};
        listener = new SupportUpdateListener();
        statement.addListener(listener);

        sendSupportBeanEvent(10);
        ArrayAssertionUtil.assertEqualsExactOrder(statement.iterator(), fields, new Object[][] {{100}});
    }

    private void sendSupportBeanEvent(int intPrimitive)
    {
        SupportBean bean = new SupportBean();
        bean.setIntPrimitive(intPrimitive);
        epService.getEPRuntime().sendEvent(bean);
    }
}
