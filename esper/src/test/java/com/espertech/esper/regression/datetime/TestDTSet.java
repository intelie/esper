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
import com.espertech.esper.support.bean.SupportDateTime;
import com.espertech.esper.support.bean.lambda.LambdaAssertionUtil;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

import java.util.Calendar;
import java.util.Date;

public class TestDTSet extends TestCase {

    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp() {

        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("SupportDateTime", SupportDateTime.class);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();
    }

    public void testInput() {

        String[] fields = "val0,val1,val2".split(",");
        String eplFragment = "select " +
                "utildate.set('month', 0) as val0," +
                "msecdate.set('month', 0) as val1," +
                "caldate.set('month', 0) as val2" +
                " from SupportDateTime";
        EPStatement stmtFragment = epService.getEPAdministrator().createEPL(eplFragment);
        stmtFragment.addListener(listener);
        LambdaAssertionUtil.assertTypes(stmtFragment.getEventType(), fields, new Class[]{Date.class, Long.class, Calendar.class});

        String startTime = "2002-05-30T09:00:00.000";
        String expectedTime = "2002-1-30T09:00:00.000";
        epService.getEPRuntime().sendEvent(SupportDateTime.make(startTime));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, SupportDateTime.getArrayCoerced(expectedTime, "util", "msec", "cal"));
    }

    public void testFields() {

        String[] fields = "val0,val1,val2,val3,val4,val5,val6,val7".split(",");
        String eplFragment = "select " +
                "utildate.set('msec', 1) as val0," +
                "utildate.set('sec', 2) as val1," +
                "utildate.set('minutes', 3) as val2," +
                "utildate.set('hour', 13) as val3," +
                "utildate.set('day', 5) as val4," +
                "utildate.set('month', 6) as val5," +
                "utildate.set('year', 7) as val6," +
                "utildate.set('week', 8) as val7" +
                " from SupportDateTime";
        EPStatement stmtFragment = epService.getEPAdministrator().createEPL(eplFragment);
        stmtFragment.addListener(listener);
        LambdaAssertionUtil.assertTypes(stmtFragment.getEventType(), fields, new Class[]{Date.class, Date.class, Date.class, Date.class, Date.class, Date.class, Date.class, Date.class});

        String[] expected = {
                "2002-05-30T09:00:00.001",
                "2002-05-30T09:00:02.000",
                "2002-05-30T09:03:00.000",
                "2002-05-30T13:00:00.000",
                "2002-05-5T09:00:00.000",
                "2002-07-30T09:00:00.000",
                "0007-05-30T09:00:00.000",
                "2002-02-21T09:00:00.000",
        };
        String startTime = "2002-05-30T09:00:00.000";
        epService.getEPRuntime().sendEvent(SupportDateTime.make(startTime));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, SupportDateTime.getArrayCoerced(expected, "util"));
    }
}
