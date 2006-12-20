package net.esper.filter;

import junit.framework.TestCase;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestFilterSpecParamComparator extends TestCase
{
    private FilterSpecParamComparator comparator;

    public void setUp()
    {
        comparator = new FilterSpecParamComparator();
    }

    public void testCompareOneByOne()
    {
        FilterValueSetParamImpl param1 = new FilterValueSetParamImpl("a", FilterOperator.EQUAL, null);
        FilterValueSetParamImpl param2 = new FilterValueSetParamImpl("b", FilterOperator.EQUAL, null);
        FilterValueSetParamImpl param3 = new FilterValueSetParamImpl("c", FilterOperator.EQUAL, null);
        FilterValueSetParamImpl param4 = new FilterValueSetParamImpl("d", FilterOperator.RANGE_CLOSED, null);
        FilterValueSetParamImpl param5 = new FilterValueSetParamImpl("e", FilterOperator.RANGE_CLOSED, null);
        FilterValueSetParamImpl param6 = new FilterValueSetParamImpl("e", FilterOperator.RANGE_CLOSED, null);
        FilterValueSetParamImpl param7 = new FilterValueSetParamImpl("f", FilterOperator.GREATER, null);
        FilterValueSetParamImpl param8 = new FilterValueSetParamImpl("g", FilterOperator.NOT_EQUAL, null);

        // Compare same comparison types
        assertTrue(comparator.compare(param1, param2) == -1);
        assertTrue(comparator.compare(param2, param1) == 1);
        assertTrue(comparator.compare(param3, param2) == 1);
        assertTrue(comparator.compare(param2, param2) == 0);
        assertTrue(comparator.compare(param8, param1) == 1);
        assertTrue(comparator.compare(param1, param8) == -1);

        assertTrue(comparator.compare(param4, param5) == -1);
        assertTrue(comparator.compare(param5, param4) == 1);
        assertTrue(comparator.compare(param4, param4) == 0);
        assertTrue(comparator.compare(param5, param6) == 0);

        // Compare across comparison types
        assertTrue(comparator.compare(param7, param6) == 1);
        assertTrue(comparator.compare(param6, param7) == -1);

        assertTrue(comparator.compare(param7, param1) == 1);
        assertTrue(comparator.compare(param1, param7) == -1);

        assertTrue(comparator.compare(param4, param1) == 1);
        assertTrue(comparator.compare(param1, param4) == -1);
    }

    public void testCompareAll()
    {
        SortedSet<FilterValueSetParam> sorted = new TreeSet<FilterValueSetParam>(comparator);

        FilterValueSetParam spec[] = new FilterValueSetParam[FilterOperator.values().length];
        for (int i = 0; i < FilterOperator.values().length; i++)
        {
            FilterOperator op = FilterOperator.values()[i];
            spec[i] = new FilterValueSetParamImpl("somename", op, null);

            // Add to sorted collection
            sorted.add(spec[i]);
        }

        assertEquals(FilterOperator.EQUAL, sorted.first().getFilterOperator());
        assertEquals(FilterOperator.NOT_EQUAL, sorted.last().getFilterOperator());

        log.debug(".testCompareAll " + Arrays.toString(sorted.toArray()));
    }

    private static final Log log = LogFactory.getLog(TestFilterSpecParamComparator.class);
}
