package net.esper.support.eql;

import net.esper.eql.core.*;
import net.esper.event.EventType;
import net.esper.event.EventBean;
import net.esper.support.bean.SupportBean;
import net.esper.support.event.SupportEventTypeFactory;
import net.esper.support.event.SupportEventBeanFactory;

public class SupportStreamTypeSvc1Stream implements StreamTypeService
{
    private StreamTypeService impl;

    public SupportStreamTypeSvc1Stream()
    {
        impl = new StreamTypeServiceImpl(getEventTypes(), getStreamNames());
    }

    public PropertyResolutionDescriptor resolveByPropertyName(String propertyName) throws DuplicatePropertyException, PropertyNotFoundException
    {
        return impl.resolveByPropertyName(propertyName);
    }

    public PropertyResolutionDescriptor resolveByStreamAndPropName(String streamName, String propertyName) throws PropertyNotFoundException, StreamNotFoundException
    {
        return impl.resolveByStreamAndPropName(streamName, propertyName);
    }

    public String[] getStreamNames()
    {
        return new String[] {"s0"};
    }

    public EventType[] getEventTypes()
    {
        EventType[] eventTypes = new EventType[] {
            SupportEventTypeFactory.createBeanType(SupportBean.class)
        };
        return eventTypes;
    }

    public static EventBean[] getSampleEvents()
    {
        return new EventBean[] {SupportEventBeanFactory.createObject(new SupportBean())
                            };
    }
}
