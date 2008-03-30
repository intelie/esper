package com.espertech.esper.core;

import com.espertech.esper.client.EPQueryResult;
import com.espertech.esper.event.EventBean;

public interface EPQueryResultSPI extends EPQueryResult
{
    public EventBean[] getArray();
}
