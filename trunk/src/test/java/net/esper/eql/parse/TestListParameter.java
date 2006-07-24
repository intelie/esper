package net.esper.eql.parse;

import junit.framework.TestCase;

import java.util.Set;

import net.esper.support.util.ArrayAssertionUtil;

public class TestListParameter extends TestCase
{
    private ListParameter listParam;

    public void setUp()
    {
        listParam = new ListParameter();
        listParam.add(new IntParameter(5));
        listParam.add(new FrequencyParameter(3));
    }

    public void testIsWildcard()
    {
        // Wildcard is expected to make only a best-guess effort, not be perfect
        assertTrue(listParam.isWildcard(5, 5));
        assertFalse(listParam.isWildcard(6, 10));
    }

    public void testGetValues()
    {
        Set<Integer> result = listParam.getValuesInRange(1, 8);
        ArrayAssertionUtil.assertEqualsAnyOrder(new int[] {3, 5, 6}, result);
    }
}
