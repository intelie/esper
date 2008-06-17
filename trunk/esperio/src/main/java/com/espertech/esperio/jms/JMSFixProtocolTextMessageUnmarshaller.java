package com.espertech.esperio.jms;

import com.espertech.esper.client.EPException;
import com.espertech.esper.event.EventAdapterService;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.EventType;
import com.espertech.esperio.message.fix.FixMsgParser;
import com.espertech.esperio.message.fix.FixMsgParserException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.util.Map;

/**
 * Unmarshaller for FIX protocol messages.
 * <p>
 * Performs very basic validation of the FIX message, see @{FixMsgParser}.
 * <p>
 * Prerequiste is a Map-based event type by the name "FIX" registered with the engine.
 */
public class JMSFixProtocolTextMessageUnmarshaller implements JMSMessageUnmarshaller
{
    private static final Log log = LogFactory.getLog(JMSFixProtocolTextMessageUnmarshaller.class);

    private EventType eventType;

    public EventBean unmarshal(EventAdapterService eventAdapterService,
                               Message message) throws EPException
    {
        if (!(message instanceof TextMessage))
        {
            throw new EPException("Expected TextMessage JMS message but received " + message.getClass().getName());
        }

        String fixMessage;
        try
        {
            TextMessage textMsg = (TextMessage) message;
            fixMessage  = textMsg.getText();
        }
        catch (JMSException ex)
        {
            throw new EPException("Error unmarshalling message", ex);
        }

        // Get event type
        if (eventType == null)
        {
            String alias = "FIX";
            eventType = eventAdapterService.getExistsTypeByAlias("FIX");
            if (eventType == null)
            {
                log.warn(".unmarshal Failed to unmarshal map message, event type alias '" + alias + "' is not a known type");
                return null;
            }
        }

        Map fixMsg;
        try
        {
            fixMsg = FixMsgParser.parse(fixMessage);
        }
        catch (FixMsgParserException ex)
        {
            throw new EPException("Error unmarshalling message :" + ex.getMessage(), ex);
        }

        return eventAdapterService.createMapFromValues(fixMsg, eventType);       
    }
}
