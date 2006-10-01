package net.esper.example.terminal.mdb;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;
import javax.ejb.EJBException;
import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.thoughtworks.xstream.XStream;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;

public class TerminalMDB implements MessageDrivenBean, MessageListener
{
    private QueueSession session;
    private QueueSender sender;
    private XStream encoder_decoder;
    private static EPServiceProvider epService;

    public void setMessageDrivenContext(MessageDrivenContext messageDrivenContext) throws EJBException
    {
    }

    public void ejbCreate() throws EJBException
    {
        epService = EPServiceProviderManager.getDefaultProvider();

        encoder_decoder = new XStream();

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
        String xml = null;
        try
        {
            TextMessage textMessage = (TextMessage) message;
            System.out.println("Message=" + textMessage.getText());
            xml = textMessage.getText();
        }
        catch (JMSException ex)
        {
            String messageText = "Error sending response message";
            log.error(messageText, ex);
            return;
        }

        try
        {
            // TODO: synchronization: get provider, send event, insert statement
            Object event = encoder_decoder.fromXML(xml);
            epService.getEPRuntime().sendEvent(event);
        }
        catch (RuntimeException ex)
        {
            String messageText = "Error processing message, xml=" + xml;
            log.error(messageText, ex);
            return;
        }

        try
        {
            Message response = session.createTextMessage(xml);
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
