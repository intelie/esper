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

public class EPIsolationUnitServices
{
    private final FilterServiceSPI filterService;
    private final SchedulingServiceSPI schedulingService;

    public EPIsolationUnitServices(FilterServiceSPI filterService, SchedulingServiceSPI schedulingService) {
        this.filterService = filterService;
        this.schedulingService = schedulingService;
    }

    public FilterServiceSPI getFilterService() {
        return filterService;
    }

    public SchedulingServiceSPI getSchedulingService() {
        return schedulingService;
    }
}