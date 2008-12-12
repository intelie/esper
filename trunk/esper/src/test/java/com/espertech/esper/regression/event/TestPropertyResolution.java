package com.espertech.esper.regression.event;

import com.espertech.esper.client.*;
import com.espertech.esper.support.bean.*;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.event.EventTypeAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

import java.util.Map;
import java.util.HashMap;

public class TestPropertyResolution extends TestCase
{
    private EPServiceProvider epService;

    public void testFragmentBeanEvent()
    {
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
        SupportUpdateListener listener = new SupportUpdateListener();

        EPStatement stmt = epService.getEPAdministrator().createEPL("select * from " + SupportBeanComplexProps.class.getName());
        stmt.addListener(listener);
        stmt = epService.getEPAdministrator().createEPL("select * from " + SupportBeanCombinedProps.class.getName());
        stmt.addListener(listener);

        // assert nested fragments
        epService.getEPRuntime().sendEvent(SupportBeanComplexProps.makeDefaultBean());
        EventBean eventBean = listener.assertOneGetNewAndReset();
        EventTypeAssertionUtil.assertConsistency(eventBean.getEventType());
        //System.out.println(EventTypeAssertionUtil.print(eventBean));

        assertTrue(eventBean.getEventType().getPropertyDescriptor("nested").isFragment());
        EventBean eventNested = (EventBean) eventBean.getFragment("nested");
        assertEquals("nestedValue", eventNested.get("nestedValue"));
        eventNested = (EventBean) eventBean.getFragment("nested?");
        assertEquals("nestedValue", eventNested.get("nestedValue"));

        assertTrue(eventNested.getEventType().getPropertyDescriptor("nestedNested").isFragment());
        assertEquals("nestedNestedValue", ((EventBean) eventNested.getFragment("nestedNested")).get("nestedNestedValue"));
        assertEquals("nestedNestedValue", ((EventBean) eventNested.getFragment("nestedNested?")).get("nestedNestedValue"));

        EventBean nestedFragment = (EventBean) eventBean.getFragment("nested.nestedNested");
        assertEquals("nestedNestedValue", nestedFragment.get("nestedNestedValue"));

        // assert indexed fragments
        SupportBeanCombinedProps eventObject = SupportBeanCombinedProps.makeDefaultBean();
        epService.getEPRuntime().sendEvent(eventObject);
        eventBean = listener.assertOneGetNewAndReset();
        EventTypeAssertionUtil.assertConsistency(eventBean.getEventType());
        //System.out.println(EventTypeAssertionUtil.print(eventBean));

        assertTrue(eventBean.getEventType().getPropertyDescriptor("array").isFragment());
        assertTrue(eventBean.getEventType().getPropertyDescriptor("array").isIndexed());
        EventBean[] eventArray = (EventBean[]) eventBean.getFragment("array");
        assertEquals(3, eventArray.length);

        EventBean eventElement = eventArray[0];
        assertSame(eventObject.getArray()[0].getMapped("0ma"), eventElement.get("mapped('0ma')"));
        assertSame(eventObject.getArray()[0].getMapped("0ma"), ((EventBean)eventBean.getFragment("array[0]")).get("mapped('0ma')"));
        assertSame(eventObject.getArray()[0].getMapped("0ma"), ((EventBean)eventBean.getFragment("array[0]?")).get("mapped('0ma')"));
    }

