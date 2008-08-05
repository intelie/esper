package com.espertech.esper.support.epl;

import com.espertech.esper.epl.core.*;
import com.espertech.esper.event.EventType;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBeanComplexProps;
import com.espertech.esper.support.event.SupportEventBeanFactory;
import com.espertech.esper.support.event.SupportEventTypeFactory;

public class SupportStreamTypeSvc3Stream implements StreamTypeService
{
    private StreamTypeService impl;

    public SupportStreamTypeSvc3Stream()
    {
        impl = new StreamTypeServiceImpl(getEventTypes(), getStreamNames(), "default", getEventTypeAliases());
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
        return new String[] {"s0", "s1", "s2"};
    }

    public EventType[] getEventTypes()
    {
        EventType[] eventTypes = new EventType[] {
            SupportEventTypeFactory.createBeanType(SupportBean.class),
            SupportEventTypeFactory.createBeanType(SupportBean.class),
            SupportEventTypeFactory.createBeanType(SupportBeanComplexProps.class)
        };
        return eventTypes;
    }

    public String[] getEventTypeAliases()
    {
        return new String[] {"SupportBean", "SupportBean", "SupportBeanComplexProps"};
    }

    public static EventBean[] getSampleEvents()
    {
        return new EventBean[] {SupportEventBeanFactory.createObject(new SupportBean()),
                                SupportEventBeanFactory.createObject(new SupportBean()),
                                SupportEventBeanFactory.createObject(SupportBeanComplexProps.makeDefaultBean()),
                            };
    }
}