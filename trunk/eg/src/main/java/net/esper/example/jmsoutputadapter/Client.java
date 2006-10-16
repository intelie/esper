package net.esper.example.jmsoutputadapter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Properties;

import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;

/**
 * Created by IntelliJ IDEA.
 * User: MYSELF
 * Date: Oct 8, 2006
 * Time: 5:02:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class Client {

    public static void main(String[] args) throws Exception
    {
      log.info("Creating jndi context - alternatively use a jndi.properties");
      Properties props = new Properties();
      props.setProperty("java.naming.provider.url", "localhost:1099");
      props.setProperty("java.naming.factory.url.pkgs", "org.jboss.naming");
      props.setProperty("java.naming.factory.initial","org.jnp.interfaces.NamingContextFactory");
      InitialContext ctx = new InitialContext( props );

      log.info("Looking up queue");
      Queue queue = (Queue) ctx.lookup("queue/testQueue");

      log.info("Looking up connection factory");
      QueueConnectionFactory qcf = (QueueConnectionFactory) ctx.lookup("UIL2ConnectionFactory");

      log.info("Creating connection");
      QueueConnection qc = qcf.createQueueConnection();
      try
      {
         log.info("Creating session");
         QueueSession qs = qc.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

         log.info("Creating sender");
         QueueSender sender = qs.createSender(queue);

         log.info("Creating message");
         TextMessage message = qs.createTextMessage("hello");

         log.info("Sending message");
         sender.send(message);

         log.info("Creating receiver");
         QueueReceiver receiver = qs.createReceiver(queue);

         log.info("Try to receive message, it will not work");
         Message received = receiver.receiveNoWait();
         if (received != null)
            throw new RuntimeException("Should not get a message if the connection is not started!");

         log.info("You have to start the connection before receiving messages");
         qc.start();

         log.info("This receive will work");
         received = receiver.receiveNoWait();

         log.info("Got message: " + received);
      }
      finally
      {
         qc.close();
      }
   }
    static Log log = LogFactory.getLog(Client.class);
}
