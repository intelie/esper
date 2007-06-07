package net.esper.util;

import junit.framework.TestCase;

import java.io.StringWriter;
import java.io.PrintWriter;

public class TestIndentWriter extends TestCase
{
    private final static String NEWLINE = System.getProperty("line.separator");
    private StringWriter buf;
    private IndentWriter writer;

    public void setUp()
    {
        buf = new StringWriter();
        writer = new IndentWriter(new PrintWriter(buf), 0, 2);
    }

    public void testWrite()
    {
        writer.println("a");
        assertWritten("a");

        writer.incrIndent();
        writer.println("a");
        assertWritten("  a");

        writer.incrIndent();
        writer.println("a");
        assertWritten("    a");

        writer.decrIndent();
        writer.println("a");
        assertWritten("  a");

        writer.decrIndent();
        writer.println("a");
        assertWritten("a");

        writer.decrIndent();
        writer.println("a");
        assertWritten("a");
    }

    public void testCtor()
    {
        try
        {
            new IndentWriter(new PrintWriter(buf), 0, -1);
            fail();
        }
        catch (IllegalArgumentException ex)
        {
            // expected
        }
        
        try
        {
            new IndentWriter(new PrintWriter(buf), -1, 11);
            fail();
        }
        catch (IllegalArgumentException ex)
        {
            // expected
        }
    }

    private void assertWritten(String text)
    {
        assertEquals(text + NEWLINE, buf.toString());
        StringBuffer buffer = buf.getBuffer();
        buf.getBuffer().delete(0, buffer.length());
    }

}
