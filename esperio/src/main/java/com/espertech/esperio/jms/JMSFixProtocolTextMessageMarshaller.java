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
import com.espertech.esperio.message.fix.FixMsgMarshaller;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * Marshals the response out of the event bean into a jms map message.
 */
public class JMSFixProtocolTextMessageMarshaller implements JMSMessageMarshaller
{
    private final Log log = LogFactory.getLog(this.getClass());

    public Message marshal(EventBean eventBean, Session session,
                           long timestamp) throws EPException
    {
        TextMessage textMessage;
        try
        {
            textMessage = session.createTextMessage();
            String fixText = FixMsgMarshaller.marshalFix(eventBean);
            textMessage.setText(fixText);
            textMessage.setJMSTimestamp(timestamp);
        }
        catch (JMSException ex)
        {
            throw new EPException(ex);
        }
        return textMessage;
    }
}
