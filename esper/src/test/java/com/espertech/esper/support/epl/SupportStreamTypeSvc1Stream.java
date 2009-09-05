package com.espertech.esper.support.epl;

import com.espertech.esper.epl.core.*;
import com.espertech.esper.client.EventType;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.event.SupportEventTypeFactory;
import com.espertech.esper.support.event.SupportEventBeanFactory;

public class SupportStreamTypeSvc1Stream implements StreamTypeService
{
    private StreamTypeService impl;

    public SupportStreamTypeSvc1Stream()
    {
        impl = new StreamTypeServiceImpl(getEventTypes(), getStreamNames(), "default");
    }

    public PropertyResolutionDescriptor resolveByPropertyName(String propertyName) throws DuplicatePropertyException, PropertyNotFoundException
    {
        return impl.resolveByPropertyName(propertyName);
    }

    public PropertyResolutionDescriptor resolveByStreamAndPropName(String streamName, String propertyName) throws PropertyNotFoundException, StreamNotFoundException
    {
        return impl.resolveByStreamAndPropName(streamName, propertyName);
    }

    public PropertyResolutionDescriptor resolveByStreamAndPropName(String streamAndPropertyName) throws DuplicatePropertyException, PropertyNotFoundException
    {
        return impl.resolveByStreamAndPropName(streamAndPropertyName);
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
