package com.espertech.esper.eql.join;

import junit.framework.TestCase;

import com.espertech.esper.event.EventType;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBean_A;
import com.espertech.esper.support.eql.SupportExprNode;
import com.espertech.esper.support.event.SupportEventTypeFactory;
import com.espertech.esper.eql.join.table.PropertyIndexedEventTable;
import com.espertech.esper.eql.join.table.UnindexedEventTable;
import com.espertech.esper.eql.join.table.EventTable;
import com.espertech.esper.eql.join.exec.TableLookupExecNode;
import com.espertech.esper.eql.spec.OuterJoinDesc;
import com.espertech.esper.eql.spec.SelectClauseStreamSelectorEnum;
import com.espertech.esper.view.Viewable;

import java.util.List;
import java.util.LinkedList;

public class TestJoinSetComposerFactory extends TestCase
{
    private EventType[] streamTypes;
    private Viewable[] streamViewables;

    public void setUp()
    {
        streamTypes = new EventType[2];
        streamTypes[0] = SupportEventTypeFactory.createBeanType(SupportBean.class);
        streamTypes[1] = SupportEventTypeFactory.createBeanType(SupportBean_A.class);

        streamViewables = new Viewable[2];
    }

    public void testBuildIndex()
    {
        EventTable table = JoinSetComposerFactoryImpl.buildIndex(0, new String[] {"intPrimitive", "boolBoxed"}, null, streamTypes[0]);
        assertTrue(table instanceof PropertyIndexedEventTable);

        table = JoinSetComposerFactoryImpl.buildIndex(0, new String[0], null, streamTypes[0]);
        assertTrue(table instanceof UnindexedEventTable);

        try
        {
            JoinSetComposerFactoryImpl.buildIndex(0, null, null, streamTypes[0]);
            fail();
        }
        catch (NullPointerException ex)
        {
            // Expected
        }
    }

    public void testBuildComposer() throws Exception
    {
        List<OuterJoinDesc> outerJoins = new LinkedList<OuterJoinDesc>();
        JoinSetComposerImpl composer = (JoinSetComposerImpl) (new JoinSetComposerFactoryImpl()).makeComposer(outerJoins, new SupportExprNode(true), streamTypes, new String[]{"a", "b", "c", "d"}, streamViewables, SelectClauseStreamSelectorEnum.RSTREAM_ISTREAM_BOTH, new boolean[4], new boolean[4]);

        // verify default indexes build
        assertEquals(2, composer.getTables().length);
        assertTrue(composer.getTables()[0][0] instanceof UnindexedEventTable);
        assertTrue(composer.getTables()[1][0] instanceof UnindexedEventTable);

        // verify default strategies
        assertEquals(2, composer.getQueryStrategies().length);
        ExecNodeQueryStrategy plan = (ExecNodeQueryStrategy) composer.getQueryStrategies()[0];
        assertEquals(0, plan.getForStream());
        assertEquals(2, plan.getNumStreams());
        assertTrue(plan.getExecNode() instanceof TableLookupExecNode);
        plan = (ExecNodeQueryStrategy) composer.getQueryStrategies()[1];
        assertEquals(1, plan.getForStream());
        assertEquals(2, plan.getNumStreams());
        assertTrue(plan.getExecNode() instanceof TableLookupExecNode);
    }


}
