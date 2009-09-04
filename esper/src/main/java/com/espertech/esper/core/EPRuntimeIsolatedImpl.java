package com.espertech.esper.core;

import com.espertech.esper.client.EPException;
import com.espertech.esper.client.EPRuntimeIsolated;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.time.CurrentTimeEvent;
import com.espertech.esper.client.time.TimerEvent;
import com.espertech.esper.collection.ArrayBackedCollection;
import com.espertech.esper.collection.ArrayDequeJDK6Backport;
import com.espertech.esper.collection.ThreadWorkQueue;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;
import com.espertech.esper.filter.FilterHandle;
import com.espertech.esper.filter.FilterHandleCallback;
import com.espertech.esper.schedule.ScheduleHandle;
import com.espertech.esper.schedule.ScheduleHandleCallback;
import com.espertech.esper.schedule.TimeProvider;
import com.espertech.esper.util.ExecutionPathDebugLog;
import com.espertech.esper.util.ThreadLogUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Implementation for isolated runtime.
 */
public class EPRuntimeIsolatedImpl implements EPRuntimeIsolated, InternalEventRouteDest
{
    private EPServicesContext unisolatedServices;
    private EPIsolationUnitServices services;
    private boolean isSubselectPreeval;
    private boolean isPrioritized;
    private boolean isLatchStatementInsertStream;
    private ExprEvaluatorContext isolatedTimeEvalContext;

    private ThreadLocal<Map<EPStatementHandle, ArrayDequeJDK6Backport<FilterHandleCallback>>> matchesPerStmtThreadLocal;
    private ThreadLocal<Map<EPStatementHandle, Object>> schedulePerStmtThreadLocal;

    /**
     * Ctor.
     * @param svc isolated services
     * @param unisolatedSvc engine services
     */
    public EPRuntimeIsolatedImpl(EPIsolationUnitServices svc, EPServicesContext unisolatedSvc)
    {
        this.services = svc;
        this.unisolatedServices = unisolatedSvc;
        isSubselectPreeval = unisolatedSvc.getEngineSettingsService().getEngineSettings().getExpression().isSelfSubselectPreeval();
        isPrioritized = unisolatedSvc.getEngineSettingsService().getEngineSettings().getExecution().isPrioritized();
        isLatchStatementInsertStream = unisolatedSvc.getEngineSettingsService().getEngineSettings().getThreading().isInsertIntoDispatchPreserveOrder();

        isolatedTimeEvalContext = new ExprEvaluatorContext()
        {
            public TimeProvider getTimeProvider()
            {
                return services.getSchedulingService();
            }
        };

        matchesPerStmtThreadLocal =
            new ThreadLocal<Map<EPStatementHandle, ArrayDequeJDK6Backport<FilterHandleCallback>>>()
            {
                protected synchronized Map<EPStatementHandle, ArrayDequeJDK6Backport<FilterHandleCallback>> initialValue()
                {
                    if (isPrioritized)
                    {
                        return new TreeMap<EPStatementHandle, ArrayDequeJDK6Backport<FilterHandleCallback>>(new Comparator<EPStatementHandle>()
                        {
                            // sorted descending order
                            public int compare(EPStatementHandle o1, EPStatementHandle o2)
                            {
                                if (o1.getPriority() == o2.getPriority())
                                {
                                    return 0;
                                }
                                return o1.getPriority() > o2.getPriority() ? -1 : 1;
                            }
                        });
                    }
                    else
                    {
                        return new HashMap<EPStatementHandle, ArrayDequeJDK6Backport<FilterHandleCallback>>(10000);
                    }
                }
            };

        schedulePerStmtThreadLocal = new ThreadLocal<Map<EPStatementHandle, Object>>()
            {
                protected synchronized Map<EPStatementHandle, Object> initialValue()
                {
                    if (isPrioritized)
                    {
                        return new TreeMap<EPStatementHandle, Object>(new Comparator<EPStatementHandle>()
                        {
                            // sorted descending order
                            public int compare(EPStatementHandle o1, EPStatementHandle o2)
                            {
                                if (o1.getPriority() == o2.getPriority())
                                {
                                    return 0;
                                }
                                return o1.getPriority() > o2.getPriority() ? -1 : 1;
                            }
                        });
                    }
                    else
                    {
                        return new HashMap<EPStatementHandle, Object>(10000);
                    }
                }
            };
    }

