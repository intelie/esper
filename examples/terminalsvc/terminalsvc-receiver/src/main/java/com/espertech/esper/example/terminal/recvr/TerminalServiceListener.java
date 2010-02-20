/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.example.terminal.recvr;

import javax.jms.MessageListener;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.jms.JMSException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TerminalServiceListener implements MessageListener
{
    public void onMessage(Message message)
    {
        try
        {
            DateFormat dateFormat = SimpleDateFormat.getInstance();
            String date = dateFormat.format(new Date());

            TextMessage textMessage = (TextMessage) message;
            System.out.println(date + " " + textMessage.getText());
        }
        catch (JMSException ex)
        {
            System.out.println("Error reading text message:" + ex);
        }
    }
}
