package net.esper.type;

import net.esper.support.util.ArrayAssertionUtil;
import net.esper.type.IntParameter;

import java.util.Set;

import junit.framework.TestCase;

public class TestIntParameter extends TestCase
{
    public void testIsWildcard()
    {
        IntParameter intParam = new IntParameter(5);
        assertTrue(intParam.isWildcard(5,5));
        assertFalse(intParam.isWildcard(4,5));
        assertFalse(intParam.isWildcard(5,6));
        assertFalse(intParam.isWildcard(4,6));
    }

    public void testGetValues()
    {
        IntParameter intParam = new IntParameter(3);
        Set<Integer> result = intParam.getValuesInRange(1, 8);
        ArrayAssertionUtil.assertEqualsAnyOrder(new int[] {3}, result);

        result = intParam.getValuesInRange(1, 2);
        ArrayAssertionUtil.assertEqualsAnyOrder(new int[] {}, result);

        result = intParam.getValuesInRange(4, 10);
        ArrayAssertionUtil.assertEqualsAnyOrder(new int[] {}, result);

        result = intParam.getValuesInRange(1, 3);
        ArrayAssertionUtil.assertEqualsAnyOrder(new int[] {3}, result);

        result = intParam.getValuesInRange(3, 5);
        ArrayAssertionUtil.assertEqualsAnyOrder(new int[] {3}, result);
    }
}
