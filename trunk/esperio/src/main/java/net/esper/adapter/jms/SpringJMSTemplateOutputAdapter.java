package net.esper.adapter.jms;

import net.esper.adapter.AdapterState;
import net.esper.adapter.AdapterStateManager;
import net.esper.adapter.OutputAdapter;
import net.esper.client.EPException;
import net.esper.client.EPRuntime;
import net.esper.client.EPServiceProvider;
import net.esper.core.EPServiceProviderSPI;
import net.esper.event.EventAdapterService;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.event.EventTypeListener;
import net.esper.filter.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.Message;
import javax.jms.Session;
import java.util.*;

/**
 * Created for ESPER.
 */

public class SpringJMSTemplateOutputAdapter
  implements OutputAdapter, FilterCallback, EventTypeListener
{

  private final AdapterStateManager stateManager = new AdapterStateManager();
  private EPServiceProviderSPI spi;
  private EventBean lastEvent;
  private int eventCount;
  private long startTime;

  private JmsTemplate jmsTemplate;
  private JMSDefaultMapMessageMarshaler jmsMarshaler;
  private String eventTypeAlias;
  private SpringMessageCreator messageCreator;
  private Set<String> subscriptionSet;

  private final Log log = LogFactory.getLog(this.getClass());

  // getters and setters called by Spring
  public JmsTemplate getJmsTemplate()
  {
    return jmsTemplate;
  }

  public void setJmsTemplate(JmsTemplate jmsTemplate)
  {
    this.jmsTemplate = jmsTemplate;
  }

  public JMSDefaultMapMessageMarshaler getJmsMarshaler()
  {
    return jmsMarshaler;
  }

  public void setJmsMarshaler(JMSDefaultMapMessageMarshaler jmsMarshaler)
  {
    this.jmsMarshaler = jmsMarshaler;
  }

  public String getEventTypeAlias()
  {
    return eventTypeAlias;
  }

  public void setEventTypeAlias(String eventTypeAlias)
  {
    this.eventTypeAlias = eventTypeAlias;
  }

  public Set getSubscriptionSet()
  {
    return subscriptionSet;
  }

  public void setSubscriptionSet(Set subscriptionSet)
  {
    this.subscriptionSet = subscriptionSet;
  }

  public EventTypeListener getEventTypeListener()
  {
    return this;
  }

  public EventBean getLastEvent()
  {
    return lastEvent;
  }

  public int getEventCount()
  {
    return eventCount;
  }

  public int getAndResetEventCount()
  {
    int count = eventCount;
    eventCount = 0;
    return count;
  }

  public void setEPServiceProvider(EPServiceProvider epService)
  {
    if (epService == null)
    {
      throw new NullPointerException("epService cannot be null");
    }
    if (!(epService instanceof EPServiceProviderSPI))
    {
      throw new IllegalArgumentException("Invalid type of EPServiceProvider");
    }
    spi = (EPServiceProviderSPI)epService;
  }

  public void start() throws EPException
  {
    log.debug(".start");
    if (spi.getEPRuntime() == null)
    {
      throw new EPException(
        "Attempting to start an Adapter that hasn't had the epService provided");
    }
    startTime = getCurrentTime();
    log.debug(".start startTime==" + startTime);
    stateManager.start();
    if (spi != null)
    {
      EventType eventType =
        spi.getEventAdapterService().getEventType(eventTypeAlias);
      FilterValueSet fvs = new FilterSpec(
        eventType, new LinkedList<FilterSpecParam>()).getValueSet(null);
      if (spi.getFilterService() != null)
      {
        spi.getFilterService().add(fvs, this);
      }
    }

  }

  public void matchFound(EventBean event)
  {
    send(event);
    lastEvent = event;
    eventCount++;
  }

  public void registeredEventType(String eventTypeAlias, EventType eventType)
  {
    if (spi.getFilterService() == null)
    {
      return;
    }
    FilterValueSet fvs =
      new FilterSpec(eventType, new LinkedList<FilterSpecParam>()).getValueSet(
        null);
    spi.getFilterService().add(fvs, this);
  }

  public void send(final EventBean eventBean) throws EPException
  {
    if (jmsTemplate != null)
    {
      if (messageCreator == null)
      {
        messageCreator = new SpringMessageCreator();
      }
      messageCreator.setMessageParameters(eventBean);
      jmsTemplate.send(messageCreator);
    }
  }

  public void pause() throws EPException
  {
    log.debug(".pause");
    stateManager.pause();
  }

  public void resume() throws EPException
  {
    log.debug(".resume");
    stateManager.resume();
  }

  public void stop() throws EPException
  {
    log.debug(".stop");
    stateManager.stop();
    reset();
  }

  public void reset()
  {
    eventCount = 0;
  }

  public void destroy() throws EPException
  {
    log.debug(".destroy");
    stateManager.destroy();
  }

  public AdapterState getState()
  {
    return stateManager.getState();
  }

  private long getCurrentTime()
  {
    return System.currentTimeMillis();
  }

  private class SpringMessageCreator implements MessageCreator
  {
    EventBean eventBean;

    public void setMessageParameters(EventBean eventBean)
    {
      this.eventBean = eventBean;
    }

    public Message createMessage(Session session)
    {
      if (eventBean == null)
      {
        return null;
      }
      Message msg =
        jmsMarshaler.marshal(eventBean, session, getCurrentTime());
      log.debug("Creating jms message from event." + msg.toString());
      return msg;
    }
  }
}
