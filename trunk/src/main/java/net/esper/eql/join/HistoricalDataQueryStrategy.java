package net.esper.eql.join;

import net.esper.event.EventBean;
import net.esper.collection.MultiKey;
import net.esper.view.HistoricalEventViewable;

import java.util.Set;

public class HistoricalDataQueryStrategy implements QueryStrategy
{
    private HistoricalEventViewable historicalEventViewable;

    public HistoricalDataQueryStrategy(HistoricalEventViewable historicalEventViewable)
    {
        this.historicalEventViewable = historicalEventViewable;
    }

    public void lookup(EventBean[] lookupEvents, Set<MultiKey<EventBean>> joinSet)
    {
        historicalEventViewable.poll(lookupEvents, joinSet);
    }

    /*
    public HistoricalDataQueryStrategy(String databaseName, DBStatementMetaData metaData, String preparedStatementSQL, DatabaseService databaseRefService)
    {
        this.databaseName = databaseName;
        this.metaData = metaData;
        this.preparedStatementSQL = preparedStatementSQL;
        this.databaseRefService = databaseRefService;
    }

    public void lookup(EventBean[] lookupEvents, Set<MultiKey<EventBean>> joinSet)
    {
    }

    */
}
