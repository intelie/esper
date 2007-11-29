package net.esper.eql.variable;

import junit.framework.TestCase;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TestVersionedValueList extends TestCase
{
    private VersionedValueList<String> list;

    public void setUp()
    {
        list = new VersionedValueList<String>("abc", 2, "a", new ReentrantReadWriteLock().readLock(), 10, 5);
    }

    public void testFlow()
    {
        tryInvalid(0);
        tryInvalid(1);
        assertEquals("a", list.getVersion(2));
        assertEquals("a", list.getVersion(3));

        list.addValue(4, "b");
        tryInvalid(1);
        assertEquals("a", list.getVersion(2));
        assertEquals("a", list.getVersion(3));
        assertEquals("b", list.getVersion(4));
        assertEquals("b", list.getVersion(5));

        list.addValue(6, "c");
        tryInvalid(1);
        assertEquals("a", list.getVersion(2));
        assertEquals("a", list.getVersion(3));
        assertEquals("b", list.getVersion(4));
        assertEquals("b", list.getVersion(5));
        assertEquals("c", list.getVersion(6));
        assertEquals("c", list.getVersion(7));

        list.addValue(7, "d");
        tryInvalid(1);
        assertEquals("a", list.getVersion(2));
        assertEquals("a", list.getVersion(3));
        assertEquals("b", list.getVersion(4));
        assertEquals("b", list.getVersion(5));
        assertEquals("c", list.getVersion(6));
        assertEquals("d", list.getVersion(7));
        assertEquals("d", list.getVersion(8));

        list.addValue(9, "e");
        tryInvalid(1);
        assertEquals("a", list.getVersion(2));
        assertEquals("a", list.getVersion(3));
        assertEquals("b", list.getVersion(4));
        assertEquals("b", list.getVersion(5));
        assertEquals("c", list.getVersion(6));
        assertEquals("d", list.getVersion(7));
        assertEquals("d", list.getVersion(8));
        assertEquals("e", list.getVersion(9));
        assertEquals("e", list.getVersion(10));
    }

    public void testHighWatermark()
    {
        for (int i = 0; i < 10; i++)
        {
            char c = 'a';
            c += i;
            list.addValue(i, Character.toString(c));
        }
        assertEquals(9, list.getOlderVersions().size());

        assertEquals("a", list.getVersion(0));
        assertEquals("b", list.getVersion(1));
        assertEquals("c", list.getVersion(2));
        assertEquals("d", list.getVersion(3));
        assertEquals("e", list.getVersion(4));
        assertEquals("f", list.getVersion(5));

        list.addValue(15, "x");
        assertEquals(5, list.getOlderVersions().size());
        
        tryInvalid(0);
        tryInvalid(1);
        tryInvalid(2);
        tryInvalid(3);
        assertEquals("e", list.getVersion(4));
        assertEquals("f", list.getVersion(5));
    }

    public void tryInvalid(int version)
    {
        try
        {
            list.getVersion(version);
            fail();
        }
        catch (IllegalStateException ex)
        {
        }
    }
}
