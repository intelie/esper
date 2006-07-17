package net.esper.eql.join;

import junit.framework.TestCase;

import net.esper.event.EventType;
import net.esper.event.BeanEventAdapter;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportBean_A;
import net.esper.support.eql.SupportExprNode;
import net.esper.support.event.SupportEventTypeFactory;
import net.esper.eql.join.table.PropertyIndexedEventTable;
import net.esper.eql.join.table.UnindexedEventTable;
import net.esper.eql.join.table.EventTable;
import net.esper.eql.join.exec.TableLookupExecNode;
import net.esper.eql.expression.OuterJoinDesc;

import java.util.List;
import java.util.LinkedList;

public class TestJoinSetComposerFactory extends TestCase
{
    private EventType[] streamTypes;

    public void setUp()
    {
        streamTypes = new EventType[2];
        streamTypes[0] = SupportEventTypeFactory.createBeanType(SupportBean.class);
        streamTypes[1] = SupportEventTypeFactory.createBeanType(SupportBean_A.class);
    }

    public void testBuildIndex()
    {
        EventTable table = JoinSetComposerFactory.buildIndex(0, new String[] {"intPrimitive", "boolBoxed"}, streamTypes[0]);
        assertTrue(table instanceof PropertyIndexedEventTable);

        table = JoinSetComposerFactory.buildIndex(0, new String[0], streamTypes[0]);
        assertTrue(table instanceof UnindexedEventTable);

        try
        {
            JoinSetComposerFactory.buildIndex(0, null, streamTypes[0]);
            fail();
        }
        catch (NullPointerException ex)
        {
            // Expected
        }
    }

    public void testBuildComposer()
    {
        List<OuterJoinDesc> outerJoins = new LinkedList<OuterJoinDesc>();
        JoinSetComposerImpl composer = (JoinSetComposerImpl) JoinSetComposerFactory.makeComposer(outerJoins, new SupportExprNode(true), streamTypes, new String[]{"a", "b", "c", "d"});

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
