package com.espertech.esper.core;

import com.espertech.esper.client.EPServiceProviderIsolated;
import com.espertech.esper.filter.FilterServiceSPI;
import com.espertech.esper.filter.FilterServiceProvider;
import com.espertech.esper.schedule.SchedulingServiceSPI;
import com.espertech.esper.schedule.SchedulingServiceImpl;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class StatementIsolationServiceImpl implements StatementIsolationService
{
    private final Map<String, EPServiceProviderIsolatedImpl> isolatedProviders;
    private EPServicesContext epServicesContext;

    public StatementIsolationServiceImpl()
    {
        isolatedProviders = new ConcurrentHashMap<String, EPServiceProviderIsolatedImpl>();
    }

    public void setEpServicesContext(EPServicesContext epServicesContext)
    {
        this.epServicesContext = epServicesContext;
    }

    public EPServiceProviderIsolated getIsolationUnit(String name)
    {
        EPServiceProviderIsolatedImpl serviceProviderIsolated = isolatedProviders.get(name);
        if (serviceProviderIsolated != null)
        {
            return serviceProviderIsolated;
        }

        FilterServiceSPI filterService = FilterServiceProvider.newService();
        SchedulingServiceSPI scheduleService = new SchedulingServiceImpl(epServicesContext.getTimeSource());
        EPIsolationUnitServices services = new EPIsolationUnitServices(filterService, scheduleService);
        serviceProviderIsolated = new EPServiceProviderIsolatedImpl(name, services, epServicesContext, isolatedProviders);
        isolatedProviders.put(name, serviceProviderIsolated);
        return serviceProviderIsolated;
    }

    public void destroy()
    {
        isolatedProviders.clear();        
    }

    public String[] getIsolationUnitNames()
    {
        Set<String> keyset = isolatedProviders.keySet();
        return keyset.toArray(new String[keyset.size()]);        
    }
}