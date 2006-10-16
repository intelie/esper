package net.esper.example.jmsoutputadapter;

/**
 * Created by IntelliJ IDEA.
 * User: MYSELF
 * Date: Oct 8, 2006
 * Time: 4:00:16 PM
 * To change this template use File | Settings | File Templates.
 */
import javax.jms.*;
import javax.naming.*;

public class OutputQueue {

    /**
     * Main method.
     *
     * @param args     the queue used by the example and,
     *                 optionally, the number of messages to send
     */
    public static void main(String[] args) {
        String                  queueName = null;
        Context                 jndiContext = null;
        QueueConnectionFactory  queueConnectionFactory = null;
        QueueConnection         queueConnection = null;
        QueueSession            queueSession = null;
        Queue                   queue = null;
        QueueSender             queueSender = null;
        TextMessage             message = null;
        final int               NUM_MSGS;

        if ( (args.length < 1) || (args.length > 2) ) {
            System.out.println("Usage: java SimpleQueueSender " +
                "<queue-name> [<number-of-messages>]");
            System.exit(1);
        }
        queueName = new String(args[0]);
        System.out.println("Queue name is " + queueName);
        if (args.length == 2){
            NUM_MSGS = (new Integer(args[1])).intValue();
        } else {
            NUM_MSGS = 1;
        }

        /*
         * Create a JNDI API InitialContext object if none exists
         * yet.
         */
        try {
            jndiContext = new InitialContext();
        } catch (NamingException e) {
            System.out.println("Could not create JNDI API " +
                "context: " + e.toString());
            System.exit(1);
        }

        /*
         * Look up connection factory and queue.  If either does
         * not exist, exit.
         */
        try {
            queueConnectionFactory = (QueueConnectionFactory)
                jndiContext.lookup("QueueConnectionFactory");
            queue = (Queue) jndiContext.lookup(queueName);
        } catch (NamingException e) {
            System.out.println("JNDI API lookup failed: " +
                e.toString());
            System.exit(1);
        }

        /*
         * Create connection.
         * Create session from connection; false means session is
         * not transacted.
         * Create sender and text message.
         * Send messages, varying text slightly.
         * Send end-of-messages message.
         * Finally, close connection.
         */
        try {
            queueConnection =
                queueConnectionFactory.createQueueConnection();
            queueSession =
                queueConnection.createQueueSession(false,
                    Session.AUTO_ACKNOWLEDGE);
            queueSender = queueSession.createSender(queue);
            message = queueSession.createTextMessage();
            for (int i = 0; i < NUM_MSGS; i++) {
                message.setText("This is message " + (i + 1));
                System.out.println("Sending message: " +
                    message.getText());
                queueSender.send(message);
            }

            /*
             * Send a non-text control message indicating end of
             * messages.
             */
            queueSender.send(queueSession.createMessage());
        } catch (JMSException e) {
            System.out.println("Exception occurred: " +
                e.toString());
        } finally {
            if (queueConnection != null) {
                try {
                    queueConnection.close();
                } catch (JMSException e) {}
            }
        }
    }
}