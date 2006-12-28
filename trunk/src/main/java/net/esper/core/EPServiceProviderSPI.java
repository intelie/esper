package net.esper.core;

import net.esper.event.EventAdapterService;
import net.esper.schedule.SchedulingService;
import net.esper.client.EPServiceProvider;
import net.esper.adapter.OutputAdapterService;
import net.esper.filter.FilterService;

/**
 * A service provider interface that makes available internal engine services.
 */
public interface EPServiceProviderSPI extends EPServiceProvider
{
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

    public FilterService getFilterService();    

    public void setOuputAdapterService(OutputAdapterService outputAdapterService);

}
