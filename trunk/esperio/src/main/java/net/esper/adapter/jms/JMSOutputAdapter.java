package net.esper.adapter.jms;

import net.esper.adapter.*;
import net.esper.adapter.subscription.*;
import net.esper.client.*;
import net.esper.core.*;
import net.esper.event.*;
import org.apache.commons.logging.*;

import java.util.*;

/**
 * Created for ESPER.
 */
public abstract class JMSOutputAdapter implements OutputAdapter
{
  protected EPServiceProviderSPI spi;
  protected long startTime;
  protected final AdapterStateManager stateManager = new AdapterStateManager();
  protected Map<String, Subscription> subscriptionMap;
  protected JMSMessageMarshaler jmsMessageMarshaler;

  private final Log log = LogFactory.getLog(this.getClass());

  public JMSMessageMarshaler getJmsMessageMarshaler()
  {
    return jmsMessageMarshaler;
  }

  public void setJmsMessageMarshaler(JMSMessageMarshaler jmsMessageMarshaler)
  {
    this.jmsMessageMarshaler = jmsMessageMarshaler;
  }

  public Map<String, Subscription> getSubscriptionMap()
  {
    return subscriptionMap;
  }

  public void setSubscriptionMap(Map<String, Subscription> subscriptionMap)
  {
    this.subscriptionMap = subscriptionMap;
    // In case an alias has not been set for each subscription
    Iterator<Map.Entry<String, Subscription>> it =
      subscriptionMap.entrySet().iterator();
    while (it.hasNext())
    {
      Map.Entry<String, Subscription> entry = it.next();
      Subscription subscription = entry.getValue();
      if (subscription.getAlias() == null)
      {
        subscription.setAlias(entry.getKey());
      }
    }
  }

  public void addSubscription(Subscription subscription,
    String subscriptionAlias)
  {
    if ((subscriptionAlias != null) || (subscription != null))
    {
      subscriptionMap.put(subscriptionAlias, subscription);
    }
  }

  public void addSubscription(Subscription subscription)
  {
    if (subscription != null)
    {
      if (subscription.getAlias() != null)
      {
        subscriptionMap.put(subscription.getAlias(), subscription);
      }
    }
  }

  public Subscription getSubscription(String subscriptionAlias)
  {
    if (subscriptionAlias == null)
    {
      return null;
    }
    return subscriptionMap.get(subscriptionAlias);
  }

  public EPServiceProvider getEPServiceProvider()
  {
    return spi;
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
    Iterator<Map.Entry<String, Subscription>> it =
      subscriptionMap.entrySet().iterator();
    while (it.hasNext())
    {
      it.next().getValue().registerAdapter(this);
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

  public abstract void send(final EventBean eventBean,
    JMSMessageMarshaler jmsAdapterMarshaler) throws EPException;

  protected long getCurrentTime()
  {
    return System.currentTimeMillis();
  }

}
