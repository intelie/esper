package net.esper.eql.parse;

import junit.framework.TestCase;

import java.util.Set;

public class TestWildcardParameter extends TestCase
{
    private WildcardParameter wildcard;

    public void setUp()
    {
        wildcard = new WildcardParameter();
    }

    public void testIsWildcard()
    {
        assertTrue(wildcard.isWildcard(1,10));
    }

    public void testGetValuesInRange()
    {
        Set<Integer> result = wildcard.getValuesInRange(1, 10);
        for (int i = 1; i <= 10; i++)
        {
            assertTrue(result.contains(i));
        }
        assertEquals(10, result.size());
    }
}
