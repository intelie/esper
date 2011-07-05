package com.espertech.esper.regression.client;

import com.espertech.esper.client.*;
import com.espertech.esper.client.hook.*;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBeanRange;
import com.espertech.esper.support.bean.SupportBean_ST0;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.virtualdw.SupportVirtualDW;
import com.espertech.esper.support.virtualdw.SupportVirtualDWFactory;
import com.espertech.esper.support.virtualdw.SupportVirtualDWInvalidFactory;
import junit.framework.TestCase;

import javax.naming.NamingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestVirtualDataWindow extends TestCase {

    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        listener = new SupportUpdateListener();

        Configuration configuration = SupportConfigFactory.getConfiguration();
        configuration.addPlugInVirtualDataWindow("test", "vdw", SupportVirtualDWFactory.class.getName());
        configuration.addPlugInVirtualDataWindow("invalid", "invalid", TestCase.class.getName());
        configuration.addPlugInVirtualDataWindow("test", "testnoindex", SupportVirtualDWInvalidFactory.class.getName());
        configuration.addEventType("SupportBean", SupportBean.class);
        configuration.addEventType("SupportBean_ST0", SupportBean_ST0.class);
        configuration.addEventType("SupportBeanRange", SupportBeanRange.class);
        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();
    }

    public void testInsertConsume() {

        epService.getEPAdministrator().createEPL("create window MyVDW.test:vdw() as SupportBean");
        SupportVirtualDW window = (SupportVirtualDW) getFromContext("/virtualdw/MyVDW");
        SupportBean supportBean = new SupportBean("S1", 100);
        window.setData(Collections.singleton(supportBean));
        epService.getEPAdministrator().createEPL("insert into MyVDW select * from SupportBean");

        // test straight consume
        String[] fields = "string,intPrimitive".split(",");
        EPStatement stmtConsume = epService.getEPAdministrator().createEPL("select irstream * from MyVDW");
        stmtConsume.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 200));
        assertNull(listener.getLastOldData());
        ArrayAssertionUtil.assertProps(listener.getAndResetLastNewData()[0], fields, new Object[] {"E1", 200});
        stmtConsume.destroy();

        // test aggregated consumer - wherein the virtual data window does not return an iterator that prefills the aggregation state
        fields = "val0".split(",");
        EPStatement stmtAggregate = epService.getEPAdministrator().createEPL("select sum(intPrimitive) as val0 from MyVDW");
        stmtAggregate.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 100));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {100});

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 50));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {150});
        stmtAggregate.destroy();
    }

    public void testOnMerge() {
        // defined test type
        Map<String, Object> mapType = new HashMap<String, Object>();
        mapType.put("col1", "string");
        mapType.put("col2", "string");
        epService.getEPAdministrator().getConfiguration().addEventType("MapType", mapType);

        epService.getEPAdministrator().createEPL("create window MyVDW.test:vdw() as MapType");

        // define some test data to return, via lookup
        SupportVirtualDW window = (SupportVirtualDW) getFromContext("/virtualdw/MyVDW");
        Map<String, Object> mapData = new HashMap<String, Object>();
        mapData.put("col1", "key1");
        mapData.put("col2", "key2");
        window.setData(Collections.singleton(mapData));

        String[] fieldsMerge = "col1,col2".split(",");
        EPStatement stmtMerge = epService.getEPAdministrator().createEPL("on SupportBean sb merge MyVDW vdw " +
                "where col1 = string " +
                "when matched then update set col2 = 'xxx'" +
                "when not matched then insert select string as col1, 'abc' as col2");
        stmtMerge.addListener(listener);
        SupportUpdateListener listenerConsume = new SupportUpdateListener();
        epService.getEPAdministrator().createEPL("select * from MyVDW").addListener(listenerConsume);

        // try yes-matched case
        epService.getEPRuntime().sendEvent(new SupportBean("key1", 2));
        ArrayAssertionUtil.assertProps(listener.getLastOldData()[0], fieldsMerge, new Object[] {"key1", "key2"});
        ArrayAssertionUtil.assertProps(listener.getAndResetLastNewData()[0], fieldsMerge, new Object[]{"key1", "xxx"});
        ArrayAssertionUtil.assertProps(window.getLastUpdateOld()[0], fieldsMerge, new Object[]{"key1", "key2"});
        ArrayAssertionUtil.assertProps(window.getLastUpdateNew()[0], fieldsMerge, new Object[]{"key1", "xxx"});
        ArrayAssertionUtil.assertProps(listenerConsume.assertOneGetNewAndReset(), fieldsMerge, new Object[] {"key1", "xxx"});

        // try not-matched case
        epService.getEPRuntime().sendEvent(new SupportBean("key2", 3));
        assertNull(listener.getLastOldData());
        ArrayAssertionUtil.assertProps(listener.getAndResetLastNewData()[0], fieldsMerge, new Object[]{"key2", "abc"});
        ArrayAssertionUtil.assertProps(listenerConsume.assertOneGetNewAndReset(), fieldsMerge, new Object[]{"key2", "abc"});
        assertNull(window.getLastUpdateOld());
        ArrayAssertionUtil.assertProps(window.getLastUpdateNew()[0], fieldsMerge, new Object[] {"key2", "abc"});
    }

    public void testLimitation() {
        EPStatement stmtWindow = epService.getEPAdministrator().createEPL("create window MyVDW.test:vdw() as SupportBean");
        SupportVirtualDW window = (SupportVirtualDW) getFromContext("/virtualdw/MyVDW");
        SupportBean supportBean = new SupportBean("S1", 100);
        window.setData(Collections.singleton(supportBean));
        epService.getEPAdministrator().createEPL("insert into MyVDW select * from SupportBean");
        
        // cannot iterate named window
        assertFalse(stmtWindow.iterator().hasNext());

        // test data window aggregation (rows not included in aggregation)
        EPStatement stmtAggregate = epService.getEPAdministrator().createEPL("select window(string) as val0 from MyVDW");
        stmtAggregate.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 100));
        ArrayAssertionUtil.assertEqualsExactOrder((String[]) listener.assertOneGetNewAndReset().get("val0"), new Object[]{"E1"});
    }

    public void testJoinAndLifecyle() {

        EPStatement stmt = epService.getEPAdministrator().createEPL("create window MyVDW.test:vdw(1, 'abc') as SupportBean");

        // define some test data to return, via lookup
        SupportVirtualDW window = (SupportVirtualDW) getFromContext("/virtualdw/MyVDW");
        SupportBean supportBean = new SupportBean("S1", 100);
        supportBean.setLongPrimitive(50);
        window.setData(Collections.singleton(supportBean));

        assertNotNull(window.getContext().getEventFactory());
        assertEquals("MyVDW", window.getContext().getEventType().getName());
        assertNotNull(window.getContext().getStatementContext());
        assertEquals(2, window.getContext().getParameters().length);
        assertEquals(1, window.getContext().getParameters()[0]);
        assertEquals("abc", window.getContext().getParameters()[1]);
        assertEquals("MyVDW", window.getContext().getNamedWindowName());

        // test no-criteria join
        String[] fields = "st0.id,vdw.string,vdw.intPrimitive".split(",");
        EPStatement stmtJoinAll = epService.getEPAdministrator().createEPL("select * from MyVDW vdw, SupportBean_ST0.std:lastevent() st0");
        stmtJoinAll.addListener(listener);
        assertIndexSpec(window.getLastRequestedIndex(), "", "");

        epService.getEPRuntime().sendEvent(new SupportBean_ST0("E1", 0));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[]{"E1", "S1", 100});
        ArrayAssertionUtil.assertEqualsExactOrder(window.getLastAccessKeys(), new Object[]{});
        stmtJoinAll.destroy();

        // test single-criteria join
        EPStatement stmtJoinSingle = epService.getEPAdministrator().createEPL("select * from MyVDW vdw, SupportBean_ST0.std:lastevent() st0 where vdw.string = st0.id");
        stmtJoinSingle.addListener(listener);
        assertIndexSpec(window.getLastRequestedIndex(), "string=(String)", "");

        epService.getEPRuntime().sendEvent(new SupportBean_ST0("E1", 0));
        ArrayAssertionUtil.assertEqualsExactOrder(window.getLastAccessKeys(), new Object[]{"E1"});
        assertFalse(listener.isInvoked());
        epService.getEPRuntime().sendEvent(new SupportBean_ST0("S1", 0));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[]{"S1", "S1", 100});
        ArrayAssertionUtil.assertEqualsExactOrder(window.getLastAccessKeys(), new Object[]{"S1"});
        stmtJoinSingle.destroy();

        // test multi-criteria join
        EPStatement stmtJoinMulti = epService.getEPAdministrator().createEPL("select vdw.string from MyVDW vdw, SupportBeanRange.std:lastevent() st0 " +
                "where vdw.string = st0.id and longPrimitive = keyLong and intPrimitive between rangeStart and rangeEnd");
        stmtJoinMulti.addListener(listener);
        assertIndexSpec(window.getLastRequestedIndex(), "string=(String)|longPrimitive=(Long)", "intPrimitive[,](Integer)");

        epService.getEPRuntime().sendEvent(SupportBeanRange.makeKeyLong("S1", 50L, 80, 120));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), "vdw.string".split(","), new Object[]{"S1"});
        ArrayAssertionUtil.assertEqualsExactOrder(window.getLastAccessKeys(), new Object[]{"S1", 50L, new VirtualDataWindowKeyRange(80, 120)});

        // destroy
        stmt.destroy();
        assertNull(getFromContext("/virtualdw/MyVDW"));
        assertTrue(window.isDestroyed());
    }

    public void testSubquery() {

        SupportVirtualDW window = registerTypeSetMapData();

        // test no-criteria subquery
        EPStatement stmtSubqueryAll = epService.getEPAdministrator().createEPL("select (select col1 from MyVDW vdw) from SupportBean_ST0");
        stmtSubqueryAll.addListener(listener);
        assertIndexSpec(window.getLastRequestedIndex(), "", "");

        epService.getEPRuntime().sendEvent(new SupportBean_ST0("E1", 0));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), "col1".split(","), new Object[]{"key1"});
        ArrayAssertionUtil.assertEqualsExactOrder(window.getLastAccessKeys(), new Object[]{});
        stmtSubqueryAll.destroy();

        // test single-criteria subquery
        EPStatement stmtSubqSingleKey = epService.getEPAdministrator().createEPL("select (select col1 from MyVDW vdw where col1=st0.id) as val0 from SupportBean_ST0 st0");
        stmtSubqSingleKey.addListener(listener);
        assertIndexSpec(window.getLastRequestedIndex(), "col1=(String)", "");

        epService.getEPRuntime().sendEvent(new SupportBean_ST0("E1", 0));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), "val0".split(","), new Object[]{null});
        ArrayAssertionUtil.assertEqualsExactOrder(window.getLastAccessKeys(), new Object[]{"E1"});
        epService.getEPRuntime().sendEvent(new SupportBean_ST0("key1", 0));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), "val0".split(","), new Object[]{"key1"});
        ArrayAssertionUtil.assertEqualsExactOrder(window.getLastAccessKeys(), new Object[]{"key1"});
        stmtSubqSingleKey.destroy();

        // test multie-criteria subquery
        EPStatement stmtSubqMultiKey = epService.getEPAdministrator().createEPL("select " +
                "(select col1 from MyVDW vdw where col1=r.id and col2=r.key and col3 between r.rangeStart and r.rangeEnd) as val0 " +
                "from SupportBeanRange r");
        stmtSubqMultiKey.addListener(listener);
        assertIndexSpec(window.getLastRequestedIndex(), "col1=(String)|col2=(String)", "col3[,](Integer)");

        epService.getEPRuntime().sendEvent(new SupportBeanRange("key1", "key2", 5, 10));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), "val0".split(","), new Object[]{"key1"});
        ArrayAssertionUtil.assertEqualsExactOrder(window.getLastAccessKeys(), new Object[]{"key1", "key2", new VirtualDataWindowKeyRange(5, 10)});
        stmtSubqMultiKey.destroy();
    }

    public void testFireAndForget() {

        SupportVirtualDW window = registerTypeSetMapData();

        // test no-criteria FAF
        EPOnDemandQueryResult result = epService.getEPRuntime().executeQuery("select col1 from MyVDW vdw");
        assertIndexSpec(window.getLastRequestedIndex(), "", "");
        ArrayAssertionUtil.assertProps(result.getArray()[0], "col1".split(","), new Object[] {"key1"});
        ArrayAssertionUtil.assertEqualsExactOrder(window.getLastAccessKeys(), new Object[0]);

        // test single-criteria FAF
        result = epService.getEPRuntime().executeQuery("select col1 from MyVDW vdw where col1='key1'");
        assertIndexSpec(window.getLastRequestedIndex(), "col1=(String)", "");
        ArrayAssertionUtil.assertProps(result.getArray()[0], "col1".split(","), new Object[]{"key1"});
        ArrayAssertionUtil.assertEqualsExactOrder(window.getLastAccessKeys(), new Object[]{"key1"});

        // test multi-criteria subquery
        result = epService.getEPRuntime().executeQuery("select col1 from MyVDW vdw where col1='key1' and col2='key2' and col3 between 5 and 15");
        assertIndexSpec(window.getLastRequestedIndex(), "col1=(String)|col2=(String)", "col3[,](Double)");
        ArrayAssertionUtil.assertProps(result.getArray()[0], "col1".split(","), new Object[]{"key1"});
        ArrayAssertionUtil.assertEqualsExactOrder(window.getLastAccessKeys(), new Object[]{"key1", "key2", new VirtualDataWindowKeyRange(5d, 15d)});

        // test multi-criteria subquery
        result = epService.getEPRuntime().executeQuery("select col1 from MyVDW vdw where col1='key1' and col2>'key0' and col3 between 5 and 15");
        assertIndexSpec(window.getLastRequestedIndex(), "col1=(String)", "col3[,](Double)|col2>(String)");
        ArrayAssertionUtil.assertProps(result.getArray()[0], "col1".split(","), new Object[]{"key1"});
        ArrayAssertionUtil.assertEqualsExactOrder(window.getLastAccessKeys(), new Object[]{"key1", new VirtualDataWindowKeyRange(5d, 15d), "key0"});
    }

    public void testOnDelete() {
        SupportVirtualDW window = registerTypeSetMapData();

        // test no-criteria on-delete
        EPStatement stmtOnDeleteAll = epService.getEPAdministrator().createEPL("on SupportBean_ST0 delete from MyVDW vdw");
        stmtOnDeleteAll.addListener(listener);
        assertIndexSpec(window.getLastRequestedIndex(), "", "");

        epService.getEPRuntime().sendEvent(new SupportBean_ST0("E1", 0));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), "col1".split(","), new Object[]{"key1"});
        ArrayAssertionUtil.assertEqualsExactOrder(window.getLastAccessKeys(), new Object[]{});
        stmtOnDeleteAll.destroy();

        // test single-criteria on-delete
        EPStatement stmtOnDeleteSingleKey = epService.getEPAdministrator().createEPL("on SupportBean_ST0 st0 delete from MyVDW vdw where col1=st0.id");
        stmtOnDeleteSingleKey.addListener(listener);
        assertIndexSpec(window.getLastRequestedIndex(), "col1=(String)", "");

        epService.getEPRuntime().sendEvent(new SupportBean_ST0("E1", 0));
        ArrayAssertionUtil.assertEqualsExactOrder(window.getLastAccessKeys(), new Object[]{"E1"});
        assertFalse(listener.isInvoked());
        epService.getEPRuntime().sendEvent(new SupportBean_ST0("key1", 0));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), "col1".split(","), new Object[]{"key1"});
        ArrayAssertionUtil.assertEqualsExactOrder(window.getLastAccessKeys(), new Object[]{"key1"});
        stmtOnDeleteSingleKey.destroy();

        // test multie-criteria on-delete
        EPStatement stmtOnDeleteMultiKey = epService.getEPAdministrator().createEPL("on SupportBeanRange r delete " +
                "from MyVDW vdw where col1=r.id and col2=r.key and col3 between r.rangeStart and r.rangeEnd");
        stmtOnDeleteMultiKey.addListener(listener);
        assertIndexSpec(window.getLastRequestedIndex(), "col1=(String)|col2=(String)", "col3[,](Integer)");

        epService.getEPRuntime().sendEvent(new SupportBeanRange("key1", "key2", 5, 10));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), "col1".split(","), new Object[]{"key1"});
        ArrayAssertionUtil.assertEqualsExactOrder(window.getLastAccessKeys(), new Object[]{"key1", "key2", new VirtualDataWindowKeyRange(5, 10)});
        stmtOnDeleteMultiKey.destroy();
    }

    public void testInvalid() {
        String epl;

        epl = "create window ABC.invalid:invalid() as SupportBean";
        tryInvalid(epl, "Error starting statement: Virtual data window factory class junit.framework.TestCase does not implement the interface com.espertech.esper.client.hook.VirtualDataWindowFactory [create window ABC.invalid:invalid() as SupportBean]");

        epl = "select * from SupportBean.test:vdw()";
        tryInvalid(epl, "Error starting statement: Virtual data window requires use with a named window in the create-window syntax [select * from SupportBean.test:vdw()]");

        epService.getEPAdministrator().createEPL("create window ABC.test:testnoindex() as SupportBean");
        epl = "select (select * from ABC) from SupportBean";
        tryInvalid(epl, "Unexpected exception starting statement: Exception obtaining index lookup from virtual data window, the implementation has returned a null index [select (select * from ABC) from SupportBean]");
    }

    public void testManagementEvents() {
        SupportVirtualDW vdw = registerTypeSetMapData();

        // create-index event
        vdw.getEvents().clear();
        EPStatement stmtIndex = epService.getEPAdministrator().createEPL("create index IndexOne on MyVDW (col3, col2 btree)");
        VirtualDataWindowEventStartIndex startEvent = (VirtualDataWindowEventStartIndex) vdw.getEvents().get(0);
        assertEquals("MyVDW", startEvent.getNamedWindowName());
        assertEquals("IndexOne", startEvent.getIndexName());
        assertEquals(2, startEvent.getFields().size());
        assertEquals("col3", startEvent.getFields().get(0).getName());
        assertEquals(true, startEvent.getFields().get(0).isHash());
        assertEquals("col2", startEvent.getFields().get(1).getName());
        assertEquals(false, startEvent.getFields().get(1).isHash());

        // stop-index event
        vdw.getEvents().clear();
        stmtIndex.stop();
        VirtualDataWindowEventStopIndex stopEvent = (VirtualDataWindowEventStopIndex) vdw.getEvents().get(0);
        assertEquals("MyVDW", stopEvent.getNamedWindowName());
        assertEquals("IndexOne", stopEvent.getIndexName());

        // stop named window
        vdw.getEvents().clear();
        epService.getEPAdministrator().getStatement("create-nw").stop();
        VirtualDataWindowEventStopWindow stopWindow = (VirtualDataWindowEventStopWindow) vdw.getEvents().get(0);
        assertEquals("MyVDW", stopWindow.getNamedWindowName());

        // start named window (not an event but a new factory call)
        SupportVirtualDWFactory.getWindows().clear();
        epService.getEPAdministrator().getStatement("create-nw").start();
        assertEquals(1, SupportVirtualDWFactory.getWindows().size());
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

    private SupportVirtualDW registerTypeSetMapData() {
        Map<String, Object> mapType = new HashMap<String, Object>();
        mapType.put("col1", "string");
        mapType.put("col2", "string");
        mapType.put("col3", "int");
        epService.getEPAdministrator().getConfiguration().addEventType("MapType", mapType);

        epService.getEPAdministrator().createEPL("@Name('create-nw') create window MyVDW.test:vdw() as MapType");

        // define some test data to return, via lookup
        SupportVirtualDW window = (SupportVirtualDW) getFromContext("/virtualdw/MyVDW");
        Map<String, Object> mapData = new HashMap<String, Object>();
        mapData.put("col1", "key1");
        mapData.put("col2", "key2");
        mapData.put("col3", 10);
        window.setData(Collections.singleton(mapData));

        return window;
    }

    private void assertIndexSpec(VirtualDataWindowLookupContext indexSpec, String hashfields, String btreefields) {
        assertIndexFields(hashfields, indexSpec.getHashFields());
        assertIndexFields(btreefields, indexSpec.getBtreeFields());
    }

    private void assertIndexFields(String hashfields, List<VirtualDataWindowLookupFieldDesc> fields) {
        if (hashfields.isEmpty() && fields.isEmpty()) {
            return;
        }
        String[] split = hashfields.split("\\|");
        for (int i = 0; i < split.length; i++) {
            String expected = split[i];
            VirtualDataWindowLookupFieldDesc field = fields.get(i);
            String found = field.getPropertyName() + field.getOperator().getOp() + "(" + field.getLookupValueType().getSimpleName() + ")";
            assertEquals(expected, found);
        }
    }

    private VirtualDataWindow getFromContext(String name) {
        try {
            return (VirtualDataWindow) epService.getContext().lookup(name);
        }
        catch (NamingException e) {
            throw new RuntimeException("Name '" + name + "' could not be looked up");
        }
    }
}
