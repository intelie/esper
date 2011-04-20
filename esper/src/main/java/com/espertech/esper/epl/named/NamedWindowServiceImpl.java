/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.named;

import com.espertech.esper.client.EPException;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;
import com.espertech.esper.core.*;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;
import com.espertech.esper.epl.expression.ExprValidationException;
import com.espertech.esper.epl.metric.MetricReportingPath;
import com.espertech.esper.epl.metric.MetricReportingService;
import com.espertech.esper.epl.variable.VariableService;
import com.espertech.esper.event.vaevent.ValueAddEventProcessor;
import com.espertech.esper.util.ManagedReadWriteLock;
import com.espertech.esper.util.MetricUtil;
import com.espertech.esper.view.ViewProcessingException;

import java.util.*;

/**
 * This service hold for each named window a dedicated processor and a lock to the named window.
 * This lock is shrared between the named window and on-delete statements.
 */
public class NamedWindowServiceImpl implements NamedWindowService
{
    private final Map<String, NamedWindowProcessor> processors;
    private final Map<String, StatementLock> windowStatementLocks;
    private final StatementLockFactory statementLockFactory;
    private final VariableService variableService;
    private final Set<NamedWindowLifecycleObserver> observers;
    private final ExceptionHandlingService exceptionHandlingService;
    private final boolean isPrioritized;
    private final ManagedReadWriteLock eventProcessingRWLock;
    private final boolean enableQueryPlanLog;
    private final MetricReportingService metricReportingService;

    private ThreadLocal<List<NamedWindowConsumerDispatchUnit>> threadLocal = new ThreadLocal<List<NamedWindowConsumerDispatchUnit>>()
    {
        protected synchronized List<NamedWindowConsumerDispatchUnit> initialValue()
        {
            return new ArrayList<NamedWindowConsumerDispatchUnit>(100);
        }
    };

    private ThreadLocal<Map<EPStatementHandle, Object>> dispatchesPerStmtTL = new ThreadLocal<Map<EPStatementHandle, Object>>()
    {
        protected synchronized Map<EPStatementHandle, Object> initialValue()
        {
            return new HashMap<EPStatementHandle, Object>();
        }
    };

    /**
     * Ctor.
     * @param statementLockFactory statement lock factory
     * @param variableService is for variable access
     * @param isPrioritized if the engine is running with prioritized execution
     */
    public NamedWindowServiceImpl(StatementLockFactory statementLockFactory, VariableService variableService, boolean isPrioritized,
                                  ManagedReadWriteLock eventProcessingRWLock, ExceptionHandlingService exceptionHandlingService, boolean enableQueryPlanLog,
                                  MetricReportingService metricReportingService)
    {
        this.processors = new HashMap<String, NamedWindowProcessor>();
        this.windowStatementLocks = new HashMap<String, StatementLock>();
        this.statementLockFactory = statementLockFactory;
        this.variableService = variableService;
        this.observers = new HashSet<NamedWindowLifecycleObserver>();
        this.isPrioritized = isPrioritized;
        this.eventProcessingRWLock = eventProcessingRWLock;
        this.exceptionHandlingService = exceptionHandlingService;
        this.enableQueryPlanLog = enableQueryPlanLog;
        this.metricReportingService = metricReportingService;
    }

    public void destroy()
    {
        processors.clear();
        threadLocal.remove();
        dispatchesPerStmtTL.remove();
    }

    public String[] getNamedWindows()
    {
        Set<String> names = processors.keySet();
        return names.toArray(new String[names.size()]);
    }

    public StatementLock getNamedWindowLock(String windowName)
    {
        return windowStatementLocks.get(windowName);
    }

    public void addNamedWindowLock(String windowName, StatementLock statementResourceLock)
    {
        windowStatementLocks.put(windowName, statementResourceLock);
    }

    public boolean isNamedWindow(String name)
    {
        return processors.containsKey(name);
    }

    public NamedWindowProcessor getProcessor(String name) throws ExprValidationException
    {
        NamedWindowProcessor processor = processors.get(name);
        if (processor == null)
        {
            throw new ExprValidationException("A named window by name '" + name + "' does not exist");
        }
        return processor;
    }

    public IndexMultiKey[] getNamedWindowIndexes(String windowName) {
        NamedWindowProcessor processor = processors.get(windowName);
        if (processor == null)
        {
            return null;
        }
        return processor.getIndexDescriptors();
    }

