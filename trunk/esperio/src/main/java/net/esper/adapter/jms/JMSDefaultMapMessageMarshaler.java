package net.esper.adapter.jms;

import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.client.EPException;
import net.esper.util.JavaClassHelper;

import javax.jms.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;

/**
 * Marshals the response out of the event bean into a jms map message.
 */
public class JMSDefaultMapMessageMarshaler implements JMSMessageMarshaler
{
    private final Log log = LogFactory.getLog(this.getClass());

    public Message marshal(EventBean eventBean, Session session,
                           long timestamp) throws EPException
    {
        EventType eventType = eventBean.getEventType();
        MapMessage mapMessage = null;
        try
        {
            mapMessage = session.createMapMessage();
            String[] properties = eventType.getPropertyNames();
            for (String property : properties)
            {
                log.debug(
                        ".Marshal EventProperty property==" + property + ", value=" +
                                eventBean.get(property));
                Class clazz = eventType.getPropertyType(property);
                if (JavaClassHelper.isNumeric(clazz))
                {
                    Class boxedClazz = JavaClassHelper.getBoxedType(clazz);
                    if (boxedClazz == Double.class)
                    {
                        mapMessage.setDouble(property, (Double) eventBean.get(property));
                    }
                    if (boxedClazz == Float.class)
                    {
                        mapMessage.setFloat(property, (Float) eventBean.get(property));
                    }
                    if (boxedClazz == Byte.class)
                    {
                        mapMessage.setFloat(property, (Byte) eventBean.get(property));
                    }
                    if (boxedClazz == Short.class)
                    {
                        mapMessage.setShort(property, (Short) eventBean.get(property));
                    }
                    if (boxedClazz == Integer.class)
                    {
                        mapMessage.setInt(property, (Integer) eventBean.get(property));
                    }
                    if (boxedClazz == Long.class)
                    {
                        mapMessage.setLong(property, (Long) eventBean.get(property));
                    }
                }
                else if ((clazz == boolean.class) || (clazz == Boolean.class))
                {
                    mapMessage.setBoolean(property, (Boolean) eventBean.get(property));
                }
                else if ((clazz == Character.class) || (clazz == char.class))
                {
                    mapMessage.setChar(property, (Character) eventBean.get(property));
                }
                else if (clazz == String.class)
                {
                    mapMessage.setString(property, (String) eventBean.get(property));
                }
                else
                {
                    mapMessage.setObject(property, eventBean.get(property));
                }
            }
            mapMessage.setJMSTimestamp(timestamp);
        }
        catch (JMSException ex)
        {
            throw new EPException(ex);
        }
        return mapMessage;
    }
}
