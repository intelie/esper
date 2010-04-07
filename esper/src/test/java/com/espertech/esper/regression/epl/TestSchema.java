package com.espertech.esper.regression.epl;

import com.espertech.esper.client.*;
import com.espertech.esper.client.soda.EPStatementObjectModel;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;

public class TestSchema extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();
    }

    // Test invalid:
    // - invalid keyword between create and schema
    // - no inherit type name
    // - duplicate column name
    // - invalid column type
    // - no such bean
    // - no such variant type
    // - stop and type still in use
    // Test Named Window support for nestable+array types
    public void testInvalid() {
        tryInvalid("create schema MyEventType as (col1 xxxx)",
                    "Error starting statement: Nestable map type configuration encountered an unexpected property type name 'xxxx' for property 'col1', expected java.lang.Class or java.util.Map or the name of a previously-declared Map type [create schema MyEventType as (col1 xxxx)]");

        tryInvalid("create schema MyEventType as (col1 int, col1 string)",
                    "");
        tryInvalid("create schema MyEventType as (col1 xxxx)",
                    "");
        tryInvalid("create schema MyEventType as (col1 xxxx)",
                    "");

        epService.getEPAdministrator().createEPL("create schema MyEventType as (col1 string)");
        tryInvalid("create schema MyEventType as (col1 string, col2 string)",
                    "Error starting statement: Event type named 'MyEventType' has already been declared with differing column name or type information: Type by name 'MyEventType' expects 1 properties but receives 2 properties [create schema MyEventType as (col1 string, col2 string)]");

        tryInvalid("create schema MyEventType as () inherit ABC",
                    "Error in expression: Expected 'inherits' keyword after create-schema clause but encountered 'inherit' [create schema MyEventType as () inherit ABC]");

        tryInvalid("create schema MyEventType as () inherits ABC",
                    "Error starting statement: Map supertype by name 'ABC' could not be found [create schema MyEventType as () inherits ABC]");

        tryInvalid("create schema MyEventType as () inherits",
                    "Incorrect syntax near 'inherits' expecting an identifier but found end of input at line 1 column 32 [create schema MyEventType as () inherits]");
    }

    public void testDestroySameType() {
        EPStatement stmtOne = epService.getEPAdministrator().createEPL("create schema MyEventType as (col1 string)");
        EPStatement stmtTwo = epService.getEPAdministrator().createEPL("create schema MyEventType as (col1 string)");
        
        stmtOne.destroy();
        assertEquals(1, epService.getEPAdministrator().getConfiguration().getEventTypeNameUsedBy("MyEventType").size());
        assertTrue(epService.getEPAdministrator().getConfiguration().isEventTypeExists("MyEventType"));

        stmtTwo.destroy();
        assertEquals(0, epService.getEPAdministrator().getConfiguration().getEventTypeNameUsedBy("MyEventType").size());
        assertFalse(epService.getEPAdministrator().getConfiguration().isEventTypeExists("MyEventType"));
    }

    public void testColDefPlain() throws Exception
    {
        EPStatement stmtCreate = epService.getEPAdministrator().createEPL("create schema MyEventType as (col1 string, col2 int, sbean " + SupportBean.class.getName() + ")");
        assertTypeColDef(stmtCreate.getEventType());
        EPStatement stmtSelect = epService.getEPAdministrator().createEPL("select * from MyEventType");
        assertTypeColDef(stmtSelect.getEventType());

        stmtSelect.destroy();
        stmtCreate.destroy();

        // destroy and create differently 
        stmtCreate = epService.getEPAdministrator().createEPL("create schema MyEventType as (col3 string, col4 int)");
        assertEquals(Integer.class, stmtCreate.getEventType().getPropertyType("col4"));
        assertEquals(2, stmtCreate.getEventType().getPropertyDescriptors().length);

        stmtCreate.stop();

        // destroy and create differently
        stmtCreate = epService.getEPAdministrator().createEPL("create schema MyEventType as (col5 string, col6 int)");
        assertEquals(Integer.class, stmtCreate.getEventType().getPropertyType("col6"));
        assertEquals(2, stmtCreate.getEventType().getPropertyDescriptors().length);
        stmtSelect = epService.getEPAdministrator().createEPL("select * from MyEventType");
        stmtSelect.addListener(listener);

        // send event
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("col5", "abc");
        data.put("col6", 1);
        epService.getEPRuntime().sendEvent(data, "MyEventType");
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), "col5,col6".split(","), new Object[] {"abc", 1});
    }

    public void testModelPOJO() throws Exception
    {
        EPStatement stmtCreateOne = epService.getEPAdministrator().createEPL("create schema SupportBeanOne as " + SupportBean.class.getName());
        assertTypeSupportBean(stmtCreateOne.getEventType());

        EPStatement stmtCreateTwo = epService.getEPAdministrator().createEPL("create schema SupportBeanTwo as " + SupportBean.class.getName());
        assertTypeSupportBean(stmtCreateTwo.getEventType());

        EPStatement stmtSelectOne = epService.getEPAdministrator().createEPL("select * from SupportBeanOne");
        assertTypeSupportBean(stmtSelectOne.getEventType());
        stmtSelectOne.addListener(listener);

        EPStatement stmtSelectTwo = epService.getEPAdministrator().createEPL("select * from SupportBeanTwo");
        assertTypeSupportBean(stmtSelectTwo.getEventType());
        stmtSelectTwo.addListener(listener);
        
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 2));
        ArrayAssertionUtil.assertPropsPerRow(listener.getNewDataListFlattened(), "string,intPrimitive".split(","), new Object[][] {{"E1", 2}, {"E1", 2}});
    }

    public void testNestableMapArray() throws Exception
    {
        EPStatement stmtInner = epService.getEPAdministrator().createEPL("create schema MyInnerType as (col1 string[], col2 int[])");
        EventType inner = stmtInner.getEventType();
        assertEquals(String[].class, inner.getPropertyType("col1"));
        assertTrue(inner.getPropertyDescriptor("col1").isIndexed());
        assertEquals(Integer[].class, inner.getPropertyType("col2"));
        assertTrue(inner.getPropertyDescriptor("col2").isIndexed());

        EPStatement stmtOuter = epService.getEPAdministrator().createEPL("create schema MyOuterType as (col1 MyInnerType, col2 MyInnerType[])");
        FragmentEventType type = stmtOuter.getEventType().getFragmentType("col1");
        assertEquals("MyInnerType", type.getFragmentType().getName());
        assertFalse(type.isIndexed());
        assertFalse(type.isNative());
        type = stmtOuter.getEventType().getFragmentType("col2");
        assertEquals("MyInnerType", type.getFragmentType().getName());
        assertTrue(type.isIndexed());
        assertFalse(type.isNative());
        
        EPStatement stmtSelect = epService.getEPAdministrator().createEPL("select * from MyOuterType");
        stmtSelect.addListener(listener);

        Map<String, Object> innerData = new HashMap<String, Object>();
        innerData.put("col1", "abc,def".split(","));
        innerData.put("col2", new int[] {1, 2});
        Map<String, Object> outerData = new HashMap<String, Object>();
        outerData.put("col1", innerData);
        outerData.put("col2", new Map[] {innerData, innerData});
        epService.getEPRuntime().sendEvent(outerData, "MyOuterType");
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), "col1.col1[1],col2[1].col2[1]".split(","), new Object[] {"def", 2});
    }

    public void testInherit() throws Exception
    {
        epService.getEPAdministrator().createEPL("create schema MyParentType as (col1 int, col2 string)");
        EPStatement stmtChild = epService.getEPAdministrator().createEPL("create schema MyChildTypeOne (col3 int) inherits MyParentType");
        assertEquals(Integer.class, stmtChild.getEventType().getPropertyType("col1"));
        assertEquals(String.class, stmtChild.getEventType().getPropertyType("col2"));
        assertEquals(Integer.class, stmtChild.getEventType().getPropertyType("col3"));

        epService.getEPAdministrator().createEPL("create schema MyChildTypeTwo as (col4 boolean)");
        String createText = "create schema MyChildChildType as (col5 short, col6 long) inherits MyChildTypeOne, MyChildTypeTwo";
        EPStatementObjectModel model = epService.getEPAdministrator().compileEPL(createText);
        assertEquals(createText, model.toEPL());
        EPStatement stmtChildChild = epService.getEPAdministrator().create(model);
        assertEquals(Boolean.class, stmtChildChild.getEventType().getPropertyType("col4"));
        assertEquals(Integer.class, stmtChildChild.getEventType().getPropertyType("col3"));
        assertEquals(Short.class, stmtChildChild.getEventType().getPropertyType("col5"));

        EPStatement stmtChildChildTwo = epService.getEPAdministrator().createEPL("create schema MyChildChildTypeTwo () inherits MyChildTypeOne, MyChildTypeTwo");
        EventType e = stmtChildChildTwo.getEventType();
        assertEquals(Boolean.class, stmtChildChildTwo.getEventType().getPropertyType("col4"));
        assertEquals(Integer.class, stmtChildChildTwo.getEventType().getPropertyType("col3"));
    }

    public void testVariantType() throws Exception
    {
        epService.getEPAdministrator().createEPL("create schema MyTypeZero as (col1 int, col2 string)");
        epService.getEPAdministrator().createEPL("create schema MyTypeOne as (col1 int, col3 string, col4 int)");
        epService.getEPAdministrator().createEPL("create schema MyTypeTwo as (col1 int, col4 boolean, col5 short)");

        EPStatement stmtChildPredef = epService.getEPAdministrator().createEPL("create variant schema MyVariantPredef as MyTypeZero, MyTypeOne");
        EventType variantTypePredef = stmtChildPredef.getEventType();
        assertEquals(Integer.class, variantTypePredef.getPropertyType("col1"));
        assertEquals(1, variantTypePredef.getPropertyDescriptors().length);

        String createText = "create variant schema MyVariantAnyModel as MyTypeZero, MyTypeOne, *";
        EPStatementObjectModel model = epService.getEPAdministrator().compileEPL(createText);
        assertEquals(createText, model.toEPL());
        EPStatement stmtChildAnyModel = epService.getEPAdministrator().create(model);
        EventType predefAnyType = stmtChildAnyModel.getEventType();
        assertEquals(4, predefAnyType.getPropertyDescriptors().length);
        assertEquals(Object.class, predefAnyType.getPropertyType("col1"));
        assertEquals(Object.class, predefAnyType.getPropertyType("col2"));
        assertEquals(Object.class, predefAnyType.getPropertyType("col3"));
        assertEquals(Object.class, predefAnyType.getPropertyType("col4"));

        EPStatement stmtChildAny = epService.getEPAdministrator().createEPL("create variant schema MyVariantAny as *");
        EventType variantTypeAny = stmtChildAny.getEventType();
        assertEquals(0, variantTypeAny.getPropertyDescriptors().length);

        epService.getEPAdministrator().createEPL("insert into MyVariantAny select * from MyTypeZero");
        epService.getEPAdministrator().createEPL("insert into MyVariantAny select * from MyTypeOne");
        epService.getEPAdministrator().createEPL("insert into MyVariantAny select * from MyTypeTwo");

        epService.getEPAdministrator().createEPL("insert into MyVariantPredef select * from MyTypeZero");
        epService.getEPAdministrator().createEPL("insert into MyVariantPredef select * from MyTypeOne");
        try {
            epService.getEPAdministrator().createEPL("insert into MyVariantPredef select * from MyTypeTwo");
            fail();
        }
        catch (EPStatementException ex) {
            assertEquals("Error starting statement: Selected event type is not a valid event type of the variant stream 'MyVariantPredef' [insert into MyVariantPredef select * from MyTypeTwo]", ex.getMessage());
        }
    }

    private void tryInvalid(String epl, String message) {
        try {
            epService.getEPAdministrator().createEPL(epl);
            fail();
        }
        catch (EPStatementException ex) {
            assertEquals(message, ex.getMessage());
        }
    }

    private void assertTypeSupportBean(EventType eventType) {
        assertEquals(SupportBean.class, eventType.getUnderlyingType());
    }

    private void assertTypeColDef(EventType eventType) {
        assertEquals(String.class, eventType.getPropertyType("col1"));
        assertEquals(Integer.class, eventType.getPropertyType("col2"));
        assertEquals(SupportBean.class, eventType.getPropertyType("sbean"));
        assertEquals(3, eventType.getPropertyDescriptors().length);
    }
}
