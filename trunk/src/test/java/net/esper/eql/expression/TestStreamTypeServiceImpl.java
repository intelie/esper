package net.esper.eql.expression;

import junit.framework.TestCase;
import net.esper.event.EventType;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportBean_A;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.event.SupportEventTypeFactory;

public class TestStreamTypeServiceImpl extends TestCase
{
    private StreamTypeServiceImpl service;

    public void setUp()
    {
        EventType[] eventTypes = new EventType[] {
            SupportEventTypeFactory.createBeanType(SupportBean.class),
            SupportEventTypeFactory.createBeanType(SupportBean.class),
            SupportEventTypeFactory.createBeanType(SupportBean_A.class),
            SupportEventTypeFactory.createBeanType(SupportMarketDataBean.class)
            };

        String[] streamNames = new String[] {"s1", null, "s3", "s4"};

        service = new StreamTypeServiceImpl(eventTypes, streamNames);
    }

    public void testResolveByStreamAndPropName() throws Exception
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

    public void testResolveByPropertyName() throws Exception
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
}
