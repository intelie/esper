/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.core;

import com.espertech.esper.client.ConfigurationInformation;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.event.EventAdapterService;
import com.espertech.esper.filter.FilterService;
import com.espertech.esper.schedule.SchedulingService;
import com.espertech.esper.timer.TimerService;
import com.espertech.esper.epl.named.NamedWindowService;
import com.espertech.esper.epl.metric.MetricReportingService;

import javax.naming.Context;

/**
 * A service provider interface that makes available internal engine services.
 */
public interface EPServiceProviderSPI extends EPServiceProvider
{
    /**
     * For the default provider instance, which carries a null provider URI,
     * the property name qualification and stream name qualification may use "default".
     */
    public static final String DEFAULT_ENGINE_URI__QUALIFIER = "default";

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
     * Returns the timer service.
     * @return timer service
     */
    public TimerService getTimerService();

    /**
     * Returns the named window service.
     * @return named window service
     */
    public NamedWindowService getNamedWindowService(); 

    /**
     * Returns the current configuration.
     * @return configuration information 
     */
    public ConfigurationInformation getConfigurationInformation();

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

    /**
     * Returns metrics reporting.
     * @return metrics reporting
     */
    public MetricReportingService getMetricReportingService();
}
