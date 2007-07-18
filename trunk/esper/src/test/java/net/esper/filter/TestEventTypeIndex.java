package net.esper.filter;

import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.support.bean.ISupportA;
import net.esper.support.bean.ISupportABCImpl;
import net.esper.support.bean.ISupportAImplSuperGImplPlus;
import net.esper.support.bean.ISupportBaseAB;
import net.esper.support.bean.SupportBean;
import net.esper.support.filter.SupportFilterHandle;
import net.esper.support.event.SupportEventBeanFactory;
import net.esper.support.event.SupportEventTypeFactory;

public class TestEventTypeIndex extends TestCase
{
    private EventTypeIndex testIndex;

    private EventBean testEventBean;
    private EventType testEventType;

    private FilterHandleSetNode handleSetNode;
    private FilterHandle filterCallback;

    public void setUp()
    {
        SupportBean testBean = new SupportBean();
        testEventBean = SupportEventBeanFactory.createObject(testBean);
        testEventType = testEventBean.getEventType();

        handleSetNode = new FilterHandleSetNode();
        filterCallback = new SupportFilterHandle();
        handleSetNode.add(filterCallback);

        testIndex = new EventTypeIndex();
        testIndex.add(testEventType, handleSetNode);
    }

    public void testMatch()
    {
        List<FilterHandle> matchesList = new LinkedList<FilterHandle>();

        // Invoke match
        testIndex.matchEvent(testEventBean, matchesList);

        assertEquals(1, matchesList.size());
        assertEquals(filterCallback, matchesList.get(0));
    }

    public void testInvalidSecondAdd()
    {
        try
        {
            testIndex.add(testEventType, handleSetNode);
            assertTrue(false);
        }
        catch (IllegalStateException ex)
        {
            // Expected
        }
    }

    public void testGet()
    {
        assertEquals(handleSetNode, testIndex.get(testEventType));
    }

    public void testSuperclassMatch()
    {
        testEventBean = SupportEventBeanFactory.createObject(new ISupportAImplSuperGImplPlus());
        testEventType = SupportEventTypeFactory.createBeanType(ISupportA.class);

        testIndex = new EventTypeIndex();
        testIndex.add(testEventType, handleSetNode);

        List<FilterHandle> matchesList = new LinkedList<FilterHandle>();
        testIndex.matchEvent(testEventBean, matchesList);

        assertEquals(1, matchesList.size());
        assertEquals(filterCallback, matchesList.get(0));
    }

    public void testInterfaceMatch()
    {
        testEventBean = SupportEventBeanFactory.createObject(new ISupportABCImpl("a", "b", "ab", "c"));
        testEventType = SupportEventTypeFactory.createBeanType(ISupportBaseAB.class);

        testIndex = new EventTypeIndex();
        testIndex.add(testEventType, handleSetNode);

        List<FilterHandle> matchesList = new LinkedList<FilterHandle>();
        testIndex.matchEvent(testEventBean, matchesList);

        assertEquals(1, matchesList.size());
        assertEquals(filterCallback, matchesList.get(0));
    }
}