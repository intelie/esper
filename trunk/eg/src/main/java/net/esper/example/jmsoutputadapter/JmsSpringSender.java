package net.esper.example.jmsoutputadapter;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.jms.*;

import net.esper.event.EventBean;

/**
 * Created by IntelliJ IDEA.
 * User: MYSELF
 * Date: Oct 14, 2006
 * Time: 6:27:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class JmsSpringSender
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

    public void sendMessage(final EventBean event) {

        jmsTemplate.send(
                new MessageCreator()
                {
                    public Message createMessage(Session session) throws JMSException {
                        log.debug("Creating the jms message from event.");
                        TextMessage message = session.createTextMessage();
                        for (String prop: event.getEventType().getPropertyNames())
                        {
                            message.setStringProperty(prop, event.get(prop).toString());
                        }
                        return message;
                    }
                 });
    }

    static Log log = LogFactory.getLog(JmsSpringSender.class);    
}
