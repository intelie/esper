package net.esper.adapter.jms;

import net.esper.event.*;
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

  public EventBean unmarshal(EventAdapterService eventAdapterService, Message message,
    long totalDelay, ScheduleSlot scheduleSlot) throws EPException
  {
    JMSEventBean eventBean = null;
    try
    {
      if ((message != null) && (message instanceof MapMessage))
      {
        Map<String, Class> eventTypeMap = new HashMap<String, Class>();        
        Map<String, Object> properties = new HashMap<String, Object>();
        MapMessage mapMsg = (MapMessage)message;
        mapMsg.getMapNames();
        Enumeration en = mapMsg.getMapNames();
        while (en.hasMoreElements())
        {
          String property = (String)en.nextElement();
          Object mapObject = mapMsg.getObject(property);
          eventTypeMap.put(property, mapObject.getClass());
          properties.put(property, mapObject);
        }
        EventType eventType = eventAdapterService.addMapType("SpringAdapterType", eventTypeMap);
        return eventAdapterService.createMapFromValues(properties, eventType);
      }
    }
    catch (JMSException ex)
    {
      throw new EPException(ex);
    }
    return eventBean;
  }

}
