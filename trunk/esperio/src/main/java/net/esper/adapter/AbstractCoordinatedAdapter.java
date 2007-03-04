package net.esper.adapter;

import net.esper.client.*;
import net.esper.core.*;
import net.esper.schedule.*;
import org.apache.commons.logging.*;

import java.util.*;

/**
 * A skeleton implementation of the ReadableAdapter interface.
 */
public abstract class AbstractCoordinatedAdapter implements CoordinatedAdapter
{
  private static final Log log =
    LogFactory.getLog(AbstractCoordinatedAdapter.class);

  protected final AdapterStateManager stateManager = new AdapterStateManager();
  protected final SortedSet<SendableEvent> eventsToSend =
    new TreeSet<SendableEvent>(new SendableEventComparator());
  protected ScheduleSlot scheduleSlot;

  private EPServiceProviderSPI spi;
  private EPRuntime runtime;
  private SchedulingService schedulingService;
  private boolean usingEngineThread;
  private long currentTime = 0;
  private long startTime;

  /**
   * Ctor.
   */
  public AbstractCoordinatedAdapter()
  {
  }

  /**
   * Ctor.
   *
   * @param epService - the EPServiceProvider for the engine runtime and
   * services
   * @param usingEngineThread - true if the Adapter should set time by the
   * scheduling service in the engine, false if it should set time externally
   * through the calling thread
   */
  public AbstractCoordinatedAdapter(EPServiceProvider epService,
    boolean usingEngineThread)
  {
    this.usingEngineThread = usingEngineThread;

    if (epService == null)
    {
      return;
    }
    if (!(epService instanceof EPServiceProviderSPI))
    {
      throw new IllegalArgumentException("Invalid epService provided");
    }
    this.runtime = ((EPServiceProviderSPI)epService).getEPRuntime();
    this.schedulingService =
      ((EPServiceProviderSPI)epService).getSchedulingService();
  }

  public AdapterState getState()
  {
    return stateManager.getState();
  }

  public void start() throws EPException
  {
    log.debug(".start");
    if (runtime == null)
    {
      throw new EPException(
        "Attempting to start an Adapter that hasn't had the epService provided");
    }
    startTime = getCurrentTime();
    log.debug(".start startTime==" + startTime);
    stateManager.start();
    continueSendingEvents();
  }

  public void pause() throws EPException
  {
    stateManager.pause();
  }

  public void resume() throws EPException
  {
    stateManager.resume();
    continueSendingEvents();
  }

  public void destroy() throws EPException
  {
    stateManager.destroy();
    close();
  }

  public void stop() throws EPException
  {
    log.debug(".stop");
    stateManager.stop();
    eventsToSend.clear();
    currentTime = 0;
    reset();
  }

  /* (non-Javadoc)
    * @see net.esper.adapter.ReadableAdapter#disallowStateChanges()
    */
  public void disallowStateTransitions()
  {
    stateManager.disallowStateTransitions();
  }

  /* (non-Javadoc)
    * @see net.esper.adapter.ReadableAdapter#setUsingEngineThread(boolean)
    */
  public void setUsingEngineThread(boolean usingEngineThread)
  {
    this.usingEngineThread = usingEngineThread;
  }

  /* (non-Javadoc)
    * @see net.esper.adapter.CoordinatedAdapter#setScheduleSlot(net.esper.schedule.ScheduleSlot)
    */
  public void setScheduleSlot(ScheduleSlot scheduleSlot)
  {
    this.scheduleSlot = scheduleSlot;
  }

  /* (non-Javadoc)
    * @see net.esper.adapter.CoordinatedAdapter#setEPService(net.esper.client.EPServiceProvider)
    */
  public void setEPService(EPServiceProvider epService)
  {
    if (epService == null)
    {
      throw new NullPointerException("epService cannot be null");
    }
    if (!(epService instanceof EPServiceProviderSPI))
    {
      throw new IllegalArgumentException("Invalid type of EPServiceProvider");
    }
    EPServiceProviderSPI spi = (EPServiceProviderSPI)epService;
    runtime = spi.getEPRuntime();
    schedulingService = spi.getSchedulingService();
  }

  /**
   * Perform any actions specific to this Adapter that should be completed before
   * the Adapter is stopped.
   */
  protected abstract void close();

  /**
   * Remove the first member of eventsToSend and insert another event chosen in
   * some fashion specific to this Adapter.
   */
  protected abstract void replaceFirstEventToSend();

  /**
   * Reset all the changeable state of this Adapter, as if it were just created.
   */
  protected abstract void reset();

  private void continueSendingEvents()
  {
    if (stateManager.getState() == AdapterState.STARTED)
    {
      currentTime = getCurrentTime();
      log.debug(".continueSendingEvents currentTime==" + currentTime);
      fillEventsToSend();
      sendSoonestEvents();
      waitToSendEvents();
    }
  }

  private void waitToSendEvents()
  {
    if (usingEngineThread)
    {
      scheduleNextCallback();
    }
    else
    {
      long sleepTime = 0;
      if (eventsToSend.isEmpty())
      {
        sleepTime = 100;
      }
      else
      {
        sleepTime =
          eventsToSend.first().getSendTime() - (currentTime - startTime);
      }

      try
      {
        Thread.sleep(sleepTime);
      }
      catch (InterruptedException ex)
      {
        throw new EPException(ex);
      }
      continueSendingEvents();
    }
  }

  private long getCurrentTime()
  {
    if (schedulingService == null)
    {
      return System.currentTimeMillis();
    }
    return usingEngineThread ?
      schedulingService.getTime() :
      System.currentTimeMillis();
  }

  private void fillEventsToSend()
  {
    if (eventsToSend.isEmpty())
    {
      SendableEvent event = read();
      if (event != null)
      {
        eventsToSend.add(event);
      }
    }
  }

  private void sendSoonestEvents()
  {
    while (!eventsToSend.isEmpty() &&
      eventsToSend.first().getSendTime() <= currentTime - startTime)
    {
      log.debug(".sendSoonestEvents currentTime==" + currentTime);
      log.debug(
        ".sendSoonestEvents sending event " + eventsToSend.first() +
          ", its sendTime==" + eventsToSend.first().getSendTime());
      eventsToSend.first().send(runtime);
      replaceFirstEventToSend();
    }
  }

  private void scheduleNextCallback()
  {
    ScheduleCallback nextScheduleCallback = new ScheduleCallback()
    {
      public void scheduledTrigger()
      {
        continueSendingEvents();
      }
    };
    ScheduleSlot nextScheduleSlot;

    if (schedulingService == null)
    {
      return;
    }

    if (eventsToSend.isEmpty())
    {
      log.debug(
        ".scheduleNextCallback no events to send, scheduling callback in 100 ms");
      nextScheduleSlot = new ScheduleSlot(0, 0);
      schedulingService.add(100, nextScheduleCallback, nextScheduleSlot);
    }
    else
    {
      long afterMsec = eventsToSend.first().getSendTime() - currentTime;
      nextScheduleSlot = eventsToSend.first().getScheduleSlot();
      log.debug(
        ".scheduleNextCallback schedulingCallback in " + afterMsec +
          " milliseconds");
      schedulingService.add(afterMsec, nextScheduleCallback, nextScheduleSlot);
    }
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
    this.runtime = spi.getEPRuntime();
  }

  public EPServiceProvider getEPServiceProvider()
  {
    return spi;
  }

}
