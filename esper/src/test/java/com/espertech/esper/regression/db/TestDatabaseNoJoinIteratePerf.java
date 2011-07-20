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

import junit.framework.TestCase;
import com.espertech.esper.client.*;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.epl.SupportDatabaseService;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.bean.SupportBean;

import java.util.Properties;

public class TestDatabaseNoJoinIteratePerf extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        ConfigurationDBRef configDB = new ConfigurationDBRef();
        configDB.setDriverManagerConnection(SupportDatabaseService.DRIVER, SupportDatabaseService.FULLURL, new Properties());
        configDB.setConnectionLifecycleEnum(ConfigurationDBRef.ConnectionLifecycleEnum.RETAIN);
        configDB.setLRUCache(100000);
        configDB.setConnectionCatalog("test");

        Configuration configuration = SupportConfigFactory.getConfiguration();
        configuration.addDatabaseReference("MyDB", configDB);

        epService = EPServiceProviderManager.getProvider("TestDatabaseJoinRetained", configuration);
        epService.initialize();
    }

    public void testVariablesPollPerformanceCache()
    {
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);
        epService.getEPAdministrator().createEPL("create variable boolean queryvar_bool");
        epService.getEPAdministrator().createEPL("create variable int lower");
        epService.getEPAdministrator().createEPL("create variable int upper");
        epService.getEPAdministrator().createEPL("on SupportBean set queryvar_bool=boolPrimitive, lower=intPrimitive,upper=intBoxed");

        String stmtText = "select * from sql:MyDB ['select mybigint, mybool from mytesttable where ${queryvar_bool} = mytesttable.mybool and myint between ${lower} and ${upper} order by mybigint']";
        String[] fields = new String[] {"mybigint", "mybool"};
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        sendSupportBeanEvent(true, 20, 60);

        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i ++)
        {
            ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), fields, new Object[][] {{4L, true}});
        }
        long end = System.currentTimeMillis();
        long delta = end - start;
        assertTrue("delta=" + delta, delta < 1000);

        stmt.destroy();
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
