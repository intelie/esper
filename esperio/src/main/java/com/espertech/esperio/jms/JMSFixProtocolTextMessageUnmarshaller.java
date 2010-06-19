/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esperio.jms;

import com.espertech.esper.client.EPException;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;
import com.espertech.esper.event.EventAdapterService;
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
            String name = "FIX";
            eventType = eventAdapterService.getExistsTypeByName("FIX");
            if (eventType == null)
            {
                log.warn(".unmarshal Failed to unmarshal map message, event type name '" + name + "' is not a known type");
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

        return eventAdapterService.adaptorForTypedMap(fixMsg, eventType);
    }
}
