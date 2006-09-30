package net.esper.example.terminal.mdb;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;
import javax.ejb.EJBException;
import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TerminalMDB implements MessageDrivenBean, MessageListener
{
    private QueueConnection conn;
    private QueueSession session;
    private Queue queA;
    private QueueSender sender;

    public void setMessageDrivenContext(MessageDrivenContext messageDrivenContext) throws EJBException
    {
    }

    public void ejbCreate() throws EJBException
    {
        try
        {
            InitialContext iniCtx = new InitialContext();
            Object tmp = iniCtx.lookup("ConnectionFactory");
            QueueConnectionFactory qcf = (QueueConnectionFactory) tmp;
            conn = qcf.createQueueConnection();
            queA = (Queue) iniCtx.lookup("queue/A");
            session = conn.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
            conn.start();
            sender = session.createSender(queA);
        }
        catch (NamingException ex)
        {
            String message = "Error looking up outbound queue";
            log.error(message, ex);
            throw new EJBException(message, ex);
        }
        catch (JMSException ex)
        {
            String message = "Error connecting to outbound queue";
            log.error(message, ex);
            throw new EJBException(message, ex);
        }
    }

    public void ejbRemove() throws EJBException
    {
    }

    public void onMessage(Message message)
    {
        try
        {
            TextMessage textMessage = (TextMessage) message;
            System.out.println("Message=" + textMessage.getText());

            Message response = session.createTextMessage("Hello" + textMessage.getText());
            sender.send(response);
        }
        catch (JMSException ex)
        {
            String messageText = "Error sending response message";
            log.error(messageText, ex);
        }
    }

    private static Log log = LogFactory.getLog(TerminalMDB.class);
}
