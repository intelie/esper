package net.esper.adapter.jms;

import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.client.EPException;
import net.esper.util.JavaClassHelper;

import javax.jms.MapMessage;
import javax.jms.Session;
import javax.jms.JMSException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created for ESPER.
 */
public class JMSDefaultMapMessageMarshaler
{

  private final Log log = LogFactory.getLog(this.getClass());

  /**
   * Marshals the response out of the event bean into a jms map message.
  */

  public JMSDefaultMapMessageMarshaler()
  {

  }
  
  public MapMessage marshal(EventBean eventBean, Session session, long timestamp) throws EPException
  {
      EventType eventType = eventBean.getEventType();
      MapMessage message;
      try
      {
          message = session.createMapMessage();
          message.setJMSTimestamp(timestamp);
          String[] properties = eventType.getPropertyNames();
          for(String property : properties)
          {
              log.debug(".Marshal EventProperty property==" + property + ", value=" + eventBean.get(property));
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
