package com.espertech.esper.epl.join;

import com.espertech.esper.collection.MultiKey;
import com.espertech.esper.collection.UniformPair;
import com.espertech.esper.epl.join.table.EventTable;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.view.Viewable;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Implements the function to determine a join result set using tables/indexes and query strategy
 * instances for each stream.
 */
public class JoinSetComposerHistoricalImpl implements JoinSetComposer
{
    private final QueryStrategy[] queryStrategies;

    // Set semantic eliminates duplicates in result set, use Linked set to preserve order
    private Set<MultiKey<EventBean>> oldResults = new LinkedHashSet<MultiKey<EventBean>>();
    private Set<MultiKey<EventBean>> newResults = new LinkedHashSet<MultiKey<EventBean>>();
    private EventTable[][] tables = new EventTable[0][];
    private Viewable[] streamViews;
    private int driverStreamNum;
    private EventBean[] lookupEvents = new EventBean[1];


    /**
     * Ctor.
     * @param queryStrategies - for each stream a strategy to execute the join
     */
    public JoinSetComposerHistoricalImpl(QueryStrategy[] queryStrategies, Viewable[] streamViews, int driverStreamNum)
    {
        this.queryStrategies = queryStrategies;
        this.streamViews = streamViews;
        this.driverStreamNum = driverStreamNum;
    }

    public void init(EventBean[][] eventsPerStream)
    {
    }

    public void destroy()
    {
    }

    public UniformPair<Set<MultiKey<EventBean>>> join(EventBean[][] newDataPerStream, EventBean[][] oldDataPerStream)
    {
        oldResults.clear();
        newResults.clear();

        // join old data
        for (int i = 0; i < oldDataPerStream.length; i++)
        {
            if (oldDataPerStream[i] != null)
            {
                queryStrategies[i].lookup(oldDataPerStream[i], oldResults);
            }
        }

        // join new data
        for (int i = 0; i < newDataPerStream.length; i++)
        {
            if (newDataPerStream[i] != null)
            {
                queryStrategies[i].lookup(newDataPerStream[i], newResults);
            }
        }

        return new UniformPair<Set<MultiKey<EventBean>>>(newResults, oldResults);
    }

    /**
     * Returns tables.
     * @return tables for stream.
     */
    protected EventTable[][] getTables()
    {
        return tables;
    }

    /**
     * Returns query strategies.
     * @return query strategies
     */
    protected QueryStrategy[] getQueryStrategies()
    {
        return queryStrategies;
    }

    public Set<MultiKey<EventBean>> staticJoin()
    {
        Set<MultiKey<EventBean>> result = new LinkedHashSet<MultiKey<EventBean>>();
        Iterator<EventBean> streamEvents = streamViews[driverStreamNum].iterator();

        for (;streamEvents.hasNext();)
        {
            lookupEvents[0] = streamEvents.next();
            queryStrategies[driverStreamNum].lookup(lookupEvents, result);
        }
        return result;
    }
}
