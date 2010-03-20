package com.espertech.esper.core;

import com.espertech.esper.dispatch.DispatchService;
import com.espertech.esper.epl.named.NamedWindowService;
import com.espertech.esper.epl.variable.VariableService;
import com.espertech.esper.event.EventAdapterService;
import com.espertech.esper.filter.FilterService;
import com.espertech.esper.filter.FilterServiceSPI;
import com.espertech.esper.schedule.SchedulingServiceImpl;
import com.espertech.esper.schedule.SchedulingService;
import com.espertech.esper.schedule.SchedulingServiceSPI;
import com.espertech.esper.util.ManagedReadWriteLock;

/**
 * Context for all services that provide the isolated runtime.
 */
public class EPIsolationUnitServices
{
    private final String name;
    private final int unitId;
    private final FilterServiceSPI filterService;
    private final SchedulingServiceSPI schedulingService;

    /**
     * Ctor.
     * @param name the isolation unit name
     * @param unitId id of the isolation unit
     * @param filterService isolated filter service
     * @param schedulingService isolated scheduling service
     */
    public EPIsolationUnitServices(String name, int unitId, FilterServiceSPI filterService, SchedulingServiceSPI schedulingService) {
        this.name = name;
        this.unitId = unitId;
        this.filterService = filterService;
        this.schedulingService = schedulingService;
    }

    /**
     * Returns the name of the isolated service.
     * @return name of the isolated service
     */
    public String getName()
    {
        return name;
    }

    /**
     * Returns the id assigned to that isolated service.
     * @return isolated service id
     */
    public int getUnitId()
    {
        return unitId;
    }

    /**
     * Returns the isolated filter service.
     * @return filter service
     */
    public FilterServiceSPI getFilterService() {
        return filterService;
    }

    /**
     * Returns the isolated scheduling service.
     * @return scheduling service
     */
    public SchedulingServiceSPI getSchedulingService() {
        return schedulingService;
    }
}