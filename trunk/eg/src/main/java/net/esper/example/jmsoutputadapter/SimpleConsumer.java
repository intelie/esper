package net.esper.example.jmsoutputadapter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.jms.*;
import javax.naming.*;
import java.util.Properties;


/**
 * Created by IntelliJ IDEA.
 * User: MYSELF
 * Date: Oct 8, 2006
 * Time: 4:54:21 PM
 * To change this template use File | Settings | File Templates.
 */


public class SimpleConsumer {

    public static void main(String[] args) {

        QueueConnectionFactory queueConnectionFactory = null;
        Queue testQueue = null;

        try {
            log.info("Creating jndi context - alternatively use a jndi.properties");
            Properties properties = new Properties();
            properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
            properties.put(Context.PROVIDER_URL, "jnp://localhost:1099");
            InitialContext jndiContext = new InitialContext(properties);
            queueConnectionFactory = (QueueConnectionFactory) jndiContext.lookup("ConnectionFactory");
            testQueue = (Queue) jndiContext.lookup("queue/testQueue");

        } catch (NamingException nameEx) {
            log.info("Naming Exception: " + nameEx.toString());
        }

        QueueConnection queueConnection = null;

        try {

            queueConnection = queueConnectionFactory.createQueueConnection();
            QueueSession queueSession = queueConnection.createQueueSession(false,Session.AUTO_ACKNOWLEDGE);
            QueueReceiver queueReceiver = queueSession.createReceiver(testQueue);
            queueConnection.start();

            TextMessage textMessage = null;

            while (true)
            {
                textMessage = (TextMessage) queueReceiver.receive(1);
                log.info(" receiving line " + " : " + textMessage.getText());
                if (textMessage.getText().equals("end of message")) {break;}
            }

            queueConnection.close();

            log.info(" receiver closed");

        } catch (javax.jms.JMSException jmsEx) {log.info("JMS Exception: " + jmsEx.toString());
        } finally { if (queueConnection != null)
            {   try
                { queueConnection.close();
                } catch (javax.jms.JMSException jmse) {}
            }
        }
    }
    static Log log = LogFactory.getLog(SimpleConsumer.class);
}
