package com.espertech.esper.core;

import com.espertech.esper.core.EPQueryResult;
import com.espertech.esper.client.EPStatementException;
import com.espertech.esper.core.EPPreparedQuery;
import com.espertech.esper.event.EventType;

public class EPPreparedQueryImpl implements EPPreparedQuery
{
    private final EPPreparedExecuteMethod executeMethod;
    private final String epl;

    public EPPreparedQueryImpl(EPPreparedExecuteMethod executeMethod, String epl)
    {
        this.executeMethod = executeMethod;
        this.epl = epl;
    }

    public EPQueryResult execute()
    {
        try
        {
            EPPreparedQueryResult result = executeMethod.execute();
            return new EPQueryResultImpl(result);
        }
        catch (EPStatementException ex)
        {
            throw ex;
        }
        catch (Throwable t)
        {
            String message = "Error executing statement: " + t.getMessage();
            throw new EPStatementException(message, epl);
        }
    }

    public EventType getEventType()
    {
        return executeMethod.getEventType();
    }
}
