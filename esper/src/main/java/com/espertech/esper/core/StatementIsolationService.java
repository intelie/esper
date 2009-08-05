package com.espertech.esper.core;

import com.espertech.esper.client.EPServiceProviderIsolated;

public interface StatementIsolationService
{
    public  EPServiceProviderIsolated getIsolationUnit(String name);

    public void destroy();

    public String[] getIsolationUnitNames();

}
