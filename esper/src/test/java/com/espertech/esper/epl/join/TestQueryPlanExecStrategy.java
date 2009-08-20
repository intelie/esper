package com.espertech.esper.epl.join;

import com.espertech.esper.support.epl.SupportQueryExecNode;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.event.SupportEventBeanFactory;
import com.espertech.esper.client.EventBean;
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
