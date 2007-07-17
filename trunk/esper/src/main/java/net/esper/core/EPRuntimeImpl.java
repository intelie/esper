/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.core;

import net.esper.client.EPException;
import net.esper.client.EPRuntime;
import net.esper.client.EmittedListener;
import net.esper.client.time.CurrentTimeEvent;
import net.esper.client.time.TimerControlEvent;
import net.esper.client.time.TimerEvent;
import net.esper.collection.ArrayBackedCollection;
import net.esper.collection.ThreadWorkQueue;
import net.esper.event.EventBean;
import net.esper.filter.FilterHandle;
import net.esper.filter.FilterHandleCallback;
import net.esper.schedule.ScheduleHandle;
import net.esper.schedule.ScheduleHandleCallback;
import net.esper.timer.TimerCallback;
import net.esper.util.ManagedLock;
import net.esper.util.ThreadLogUtil;
import net.esper.util.ExecutionPathDebugLog;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

/**
 * Implements runtime interface. Also accepts timer callbacks for synchronizing time events with regular events
 * sent in.
 */
public class EPRuntimeImpl implements EPRuntime, TimerCallback, InternalEventRouter
{
    private EPServicesContext services;
    private ThreadWorkQueue threadWorkQueue;
    private boolean isHoldInsertStreamLock;

    private ThreadLocal<ArrayBackedCollection<FilterHandle>> matchesArrayThreadLocal = new ThreadLocal<ArrayBackedCollection<FilterHandle>>()
    {
        protected synchronized ArrayBackedCollection<FilterHandle> initialValue()
        {
            return new ArrayBackedCollection<FilterHandle>(100);
        }
    };

    private ThreadLocal<ArrayList<ManagedLock>> locksHeldThreadLocal = new ThreadLocal<ArrayList<ManagedLock>>()
    {
        protected synchronized ArrayList<ManagedLock> initialValue()
        {
            return new ArrayList<ManagedLock>(100);
        }
    };

    private ThreadLocal<HashMap<EPStatementHandle, Object>> matchesPerStmtThreadLocal =
            new ThreadLocal<HashMap<EPStatementHandle, Object>>()
    {
        protected synchronized HashMap<EPStatementHandle, Object> initialValue()
        {
            return new HashMap<EPStatementHandle, Object>(10000);
        }
    };

    private ThreadLocal<ArrayBackedCollection<ScheduleHandle>> scheduleArrayThreadLocal = new ThreadLocal<ArrayBackedCollection<ScheduleHandle>>()
    {
        protected synchronized ArrayBackedCollection<ScheduleHandle> initialValue()
        {
            return new ArrayBackedCollection<ScheduleHandle>(100);
        }
    };

    private ThreadLocal<HashMap<EPStatementHandle, Object>> schedulePerStmtThreadLocal =
            new ThreadLocal<HashMap<EPStatementHandle, Object>>()
    {
        protected synchronized HashMap<EPStatementHandle, Object> initialValue()
        {
            return new HashMap<EPStatementHandle, Object>(10000);
        }
    };

    /**
     * Constructor.
     * @param services - references to services
     */
    public EPRuntimeImpl(EPServicesContext services)
    {
        this.services = services;
        threadWorkQueue = new ThreadWorkQueue();
        isHoldInsertStreamLock = this.services.getEngineSettingsService().getEngineSettings().getThreading().isInsertIntoDispatchPreserveOrder();
    }