    public NamedWindowProcessor addProcessor(String name, EventType eventType, EPStatementHandle createWindowStmtHandle, StatementResultService statementResultService,
                                             ValueAddEventProcessor revisionProcessor, String eplExpression, String statementName, boolean isPrioritized,
                                             ExprEvaluatorContext exprEvaluatorContext, boolean isEnableSubqueryIndexShare) throws ViewProcessingException
    {
        if (processors.containsKey(name))
        {
            throw new ViewProcessingException("A named window by name '" + name + "' has already been created");
        }

        StatementLock statementResourceLock = windowStatementLocks.get(name);
        if (statementResourceLock == null) {
            throw new ViewProcessingException("A lock for named window by name '" + name + "' is not allocated");
        }

        NamedWindowProcessor processor = new NamedWindowProcessor(this, name, eventType, createWindowStmtHandle, statementResultService, revisionProcessor, eplExpression, statementName, isPrioritized, exprEvaluatorContext, statementResourceLock, isEnableSubqueryIndexShare, enableQueryPlanLog, metricReportingService);
        processors.put(name, processor);

        if (!observers.isEmpty())
        {
            NamedWindowLifecycleEvent event = new NamedWindowLifecycleEvent(name, processor, NamedWindowLifecycleEvent.LifecycleEventType.CREATE);
            for (NamedWindowLifecycleObserver observer : observers)
            {
                observer.observe(event);
            }
        }

        return processor;
    }

    public void removeProcessor(String name)
    {
        NamedWindowProcessor processor = processors.get(name);
        if (processor != null)
        {
            processor.destroy();
            processors.remove(name);

            if (!observers.isEmpty())
            {
                NamedWindowLifecycleEvent event = new NamedWindowLifecycleEvent(name, processor, NamedWindowLifecycleEvent.LifecycleEventType.DESTROY);
                for (NamedWindowLifecycleObserver observer : observers)
                {
                    observer.observe(event);
                }
            }
        }
    }

    public void addDispatch(NamedWindowDeltaData delta, Map<EPStatementHandle, List<NamedWindowConsumerView>> consumers)
    {
        NamedWindowConsumerDispatchUnit unit = new NamedWindowConsumerDispatchUnit(delta, consumers);
        threadLocal.get().add(unit);
    }

    public boolean dispatch(ExprEvaluatorContext exprEvaluatorContext)
    {
        List<NamedWindowConsumerDispatchUnit> dispatches = threadLocal.get();
        if (dispatches.isEmpty())
        {
            return false;
        }

        // Acquire main processing lock which locks out statement management
        eventProcessingRWLock.acquireReadLock();
        try
        {
            return processDispatches(exprEvaluatorContext, dispatches);
        }
        catch (RuntimeException ex)
        {
            throw new EPException(ex);
        }
        finally
        {
            eventProcessingRWLock.releaseReadLock();
        }
    }

