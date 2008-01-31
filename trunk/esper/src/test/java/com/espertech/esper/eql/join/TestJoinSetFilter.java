package com.espertech.esper.eql.join;

import com.espertech.esper.eql.expression.ExprNode;
import com.espertech.esper.support.eql.SupportExprNodeFactory;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.event.SupportEventBeanFactory;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.collection.MultiKey;

import java.util.Set;
import java.util.HashSet;

import junit.framework.TestCase;

public class TestJoinSetFilter extends TestCase
{
    public void testFilter() throws Exception
    {
        ExprNode topNode = SupportExprNodeFactory.make2SubNodeAnd();

        EventBean[] pairOne = new EventBean[2];
        pairOne[0] = makeEvent(1, 2, "a");
        pairOne[1] = makeEvent(2, 1, "a");

        EventBean[] pairTwo = new EventBean[2];
        pairTwo[0] = makeEvent(1, 2, "a");
        pairTwo[1] = makeEvent(2, 999, "a");

        Set<MultiKey<EventBean>> eventSet = new HashSet<MultiKey<EventBean>>();
        eventSet.add(new MultiKey<EventBean>(pairOne));
        eventSet.add(new MultiKey<EventBean>(pairTwo));

        JoinSetFilter.filter(topNode, eventSet, true);

        assertEquals(1, eventSet.size());
        assertSame(pairOne, eventSet.iterator().next().getArray());
    }

    private EventBean makeEvent(int intPrimitive, int intBoxed, String string)
    {
        SupportBean event = new SupportBean();
        event.setIntPrimitive(intPrimitive);
        event.setIntBoxed(intBoxed);
        event.setString(string);
        return SupportEventBeanFactory.createObject(event);
    }
}
