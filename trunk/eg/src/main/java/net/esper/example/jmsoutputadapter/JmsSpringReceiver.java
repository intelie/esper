package net.esper.example.jmsoutputadapter;

import org.springframework.jms.core.JmsTemplate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import net.esper.event.EventBean;

import javax.jms.Message;
import javax.jms.JMSException;
import java.util.Enumeration;

/**
 * Created by IntelliJ IDEA.
 * User: MYSELF
 * Date: Oct 15, 2006
 * Time: 5:05:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class JmsSpringReceiver
{
    private JmsTemplate jmsTemplate;

    /**
     * @return Returns the jmsTemplate.
     */
    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }
    /**
     * @param jmsTemplate The jmsTemplate to set.
     */
    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void receiveMessage()
    {
        EventBean event = null;
        Message msg = jmsTemplate.receive();
        try {
            Enumeration en = msg.getPropertyNames();
            while (en.hasMoreElements())
            {
                String prop = (String) en.nextElement();
                log.info("Received property: "+ msg.getStringProperty(prop));
            }
        } catch (JMSException e1) {
            e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
    static Log log = LogFactory.getLog(JmsSpringReceiver.class); 
}
