package com.espertech.esper.type;

import junit.framework.TestCase;

import java.util.Set;

import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.type.RangeParameter;

public class TestRangeParameter extends TestCase
{
    public void testIsWildcard()
    {
        RangeParameter rangeParameter = new RangeParameter(10, 20);
        assertTrue(rangeParameter.isWildcard(10, 20));
        assertFalse(rangeParameter.isWildcard(11, 20));
        assertFalse(rangeParameter.isWildcard(10, 19));
        assertTrue(rangeParameter.isWildcard(9, 21));
    }

    public void testGetValues()
    {
        RangeParameter rangeParameter = new RangeParameter(0, 5);
        Set<Integer> values = rangeParameter.getValuesInRange(1, 3);
        ArrayAssertionUtil.assertEqualsAnyOrder(new int[] {1, 2, 3}, values);

        values = rangeParameter.getValuesInRange(-2, 3);
        ArrayAssertionUtil.assertEqualsAnyOrder(new int[] {0, 1, 2, 3}, values);
        
        values = rangeParameter.getValuesInRange(4, 6);
        ArrayAssertionUtil.assertEqualsAnyOrder(new int[] {4, 5}, values);

        values = rangeParameter.getValuesInRange(10, 20);
        ArrayAssertionUtil.assertEqualsAnyOrder(new int[] {}, values);

        values = rangeParameter.getValuesInRange(-7, -1);
        ArrayAssertionUtil.assertEqualsAnyOrder(new int[] {}, values);
    }
}
