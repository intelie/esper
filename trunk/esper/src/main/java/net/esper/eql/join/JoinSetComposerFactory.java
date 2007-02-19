package net.esper.eql.join;

import net.esper.eql.expression.ExprNode;
import net.esper.eql.expression.ExprValidationException;
import net.esper.eql.expression.ExprEqualsNode;
import net.esper.eql.spec.OuterJoinDesc;
import net.esper.eql.spec.SelectClauseStreamSelectorEnum;
import net.esper.eql.join.exec.ExecNode;
import net.esper.eql.join.plan.QueryPlan;
import net.esper.eql.join.plan.QueryPlanBuilder;
import net.esper.eql.join.plan.QueryPlanIndex;
import net.esper.eql.join.plan.QueryPlanNode;
import net.esper.eql.join.table.EventTable;
import net.esper.eql.join.table.PropertyIndexedEventTable;
import net.esper.eql.join.table.UnindexedEventTable;
import net.esper.event.EventType;
import net.esper.view.Viewable;
import net.esper.view.HistoricalEventViewable;
import net.esper.type.OuterJoinType;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Factory for building a {@link JoinSetComposer} from analyzing filter nodes, for
 * fast join tuple result set composition.
 */
@SuppressWarnings({"StringContatenationInLoop"})
public class JoinSetComposerFactory
{
    /**
     * Builds join tuple composer.
     * @param outerJoinDescList - list of descriptors for outer join criteria
     * @param optionalFilterNode - filter tree for analysis to build indexes for fast access
     * @param streamTypes - types of streams
     * @param streamNames - names of streams
     * @param streamViews - leaf view per stream
     * @param selectStreamSelectorEnum - indicator for rstream or istream-only, for optimization
     * @return composer implementation
     * @throws ExprValidationException is thrown to indicate that
     * validation of view use in joins failed.
     */
    public static JoinSetComposer makeComposer(List<OuterJoinDesc> outerJoinDescList,
                                                   ExprNode optionalFilterNode, 
                                                   EventType[] streamTypes,
                                                   String[] streamNames,
                                                   Viewable[] streamViews,
                                                   SelectClauseStreamSelectorEnum selectStreamSelectorEnum)
            throws ExprValidationException
    {
        // Determine if there is a historical
        boolean hasHistorical = false;
        for (int i = 0; i < streamViews.length; i++)
        {
            if (streamViews[i] instanceof HistoricalEventViewable)
            {
                if (hasHistorical)
                {
                    throw new ExprValidationException("Joins between historical data streams are not supported");
                }
                hasHistorical = true;
                if (streamTypes.length > 2)
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
            indexes = new EventTable[streamTypes.length][];
            queryStrategies = new QueryStrategy[streamTypes.length];
            
            for (int streamNo = 0; streamNo < streamTypes.length; streamNo++)
            {
                indexes[streamNo] = new EventTable[0];
            }

            int polledView = 0;
            int streamView = 1;
            if (streamViews[1] instanceof HistoricalEventViewable)
            {
                streamView = 0;
                polledView = 1;
            }

            boolean isOuterJoin = false;
            ExprEqualsNode equalsNode = null;
            if (!outerJoinDescList.isEmpty())
            {
                OuterJoinDesc outerJoinDesc = outerJoinDescList.get(0);
                if (outerJoinDesc.getOuterJoinType().equals(OuterJoinType.FULL))
                {
                    isOuterJoin = true;
                }
                else if ((outerJoinDesc.getOuterJoinType().equals(OuterJoinType.LEFT)) &&
                        (streamView == 0))
                {
                        isOuterJoin = true;
                }
                else if ((outerJoinDesc.getOuterJoinType().equals(OuterJoinType.RIGHT)) &&
                        (streamView == 1))
                {
                        isOuterJoin = true;
                }

                equalsNode = new ExprEqualsNode(false);
                equalsNode.addChildNode(outerJoinDesc.getLeftNode());
                equalsNode.addChildNode(outerJoinDesc.getRightNode());
                equalsNode.validate(null, null, null);
            }

            HistoricalEventViewable viewable = (HistoricalEventViewable) streamViews[polledView];
            queryStrategies[streamView] = new HistoricalDataQueryStrategy(streamView, polledView, viewable, isOuterJoin, equalsNode);
        }
        else
        {
            QueryPlan queryPlan = QueryPlanBuilder.getPlan(streamTypes.length, outerJoinDescList, optionalFilterNode, streamNames);

            // Build indexes
            QueryPlanIndex[] indexSpecs = queryPlan.getIndexSpecs();
            indexes = new EventTable[indexSpecs.length][];
            for (int streamNo = 0; streamNo < indexSpecs.length; streamNo++)
            {
                String[][] indexProps = indexSpecs[streamNo].getIndexProps();
                indexes[streamNo] = new EventTable[indexProps.length];
                for (int indexNo = 0; indexNo < indexProps.length; indexNo++)
                {
                    indexes[streamNo][indexNo] = buildIndex(streamNo, indexProps[indexNo], streamTypes[streamNo]);
                }
            }

            // Build strategies
            QueryPlanNode[] queryExecSpecs = queryPlan.getExecNodeSpecs();
            queryStrategies = new QueryStrategy[queryExecSpecs.length];
            for (int i = 0; i < queryExecSpecs.length; i++)
            {
                QueryPlanNode planNode = queryExecSpecs[i];
                ExecNode executionNode = planNode.makeExec(indexes, streamTypes);

                if (log.isInfoEnabled())
                {
                    log.debug(".makeComposer Execution nodes for stream " + i + " '" + streamNames[i] +
                        "' : \n" + ExecNode.print(executionNode));
                }

                queryStrategies[i] = new ExecNodeQueryStrategy(i, streamTypes.length, executionNode);
            }
        }

        return new JoinSetComposerImpl(indexes, queryStrategies, selectStreamSelectorEnum);
    }

    /**
     * Build an index/table instance using the event properties for the event type.
     * @param indexedStreamNum - number of stream indexed
     * @param indexProps - properties to index
     * @param eventType - type of event to expect
     * @return table build
     */
    protected static EventTable buildIndex(int indexedStreamNum, String[] indexProps, EventType eventType)
    {
        EventTable table = null;
        if (indexProps.length == 0)
        {
            table = new UnindexedEventTable(indexedStreamNum);
        }
        else
        {
            table = new PropertyIndexedEventTable(indexedStreamNum, eventType, indexProps);
        }
        return table;
    }

    private static final Log log = LogFactory.getLog(JoinSetComposerFactory.class);
}
