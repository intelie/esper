package net.esper.example.terminal.sender;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.jms.*;
import java.util.Random;
import net.esper.example.terminal.common.*;
import com.thoughtworks.xstream.XStream;

public class TerminalEventSender
{
    private static final String SEND_QUEUE = "queue/B";

    private static volatile boolean isShutdownRequested;

    private final Random random;
    private final XStream encoder_decoder;

    private final QueueConnection conn;
    private final QueueSession session;
    private final QueueSender sender;

    public TerminalEventSender() throws JMSException, NamingException
    {
        random = new Random();
        encoder_decoder = new XStream();

        System.setProperty("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");
        System.setProperty("java.naming.provider.url", "localhost:1099");

        InitialContext iniCtx = new InitialContext();
        Object tmp = iniCtx.lookup("ConnectionFactory");
        QueueConnectionFactory qcf = (QueueConnectionFactory) tmp;
        conn = qcf.createQueueConnection();
        Queue queB = (Queue) iniCtx.lookup("queue/B");
        session = conn.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
        sender = session.createSender(queB);
        conn.start();
    }

    public void sendEvents()
        throws JMSException, NamingException, InterruptedException
    {
        // Generate up to 100 unique desk ids between 100 and 200
        String[] deskIds = new String[1];
        for (int i = 0; i < deskIds.length; i++)
        {
            deskIds[i] = Long.toString(i + 100);
        }

        // Swap desks, send check-in events
        randomize(deskIds);
        sendCheckinEvents(deskIds);

        // Swap desks, send cancelled or completed events
        randomize(deskIds);
        sendDoneEvents(deskIds);
    }

    private void sendCheckinEvents(String[] deskIds)
    {
        for (int i = 0; i < deskIds.length; i++)
        {
            Checkin checkin = new Checkin(new DeskInfo(deskIds[i], false, ""));
            sendEvent(checkin);
        }
    }

    private void sendDoneEvents(String[] deskIds)
    {
        for (int i = 0; i < deskIds.length; i++)
        {
            BaseDeskEvent doneEvent = null;
            if (random.nextBoolean())
            {
                doneEvent = new Completed(new DeskInfo(deskIds[i], false, ""));
            }
            else
            {
                doneEvent = new Cancelled(new DeskInfo(deskIds[i], false, ""));
            }
            sendEvent(doneEvent);
        }
    }

    public void stop()
        throws JMSException
    {
        sender.close();
        conn.stop();
        session.close();
        conn.close();
    }

    public static void main(String args[])
        throws Exception
    {
        System.out.println("TerminalEventSender sending to queue " + SEND_QUEUE + "...");

        TerminalEventSender client = new TerminalEventSender();

        Runtime.getRuntime().addShutdownHook(new ShutdownThread());
        while (!isShutdownRequested)
        {
            client.sendEvents();
            isShutdownRequested = true;
        }

        client.stop();
        System.exit(0);
        System.out.println("TerminalEventSender ended");
    }

    // Swap 100 values in the array
    private void randomize(String[] values)
    {
        for (int i = 0; i < 100; i++)
        {
            int pos1 = random.nextInt(values.length);
            int pos2 = random.nextInt(values.length);
            String temp = values[pos2];
            values[pos2] = values[pos1];
            values[pos1] = temp;
        }
    }

    private void sendEvent(BaseDeskEvent baseDeskEvent)
    {
        try
        {
            String xml = encoder_decoder.toXML(baseDeskEvent);
            TextMessage textMessage = session.createTextMessage();
            textMessage.setText(xml);
            sender.send(textMessage);

            System.out.println(xml);
        }
        catch (JMSException ex)
        {
            System.out.println("Error sending event:" + ex.toString());
        }
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
