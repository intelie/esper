package com.espertech.esper.type;

import java.io.StringWriter;

/**
 * Interface for parameter types that can represent themselves as an EPL syntax.
 */
public interface EPLParameterType
{
    /**
     * Returns the EPL representation of the parameter.
     * @param writer for output to
     */
    public void toEPL(StringWriter writer);
}
