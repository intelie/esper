/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esperio.regression.adapter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.*;
import java.util.HashMap;
import java.util.Enumeration;

public class SupportJMSReceiver
{
    private static final Log log = LogFactory.getLog(SupportJMSReceiver.class);

    private AbstractXmlApplicationContext springContext;
    private JmsTemplate jmsTemplate;

    public static void main(String[] args) throws JMSException
    {
        SupportJMSReceiver receiver = new SupportJMSReceiver();
        while(true)
        {
            Message msg = receiver.receiveMessage();
            if (msg == null)
            {
                log.info(".main received no object");
                continue;
            }
            print(msg);
        }
    }

    public SupportJMSReceiver()
    {
        springContext = new ClassPathXmlApplicationContext("regression/jms_activemq_spring.xml");
        jmsTemplate = (JmsTemplate) springContext.getBean("jmsTemplate");
    }

    public Message receiveMessage() throws JMSException
    {
        Message message = jmsTemplate.receive();
        if (message != null)
        {
            message.acknowledge();
        }
        return message;
    }

    public static void print(Message msg) throws JMSException
    {
        log.info(".print received message: " + msg.getJMSMessageID());
        if (msg instanceof ObjectMessage)
        {
            ObjectMessage objMsg = (ObjectMessage) msg;
            log.info(".print object: " + objMsg.getObject().toString());
        }
        else
        {
            MapMessage mapMsg = (MapMessage) msg;
            HashMap map = new HashMap();
            Enumeration en = mapMsg.getMapNames();
            while (en.hasMoreElements())
            {
                String property = (String) en.nextElement();
                Object mapObject = mapMsg.getObject(property);
                map.put(property, mapObject);
            }
            log.info(".print map: " + map);
        }
    }

    public void destroy()
    {
        springContext.destroy();
    }
}
