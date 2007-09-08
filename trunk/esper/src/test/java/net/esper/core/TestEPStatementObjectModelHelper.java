package net.esper.core;

import junit.framework.TestCase;

import java.io.StringWriter;

import net.esper.core.EPStatementObjectModelHelper;

public class TestEPStatementObjectModelHelper extends TestCase
{
    public void testRenderEQL()
    {
        assertEquals("null", tryConstant(null));
        assertEquals("\"\"", tryConstant(""));
        assertEquals("1", tryConstant(1));
        assertEquals("\"abc\"", tryConstant("abc"));
    }

    private String tryConstant(Object value)
    {
        StringWriter writer = new StringWriter();
        EPStatementObjectModelHelper.renderEQL(writer, value);
        return writer.toString();
    }
}
