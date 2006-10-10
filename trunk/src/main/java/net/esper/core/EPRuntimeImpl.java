package net.esper.core;

import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import net.esper.client.EPException;
import net.esper.client.EPRuntime;
import net.esper.client.EmittedListener;
import net.esper.client.time.CurrentTimeEvent;
import net.esper.client.time.TimerControlEvent;
import net.esper.client.time.TimerEvent;
import net.esper.collection.ThreadWorkQueue;
import net.esper.timer.TimerCallback;
import net.esper.event.EventBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Implements runtime interface. Also accepts timer callbacks for synchronizing time events with regular events
 * sent in.
 */
public class EPRuntimeImpl implements EPRuntime, TimerCallback, InternalEventRouter
{
    private EPServicesContext services;
    private ReadWriteLock timerRWLock;
    private ThreadWorkQueue threadWorkQueue;

    /**
     * Constructor.
     * @param services - references to services
     */
    public EPRuntimeImpl(EPServicesContext services)
    {
        this.services = services;
        timerRWLock = new ReentrantReadWriteLock();
        threadWorkQueue = new ThreadWorkQueue();
    }

    public void timerCallback()
    {
        if (log.isDebugEnabled())
        {
            log.debug(".timerCallback Evaluating scheduled callbacks");
        }

        long msec = System.currentTimeMillis();
        CurrentTimeEvent currentTimeEvent = new CurrentTimeEvent(msec);
        sendEvent(currentTimeEvent);
    }

    public void sendEvent(Object event) throws EPException
    {
        if (event == null)
        {
            log.fatal(".sendEvent Null object supplied");
            return;
        }

        if (log.isDebugEnabled())
        {
            log.debug(".sendEvent Processing event " + event);
        }

        // Process event
        processEvent(event);
    }

    public void sendEvent(org.w3c.dom.Node document) throws EPException
    {
        if (document == null)
        {
            log.fatal(".sendEvent Null object supplied");
            return;
        }

        if (log.isDebugEnabled())
        {
            log.debug(".sendEvent Processing DOM node event " + document);
        }

        // Get it wrapped up, process event
        EventBean eventBean = services.getEventAdapterService().adapterForDOM(document);
        processEvent(eventBean);
    }

    public void sendEvent(Map map, String eventTypeAlias) throws EPException
    {
        if (map == null)
        {
            throw new IllegalArgumentException("Invalid null event object");
        }

        if (log.isDebugEnabled())
        {
            log.debug(".sendMap Processing event " + map);
        }

        // Process event
        EventBean eventBean = services.getEventAdapterService().adapterForMap(map, eventTypeAlias);
        processEvent(eventBean);
    }

    public int getNumEventsReceived()
    {
        return services.getFilterService().getNumEventsEvaluated();
    }

    public int getNumEventsEmitted()
    {
        return services.getEmitService().getNumEventsEmitted();
    }

    public void route(Object event)
    {
        threadWorkQueue.add(event);
    }

    public void route(EventBean event)
    {
        threadWorkQueue.add(event);
    }

    public void emit(Object object)
    {
        services.getEmitService().emitEvent(object, null);
    }

    public void emit(Object object, String channel)
    {
        services.getEmitService().emitEvent(object, channel);
    }

    public void addEmittedListener(EmittedListener listener, String channel)
    {
        services.getEmitService().addListener(listener, channel);
    }

    public void clearEmittedListeners()
    {
        services.getEmitService().clearListeners();
    }

    private void processEvent(Object event)
    {
        if (event instanceof TimerEvent)
        {
            processTimeEvent((TimerEvent) event);
            return;
        }

        EventBean eventBean;

        if (event instanceof EventBean)
        {
            eventBean = (EventBean) event;
        }
        else
        {
            eventBean = services.getEventAdapterService().adapterForBean(event);
        }

        // All events are processed by the filter service
        try
        {
            timerRWLock.readLock().lock();

            services.getFilterService().evaluate(eventBean);

            // Dispatch internal work items and results
            dispatch();

            // Work off the event queue if any events accumulated in there via a route()
            processThreadWorkQueue();
        }
        catch (RuntimeException ex)
        {
            throw new EPException(ex);
        }
        finally
        {
            timerRWLock.readLock().unlock();
        }
    }

    private void processTimeEvent(TimerEvent event)
    {
        if (event instanceof TimerControlEvent)
        {
            TimerControlEvent timerControlEvent = (TimerControlEvent) event;
            if (timerControlEvent.getClockType() == TimerControlEvent.ClockType.CLOCK_INTERNAL)
            {
                // Start internal clock which supplies CurrentTimeEvent events every 100ms
                // This may be done without delay thus the write lock indeed must be reentrant.
                services.getTimerService().startInternalClock();
            }
            else
            {
                // Stop internal clock, for unit testing and for external clocking
                services.getTimerService().stopInternalClock(true);
            }

            return;
        }

        // Evaluation of all time events is protected from regular event stream processing
        timerRWLock.writeLock().lock();

        try
        {
            if (log.isDebugEnabled())
            {
                log.debug(".processTimeEvent Setting time and evaluating schedules");
            }

            CurrentTimeEvent current = (CurrentTimeEvent) event;
            long currentTime = current.getTimeInMillis();
            services.getSchedulingService().setTime(currentTime);

            services.getSchedulingService().evaluate();

            // Let listeners know of results
            dispatch();

            // Work off the event queue if any events accumulated in there via a route()
            processThreadWorkQueue();
        }
        catch (RuntimeException ex)
        {
            throw new EPException(ex);
        }
        finally
        {
            timerRWLock.writeLock().unlock();
        }
    }

    private void processThreadWorkQueue()
    {
        Object event;
        while ( (event = threadWorkQueue.next()) != null)
        {
            EventBean eventBean;
            if (event instanceof EventBean)
            {
                eventBean = (EventBean) event;
            }
            else
            {
                eventBean = services.getEventAdapterService().adapterForBean(event);
            }

            services.getFilterService().evaluate(eventBean);

            dispatch();
        }
    }

    private void dispatch()
    {
        try
        {
            services.getDispatchService().dispatch();
        }
        catch (RuntimeException ex)
        {
            throw new EPException(ex);
        }
    }

    private static final Log log = LogFactory.getLog(EPRuntimeImpl.class);
}
