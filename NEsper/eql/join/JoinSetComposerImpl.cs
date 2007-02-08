using System;
using System.Collections.Generic;

using net.esper.collection;
using net.esper.compat;
using net.esper.eql.join.table;
using net.esper.eql.spec;
using net.esper.events;

namespace net.esper.eql.join
{
    /// <summary> Implements the function to determine a join result set using tables/indexes and query strategy
    /// instances for each stream.
    /// </summary>

    public class JoinSetComposerImpl : JoinSetComposer
    {
        /// <summary> Returns tables.</summary>
        /// <returns> tables for stream.
        /// </returns>

        virtual public EventTable[][] Tables
        {
            get { return repositories; }
        }

        /// <summary> Returns query strategies.</summary>
        /// <returns> query strategies
        /// </returns>

        virtual public QueryStrategy[] QueryStrategies
        {
            get { return queryStrategies; }
        }

        private readonly EventTable[][] repositories;
        private readonly QueryStrategy[] queryStrategies;
        private readonly SelectClauseStreamSelectorEnum selectStreamSelectorEnum;

        // Set semantic eliminates duplicates in result set, use Linked set to preserve order
        private ISet<MultiKey<EventBean>> oldResults = new LinkedHashSet<MultiKey<EventBean>>();
        private ISet<MultiKey<EventBean>> newResults = new LinkedHashSet<MultiKey<EventBean>>();

        /// <summary> Ctor.</summary>
        /// <param name="repositories">- for each stream an array of (indexed/unindexed) tables for lookup.
        /// </param>
        /// <param name="queryStrategies">- for each stream a strategy to execute the join
        /// </param>
        /// <param name="selectStreamSelectorEnum">- indicator for rstream or istream-only, for optimization
        /// </param>

        public JoinSetComposerImpl(EventTable[][] repositories, QueryStrategy[] queryStrategies, SelectClauseStreamSelectorEnum selectStreamSelectorEnum)
        {
            this.repositories = repositories;
            this.queryStrategies = queryStrategies;
            this.selectStreamSelectorEnum = selectStreamSelectorEnum;
        }

        public UniformPair<ISet<MultiKey<EventBean>>> join(EventBean[][] newDataPerStream, EventBean[][] oldDataPerStream)
        {
            oldResults.Clear();
            newResults.Clear();

            // join old data
            if (!selectStreamSelectorEnum.Equals(SelectClauseStreamSelectorEnum.ISTREAM_ONLY))
            {
                for (int i = 0; i < oldDataPerStream.Length; i++)
                {
                    if (oldDataPerStream[i] != null)
                    {
                        queryStrategies[i].lookup(oldDataPerStream[i], oldResults);
                    }
                }
            }

            // remove old data from indexes
            for (int i = 0; i < oldDataPerStream.Length; i++)
            {
                if (oldDataPerStream[i] != null)
                {
                    for (int j = 0; j < repositories[i].Length; j++)
                    {
                        repositories[i][j].Remove(oldDataPerStream[i]);
                    }
                }
            }

            // add new data to indexes
            for (int i = 0; i < newDataPerStream.Length; i++)
            {
                if (newDataPerStream[i] != null)
                {
                    for (int j = 0; j < repositories[i].Length; j++)
                    {
                        repositories[i][j].Add((newDataPerStream[i]));
                    }
                }
            }

            // join new data
            if (!selectStreamSelectorEnum.Equals(SelectClauseStreamSelectorEnum.RSTREAM_ONLY))
            {
                for (int i = 0; i < newDataPerStream.Length; i++)
                {
                    if (newDataPerStream[i] != null)
                    {
                        queryStrategies[i].lookup(newDataPerStream[i], newResults);
                    }
                }
            }

            return new UniformPair<ISet<MultiKey<EventBean>>>(newResults, oldResults);
        }
    }
}
