package net.esper.example.terminal.mdb;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;
import javax.ejb.EJBException;
import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;

public class TerminalMDB implements MessageDrivenBean, MessageListener
{
    private QueueSession session;
    private QueueSender sender;
    private static EPServiceProvider epService;

    public void setMessageDrivenContext(MessageDrivenContext messageDrivenContext) throws EJBException
    {
    }

    public void ejbCreate() throws EJBException
    {
        epService = EPServiceProviderManager.getDefaultProvider();

        try
        {
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
        Object event = null;
        try
        {
            ObjectMessage objMessage = (ObjectMessage) message;
            System.out.println("onMessage received messageId=" + objMessage.getJMSMessageID());
            event = objMessage.getObject();
        }
        catch (JMSException ex)
        {
            String messageText = "Error sending response message";
            log.error(messageText, ex);
            return;
        }

        try
        {
            epService.getEPRuntime().sendEvent(event);
        }
        catch (RuntimeException ex)
        {
            String messageText = "Error processing event, event=" + event;
            log.error(messageText, ex);
            return;
        }

        try
        {
            Message response = session.createTextMessage("hello");
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