    private boolean processDispatches(ExprEvaluatorContext exprEvaluatorContext, List<NamedWindowConsumerDispatchUnit> dispatches) {

        if (dispatches.size() == 1)
        {
            NamedWindowConsumerDispatchUnit unit = dispatches.get(0);
            EventBean[] newData = unit.getDeltaData().getNewData();
            EventBean[] oldData = unit.getDeltaData().getOldData();

            if (MetricReportingPath.isMetricsEnabled)
            {
                for (Map.Entry<EPStatementHandle, List<NamedWindowConsumerView>> entry : unit.getDispatchTo().entrySet())
                {
                    EPStatementHandle handle = entry.getKey();
                    if (handle.getMetricsHandle().isEnabled()) {
                        long cpuTimeBefore = MetricUtil.getCPUCurrentThread();
                        long wallTimeBefore = MetricUtil.getWall();

                        processHandle(handle, entry.getValue(), newData, oldData, exprEvaluatorContext);

                        long wallTimeAfter = MetricUtil.getWall();
                        long cpuTimeAfter = MetricUtil.getCPUCurrentThread();
                        long deltaCPU = cpuTimeAfter - cpuTimeBefore;
                        long deltaWall = wallTimeAfter - wallTimeBefore;
                        metricReportingService.accountTime(handle.getMetricsHandle(), deltaCPU, deltaWall, 1);
                    }
                    else {
                        processHandle(handle, entry.getValue(), newData, oldData, exprEvaluatorContext);
                    }

                    if ((isPrioritized) && (handle.isPreemptive()))
                    {
                        break;
                    }
                }
            }
            else {
                for (Map.Entry<EPStatementHandle, List<NamedWindowConsumerView>> entry : unit.getDispatchTo().entrySet())
                {
                    EPStatementHandle handle = entry.getKey();
                    processHandle(handle, entry.getValue(), newData, oldData, exprEvaluatorContext);

                    if ((isPrioritized) && (handle.isPreemptive()))
                    {
                        break;
                    }
                }
            }

            dispatches.clear();
            return true;
        }

        // Multiple different-result dispatches to same or different statements are needed in two situations:
        // a) an event comes in, triggers two insert-into statements inserting into the same named window and the window produces 2 results
        // b) a time batch is grouped in the named window, and a timer fires for both groups at the same time producing more then one result
        // c) two on-merge/update/delete statements fire for the same arriving event each updating the named window

        // Most likely all dispatches go to different statements since most statements are not joins of
        // named windows that produce results at the same time. Therefore sort by statement handle.
        Map<EPStatementHandle, Object> dispatchesPerStmt = dispatchesPerStmtTL.get();
        for (NamedWindowConsumerDispatchUnit unit : dispatches)
        {
            for (Map.Entry<EPStatementHandle, List<NamedWindowConsumerView>> entry : unit.getDispatchTo().entrySet())
            {
                EPStatementHandle handle = entry.getKey();
                Object perStmtObj = dispatchesPerStmt.get(handle);
                if (perStmtObj == null)
                {
                    dispatchesPerStmt.put(handle, unit);
                }
                else if (perStmtObj instanceof List)
                {
                    List<NamedWindowConsumerDispatchUnit> list = (List<NamedWindowConsumerDispatchUnit>) perStmtObj;
                    list.add(unit);
                }
                else    // convert from object to list
                {
                    NamedWindowConsumerDispatchUnit unitObj = (NamedWindowConsumerDispatchUnit) perStmtObj;
                    List<NamedWindowConsumerDispatchUnit> list = new ArrayList<NamedWindowConsumerDispatchUnit>();
                    list.add(unitObj);
                    list.add(unit);
                    dispatchesPerStmt.put(handle, list);
                }
            }
        }

        // Dispatch - with or without metrics reporting
        if (MetricReportingPath.isMetricsEnabled)
        {
            for (Map.Entry<EPStatementHandle, Object> entry : dispatchesPerStmt.entrySet())
            {
                EPStatementHandle handle = entry.getKey();
                Object perStmtObj = entry.getValue();

                // dispatch of a single result to the statement
                if (perStmtObj instanceof NamedWindowConsumerDispatchUnit)
                {
                    NamedWindowConsumerDispatchUnit unit = (NamedWindowConsumerDispatchUnit) perStmtObj;
                    EventBean[] newData = unit.getDeltaData().getNewData();
                    EventBean[] oldData = unit.getDeltaData().getOldData();

                    if (handle.getMetricsHandle().isEnabled()) {
                        long cpuTimeBefore = MetricUtil.getCPUCurrentThread();
                        long wallTimeBefore = MetricUtil.getWall();

                        processHandle(handle, unit.getDispatchTo().get(handle), newData, oldData, exprEvaluatorContext);

                        long wallTimeAfter = MetricUtil.getWall();
                        long cpuTimeAfter = MetricUtil.getCPUCurrentThread();
                        long deltaCPU = cpuTimeAfter - cpuTimeBefore;
                        long deltaWall = wallTimeAfter - wallTimeBefore;
                        metricReportingService.accountTime(handle.getMetricsHandle(), deltaCPU, deltaWall, 1);
                    }
                    else {
                        Map<EPStatementHandle, List<NamedWindowConsumerView>> entries = unit.getDispatchTo();
                    	List<NamedWindowConsumerView> items = entries.get(handle);
                    	if (items != null) {
							processHandle(handle, items, newData, oldData, exprEvaluatorContext);
						}
                    }

                    if ((isPrioritized) && (handle.isPreemptive()))
                    {
                        break;
                    }

                    continue;
                }

                // dispatch of multiple results to a the same statement, need to aggregate per consumer view
                LinkedHashMap<NamedWindowConsumerView, NamedWindowDeltaData> deltaPerConsumer = getDeltaPerConsumer(perStmtObj, handle);
                if (handle.getMetricsHandle().isEnabled()) {
                    long cpuTimeBefore = MetricUtil.getCPUCurrentThread();
                    long wallTimeBefore = MetricUtil.getWall();

                    processHandleMultiple(handle, deltaPerConsumer, exprEvaluatorContext);

                    long wallTimeAfter = MetricUtil.getWall();
                    long cpuTimeAfter = MetricUtil.getCPUCurrentThread();
                    long deltaCPU = cpuTimeAfter - cpuTimeBefore;
                    long deltaWall = wallTimeAfter - wallTimeBefore;
                    metricReportingService.accountTime(handle.getMetricsHandle(), deltaCPU, deltaWall, 1);
                }
                else {
                    processHandleMultiple(handle, deltaPerConsumer, exprEvaluatorContext);
                }

                if ((isPrioritized) && (handle.isPreemptive()))
                {
                    break;
                }
            }
        }
        else {

            for (Map.Entry<EPStatementHandle, Object> entry : dispatchesPerStmt.entrySet())
            {
                EPStatementHandle handle = entry.getKey();
                Object perStmtObj = entry.getValue();

                // dispatch of a single result to the statement
                if (perStmtObj instanceof NamedWindowConsumerDispatchUnit)
                {
                    NamedWindowConsumerDispatchUnit unit = (NamedWindowConsumerDispatchUnit) perStmtObj;
                    EventBean[] newData = unit.getDeltaData().getNewData();
                    EventBean[] oldData = unit.getDeltaData().getOldData();

                    processHandle(handle, unit.getDispatchTo().get(handle), newData, oldData, exprEvaluatorContext);

                    if ((isPrioritized) && (handle.isPreemptive()))
                    {
                        break;
                    }

                    continue;
                }

                // dispatch of multiple results to a the same statement, need to aggregate per consumer view
                LinkedHashMap<NamedWindowConsumerView, NamedWindowDeltaData> deltaPerConsumer = getDeltaPerConsumer(perStmtObj, handle);
                processHandleMultiple(handle, deltaPerConsumer, exprEvaluatorContext);

                if ((isPrioritized) && (handle.isPreemptive()))
                {
                    break;
                }
            }
        }

        dispatches.clear();
        dispatchesPerStmt.clear();

        return true;
    }

