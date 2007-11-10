package net.esper.eql.named;

import net.esper.core.EPStatementHandle;

import java.util.List;
import java.util.Map;


/**
 * Holds a unit of dispatch that is a result of a named window processing incoming or timer events.
 */
public class NamedWindowConsumerDispatchUnit
{
    private NamedWindowDeltaData deltaData;
    private Map<EPStatementHandle, List<NamedWindowConsumerView>> dispatchTo;

    /**
     * Ctor.
     * @param deltaData the insert and remove stream posted by the named window
     * @param dispatchTo the list of consuming statements, and for each the list of consumer views 
     */
    public NamedWindowConsumerDispatchUnit(NamedWindowDeltaData deltaData, Map<EPStatementHandle, List<NamedWindowConsumerView>> dispatchTo)
    {
        this.deltaData = deltaData;
        this.dispatchTo = dispatchTo;
    }

    /**
     * Returns the data to dispatch.
     * @return dispatch insert and remove stream events
     */
    public NamedWindowDeltaData getDeltaData()
    {
        return deltaData;
    }

    /**
     * Returns the destination of the dispatch: a map of statements and their consuming views (one or multiple)
     * @return map of statement to consumer views
     */
    public Map<EPStatementHandle, List<NamedWindowConsumerView>> getDispatchTo()
    {
        return dispatchTo;
    }
}
