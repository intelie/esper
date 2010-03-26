package com.espertech.esper.type;

import junit.framework.TestCase;

import java.util.Set;

import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.type.FrequencyParameter;

public class TestFrequencyParameter extends TestCase
{
    public void testInvalid()
    {
        try
        {
            new FrequencyParameter(0);
            fail();
        }
        catch (IllegalArgumentException ex)
        {
            // Expected
        }
    }

    public void testIsWildcard()
    {
        FrequencyParameter freq = new FrequencyParameter(1);
        assertTrue(freq.isWildcard(1,10));

        freq = new FrequencyParameter(2);
        assertFalse(freq.isWildcard(1,20));
    }

    public void testGetValues()
    {
        FrequencyParameter freq = new FrequencyParameter(3);
        Set<Integer> result = freq.getValuesInRange(1, 8);
        ArrayAssertionUtil.assertEqualsAnyOrder(new int[] {3, 6}, result);

        freq = new FrequencyParameter(4);
        result = freq.getValuesInRange(6, 16);
        ArrayAssertionUtil.assertEqualsAnyOrder(new int[] {8, 12, 16}, result);

        freq = new FrequencyParameter(4);
        result = freq.getValuesInRange(0, 14);
        ArrayAssertionUtil.assertEqualsAnyOrder(new int[] {0, 4, 8, 12}, result);

        freq = new FrequencyParameter(1);
        result = freq.getValuesInRange(2, 5);
        ArrayAssertionUtil.assertEqualsAnyOrder(new int[] {2, 3, 4, 5}, result);
    }
}
