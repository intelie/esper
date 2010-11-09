package com.espertech.esper.regression.event;

import com.espertech.esper.client.*;
import com.espertech.esper.support.bean.*;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.event.EventTypeSPI;
import com.espertech.esper.event.EventTypeMetadata;
import com.espertech.esper.client.EventType;
import com.espertech.esper.core.EPServiceProviderSPI;
import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestVariantStreamAny extends TestCase
{
    private static final Log log = LogFactory.getLog(TestVariantStreamAny.class);
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        ConfigurationVariantStream variant = new ConfigurationVariantStream();
        variant.setTypeVariance(ConfigurationVariantStream.TypeVariance.ANY);
        config.addVariantStream("MyVariantStream", variant);
        assertTrue(config.isVariantStreamExists("MyVariantStream"));

        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();

        // assert type metadata
        EventTypeSPI type = (EventTypeSPI) ((EPServiceProviderSPI)epService).getValueAddEventService().getValueAddProcessor("MyVariantStream").getValueAddEventType();
        assertEquals(null, type.getMetadata().getOptionalApplicationType());
        assertEquals(null, type.getMetadata().getOptionalSecondaryNames());
        assertEquals("MyVariantStream", type.getMetadata().getPrimaryName());
        assertEquals("MyVariantStream", type.getMetadata().getPublicName());
        assertEquals("MyVariantStream", type.getName());
        assertEquals(EventTypeMetadata.TypeClass.VARIANT, type.getMetadata().getTypeClass());
        assertEquals(true, type.getMetadata().isApplicationConfigured());
        assertEquals(true, type.getMetadata().isApplicationPreConfigured());
        assertEquals(true, type.getMetadata().isApplicationPreConfiguredStatic());

        EventType[] valueAddTypes = ((EPServiceProviderSPI)epService).getValueAddEventService().getValueAddedTypes();
        assertEquals(1, valueAddTypes.length);
        assertSame(type, valueAddTypes[0]);

        assertEquals(0, type.getPropertyNames().length);
        assertEquals(0, type.getPropertyDescriptors().length);
    }

    public void testAnyType()
    {
        assertTrue(epService.getEPAdministrator().getConfiguration().isVariantStreamExists("MyVariantStream"));
        epService.getEPAdministrator().createEPL("insert into MyVariantStream select * from " + SupportBean.class.getName());
        epService.getEPAdministrator().createEPL("insert into MyVariantStream select * from " + SupportBeanVariantStream.class.getName());
        epService.getEPAdministrator().createEPL("insert into MyVariantStream select * from " + SupportBean_A.class.getName());
        epService.getEPAdministrator().createEPL("insert into MyVariantStream select symbol as string, volume as intPrimitive, feed as id from " + SupportMarketDataBean.class.getName());

        EPStatement stmt = epService.getEPAdministrator().createEPL("select * from MyVariantStream");
        stmt.addListener(listener);
        assertEquals(0, stmt.getEventType().getPropertyNames().length);

        Object eventOne = new SupportBean("E0", -1);
        epService.getEPRuntime().sendEvent(eventOne);
        assertSame(eventOne, listener.assertOneGetNewAndReset().getUnderlying());

        Object eventTwo = new SupportBean_A("E1");
        epService.getEPRuntime().sendEvent(eventTwo);
        assertSame(eventTwo, listener.assertOneGetNewAndReset().getUnderlying());

        stmt.destroy();
        stmt = epService.getEPAdministrator().createEPL("select string,id,intPrimitive from MyVariantStream");
        stmt.addListener(listener);
        assertEquals(Object.class, stmt.getEventType().getPropertyType("string"));
        assertEquals(Object.class, stmt.getEventType().getPropertyType("id"));
        assertEquals(Object.class, stmt.getEventType().getPropertyType("intPrimitive"));

        String[] fields = "string,id,intPrimitive".split(",");
        epService.getEPRuntime().sendEvent(new SupportBeanVariantStream("E1"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E1", null, null});

        epService.getEPRuntime().sendEvent(new SupportBean("E2", 10));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E2", null, 10});

        epService.getEPRuntime().sendEvent(new SupportBean_A("E3"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {null, "E3", null});

        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("s1", 100, 1000L, "f1"));
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"s1", "f1", 1000L});
    }

    public void testAnyTypeStaggered()
    {
        // test insert into staggered with map
        ConfigurationVariantStream configVariantStream = new ConfigurationVariantStream();
        configVariantStream.setTypeVariance(ConfigurationVariantStream.TypeVariance.ANY);
        epService.getEPAdministrator().getConfiguration().addVariantStream("VarStream", configVariantStream);
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);
        epService.getEPAdministrator().getConfiguration().addEventType("SupportMarketDataBean", SupportMarketDataBean.class);

        epService.getEPAdministrator().createEPL("insert into MyStream select string, intPrimitive from SupportBean");
        epService.getEPAdministrator().createEPL("insert into VarStream select string as abc from MyStream");
        epService.getEPAdministrator().createEPL("@Name('Target') select * from VarStream.win:keepall()");

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));

        EventBean[] arr = ArrayAssertionUtil.iteratorToArray(epService.getEPAdministrator().getStatement("Target").iterator());
        ArrayAssertionUtil.assertPropsPerRow(arr, new String[] {"abc"}, new Object[][] {{"E1"}});

        epService.getEPAdministrator().createEPL("insert into MyStream2 select feed from SupportMarketDataBean");
        epService.getEPAdministrator().createEPL("insert into VarStream select feed as abc from MyStream2");

        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("IBM", 1, 1L, "E2"));

        arr = ArrayAssertionUtil.iteratorToArray(epService.getEPAdministrator().getStatement("Target").iterator());
        ArrayAssertionUtil.assertPropsPerRow(arr, new String[] {"abc"}, new Object[][] {{"E1"}, {"E2"}});
    }
}
