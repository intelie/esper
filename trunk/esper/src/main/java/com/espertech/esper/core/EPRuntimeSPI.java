package com.espertech.esper.core;

import com.espertech.esper.client.EPRuntime;

public interface EPRuntimeSPI extends EPRuntime
{
    public EPQueryResult executeQuery(String epl);
    public EPPreparedQuery prepareQuery(String epl);
}
