/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.regression.db;

import com.espertech.esper.client.*;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.epl.SupportDatabaseService;
import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.Properties;

public class TestDatabaseJoinOptions extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void testHasMetaSQLStringParam()
    {
        ConfigurationDBRef dbconfig = getDBConfig();
        dbconfig.setColumnChangeCase(ConfigurationDBRef.ColumnChangeCaseEnum.UPPERCASE);
        Configuration configuration = getConfig(dbconfig);

        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();

        String sql = "select myint from mytesttable where ${string} = myvarchar'" +
                "metadatasql 'select myint from mytesttable'";
        String stmtText = "select MYINT from " +
                " sql:MyDB ['" + sql + "] as s0," +
                SupportBean.class.getName() + ".win:length(100) as s1";

        EPStatement statement = epService.getEPAdministrator().createEPL(stmtText);
        listener = new SupportUpdateListener();
        statement.addListener(listener);

        Assert.assertEquals(Integer.class, statement.getEventType().getPropertyType("MYINT"));

        sendSupportBeanEvent("A");
        Assert.assertEquals(10, listener.assertOneGetNewAndReset().get("MYINT"));

        sendSupportBeanEvent("H");
        Assert.assertEquals(80, listener.assertOneGetNewAndReset().get("MYINT"));
    }

    public void testTypeMapped()
    {
        ConfigurationDBRef dbconfig = getDBConfig();
        dbconfig.setColumnChangeCase(ConfigurationDBRef.ColumnChangeCaseEnum.LOWERCASE);
        dbconfig.addSqlTypesBinding(java.sql.Types.INTEGER, "string");
        Configuration configuration = getConfig(dbconfig);
        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();

        String sql = "select myint from mytesttable where ${intPrimitive} = myint'" +
                "metadatasql 'select myint from mytesttable'";
        String stmtText = "select myint from " +
                " sql:MyDB ['" + sql + "] as s0," +
                SupportBean.class.getName() + ".win:length(100) as s1";

        EPStatement statement = epService.getEPAdministrator().createEPL(stmtText);
        listener = new SupportUpdateListener();
        statement.addListener(listener);

        Assert.assertEquals(String.class, statement.getEventType().getPropertyType("myint"));

        sendSupportBeanEvent(10);
        Assert.assertEquals("10", listener.assertOneGetNewAndReset().get("myint"));

        sendSupportBeanEvent(80);
        Assert.assertEquals("80", listener.assertOneGetNewAndReset().get("myint"));
    }

    public void testNoMetaLexAnalysis()
    {
        ConfigurationDBRef dbconfig = getDBConfig();
        Configuration configuration = getConfig(dbconfig);

        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();

        String sql = "select mydouble from mytesttable where ${intPrimitive} = myint";
        run(sql);
    }

    public void testNoMetaLexAnalysisGroup()
    {
        ConfigurationDBRef dbconfig = getDBConfig();
        Configuration configuration = getConfig(dbconfig);

        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();

        String sql = "select mydouble, sum(myint) from mytesttable where ${intPrimitive} = myint group by mydouble";
        run(sql);
    }

    public void testPlaceholderWhere()
    {
        ConfigurationDBRef dbconfig = getDBConfig();
        Configuration configuration = getConfig(dbconfig);

        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();

        String sql = "select mydouble from mytesttable ${$ESPER-SAMPLE-WHERE} where ${intPrimitive} = myint";
        run(sql);
    }

    private void run(String sql)
    {
        String stmtText = "select mydouble from " +
                " sql:MyDB ['" + sql + "'] as s0," +
                SupportBean.class.getName() + ".win:length(100) as s1";

        EPStatement statement = epService.getEPAdministrator().createEPL(stmtText);
        listener = new SupportUpdateListener();
        statement.addListener(listener);

        Assert.assertEquals(Double.class, statement.getEventType().getPropertyType("mydouble"));

        sendSupportBeanEvent(10);
        Assert.assertEquals(1.2, listener.assertOneGetNewAndReset().get("mydouble"));

        sendSupportBeanEvent(80);
        Assert.assertEquals(8.2, listener.assertOneGetNewAndReset().get("mydouble"));
    }

    private ConfigurationDBRef getDBConfig()
    {
        ConfigurationDBRef configDB = new ConfigurationDBRef();
        configDB.setDriverManagerConnection(SupportDatabaseService.DRIVER, SupportDatabaseService.FULLURL, new Properties());
        configDB.setConnectionLifecycleEnum(ConfigurationDBRef.ConnectionLifecycleEnum.RETAIN);
        configDB.setConnectionCatalog("test");
        configDB.setConnectionReadOnly(true);
        configDB.setConnectionAutoCommit(true);
        return configDB;
    }

    private Configuration getConfig(ConfigurationDBRef configOracle)
    {
        Configuration configuration = SupportConfigFactory.getConfiguration();
        configuration.addDatabaseReference("MyDB", configOracle);
        configuration.getEngineDefaults().getLogging().setEnableExecutionDebug(true);

        return configuration;
    }

    private void sendSupportBeanEvent(String string)
    {
        SupportBean bean = new SupportBean();
        bean.setString(string);
        epService.getEPRuntime().sendEvent(bean);
    }

    private void sendSupportBeanEvent(int intPrimitive)
    {
        SupportBean bean = new SupportBean();
        bean.setIntPrimitive(intPrimitive);
        epService.getEPRuntime().sendEvent(bean);
    }
}
