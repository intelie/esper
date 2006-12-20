package net.esper.filter;

import junit.framework.TestCase;
import net.esper.support.bean.SupportBean;
import net.esper.support.filter.SupportFilterSpecBuilder;
import net.esper.support.filter.SupportFilterCallback;
import net.esper.support.event.SupportEventBeanFactory;
import net.esper.event.EventType;
import net.esper.event.EventBean;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;

public class TestIndexTreeBuilder extends TestCase
{
    List<FilterCallback> matches;
    IndexTreeBuilder builder;
    EventBean eventBean;
    EventType eventType;
    FilterCallback testFilterCallback[];

    public void setUp()
    {
        SupportBean testBean = new SupportBean();
        testBean.setIntPrimitive(50);
        testBean.setDoublePrimitive(0.5);
        testBean.setString("jack");
        testBean.setLongPrimitive(10);
        testBean.setShortPrimitive((short) 20);

        builder = new IndexTreeBuilder();
        eventBean = SupportEventBeanFactory.createObject(testBean);
        eventType = eventBean.getEventType();

        matches = new LinkedList<FilterCallback>();

        // Allocate a couple of callbacks
        testFilterCallback = new SupportFilterCallback[20];
        for (int i = 0; i < testFilterCallback.length; i++)
        {
            testFilterCallback[i] = new SupportFilterCallback();
        }
    }

    public void testCopyParameters()
    {
        FilterValueSet spec = makeFilterValues(
                "doublePrimitive", FilterOperator.LESS, 1.1,
                "doubleBoxed", FilterOperator.LESS, 1.1,
                "intPrimitive", FilterOperator.EQUAL, 1,
                "string", FilterOperator.EQUAL, "jack",
                "intBoxed", FilterOperator.EQUAL, 2,
                "floatBoxed", FilterOperator.RANGE_CLOSED, 1.1d, 2.2d);

        SortedSet<FilterValueSetParam> copy = IndexTreeBuilder.copySortParameters(spec.getParameters());

        assertTrue(copy.first().getPropertyName().equals("intBoxed"));
        copy.remove(copy.first());

        assertTrue(copy.first().getPropertyName().equals("intPrimitive"));
        copy.remove(copy.first());

        assertTrue(copy.first().getPropertyName().equals("string"));
        copy.remove(copy.first());

        assertTrue(copy.first().getPropertyName().equals("floatBoxed"));
        copy.remove(copy.first());

        assertTrue(copy.first().getPropertyName().equals("doubleBoxed"));
        copy.remove(copy.first());

        assertTrue(copy.first().getPropertyName().equals("doublePrimitive"));
        copy.remove(copy.first());
    }

