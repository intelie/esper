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

import junit.framework.Assert;
import com.espertech.esper.client.*;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportUpdateListener;

import java.math.BigDecimal;

/**
 * Manual test for testing Oracle database access
 * <p>
 * Peculiarities for Oracle:
 *   - all column names are uppercase
 *   - integer and other number type maps to BigDecmial (getObject)
 *   - Oracle driver does not support obtaining metadata from prepared statement
 */
public class TestManualDatabaseJoinOracle
{
    // Oracle access
    private final static String DBUSER = "USER";
    private final static String DBPWD = "PWD";
    private final static String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private final static String FULLURL = "jdbc:oracle:thin:@host:port:sid";
    private final static String CATALOG = "CATALOG";
    private final static String TABLE = "mytesttable";
    private final static String TABLE_NAME = CATALOG + ".\"" + TABLE + "\"";

    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void manualHasMetaSQLStringParam()
    {
        ConfigurationDBRef dbconfig = getConfigOracle();
        Configuration configuration = getConfig(dbconfig);

        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();

        String table = CATALOG + ".\"" + TABLE + "\"";
        String sql = "select myint from " + table + " where ${string} = myvarchar'" +
                "metadatasql 'select myint from " + table + "'";
        String stmtText = "select MYINT from " +
                " sql:MyDB ['" + sql + "] as s0," +
                SupportBean.class.getName() + ".win:length(100) as s1";

        EPStatement statement = epService.getEPAdministrator().createEPL(stmtText);
        listener = new SupportUpdateListener();
        statement.addListener(listener);

        Assert.assertEquals(BigDecimal.class, statement.getEventType().getPropertyType("MYINT"));

        sendSupportBeanEvent("A");
        EventBean event = listener.assertOneGetNewAndReset();
        Assert.assertEquals(new BigDecimal(10), event.get("MYINT"));

        sendSupportBeanEvent("H");
        event = listener.assertOneGetNewAndReset();
        Assert.assertEquals(new BigDecimal(80), event.get("MYINT"));
    }

    public void manualHasMetaSQLIntParamLowercase()
    {
        ConfigurationDBRef dbconfig = getConfigOracle();
        dbconfig.setColumnChangeCase(ConfigurationDBRef.ColumnChangeCaseEnum.LOWERCASE);
        Configuration configuration = getConfig(dbconfig);
        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();

        String sql = "select mydouble from " + TABLE_NAME + " where ${intPrimitive} = myint'" +
                "metadatasql 'select mydouble from " + TABLE_NAME + "'";
        String stmtText = "select mydouble from " +
                " sql:MyDB ['" + sql + "] as s0," +
                SupportBean.class.getName() + ".win:length(100) as s1";

        EPStatement statement = epService.getEPAdministrator().createEPL(stmtText);
        listener = new SupportUpdateListener();
        statement.addListener(listener);

        Assert.assertEquals(BigDecimal.class, statement.getEventType().getPropertyType("mydouble"));

        sendSupportBeanEvent(10);
        BigDecimal result = (BigDecimal) listener.assertOneGetNewAndReset().get("mydouble");
        Assert.assertEquals(12, Math.round(result.doubleValue() * 10d));

        sendSupportBeanEvent(80);
        result = (BigDecimal) listener.assertOneGetNewAndReset().get("mydouble");
        Assert.assertEquals(82, Math.round(result.doubleValue() * 10d));
    }

    public void manualTypeMapped()
    {
        ConfigurationDBRef dbconfig = getConfigOracle();
        dbconfig.setColumnChangeCase(ConfigurationDBRef.ColumnChangeCaseEnum.LOWERCASE);
        dbconfig.addSqlTypesBinding(2, "int");
        Configuration configuration = getConfig(dbconfig);
        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();

        String sql = "select myint from " + TABLE_NAME + " where ${intPrimitive} = myint'" +
                "metadatasql 'select myint from " + TABLE_NAME + "'";
        String stmtText = "select myint from " +
                " sql:MyDB ['" + sql + "] as s0," +
                SupportBean.class.getName() + ".win:length(100) as s1";

        EPStatement statement = epService.getEPAdministrator().createEPL(stmtText);
        listener = new SupportUpdateListener();
        statement.addListener(listener);

        Assert.assertEquals(Integer.class, statement.getEventType().getPropertyType("myint"));

        sendSupportBeanEvent(10);
        Assert.assertEquals(10, listener.assertOneGetNewAndReset().get("myint"));

        sendSupportBeanEvent(80);
        Assert.assertEquals(80, listener.assertOneGetNewAndReset().get("myint"));
    }

    public void manualNoMetaLexAnalysis()
    {
        ConfigurationDBRef dbconfig = getConfigOracle();
        dbconfig.setColumnChangeCase(ConfigurationDBRef.ColumnChangeCaseEnum.LOWERCASE);
        Configuration configuration = getConfig(dbconfig);

        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();

        String sql = "select mydouble from " + TABLE_NAME + " where ${intPrimitive} = myint";
        run(sql);
    }

    public void manualNoMetaLexAnalysisGroup()
    {
        ConfigurationDBRef dbconfig = getConfigOracle();
        dbconfig.setColumnChangeCase(ConfigurationDBRef.ColumnChangeCaseEnum.LOWERCASE);
        Configuration configuration = getConfig(dbconfig);

        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();

        String sql = "select mydouble, sum(myint) from " + TABLE_NAME + " where ${intPrimitive} = myint group by mydouble";
        run(sql);
    }

    public void manualPlaceholderWhere()
    {
        ConfigurationDBRef dbconfig = getConfigOracle();
        dbconfig.setColumnChangeCase(ConfigurationDBRef.ColumnChangeCaseEnum.LOWERCASE);
        Configuration configuration = getConfig(dbconfig);

        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();

        String sql = "select mydouble from " + TABLE_NAME + " ${$ESPER-SAMPLE-WHERE} where ${intPrimitive} = myint";
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

        Assert.assertEquals(BigDecimal.class, statement.getEventType().getPropertyType("mydouble"));

        sendSupportBeanEvent(10);
        BigDecimal result = (BigDecimal) listener.assertOneGetNewAndReset().get("mydouble");
        Assert.assertEquals(12, Math.round(result.doubleValue() * 10d));

        sendSupportBeanEvent(80);
        result = (BigDecimal) listener.assertOneGetNewAndReset().get("mydouble");
        Assert.assertEquals(82, Math.round(result.doubleValue() * 10d));
    }

    private ConfigurationDBRef getConfigOracle()
    {
        ConfigurationDBRef configDB = new ConfigurationDBRef();
        configDB.setDriverManagerConnection(DRIVER, FULLURL, DBUSER, DBPWD);
        configDB.setConnectionLifecycleEnum(ConfigurationDBRef.ConnectionLifecycleEnum.RETAIN);
        configDB.setConnectionCatalog(CATALOG);
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
