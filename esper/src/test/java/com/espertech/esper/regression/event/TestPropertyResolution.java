package com.espertech.esper.regression.event;

import com.espertech.esper.client.*;
import com.espertech.esper.support.bean.*;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

public class TestPropertyResolution extends TestCase
{
    private EPServiceProvider epService;

    public void testReservedKeywordEscape()
    {
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
        epService.getEPAdministrator().getConfiguration().addEventType("SomeKeywords", SupportBeanReservedKeyword.class);
        epService.getEPAdministrator().getConfiguration().addEventType("Order", SupportBeanReservedKeyword.class);

        EPStatement stmt = epService.getEPAdministrator().createEPL("select `seconds`, `order` from SomeKeywords");
        SupportUpdateListener listener = new SupportUpdateListener();
        stmt.addListener(listener);

        Object event = new SupportBeanReservedKeyword(1, 2);
        epService.getEPRuntime().sendEvent(event);
        EventBean eventBean = listener.assertOneGetNewAndReset();
        assertEquals(1, eventBean.get("seconds"));
        assertEquals(2, eventBean.get("order"));

        stmt.destroy();
        stmt = epService.getEPAdministrator().createEPL("select * from `Order`");
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(event);
        eventBean = listener.assertOneGetNewAndReset();
        assertEquals(1, eventBean.get("seconds"));
        assertEquals(2, eventBean.get("order"));
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
        configuration.addEventType("Bean", SupportBean.class);

        tryCaseInsensitive(configuration, "select STRING, INTPRIMITIVE from Bean where STRING='A'", "STRING", "INTPRIMITIVE");
        tryCaseInsensitive(configuration, "select sTrInG, INTprimitIVE from Bean where STRing='A'", "sTrInG", "INTprimitIVE");
    }

    public void testCaseInsensitiveTypeConfig()
    {
        Configuration configuration = SupportConfigFactory.getConfiguration();
        ConfigurationEventTypeLegacy legacyDef = new ConfigurationEventTypeLegacy();
        legacyDef.setPropertyResolutionStyle(Configuration.PropertyResolutionStyle.CASE_INSENSITIVE);
        configuration.addEventType("Bean", SupportBean.class.getName(), legacyDef);

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
