package com.espertech.esper.util;

import junit.framework.TestCase;

public class TestIndent extends TestCase
{
    public void testIndent()
    {
        assertEquals("", Indent.indent(0));
        assertEquals(" ", Indent.indent(1));
        assertEquals("  ", Indent.indent(2));

        try
        {
            Indent.indent(-1);
            fail();
        }
        catch (IllegalArgumentException ex)
        {
            // expected
        }
    }

}
