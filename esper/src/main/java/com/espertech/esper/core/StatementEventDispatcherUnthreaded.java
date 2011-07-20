/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.core;

import com.espertech.esper.client.EPStatementStateListener;
import com.espertech.esper.client.EPServiceProvider;

import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Dispatcher for statement lifecycle events to service provider statement state listeners.
 */
public class StatementEventDispatcherUnthreaded implements StatementLifecycleObserver
{
    private static Log log = LogFactory.getLog(StatementEventDispatcherUnthreaded.class);
    private final EPServiceProvider serviceProvider;
    private final Iterable<EPStatementStateListener> statementListeners;

    /**
     * Ctor.
     * @param serviceProvider engine instance
     * @param statementListeners listeners to dispatch to
     */
    public StatementEventDispatcherUnthreaded(EPServiceProvider serviceProvider, Iterable<EPStatementStateListener> statementListeners)
    {
        this.serviceProvider = serviceProvider;
        this.statementListeners = statementListeners;
    }

    public void observe(StatementLifecycleEvent event)
    {
        if (event.getEventType() == StatementLifecycleEvent.LifecycleEventType.CREATE)
        {
            Iterator<EPStatementStateListener> it = statementListeners.iterator();
            for (;it.hasNext();)
            {
                try
                {
                    it.next().onStatementCreate(serviceProvider, event.getStatement());
                }
                catch (RuntimeException ex)
                {
                    log.error("Caught runtime exception in onStatementCreate callback:" + ex.getMessage(), ex);
                }
            }
        }
        else if (event.getEventType() == StatementLifecycleEvent.LifecycleEventType.STATECHANGE)
        {
            Iterator<EPStatementStateListener> it = statementListeners.iterator();
            for (;it.hasNext();)
            {
                try
                {
                    it.next().onStatementStateChange(serviceProvider, event.getStatement());
                }
                catch (RuntimeException ex)
                {
                    log.error("Caught runtime exception in onStatementCreate callback:" + ex.getMessage(), ex);
                }
            }
        }
    }
}
