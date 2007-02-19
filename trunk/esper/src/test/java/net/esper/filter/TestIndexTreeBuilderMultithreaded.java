package net.esper.filter;

import java.util.Vector;
import java.util.List;
import java.util.LinkedList;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.LinkedBlockingQueue;

import junit.framework.TestCase;
import net.esper.support.bean.SupportBean;
import net.esper.support.filter.SupportFilterSpecBuilder;
import net.esper.support.filter.SupportFilterHandle;
import net.esper.support.filter.IndexTreeBuilderRunnable;
import net.esper.support.event.SupportEventTypeFactory;
import net.esper.support.event.SupportEventBeanFactory;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestIndexTreeBuilderMultithreaded extends TestCase
{
    private Vector<FilterSpec> testFilterSpecs;
    private Vector<EventBean> matchedEvents;
    private Vector<EventBean> unmatchedEvents;

    private EventType eventType;

    private FilterHandleSetNode topNode;
    private IndexTreeBuilder builder;
    private List<FilterHandle> filterCallbacks;
    private List<IndexTreePath> pathsAddedTo;

    public void setUp()
    {
        builder = new IndexTreeBuilder();
        eventType = SupportEventTypeFactory.createBeanType(SupportBean.class);
        topNode = new FilterHandleSetNode();
        filterCallbacks = new LinkedList<FilterHandle>();
        pathsAddedTo = new LinkedList<IndexTreePath>();

        testFilterSpecs = new Vector<FilterSpec>();
        matchedEvents = new Vector<EventBean>();
        unmatchedEvents = new Vector<EventBean>();

        // Any int and double value specified here must match only the current filter spec not any other filter spec
        testFilterSpecs.add(makeSpec(new Object[] { "intPrimitive", FilterOperator.GREATER_OR_EQUAL, 100000 }));
        matchedEvents.add(makeEvent(9999999, -1));
        unmatchedEvents.add(makeEvent(0, -1));

        testFilterSpecs.add(makeSpec(new Object[] { "intPrimitive", FilterOperator.GREATER_OR_EQUAL, 10,
                                          "doublePrimitive", FilterOperator.EQUAL, 0.5}));
        matchedEvents.add(makeEvent(10, 0.5));
        unmatchedEvents.add(makeEvent(0, 0.5));

        testFilterSpecs.add(makeSpec(new Object[] { "doublePrimitive", FilterOperator.EQUAL, 0.8}));
        matchedEvents.add(makeEvent(-1, 0.8));
        unmatchedEvents.add(makeEvent(-1, 0.1));

        testFilterSpecs.add(makeSpec(new Object[] { "doublePrimitive", FilterOperator.EQUAL, 99.99,
                                                     "intPrimitive", FilterOperator.LESS, 1}));
        matchedEvents.add(makeEvent(0, 99.99));
        unmatchedEvents.add(makeEvent(2, 0.5));

        testFilterSpecs.add(makeSpec(new Object[] { "doublePrimitive", FilterOperator.GREATER, .99,
                                                     "intPrimitive", FilterOperator.EQUAL, 5001}));
        matchedEvents.add(makeEvent(5001, 1.1));
        unmatchedEvents.add(makeEvent(5002, 0.98));

        testFilterSpecs.add(makeSpec(new Object[] { "intPrimitive", FilterOperator.LESS, -99000}));
        matchedEvents.add(makeEvent(-99001, -1));
        unmatchedEvents.add(makeEvent(-98999, -1));

        testFilterSpecs.add(makeSpec(new Object[] { "intPrimitive", FilterOperator.GREATER_OR_EQUAL, 11,
                                          "doublePrimitive", FilterOperator.GREATER, 888.0}));
        matchedEvents.add(makeEvent(11, 888.001));
        unmatchedEvents.add(makeEvent(10, 888));

        testFilterSpecs.add(makeSpec(new Object[] { "intPrimitive", FilterOperator.EQUAL, 973,
                                          "doublePrimitive", FilterOperator.EQUAL, 709.0}));
        matchedEvents.add(makeEvent(973, 709));
        unmatchedEvents.add(makeEvent(0, 0.5));

        testFilterSpecs.add(makeSpec(new Object[] { "intPrimitive", FilterOperator.EQUAL, 973,
                                          "doublePrimitive", FilterOperator.EQUAL, 655.0}));
        matchedEvents.add(makeEvent(973, 655));
        unmatchedEvents.add(makeEvent(33838, 655.5));
    }

    public void testVerifyFilterSpecSet()
    {
        // Add all the above filter definitions
        for (FilterSpec filterSpec : testFilterSpecs)
        {
            FilterValueSet filterValues = filterSpec.getValueSet(null);
            FilterHandle callback = new SupportFilterHandle();
            filterCallbacks.add(callback);
            pathsAddedTo.add(builder.add(filterValues, callback, topNode));
        }

        // None of the not-matching events should cause any match
        for (EventBean event : unmatchedEvents)
        {
            List<FilterHandle> matches = new LinkedList<FilterHandle>();
            topNode.matchEvent(event, matches);
            assertTrue(matches.size() == 0);
        }

        // All of the matching events should cause exactly one match
        for (EventBean event : matchedEvents)
        {
            List<FilterHandle> matches = new LinkedList<FilterHandle>();
            topNode.matchEvent(event, matches);
            assertTrue(matches.size() == 1);
        }

        // Remove all expressions previously added
        int count = 0;
        for (IndexTreePath treePath : pathsAddedTo)
        {
            FilterHandle callback = filterCallbacks.get(count++);
            builder.remove(callback, treePath, topNode);
        }

        // After the remove no matches are expected
        for (EventBean event : matchedEvents)
        {
            List<FilterHandle> matches = new LinkedList<FilterHandle>();
            topNode.matchEvent(event, matches);
            assertTrue(matches.size() == 0);
        }
    }

    public void testMultithreaded() throws Exception
    {
        FilterHandleSetNode topNode = new FilterHandleSetNode();

        performMultithreadedTest(topNode, 2, 1000, 1);
        performMultithreadedTest(topNode, 3, 1000, 1);
        performMultithreadedTest(topNode, 4, 1000, 1);

        performMultithreadedTest(new FilterHandleSetNode(), 2, 1000, 1);
        performMultithreadedTest(new FilterHandleSetNode(), 3, 1000, 1);
        performMultithreadedTest(new FilterHandleSetNode(), 4, 1000, 1);
    }

    private void performMultithreadedTest(FilterHandleSetNode topNode,
                             int numberOfThreads,
                             int numberOfRunnables,
                             int numberOfSecondsSleep) throws Exception
    {
        log.info(".performMultithreadedTest Loading thread pool work queue,numberOfRunnables=" + numberOfRunnables);

        ThreadPoolExecutor pool = new ThreadPoolExecutor(0, numberOfThreads, 99999, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

        for (int i = 0; i < numberOfRunnables; i++)
        {
            IndexTreeBuilderRunnable runnable = new IndexTreeBuilderRunnable(topNode,
                    testFilterSpecs, matchedEvents, unmatchedEvents);

            pool.execute(runnable);
        }

        log.info(".performMultithreadedTest Starting thread pool, threads=" + numberOfThreads);
        pool.setCorePoolSize(numberOfThreads);

        // Sleep X seconds
        sleep(numberOfSecondsSleep);

        log.info(".performMultithreadedTest Completed, numberOfRunnables=" + numberOfRunnables +
                 "  numberOfThreads=" + numberOfThreads +
                 "  completed=" + pool.getCompletedTaskCount());

        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.SECONDS);

        assertTrue(pool.getCompletedTaskCount() == numberOfRunnables);
    }

    private void sleep(int sec)
    {
        try
        {
            Thread.sleep(sec * 1000);
        }
        catch (InterruptedException e)
        {
            log.warn(e);
        }
    }

    private FilterSpec makeSpec(Object[] args)
    {
        return SupportFilterSpecBuilder.build(eventType, args);
    }

    private EventBean makeEvent(int aInt, double aDouble)
    {
        SupportBean bean = new SupportBean();
        bean.setIntPrimitive(aInt);
        bean.setDoublePrimitive(aDouble);
        return SupportEventBeanFactory.createObject(bean);
    }

    private static final Log log = LogFactory.getLog(TestIndexTreeBuilderMultithreaded.class);
}
