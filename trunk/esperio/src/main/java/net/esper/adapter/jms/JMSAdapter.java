package net.esper.adapter.jms;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import java.util.*;

import net.esper.event.*;
import net.esper.client.UpdateListener;
import net.esper.client.EPException;
import net.esper.client.EPServiceProvider;
import net.esper.adapter.*;
import net.esper.core.EPServiceProviderSPI;

import javax.jms.Message;
import javax.jms.Session;

/**
 * Created by IntelliJ IDEA.
 * User: MYSELF
 * Date: Nov 25, 2006
 * Time: 10:53:15 AM
 * To change this template use File | Settings | File Templates.
 */
public class JMSAdapter extends AbstractCoordinatedAdapter implements UpdateListener
{
    private AdapterRole role;
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
    private int eventsPerSec = -1;
    private long totalDelay;

    private final Log log = LogFactory.getLog(getClass());

    public JMSAdapter()
    {
        newDataList = new LinkedList<EventBean[]>();
        oldDataList = new LinkedList<EventBean[]>();
    }

    public AdapterRole getRole()
    {
        return role;
    }

    public void setRole(AdapterRole role)
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

    public int getEventsPerSec()
    {
        return eventsPerSec;
    }

    public void setEventsPerSec(int eventsPerSec)
    {
        this.eventsPerSec = eventsPerSec;
    }

    /* (non-Javadoc)
     * @see net.esper.adapter.AbstractCoordinatedAdapter#setEPService(net.esper.client.EPServiceProvider)
     */
    @Override
    public void setEPService(EPServiceProvider epService)
    {
        assertValidParameters(epService);

        EPServiceProviderSPI spi = (EPServiceProviderSPI)epService;

        scheduleSlot = spi.getSchedulingService().allocateBucket().allocateSlot();

        super.setEPService(epService);
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

    public SendableEvent read()
    {
        JMSEventBean evBean;

        if(stateManager.getState() == AdapterState.DESTROYED)
        {
            return null;
        }

        try
        {
            if(eventsToSend.isEmpty())
            {
                if ((jmsTemplate != null) && (inputEventType != null))
                {
                    evBean = jmsMarshaler.marshal(inputEventType, jmsTemplate.receive(), totalDelay, scheduleSlot);
                }
                else
                {
                    evBean = defaultJMSMarshaler.marshal(defaultInputEventType, defaultJmsTemplate.receive(), totalDelay, scheduleSlot);
                }
                updateTotalDelay();
                return evBean;
            }
            else
            {
                SendableEvent event = eventsToSend.first();
                eventsToSend.remove(event);
                return event;
            }
        }
        catch (EPException ex)
        {
            log.debug(".Marshalling exception");
            if(stateManager.getState() == AdapterState.STARTED)
            {
                stop();
            }
            else
            {
                destroy();
            }
            return null;
        }

    }

    private void updateTotalDelay()
    {
        if(eventsPerSec > -1)
        {
            totalDelay += 1000/eventsPerSec;
        }
    }

    /**
     * Remove the first member of eventsToSend.
     */
    protected void replaceFirstEventToSend()
    {
        eventsToSend.remove(eventsToSend.first());
        SendableEvent event = read();
        if(event != null)
        {
            eventsToSend.add(event);
        }
    }

    /**
     * Reset all the changeable state of this Adapter, as if it were just created.
     */
    protected void reset()
    {
        if ((role == AdapterRole.RECEIVER) || (role == AdapterRole.BOTH))
        {
            totalDelay = 0;
        }
        // Sender
        else
        {
            this.oldDataList.clear();
            this.newDataList.clear();
            this.lastNewData = null;
            this.lastOldData = null;
            isInvoked = false;
        }
    }

    /**
     * close not relevant for JMS adapter?
     */
    protected void close()
    {

    }

    private void assertValidParameters(EPServiceProvider epService)
    {
        if(!(epService instanceof EPServiceProviderSPI))
        {
            throw new IllegalArgumentException("Invalid type of EPServiceProvider");
        }
    }


}

