package com.espertech.esper.core;

import junit.framework.TestCase;

import java.io.StringWriter;

import com.espertech.esper.core.EPStatementObjectModelHelper;

public class TestEPStatementObjectModelHelper extends TestCase
{
    public void testRenderEPL()
    {
        assertEquals("null", tryConstant(null));
        assertEquals("\"\"", tryConstant(""));
        assertEquals("1", tryConstant(1));
        assertEquals("\"abc\"", tryConstant("abc"));
    }

    private String tryConstant(Object value)
    {
        StringWriter writer = new StringWriter();
        EPStatementObjectModelHelper.renderEPL(writer, value);
        return writer.toString();
    }
}
