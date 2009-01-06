/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.example.servershell;

import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.example.servershell.SampleEvent;

import javax.jms.MessageListener;
import javax.jms.Message;
import javax.jms.BytesMessage;
import javax.jms.JMSException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SampleJMSMessageListener implements MessageListener
{
    private static Log log = LogFactory.getLog(SampleJMSMessageListener.class);
    private EPRuntime engine;
    private int count;

    public SampleJMSMessageListener(EPRuntime engine)
    {
        this.engine = engine;
    }

    public void onMessage(Message message)
    {
        BytesMessage bytesMsg = (BytesMessage) message;
        String body = getBody(bytesMsg);

        String[] payload = body.split(",");
        String ipAddress = payload[0];
        double duration = Double.parseDouble(payload[1]);

        SampleEvent event = new SampleEvent(ipAddress, duration);

        engine.sendEvent(event);
        count++;
    }

    public int getCount()
    {
        return count;
    }

    private String getBody(BytesMessage bytesMsg)
    {
        try
        {
            long length = bytesMsg.getBodyLength();
            byte[] buf = new byte[(int)length];
            bytesMsg.readBytes(buf);
            return new String(buf);
        }
        catch (JMSException e)
        {
            String text = "Error getting message body";
            log.error(text, e);
            throw new RuntimeException(text, e);
        }
    }
}
