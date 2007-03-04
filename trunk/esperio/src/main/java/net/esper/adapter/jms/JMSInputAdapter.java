package net.esper.adapter.jms;

import net.esper.adapter.*;
import net.esper.client.*;
import net.esper.core.*;
import net.esper.event.*;
import org.apache.commons.logging.*;

/**
 * Created for ESPER.
 */
public abstract class JMSInputAdapter implements InputAdapter
{
  protected final AdapterStateManager stateManager = new AdapterStateManager();
  protected EPServiceProviderSPI spi;
  protected EPRuntime epRuntime;
  protected EventAdapterService eventAdapterSvc;
  protected long currentTime = 0;
  protected long startTime;
  protected JMSMessageUnmarshaler jmsMessageUnmarshaler;

  private final Log log = LogFactory.getLog(this.getClass());

  public JMSMessageUnmarshaler getJmsMessageUnmarshaler()
  {
    return jmsMessageUnmarshaler;
  }

  public void setJmsMessageUnmarshaler(
    JMSMessageUnmarshaler jmsMessageUnmarshaler)
  {
    this.jmsMessageUnmarshaler = jmsMessageUnmarshaler;
  }

  public EPServiceProvider getEPServiceProvider()
  {
    return spi;
  }

  public EPRuntime getEPRuntime()
  {
    return epRuntime;
  }

  public void setEPRuntime(EPRuntime epRuntime)
  {
    this.epRuntime = epRuntime;
  }

  public void setEPServiceProvider(EPServiceProvider epService)
  {
    if (epService == null)
    {
      throw new NullPointerException(AdapterUtils.EP_SERVICE_NOT_FOUND_ERROR);
    }
    if (!(epService instanceof EPServiceProviderSPI))
    {
      throw new IllegalArgumentException(
        AdapterUtils.EP_SERVICE_INVALID_TYPE_ERROR);
    }
    spi = (EPServiceProviderSPI)epService;
    this.epRuntime = spi.getEPRuntime();
    this.eventAdapterSvc = spi.getEventAdapterService();
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

  protected long getCurrentTime()
  {
    return System.currentTimeMillis();
  }


}
