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
import com.espertech.esper.client.util.DateTime;
import com.espertech.esper.support.bean.SupportDateTime;
import com.espertech.esper.support.bean.SupportTimeStartEndA;
import com.espertech.esper.support.bean.lambda.LambdaAssertionUtil;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

public class TestDTBetween extends TestCase {

    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp() {

        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("SupportDateTime", SupportDateTime.class);
        config.addEventType("SupportTimeStartEndA", SupportTimeStartEndA.class);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();
    }

    public void testIncludeEndpoints() {

        String startTime = "2002-05-30T9:00:00.000";
        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(SupportDateTime.make(startTime).getMsecdate()));

        String[] fieldsCurrentTs = "val0,val1,val2,val3,val4,val5,val6".split(",");
        String eplCurrentTS = "select " +
                "current_timestamp.after(msecdateStart) as val0, " +
                "current_timestamp.between(msecdateStart, msecdateEnd) as val1, " +
                "current_timestamp.between(utildateStart, caldateEnd) as val2, " +
                "current_timestamp.between(caldateStart, utildateEnd) as val3, " +
                "current_timestamp.between(utildateStart, utildateEnd) as val4, " +
                "current_timestamp.between(caldateStart, caldateEnd) as val5, " +
                "current_timestamp.between(caldateEnd, caldateStart) as val6 " +
                "from SupportTimeStartEndA";
        EPStatement stmtCurrentTs = epService.getEPAdministrator().createEPL(eplCurrentTS);
        stmtCurrentTs.addListener(listener);
        LambdaAssertionUtil.assertTypesAllSame(stmtCurrentTs.getEventType(), fieldsCurrentTs, Boolean.class);

