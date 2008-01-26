package net.esper.filter;

import junit.framework.TestCase;
import net.esper.support.filter.SupportEventEvaluator;
import net.esper.support.bean.SupportBean;
import net.esper.support.event.SupportEventBeanFactory;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.collection.MultiKeyUntyped;

import java.util.List;
import java.util.LinkedList;

public class TestFilterParamIndexNotIn extends TestCase
{
    private SupportEventEvaluator testEvaluators[];
    private SupportBean testBean;
    private EventBean testEventBean;
    private EventType testEventType;
    private List<FilterHandle> matchesList;

    public void setUp()
    {
        testEvaluators = new SupportEventEvaluator[4];
        for (int i = 0; i < testEvaluators.length; i++)
        {
            testEvaluators[i] = new SupportEventEvaluator();
        }
        
        testBean = new SupportBean();
        testEventBean = SupportEventBeanFactory.createObject(testBean);
        testEventType = testEventBean.getEventType();
        matchesList = new LinkedList<FilterHandle>();
    }

    public void testIndex()
    {
        FilterParamIndexNotIn index = new FilterParamIndexNotIn("longBoxed", testEventType);
        assertEquals(FilterOperator.NOT_IN_LIST_OF_VALUES, index.getFilterOperator());

        index.put(new MultiKeyUntyped(new Object[] {2L, 5L}), testEvaluators[0]);
        index.put(new MultiKeyUntyped(new Object[] {3L, 4L, 5L}), testEvaluators[1]);
        index.put(new MultiKeyUntyped(new Object[] {1L, 4L, 5L}), testEvaluators[2]);
        index.put(new MultiKeyUntyped(new Object[] {2L, 5L}), testEvaluators[3]);

        verify(index, 0L, new boolean[] {true, true, true, true});
        verify(index, 1L, new boolean[] {true, true, false, true});
        verify(index, 2L, new boolean[] {false, true, true, false});
        verify(index, 3L, new boolean[] {true, false, true, true});
        verify(index, 4L, new boolean[] {true, false, false, true});
        verify(index, 5L, new boolean[] {false, false, false, false});
        verify(index, 6L, new boolean[] {true, true, true, true});

        MultiKeyUntyped inList = new MultiKeyUntyped(new Object[] {3L, 4L, 5L});
        assertEquals(testEvaluators[1], index.get(inList));
        assertTrue(index.getReadWriteLock() != null);
        assertTrue(index.remove(inList));
        assertFalse(index.remove(inList));
        assertEquals(null, index.get(inList));

        // now that {3,4,5} is removed, verify results again
        verify(index, 0L, new boolean[] {true, false, true, true});
        verify(index, 1L, new boolean[] {true, false, false, true});
        verify(index, 2L, new boolean[] {false, false, true, false});
        verify(index, 3L, new boolean[] {true, false, true, true});
        verify(index, 4L, new boolean[] {true, false, false, true});
        verify(index, 5L, new boolean[] {false, false, false, false});
        verify(index, 6L, new boolean[] {true, false, true, true});
        
        try
        {
            index.put("a", testEvaluators[0]);
            assertTrue(false);
        }
        catch (Exception ex)
        {
            // Expected
        }
    }

    private void verify(FilterParamIndexBase index, Long testValue, boolean[] expected)
    {
        testBean.setLongBoxed(testValue);
        index.matchEvent(testEventBean, matchesList);
        for (int i = 0; i < expected.length; i++)
        {
            assertEquals(expected[i], testEvaluators[i].getAndResetCountInvoked() == 1);
        }
    }
}
