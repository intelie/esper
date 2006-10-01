package net.esper.example.terminal.recvr;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.jms.*;

public class TerminalServiceReceiver
{
    private static final String LISTEN_QUEUE = "queue/A";

    private static Object lock = new Object();

    private QueueConnection conn;
    private QueueSession session;
    private Queue queA;
    private QueueReceiver receiver;


    public void setupPTP()
        throws JMSException, NamingException
    {
        System.setProperty("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");
        System.setProperty("java.naming.provider.url", "localhost:1099");

        InitialContext iniCtx = new InitialContext();
        Object tmp = iniCtx.lookup("ConnectionFactory");
        QueueConnectionFactory qcf = (QueueConnectionFactory) tmp;
        conn = qcf.createQueueConnection();
        queA = (Queue) iniCtx.lookup(LISTEN_QUEUE);
        session = conn.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
        conn.start();
        receiver = session.createReceiver(queA);
        receiver.setMessageListener(new TerminalServiceListener());
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
        System.out.println("TerminalServiceReceiver listening to queue " + LISTEN_QUEUE + "...");

        TerminalServiceReceiver client = new TerminalServiceReceiver();
        client.setupPTP();

        // wait for shutdown
        Runtime.getRuntime().addShutdownHook(new ShutdownThread());
        synchronized (lock) {
            lock.wait();
        }

        client.stop();
        System.out.println("TerminalServiceReceiver ended");
        System.exit(0);
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
            System.out.println("Shutting down TerminalServiceReceiver");

            synchronized (lock) {
                lock.notifyAll();
            }
        }
    }
}