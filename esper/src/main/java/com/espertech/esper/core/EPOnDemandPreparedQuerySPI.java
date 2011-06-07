package com.espertech.esper.core;

import com.espertech.esper.client.EPOnDemandPreparedQuery;

public interface EPOnDemandPreparedQuerySPI extends EPOnDemandPreparedQuery {
    public EPPreparedExecuteMethod getExecuteMethod();
}
