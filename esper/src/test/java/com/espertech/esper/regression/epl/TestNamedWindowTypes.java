package com.espertech.esper.regression.epl;

import com.espertech.esper.client.*;
import com.espertech.esper.client.soda.EPStatementObjectModel;
import com.espertech.esper.core.EPServiceProviderSPI;
import com.espertech.esper.event.EventTypeMetadata;
import com.espertech.esper.event.EventTypeSPI;
import com.espertech.esper.event.MappedEventBean;
import com.espertech.esper.event.map.MapEventType;
import com.espertech.esper.support.bean.*;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;

public class TestNamedWindowTypes extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listenerWindow;
    private SupportUpdateListener listenerStmtOne;
    private SupportUpdateListener listenerStmtDelete;

    public void setUp()
    {
        Map<String, Object> types = new HashMap<String, Object>();
        types.put("key", String.class);
        types.put("primitive", long.class);
        types.put("boxed", Long.class);

        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("MyMap", types);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();

        listenerWindow = new SupportUpdateListener();
        listenerStmtOne = new SupportUpdateListener();
        listenerStmtDelete = new SupportUpdateListener();
    }

    public void testEventTypeColumnDef() {
        epService.getEPAdministrator().createEPL("create schema SchemaOne(col1 int, col2 int)");
        EPStatement stmt = epService.getEPAdministrator().createEPL("create window SchemaWindow.std:lastevent() as (s1 SchemaOne)");
        stmt.addListener(listenerWindow);
        epService.getEPAdministrator().createEPL("insert into SchemaWindow (s1) select sone from SchemaOne as sone");
        
        Map<String, Object> value = new HashMap<String, Object>();
        value.put("col1", 10);
        value.put("col2", 11);
        epService.getEPRuntime().sendEvent(value, "SchemaOne");
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNew(), "s1.col1,s1.col2".split(","),new Object[] {10,11});
    }

    public void testMapTranspose()
    {
        Map<String, Object> innerTypeOne = new HashMap<String, Object>();
        innerTypeOne.put("i1", int.class);
        Map<String, Object> innerTypeTwo = new HashMap<String, Object>();
        innerTypeTwo.put("i2", int.class);
        Map<String, Object> outerType = new HashMap<String, Object>();
        outerType.put("one", "T1");
        outerType.put("two", "T2");
        epService.getEPAdministrator().getConfiguration().addEventType("T1", innerTypeOne);
        epService.getEPAdministrator().getConfiguration().addEventType("T2", innerTypeTwo);
        epService.getEPAdministrator().getConfiguration().addEventType("OuterType", outerType);

        // create window
        String stmtTextCreate = "create window MyWindow.win:keepall() as select one, two from OuterType";
        EPStatement stmtCreate = epService.getEPAdministrator().createEPL(stmtTextCreate);
        stmtCreate.addListener(listenerWindow);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmtCreate.getEventType().getPropertyNames(), new String[] {"one", "two"});
        EventType eventType = stmtCreate.getEventType();
        assertEquals("T1", eventType.getFragmentType("one").getFragmentType().getName());
        assertEquals("T2", eventType.getFragmentType("two").getFragmentType().getName());

        // create insert into
        String stmtTextInsertOne = "insert into MyWindow select one, two from OuterType";
        epService.getEPAdministrator().createEPL(stmtTextInsertOne);

        Map<String, Object> innerDataOne = new HashMap<String, Object>();
        innerDataOne.put("i1", 1);
        Map<String, Object> innerDataTwo = new HashMap<String, Object>();
        innerDataTwo.put("i2", 2);
        Map<String, Object> outerData = new HashMap<String, Object>();
        outerData.put("one", innerDataOne);
        outerData.put("two", innerDataTwo);

        epService.getEPRuntime().sendEvent(outerData, "OuterType");
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNew(), "one.i1,two.i2".split(","),new Object[] {1,2});
    }

    public void testNoWildcardWithAs()
    {
        // create window
        String stmtTextCreate = "create window MyWindow.win:keepall() as select string as a, longPrimitive as b, longBoxed as c from " + SupportBean.class.getName();
        EPStatement stmtCreate = epService.getEPAdministrator().createEPL(stmtTextCreate);
        stmtCreate.addListener(listenerWindow);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmtCreate.getEventType().getPropertyNames(), new String[] {"a", "b", "c"});
        assertEquals(String.class, stmtCreate.getEventType().getPropertyType("a"));
        assertEquals(long.class, stmtCreate.getEventType().getPropertyType("b"));
        assertEquals(Long.class, stmtCreate.getEventType().getPropertyType("c"));

        // assert type metadata
        EventTypeSPI type = (EventTypeSPI) ((EPServiceProviderSPI)epService).getEventAdapterService().getExistsTypeByName("MyWindow");
        assertEquals(null, type.getMetadata().getOptionalApplicationType());
        assertEquals(null, type.getMetadata().getOptionalSecondaryNames());
        assertEquals("MyWindow", type.getMetadata().getPrimaryName());
        assertEquals("MyWindow", type.getMetadata().getPublicName());
        assertEquals("MyWindow", type.getName());
        assertEquals(EventTypeMetadata.TypeClass.NAMED_WINDOW, type.getMetadata().getTypeClass());
        assertEquals(false, type.getMetadata().isApplicationConfigured());
        assertEquals(false, type.getMetadata().isApplicationPreConfigured());
        assertEquals(false, type.getMetadata().isApplicationPreConfiguredStatic());

        // create insert into
        String stmtTextInsertOne = "insert into MyWindow select string as a, longPrimitive as b, longBoxed as c from " + SupportBean.class.getName();
        epService.getEPAdministrator().createEPL(stmtTextInsertOne);

        String stmtTextInsertTwo = "insert into MyWindow select symbol as a, volume as b, volume as c from " + SupportMarketDataBean.class.getName();
        epService.getEPAdministrator().createEPL(stmtTextInsertTwo);

        String stmtTextInsertThree = "insert into MyWindow select key as a, boxed as b, primitive as c from MyMap";
        epService.getEPAdministrator().createEPL(stmtTextInsertThree);

        // create consumer
        String stmtTextSelectOne = "select a, b, c from MyWindow";
        EPStatement stmtSelectOne = epService.getEPAdministrator().createEPL(stmtTextSelectOne);
        stmtSelectOne.addListener(listenerStmtOne);
        ArrayAssertionUtil.assertEqualsAnyOrder(stmtSelectOne.getEventType().getPropertyNames(), new String[] {"a", "b", "c"});
        assertEquals(String.class, stmtCreate.getEventType().getPropertyType("a"));
        assertEquals(long.class, stmtCreate.getEventType().getPropertyType("b"));
        assertEquals(Long.class, stmtCreate.getEventType().getPropertyType("c"));

        // create delete stmt
        String stmtTextDelete = "on " + SupportMarketDataBean.class.getName() + " as s0 delete from MyWindow as s1 where s0.symbol = s1.a";
        EPStatement stmtDelete = epService.getEPAdministrator().createEPL(stmtTextDelete);
        stmtDelete.addListener(listenerStmtDelete);

        sendSupportBean("E1", 1L, 10L);
        String[] fields = new String[] {"a", "b", "c"};
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E1", 1L, 10L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"E1", 1L, 10L});

        sendMarketBean("S1", 99L);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"S1", 99L, 99L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"S1", 99L, 99L});

        sendMap("M1", 100L, 101L);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"M1", 101L, 100L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"M1", 101L, 100L});
    }

    public void testNoWildcardNoAs()
    {
        // create window
        String stmtTextCreate = "create window MyWindow.win:keepall() as select string, longPrimitive, longBoxed from " + SupportBean.class.getName();
        EPStatement stmtCreate = epService.getEPAdministrator().createEPL(stmtTextCreate);
        stmtCreate.addListener(listenerWindow);

        // create insert into
        String stmtTextInsertOne = "insert into MyWindow select string, longPrimitive, longBoxed from " + SupportBean.class.getName();
        epService.getEPAdministrator().createEPL(stmtTextInsertOne);

        String stmtTextInsertTwo = "insert into MyWindow select symbol as string, volume as longPrimitive, volume as longBoxed from " + SupportMarketDataBean.class.getName();
        epService.getEPAdministrator().createEPL(stmtTextInsertTwo);

        String stmtTextInsertThree = "insert into MyWindow select key as string, boxed as longPrimitive, primitive as longBoxed from MyMap";
        epService.getEPAdministrator().createEPL(stmtTextInsertThree);

        // create consumer
        String stmtTextSelectOne = "select string, longPrimitive, longBoxed from MyWindow";
        EPStatement stmtSelectOne = epService.getEPAdministrator().createEPL(stmtTextSelectOne);
        stmtSelectOne.addListener(listenerStmtOne);

        sendSupportBean("E1", 1L, 10L);
        String[] fields = new String[] {"string", "longPrimitive", "longBoxed"};
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E1", 1L, 10L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"E1", 1L, 10L});

        sendMarketBean("S1", 99L);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"S1", 99L, 99L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"S1", 99L, 99L});

        sendMap("M1", 100L, 101L);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"M1", 101L, 100L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"M1", 101L, 100L});
    }

    public void testConstantsAs()
    {
        // create window
        String stmtTextCreate = "create window MyWindow.win:keepall() as select '' as string, 0L as longPrimitive, 0L as longBoxed from MyMap";
        EPStatement stmtCreate = epService.getEPAdministrator().createEPL(stmtTextCreate);
        stmtCreate.addListener(listenerWindow);

        // create insert into
        String stmtTextInsertOne = "insert into MyWindow select string, longPrimitive, longBoxed from " + SupportBean.class.getName();
        epService.getEPAdministrator().createEPL(stmtTextInsertOne);

        String stmtTextInsertTwo = "insert into MyWindow select symbol as string, volume as longPrimitive, volume as longBoxed from " + SupportMarketDataBean.class.getName();
        epService.getEPAdministrator().createEPL(stmtTextInsertTwo);

        // create consumer
        String stmtTextSelectOne = "select string, longPrimitive, longBoxed from MyWindow";
        EPStatement stmtSelectOne = epService.getEPAdministrator().createEPL(stmtTextSelectOne);
        stmtSelectOne.addListener(listenerStmtOne);

        sendSupportBean("E1", 1L, 10L);
        String[] fields = new String[] {"string", "longPrimitive", "longBoxed"};
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E1", 1L, 10L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"E1", 1L, 10L});

        sendMarketBean("S1", 99L);
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"S1", 99L, 99L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"S1", 99L, 99L});
    }

    public void testCreateSchemaModelAfter()
    {
        epService.getEPAdministrator().createEPL("create schema EventTypeOne (hsi int)");
        epService.getEPAdministrator().createEPL("create schema EventTypeTwo (event EventTypeOne)");
        EPStatement stmt = epService.getEPAdministrator().createEPL("create window NamedWidnow.std:unique(event.hsi) as EventTypeTwo");
        epService.getEPAdministrator().createEPL("on EventTypeOne as ev insert into NamedWidnow select ev as event");

        Map<String, Object> event = new HashMap<String, Object>();
        event.put("hsi", 10);
        epService.getEPRuntime().sendEvent(event, "EventTypeOne");
        EventBean result = stmt.iterator().next();
        EventPropertyGetter getter = result.getEventType().getGetter("event.hsi");
        assertEquals(10, getter.get(result));
    }

    public void testCreateTableArray()
    {
        // create window
        String stmtTextCreate = "create window MyWindow.win:keepall() (myvalue string[])";
        EPStatement stmtCreate = epService.getEPAdministrator().createEPL(stmtTextCreate);
        stmtCreate.addListener(listenerWindow);

        // create insert into
        String stmtTextInsertOne = "insert into MyWindow select {'a','b'} as myvalue from " + SupportBean.class.getName();
        epService.getEPAdministrator().createEPL(stmtTextInsertOne);
        
        sendSupportBean("E1", 1L, 10L);
        String[] values = (String[]) listenerWindow.assertOneGetNewAndReset().get("myvalue");
        ArrayAssertionUtil.assertEqualsExactOrder(new String[] {"a","b"}, values);
    }

    public void testCreateTableSyntax()
    {
        // create window
        String stmtTextCreate = "create window MyWindow.win:keepall() (stringValOne varchar, stringValTwo string, intVal int, longVal long)";
        EPStatement stmtCreate = epService.getEPAdministrator().createEPL(stmtTextCreate);
        stmtCreate.addListener(listenerWindow);

        // assert type metadata
        EventTypeSPI type = (EventTypeSPI) ((EPServiceProviderSPI)epService).getEventAdapterService().getExistsTypeByName("MyWindow");
        assertEquals(null, type.getMetadata().getOptionalApplicationType());
        assertEquals(null, type.getMetadata().getOptionalSecondaryNames());
        assertEquals("MyWindow", type.getMetadata().getPrimaryName());
        assertEquals("MyWindow", type.getMetadata().getPublicName());
        assertEquals("MyWindow", type.getName());
        assertEquals(EventTypeMetadata.TypeClass.NAMED_WINDOW, type.getMetadata().getTypeClass());
        assertEquals(false, type.getMetadata().isApplicationConfigured());
        assertEquals(false, type.getMetadata().isApplicationPreConfigured());
        assertEquals(false, type.getMetadata().isApplicationPreConfiguredStatic());

        // create insert into
        String stmtTextInsertOne = "insert into MyWindow select string as stringValOne, string as stringValTwo, cast(longPrimitive, int) as intVal, longBoxed as longVal from " + SupportBean.class.getName();
        epService.getEPAdministrator().createEPL(stmtTextInsertOne);

        // create consumer
        String stmtTextSelectOne = "select stringValOne, stringValTwo, intVal, longVal from MyWindow";
        EPStatement stmtSelectOne = epService.getEPAdministrator().createEPL(stmtTextSelectOne);
        stmtSelectOne.addListener(listenerStmtOne);

        sendSupportBean("E1", 1L, 10L);
        String[] fields = "stringValOne,stringValTwo,intVal,longVal".split(",");
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E1", "E1", 1, 10L});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"E1","E1", 1, 10L});

        // create window with two views
        stmtTextCreate = "create window MyWindowTwo.std:unique(stringValOne).win:keepall() (stringValOne varchar, stringValTwo string, intVal int, longVal long)";
        stmtCreate = epService.getEPAdministrator().createEPL(stmtTextCreate);

        //create window with statement object model
        String text = "create window MyWindowThree.win:keepall() as (a string, b integer, c integer)";
        EPStatementObjectModel model = epService.getEPAdministrator().compileEPL(text);
        assertEquals(text, model.toEPL());
        stmtCreate = epService.getEPAdministrator().create(model);
        assertEquals(String.class, stmtCreate.getEventType().getPropertyType("a"));
        assertEquals(Integer.class, stmtCreate.getEventType().getPropertyType("b"));
        assertEquals(Integer.class, stmtCreate.getEventType().getPropertyType("c"));
        assertEquals(text, model.toEPL());

        text = "create window MyWindowFour.std:unique(a).std:unique(b) retain-union as (a string, b integer, c integer)";
        model = epService.getEPAdministrator().compileEPL(text);
        stmtCreate = epService.getEPAdministrator().create(model);
        assertEquals(text, model.toEPL());
    }

    public void testWildcardNoFieldsNoAs()
    {
        // create window
        String stmtTextCreate = "create window MyWindow.win:keepall() select * from " + SupportBean_A.class.getName();
        EPStatement stmtCreate = epService.getEPAdministrator().createEPL(stmtTextCreate);
        stmtCreate.addListener(listenerWindow);

        // create insert into
        String stmtTextInsertOne = "insert into MyWindow select * from " + SupportBean_A.class.getName();
        epService.getEPAdministrator().createEPL(stmtTextInsertOne);

        // create consumer
        String stmtTextSelectOne = "select id from default.MyWindow";
        EPStatement stmtSelectOne = epService.getEPAdministrator().createEPL(stmtTextSelectOne);
        stmtSelectOne.addListener(listenerStmtOne);

        epService.getEPRuntime().sendEvent(new SupportBean_A("E1"));
        String[] fields = new String[] {"id"};
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E1"});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"E1"});
    }

    public void testModelAfterMap()
    {
        // create window
        String stmtTextCreate = "create window MyWindow.win:keepall() select * from MyMap";
        EPStatement stmtCreate = epService.getEPAdministrator().createEPL(stmtTextCreate);
        assertTrue(stmtCreate.getEventType() instanceof MapEventType);

        // create insert into
        String stmtTextInsertOne = "insert into MyWindow select * from MyMap";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtTextInsertOne);
        stmt.addListener(listenerWindow);

        sendMap("k1", 100L, 200L);
        EventBean event = listenerWindow.assertOneGetNewAndReset();
        assertTrue(event instanceof MappedEventBean);
        ArrayAssertionUtil.assertProps(event, "key,primitive".split(","), new Object[] {"k1", 100L});
    }

    public void testWildcardInheritance()
    {
        // create window
        String stmtTextCreate = "create window MyWindow.win:keepall() as select * from " + SupportBeanBase.class.getName();
        EPStatement stmtCreate = epService.getEPAdministrator().createEPL(stmtTextCreate);
        stmtCreate.addListener(listenerWindow);

        // create insert into
        String stmtTextInsertOne = "insert into MyWindow select * from " + SupportBean_A.class.getName();
        epService.getEPAdministrator().createEPL(stmtTextInsertOne);

        // create insert into
        String stmtTextInsertTwo = "insert into MyWindow select * from " + SupportBean_B.class.getName();
        epService.getEPAdministrator().createEPL(stmtTextInsertTwo);

        // create consumer
        String stmtTextSelectOne = "select id from MyWindow";
        EPStatement stmtSelectOne = epService.getEPAdministrator().createEPL(stmtTextSelectOne);
        stmtSelectOne.addListener(listenerStmtOne);

        epService.getEPRuntime().sendEvent(new SupportBean_A("E1"));
        String[] fields = new String[] {"id"};
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E1"});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"E1"});

        epService.getEPRuntime().sendEvent(new SupportBean_B("E2"));
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E2"});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"E2"});
    }

    public void testNoSpecificationBean()
    {
        // create window
        String stmtTextCreate = "create window MyWindow.win:keepall() as " + SupportBean_A.class.getName();
        EPStatement stmtCreate = epService.getEPAdministrator().createEPL(stmtTextCreate);
        stmtCreate.addListener(listenerWindow);

        // create insert into
        String stmtTextInsertOne = "insert into MyWindow select * from " + SupportBean_A.class.getName();
        epService.getEPAdministrator().createEPL(stmtTextInsertOne);

        // create consumer
        String stmtTextSelectOne = "select id from MyWindow";
        EPStatement stmtSelectOne = epService.getEPAdministrator().createEPL(stmtTextSelectOne);
        stmtSelectOne.addListener(listenerStmtOne);

        epService.getEPRuntime().sendEvent(new SupportBean_A("E1"));
        String[] fields = new String[] {"id"};
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E1"});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"E1"});
    }

    public void testWildcardWithFields()
    {
        // create window
        String stmtTextCreate = "create window MyWindow.win:keepall() as select *, id as myid from " + SupportBean_A.class.getName();
        EPStatement stmtCreate = epService.getEPAdministrator().createEPL(stmtTextCreate);
        stmtCreate.addListener(listenerWindow);

        // create insert into
        String stmtTextInsertOne = "insert into MyWindow select *, id || 'A' as myid from " + SupportBean_A.class.getName();
        epService.getEPAdministrator().createEPL(stmtTextInsertOne);

        // create consumer
        String stmtTextSelectOne = "select id, myid from MyWindow";
        EPStatement stmtSelectOne = epService.getEPAdministrator().createEPL(stmtTextSelectOne);
        stmtSelectOne.addListener(listenerStmtOne);

        epService.getEPRuntime().sendEvent(new SupportBean_A("E1"));
        String[] fields = new String[] {"id", "myid"};
        ArrayAssertionUtil.assertProps(listenerWindow.assertOneGetNewAndReset(), fields, new Object[] {"E1", "E1A"});
        ArrayAssertionUtil.assertProps(listenerStmtOne.assertOneGetNewAndReset(), fields, new Object[] {"E1", "E1A"});
    }

    private SupportBean sendSupportBean(String string, long longPrimitive, Long longBoxed)
    {
        SupportBean bean = new SupportBean();
        bean.setString(string);
        bean.setLongPrimitive(longPrimitive);
        bean.setLongBoxed(longBoxed);
        epService.getEPRuntime().sendEvent(bean);
        return bean;
    }

    private void sendMarketBean(String symbol, long volume)
    {
        SupportMarketDataBean bean = new SupportMarketDataBean(symbol, 0, volume, "");
        epService.getEPRuntime().sendEvent(bean);
    }

    private void sendMap(String key, long primitive, Long boxed)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("key", key);
        map.put("primitive", primitive);
        map.put("boxed", boxed);
        epService.getEPRuntime().sendEvent(map, "MyMap");
    }
}