    public void timerCallback()
    {
        if ((ExecutionPathDebugLog.isEnabled()) && (log.isDebugEnabled()))
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

        if ((ExecutionPathDebugLog.isEnabled()) && (log.isDebugEnabled()))
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

        if ((ExecutionPathDebugLog.isEnabled()) && (log.isDebugEnabled()))
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

        if ((ExecutionPathDebugLog.isEnabled()) && (log.isDebugEnabled()))
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

    // Internal route of events via insert-into, holds a statement lock
    public void route(EventBean events[], EPStatementHandle epStatementHandle)
    {
        for (int i = 0; i < events.length; i++)
        {
            threadWorkQueue.add(events[i]);
        }

        if (isHoldInsertStreamLock)
        {
            ManagedLock lock = epStatementHandle.getRoutedInsertStreamLock();
            if (!lock.isHeldByCurrentThread())
            {
                lock.acquireLock(services.getStatementLockFactory());
                locksHeldThreadLocal.get().add(lock);
            }
        }
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

        // Acquire main processing lock which locks out statement management
        services.getEventProcessingRWLock().acquireReadLock();
        try
        {
            processMatches(eventBean);
        }
        catch (RuntimeException ex)
        {
            throw new EPException(ex);
        }
        finally
        {
            services.getEventProcessingRWLock().releaseReadLock();
        }

        // Dispatch results to listeners
        // Done outside of the read-lock to prevent lockups when listeners create statements
        dispatch();

        // Work off the event queue if any events accumulated in there via a route() or insert-into
        processThreadWorkQueue();
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

        // Evaluation of all time events is protected from statement management
        if ((ExecutionPathDebugLog.isEnabled()) && (log.isDebugEnabled()))
        {
            log.debug(".processTimeEvent Setting time and evaluating schedules");
        }

        CurrentTimeEvent current = (CurrentTimeEvent) event;
        long currentTime = current.getTimeInMillis();
        services.getSchedulingService().setTime(currentTime);

        processSchedule();

        // Let listeners know of results
        dispatch();

        // Work off the event queue if any events accumulated in there via a route()
        processThreadWorkQueue();
    }

    private void processSchedule()
    {
        ArrayBackedCollection<ScheduleHandle> handles = scheduleArrayThreadLocal.get();

        // Evaluation of schedules is protected by an optional scheduling service lock and then the engine lock
        // We want to stay in this order for allowing the engine lock as a second-order lock to the
        // services own lock, if it has one.
        services.getEventProcessingRWLock().acquireReadLock();
        try
        {
            services.getSchedulingService().evaluate(handles);
        }
        catch (RuntimeException ex)
        {
            throw ex;
        }
        finally
        {
            services.getEventProcessingRWLock().releaseReadLock();
        }

        services.getEventProcessingRWLock().acquireReadLock();
        try
        {
            processScheduleHandles(handles);
        }
        catch (RuntimeException ex)
        {
            throw ex;
        }
        finally
        {
            services.getEventProcessingRWLock().releaseReadLock();
        }
    }

    private void processScheduleHandles(ArrayBackedCollection<ScheduleHandle> handles)
    {
        if (ThreadLogUtil.ENABLED_TRACE)
        {
            ThreadLogUtil.trace("Found schedules for", handles.size());
        }

        if (handles.size() == 0)
        {
            return;
        }

        // handle 1 result separatly for performance reasons
        if (handles.size() == 1)
        {
            Object[] handleArray = handles.getArray();
            EPStatementHandleCallback handle = (EPStatementHandleCallback) handleArray[0];
            ManagedLock statementLock = handle.getEpStatementHandle().getStatementLock();
            statementLock.acquireLock(services.getStatementLockFactory());
            try
            {
                handle.getScheduleCallback().scheduledTrigger(services.getExtensionServicesContext());

                handle.getEpStatementHandle().internalDispatch();
            }
            catch (RuntimeException ex)
            {
                throw ex;
            }
            finally
            {
                handle.getEpStatementHandle().getStatementLock().releaseLock(services.getStatementLockFactory());
            }
            handles.clear();
            return;
        }

        Object[] matchArray = handles.getArray();
        int entryCount = handles.size();

        // sort multiple matches for the event into statements
        HashMap<EPStatementHandle, Object> stmtCallbacks = schedulePerStmtThreadLocal.get();
        stmtCallbacks.clear();
        for (int i = 0; i < entryCount; i++)    // need to use the size of the collection
        {
            EPStatementHandleCallback handleCallback = (EPStatementHandleCallback) matchArray[i];
            EPStatementHandle handle = handleCallback.getEpStatementHandle();
            ScheduleHandleCallback callback = handleCallback.getScheduleCallback();

            Object entry = stmtCallbacks.get(handle);

            // This statement has not been encountered before
            if (entry == null)
            {
                stmtCallbacks.put(handle, callback);
                continue;
            }

            // This statement has been encountered once before
            if (entry instanceof ScheduleHandleCallback)
            {
                ScheduleHandleCallback existingCallback = (ScheduleHandleCallback) entry;
                LinkedList<ScheduleHandleCallback> entries = new LinkedList<ScheduleHandleCallback>();
                entries.add(existingCallback);
                entries.add(callback);
                stmtCallbacks.put(handle, entries);
                continue;
            }

            // This statement has been encountered more then once before
            LinkedList<ScheduleHandleCallback> entries = (LinkedList<ScheduleHandleCallback>) entry;
            entries.add(callback);
        }
        handles.clear();

        for (Map.Entry<EPStatementHandle, Object> entry : stmtCallbacks.entrySet())
        {
            EPStatementHandle handle = entry.getKey();
            Object callbackObject = entry.getValue();

            handle.getStatementLock().acquireLock(services.getStatementLockFactory());
            try
            {
                if (callbackObject instanceof LinkedList)
                {
                    LinkedList<ScheduleHandleCallback> callbackList = (LinkedList<ScheduleHandleCallback>) callbackObject;
                    for (ScheduleHandleCallback callback : callbackList)
                    {
                        callback.scheduledTrigger(services.getExtensionServicesContext());
                    }
                }
                else
                {
                    ScheduleHandleCallback callback = (ScheduleHandleCallback) callbackObject;
                    callback.scheduledTrigger(services.getExtensionServicesContext());
                }

                // internal join processing, if applicable
                handle.internalDispatch();
            }
            catch (RuntimeException ex)
            {
                throw ex;
            }
            finally
            {
                handle.getStatementLock().releaseLock(services.getStatementLockFactory());
            }
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

            services.getEventProcessingRWLock().acquireReadLock();
            try
            {
                processMatches(eventBean);
            }
            catch (RuntimeException ex)
            {
                unlockInsertStreamLocks();
                throw ex;
            }
            finally
            {
                services.getEventProcessingRWLock().releaseReadLock();
            }

            dispatch();
        }

        unlockInsertStreamLocks();
    }

    private void unlockInsertStreamLocks()
    {
        if (!isHoldInsertStreamLock)
        {
            return;
        }
        for (ManagedLock insertStreamLock : locksHeldThreadLocal.get())
        {
            if (insertStreamLock.isHeldByCurrentThread())
            {
                insertStreamLock.releaseLock(services.getStatementLockFactory());
            }
        }
        locksHeldThreadLocal.get().clear();
    }

    private void processMatches(EventBean event)
    {
        // get matching filters
        ArrayBackedCollection<FilterHandle> matches = matchesArrayThreadLocal.get();
        services.getFilterService().evaluate(event, matches);

        if (ThreadLogUtil.ENABLED_TRACE)
        {
            ThreadLogUtil.trace("Found matches for underlying ", matches.size(), event.getUnderlying());
        }

        if (matches.size() == 0)
        {
            return;
        }

        HashMap<EPStatementHandle, Object> stmtCallbacks = matchesPerStmtThreadLocal.get();
        Object[] matchArray = matches.getArray();
        int entryCount = matches.size();

        for (int i = 0; i < entryCount; i++)
        {
            EPStatementHandleCallback handleCallback = (EPStatementHandleCallback) matchArray[i];
            EPStatementHandle handle = handleCallback.getEpStatementHandle();

            // Self-joins require that the internal dispatch happens after all streams are evaluated
            if (handle.isCanSelfJoin())
            {
                List<FilterHandleCallback> callbacks = (List<FilterHandleCallback>) stmtCallbacks.get(handle);
                if (callbacks == null)
                {
                    callbacks = new ArrayList<FilterHandleCallback>();
                    stmtCallbacks.put(handle, callbacks);
                }
                callbacks.add(handleCallback.getFilterCallback());
                continue;
            }

            handle.getStatementLock().acquireLock(services.getStatementLockFactory());
            try
            {
                handleCallback.getFilterCallback().matchFound(event);
                
                // internal join processing, if applicable
                handle.internalDispatch();
            }
            catch (RuntimeException ex)
            {
                unlockInsertStreamLocks();                
                throw ex;
            }
            finally
            {
                handleCallback.getEpStatementHandle().getStatementLock().releaseLock(services.getStatementLockFactory());
            }
        }
        matches.clear();
        if (stmtCallbacks.isEmpty())
        {
            return;
        }

        for (Map.Entry<EPStatementHandle, Object> entry : stmtCallbacks.entrySet())
        {
            EPStatementHandle handle = entry.getKey();
            handle.getStatementLock().acquireLock(services.getStatementLockFactory());
            try
            {
                List<FilterHandleCallback> callbackList = (List<FilterHandleCallback>) entry.getValue();
                for (FilterHandleCallback callback : callbackList)
                {
                    callback.matchFound(event);
                }

                // internal join processing, if applicable
                handle.internalDispatch();
            }
            catch (RuntimeException ex)
            {
                unlockInsertStreamLocks();
                throw ex;
            }
            finally
            {
                handle.getStatementLock().releaseLock(services.getStatementLockFactory());
            }
        }
        stmtCallbacks.clear();
    }

    private void dispatch()
    {
        try
        {
            services.getDispatchService().dispatch();
        }
        catch (RuntimeException ex)
        {
            unlockInsertStreamLocks();
            throw new EPException(ex);
        }
    }

    private static final Log log = LogFactory.getLog(EPRuntimeImpl.class);
}