        epService.getEPRuntime().sendEvent(SupportTimeStartEndA.make("E1", "2002-05-30T8:59:59.999", 0));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fieldsCurrentTs, new Object[]{true, false, false, false, false, false, false});

        epService.getEPRuntime().sendEvent(SupportTimeStartEndA.make("E1", "2002-05-30T8:59:59.999", 1));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fieldsCurrentTs, new Object[]{true, true, true, true, true, true, true});

        epService.getEPRuntime().sendEvent(SupportTimeStartEndA.make("E1", "2002-05-30T8:59:59.999", 100));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fieldsCurrentTs, new Object[]{true, true, true, true, true, true, true});

        epService.getEPRuntime().sendEvent(SupportTimeStartEndA.make("E1", "2002-05-30T9:00:00.000", 0));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fieldsCurrentTs, new Object[]{false, true, true, true, true, true, true});

        epService.getEPRuntime().sendEvent(SupportTimeStartEndA.make("E1", "2002-05-30T9:00:00.000", 100));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fieldsCurrentTs, new Object[]{false, true, true, true, true, true, true});

        epService.getEPRuntime().sendEvent(SupportTimeStartEndA.make("E1", "2002-05-30T9:00:00.001", 100));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fieldsCurrentTs, new Object[]{false, false, false, false, false, false, false});
        stmtCurrentTs.destroy();

        // test calendar field and constants
        epService.getEPAdministrator().getConfiguration().addImport(DateTime.class);
        String[] fieldsConstants = "val0,val1,val2,val3".split(",");
        String eplConstants = "select " +
                "msecdateStart.between(DateTime.toCalendar('2002-05-30T9:00:00.000', \"yyyy-MM-dd'T'HH:mm:ss.SSS\"), DateTime.toCalendar('2002-05-30T9:01:00.000', \"yyyy-MM-dd'T'HH:mm:ss.SSS\")) as val0, " +
                "utildateStart.between(DateTime.toCalendar('2002-05-30T9:00:00.000', \"yyyy-MM-dd'T'HH:mm:ss.SSS\"), DateTime.toCalendar('2002-05-30T9:01:00.000', \"yyyy-MM-dd'T'HH:mm:ss.SSS\")) as val1, " +
                "caldateStart.between(DateTime.toCalendar('2002-05-30T9:00:00.000', \"yyyy-MM-dd'T'HH:mm:ss.SSS\"), DateTime.toCalendar('2002-05-30T9:01:00.000', \"yyyy-MM-dd'T'HH:mm:ss.SSS\")) as val2, " +
                "msecdateStart.between(DateTime.toCalendar('2002-05-30T9:01:00.000', \"yyyy-MM-dd'T'HH:mm:ss.SSS\"), DateTime.toCalendar('2002-05-30T9:00:00.000', \"yyyy-MM-dd'T'HH:mm:ss.SSS\")) as val3 " +
                "from SupportTimeStartEndA";
        EPStatement stmtConstants = epService.getEPAdministrator().createEPL(eplConstants);
        stmtConstants.addListener(listener);
        LambdaAssertionUtil.assertTypesAllSame(stmtConstants.getEventType(), fieldsConstants, Boolean.class);

        epService.getEPRuntime().sendEvent(SupportTimeStartEndA.make("E1", "2002-05-30T8:59:59.999", 0));
        ArrayAssertionUtil.assertAllValuesSame(listener.assertOneGetNewAndReset(), fieldsConstants, false);

        epService.getEPRuntime().sendEvent(SupportTimeStartEndA.make("E2", "2002-05-30T9:00:00.000", 0));
        ArrayAssertionUtil.assertAllValuesSame(listener.assertOneGetNewAndReset(), fieldsConstants, true);

        epService.getEPRuntime().sendEvent(SupportTimeStartEndA.make("E2", "2002-05-30T9:00:05.000", 0));
        ArrayAssertionUtil.assertAllValuesSame(listener.assertOneGetNewAndReset(), fieldsConstants, true);

        epService.getEPRuntime().sendEvent(SupportTimeStartEndA.make("E2", "2002-05-30T9:00:59.999", 0));
        ArrayAssertionUtil.assertAllValuesSame(listener.assertOneGetNewAndReset(), fieldsConstants, true);

        epService.getEPRuntime().sendEvent(SupportTimeStartEndA.make("E2", "2002-05-30T9:01:00.000", 0));
        ArrayAssertionUtil.assertAllValuesSame(listener.assertOneGetNewAndReset(), fieldsConstants, true);

        epService.getEPRuntime().sendEvent(SupportTimeStartEndA.make("E2", "2002-05-30T9:01:00.001", 0));
        ArrayAssertionUtil.assertAllValuesSame(listener.assertOneGetNewAndReset(), fieldsConstants, false);

        stmtConstants.destroy();
    }

    public void testExcludeEndpoints() {

        String startTime = "2002-05-30T9:00:00.000";
        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(SupportDateTime.make(startTime).getMsecdate()));
        epService.getEPAdministrator().createEPL("create variable boolean VAR_TRUE = true");
        epService.getEPAdministrator().createEPL("create variable boolean VAR_FALSE = false");

        String[] fieldsCurrentTs = "val0,val1,val2,val3,val4,val5,val6,val7".split(",");
        String eplCurrentTS = "select " +
                "current_timestamp.between(msecdateStart, msecdateEnd, true, true) as val0, " +
                "current_timestamp.between(msecdateStart, msecdateEnd, true, false) as val1, " +
                "current_timestamp.between(msecdateStart, msecdateEnd, false, true) as val2, " +
                "current_timestamp.between(msecdateStart, msecdateEnd, false, false) as val3, " +
                "current_timestamp.between(msecdateStart, msecdateEnd, VAR_TRUE, VAR_TRUE) as val4, " +
                "current_timestamp.between(msecdateStart, msecdateEnd, VAR_TRUE, VAR_FALSE) as val5, " +
                "current_timestamp.between(msecdateStart, msecdateEnd, VAR_FALSE, VAR_TRUE) as val6, " +
                "current_timestamp.between(msecdateStart, msecdateEnd, VAR_FALSE, VAR_FALSE) as val7 " +
                "from SupportTimeStartEndA";
        EPStatement stmtCurrentTs = epService.getEPAdministrator().createEPL(eplCurrentTS);
        stmtCurrentTs.addListener(listener);
        LambdaAssertionUtil.assertTypesAllSame(stmtCurrentTs.getEventType(), fieldsCurrentTs, Boolean.class);

        epService.getEPRuntime().sendEvent(SupportTimeStartEndA.make("E1", "2002-05-30T8:59:59.999", 0));
        ArrayAssertionUtil.assertAllValuesSame(listener.assertOneGetNewAndReset(), fieldsCurrentTs, false);

        epService.getEPRuntime().sendEvent(SupportTimeStartEndA.make("E1", "2002-05-30T8:59:59.999", 1));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fieldsCurrentTs, new Object[]{true, false, true, false, true, false, true, false});

        epService.getEPRuntime().sendEvent(SupportTimeStartEndA.make("E1", "2002-05-30T8:59:59.999", 2));
        ArrayAssertionUtil.assertAllValuesSame(listener.assertOneGetNewAndReset(), fieldsCurrentTs, true);

        epService.getEPRuntime().sendEvent(SupportTimeStartEndA.make("E1", "2002-05-30T9:00:00.000", 1));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fieldsCurrentTs, new Object[]{true, true, false, false, true, true, false, false});

        stmtCurrentTs.destroy();

        // test calendar field and constants
        epService.getEPAdministrator().getConfiguration().addImport(DateTime.class);
        String[] fieldsConstants = "val0,val1,val2,val3".split(",");
        String eplConstants = "select " +
                "msecdateStart.between(DateTime.toCalendar('2002-05-30T9:00:00.000', \"yyyy-MM-dd'T'HH:mm:ss.SSS\"), DateTime.toCalendar('2002-05-30T9:01:00.000', \"yyyy-MM-dd'T'HH:mm:ss.SSS\"), true, true) as val0, " +
                "msecdateStart.between(DateTime.toCalendar('2002-05-30T9:00:00.000', \"yyyy-MM-dd'T'HH:mm:ss.SSS\"), DateTime.toCalendar('2002-05-30T9:01:00.000', \"yyyy-MM-dd'T'HH:mm:ss.SSS\"), true, false) as val1, " +
                "msecdateStart.between(DateTime.toCalendar('2002-05-30T9:00:00.000', \"yyyy-MM-dd'T'HH:mm:ss.SSS\"), DateTime.toCalendar('2002-05-30T9:01:00.000', \"yyyy-MM-dd'T'HH:mm:ss.SSS\"), false, true) as val2, " +
                "msecdateStart.between(DateTime.toCalendar('2002-05-30T9:00:00.000', \"yyyy-MM-dd'T'HH:mm:ss.SSS\"), DateTime.toCalendar('2002-05-30T9:01:00.000', \"yyyy-MM-dd'T'HH:mm:ss.SSS\"), false, false) as val3 " +
                "from SupportTimeStartEndA";
        EPStatement stmtConstants = epService.getEPAdministrator().createEPL(eplConstants);
        stmtConstants.addListener(listener);
        LambdaAssertionUtil.assertTypesAllSame(stmtConstants.getEventType(), fieldsConstants, Boolean.class);

        epService.getEPRuntime().sendEvent(SupportTimeStartEndA.make("E1", "2002-05-30T8:59:59.999", 0));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fieldsConstants, new Object[]{false, false, false, false});

        epService.getEPRuntime().sendEvent(SupportTimeStartEndA.make("E2", "2002-05-30T9:00:00.000", 0));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fieldsConstants, new Object[]{true, true, false, false});

        epService.getEPRuntime().sendEvent(SupportTimeStartEndA.make("E2", "2002-05-30T9:00:05.000", 0));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fieldsConstants, new Object[]{true, true, true, true});

        epService.getEPRuntime().sendEvent(SupportTimeStartEndA.make("E2", "2002-05-30T9:00:59.999", 0));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fieldsConstants, new Object[]{true, true, true, true});

        epService.getEPRuntime().sendEvent(SupportTimeStartEndA.make("E2", "2002-05-30T9:01:00.000", 0));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fieldsConstants, new Object[]{true, false, true, false});

        epService.getEPRuntime().sendEvent(SupportTimeStartEndA.make("E2", "2002-05-30T9:01:00.001", 0));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fieldsConstants, new Object[]{false, false, false, false});

        stmtConstants.destroy();
    }
}
