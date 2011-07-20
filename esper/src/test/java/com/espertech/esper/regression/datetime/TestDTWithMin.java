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

public class TestDTWithMin extends TestCase {

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
                "utildate.withMin('month') as val0," +
                "msecdate.withMin('month') as val1," +
                "caldate.withMin('month') as val2" +
                " from SupportDateTime";
        EPStatement stmtFragment = epService.getEPAdministrator().createEPL(eplFragment);
        stmtFragment.addListener(listener);
        LambdaAssertionUtil.assertTypes(stmtFragment.getEventType(), fields, new Class[]{Date.class, Long.class, Calendar.class});

        String startTime = "2002-05-30T09:00:00.000";
        String expectedTime = "2002-01-30T09:00:00.000";
        epService.getEPRuntime().sendEvent(SupportDateTime.make(startTime));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, SupportDateTime.getArrayCoerced(expectedTime, "util", "msec", "cal"));
    }

    public void testFields() {

        String[] fields = "val0,val1,val2,val3,val4,val5,val6,val7".split(",");
        String eplFragment = "select " +
                "utildate.withMin('msec') as val0," +
                "utildate.withMin('sec') as val1," +
                "utildate.withMin('minutes') as val2," +
                "utildate.withMin('hour') as val3," +
                "utildate.withMin('day') as val4," +
                "utildate.withMin('month') as val5," +
                "utildate.withMin('year') as val6," +
                "utildate.withMin('week') as val7" +
                " from SupportDateTime";
        EPStatement stmtFragment = epService.getEPAdministrator().createEPL(eplFragment);
        stmtFragment.addListener(listener);
        LambdaAssertionUtil.assertTypes(stmtFragment.getEventType(), fields, new Class[]{Date.class, Date.class, Date.class, Date.class, Date.class, Date.class, Date.class, Date.class});

        String[] expected = {
                "2002-05-30T09:01:02.000",
                "2002-05-30T09:01:00.003",
                "2002-05-30T09:00:02.003",
                "2002-05-30T00:01:02.003",
                "2002-05-01T09:01:02.003",
                "2002-01-30T09:01:02.003",
                "0001-05-30T09:01:02.003",
                "2002-01-03T09:01:02.003",
        };
        String startTime = "2002-05-30T09:01:02.003";
        epService.getEPRuntime().sendEvent(SupportDateTime.make(startTime));
        //System.out.println("===> " + SupportDateTime.print(listener.assertOneGetNew().get("val7")));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, SupportDateTime.getArrayCoerced(expected, "util"));
    }
}
