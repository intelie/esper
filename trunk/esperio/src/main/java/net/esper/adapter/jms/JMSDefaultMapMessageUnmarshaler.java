package net.esper.adapter.jms;

import net.esper.adapter.*;
import net.esper.client.*;
import net.esper.event.*;
import org.apache.commons.logging.*;

import javax.jms.*;
import java.util.*;

/**
 * Created for ESPER.
 */
public class JMSDefaultMapMessageUnmarshaler implements JMSMessageUnmarshaler
{

  private final Log log = LogFactory.getLog(this.getClass());

  public EventBean unmarshal(EventAdapterService eventAdapterService,
    Message message) throws EPException
  {
    EventBean eventBean = null;
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
        EventType eventType =
          eventAdapterService.addMapType(
            AdapterUtils.JMS_ADAPTER_DEFAULT_EVENT_TYPE_ALIAS, eventTypeMap);
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