    public void testFragmentMapEvent()
    {
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
        SupportUpdateListener listener = new SupportUpdateListener();

        Map<String, Object> mapInnerInner = new HashMap<String, Object>();
        mapInnerInner.put("innerInnerId", int.class);

        Map<String, Object> mapInner = new HashMap<String, Object>();
        mapInner.put("bean", SupportBean.class);
        mapInner.put("beanComplex", SupportBeanComplexProps.class);
        mapInner.put("beanArray", SupportBean[].class);
        mapInner.put("innerId", int.class);
        mapInner.put("innerMap", mapInnerInner);
        epService.getEPAdministrator().getConfiguration().addEventTypeAliasNestable("InnerMap", mapInner);

        Map<String, Object> mapOuter = new HashMap<String, Object>();
        mapOuter.put("innerSimple", "InnerMap");
        mapOuter.put("innerArray", "InnerMap[]");
        epService.getEPAdministrator().getConfiguration().addEventTypeAliasNestable("OuterMap", mapOuter);

        EPStatement stmt = epService.getEPAdministrator().createEPL("select * from OuterMap");
        stmt.addListener(listener);

        Map<String, Object> dataInnerInner = new HashMap<String, Object>();
        dataInnerInner.put("innerInnerId", 10);

        Map<String, Object> dataInner = new HashMap<String, Object>();
        dataInner.put("bean", new SupportBean("string1", 2000));
        dataInner.put("beanComplex", SupportBeanComplexProps.class);
        dataInner.put("beanArray", SupportBean[].class);
        dataInner.put("innerId", int.class);
        dataInner.put("innerMap", dataInnerInner);

        Map<String, Object> dataOuter = new HashMap<String, Object>();
        dataOuter.put("innerSimple", dataInner);
        dataOuter.put("innerArray", new Map[] {dataInner,dataInner});

        // send event
        epService.getEPRuntime().sendEvent(dataOuter, "OuterMap");
        EventBean eventBean = listener.assertOneGetNewAndReset();
        System.out.println(EventTypeAssertionUtil.print(eventBean));

        // assert simple fragment
        EventTypeAssertionUtil.assertConsistency(eventBean.getEventType());
    }

    public void testWriteOnly()
    {
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();

        EPStatement stmt = epService.getEPAdministrator().createEPL("select * from " + SupportBeanWriteOnly.class.getName());
        SupportUpdateListener listener = new SupportUpdateListener();
        stmt.addListener(listener);

        Object event = new SupportBeanWriteOnly();
        epService.getEPRuntime().sendEvent(event);
        EventBean eventBean = listener.assertOneGetNewAndReset();
        assertSame(event, eventBean.getUnderlying());

        EventType type = stmt.getEventType();
        assertEquals(0, type.getPropertyNames().length);
    }

    public void testCaseSensitive()
    {
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();

        EPStatement stmt = epService.getEPAdministrator().createEPL("select MYPROPERTY, myproperty, myProperty from " + SupportBeanDupProperty.class.getName());
        SupportUpdateListener listener = new SupportUpdateListener();
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBeanDupProperty("lowercamel", "uppercamel", "upper", "lower"));
        EventBean result = listener.assertOneGetNewAndReset();
        assertEquals("upper", result.get("MYPROPERTY"));
        assertEquals("lower", result.get("myproperty"));
        assertEquals("uppercamel", result.get("myProperty"));

