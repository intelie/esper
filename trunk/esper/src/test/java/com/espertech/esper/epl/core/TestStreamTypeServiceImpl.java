package com.espertech.esper.epl.core;

import junit.framework.TestCase;
import com.espertech.esper.event.EventType;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBean_A;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.event.SupportEventTypeFactory;
import com.espertech.esper.collection.Pair;

import java.util.LinkedHashMap;

public class TestStreamTypeServiceImpl extends TestCase
{
    private StreamTypeServiceImpl serviceRegular;
    private StreamTypeServiceImpl serviceStreamZeroUnambigous;
    private StreamTypeServiceImpl serviceRequireStreamName;

    public void setUp()
    {
        // Prepare regualar test service
        EventType[] eventTypes = new EventType[] {
            SupportEventTypeFactory.createBeanType(SupportBean.class),
            SupportEventTypeFactory.createBeanType(SupportBean.class),
            SupportEventTypeFactory.createBeanType(SupportBean_A.class),
            SupportEventTypeFactory.createBeanType(SupportMarketDataBean.class)
            };
        String[] eventTypeAlias = new String[] {"SupportBean", "SupportBean", "SupportBean_A", "SupportMarketDataBean"};
        String[] streamNames = new String[] {"s1", null, "s3", "s4"};
        serviceRegular = new StreamTypeServiceImpl(eventTypes, streamNames, "default", eventTypeAlias);

        // Prepare with stream-zero being unambigous
        LinkedHashMap<String, Pair<EventType, String>> streamTypes = new LinkedHashMap<String, Pair<EventType, String>>();
        for (int i = 0; i < streamNames.length; i++)
        {
            streamTypes.put(streamNames[i], new Pair<EventType, String>(eventTypes[i], eventTypeAlias[i]));
        }
        serviceStreamZeroUnambigous = new StreamTypeServiceImpl(streamTypes, "default", true, false);

        // Prepare requiring stream names for non-zero streams
        serviceRequireStreamName = new StreamTypeServiceImpl(streamTypes, "default", true, true);
    }

    public void testResolveByStreamAndPropNameInOne() throws Exception
    {
        tryResolveByStreamAndPropNameInOne(serviceRegular);
        tryResolveByStreamAndPropNameInOne(serviceStreamZeroUnambigous);
        tryResolveByStreamAndPropNameInOne(serviceRequireStreamName);
    }

    public void testResolveByPropertyName() throws Exception
    {
        tryResolveByPropertyName(serviceRegular);
        serviceStreamZeroUnambigous.resolveByPropertyName("boolPrimitive");
        serviceRequireStreamName.resolveByPropertyName("boolPrimitive");

        try
        {
            serviceRequireStreamName.resolveByPropertyName("volume");
            fail();
        }
        catch (PropertyNotFoundException ex)
        {
            // expected
        }
    }

    public void testResolveByStreamAndPropNameBoth() throws Exception
    {
        tryResolveByStreamAndPropNameBoth(serviceRegular);
        tryResolveByStreamAndPropNameBoth(serviceStreamZeroUnambigous);
        tryResolveByStreamAndPropNameBoth(serviceRequireStreamName);
    }

    private static void tryResolveByStreamAndPropNameBoth(StreamTypeService service) throws Exception
    {
        // Test lookup by stream name and prop name
        PropertyResolutionDescriptor desc = service.resolveByStreamAndPropName("s4", "volume");
        assertEquals(3, (int) desc.getStreamNum());
        assertEquals(Long.class, desc.getPropertyType());
        assertEquals("volume", desc.getPropertyName());
        assertEquals("s4", desc.getStreamName());
        assertEquals(SupportMarketDataBean.class, desc.getStreamEventType().getUnderlyingType());

        try
        {
            service.resolveByStreamAndPropName("xxx", "volume");
            fail();
        }
        catch (StreamNotFoundException ex)
        {
            // Expected
        }

        try
        {
            service.resolveByStreamAndPropName("s4", "xxxx");
            fail();
        }
        catch (PropertyNotFoundException ex)
        {
            // Expected
        }
    }

    private static void tryResolveByPropertyName(StreamTypeService service) throws Exception
    {
        // Test lookup by property name only
        PropertyResolutionDescriptor desc = service.resolveByPropertyName("volume");
        assertEquals(3, (int) (desc.getStreamNum()));
        assertEquals(Long.class, desc.getPropertyType());
        assertEquals("volume", desc.getPropertyName());
        assertEquals("s4", desc.getStreamName());
        assertEquals(SupportMarketDataBean.class, desc.getStreamEventType().getUnderlyingType());

        try
        {
            service.resolveByPropertyName("boolPrimitive");
            fail();
        }
        catch (DuplicatePropertyException ex)
        {
            // Expected
        }

        try
        {
            service.resolveByPropertyName("xxxx");
            fail();
        }
        catch (PropertyNotFoundException ex)
        {
            // Expected
        }
    }

    private static void tryResolveByStreamAndPropNameInOne(StreamTypeService service) throws Exception
    {
        // Test lookup by stream name and prop name
        PropertyResolutionDescriptor desc = service.resolveByStreamAndPropName("s4.volume");
        assertEquals(3, (int) desc.getStreamNum());
        assertEquals(Long.class, desc.getPropertyType());
        assertEquals("volume", desc.getPropertyName());
        assertEquals("s4", desc.getStreamName());
        assertEquals(SupportMarketDataBean.class, desc.getStreamEventType().getUnderlyingType());

        try
        {
            service.resolveByStreamAndPropName("xxx.volume");
            fail();
        }
        catch (PropertyNotFoundException ex)
        {
            // Expected
        }

        try
        {
            service.resolveByStreamAndPropName("s4.xxxx");
            fail();
        }
        catch (PropertyNotFoundException ex)
        {
            // Expected
        }

        // resolve by event type alias (table name)
        desc = service.resolveByStreamAndPropName("SupportMarketDataBean.volume");
        assertEquals(3, (int) desc.getStreamNum());

        // resolve by engine URI plus event type alias
        desc = service.resolveByStreamAndPropName("default.SupportMarketDataBean.volume");
        assertEquals(3, (int) desc.getStreamNum());
    }
}
