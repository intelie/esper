package com.espertech.esper.adapter.jms;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.EventAdapterService;
import com.espertech.esper.event.EventType;
import com.espertech.esper.client.EPException;
import com.espertech.esper.adapter.InputAdapter;

import javax.jms.Message;
import javax.jms.MapMessage;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import java.util.Map;
import java.util.HashMap;
import java.util.Enumeration;
import java.io.Serializable;

/**
 * Created for ESPER.
 */
public class JMSDefaultAnyMessageUnmarshaller implements JMSMessageUnmarshaller
{
    private static final Log log = LogFactory.getLog(JMSDefaultAnyMessageUnmarshaller.class);

    public EventBean unmarshal(EventAdapterService eventAdapterService,
                               Message message) throws EPException
    {
        try
        {
            if (message instanceof ObjectMessage)
            {
                ObjectMessage objmsg = (ObjectMessage) message;
                Serializable obj = objmsg.getObject();
                return eventAdapterService.adapterForBean(obj);
            }
            else if (message instanceof MapMessage)
            {
                Map<String, Object> properties = new HashMap<String, Object>();
                MapMessage mapMsg = (MapMessage) message;
                Enumeration en = mapMsg.getMapNames();
                while (en.hasMoreElements())
                {
                    String property = (String) en.nextElement();
                    Object mapObject = mapMsg.getObject(property);
                    properties.put(property, mapObject);
                }

                // Get event type property
                Object typeProperty = properties.get(InputAdapter.ESPERIO_MAP_EVENT_TYPE);
                if (typeProperty == null)
                {
                    log.warn(".unmarshal Failed to unmarshal map message, expected type property not found: '" + InputAdapter.ESPERIO_MAP_EVENT_TYPE + "'");
                    return null;
                }

                // Get event type
                String alias = typeProperty.toString();
                EventType eventType = eventAdapterService.getExistsTypeByAlias(alias);
                if (eventType == null)
                {
                    log.warn(".unmarshal Failed to unmarshal map message, event type alias '" + alias + "' is not a known type");
                    return null;
                }

                return eventAdapterService.createMapFromValues(properties, eventType);
            }
            else
            {
                String error = ".unmarshal Failed to unmarshal message of JMS type: " + message.getJMSType();
                log.error(error);
                throw new EPException(error);
            }
        }
        catch (JMSException ex)
        {
            throw new EPException("Error unmarshalling message", ex);
        }
    }

}
