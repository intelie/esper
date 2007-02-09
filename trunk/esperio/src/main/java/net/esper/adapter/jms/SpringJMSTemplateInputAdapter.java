package net.esper.adapter.jms;

import net.esper.adapter.*;
import net.esper.client.*;
import net.esper.core.*;
import net.esper.event.*;
import net.esper.schedule.*;
import org.apache.commons.logging.*;
import org.springframework.jms.core.*;

/**
 * Created for ESPER.
 */
public class SpringJMSTemplateInputAdapter extends AbstractCoordinatedAdapter
{
  private EPServiceProviderSPI spi;
  private EPRuntime runtime;
  private EventAdapterService evAdaptSvc;
  private SchedulingService schedulingService;

  private JmsTemplate jmsTemplate;
  private JMSDefaultMapMessageUnmarshaler jmsUnmarshaler;
  private String eventTypeAlias;

  private long totalDelay;

  private final Log log = LogFactory.getLog(getClass());

  // getters and setters called by Spring
  public JmsTemplate getJmsTemplate()
  {
    return jmsTemplate;
  }

  public void setJmsTemplate(JmsTemplate jmsTemplate)
  {
    this.jmsTemplate = jmsTemplate;
  }

  public JMSDefaultMapMessageUnmarshaler getJmsMarshaler()
  {
    return jmsUnmarshaler;
  }

  public void setJmsMarshaler(JMSDefaultMapMessageUnmarshaler jmsUnmarshaler)
  {
    this.jmsUnmarshaler = jmsUnmarshaler;
  }

  public String getEventTypeAlias()
  {
    return eventTypeAlias;
  }

  public void setEventTypeAlias(String eventTypeAlias)
  {
    this.eventTypeAlias = eventTypeAlias;
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
    EPServiceProviderSPI spi = (EPServiceProviderSPI)epService;
    runtime = spi.getEPRuntime();
    schedulingService = spi.getSchedulingService();
    evAdaptSvc = spi.getEventAdapterService();
  }


  public SendableEvent read()
  {
    JMSEventBean evBean = null;

    if (stateManager.getState() == AdapterState.DESTROYED)
    {
      return null;
    }

    try
    {
      if (eventsToSend.isEmpty())
      {
        EventType eventType = evAdaptSvc.getEventType(eventTypeAlias);
        if ((jmsTemplate != null) && (eventType != null))
        {
          //evBean = jmsUnmarshaler.unmarshal(
          //  evAdaptSvc, jmsTemplate.receive(), totalDelay, scheduleSlot);
          evBean = null;
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
      if (stateManager.getState() == AdapterState.STARTED)
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
    /*if(eventsPerSec > -1)
    {
        totalDelay += 1000/eventsPerSec;
    }*/
  }

  /**
   * Reset all the changeable state of this Adapter, as if it were just
   * created.
   */
  protected void reset()
  {
    totalDelay = 0;
  }

  /**
   * Remove the first member of eventsToSend.
   */
  protected void replaceFirstEventToSend()
  {
    eventsToSend.remove(eventsToSend.first());
    SendableEvent event = read();
    if (event != null)
    {
      eventsToSend.add(event);
    }
  }


  /**
   * close() method not relevant for JMS adapter.
   */
  protected void close()
  {

  }

}
