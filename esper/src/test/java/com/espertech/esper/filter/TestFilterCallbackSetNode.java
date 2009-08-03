package com.espertech.esper.filter;

import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.support.bean.SupportBeanSimple;
import com.espertech.esper.support.filter.SupportEventEvaluator;
import com.espertech.esper.support.filter.SupportFilterHandle;
import com.espertech.esper.support.filter.SupportFilterParamIndex;
import com.espertech.esper.support.event.SupportEventBeanFactory;

public class TestFilterCallbackSetNode extends TestCase
{
    private SupportEventEvaluator testEvaluator;
    private FilterHandleSetNode testNode;

    public void setUp()
    {
        testEvaluator = new SupportEventEvaluator();
        testNode = new FilterHandleSetNode();
    }

    public void testNodeGetSet()
    {
        FilterHandle exprOne = new SupportFilterHandle();

        // Check pre-conditions
        assertTrue(testNode.getNodeRWLock() != null);
        assertFalse(testNode.contains(exprOne));
        assertEquals(0, testNode.getFilterCallbackCount());
        assertEquals(0, testNode.getIndizes().size());
        assertTrue(testNode.isEmpty());

        testNode.add(exprOne);

        // Check after add
        assertTrue(testNode.contains(exprOne));
        assertEquals(1, testNode.getFilterCallbackCount());
        assertFalse(testNode.isEmpty());

        // Add an indexOne
        FilterParamIndexBase indexOne = new SupportFilterParamIndex();
        testNode.add(indexOne);

        // Check after add
        assertEquals(1, testNode.getIndizes().size());
        assertEquals(indexOne, testNode.getIndizes().get(0));

        // Check removes
        assertTrue(testNode.remove(exprOne));
        assertFalse(testNode.isEmpty());
        assertFalse(testNode.remove(exprOne));
        assertTrue(testNode.remove(indexOne));
        assertFalse(testNode.remove(indexOne));
        assertTrue(testNode.isEmpty());
    }

    public void testNodeMatching()
    {
        SupportBeanSimple eventObject = new SupportBeanSimple("DepositEvent_1", 1);
        EventBean eventBean = SupportEventBeanFactory.createObject(eventObject);

        FilterHandle expr = new SupportFilterHandle();
        testNode.add(expr);

        // Check matching without an index node
        List<FilterHandle> matches = new LinkedList<FilterHandle>();
        testNode.matchEvent(eventBean, matches);
        assertEquals(1, matches.size());
        assertEquals(expr, matches.get(0));
        matches.clear();

        // Create, add and populate an index node
        FilterParamIndexBase index = new FilterParamIndexEquals("myString", eventBean.getEventType());
        testNode.add(index);
        index.put("DepositEvent_1", testEvaluator);

        // Verify matcher instance stored in index is called
        testNode.matchEvent(eventBean, matches);

        assertTrue(testEvaluator.getAndResetCountInvoked() == 1);
        assertTrue(testEvaluator.getLastEvent() == eventBean);
        assertEquals(1, matches.size());
        assertEquals(expr, matches.get(0));
    }
}