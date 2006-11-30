package net.esper.adapter.jms;

import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.client.EPException;
import net.esper.util.JavaClassHelper;

import javax.jms.Message;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.MapMessage;
import java.util.Map;
import java.util.HashMap;
import java.util.Enumeration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by IntelliJ IDEA.
 * User: MYSELF
 * Date: Nov 15, 2006
 * Time: 9:50:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultJMSMarshaler implements JMSMarshaler
{

    private static final Log log = LogFactory.getLog(DefaultJMSMarshaler.class);

    /**
     * Marshals the jms message payload into the event bean, typically as the content
     * property.
     */

    public EventBean marshal(EventType eventType, Message message) throws EPException
    {
        JMSEventBean eventBean = null;
        try
        {
            if (message instanceof MapMessage)
            {
                Map<String, Object> eventTypeMap = new HashMap<String, Object>();
                MapMessage mapMsg = (MapMessage) message;
                mapMsg.getMapNames();
                Enumeration en = mapMsg.getMapNames();
                while (en.hasMoreElements())
                {
                    String property = (String) en.nextElement();
                    eventTypeMap.put(property, mapMsg.getObject(property));
                }
                eventBean = new JMSEventBean(eventTypeMap, eventType);
            }
        }
        catch (JMSException ex)
        {
            throw new EPException(ex);
        }
        return eventBean;
    }

    /**
     * Unmarshals the response out of the event bean.
    */

    public MapMessage unmarshal(EventBean eventBean, Session session) throws EPException
    {
        EventType eventType = eventBean.getEventType();
        MapMessage message;
        try
        {
            message = session.createMapMessage();
            String[] properties = eventType.getPropertyNames();
            for(String property : properties)
            {
                log.debug(".Unmarshal EventProperty property==" + property + ", value=" + eventBean.get(property));
                Class clazz = eventType.getPropertyType(property);
                if (JavaClassHelper.isNumeric(clazz))
                {
                    Class boxedClazz = JavaClassHelper.getBoxedType(clazz);
                    if (boxedClazz == Double.class)
                    {
                        message.setDouble(property, (Double) eventBean.get(property));
                    }
                    if (boxedClazz == Float.class)
                    {
                        message.setFloat(property, (Float) eventBean.get(property));
                    }
                    if (boxedClazz == Byte.class)
                    {
                        message.setFloat(property, (Byte) eventBean.get(property));
                    }
                    if (boxedClazz == Short.class)
                    {
                        message.setShort(property, (Short) eventBean.get(property));
                    }
                    if (boxedClazz == Integer.class)
                    {
                        message.setInt(property, (Integer) eventBean.get(property));
                    }
                    if (boxedClazz == Long.class)
                    {
                        message.setLong(property, (Long) eventBean.get(property));
                    }
                }
                else if ((clazz == boolean.class) || (clazz == Boolean.class))
                {
                        message.setBoolean(property, (Boolean) eventBean.get(property));
                }
                else if ((clazz == Character.class) || (clazz == char.class))
                {
                        message.setChar(property, (Character) eventBean.get(property));
                }
                else if (clazz == String.class)
                {
                        message.setString(property, (String) eventBean.get(property));
                }
                else
                {
                        message.setObject(property, eventBean.get(property));
                }
            }
        }
        catch (JMSException ex)
        {
            throw new EPException(ex);
        }
        return message;
    }

}
