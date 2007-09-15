package net.esper.regression.event;

import junit.framework.TestCase;
import net.esper.client.*;
import net.esper.event.EventBean;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportBeanDupProperty;
import net.esper.support.bean.SupportBeanComplexProps;
import net.esper.support.bean.SupportBeanWriteOnly;
import net.esper.support.client.SupportConfigFactory;
import net.esper.support.util.SupportUpdateListener;

public class TestPropertyResolution extends TestCase
{
    private EPServiceProvider epService;

    public void testWriteOnly()
    {
        epService = EPServiceProviderManager.getDefaultProvider();
        epService.initialize();

        EPStatement stmt = epService.getEPAdministrator().createEQL("select * from " + SupportBeanWriteOnly.class.getName());
        SupportUpdateListener listener = new SupportUpdateListener();
        stmt.addListener(listener);

        Object event = new SupportBeanWriteOnly();
        epService.getEPRuntime().sendEvent(event);
        assertSame(event, listener.assertOneGetNewAndReset().getUnderlying());
    }

    public void testCaseSensitive()
    {
        epService = EPServiceProviderManager.getDefaultProvider();
        epService.initialize();

        EPStatement stmt = epService.getEPAdministrator().createEQL("select MYPROPERTY, myproperty, myProperty from " + SupportBeanDupProperty.class.getName());
        SupportUpdateListener listener = new SupportUpdateListener();
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBeanDupProperty("lowercamel", "uppercamel", "upper", "lower"));
        EventBean result = listener.assertOneGetNewAndReset();
        assertEquals("upper", result.get("MYPROPERTY"));
        assertEquals("lower", result.get("myproperty"));
        assertEquals("uppercamel", result.get("myProperty"));

        try
        {
            epService.getEPAdministrator().createEQL("select MyProperty from " + SupportBeanDupProperty.class.getName());
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

        EPStatement stmt = epService.getEPAdministrator().createEQL("select MYPROPERTY, myproperty, myProperty, MyProperty from " + SupportBeanDupProperty.class.getName());
        SupportUpdateListener listener = new SupportUpdateListener();
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBeanDupProperty("lowercamel", "uppercamel", "upper", "lower"));
        EventBean result = listener.assertOneGetNewAndReset();
        assertEquals("upper", result.get("MYPROPERTY"));
        assertEquals("lower", result.get("myproperty"));
        assertEquals("uppercamel", result.get("myProperty"));
        assertEquals("upper", result.get("MyProperty"));

        stmt = epService.getEPAdministrator().createEQL("select " +
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

        EPStatement stmt = epService.getEPAdministrator().createEQL("select MYPROPERTY, myproperty, myProperty from " + SupportBeanDupProperty.class.getName());
        SupportUpdateListener listener = new SupportUpdateListener();
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBeanDupProperty("lowercamel", "uppercamel", "upper", "lower"));
        EventBean result = listener.assertOneGetNewAndReset();
        assertEquals("upper", result.get("MYPROPERTY"));
        assertEquals("lower", result.get("myproperty"));
        assertEquals("uppercamel", result.get("myProperty"));

        try
        {
            epService.getEPAdministrator().createEQL("select MyProperty from " + SupportBeanDupProperty.class.getName());
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

        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        SupportUpdateListener listener = new SupportUpdateListener();
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean("A", 10));
        EventBean result = listener.assertOneGetNewAndReset();
        assertEquals("A", result.get(propOneName));
        assertEquals(10, result.get(propTwoName));
    }

}