    public void testBuildWithMatch()
    {
        FilterCallbackSetNode topNode = new FilterCallbackSetNode();

        // Add some parameter-less expression
        FilterValueSet filterSpec = makeFilterValues();
        builder.add(filterSpec, testFilterCallback[0], topNode);
        assertTrue(topNode.contains(testFilterCallback[0]));

        // Attempt a match
        topNode.matchEvent(eventBean, matches);
        assertTrue(matches.size() == 1);
        matches.clear();

        // Add a filter that won't match, with a single parameter matching against an int
        filterSpec = makeFilterValues("intPrimitive", FilterOperator.EQUAL, 100);
        builder.add(filterSpec, testFilterCallback[1], topNode);
        assertTrue(topNode.getIndizes().size() == 1);
        assertTrue(topNode.getIndizes().get(0).size() == 1);

        // Match again
        topNode.matchEvent(eventBean, matches);
        assertTrue(matches.size() == 1);
        matches.clear();

        // Add a filter that will match
        filterSpec = makeFilterValues("intPrimitive", FilterOperator.EQUAL, 50);
        builder.add(filterSpec, testFilterCallback[2], topNode);
        assertTrue(topNode.getIndizes().size() == 1);
        assertTrue(topNode.getIndizes().get(0).size() == 2);

        // match
        topNode.matchEvent(eventBean, matches);
        assertTrue(matches.size() == 2);
        matches.clear();

        // Add some filter against a double
        filterSpec = makeFilterValues("doublePrimitive", FilterOperator.LESS, 1.1);
        builder.add(filterSpec, testFilterCallback[3], topNode);
        assertTrue(topNode.getIndizes().size() == 2);
        assertTrue(topNode.getIndizes().get(0).size() == 2);
        assertTrue(topNode.getIndizes().get(1).size() == 1);

        topNode.matchEvent(eventBean, matches);
        assertTrue(matches.size() == 3);
        matches.clear();

        filterSpec = makeFilterValues("doublePrimitive", FilterOperator.LESS_OR_EQUAL, 0.5);
        builder.add(filterSpec, testFilterCallback[4], topNode);
        assertTrue(topNode.getIndizes().size() == 3);
        assertTrue(topNode.getIndizes().get(0).size() == 2);
        assertTrue(topNode.getIndizes().get(1).size() == 1);
        assertTrue(topNode.getIndizes().get(2).size() == 1);

        topNode.matchEvent(eventBean, matches);
        assertTrue(matches.size() == 4);
        matches.clear();

        // Add an filterSpec against double and string
        filterSpec = makeFilterValues("doublePrimitive", FilterOperator.LESS, 1.1,
                                    "string", FilterOperator.EQUAL, "jack");
        builder.add(filterSpec, testFilterCallback[5], topNode);
        assertTrue(topNode.getIndizes().size() == 3);
        assertTrue(topNode.getIndizes().get(0).size() == 2);
        assertTrue(topNode.getIndizes().get(1).size() == 1);
        assertTrue(topNode.getIndizes().get(2).size() == 1);
        FilterCallbackSetNode nextLevelSetNode = (FilterCallbackSetNode) topNode.getIndizes().get(1).get(Double.valueOf(1.1));
        assertTrue(nextLevelSetNode != null);
        assertTrue(nextLevelSetNode.getIndizes().size() == 1);

        topNode.matchEvent(eventBean, matches);
        assertTrue(matches.size() == 5);
        matches.clear();

        filterSpec = makeFilterValues("doublePrimitive", FilterOperator.LESS, 1.1,
                                    "string", FilterOperator.EQUAL, "beta");
        builder.add(filterSpec, testFilterCallback[6], topNode);

        topNode.matchEvent(eventBean, matches);
        assertTrue(matches.size() == 5);
        matches.clear();

        filterSpec = makeFilterValues("doublePrimitive", FilterOperator.LESS, 1.1,
                                    "string", FilterOperator.EQUAL, "jack");
        builder.add(filterSpec, testFilterCallback[7], topNode);
        assertTrue(nextLevelSetNode.getIndizes().size() == 1);
        FilterCallbackSetNode nodeTwo = (FilterCallbackSetNode) nextLevelSetNode.getIndizes().get(0).get("jack");
        assertTrue(nodeTwo.getFilterCallbackCount() == 2);

        topNode.matchEvent(eventBean, matches);
        assertTrue(matches.size() == 6);
        matches.clear();

        // Try depth first
        filterSpec = makeFilterValues("string", FilterOperator.EQUAL, "jack",
                                    "longPrimitive", FilterOperator.EQUAL, 10L,
                                    "shortPrimitive", FilterOperator.EQUAL, (short) 20);
        builder.add(filterSpec, testFilterCallback[8], topNode);

        topNode.matchEvent(eventBean, matches);
        assertTrue(matches.size() == 7);
        matches.clear();

        // Add an filterSpec in the middle
        filterSpec = makeFilterValues("longPrimitive", FilterOperator.EQUAL, 10L,
                                    "string", FilterOperator.EQUAL, "jack");
        builder.add(filterSpec, testFilterCallback[9], topNode);

        filterSpec = makeFilterValues("longPrimitive", FilterOperator.EQUAL, 10L,
                                    "string", FilterOperator.EQUAL, "jim");
        builder.add(filterSpec, testFilterCallback[10], topNode);

        filterSpec = makeFilterValues("longPrimitive", FilterOperator.EQUAL, 10L,
                                    "string", FilterOperator.EQUAL, "joe");
        builder.add(filterSpec, testFilterCallback[11], topNode);

        topNode.matchEvent(eventBean, matches);
        assertTrue(matches.size() == 8);
        matches.clear();
    }

