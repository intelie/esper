package net.esper.adapter.jms;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import java.util.*;

import net.esper.event.*;
import net.esper.client.UpdateListener;
import net.esper.client.EPException;
import net.esper.adapter.*;

import javax.jms.Message;
import javax.jms.Session;

/**
 * Created by IntelliJ IDEA.
 * User: MYSELF
 * Date: Nov 25, 2006
 * Time: 10:53:15 AM
 * To change this template use File | Settings | File Templates.
 */
public class JMSAdapter implements UpdateListener, OutputAdapter
{
    private String role;
    private String outputStreamAlias;
    private EventType defaultInputEventType;
    private EventType inputEventType;
    private JmsTemplate defaultJmsTemplate;
    private JmsTemplate jmsTemplate;
    private JMSMarshaler defaultJMSMarshaler;
    private JMSMarshaler jmsMarshaler;
    private final List<EventBean[]> newDataList;
    private final List<EventBean[]> oldDataList;
    private EventBean[] lastNewData;
    private EventBean[] lastOldData;
    private boolean isInvoked;

    private static final Log log = LogFactory.getLog(JMSAdapter.class);

    public JMSAdapter()
    {
        newDataList = new LinkedList<EventBean[]>();
        oldDataList = new LinkedList<EventBean[]>();
    }

    public String getRole()
    {
        return role;
    }

    public void setRole(String role)
    {
        this.role = role;
    }

    public String getOutputStreamAlias()
    {
        return outputStreamAlias;
    }

    public void setOutputStreamAlias(String outputStreamAlias)
    {
        this.outputStreamAlias = outputStreamAlias;
    }

    public JmsTemplate getDefaultJmsTemplate()
    {
        return defaultJmsTemplate;
    }

    public void setDefaultJmsTemplate(JmsTemplate jmsTemplate)
    {
        this.defaultJmsTemplate = jmsTemplate;
    }

    public JmsTemplate getJmsTemplate()
    {
        return jmsTemplate;
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate)
    {
        this.jmsTemplate = jmsTemplate;
    }

    public JMSMarshaler getDefaultJMSMarshaler()
    {
        return defaultJMSMarshaler;
    }

    public void setDefaultJMSMarshaler(JMSMarshaler jmsMarshaler)
    {
        this.defaultJMSMarshaler = jmsMarshaler;
    }

    public JMSMarshaler getJMSMarshaler()
    {
        return jmsMarshaler;
    }

    public void setJMSMarshaler(JMSMarshaler jmsMarshaler)
    {
        this.jmsMarshaler = jmsMarshaler;
    }

    public void setDefaultInputEventType(EventType eventType)
    {
        this.defaultInputEventType= eventType;
    }

    public EventType getDefaultInputEventType()
    {
        return defaultInputEventType;
    }

    public void setInputEventType(EventType eventType)
    {
        this.inputEventType= eventType;
    }

    public EventType getInputEventType()
    {
        return inputEventType;
    }

    
    public void update(EventBean[] newData, EventBean[] oldData)
    {
        this.oldDataList.add(oldData);
        this.newDataList.add(newData);
        this.lastNewData = newData;
        this.lastOldData = oldData;
        isInvoked = true;
        for (EventBean event: lastNewData)
        {
            send(event);
            EventBean readEvent = read();
            log.info("Event Received " + readEvent.toString());
        }
    }

    public void send(final EventBean eventBean_) throws EPException
    {
        if (jmsTemplate != null)
        {
            jmsTemplate.send(
                    new MessageCreator()
                    {
                        public Message createMessage(Session session_)
                        {
                            Message msg = jmsMarshaler.unmarshal(eventBean_, session_);
                            log.debug("Creating jms message from event." + msg.toString());
                            return msg;
                        }
                     });
        }
        else
        {
            defaultJmsTemplate.send(
                    new MessageCreator()
                    {
                        public Message createMessage(Session session_)
                        {
                            Message msg = defaultJMSMarshaler.unmarshal(eventBean_, session_);
                            log.debug("Creating jms message from event." + msg.toString());
                            return msg;
                        }
                     });
        }
    }

    public EventBean read() throws EPException
    {
        EventBean evBean;
        if ((jmsTemplate != null) && (inputEventType != null))
        {
            evBean = jmsMarshaler.marshal(inputEventType, jmsTemplate.receive());
        }
        else
        {
            evBean = defaultJMSMarshaler.marshal(defaultInputEventType, defaultJmsTemplate.receive());
        }
        return evBean;
        //return new Object[]{evBean};
    }

    public List<EventBean[]> getNewDataList()
    {
        return newDataList;
    }

    public List<EventBean[]> getOldDataList()
    {
        return oldDataList;
    }

    public EventBean[] getLastNewData()
    {
        return lastNewData;
    }

    public EventBean[] getLastOldData()
    {
        return lastOldData;
    }

    public boolean isInvoked()
    {
        return isInvoked;
    }

    public void reset()
    {
        this.oldDataList.clear();
        this.newDataList.clear();
        this.lastNewData = null;
        this.lastOldData = null;
        isInvoked = false;
    }

    public EventBean[] getAndResetLastNewData()
    {
        EventBean[] lastNew = lastNewData;
        reset();
        return lastNew;
    }

    public UpdateListener getEventBeanListener()
    {
        return this;
    }

    /**
     * close not relevant for JMS adapter?
     */
    protected void close()
    {

    }


}

