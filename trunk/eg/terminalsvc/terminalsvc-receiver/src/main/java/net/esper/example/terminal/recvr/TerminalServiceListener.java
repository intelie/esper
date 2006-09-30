package net.esper.example.terminal.recvr;

import javax.jms.MessageListener;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.jms.JMSException;

public class TerminalServiceListener implements MessageListener
{
    public void onMessage(Message message)
    {
        try
        {
            TextMessage textMessage = (TextMessage) message;
            System.out.println(textMessage.getText());
        }
        catch (JMSException ex)
        {
            System.out.println("Error reading text message:" + ex);
        }
    }
}
