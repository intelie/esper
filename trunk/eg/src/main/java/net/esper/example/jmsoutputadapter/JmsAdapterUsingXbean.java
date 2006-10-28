package net.esper.example.jmsoutputadapter;

import net.esper.client.*;
import net.esper.event.EventBean;

import java.net.URL;
import java.net.URI;
import java.net.URISyntaxException;
import java.io.File;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by IntelliJ IDEA.
 * User: MYSELF
 * Date: Oct 14, 2006
 * Time: 6:29:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class JmsAdapterUsingXbean extends SpringTestSupport implements UpdateListener {
    private EventBean[] lastNewData;
    private EventBean[] lastOldData;
    private boolean isInvoked;

    private EPServiceProvider epService;
    private EPAdministrator admin;
    private EPStatement statement;
    private JmsSpringSender jmsSender;
    private JmsSpringReceiver jmsReceiver;

    public JmsAdapterUsingXbean()
    {
        try {
            setUp();
            jmsSender = (JmsSpringSender)getBean("jmsSender");
            jmsReceiver = (JmsSpringReceiver) getBean("jmsReceiver");
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

    protected AbstractXmlApplicationContext createApplicationContext()
    {
        URL url = getClass().getClassLoader().getResource("Spring/jms-spring.xml");
        return new org.apache.xbean.spring.context.ClassPathXmlApplicationContext(url.toString());
    }

     static Log log = LogFactory.getLog(JmsAdapterUsingXbean.class);
}
