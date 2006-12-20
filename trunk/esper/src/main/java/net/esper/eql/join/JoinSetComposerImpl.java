package net.esper.eql.join;

import net.esper.event.EventBean;
import net.esper.collection.MultiKey;
import net.esper.collection.UniformPair;
import net.esper.eql.join.table.EventTable;
import net.esper.eql.spec.SelectClauseStreamSelectorEnum;

import java.util.Set;
import java.util.HashSet;
import java.util.LinkedHashSet;

/**
 * Implements the function to determine a join result set using tables/indexes and query strategy
 * instances for each stream.
 */
public class JoinSetComposerImpl implements JoinSetComposer
{
    private final EventTable[][] repositories;
    private final QueryStrategy[] queryStrategies;
    private final SelectClauseStreamSelectorEnum selectStreamSelectorEnum;

    // Set semantic eliminates duplicates in result set, use Linked set to preserve order
    private Set<MultiKey<EventBean>> oldResults = new LinkedHashSet<MultiKey<EventBean>>();
    private Set<MultiKey<EventBean>> newResults = new LinkedHashSet<MultiKey<EventBean>>();

    /**
     * Ctor.
     * @param repositories - for each stream an array of (indexed/unindexed) tables for lookup.
     * @param queryStrategies - for each stream a strategy to execute the join
     * @param selectStreamSelectorEnum - indicator for rstream or istream-only, for optimization
     */
    public JoinSetComposerImpl(EventTable[][] repositories, QueryStrategy[] queryStrategies, SelectClauseStreamSelectorEnum selectStreamSelectorEnum)
    {
        this.repositories = repositories;
        this.queryStrategies = queryStrategies;
        this.selectStreamSelectorEnum = selectStreamSelectorEnum;
    }

    public UniformPair<Set<MultiKey<EventBean>>> join(EventBean[][] newDataPerStream, EventBean[][] oldDataPerStream)
    {
        oldResults.clear();
        newResults.clear();

        // join old data
        if (!selectStreamSelectorEnum.equals(SelectClauseStreamSelectorEnum.ISTREAM_ONLY))
        {
            for (int i = 0; i < oldDataPerStream.length; i++)
            {
                if (oldDataPerStream[i] != null)
                {
                    queryStrategies[i].lookup(oldDataPerStream[i], oldResults);
                }
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
        if (!selectStreamSelectorEnum.equals(SelectClauseStreamSelectorEnum.RSTREAM_ONLY))
        {
            for (int i = 0; i < newDataPerStream.length; i++)
            {
                if (newDataPerStream[i] != null)
                {
                    queryStrategies[i].lookup(newDataPerStream[i], newResults);
                }
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
