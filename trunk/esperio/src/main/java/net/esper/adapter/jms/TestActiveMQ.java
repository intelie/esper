package net.esper.adapter.jms;

import junit.framework.*;
import net.esper.client.*;
import org.apache.activemq.*;
import org.apache.activemq.broker.*;
import org.apache.activemq.util.*;
import org.apache.commons.logging.*;
import org.springframework.beans.*;
import org.springframework.context.support.*;

import javax.jms.Connection;
import javax.jms.*;
import java.io.*;
import java.util.*;

/**
 * Created for ESPER.
 */
public class TestActiveMQ extends TestCase
{
  private final Log log = LogFactory.getLog(this.getClass());
  private AbstractXmlApplicationContext adapterSpringContext;
  private int messageCount = 10;
  private long sleepTime = 0L;
  private boolean verbose = true;
  private int messageSize = 255;
  private long timeToLive;
  private String testQueue = "ESPER.QUEUE";
  private boolean topic = false;
  private String user = ActiveMQConnection.DEFAULT_USER;
  private String password = ActiveMQConnection.DEFAULT_PASSWORD;
  private String url = "vm://localhost";
  //private String url = ActiveMQConnection.DEFAULT_BROKER_URL;
  private boolean transacted = false;
  private boolean persistent = false;
  private Destination destination;
  private MessageProducer producer;
  private MessageConsumer consumer;
  private Session session;

  protected void setUp() throws Exception
  {
    adapterSpringContext =
      createSpringApplicationContext("spring\\spring-activeMQ.xml", true);
    String[] beanNames = adapterSpringContext.getBeanDefinitionNames();
    for (String beanName : beanNames)
    {
      Object o = getBean(beanName);
    }
  }

  public void testConsumerProducer() throws Throwable
  {
    createActiveMQProducer();
  }

  public void createBroker() throws Exception
  {
    BrokerService broker = new BrokerService();
    broker.setUseJmx(true);
    broker.addConnector(url);
    broker.start();
  }

  private void createActiveMQProducer()
  {
    Connection connection = null;
    try
    {

      System.out.println("Connecting to URL: " + url);
      System.out.println(
        "Publishing a Message with size " + messageSize + " to " +
          (topic ? "topic" : "queue") + ": " + testQueue);
      System.out.println(
        "Using " + (persistent ? "persistent" : "non-persistent") +
          " messages");
      System.out.println("Sleeping between publish " + sleepTime + " ms");

      // Create the connection
      ActiveMQConnectionFactory connectionFactory =
        new ActiveMQConnectionFactory(user, password, url);
      connection = connectionFactory.createConnection();
      connection.start();

      // Create the Session
      session = connection.createSession(transacted, Session.AUTO_ACKNOWLEDGE);

      // And the Destinations..
      destination = session.createTemporaryQueue();
      System.out.println("Destination: " + destination);

      // Create the producer
      producer = session.createProducer(destination);
      producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
      if (timeToLive != 0)
      {
        System.out.println("Messages time to live " + timeToLive + " ms");
        producer.setTimeToLive(timeToLive);
      }

      // Create the reply consumer
      consumer = session.createConsumer(destination);
      //consumer.setMessageListener(this);

      // Start sending reqests.
      requestLoop();

      System.out.println("Done.");

      // Use the ActiveMQConnection interface to dump the connection stats.
      ActiveMQConnection c = (ActiveMQConnection)connection;
      c.getConnectionStats().dump(new IndentPrinter());

    }
    catch (Exception e)
    {
      System.out.println("Caught: " + e);
      e.printStackTrace();
    }
    finally
    {
      try
      {
        connection.close();
      }
      catch (Throwable ignore)
      {
      }
    }
  }

  protected void requestLoop() throws Exception
  {

    for (int i = 0; i < messageCount || messageCount == 0; i++)
    {

      TextMessage message = session.createTextMessage(createMessageText(i));
      //message.setJMSReplyTo(destination);

      if (verbose)
      {
        String msg = message.getText();
        if (msg.length() > 50)
        {
          msg = msg.substring(0, 50) + "...";
        }
        System.out.println("Sending message: " + msg);
      }

      producer.send(message);
      System.out.println("Waiting for reponse message...");
      Message message2 = consumer.receive();
      if (message2 instanceof TextMessage)
      {
        System.out.println(
          "Reponse message: " + ((TextMessage)message2).getText());
      }
      else
      {
        System.out.println("Reponse message: " + message2);
      }
      Thread.sleep(sleepTime);

    }
  }

  /**
   * @param index
   *
   * @return
   */
  private String createMessageText(int index)
  {
    StringBuffer buffer = new StringBuffer(messageSize);
    buffer.append("Message: " + index + " sent at: " + new Date());
    if (buffer.length() > messageSize)
    {
      return buffer.substring(0, messageSize);
    }
    for (int i = buffer.length(); i < messageSize; i++)
    {
      buffer.append(' ');
    }
    return buffer.toString();
  }


  public void setPersistent(boolean durable)
  {
    this.persistent = durable;
  }

  public void setMessageCount(int messageCount)
  {
    this.messageCount = messageCount;
  }

  public void setMessageSize(int messageSize)
  {
    this.messageSize = messageSize;
  }

  public void setPassword(String password)
  {
    this.password = password;
  }

  public void setSleepTime(long sleepTime)
  {
    this.sleepTime = sleepTime;
  }

  public void setQueue(String testQueue)
  {
    this.testQueue = testQueue;
  }

  public void setTimeToLive(long timeToLive)
  {
    this.timeToLive = timeToLive;
  }

  public void setTopic(boolean topic)
  {
    this.topic = topic;
  }

  public void setQueue(boolean queue)
  {
    this.topic = !queue;
  }

  public void setTransacted(boolean transacted)
  {
    this.transacted = transacted;
  }

  public void setUrl(String url)
  {
    this.url = url;
  }

  public void setUser(String user)
  {
    this.user = user;
  }

  public void setVerbose(boolean verbose)
  {
    this.verbose = verbose;
  }

  private Object getBean(String name)
  {
    //assertNotNull("Could not find object in Spring for key: " + name);
    return adapterSpringContext.getBean(name);
  }

  private AbstractXmlApplicationContext createSpringApplicationContext(
    String configuration, boolean fromClassPath) throws BeansException
  {
    if (fromClassPath)
    {
      log.debug("classpath configuration");
      return new ClassPathXmlApplicationContext(configuration);
    }
    if (new File(configuration).exists())
    {
      log.debug("File configuration");
      return new FileSystemXmlApplicationContext(configuration);
    }
    else
    {
      throw new EPException("Spring configuration file not found.");
    }
  }

}
