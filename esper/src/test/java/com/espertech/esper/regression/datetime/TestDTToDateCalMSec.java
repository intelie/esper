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

public class TestDTToDateCalMSec extends TestCase {

    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp() {

        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("SupportDateTime", SupportDateTime.class);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();
    }

    public void testToDateCalMilli() {

        String startTime = "2002-05-30T9:00:00.000";
        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(SupportDateTime.make(startTime).getMsecdate()));

        String[] fields = "val0,val1,val2,val3,val4,val5,val6,val7,val8,val9,val10,val11".split(",");
        String eplFragment = "select " +
                "current_timestamp.toDate() as val0," +
                "utildate.toDate() as val1," +
                "msecdate.toDate() as val2," +
                "caldate.toDate() as val3," +
                "current_timestamp.toCalendar() as val4," +
                "utildate.toCalendar() as val5," +
                "msecdate.toCalendar() as val6," +
                "caldate.toCalendar() as val7," +
                "current_timestamp.toMillisec() as val8," +
                "utildate.toMillisec() as val9," +
                "msecdate.toMillisec() as val10," +
                "caldate.toMillisec() as val11" +
                " from SupportDateTime";
        EPStatement stmtFragment = epService.getEPAdministrator().createEPL(eplFragment);
        stmtFragment.addListener(listener);
        LambdaAssertionUtil.assertTypes(stmtFragment.getEventType(), fields, new Class[]{Date.class, Date.class, Date.class, Date.class,
                Calendar.class, Calendar.class, Calendar.class, Calendar.class, Long.class, Long.class, Long.class, Long.class});

        epService.getEPRuntime().sendEvent(SupportDateTime.make(startTime));
        Object[] expectedUtil = SupportDateTime.getArrayCoerced(startTime, "util", "util", "util", "util");
        Object[] expectedCal = SupportDateTime.getArrayCoerced(startTime, "cal", "cal", "cal", "cal");
        Object[] expectedMsec = SupportDateTime.getArrayCoerced(startTime, "msec", "msec", "msec", "msec");
        Object[] expected = ArrayAssertionUtil.addArrayObjectArr(expectedUtil, expectedCal, expectedMsec);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, expected);

        epService.getEPRuntime().sendEvent(SupportDateTime.make(null));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {
                SupportDateTime.getValueCoerced(startTime, "util"), null, null, null,
                SupportDateTime.getValueCoerced(startTime, "cal"), null, null, null,
                SupportDateTime.getValueCoerced(startTime, "msec"), null, null, null});
    }
}
