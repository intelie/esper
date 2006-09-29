package net.esper.example.terminal.mdb;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.jms.*;

public class TerminalAlertClient
{
    QueueConnection conn;
    QueueSession session;
    Queue queA;
    Queue queB;

    public void setupPTP()
        throws JMSException, NamingException
    {
        System.setProperty("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");
        System.setProperty("java.naming.provider.url", "localhost:1099");
        
        InitialContext iniCtx = new InitialContext();
        Object tmp = iniCtx.lookup("ConnectionFactory");
        QueueConnectionFactory qcf = (QueueConnectionFactory) tmp;
        conn = qcf.createQueueConnection();
        queA = (Queue) iniCtx.lookup("queue/A");
        queB = (Queue) iniCtx.lookup("queue/B");
        session = conn.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
        conn.start();
    }

    public void sendMessage(String textBase)
        throws JMSException, NamingException, InterruptedException
    {
        System.out.println("Begin sendRecvAsync");

        // Setup the PTP connection, session
        setupPTP();

        // Send a few text msgs to queB
        QueueSender send = session.createSender(queB);

        for(int m = 0; m < 10; m ++) {
            TextMessage tm = session.createTextMessage(textBase+"#"+m);
            tm.setJMSReplyTo(queA);
            send.send(tm);
            System.out.println("sendRecvAsync, sent text=" + tm.getText());
        }
        System.out.println("End sendRecvAsync");
    }

    public void stop()
        throws JMSException
    {
        conn.stop();
        session.close();
        conn.close();
    }

    public static void main(String args[])
        throws Exception
    {
        System.out.println("Begin SendRecvClient,now=" +
                           System.currentTimeMillis());
        TerminalAlertClient client = new TerminalAlertClient();
        client.sendMessage("A text msg");
        client.stop();
        System.exit(0);
        System.out.println("End SendRecvClient");
    }
}
