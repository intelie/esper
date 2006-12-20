package net.esper.indicator.jmx;

import net.esper.event.EventType;
import net.esper.event.EventBean;

import javax.management.*;

/**
 * JMX DynamicMBean that exposes all properties of an element as attributes for querying.
 */
public final class JMXLastElementDynamicMBean implements JMXLastElementObserver
{
    private final MBeanInfo mBeanInfo;
    private EventBean lastValue;

    /**
     * Constructor.
     * @param eventType is the schema describing the elements to expect.
     */
    public JMXLastElementDynamicMBean(EventType eventType)
    {
        mBeanInfo = determineMBeanInfo(eventType);
    }

    public final MBeanInfo getMBeanInfo()
    {
        return mBeanInfo;
    }

    public final void setLastValue(EventBean lastValue)
    {
        this.lastValue = lastValue;
    }

    public final Object getAttribute(String attribute) throws AttributeNotFoundException, MBeanException, ReflectionException
    {
        if (lastValue == null)
        {
            return null;
        }

        return lastValue.get(attribute);
    }

    public final void setAttribute(Attribute attribute) throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException
    {
        throw new AttributeNotFoundException("Attribute " + attribute + " is not writable");
    }

    public final AttributeList getAttributes(String[] attributes)
    {
        AttributeList list = new AttributeList();

        for (String key : attributes)
        {
            list.add(new Attribute(key, lastValue.get(key)));
        }

        return list;
    }

    public final AttributeList setAttributes(AttributeList attributes)
    {
        return null;
    }

    public final Object invoke(String actionName, Object params[], String signature[]) throws MBeanException, ReflectionException
    {
        return null;
    }

    private static MBeanInfo determineMBeanInfo(EventType eventType)
    {
        String[] keys = eventType.getPropertyNames();
        MBeanAttributeInfo infos[] = new MBeanAttributeInfo[keys.length];

        for (int i = 0; i < infos.length; i++)
        {
            String type = eventType.getPropertyType(keys[i]).getName();
            infos[i] = new MBeanAttributeInfo(keys[i], type, "", true, false, false);
        }

        return new MBeanInfo(JMXLastElementDynamicMBean.class.getName(),
                "",
                infos,
                null,
                null,
                null);
    }
}
