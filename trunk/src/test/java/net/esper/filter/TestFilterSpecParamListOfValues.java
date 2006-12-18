package net.esper.filter;

import junit.framework.TestCase;

import java.util.Arrays;

public class TestFilterSpecParamListOfValues extends TestCase
{
    private FilterSpecParamListOfValues values;

    public void testEquals()
    {
        values = new FilterSpecParamListOfValues("a", FilterOperator.IN_LIST_OF_VALUES, Arrays.asList(new Object[] {"A", "B"}));
        FilterSpecParamListOfValues values2 = new FilterSpecParamListOfValues("a", FilterOperator.IN_LIST_OF_VALUES, Arrays.asList(new Object[] {"A"}));
        FilterSpecParamListOfValues values3 = new FilterSpecParamListOfValues("a", FilterOperator.IN_LIST_OF_VALUES, Arrays.asList(new Object[] {"A", "B"}));
        FilterSpecParamListOfValues values4 = new FilterSpecParamListOfValues("a", FilterOperator.IN_LIST_OF_VALUES, Arrays.asList(new Object[] {"A", "C"}));

        assertFalse(values.equals(new FilterSpecParamConstant("a", FilterOperator.EQUAL, "a")));
        assertFalse(values.equals(values2));
        assertTrue(values.equals(values3));
        assertFalse(values.equals(values4));
    }


}
