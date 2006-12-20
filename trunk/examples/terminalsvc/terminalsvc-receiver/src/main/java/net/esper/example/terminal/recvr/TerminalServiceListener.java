package net.esper.example.terminal.recvr;

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
