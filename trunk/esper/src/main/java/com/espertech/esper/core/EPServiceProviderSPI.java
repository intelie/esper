/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.core;

import com.espertech.esper.event.EventAdapterService;
import com.espertech.esper.schedule.SchedulingService;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.filter.FilterService;

import javax.naming.Context;

/**
 * A service provider interface that makes available internal engine services.
 */
public interface EPServiceProviderSPI extends EPServiceProvider
{
    /**
     * Returns statement management service for the engine.
     * @return the StatementLifecycleSvc
     */
    public StatementLifecycleSvc getStatementLifecycleSvc();

    /**
     * Get the EventAdapterService for this engine.
     * @return the EventAdapterService
     */
    public EventAdapterService getEventAdapterService();

    /**
     * Get the SchedulingService for this engine.
     * @return the SchedulingService
     */
    public SchedulingService getSchedulingService();

    /**
     * Returns the filter service.
     * @return filter service
     */
    public FilterService getFilterService();

    /**
     * Returns the engine environment context for engine-external resources such as adapters.
     * @return engine environment context
     */
    public Context getContext();

    /**
     * Returns the extension services context.
     * @return extension services context
     */
    public ExtensionServicesContext getExtensionServicesContext();
}
