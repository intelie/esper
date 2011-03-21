package com.espertech.esper.regression.enummethod;

import com.espertech.esper.client.*;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBean_ST0_Container;
import com.espertech.esper.support.bean.SupportCollection;
import com.espertech.esper.support.bean.lambda.LambdaAssertionUtil;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TestEnumUnion extends TestCase {

    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp() {

        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("SupportBean_ST0_Container", SupportBean_ST0_Container.class);
        config.addEventType("SupportBean", SupportBean.class);
        config.addEventType("SupportCollection", SupportCollection.class);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();
    }

    public void testUnionWhere() {

        String epl = "expression one {" +
                "  x => x.contained.where(y => p00 = 10)" +
                "} " +
                "" +
                "expression two {" +
                "  x => x.contained.where(y => p00 = 11)" +
                "} " +
                "" +
                "select one(bean).union(two(bean)) as val0 from SupportBean_ST0_Container as bean";
        EPStatement stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(listener);
        LambdaAssertionUtil.assertTypes(stmt.getEventType(), "val0".split(","), new Class[]{Collection.class});

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make2Value("E1,1", "E2,10", "E3,1", "E4,10", "E5,11"));
        LambdaAssertionUtil.assertST0Id(listener, "val0", "E2,E4,E5");
        listener.reset();

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make2Value("E1,10", "E2,1", "E3,1"));
        LambdaAssertionUtil.assertST0Id(listener, "val0", "E1");
        listener.reset();

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make2Value("E1,1", "E2,1", "E3,10", "E4,11"));
        LambdaAssertionUtil.assertST0Id(listener, "val0", "E3,E4");
        listener.reset();

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make2Value(null));
        LambdaAssertionUtil.assertST0Id(listener, "val0", null);
        listener.reset();

        epService.getEPRuntime().sendEvent(SupportBean_ST0_Container.make2Value());
        LambdaAssertionUtil.assertST0Id(listener, "val0", "");
        listener.reset();
    }

    public void testInheritance() {
        epService.getEPAdministrator().createEPL("create schema BaseEvent as (b1 string)");
        epService.getEPAdministrator().createEPL("create schema SubEvent as (s1 string) inherits BaseEvent");
        epService.getEPAdministrator().createEPL("create schema OuterEvent as (bases BaseEvent[], subs SubEvent[])");
        EPStatement stmt = epService.getEPAdministrator().createEPL("select bases.union(subs) as val from OuterEvent");
        stmt.addListener(listener);

        Map<String, Object> baseEvent = makeMap("b1", "b10");
        Map<String, Object> subEvent = makeMap("s1", "s10");
        Map<String, Object> outerEvent = makeMap("bases", new Map[] {baseEvent}, "subs", new Map[] {subEvent});
        epService.getEPRuntime().sendEvent(outerEvent, "OuterEvent");
        
        Collection maps = (Collection) listener.assertOneGetNewAndReset().get("val");
        assertEquals(2, maps.size());
    }

    public void testUnionScalar() {

        String epl = "select strvals.union(strvalstwo) as val0 from SupportCollection as bean";
        EPStatement stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(listener);
        LambdaAssertionUtil.assertTypes(stmt.getEventType(), "val0".split(","), new Class[]{Collection.class});

        epService.getEPRuntime().sendEvent(SupportCollection.makeString("E1,E2", "E3,E4"));
        LambdaAssertionUtil.assertValues(listener, "val0", "E1", "E2", "E3", "E4");
        listener.reset();
    }

    public void testInvalid() {
        String epl;

        epl = "select contained.union(true) from SupportBean_ST0_Container";
        tryInvalid(epl, "Error starting statement: Enumeration method 'union' requires an expression yielding an event-collection as input paramater [select contained.union(true) from SupportBean_ST0_Container]");

        epl = "select contained.union(prevwindow(s1)) from SupportBean_ST0_Container.std:lastevent(), SupportBean.win:keepall() s1";
        tryInvalid(epl, "Error starting statement: Enumeration method 'union' expects event type 'com.espertech.esper.support.bean.SupportBean_ST0' but receives event type 'SupportBean' [select contained.union(prevwindow(s1)) from SupportBean_ST0_Container.std:lastevent(), SupportBean.win:keepall() s1]");
    }

    private void tryInvalid(String epl, String message) {
        try
        {
            epService.getEPAdministrator().createEPL(epl);
            fail();
        }
        catch (EPStatementException ex) {
            assertEquals(message, ex.getMessage());
        }
    }

    private Map<String, Object> makeMap(String key, Object value) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(key, value);
        return map;
    }

    private Map<String, Object> makeMap(String key, Object value, String key2, Object value2) {
        Map<String, Object> map = makeMap(key, value);
        map.put(key2, value2);
        return map;
    }
}
