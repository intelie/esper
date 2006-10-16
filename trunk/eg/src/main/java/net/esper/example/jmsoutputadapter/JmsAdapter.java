package net.esper.example.jmsoutputadapter;

import net.esper.event.EventBean;
import net.esper.client.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.URL;

/**
 * Created by IntelliJ IDEA.
 * User: MYSELF
 * Date: Oct 14, 2006
 * Time: 6:29:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class JmsAdapter implements UpdateListener
{
    private EventBean[] lastNewData;
    private EventBean[] lastOldData;
    private boolean isInvoked;

    private EPServiceProvider epService;
    private EPAdministrator admin;
    private EPStatement statement;
    private JmsSpringSender jmsSender;
    private JmsSpringReceiver jmsReceiver;

    public JmsAdapter()
    {
        try {
            URL url = getClass().getClassLoader().getResource("Spring/jms-spring.xml");
            ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(url.toString());
            jmsSender = (JmsSpringSender)appContext.getBean("jmsSender");
            jmsReceiver = (JmsSpringReceiver)appContext.getBean("jmsReceiver");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void setStatement(String stmt)
    {
        statement = admin.createEQL(stmt);
        statement.addListener(this);
    }

    public void initialize(String provider, String eventAlias, Class javaEventClass)
    {
        Configuration configuration = new Configuration();
        configuration.addEventTypeAlias(eventAlias, javaEventClass.getName());
        epService =  EPServiceProviderManager.getProvider(provider, configuration);
        admin = epService.getEPAdministrator();
    }

    public void update(EventBean[] newData, EventBean[] oldData)
    {
        this.lastNewData = newData;
        this.lastOldData = oldData;
        isInvoked = true;
        for (EventBean event: lastNewData)
        {
            jmsSender.sendMessage(event);
        }
    }

    public void sendEvent(Object event) throws InterruptedException
    {
        epService.getEPRuntime().sendEvent(event);
    }

    public void receiveEvent()
    {
        jmsReceiver.receiveMessage();
    }
    
     static Log log = LogFactory.getLog(JmsAdapter.class);
}
