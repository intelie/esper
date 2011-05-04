package com.espertech.esper.core;

import com.espertech.esper.client.EventBean;

public interface InsertIntoListener {
    public void inserted(EventBean event, EPStatementHandle statementHandle);
}
