package net.esper.collection;

import junit.framework.TestCase;
import net.esper.support.util.ArrayAssertionUtil;

import java.util.ArrayList;

public class TestThreadWorkQueue extends TestCase
{
    private ThreadWorkQueue queue;

    public void setUp()
    {
        queue = new ThreadWorkQueue();
    }

    public void testFlow()
    {
        queue.add("a");
        queue.add("b");
        compare(new String[] { "a", "b" });

        queue.addFront("0");
        queue.add("c");
        compare(new String[] { "0", "c" });

        queue.add("d");
        queue.addFront("1");
        compare(new String[] { "1", "d" });

        queue.addFront("e");
        queue.addFront("2");
        compare(new String[] { "2", "e" });

        queue.add("a");
        queue.addFront("0");
        queue.add("b");
        queue.addFront("1");
        queue.add("c");
        queue.addFront("2");
        compare(new String[] { "2", "1", "0", "a", "b", "c" });
    }

    private void compare(String[] results)
    {
        ArrayList<String> result = new ArrayList<String>();

        String entry;
        while ((entry = (String) queue.next()) != null)
        {
            result.add(entry);
        }

        ArrayAssertionUtil.assertEqualsExactOrder(result.toArray(), results);
    }
}
