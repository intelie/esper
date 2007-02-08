using System;
using System.Collections.Generic;

using net.esper.eql.expression;
using net.esper.eql.join.exec;
using net.esper.eql.join.plan;
using net.esper.eql.join.table;
using net.esper.eql.spec;
using net.esper.events;
using net.esper.type;
using net.esper.view;

using org.apache.commons.logging;

namespace net.esper.eql.join
{
	/// <summary>
    /// Factory for building a {@link JoinSetComposer} from analyzing filter nodes, for
	/// fast join tuple result set composition.
	/// </summary>

    public class JoinSetComposerFactory
	{
		/// <summary> Builds join tuple composer.</summary>
		/// <param name="outerJoinDescList">- list of descriptors for outer join criteria
		/// </param>
		/// <param name="optionalFilterNode">- filter tree for analysis to build indexes for fast access
		/// </param>
		/// <param name="streamTypes">- types of streams
		/// </param>
		/// <param name="streamNames">- names of streams
		/// </param>
		/// <param name="streamViews">- leaf view per stream
		/// </param>
		/// <param name="selectStreamSelectorEnum">- indicator for rstream or istream-only, for optimization
		/// </param>
		/// <returns> composer implementation
		/// </returns>
		/// <throws>  ExprValidationException is thrown to indicate that </throws>
		/// <summary> validation of view use in joins failed.
		/// </summary>
        public static JoinSetComposer makeComposer(IList<OuterJoinDesc> outerJoinDescList, ExprNode optionalFilterNode, EventType[] streamTypes, String[] streamNames, Viewable[] streamViews, SelectClauseStreamSelectorEnum selectStreamSelectorEnum)
        {
            // Determine if there is a historical
            bool hasHistorical = false;
            for (int i = 0; i < streamViews.Length; i++)
            {
                if (streamViews[i] is HistoricalEventViewable)
                {
                    if (hasHistorical)
                    {
                        throw new ExprValidationException("Joins between historical data streams are not supported");
                    }
                    hasHistorical = true;
                    if (streamTypes.Length > 2)
                    {
                        throw new ExprValidationException("Joins between historical data require a only one event stream in the join");
                    }
                }
            }

            EventTable[][] indexes = null;
            QueryStrategy[] queryStrategies = null;
            if (hasHistorical)
            {
                // No tables for any streams
                indexes = new EventTable[streamTypes.Length][];
                queryStrategies = new QueryStrategy[streamTypes.Length];

                for (int streamNo = 0; streamNo < streamTypes.Length; streamNo++)
                {
                    indexes[streamNo] = new EventTable[0];
                }

                int polledView = 0;
                int streamView = 1;
                if (streamViews[1] is HistoricalEventViewable)
                {
                    streamView = 0;
                    polledView = 1;
                }

                bool isOuterJoin = false;
                ExprEqualsNode EqualsNode = null;
                if (outerJoinDescList.Count > 0)
                {
                    OuterJoinDesc outerJoinDesc = outerJoinDescList[0];
                    if (outerJoinDesc.OuterJoinType.Equals(OuterJoinType.FULL))
                    {
                        isOuterJoin = true;
                    }
                    else if ((outerJoinDesc.OuterJoinType.Equals(OuterJoinType.LEFT)) && (streamView == 0))
                    {
                        isOuterJoin = true;
                    }
                    else if ((outerJoinDesc.OuterJoinType.Equals(OuterJoinType.RIGHT)) && (streamView == 1))
                    {
                        isOuterJoin = true;
                    }

                    EqualsNode = new ExprEqualsNode(false);
                    EqualsNode.AddChildNode(outerJoinDesc.LeftNode);
                    EqualsNode.AddChildNode(outerJoinDesc.RightNode);
                    EqualsNode.validate(null, null);
                }

                HistoricalEventViewable viewable = (HistoricalEventViewable)streamViews[polledView];
                queryStrategies[streamView] = new HistoricalDataQueryStrategy(streamView, polledView, viewable, isOuterJoin, EqualsNode);
            }
            else
            {
                QueryPlan queryPlan = QueryPlanBuilder.getPlan(streamTypes.Length, outerJoinDescList, optionalFilterNode, streamNames);

                // Build indexes
                QueryPlanIndex[] indexSpecs = queryPlan.IndexSpecs;
                indexes = new EventTable[indexSpecs.Length][];
                for (int streamNo = 0; streamNo < indexSpecs.Length; streamNo++)
                {
                    String[][] indexProps = indexSpecs[streamNo].IndexProps;
                    indexes[streamNo] = new EventTable[indexProps.Length];
                    for (int indexNo = 0; indexNo < indexProps.Length; indexNo++)
                    {
                        indexes[streamNo][indexNo] = buildIndex(streamNo, indexProps[indexNo], streamTypes[streamNo]);
                    }
                }

                // Build strategies
                QueryPlanNode[] queryExecSpecs = queryPlan.ExecNodeSpecs;
                queryStrategies = new QueryStrategy[queryExecSpecs.Length];
                for (int i = 0; i < queryExecSpecs.Length; i++)
                {
                    QueryPlanNode planNode = queryExecSpecs[i];
                    ExecNode executionNode = planNode.MakeExec(indexes, streamTypes);

                    if (log.IsInfoEnabled)
                    {
                        log.Debug(".makeComposer Execution nodes for stream " + i + " '" + streamNames[i] + "' : \n" + ExecNode.Print(executionNode));
                    }

                    queryStrategies[i] = new ExecNodeQueryStrategy(i, streamTypes.Length, executionNode);
                }
            }

            return new JoinSetComposerImpl(indexes, queryStrategies, selectStreamSelectorEnum);
        }
		
		/// <summary> Build an index/table instance using the event properties for the event type.</summary>
		/// <param name="indexedStreamNum">- number of stream indexed
		/// </param>
		/// <param name="indexProps">- properties to index
		/// </param>
		/// <param name="eventType">- type of event to expect
		/// </param>
		/// <returns> table build
		/// </returns>
		public static EventTable buildIndex(int indexedStreamNum, String[] indexProps, EventType eventType)
		{
			EventTable table = null;
			if (indexProps.Length == 0)
			{
				table = new UnindexedEventTable(indexedStreamNum);
			}
			else
			{
				table = new PropertyIndexedEventTable(indexedStreamNum, eventType, indexProps);
			}
			return table;
		}
		
		private static readonly Log log = LogFactory.GetLog(typeof(JoinSetComposerFactory));
	}
}