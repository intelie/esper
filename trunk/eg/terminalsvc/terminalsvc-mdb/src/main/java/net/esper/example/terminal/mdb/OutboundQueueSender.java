package net.esper.example.terminal.mdb;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.jms.*;
import javax.ejb.EJBException;

public class OutboundQueueSender
{
    private QueueSession session;
    private QueueSender sender;

    public OutboundQueueSender() throws EJBException
    {
        try
        {
            // Connect to outbound queue
            InitialContext iniCtx = new InitialContext();
            Object tmp = iniCtx.lookup("ConnectionFactory");
            QueueConnectionFactory qcf = (QueueConnectionFactory) tmp;
            QueueConnection conn = qcf.createQueueConnection();
            Queue queA = (Queue) iniCtx.lookup("queue/A");
            session = conn.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
            conn.start();
            sender = session.createSender(queA);
        }
        catch (NamingException ex)
        {
            String message = "Error looking up outbound queue";
            System.out.println(message + ": " + ex);
            throw new EJBException(message, ex);
        }
        catch (JMSException ex)
        {
            String message = "Error connecting to outbound queue";
            System.out.println(message + ": " + ex);
            throw new EJBException(message, ex);
        }
    }

    public void send(String text)
    {
        try
        {
            Message response = session.createTextMessage(text);
            sender.send(response);
        }
        catch (JMSException ex)
        {
            String messageText = "Error sending response message";
            System.out.println(messageText + ": " + ex);
        }
    }

    public void destroy()
    {
        try
        {
            sender.close();
            session.close();
        }
        catch (JMSException ex)
        {
            String messageText = "Error closing outbound sender";
            System.out.println(messageText + ": " + ex);
        }
    }
}
