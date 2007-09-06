package net.esper.client.soda;

import junit.framework.TestCase;

import java.io.StringWriter;

public class TestConstantExpression extends TestCase
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
        ConstantExpression.renderEQL(writer, value);
        return writer.toString();
    }
}
