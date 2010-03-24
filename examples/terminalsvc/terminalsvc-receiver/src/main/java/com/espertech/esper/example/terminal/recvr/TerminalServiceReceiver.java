/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.example.terminal.recvr;

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

    public TerminalServiceReceiver(String providerURL) throws NamingException, JMSException
    {
        System.setProperty("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");
        System.setProperty("java.naming.provider.url", providerURL);

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
        String providerURL = "localhost:1099";
        if (args.length > 0)
        {
            providerURL = args[0];
        }

        System.out.println("TerminalServiceReceiver attaching to provider url " +
                providerURL + " and queue " + LISTEN_QUEUE + "...");

        TerminalServiceReceiver client = new TerminalServiceReceiver(providerURL);

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
