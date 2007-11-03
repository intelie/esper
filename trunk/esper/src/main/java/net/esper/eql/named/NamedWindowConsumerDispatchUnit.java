package net.esper.eql.named;

import net.esper.core.EPStatementHandle;

import java.util.List;
import java.util.Map;


public class NamedWindowConsumerDispatchUnit
{
    private NamedWindowDeltaData deltaData;
    private Map<EPStatementHandle, List<NamedWindowConsumerView>> dispatchTo;

    public NamedWindowConsumerDispatchUnit(NamedWindowDeltaData deltaData, Map<EPStatementHandle, List<NamedWindowConsumerView>> dispatchTo)
    {
        this.deltaData = deltaData;
        this.dispatchTo = dispatchTo;
    }

    public NamedWindowDeltaData getDeltaData()
    {
        return deltaData;
    }

    public Map<EPStatementHandle, List<NamedWindowConsumerView>> getDispatchTo()
    {
        return dispatchTo;
    }
}