    private ThreadLocal<ArrayBackedCollection<FilterHandle>> matchesArrayThreadLocal = new ThreadLocal<ArrayBackedCollection<FilterHandle>>()
    {
        protected synchronized ArrayBackedCollection<FilterHandle> initialValue()
        {
            return new ArrayBackedCollection<FilterHandle>(100);
        }
    };

    private ThreadLocal<ArrayBackedCollection<ScheduleHandle>> scheduleArrayThreadLocal = new ThreadLocal<ArrayBackedCollection<ScheduleHandle>>()
    {
        protected synchronized ArrayBackedCollection<ScheduleHandle> initialValue()
        {
            return new ArrayBackedCollection<ScheduleHandle>(100);
        }
    };

    public void sendEvent(Object event) throws EPException
    {
        if (event == null)
        {
            log.fatal(".sendEvent Null object supplied");
            return;
        }

        if ((ExecutionPathDebugLog.isDebugEnabled) && (log.isDebugEnabled()))
        {
            if ((!(event instanceof CurrentTimeEvent)) || (ExecutionPathDebugLog.isTimerDebugEnabled))
            {
                log.debug(".sendEvent Processing event " + event);
            }
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

        if ((ExecutionPathDebugLog.isDebugEnabled) && (log.isDebugEnabled()))
        {
            log.debug(".sendEvent Processing DOM node event " + document);
        }

        // Get it wrapped up, process event
        EventBean eventBean = unisolatedServices.getEventAdapterService().adapterForDOM(document);
        processEvent(eventBean);
    }

    /**
     * Route a XML docment event
     * @param document to route
     * @throws EPException if routing failed
     */
    public void route(org.w3c.dom.Node document) throws EPException
    {
        if (document == null)
        {
            log.fatal(".sendEvent Null object supplied");
            return;
        }

        if ((ExecutionPathDebugLog.isDebugEnabled) && (log.isDebugEnabled()))
        {
            log.debug(".sendEvent Processing DOM node event " + document);
        }

        // Get it wrapped up, process event
        EventBean eventBean = unisolatedServices.getEventAdapterService().adapterForDOM(document);
        ThreadWorkQueue.add(eventBean);
    }

    public void sendEvent(Map map, String eventTypeName) throws EPException
    {
        if (map == null)
        {
            throw new IllegalArgumentException("Invalid null event object");
        }

        if ((ExecutionPathDebugLog.isDebugEnabled) && (log.isDebugEnabled()))
        {
            log.debug(".sendMap Processing event " + map);
        }

        // Process event
        EventBean eventBean = unisolatedServices.getEventAdapterService().adapterForMap(map, eventTypeName);
        processWrappedEvent(eventBean);
    }

    /**
     * Process an unwrapped event.
     * @param event to process.
     */
    public void processEvent(Object event)
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
            eventBean = unisolatedServices.getEventAdapterService().adapterForBean(event);
        }

