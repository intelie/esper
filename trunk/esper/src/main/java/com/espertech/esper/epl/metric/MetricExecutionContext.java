package com.espertech.esper.epl.metric;

import com.espertech.esper.core.EPServicesContext;

public class MetricExecutionContext
{
    private final EPServicesContext epServicesContext;

    public MetricExecutionContext(EPServicesContext epServicesContext)
    {
        this.epServicesContext = epServicesContext;
    }

    public EPServicesContext getServices()
    {
        return epServicesContext;
    }
}