        try
        {
            epService.getEPAdministrator().createEPL("select MyProperty from " + SupportBeanDupProperty.class.getName());
            fail();
        }
        catch (EPException ex)
        {
            // expected
        }
    }

    public void testCaseInsensitive()
    {
        Configuration configuration = SupportConfigFactory.getConfiguration();
        configuration.getEngineDefaults().getEventMeta().setClassPropertyResolutionStyle(Configuration.PropertyResolutionStyle.CASE_INSENSITIVE);
        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();

        EPStatement stmt = epService.getEPAdministrator().createEPL("select MYPROPERTY, myproperty, myProperty, MyProperty from " + SupportBeanDupProperty.class.getName());
        SupportUpdateListener listener = new SupportUpdateListener();
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBeanDupProperty("lowercamel", "uppercamel", "upper", "lower"));
        EventBean result = listener.assertOneGetNewAndReset();
        assertEquals("upper", result.get("MYPROPERTY"));
        assertEquals("lower", result.get("myproperty"));
        assertEquals("uppercamel", result.get("myProperty"));
        assertEquals("upper", result.get("MyProperty"));

        stmt = epService.getEPAdministrator().createEPL("select " +
                "NESTED.NESTEDVALUE as val1, " +
                "ARRAYPROPERTY[0] as val2, " +
                "MAPPED('keyOne') as val3, " +
                "INDEXED[0] as val4 " +
                " from " + SupportBeanComplexProps.class.getName());
        stmt.addListener(listener);
        epService.getEPRuntime().sendEvent(SupportBeanComplexProps.makeDefaultBean());
        EventBean event = listener.assertOneGetNewAndReset();
        assertEquals("nestedValue", event.get("val1"));
        assertEquals(10, event.get("val2"));
        assertEquals("valueOne", event.get("val3"));
        assertEquals(1, event.get("val4"));
    }

    public void testCaseDistinctInsensitive()
    {
        Configuration configuration = SupportConfigFactory.getConfiguration();
        configuration.getEngineDefaults().getEventMeta().setClassPropertyResolutionStyle(Configuration.PropertyResolutionStyle.DISTINCT_CASE_INSENSITIVE);
        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();

        EPStatement stmt = epService.getEPAdministrator().createEPL("select MYPROPERTY, myproperty, myProperty from " + SupportBeanDupProperty.class.getName());
        SupportUpdateListener listener = new SupportUpdateListener();
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBeanDupProperty("lowercamel", "uppercamel", "upper", "lower"));
        EventBean result = listener.assertOneGetNewAndReset();
        assertEquals("upper", result.get("MYPROPERTY"));
        assertEquals("lower", result.get("myproperty"));
        assertEquals("uppercamel", result.get("myProperty"));

        try
        {
            epService.getEPAdministrator().createEPL("select MyProperty from " + SupportBeanDupProperty.class.getName());
            fail();
        }
        catch (EPException ex)
        {
            assertEquals("Unable to determine which property to use for \"MyProperty\" because more than one property matched", ex.getMessage());
            // expected
        }
    }

    public void testCaseInsensitiveEngineDefault()
    {
        Configuration configuration = SupportConfigFactory.getConfiguration();
        configuration.getEngineDefaults().getEventMeta().setClassPropertyResolutionStyle(Configuration.PropertyResolutionStyle.CASE_INSENSITIVE);
        configuration.addEventTypeAlias("Bean", SupportBean.class);

        tryCaseInsensitive(configuration, "select STRING, INTPRIMITIVE from Bean where STRING='A'", "STRING", "INTPRIMITIVE");
        tryCaseInsensitive(configuration, "select sTrInG, INTprimitIVE from Bean where STRing='A'", "sTrInG", "INTprimitIVE");
    }

    public void testCaseInsensitiveTypeConfig()
    {
        Configuration configuration = SupportConfigFactory.getConfiguration();
        ConfigurationEventTypeLegacy legacyDef = new ConfigurationEventTypeLegacy();
        legacyDef.setPropertyResolutionStyle(Configuration.PropertyResolutionStyle.CASE_INSENSITIVE);
        configuration.addEventTypeAlias("Bean", SupportBean.class.getName(), legacyDef);

        tryCaseInsensitive(configuration, "select STRING, INTPRIMITIVE from Bean where STRING='A'", "STRING", "INTPRIMITIVE");
        tryCaseInsensitive(configuration, "select sTrInG, INTprimitIVE from Bean where STRing='A'", "sTrInG", "INTprimitIVE");
    }

    private void tryCaseInsensitive(Configuration configuration, String stmtText, String propOneName, String propTwoName)
    {
        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();

        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        SupportUpdateListener listener = new SupportUpdateListener();
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean("A", 10));
        EventBean result = listener.assertOneGetNewAndReset();
        assertEquals("A", result.get(propOneName));
        assertEquals(10, result.get(propTwoName));
    }

}
