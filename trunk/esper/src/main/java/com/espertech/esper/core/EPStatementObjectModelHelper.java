package com.espertech.esper.core;

import java.io.StringWriter;

/**
 * Helper methods for use by the statement object model.
 */
public class EPStatementObjectModelHelper
{
    /**
     * Renders a constant as an EPL.
     * @param writer to output to
     * @param constant to render
     */
    public static void renderEPL(StringWriter writer, Object constant)
    {
        if (constant == null)
        {
            writer.write("null");
            return;
        }

        if ((constant instanceof String) ||
            (constant instanceof Character))
        {
            writer.write('\"');
            writer.write(constant.toString());
            writer.write('\"');
        }
        else
        {
            writer.write(constant.toString());
        }
    }
}
