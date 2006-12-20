package net.esper.example.terminal.sender;

import net.esper.example.terminal.common.BaseTerminalEvent;

import javax.naming.NamingException;
import javax.jms.*;
import java.util.List;

public class TerminalEventSender
{
    private static volatile boolean isShutdownRequested;
    private final InboundQueueSender sender;
    private final EventGenerator eventGenerator;

    public TerminalEventSender(String providerURL) throws JMSException, NamingException
    {
        sender = new InboundQueueSender(providerURL);
        eventGenerator = new EventGenerator();
    }

    public void destroy() throws JMSException
    {
        sender.destroy();
    }

    public void sendEvents() throws JMSException, NamingException, InterruptedException
    {
        List<BaseTerminalEvent> eventsToSend = eventGenerator.generateBatch();

        for (BaseTerminalEvent event : eventsToSend)
        {
            sender.sendEvent(event);
        }

        // Throttle the sender to roughly send a batch every 1 second
        System.out.println("Sleeping 1 second");
        Thread.sleep(1000);
    }

    public static void main(String args[])
        throws Exception
    {
        String providerURL = "localhost:1099";
        if (args.length > 0)
        {
            providerURL = args[0];
        }

        System.out.println("TerminalServiceReceiver attaching to provider url " +
                providerURL + " and queue " + InboundQueueSender.SEND_QUEUE + "...");

        TerminalEventSender client = new TerminalEventSender(providerURL);

        Runtime.getRuntime().addShutdownHook(new ShutdownThread());
        while (!isShutdownRequested)
        {
            client.sendEvents();
        }

        client.destroy();
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
