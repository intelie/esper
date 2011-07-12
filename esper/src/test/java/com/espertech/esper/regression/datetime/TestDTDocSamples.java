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

import java.sql.Date;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TestDTDocSamples extends TestCase {

    private EPServiceProvider epService;

    public void setUp() {

        Configuration config = SupportConfigFactory.getConfiguration();
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
    }

    public void testInput() {
        Map<String, Object> meta = new HashMap<String, Object>();
        meta.put("timeTaken", Date.class);
        epService.getEPAdministrator().getConfiguration().addEventType("RFIDEvent", meta);

        epService.getEPAdministrator().createEPL("select timeTaken.format() as timeTakenStr from RFIDEvent");
        epService.getEPAdministrator().createEPL("select timeTaken.get('month') as timeTakenMonth from RFIDEvent");
        epService.getEPAdministrator().createEPL("select timeTaken.getMonthOfYear() as timeTakenMonth from RFIDEvent");
        epService.getEPAdministrator().createEPL("select timeTaken.minus(2 minutes) as timeTakenMinus2Min from RFIDEvent");
        epService.getEPAdministrator().createEPL("select timeTaken.minus(2*60*1000) as timeTakenMinus2Min from RFIDEvent");
        epService.getEPAdministrator().createEPL("select timeTaken.plus(2 minutes) as timeTakenMinus2Min from RFIDEvent");
        epService.getEPAdministrator().createEPL("select timeTaken.plus(2*60*1000) as timeTakenMinus2Min from RFIDEvent");
        epService.getEPAdministrator().createEPL("select timeTaken.roundCeiling('min') as timeTakenRounded from RFIDEvent");
        epService.getEPAdministrator().createEPL("select timeTaken.roundFloor('min') as timeTakenRounded from RFIDEvent");
        epService.getEPAdministrator().createEPL("select timeTaken.set('month', 3) as timeTakenMonth from RFIDEvent");
        epService.getEPAdministrator().createEPL("select timeTaken.withDate(2002, 4, 30) as timeTakenDated from RFIDEvent");
        epService.getEPAdministrator().createEPL("select timeTaken.withMax('sec') as timeTakenMaxSec from RFIDEvent");
        epService.getEPAdministrator().createEPL("select timeTaken.toCalendar() as timeTakenCal from RFIDEvent");
        epService.getEPAdministrator().createEPL("select timeTaken.toDate() as timeTakenDate from RFIDEvent");
        epService.getEPAdministrator().createEPL("select timeTaken.toMillisec() as timeTakenLong from RFIDEvent");
    }
}
