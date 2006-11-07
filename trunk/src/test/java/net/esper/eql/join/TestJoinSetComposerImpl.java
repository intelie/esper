package net.esper.eql.join;

import java.util.Set;

import junit.framework.TestCase;
import net.esper.collection.MultiKey;
import net.esper.collection.UniformPair;
import net.esper.eql.join.exec.FullTableScanLookupStrategy;
import net.esper.eql.join.exec.TableLookupExecNode;
import net.esper.eql.join.table.EventTable;
import net.esper.eql.join.table.UnindexedEventTable;
import net.esper.eql.spec.SelectClauseStreamSelectorEnum;
import net.esper.event.EventBean;
import net.esper.support.bean.SupportBean;
import net.esper.support.event.SupportEventBeanFactory;

public class TestJoinSetComposerImpl extends TestCase
{
    private JoinSetComposerImpl joinSetComposerImpl;
    private EventBean[] indexedEventOne, indexedEventTwo, newEventOne, newEventTwo;
    private UnindexedEventTable indexLeft;
    private UnindexedEventTable indexRight;

    public void setUp()
    {
        indexedEventOne = SupportEventBeanFactory.makeEvents(new String[] { "s1_1", "s1_2"});
        indexedEventTwo = SupportEventBeanFactory.makeEvents(new String[] { "s2_1", "s2_2"});

        newEventOne = SupportEventBeanFactory.makeEvents(new String[] { "s1_3"});
        newEventTwo = SupportEventBeanFactory.makeEvents(new String[] { "s2_3"});

        indexLeft = new UnindexedEventTable(1);
        indexLeft.add(indexedEventOne);
        indexRight = new UnindexedEventTable(1);
        indexRight.add(indexedEventTwo);

        QueryStrategy[] queryStrategies = new QueryStrategy[2];
        TableLookupExecNode lookupLeft = new TableLookupExecNode(1, new FullTableScanLookupStrategy(indexRight));
        TableLookupExecNode lookupRight = new TableLookupExecNode(0, new FullTableScanLookupStrategy(indexLeft));
        queryStrategies[0] = new ExecNodeQueryStrategy(0, 2, lookupLeft);
        queryStrategies[1] = new ExecNodeQueryStrategy(1, 2, lookupRight);

        EventTable[][] indexesPerStream = new EventTable[2][1];
        indexesPerStream[0][0] = indexLeft;
        indexesPerStream[1][0] = indexRight;
        joinSetComposerImpl = new JoinSetComposerImpl(indexesPerStream, queryStrategies, SelectClauseStreamSelectorEnum.RSTREAM_ISTREAM_BOTH);
    }

    public void testJoin()
    {
        // Should return all possible combinations, not matching performed, remember: duplicate pairs have been removed
        UniformPair<Set<MultiKey<EventBean>>> result = joinSetComposerImpl.join(
                new EventBean[][] {newEventOne, newEventTwo},                 // new left and right
                new EventBean[][] {new EventBean[] {indexedEventOne[0]}, new EventBean[] {indexedEventTwo[1]}} // old left and right
                );

        assertEquals(3, result.getFirst().size());      // check old events joined
        String eventStringText = toString(result.getSecond());
        assertTrue(eventStringText.contains("s1_1|s2_1"));
        assertTrue(eventStringText.contains("s1_1|s2_2"));
        assertTrue(eventStringText.contains("s1_2|s2_2"));

        // check new events joined, remember: duplicate pairs have been removed
        assertEquals(3, result.getSecond().size());
        eventStringText = toString(result.getFirst());
        assertTrue(eventStringText.contains("s1_3|s2_1"));
        assertTrue(eventStringText.contains("s1_3|s2_3"));
        assertTrue(eventStringText.contains("s1_2|s2_3"));
    }

    private String toString(Set<MultiKey<EventBean>> events)
    {
        String delimiter = "";
        StringBuffer buf = new StringBuffer();

        for (MultiKey<EventBean> key : events)
        {
            buf.append(delimiter);
            buf.append(toString(key.getArray()));
            delimiter = ",";
        }
        return buf.toString();
    }

    private String toString(EventBean[] events)
    {
        String delimiter = "";
        StringBuffer buf = new StringBuffer();
        for (EventBean event : events)
        {
            buf.append(delimiter);
            buf.append(((SupportBean) event.getUnderlying()).getString());
            delimiter = "|";
        }
        return buf.toString();
    }
}
