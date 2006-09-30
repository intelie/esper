package net.esper.example.terminal.sender;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.jms.*;
import java.util.Random;
import java.util.Set;
import java.util.HashSet;

public class TerminalEventSender
{
    private static final String SEND_QUEUE = "queue/B";

    private static volatile boolean isShutdownRequested;

    private QueueConnection conn;
    private QueueSession session;
    private Queue queB;

    private Random random = new Random();

    public void setupPTP()
        throws JMSException, NamingException
    {
        System.setProperty("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");
        System.setProperty("java.naming.provider.url", "localhost:1099");

        InitialContext iniCtx = new InitialContext();
        Object tmp = iniCtx.lookup("ConnectionFactory");
        QueueConnectionFactory qcf = (QueueConnectionFactory) tmp;
        conn = qcf.createQueueConnection();
        queB = (Queue) iniCtx.lookup("queue/B");
        session = conn.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
        conn.start();
    }

    public void sendEvents()
        throws JMSException, NamingException, InterruptedException
    {
        // Generate up to 100 unique desk ids between 1000 and 2000
        long[] deskIds = new long[100];
        for (int i = 0; i < deskIds.length; i++)
        {
            deskIds.add(1000L + random.nextInt(1000));
        }

        // Send a check-in started event for each
        for (Long deskId = deskIds)

        // long deskId;
        // string eventType;

        System.out.println("Begin sendRecvAsync");

        setupPTP();

        QueueSender send = session.createSender(queB);

        for(int m = 0; m < 10; m ++) {
            TextMessage tm = session.createTextMessage(textBase+"#"+m);
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
        System.out.println("TerminalEventSender sending to queue " + SEND_QUEUE + "...");

        TerminalEventSender client = new TerminalEventSender();
        client.setupPTP();

        Runtime.getRuntime().addShutdownHook(new ShutdownThread());
        while (!isShutdownRequested)
        {
            client.sendEvents();
        }

        client.stop();
        System.exit(0);
        System.out.println("TerminalEventSender ended");
    }

    /**
     * Inner class for registration as a shutdown hook. Implements Thread and
     * coordinates with Main thread via _lock.
     */
    static class ShutdownThread extends Thread {

        /**
         * Called by the JVM on initiation of a process shutdown. Registration
         * occurs in main thread.
         */
        public void run() {
            System.out.println("Shutting down TerminalEventSender");
            isShutdownRequested = true;
        }
    }
}
