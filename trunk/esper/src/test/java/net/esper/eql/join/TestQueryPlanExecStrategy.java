package net.esper.eql.join;

import net.esper.support.eql.SupportQueryExecNode;
import net.esper.support.bean.SupportBean;
import net.esper.support.event.SupportEventBeanFactory;
import net.esper.event.EventBean;
import junit.framework.TestCase;

public class TestQueryPlanExecStrategy extends TestCase
{
    private ExecNodeQueryStrategy strategy;
    private SupportQueryExecNode supportQueryExecNode;

    public void setUp()
    {
        supportQueryExecNode = new SupportQueryExecNode(null);
        strategy = new ExecNodeQueryStrategy(4, 20, supportQueryExecNode);
    }

    public void testLookup()
    {
        EventBean lookupEvent = SupportEventBeanFactory.createObject(new SupportBean());

        strategy.lookup(new EventBean[] {lookupEvent}, null);

        assertSame(lookupEvent, supportQueryExecNode.getLastPrefillPath()[4]);
    }
}
