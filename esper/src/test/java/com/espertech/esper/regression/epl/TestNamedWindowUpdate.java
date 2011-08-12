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

package com.espertech.esper.regression.epl;

import com.espertech.esper.client.time.CurrentTimeEvent;
import com.espertech.esper.support.bean.*;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import junit.framework.TestCase;
import com.espertech.esper.client.*;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportUpdateListener;

import java.util.HashMap;

public class TestNamedWindowUpdate extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listenerWindow;

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listenerWindow = new SupportUpdateListener();
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean_A", SupportBean_A.class);
    }

    public void testSubquerySelf() {
        // ESPER-507

        EPStatement stmt = epService.getEPAdministrator().createEPL("create window MyWindow.win:keepall() as SupportBean");
        epService.getEPAdministrator().createEPL("insert into MyWindow select * from SupportBean");

        // This is better done with "set intPrimitive = intPrimitive + 1"
        String epl = "@Name(\"Self Update\")\n" +
                "on SupportBean_A c\n" +
                "update MyWindow s\n" +
                "set intPrimitive = (select intPrimitive from MyWindow t where t.string = c.id) + 1\n" +
                "where s.string = c.id";
        epService.getEPAdministrator().createEPL(epl);
        
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        epService.getEPRuntime().sendEvent(new SupportBean("E2", 6));
        epService.getEPRuntime().sendEvent(new SupportBean_A("E1"));
        epService.getEPRuntime().sendEvent(new SupportBean_A("E1"));
        epService.getEPRuntime().sendEvent(new SupportBean_A("E2"));
        
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), "string,intPrimitive".split(","), new Object[][]{{"E1", 3}, {"E2", 7}});
    }

    public void testMultipleDataWindowIntersect() {
        String stmtTextCreate = "create window MyWindow.std:unique(string).win:length(2) as select * from SupportBean";
        EPStatement stmtCreate = epService.getEPAdministrator().createEPL(stmtTextCreate);
        stmtCreate.addListener(listenerWindow);

        String stmtTextInsertOne = "insert into MyWindow select * from SupportBean";
        epService.getEPAdministrator().createEPL(stmtTextInsertOne);

        String stmtTextUpdate = "on SupportBean_A update MyWindow set intPrimitive=intPrimitive*100 where string=id";
        epService.getEPAdministrator().createEPL(stmtTextUpdate);
        
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 2));
        epService.getEPRuntime().sendEvent(new SupportBean("E2", 3));
        epService.getEPRuntime().sendEvent(new SupportBean_A("E2"));
        EventBean[] newevents = listenerWindow.getLastNewData();
        EventBean[] oldevents = listenerWindow.getLastOldData();

        assertEquals(1, newevents.length);
        ArrayAssertionUtil.assertProps(newevents[0], "intPrimitive".split(","), new Object[] {300});
        assertEquals(2, oldevents.length);
        oldevents = ArrayAssertionUtil.sort(oldevents, "string");
        ArrayAssertionUtil.assertPropsPerRow(oldevents, "string,intPrimitive".split(","), new Object[][] {{"E1", 2}, {"E2", 3}});

        ArrayAssertionUtil.assertEqualsExactOrder(stmtCreate.iterator(), "string,intPrimitive".split(","), new Object[][] {{"E2", 300}});
    }
    
    public void testMultipleDataWindowIntersectOnUpdate() {
        SupportUpdateListener listener = new SupportUpdateListener();
        String[] fields = "company,value,total".split(",");

        // ESPER-568
        epService.getEPAdministrator().createEPL("create schema S2 ( company string, value double, total double)");
	    EPStatement stmtWin = epService.getEPAdministrator().createEPL("create window S2Win.win:time(25 hour).std:firstunique(company) as S2");
        epService.getEPAdministrator().createEPL("insert into S2Win select * from S2.std:firstunique(company)");
        epService.getEPAdministrator().createEPL("on S2 as a update S2Win as b set total = b.value + a.value");
        EPStatement stmt = epService.getEPAdministrator().createEPL("select count(*) as cnt from S2Win");
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(createEvent("AComp", 3.0, 0.0), "S2");
        assertEquals(1L, listener.assertOneGetNewAndReset().get("cnt"));
        ArrayAssertionUtil.assertPropsPerRow(stmtWin.iterator(), fields, new Object[][] {{"AComp", 3.0, 0.0}});

        epService.getEPRuntime().sendEvent(createEvent("AComp", 6.0, 0.0), "S2");
        assertEquals(1L, listener.assertOneGetNewAndReset().get("cnt"));
        ArrayAssertionUtil.assertPropsPerRow(stmtWin.iterator(), fields, new Object[][] {{"AComp", 3.0, 9.0}});

        epService.getEPRuntime().sendEvent(createEvent("AComp", 5.0, 0.0), "S2");
        assertEquals(1L, listener.assertOneGetNewAndReset().get("cnt"));
        ArrayAssertionUtil.assertPropsPerRow(stmtWin.iterator(), fields, new Object[][] {{"AComp", 3.0, 8.0}});

        epService.getEPRuntime().sendEvent(createEvent("BComp", 4.0, 0.0), "S2");
        assertEquals(2L, listener.assertOneGetNewAndReset().get("cnt"));
        ArrayAssertionUtil.assertPropsPerRow(stmtWin.iterator(), fields, new Object[][] {{"AComp", 3.0, 7.0}, {"BComp", 4.0, 0.0}});
    }

    private HashMap<String, Object> createEvent(String company, double value, double total) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("company", company);
        map.put("value", value);
        map.put("total", total);
        return map;
    }

    public void testMultipleDataWindowUnion() {
        String stmtTextCreate = "create window MyWindow.std:unique(string).win:length(2) retain-union as select * from SupportBean";
        EPStatement stmtCreate = epService.getEPAdministrator().createEPL(stmtTextCreate);
        stmtCreate.addListener(listenerWindow);

        String stmtTextInsertOne = "insert into MyWindow select * from SupportBean";
        epService.getEPAdministrator().createEPL(stmtTextInsertOne);

        String stmtTextUpdate = "on SupportBean_A update MyWindow mw set mw.intPrimitive=intPrimitive*100 where string=id";
        epService.getEPAdministrator().createEPL(stmtTextUpdate);

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 2));
        epService.getEPRuntime().sendEvent(new SupportBean("E2", 3));
        epService.getEPRuntime().sendEvent(new SupportBean_A("E2"));
        EventBean[] newevents = listenerWindow.getLastNewData();
        EventBean[] oldevents = listenerWindow.getLastOldData();

        assertEquals(1, newevents.length);
        ArrayAssertionUtil.assertProps(newevents[0], "intPrimitive".split(","), new Object[] {300});
        assertEquals(1, oldevents.length);
        ArrayAssertionUtil.assertPropsPerRow(oldevents, "string,intPrimitive".split(","), new Object[][] {{"E2", 3}});

        EventBean[] events = ArrayAssertionUtil.sort(stmtCreate.iterator(), "string");
        ArrayAssertionUtil.assertPropsPerRow(events, "string,intPrimitive".split(","), new Object[][] {{"E1", 2}, {"E2", 300}});
    }

    public void testSubclass()
    {
        // create window
        String stmtTextCreate = "create window MyWindow.win:keepall() as select * from " + SupportBeanAbstractSub.class.getName();
        EPStatement stmtCreate = epService.getEPAdministrator().createEPL(stmtTextCreate);
        stmtCreate.addListener(listenerWindow);

        // create insert into
        String stmtTextInsertOne = "insert into MyWindow select * from " + SupportBeanAbstractSub.class.getName();
        epService.getEPAdministrator().createEPL(stmtTextInsertOne);

        // create update
        String stmtTextUpdate = "on " + SupportBean.class.getName() + " update MyWindow set v1=string, v2=string";
        epService.getEPAdministrator().createEPL(stmtTextUpdate);
        
        epService.getEPRuntime().sendEvent(new SupportBeanAbstractSub("value2"));
        listenerWindow.reset();

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        ArrayAssertionUtil.assertProps(listenerWindow.getLastNewData()[0], new String[] {"v1", "v2"}, new Object[] {"E1", "E1"});
    }
}