        processWrappedEvent(eventBean);
    }

    /**
     * Process a wrapped event.
     * @param eventBean to process
     */
    public void processWrappedEvent(EventBean eventBean)
    {
        // Acquire main processing lock which locks out statement management
        unisolatedServices.getEventProcessingRWLock().acquireReadLock();
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
            unisolatedServices.getEventProcessingRWLock().releaseReadLock();
        }

        // Dispatch results to listeners
        // Done outside of the read-lock to prevent lockups when listeners create statements
        dispatch();

        // Work off the event queue if any events accumulated in there via a route() or insert-into
        processThreadWorkQueue();
    }

    private void processTimeEvent(TimerEvent event)
    {
        // Evaluation of all time events is protected from statement management
        if ((ExecutionPathDebugLog.isDebugEnabled) && (log.isDebugEnabled()) && (ExecutionPathDebugLog.isTimerDebugEnabled))
        {
            log.debug(".processTimeEvent Setting time and evaluating schedules");
        }

        CurrentTimeEvent current = (CurrentTimeEvent) event;
        long currentTime = current.getTimeInMillis();

        if (currentTime == services.getSchedulingService().getTime())
        {
            if (log.isWarnEnabled())
            {
                log.warn("Duplicate time event received for currentTime " + currentTime);
            }
        }
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
        unisolatedServices.getEventProcessingRWLock().acquireReadLock();
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
            unisolatedServices.getEventProcessingRWLock().releaseReadLock();
        }

        unisolatedServices.getEventProcessingRWLock().acquireReadLock();
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
            unisolatedServices.getEventProcessingRWLock().releaseReadLock();
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

            EPRuntimeImpl.processStatementScheduleSingle(handle, unisolatedServices, isolatedTimeEvalContext);

            handles.clear();
            return;
        }

        Object[] matchArray = handles.getArray();
        int entryCount = handles.size();

        // sort multiple matches for the event into statements
        Map<EPStatementHandle, Object> stmtCallbacks = schedulePerStmtThreadLocal.get();
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
                ArrayDequeJDK6Backport<ScheduleHandleCallback> entries = new ArrayDequeJDK6Backport<ScheduleHandleCallback>();
                entries.add(existingCallback);
                entries.add(callback);
                stmtCallbacks.put(handle, entries);
                continue;
            }

            // This statement has been encountered more then once before
            ArrayDequeJDK6Backport<ScheduleHandleCallback> entries = (ArrayDequeJDK6Backport<ScheduleHandleCallback>) entry;
            entries.add(callback);
        }
        handles.clear();

        for (Map.Entry<EPStatementHandle, Object> entry : stmtCallbacks.entrySet())
        {
            EPStatementHandle handle = entry.getKey();
            Object callbackObject = entry.getValue();

            EPRuntimeImpl.processStatementScheduleMultiple(handle, callbackObject, unisolatedServices, isolatedTimeEvalContext);

            if ((isPrioritized) && (handle.isPreemptive()))
            {
                break;
            }
        }
    }

    /**
     * Works off the thread's work queue.
     */
    public void processThreadWorkQueue()
    {
        Object item;
        while ( (item = ThreadWorkQueue.next()) != null)
        {
            if (item instanceof InsertIntoLatchSpin)
            {
                processThreadWorkQueueLatchedSpin((InsertIntoLatchSpin) item);
            }
            else if (item instanceof InsertIntoLatchWait)
            {
                processThreadWorkQueueLatchedWait((InsertIntoLatchWait) item);
            }
            else
            {
                processThreadWorkQueueUnlatched(item);
            }
        }

        // Process named window deltas
        boolean haveDispatched = unisolatedServices.getNamedWindowService().dispatch(isolatedTimeEvalContext);
        if (haveDispatched)
        {
            // Dispatch results to listeners
            dispatch();
        }

        if (!(ThreadWorkQueue.isEmpty()))
        {
            processThreadWorkQueue();
        }
    }

    private void processThreadWorkQueueLatchedWait(InsertIntoLatchWait insertIntoLatch)
    {
        // wait for the latch to complete
        Object item = insertIntoLatch.await();

        EventBean eventBean;
        if (item instanceof EventBean)
        {
            eventBean = (EventBean) item;
        }
        else
        {
            eventBean = unisolatedServices.getEventAdapterService().adapterForBean(item);
        }

        unisolatedServices.getEventProcessingRWLock().acquireReadLock();
        try
        {
            processMatches(eventBean);
        }
        catch (RuntimeException ex)
        {
            throw ex;
        }
        finally
        {
            insertIntoLatch.done();
            unisolatedServices.getEventProcessingRWLock().releaseReadLock();
        }

        dispatch();
    }

    private void processThreadWorkQueueLatchedSpin(InsertIntoLatchSpin insertIntoLatch)
    {
        // wait for the latch to complete
        Object item = insertIntoLatch.await();

        EventBean eventBean;
        if (item instanceof EventBean)
        {
            eventBean = (EventBean) item;
        }
        else
        {
            eventBean = unisolatedServices.getEventAdapterService().adapterForBean(item);
        }

        unisolatedServices.getEventProcessingRWLock().acquireReadLock();
        try
        {
            processMatches(eventBean);
        }
        catch (RuntimeException ex)
        {
            throw ex;
        }
        finally
        {
            insertIntoLatch.done();
            unisolatedServices.getEventProcessingRWLock().releaseReadLock();
        }

        dispatch();
    }

    private void processThreadWorkQueueUnlatched(Object item)
    {
        EventBean eventBean;
        if (item instanceof EventBean)
        {
            eventBean = (EventBean) item;
        }
        else
        {
            eventBean = unisolatedServices.getEventAdapterService().adapterForBean(item);
        }

        unisolatedServices.getEventProcessingRWLock().acquireReadLock();
        try
        {
            processMatches(eventBean);
        }
        catch (RuntimeException ex)
        {
            throw ex;
        }
        finally
        {
            unisolatedServices.getEventProcessingRWLock().releaseReadLock();
        }

        dispatch();
    }

    private void processMatches(EventBean event)
    {
        // get matching filters
        ArrayBackedCollection<FilterHandle> matches = matchesArrayThreadLocal.get();
        services.getFilterService().evaluate(event, matches, isolatedTimeEvalContext);

        if (ThreadLogUtil.ENABLED_TRACE)
        {
            ThreadLogUtil.trace("Found matches for underlying ", matches.size(), event.getUnderlying());
        }

        if (matches.size() == 0)
        {
            return;
        }

        Map<EPStatementHandle, ArrayDequeJDK6Backport<FilterHandleCallback>> stmtCallbacks = matchesPerStmtThreadLocal.get();
        Object[] matchArray = matches.getArray();
        int entryCount = matches.size();

        for (int i = 0; i < entryCount; i++)
        {
            EPStatementHandleCallback handleCallback = (EPStatementHandleCallback) matchArray[i];
            EPStatementHandle handle = handleCallback.getEpStatementHandle();

            // Self-joins require that the internal dispatch happens after all streams are evaluated.
            // Priority or preemptive settings also require special ordering.
            if (handle.isCanSelfJoin() || isPrioritized)
            {
                ArrayDequeJDK6Backport<FilterHandleCallback> callbacks = stmtCallbacks.get(handle);
                if (callbacks == null)
                {
                    callbacks = new ArrayDequeJDK6Backport<FilterHandleCallback>();
                    stmtCallbacks.put(handle, callbacks);
                }
                callbacks.add(handleCallback.getFilterCallback());
                continue;
            }

            processStatementFilterSingle(handle, handleCallback, event);
        }
        matches.clear();
        if (stmtCallbacks.isEmpty())
        {
            return;
        }

        for (Map.Entry<EPStatementHandle, ArrayDequeJDK6Backport<FilterHandleCallback>> entry : stmtCallbacks.entrySet())
        {
            EPStatementHandle handle = entry.getKey();
            ArrayDequeJDK6Backport<FilterHandleCallback> callbackList = entry.getValue();

            processStatementFilterMultiple(handle, callbackList, event);

            if ((isPrioritized) && (handle.isPreemptive()))
            {
                break;
            }
        }
        stmtCallbacks.clear();
    }

    /**
     * Processing multiple filter matches for a statement.
     * @param handle statement handle
     * @param callbackList object containing callbacks
     * @param event to process
     */
    public void processStatementFilterMultiple(EPStatementHandle handle, ArrayDequeJDK6Backport<FilterHandleCallback> callbackList, EventBean event)
    {
        handle.getStatementLock().acquireLock(unisolatedServices.getStatementLockFactory());
        try
        {
            if (handle.isHasVariables())
            {
                unisolatedServices.getVariableService().setLocalVersion();
            }

            if (isSubselectPreeval)
            {
                // sub-selects always go first
                for (FilterHandleCallback callback : callbackList)
                {
                    if (callback.isSubSelect())
                    {
                        callback.matchFound(event);
                    }
                }

                for (FilterHandleCallback callback : callbackList)
                {
                    if (!callback.isSubSelect())
                    {
                        callback.matchFound(event);
                    }
                }
            }
            else
            {
                // sub-selects always go last
                for (FilterHandleCallback callback : callbackList)
                {
                    if (!callback.isSubSelect())
                    {
                        callback.matchFound(event);
                    }
                }

                for (FilterHandleCallback callback : callbackList)
                {
                    if (callback.isSubSelect())
                    {
                        callback.matchFound(event);
                    }
                }
            }

            // internal join processing, if applicable
            handle.internalDispatch(isolatedTimeEvalContext);
        }
        catch (RuntimeException ex)
        {
            throw ex;
        }
        finally
        {
            handle.getStatementLock().releaseLock(unisolatedServices.getStatementLockFactory());
        }
    }

    /**
     * Process a single match.
     * @param handle statement
     * @param handleCallback callback
     * @param event event to indicate
     */
    public void processStatementFilterSingle(EPStatementHandle handle, EPStatementHandleCallback handleCallback, EventBean event)
    {
        handle.getStatementLock().acquireLock(unisolatedServices.getStatementLockFactory());
        try
        {
            if (handle.isHasVariables())
            {
                unisolatedServices.getVariableService().setLocalVersion();
            }

            handleCallback.getFilterCallback().matchFound(event);

            // internal join processing, if applicable
            handle.internalDispatch(isolatedTimeEvalContext);
        }
        catch (RuntimeException ex)
        {
            throw ex;
        }
        finally
        {
            handleCallback.getEpStatementHandle().getStatementLock().releaseLock(unisolatedServices.getStatementLockFactory());
        }
    }

    /**
     * Dispatch events.
     */
    public void dispatch()
    {
        try
        {
            unisolatedServices.getDispatchService().dispatch();
        }
        catch (RuntimeException ex)
        {
            throw new EPException(ex);
        }
    }

    /**
     * Destroy for destroying an engine instance: sets references to null and clears thread-locals
     */
    public void destroy()
    {
        services = null;

        matchesArrayThreadLocal.remove();
        matchesPerStmtThreadLocal.remove();
        scheduleArrayThreadLocal.remove();
        schedulePerStmtThreadLocal.remove();

        matchesArrayThreadLocal = null;
        matchesPerStmtThreadLocal = null;
        scheduleArrayThreadLocal = null;
        schedulePerStmtThreadLocal = null;
    }

    public long getCurrentTime() {
        return services.getSchedulingService().getTime();
    }

    // Internal route of events via insert-into, holds a statement lock
    public void route(EventBean event, EPStatementHandle epStatementHandle)
    {
        if (isLatchStatementInsertStream)
        {
            InsertIntoLatchFactory insertIntoLatchFactory = epStatementHandle.getInsertIntoLatchFactory();
            Object latch = insertIntoLatchFactory.newLatch(event);
            ThreadWorkQueue.add(latch);
        }
        else
        {
            ThreadWorkQueue.add(event);
        }
    }

    private static final Log log = LogFactory.getLog(EPRuntimeImpl.class);
}
