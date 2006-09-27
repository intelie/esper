package net.esper.eql.join;

import net.esper.event.EventBean;
import net.esper.collection.MultiKey;
import net.esper.collection.UniformPair;
import net.esper.eql.join.table.EventTable;

import java.util.Set;
import java.util.HashSet;

/**
 * Implements the function to determine a join result set using tables/indexes and query strategy
 * instances for each stream.
 */
public class JoinSetComposerImpl implements JoinSetComposer
{
    private final EventTable[][] repositories;
    private final QueryStrategy[] queryStrategies;

    private Set<MultiKey<EventBean>> oldResults = new HashSet<MultiKey<EventBean>>();
    private Set<MultiKey<EventBean>> newResults = new HashSet<MultiKey<EventBean>>();

    /**
     * Ctor.
     * @param repositories - for each stream an array of (indexed/unindexed) tables for lookup.
     * @param queryStrategies - for each stream a strategy to execute the join
     */
    public JoinSetComposerImpl(EventTable[][] repositories, QueryStrategy[] queryStrategies)
    {
        this.repositories = repositories;
        this.queryStrategies = queryStrategies;
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

        // remove old data from indexes
        for (int i = 0; i < oldDataPerStream.length; i++)
        {
            if (oldDataPerStream[i] != null)
            {
                for (int j = 0; j < repositories[i].length; j++)
                {
                    repositories[i][j].remove(oldDataPerStream[i]);
                }
            }
        }

        // add new data to indexes
        for (int i = 0; i < newDataPerStream.length; i++)
        {
            if (newDataPerStream[i] != null)
            {
                for (int j = 0; j < repositories[i].length; j++)
                {
                    repositories[i][j].add((newDataPerStream[i]));
                }
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
        return repositories;
    }

    /**
     * Returns query strategies.
     * @return query strategies
     */
    protected QueryStrategy[] getQueryStrategies()
    {
        return queryStrategies;
    }
}