    public void testBuildMatchRemove()
    {
        FilterCallbackSetNode top = new FilterCallbackSetNode();

        // Add a parameter-less filter
        FilterValueSet filterSpecNoParams = makeFilterValues();
        IndexTreePath pathAddedTo = builder.add(filterSpecNoParams, testFilterCallback[0], top);

        // Try a match
        top.matchEvent(eventBean, matches);
        assertTrue(matches.size() == 1);
        matches.clear();

        // Remove filter
        builder.remove(testFilterCallback[0], pathAddedTo, top);

        // Match should not be found
        top.matchEvent(eventBean, matches);
        assertTrue(matches.size() == 0);
        matches.clear();

        // Add a depth-first filterSpec
        FilterValueSet filterSpecOne = makeFilterValues(
                "string", FilterOperator.EQUAL, "jack",
                "longPrimitive", FilterOperator.EQUAL, 10L,
                "shortPrimitive", FilterOperator.EQUAL, (short) 20);
        IndexTreePath pathAddedToOne = builder.add(filterSpecOne, testFilterCallback[1], top);

        FilterValueSet filterSpecTwo = makeFilterValues(
                "string", FilterOperator.EQUAL, "jack",
                "longPrimitive", FilterOperator.EQUAL, 10L,
                "shortPrimitive", FilterOperator.EQUAL, (short) 20);
        IndexTreePath pathAddedToTwo = builder.add(filterSpecTwo, testFilterCallback[2], top);

        FilterValueSet filterSpecThree = makeFilterValues(
                "string", FilterOperator.EQUAL, "jack",
                "longPrimitive", FilterOperator.EQUAL, 10L);
        IndexTreePath pathAddedToThree = builder.add(filterSpecThree, testFilterCallback[3], top);

        FilterValueSet filterSpecFour = makeFilterValues(
                "string", FilterOperator.EQUAL, "jack");
        IndexTreePath pathAddedToFour = builder.add(filterSpecFour, testFilterCallback[4], top);

        FilterValueSet filterSpecFive = makeFilterValues(
                "longPrimitive", FilterOperator.EQUAL, 10L);
        IndexTreePath pathAddedToFive = builder.add(filterSpecFive, testFilterCallback[5], top);

        top.matchEvent(eventBean, matches);
        assertTrue(matches.size() == 5);
        matches.clear();

        // Remove some of the nodes
        builder.remove(testFilterCallback[2], pathAddedToTwo, top);

        top.matchEvent(eventBean, matches);
        assertTrue(matches.size() == 4);
        matches.clear();

        // Remove some of the nodes
        builder.remove(testFilterCallback[4], pathAddedToFour, top);

        top.matchEvent(eventBean, matches);
        assertTrue(matches.size() == 3);
        matches.clear();

        // Remove some of the nodes
        builder.remove(testFilterCallback[5], pathAddedToFive, top);

        top.matchEvent(eventBean, matches);
        assertTrue(matches.size() == 2);
        matches.clear();

        // Remove some of the nodes
        builder.remove(testFilterCallback[1], pathAddedToOne, top);

        top.matchEvent(eventBean, matches);
        assertTrue(matches.size() == 1);
        matches.clear();

        // Remove some of the nodes
        builder.remove(testFilterCallback[3], pathAddedToThree, top);

        top.matchEvent(eventBean, matches);
        assertTrue(matches.size() == 0);
        matches.clear();
    }

    private FilterValueSet makeFilterValues(Object ... filterSpecArgs)
    {
        FilterSpec spec = SupportFilterSpecBuilder.build(eventType, filterSpecArgs);
        FilterValueSet filterValues = spec.getValueSet(null);
        return filterValues;
    }
}