    private void processHandleMultiple(EPStatementHandle handle, Map<NamedWindowConsumerView, NamedWindowDeltaData> deltaPerConsumer, ExprEvaluatorContext exprEvaluatorContext) {
        handle.getStatementLock().acquireWriteLock(statementLockFactory);
        try
        {
            if (handle.isHasVariables())
            {
                variableService.setLocalVersion();
            }
            for (Map.Entry<NamedWindowConsumerView, NamedWindowDeltaData> entryDelta : deltaPerConsumer.entrySet())
            {
                EventBean[] newData = entryDelta.getValue().getNewData();
                EventBean[] oldData = entryDelta.getValue().getOldData();
                entryDelta.getKey().update(newData, oldData);
            }

            // internal join processing, if applicable
            handle.internalDispatch(exprEvaluatorContext);
        }
        catch (RuntimeException ex) {
            exceptionHandlingService.handleException(ex, handle);
        }
        finally
        {
            handle.getStatementLock().releaseWriteLock(null);
        }
    }

    private void processHandle(EPStatementHandle handle, List<NamedWindowConsumerView> value, EventBean[] newData, EventBean[] oldData, ExprEvaluatorContext exprEvaluatorContext) {
        handle.getStatementLock().acquireWriteLock(statementLockFactory);
        try
        {
            if (handle.isHasVariables())
            {
                variableService.setLocalVersion();
            }

            for (NamedWindowConsumerView consumerView : value)
            {
                consumerView.update(newData, oldData);
            }

            // internal join processing, if applicable
            handle.internalDispatch(exprEvaluatorContext);
        }
        catch (RuntimeException ex) {
            exceptionHandlingService.handleException(ex, handle);
        }
        finally
        {
            handle.getStatementLock().releaseWriteLock(null);
        }
    }

    public void addObserver(NamedWindowLifecycleObserver observer)
    {
        observers.add(observer);
    }

    public void removeObserver(NamedWindowLifecycleObserver observer)
    {
        observers.remove(observer);
    }

    public LinkedHashMap<NamedWindowConsumerView, NamedWindowDeltaData> getDeltaPerConsumer(Object perStmtObj, EPStatementHandle handle) {
        List<NamedWindowConsumerDispatchUnit> list = (List<NamedWindowConsumerDispatchUnit>) perStmtObj;
        LinkedHashMap<NamedWindowConsumerView, NamedWindowDeltaData> deltaPerConsumer = new LinkedHashMap<NamedWindowConsumerView, NamedWindowDeltaData>();
        for (NamedWindowConsumerDispatchUnit unit : list)   // for each unit
        {
            for (NamedWindowConsumerView consumerView : unit.getDispatchTo().get(handle))   // each consumer
            {
                NamedWindowDeltaData deltaForConsumer = deltaPerConsumer.get(consumerView);
                if (deltaForConsumer == null)
                {
                    deltaPerConsumer.put(consumerView, unit.getDeltaData());
                }
                else
                {
                    NamedWindowDeltaData aggregated = new NamedWindowDeltaData(deltaForConsumer, unit.getDeltaData());
                    deltaPerConsumer.put(consumerView, aggregated);
                }
            }
        }
        return deltaPerConsumer;
    }
}
