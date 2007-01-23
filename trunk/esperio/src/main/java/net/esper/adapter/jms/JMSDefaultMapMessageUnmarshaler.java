package net.esper.adapter.jms;

import net.esper.event.EventType;
import net.esper.schedule.ScheduleSlot;
import net.esper.client.EPException;

import javax.jms.Message;
import javax.jms.MapMessage;
import javax.jms.JMSException;
import java.util.Map;
import java.util.HashMap;
import java.util.Enumeration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created for ESPER.
 */
public class JMSDefaultMapMessageUnmarshaler
{

  private final Log log = LogFactory.getLog(this.getClass());

  public JMSEventBean unmarshal(EventType eventType, Message message,
    long totalDelay, ScheduleSlot scheduleSlot) throws EPException
  {
    JMSEventBean eventBean = null;
    try
    {
      if ((message != null) && (message instanceof MapMessage))
      {
        Map<String, Object> eventTypeMap = new HashMap<String, Object>();
        MapMessage mapMsg = (MapMessage)message;
        mapMsg.getMapNames();
        Enumeration en = mapMsg.getMapNames();
        while (en.hasMoreElements())
        {
          String property = (String)en.nextElement();
          eventTypeMap.put(property, mapMsg.getObject(property));
        }
        eventBean =
          new JMSEventBean(eventTypeMap, eventType, totalDelay, scheduleSlot);
      }
    }
    catch (JMSException ex)
    {
      throw new EPException(ex);
    }
    return eventBean;
  }

}
