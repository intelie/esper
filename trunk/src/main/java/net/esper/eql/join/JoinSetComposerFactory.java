package net.esper.eql.join;

import net.esper.eql.expression.ExprNode;
import net.esper.eql.expression.OuterJoinDesc;
import net.esper.eql.join.exec.ExecNode;
import net.esper.eql.join.plan.QueryPlan;
import net.esper.eql.join.plan.QueryPlanBuilder;
import net.esper.eql.join.plan.QueryPlanIndex;
import net.esper.eql.join.plan.QueryPlanNode;
import net.esper.eql.join.table.EventTable;
import net.esper.eql.join.table.PropertyIndexedEventTable;
import net.esper.eql.join.table.UnindexedEventTable;
import net.esper.event.EventType;
import net.esper.core.EPEQLStmtStartMethod;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Factory for building a {@link JoinSetComposer} from analyzing filter nodes, for
 * fast join tuple result set composition.
 */
public class JoinSetComposerFactory
{
    /**
     * Builds join tuple composer.
     * @param outerJoinDescList - list of descriptors for outer join criteria
     * @param optionalFilterNode - filter tree for analysis to build indexes for fast access
     * @param streamTypes - types of streams
     * @param streamNames - names of streams
     * @return composer implementation
     */
    public static JoinSetComposerImpl makeComposer(List<OuterJoinDesc> outerJoinDescList, 
                                                   ExprNode optionalFilterNode, 
                                                   EventType[] streamTypes,
                                                   String[] streamNames)
    {
        QueryPlan queryPlan = QueryPlanBuilder.getPlan(streamTypes.length, outerJoinDescList, optionalFilterNode, streamNames);

        // Build indexes
        QueryPlanIndex[] indexSpecs = queryPlan.getIndexSpecs();
        EventTable[][] indexes = new EventTable[indexSpecs.length][];
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
        QueryStrategy[] queryStrategies = new QueryStrategy[queryExecSpecs.length];
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

        return new JoinSetComposerImpl(indexes, queryStrategies);
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
