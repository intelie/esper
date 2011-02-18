package com.espertech.esper.regression.db;

import com.espertech.esper.client.*;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBeanTwo;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.epl.SupportDatabaseService;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

import java.util.Properties;

public class TestDatabase3StreamOuterJoin extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        ConfigurationDBRef configDB = new ConfigurationDBRef();
        configDB.setDriverManagerConnection(SupportDatabaseService.DRIVER, SupportDatabaseService.FULLURL, new Properties());
        configDB.setConnectionLifecycleEnum(ConfigurationDBRef.ConnectionLifecycleEnum.RETAIN);

        Configuration configuration = SupportConfigFactory.getConfiguration();
        configuration.getEngineDefaults().getLogging().setEnableQueryPlan(true);
        configuration.getEngineDefaults().getLogging().setEnableJDBC(true);
        configuration.addDatabaseReference("MyDB", configDB);

        epService = EPServiceProviderManager.getProvider("TestDatabaseJoinRetained", configuration);
        epService.initialize();
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBeanTwo", SupportBeanTwo.class);
    }

    public void testInnerJoinLeftS0()
    {
        String stmtText = "select * from SupportBean.std:lastevent() sb" +
                " inner join " +
                " SupportBeanTwo.std:lastevent() sbt" +
                " on sb.string = sbt.stringTwo " +
                " inner join " +
                " sql:MyDB ['select myint from mytesttable'] as s1 " +
                "  on s1.myint = sbt.intPrimitiveTwo";

        EPStatement statement = epService.getEPAdministrator().createEPL(stmtText);
        listener = new SupportUpdateListener();
        statement.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBeanTwo("T1", 2));
        epService.getEPRuntime().sendEvent(new SupportBean("T1", -1));

        epService.getEPRuntime().sendEvent(new SupportBeanTwo("T2", 30));
        epService.getEPRuntime().sendEvent(new SupportBean("T2", -1));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), "sb.string,sbt.stringTwo,s1.myint".split(","), new Object[] {"T2", "T2", 30});

        epService.getEPRuntime().sendEvent(new SupportBean("T3", -1));
        epService.getEPRuntime().sendEvent(new SupportBeanTwo("T3", 40));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), "sb.string,sbt.stringTwo,s1.myint".split(","), new Object[] {"T3", "T3", 40});
    }

    public void testOuterJoinLeftS0()
    {
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBeanTwo", SupportBeanTwo.class);
        String stmtText = "select * from SupportBean.std:lastevent() sb" +
                " left outer join " +
                " SupportBeanTwo.std:lastevent() sbt" +
                " on sb.string = sbt.stringTwo " +
                " left outer join " +
                " sql:MyDB ['select myint from mytesttable'] as s1 " +
                "  on s1.myint = sbt.intPrimitiveTwo";

        EPStatement statement = epService.getEPAdministrator().createEPL(stmtText);
        listener = new SupportUpdateListener();
        statement.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBeanTwo("T1", 2));
        epService.getEPRuntime().sendEvent(new SupportBean("T1", 3));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), "sb.string,sbt.stringTwo,s1.myint".split(","), new Object[] {"T1", "T1", null});

        epService.getEPRuntime().sendEvent(new SupportBeanTwo("T2", 30));
        epService.getEPRuntime().sendEvent(new SupportBean("T2", -2));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), "sb.string,sbt.stringTwo,s1.myint".split(","), new Object[] {"T2", "T2", 30});

        epService.getEPRuntime().sendEvent(new SupportBean("T3", -1));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), "sb.string,sbt.stringTwo,s1.myint".split(","), new Object[] {"T3", null, null});

        epService.getEPRuntime().sendEvent(new SupportBeanTwo("T3", 40));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), "sb.string,sbt.stringTwo,s1.myint".split(","), new Object[] {"T3", "T3", 40});
    }
}
