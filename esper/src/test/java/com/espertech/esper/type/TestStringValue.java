package com.espertech.esper.type;

import junit.framework.TestCase;

public class TestStringValue extends TestCase
{
    public void testParse()
    {
        assertEquals("a", StringValue.parseString("\"a\""));
        assertEquals("", StringValue.parseString("\"\""));
        assertEquals("", StringValue.parseString("''"));
        assertEquals("b", StringValue.parseString("'b'"));
    }

    public void testInvalid()
    {
        tryInvalid("\"");
        tryInvalid("'");
    }

    private void tryInvalid(String invalidString)
    {
        try
        {
            StringValue.parseString(invalidString);
        }
        catch (IllegalArgumentException ex)
        {
            // Expected exception
        }

    }
}
