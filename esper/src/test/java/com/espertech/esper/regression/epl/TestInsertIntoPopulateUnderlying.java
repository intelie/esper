package com.espertech.esper.regression.epl;

import com.espertech.esper.client.*;
import com.espertech.esper.client.time.CurrentTimeEvent;
import com.espertech.esper.support.bean.*;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.util.SupportSubscriber;
import com.espertech.esper.support.epl.SupportStaticMethodLib;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;

public class TestInsertIntoPopulateUnderlying extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;
    private SupportSubscriber subscriber;

    public void setUp()
    {
        Configuration configuration = SupportConfigFactory.getConfiguration();
        
        ConfigurationEventTypeLegacy legacy = new ConfigurationEventTypeLegacy();
        legacy.setFactoryMethod("getInstance");
        configuration.addEventType("SupportBeanString", SupportBeanString.class.getName(), legacy);
        configuration.addImport(TestInsertIntoPopulateUnderlying.class.getPackage().getName() + ".*");

        legacy = new ConfigurationEventTypeLegacy();
        legacy.setFactoryMethod(SupportSensorEventFactory.class.getName() + ".getInstance");
        configuration.addEventType("SupportSensorEvent", SupportSensorEvent.class.getName(), legacy);               

        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();
        listener = new SupportUpdateListener();
        subscriber = new SupportSubscriber();

        epService.getEPAdministrator().getConfiguration().addImport(SupportStaticMethodLib.class);
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);
        epService.getEPAdministrator().getConfiguration().addEventType("SupportTemperatureBean", SupportTemperatureBean.class);
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBeanComplexProps", SupportBeanComplexProps.class);
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBeanInterfaceProps", SupportBeanInterfaceProps.class);
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBeanErrorTestingOne", SupportBeanErrorTestingOne.class);
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBeanErrorTestingTwo", SupportBeanErrorTestingTwo.class);
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBeanReadOnly", SupportBeanReadOnly.class);
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBeanArrayCollMap", SupportBeanArrayCollMap.class);                
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean_N", SupportBean_N.class);
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean_S0", SupportBean_S0.class);
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBeanObject", SupportBeanObject.class);       
        epService.getEPAdministrator().getConfiguration().addImport(SupportEnum.class);

        Map<String, Object> mymapDef = new HashMap<String, Object>();
        mymapDef.put("anint", int.class);
        mymapDef.put("intBoxed", Integer.class);
        mymapDef.put("floatBoxed", Float.class);
        mymapDef.put("intArr", int[].class);
        mymapDef.put("mapProp", Map.class);
        mymapDef.put("isaImpl", ISupportAImpl.class);
        mymapDef.put("isbImpl", ISupportBImpl.class);
        mymapDef.put("isgImpl", ISupportAImplSuperGImpl.class);
        mymapDef.put("isabImpl", ISupportBaseABImpl.class);
        mymapDef.put("nested", SupportBeanComplexProps.SupportBeanSpecialGetterNested.class);
        epService.getEPAdministrator().getConfiguration().addEventType("MyMap", mymapDef);

        ConfigurationEventTypeXMLDOM xml = new ConfigurationEventTypeXMLDOM();
        xml.setRootElementName("abc");
        epService.getEPAdministrator().getConfiguration().addEventType("xmltype", xml);
    }

    public void testBeanJoin()
    {
        // test wildcard
        String stmtTextOne = "insert into SupportBeanObject select * from SupportBean_N.std:lastevent() as one, SupportBean_S0.std:lastevent() as two";
        EPStatement stmtOne = epService.getEPAdministrator().createEPL(stmtTextOne);
        stmtOne.addListener(listener);

        SupportBean_N n1 = new SupportBean_N(1, 10, 100d, 1000d, true, true);
        epService.getEPRuntime().sendEvent(n1);
        SupportBean_S0 s01 = new SupportBean_S0(1);
        epService.getEPRuntime().sendEvent(s01);
        SupportBeanObject event = (SupportBeanObject) listener.assertOneGetNewAndReset().getUnderlying();
        assertSame(n1, event.getOne());
        assertSame(s01, event.getTwo());

        // test select stream names
        stmtOne.destroy();
        stmtTextOne = "insert into SupportBeanObject select one, two from SupportBean_N.std:lastevent() as one, SupportBean_S0.std:lastevent() as two";
        stmtOne = epService.getEPAdministrator().createEPL(stmtTextOne);
        stmtOne.addListener(listener);

        epService.getEPRuntime().sendEvent(n1);
        epService.getEPRuntime().sendEvent(s01);
        event = (SupportBeanObject) listener.assertOneGetNewAndReset().getUnderlying();
        assertSame(n1, event.getOne());
        assertSame(s01, event.getTwo());
        stmtOne.destroy();

        // test fully-qualified class name as target
        stmtTextOne = "insert into " + SupportBeanObject.class.getName() + " select one, two from SupportBean_N.std:lastevent() as one, SupportBean_S0.std:lastevent() as two";
        stmtOne = epService.getEPAdministrator().createEPL(stmtTextOne);
        stmtOne.addListener(listener);

        epService.getEPRuntime().sendEvent(n1);
        epService.getEPRuntime().sendEvent(s01);
        event = (SupportBeanObject) listener.assertOneGetNewAndReset().getUnderlying();
        assertSame(n1, event.getOne());
        assertSame(s01, event.getTwo());

        // test local class and auto-import
        stmtOne.destroy();
        stmtTextOne = "insert into " + this.getClass().getName() + "$MyLocalTarget select 1 as value from SupportBean_N";
        stmtOne = epService.getEPAdministrator().createEPL(stmtTextOne);
        stmtOne.addListener(listener);
        epService.getEPRuntime().sendEvent(n1);
        MyLocalTarget eventLocal = (MyLocalTarget) listener.assertOneGetNewAndReset().getUnderlying();
        assertEquals(1, eventLocal.getValue());
    }

    public void testInvalid()
    {
        String text = "insert into SupportBean(intPrimitive) select 1L from SupportBean";
        tryInvalid("Error starting statement: Invalid assignment of column 'intPrimitive' of type 'java.lang.Long' to event property 'intPrimitive' typed as 'int', column and parameter types mismatch [insert into SupportBean(intPrimitive) select 1L from SupportBean]", text);

        text = "insert into SupportBean(intPrimitive) select null from SupportBean";
        tryInvalid("Error starting statement: Invalid assignment of column 'intPrimitive' of null type to event property 'intPrimitive' typed as 'int', nullable type mismatch [insert into SupportBean(intPrimitive) select null from SupportBean]", text);
        
        text = "insert into SupportTemperatureBean select 'a' as geom from SupportBean";
        tryInvalid("Error starting statement: Failed to instantiate class 'com.espertech.esper.support.bean.SupportTemperatureBean', define a factory method if the class has no default constructor [insert into SupportTemperatureBean select 'a' as geom from SupportBean]", text);

        text = "insert into SupportBean select 3 as dummyField from SupportBean";
        tryInvalid("Error starting statement: Column 'dummyField' could not be assigned to any of the properties of the underlying type (missing column names, event property or setter method?) [insert into SupportBean select 3 as dummyField from SupportBean]", text);

        text = "insert into SupportBean select 3 from SupportBean";
        tryInvalid("Error starting statement: Column '3' could not be assigned to any of the properties of the underlying type (missing column names, event property or setter method?) [insert into SupportBean select 3 from SupportBean]", text);

        text = "insert into SupportBeanInterfaceProps(isa) select isbImpl from MyMap";
        tryInvalid("Error starting statement: Invalid assignment of column 'isa' of type 'com.espertech.esper.support.bean.ISupportBImpl' to event property 'isa' typed as 'com.espertech.esper.support.bean.ISupportA', column and parameter types mismatch [insert into SupportBeanInterfaceProps(isa) select isbImpl from MyMap]", text);

        text = "insert into SupportBeanInterfaceProps(isg) select isabImpl from MyMap";
        tryInvalid("Error starting statement: Invalid assignment of column 'isg' of type 'com.espertech.esper.support.bean.ISupportBaseABImpl' to event property 'isg' typed as 'com.espertech.esper.support.bean.ISupportAImplSuperG', column and parameter types mismatch [insert into SupportBeanInterfaceProps(isg) select isabImpl from MyMap]", text);

        text = "insert into SupportBean(dummy) select 3 from SupportBean";
        tryInvalid("Error starting statement: Column 'dummy' could not be assigned to any of the properties of the underlying type (missing column names, event property or setter method?) [insert into SupportBean(dummy) select 3 from SupportBean]", text);

        text = "insert into SupportBeanErrorTestingOne(value) select 'E1' from MyMap";
        tryInvalid("Error starting statement: Failed to instantiate class 'com.espertech.esper.support.bean.SupportBeanErrorTestingOne', define a factory method if the class has no default constructor: Default ctor manufactured test exception [insert into SupportBeanErrorTestingOne(value) select 'E1' from MyMap]", text);

        text = "insert into SupportBeanReadOnly(side) select 'E1' from MyMap";
        tryInvalid("Error starting statement: Column 'side' could not be assigned to any of the properties of the underlying type (missing column names, event property or setter method?) [insert into SupportBeanReadOnly(side) select 'E1' from MyMap]", text);

        epService.getEPAdministrator().createEPL("insert into ABCStream select *, 1+1 from SupportBean");
        text = "insert into ABCStream(string) select 'E1' from MyMap";
        tryInvalid("Error starting statement: Event type named 'ABCStream' has already been declared with differing column name or type information: Type by name 'ABCStream' is not a compatible type (target type underlying is 'Pair') [insert into ABCStream(string) select 'E1' from MyMap]", text);

        text = "insert into xmltype select 1 from SupportBean";
        tryInvalid("Error starting statement: Event type named 'xmltype' has already been declared with differing column name or type information: Type by name 'xmltype' is not a compatible type (target type underlying is 'Node') [insert into xmltype select 1 from SupportBean]", text);

        text = "insert into MyMap(dummy) select 1 from SupportBean";
        tryInvalid("Error starting statement: Event type named 'MyMap' has already been declared with differing column name or type information: Type by name 'MyMap' expects 10 properties but receives 1 properties [insert into MyMap(dummy) select 1 from SupportBean]", text);

        // setter throws exception
        String stmtTextOne = "insert into SupportBeanErrorTestingTwo(value) select 'E1' from MyMap";
        EPStatement stmtOne = epService.getEPAdministrator().createEPL(stmtTextOne);
        stmtOne.addListener(listener);
        epService.getEPRuntime().sendEvent(new HashMap(), "MyMap");
        SupportBeanErrorTestingTwo underlying = (SupportBeanErrorTestingTwo) listener.assertOneGetNewAndReset().getUnderlying();
        assertEquals("default", underlying.getValue());
        stmtOne.destroy();

        // surprise - wrong type then defined
        stmtTextOne = "insert into SupportBean(intPrimitive) select anint from MyMap";
        stmtOne = epService.getEPAdministrator().createEPL(stmtTextOne);
        stmtOne.addListener(listener);
        listener.reset();
        Map map = new HashMap();
        map.put("anint", "notAnInt");
        epService.getEPRuntime().sendEvent(map, "MyMap");
        assertEquals(0, listener.assertOneGetNewAndReset().get("intPrimitive"));
    }

    public void testPopulateBeanSimple()
    {
        // test select column names
        String stmtTextOne = "insert into SupportBean select " +
                "'E1' as string, 1 as intPrimitive, 2 as intBoxed, 3L as longPrimitive," +
                "null as longBoxed, true as boolPrimitive, " +
                "'x' as charPrimitive, 0xA as bytePrimitive, " +
                "8.0f as floatPrimitive, 9.0d as doublePrimitive, " +
                "0x05 as shortPrimitive, SupportEnum.ENUM_VALUE_2 as enumValue " +
                " from MyMap";
        EPStatement stmtOne = epService.getEPAdministrator().createEPL(stmtTextOne);

        String stmtTextTwo = "select * from SupportBean";
        EPStatement stmtTwo = epService.getEPAdministrator().createEPL(stmtTextTwo);
        stmtTwo.addListener(listener);

        epService.getEPRuntime().sendEvent(new HashMap(), "MyMap");
        SupportBean received = (SupportBean) listener.assertOneGetNewAndReset().getUnderlying();
        assertEquals("E1", received.getString());
        ArrayAssertionUtil.assertProps(received,
                "intPrimitive,intBoxed,longPrimitive,longBoxed,boolPrimitive,charPrimitive,bytePrimitive,floatPrimitive,doublePrimitive,shortPrimitive,enumValue".split(","),
                new Object[] {1, 2, 3l, null, true, 'x', (byte) 10, 8f, 9d, (short)5, SupportEnum.ENUM_VALUE_2});

        // test insert-into column names
        stmtOne.destroy();
        stmtTwo.destroy();
        listener.reset();
        stmtTextOne = "insert into SupportBean(string, intPrimitive, intBoxed, longPrimitive," +
                "longBoxed, boolPrimitive, charPrimitive, bytePrimitive, floatPrimitive, doublePrimitive, " +
                "shortPrimitive, enumValue) select " +
                "'E1', 1, 2, 3L," +
                "null, true, " +
                "'x', 0xA, " +
                "8.0f, 9.0d, " +
                "0x05 as shortPrimitive, SupportEnum.ENUM_VALUE_2 " +
                " from MyMap";
        stmtOne = epService.getEPAdministrator().createEPL(stmtTextOne);
        stmtOne.addListener(listener);

        epService.getEPRuntime().sendEvent(new HashMap(), "MyMap");
        received = (SupportBean) listener.assertOneGetNewAndReset().getUnderlying();
        assertEquals("E1", received.getString());
        ArrayAssertionUtil.assertProps(received,
                "intPrimitive,intBoxed,longPrimitive,longBoxed,boolPrimitive,charPrimitive,bytePrimitive,floatPrimitive,doublePrimitive,shortPrimitive,enumValue".split(","),
                new Object[] {1, 2, 3l, null, true, 'x', (byte) 10, 8f, 9d, (short)5, SupportEnum.ENUM_VALUE_2});

        // test convert Integer boxed to Long boxed
        stmtOne.destroy();
        listener.reset();
        stmtTextOne = "insert into SupportBean(longBoxed, doubleBoxed) select intBoxed, floatBoxed from MyMap";
        stmtOne = epService.getEPAdministrator().createEPL(stmtTextOne);
        stmtOne.addListener(listener);

        Map<String, Object> vals = new HashMap<String, Object>();
        vals.put("intBoxed", 4);
        vals.put("floatBoxed", 0f);
        epService.getEPRuntime().sendEvent(vals, "MyMap");
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(),"longBoxed,doubleBoxed".split(","), new Object[] {4L, 0d});
    }

    public void testBeanWildcard()
    {
        Map<String, Object> mapDef = new HashMap<String, Object>();
        mapDef.put("intPrimitive", int.class);
        mapDef.put("longBoxed", Long.class);
        mapDef.put("string", String.class);
        mapDef.put("boolPrimitive", Boolean.class);
        epService.getEPAdministrator().getConfiguration().addEventType("MySupportMap", mapDef);

        String stmtTextOne = "insert into SupportBean select * from MySupportMap";
        EPStatement stmtOne = epService.getEPAdministrator().createEPL(stmtTextOne);
        stmtOne.addListener(listener);

        Map<String, Object> vals = new HashMap<String, Object>();
        vals.put("intPrimitive", 4);
        vals.put("longBoxed", 100L);
        vals.put("string", "E1");
        vals.put("boolPrimitive", true);

        epService.getEPRuntime().sendEvent(vals, "MySupportMap");
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(),
                "intPrimitive,longBoxed,string,boolPrimitive".split(","),
                new Object[] {4, 100L, "E1", true});
    }

    public void testPopulateBeanObjects()
    {
        // arrays and maps
        String stmtTextOne = "insert into SupportBeanComplexProps(arrayProperty,objectArray,mapProperty) select " +
                "intArr,{10,20,30},mapProp" +
                " from MyMap as m";
        EPStatement stmtOne = epService.getEPAdministrator().createEPL(stmtTextOne);
        stmtOne.addListener(listener);

        Map<String, Object> mymapVals = new HashMap<String, Object>();
        mymapVals.put("intArr", new int[] {-1, -2});
        Map inner = new HashMap<String, Object>();
        inner.put("mykey", "myval");
        mymapVals.put("mapProp", inner);
        epService.getEPRuntime().sendEvent(mymapVals, "MyMap");
        SupportBeanComplexProps event = (SupportBeanComplexProps) listener.assertOneGetNewAndReset().getUnderlying();
        assertEquals(-2, event.getArrayProperty()[1]);
        assertEquals(20, event.getObjectArray()[1]);
        assertEquals("myval", event.getMapProperty().get("mykey"));

        // inheritance
        stmtOne.destroy();
        stmtTextOne = "insert into SupportBeanInterfaceProps(isa,isg) select " +
                "isaImpl,isgImpl" +
                " from MyMap";
        stmtOne = epService.getEPAdministrator().createEPL(stmtTextOne);
        stmtOne.addListener(listener);

        mymapVals = new HashMap<String, Object>();
        mymapVals.put("mapProp", inner);
        epService.getEPRuntime().sendEvent(mymapVals, "MyMap");
        SupportBeanInterfaceProps eventTwo = (SupportBeanInterfaceProps) listener.assertOneGetNewAndReset().getUnderlying();
        assertEquals(SupportBeanInterfaceProps.class, stmtOne.getEventType().getUnderlyingType());

        // object values from Map same type
        stmtOne.destroy();
        stmtTextOne = "insert into SupportBeanComplexProps(nested) select nested from MyMap";
        stmtOne = epService.getEPAdministrator().createEPL(stmtTextOne);
        stmtOne.addListener(listener);

        mymapVals = new HashMap<String, Object>();
        mymapVals.put("nested", new SupportBeanComplexProps.SupportBeanSpecialGetterNested("111", "222"));
        epService.getEPRuntime().sendEvent(mymapVals, "MyMap");
        SupportBeanComplexProps eventThree = (SupportBeanComplexProps) listener.assertOneGetNewAndReset().getUnderlying();
        assertEquals("111", eventThree.getNested().getNestedValue());

        // object to Object
        stmtOne.destroy();
        stmtTextOne = "insert into SupportBeanArrayCollMap(anyObject) select nested from SupportBeanComplexProps";
        stmtOne = epService.getEPAdministrator().createEPL(stmtTextOne);
        stmtOne.addListener(listener);

        epService.getEPRuntime().sendEvent(SupportBeanComplexProps.makeDefaultBean());
        SupportBeanArrayCollMap eventFour = (SupportBeanArrayCollMap) listener.assertOneGetNewAndReset().getUnderlying();
        assertEquals("nestedValue", ((SupportBeanComplexProps.SupportBeanSpecialGetterNested) eventFour.getAnyObject()).getNestedValue());

        // test null value
        String stmtTextThree = "insert into SupportBean select 'B' as string, intBoxed as intPrimitive from SupportBean(string='A')";
        EPStatement stmtThree = epService.getEPAdministrator().createEPL(stmtTextThree);
        stmtThree.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean("A", 0));
        SupportBean received = (SupportBean) listener.assertOneGetNewAndReset().getUnderlying();
        assertEquals(0, received.getIntPrimitive());

        SupportBean bean = new SupportBean("A", 1);
        bean.setIntBoxed(20);
        epService.getEPRuntime().sendEvent(bean);
        received = (SupportBean) listener.assertOneGetNewAndReset().getUnderlying();
        assertEquals(20, received.getIntPrimitive());
    }

    public void testPopulateMap()
    {
        Map<String, Object> defMap = new HashMap<String, Object>();
        defMap.put("intVal", int.class);
        defMap.put("stringVal", String.class);
        defMap.put("doubleVal", Double.class);
        defMap.put("nullVal", null);
        epService.getEPAdministrator().getConfiguration().addEventType("MyMapType", defMap);
        EPStatement stmtOrig = epService.getEPAdministrator().createEPL("select * from MyMapType");

        String stmtTextOne = "insert into MyMapType select intPrimitive as intVal, string as stringVal, doubleBoxed as doubleVal from SupportBean";
        EPStatement stmtOne = epService.getEPAdministrator().createEPL(stmtTextOne);
        stmtOne.addListener(listener);
        assertSame(stmtOrig.getEventType(), stmtOne.getEventType());

        SupportBean bean = new SupportBean();
        bean.setIntPrimitive(1000);
        bean.setString("E1");
        bean.setDoubleBoxed(1001d);
        epService.getEPRuntime().sendEvent(bean);
        
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(),"intVal,stringVal,doubleVal".split(","),new Object[] {1000, "E1", 1001d});
    }

    public void testBeanFactoryMethod()
    {
        // test factory method on the same event class
        String stmtTextOne = "insert into SupportBeanString select 'abc' as string from MyMap";
        EPStatement stmtOne = epService.getEPAdministrator().createEPL(stmtTextOne);
        stmtOne.addListener(listener);
        stmtOne.setSubscriber(subscriber);

        epService.getEPRuntime().sendEvent(new HashMap(), "MyMap");
        assertEquals("abc", listener.assertOneGetNewAndReset().get("string"));
        assertEquals("abc", subscriber.assertOneGetNewAndReset());
        stmtOne.destroy();

        // test factory method fully-qualified
        stmtTextOne = "insert into SupportSensorEvent(id, type, device, measurement, confidence)" +
                "select 2, 'A01', 'DHC1000', 100, 5 from MyMap";
        stmtOne = epService.getEPAdministrator().createEPL(stmtTextOne);
        stmtOne.addListener(listener);

        epService.getEPRuntime().sendEvent(new HashMap(), "MyMap");
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), "id,type,device,measurement,confidence".split(","), new Object[] {2,"A01","DHC1000",100.0,5.0});

        try
        {
            SupportBeanString.class.newInstance();
            fail();
        }
        catch(InstantiationException ex)
        {
            // expected
        }
        catch(Exception ex)
        {
            fail();
        }

        try
        {
            SupportSensorEvent.class.newInstance();
            fail();
        }
        catch(IllegalAccessException ex)
        {
            // expected
        }
        catch (InstantiationException e)
        {
            fail();
        }
    }

    public void testArrayPOJOInsert() {

        epService.getEPAdministrator().getConfiguration().addEventType("FinalEventInvalidNonArray", FinalEventInvalidNonArray.class);
        epService.getEPAdministrator().getConfiguration().addEventType("FinalEventInvalidArray", FinalEventInvalidArray.class);
        epService.getEPAdministrator().getConfiguration().addEventType("FinalEventValid", FinalEventValid.class);

        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(0));

        // Test valid case of array insert
        String validEpl = "INSERT INTO FinalEventValid SELECT s as startEvent, e as endEvent FROM PATTERN [" +
                "every s=SupportBean_S0 -> e=SupportBean(string=s.p00) until timer:interval(10 sec)]";
        EPStatement stmt = epService.getEPAdministrator().createEPL(validEpl);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean_S0(1, "G1"));
        epService.getEPRuntime().sendEvent(new SupportBean("G1", 2));
        epService.getEPRuntime().sendEvent(new SupportBean("G1", 3));
        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(10000));

        FinalEventValid out = ((FinalEventValid) listener.assertOneGetNewAndReset().getUnderlying());
        assertEquals(1, out.getStartEvent().getId());
        assertEquals("G1", out.getStartEvent().getP00());
        assertEquals(2, out.getEndEvent().length);
        assertEquals(2, out.getEndEvent()[0].getIntPrimitive());
        assertEquals(3, out.getEndEvent()[1].getIntPrimitive());

        // Test invalid case of non-array destination insert
        String invalidEpl = "INSERT INTO FinalEventInvalidNonArray SELECT s as startEvent, e as endEvent FROM PATTERN [" +
                "every s=SupportBean_S0 -> e=SupportBean(string=s.p00) until timer:interval(10 sec)]";
        try {
            epService.getEPAdministrator().createEPL(invalidEpl);
            fail();
        }
        catch (EPException ex) {
            assertEquals("Error starting statement: Invalid assignment of column 'endEvent' of type 'com.espertech.esper.support.bean.SupportBean[]' to event property 'endEvent' typed as 'com.espertech.esper.support.bean.SupportBean', column and parameter types mismatch [INSERT INTO FinalEventInvalidNonArray SELECT s as startEvent, e as endEvent FROM PATTERN [every s=SupportBean_S0 -> e=SupportBean(string=s.p00) until timer:interval(10 sec)]]", ex.getMessage());
        }

        // Test invalid case of array destination insert from non-array var
        String invalidEplTwo = "INSERT INTO FinalEventInvalidArray SELECT s as startEvent, e as endEvent FROM PATTERN [" +
                "every s=SupportBean_S0 -> e=SupportBean(string=s.p00) until timer:interval(10 sec)]";
        try {
            epService.getEPAdministrator().createEPL(invalidEplTwo);
            fail();
        }
        catch (EPException ex) {
            assertEquals("Error starting statement: Invalid assignment of column 'startEvent' of type 'com.espertech.esper.support.bean.SupportBean_S0' to event property 'startEvent' typed as 'com.espertech.esper.support.bean.SupportBean_S0[]', column and parameter types mismatch [INSERT INTO FinalEventInvalidArray SELECT s as startEvent, e as endEvent FROM PATTERN [every s=SupportBean_S0 -> e=SupportBean(string=s.p00) until timer:interval(10 sec)]]", ex.getMessage());
        }
    }

    public void testArrayMapInsert() {

        epService.getEPAdministrator().createEPL("create schema EventOne(id string)");
        epService.getEPAdministrator().createEPL("create schema EventTwo(id string, val int)");
        epService.getEPAdministrator().createEPL("create schema FinalEventValid (startEvent EventOne, endEvent EventTwo[])");
        epService.getEPAdministrator().createEPL("create schema FinalEventInvalidNonArray (startEvent EventOne, endEvent EventTwo)");
        epService.getEPAdministrator().createEPL("create schema FinalEventInvalidArray (startEvent EventOne, endEvent EventTwo)");

        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(0));

        // Test valid case of array insert
        String validEpl = "INSERT INTO FinalEventValid SELECT s as startEvent, e as endEvent FROM PATTERN [" +
                "every s=EventOne -> e=EventTwo(id=s.id) until timer:interval(10 sec)]";
        EPStatement stmt = epService.getEPAdministrator().createEPL(validEpl);
        stmt.addListener(listener);

        sendEventOne("G1");
        sendEventTwo("G1", 2);
        sendEventTwo("G1", 3);
        epService.getEPRuntime().sendEvent(new CurrentTimeEvent(10000));

        Map out = ((Map) listener.assertOneGetNewAndReset().getUnderlying());
        EventBean startEventOne = (EventBean) out.get("startEvent");
        EventBean endEventOne = ((EventBean[]) out.get("endEvent"))[0];
        EventBean endEventTwo = ((EventBean[]) out.get("endEvent"))[1];
        assertEquals("G1", startEventOne.get("id"));
        assertEquals(2, endEventOne.get("val"));
        assertEquals(3, endEventTwo.get("val"));

        // Test invalid case of non-array destination insert
        String invalidEpl = "INSERT INTO FinalEventInvalidNonArray SELECT s as startEvent, e as endEvent FROM PATTERN [" +
                "every s=EventOne -> e=EventTwo(id=s.id) until timer:interval(10 sec)]";
        try {
            epService.getEPAdministrator().createEPL(invalidEpl);
            fail();
        }
        catch (EPException ex) {
            assertEquals("Error starting statement: Event type named 'FinalEventInvalidNonArray' has already been declared with differing column name or type information: Type by name 'FinalEventInvalidNonArray' in property 'endEvent' expected event type 'EventTwo' but receives event type 'EventTwo[]' [INSERT INTO FinalEventInvalidNonArray SELECT s as startEvent, e as endEvent FROM PATTERN [every s=EventOne -> e=EventTwo(id=s.id) until timer:interval(10 sec)]]", ex.getMessage());
        }

        // Test invalid case of array destination insert from non-array var
        String invalidEplTwo = "INSERT INTO FinalEventInvalidArray SELECT s as startEvent, e as endEvent FROM PATTERN [" +
                "every s=EventOne -> e=EventTwo(id=s.id) until timer:interval(10 sec)]";
        try {
            epService.getEPAdministrator().createEPL(invalidEplTwo);
            fail();
        }
        catch (EPException ex) {
            assertEquals("Error starting statement: Event type named 'FinalEventInvalidArray' has already been declared with differing column name or type information: Type by name 'FinalEventInvalidArray' in property 'endEvent' expected event type 'EventTwo' but receives event type 'EventTwo[]' [INSERT INTO FinalEventInvalidArray SELECT s as startEvent, e as endEvent FROM PATTERN [every s=EventOne -> e=EventTwo(id=s.id) until timer:interval(10 sec)]]", ex.getMessage());
        }
    }

    private void sendEventTwo(String id, int val) {
        Map<String, Object> event = new HashMap<String, Object>();
        event.put("id", id);
        event.put("val", val);
        epService.getEPRuntime().sendEvent(event, "EventTwo");
    }

    private void sendEventOne(String id) {
        Map<String, Object> event = new HashMap<String, Object>();
        event.put("id", id);
        epService.getEPRuntime().sendEvent(event, "EventOne");
    }

    private void tryInvalid(String msg, String stmt)
    {
        try
        {
            epService.getEPAdministrator().createEPL(stmt);
            fail();
        }
        catch (EPStatementException ex)
        {
            assertEquals(msg, ex.getMessage());
        }
    }

    public static class FinalEventInvalidNonArray {
        private SupportBean_S0 startEvent;
        private SupportBean endEvent;

        public SupportBean_S0 getStartEvent() {
            return startEvent;
        }

        public void setStartEvent(SupportBean_S0 startEvent) {
            this.startEvent = startEvent;
        }

        public SupportBean getEndEvent() {
            return endEvent;
        }

        public void setEndEvent(SupportBean endEvent) {
            this.endEvent = endEvent;
        }
    }

    public static class FinalEventInvalidArray {
        private SupportBean_S0[] startEvent;
        private SupportBean[] endEvent;

        public SupportBean_S0[] getStartEvent() {
            return startEvent;
        }

        public void setStartEvent(SupportBean_S0[] startEvent) {
            this.startEvent = startEvent;
        }

        public SupportBean[] getEndEvent() {
            return endEvent;
        }

        public void setEndEvent(SupportBean[] endEvent) {
            this.endEvent = endEvent;
        }
    }

    public static class FinalEventValid {
        private SupportBean_S0 startEvent;
        private SupportBean[] endEvent;

        public SupportBean_S0 getStartEvent() {
            return startEvent;
        }

        public void setStartEvent(SupportBean_S0 startEvent) {
            this.startEvent = startEvent;
        }

        public SupportBean[] getEndEvent() {
            return endEvent;
        }

        public void setEndEvent(SupportBean[] endEvent) {
            this.endEvent = endEvent;
        }
    }

    public static class MyLocalTarget {
        public int value;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }
}
