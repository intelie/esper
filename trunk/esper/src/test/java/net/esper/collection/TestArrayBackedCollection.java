package net.esper.collection;

import junit.framework.TestCase;
import net.esper.support.util.ArrayAssertionUtil;

public class TestArrayBackedCollection extends TestCase
{
    private ArrayBackedCollection<Integer> coll;

    public void setUp()
    {
        coll = new ArrayBackedCollection<Integer>(5);
    }

    public void testGet()
    {
        assertEquals(0, coll.size());
        assertEquals(5, coll.getArray().length);

        coll.add(5);
        ArrayAssertionUtil.assertEqualsExactOrder(new Object[] {5, null, null, null, null}, coll.getArray());
        coll.add(4);
        ArrayAssertionUtil.assertEqualsExactOrder(new Object[] {5, 4, null, null, null}, coll.getArray());
        assertEquals(2, coll.size());

        coll.add(1);
        coll.add(2);
        coll.add(3);
        ArrayAssertionUtil.assertEqualsExactOrder(new Object[] {5, 4, 1, 2, 3}, coll.getArray());
        assertEquals(5, coll.size());

        coll.add(10);
        ArrayAssertionUtil.assertEqualsExactOrder(new Object[] {5, 4, 1, 2, 3, 10, null, null, null, null}, coll.getArray());
        assertEquals(6, coll.size());

        coll.add(11);
        coll.add(12);
        coll.add(13);
        coll.add(14);
        coll.add(15);
        ArrayAssertionUtil.assertEqualsExactOrder(new Object[] {5, 4, 1, 2, 3, 10, 11, 12, 13, 14, 15,
                null,null,null,null,null,null,null,null,null}, coll.getArray());
        assertEquals(11, coll.size());

        coll.clear();
        assertEquals(0, coll.size());
    }
}
