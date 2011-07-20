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

package com.espertech.esper.regression.datetime;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.time.CurrentTimeEvent;
import com.espertech.esper.support.bean.SupportDateTime;
import com.espertech.esper.support.bean.lambda.LambdaAssertionUtil;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

import java.util.Calendar;
import java.util.Date;

public class TestDTWithTime extends TestCase {

    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp() {

        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("SupportDateTime", SupportDateTime.class);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();
    }

    public void testWithTime() {

        epService.getEPAdministrator().createEPL("create variable int varhour");
        epService.getEPAdministrator().createEPL("create variable int varmin");
        epService.getEPAdministrator().createEPL("create variable int varsec");
        epService.getEPAdministrator().createEPL("create variable int varmsec");
        String startTime = "2002-05-30T9:00:00.000";
        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(SupportDateTime.make(startTime).getMsecdate()));

        String[] fields = "val0,val1,val2,val3".split(",");
        String eplFragment = "select " +
                "current_timestamp.withTime(varhour, varmin, varsec, varmsec) as val0," +
                "utildate.withTime(varhour, varmin, varsec, varmsec) as val1," +
                "msecdate.withTime(varhour, varmin, varsec, varmsec) as val2," +
                "caldate.withTime(varhour, varmin, varsec, varmsec) as val3" +
                " from SupportDateTime";
        EPStatement stmtFragment = epService.getEPAdministrator().createEPL(eplFragment);
        stmtFragment.addListener(listener);
        LambdaAssertionUtil.assertTypes(stmtFragment.getEventType(), fields, new Class[]{Long.class, Date.class, Long.class, Calendar.class});

        epService.getEPRuntime().sendEvent(SupportDateTime.make(null));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {SupportDateTime.getValueCoerced(startTime, "msec"), null, null, null});

        String expectedTime = "2002-05-30T09:00:00.000";
        epService.getEPRuntime().setVariableValue("varhour", null); // variable is null
        epService.getEPRuntime().sendEvent(SupportDateTime.make(startTime));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, SupportDateTime.getArrayCoerced(expectedTime, "msec", "util", "msec", "cal"));

        expectedTime = "2002-05-30T1:02:03.004";
        epService.getEPRuntime().setVariableValue("varhour", 1);
        epService.getEPRuntime().setVariableValue("varmin", 2);
        epService.getEPRuntime().setVariableValue("varsec", 3);
        epService.getEPRuntime().setVariableValue("varmsec", 4);
        epService.getEPRuntime().sendEvent(SupportDateTime.make(startTime));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, SupportDateTime.getArrayCoerced(expectedTime, "msec", "util", "msec", "cal"));

        expectedTime = "2002-05-30T0:00:00.006";
        epService.getEPRuntime().setVariableValue("varhour", 0);
        epService.getEPRuntime().setVariableValue("varmin", null);
        epService.getEPRuntime().setVariableValue("varsec", null);
        epService.getEPRuntime().setVariableValue("varmsec", 6);
        epService.getEPRuntime().sendEvent(SupportDateTime.make(startTime));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, SupportDateTime.getArrayCoerced(expectedTime, "msec", "util", "msec", "cal"));
    }
}
