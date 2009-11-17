package com.espertech.esper.core;

import com.espertech.esper.client.EventBean;

public interface StatementResultListener {
    public void update(EventBean[] newEvents, EventBean[] oldEvents, String statementName, EPStatementSPI statement, EPServiceProviderSPI epServiceProvider);
}
