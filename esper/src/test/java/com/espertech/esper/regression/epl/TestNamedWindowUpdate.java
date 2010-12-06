package com.espertech.esper.regression.epl;

import com.espertech.esper.support.bean.SupportBean_A;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import junit.framework.TestCase;
import com.espertech.esper.client.*;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportUpdateListener;

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
        
        ArrayAssertionUtil.assertEqualsAnyOrder(stmt.iterator(), "string,intPrimitive".split(","), new Object[][] {{"E1", 3}, {"E2", 7}});
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

    public void testSubquery()
    {
        // create window
        String stmtTextCreate = "create window MyWindow.win:keepall() as select string as a, longPrimitive as b, longBoxed as c from " + SupportBean.class.getName();
        EPStatement stmtCreate = epService.getEPAdministrator().createEPL(stmtTextCreate);
        stmtCreate.addListener(listenerWindow);

        // create insert into
        String stmtTextInsertOne = "insert into MyWindow select string as a, longPrimitive as b, longBoxed as c from " + SupportBean.class.getName();
        epService.getEPAdministrator().createEPL(stmtTextInsertOne);

        // create consumer
        String stmtTextSelectOne = "select irstream * from MyWindow";
        epService.getEPAdministrator().createEPL(stmtTextSelectOne);

        // create update
        String stmtTextUpdate = "on " + SupportMarketDataBean.class + " update MyWindow set a='new' where a='old'";
        epService.getEPAdministrator().createEPL(stmtTextSelectOne);
    }
}