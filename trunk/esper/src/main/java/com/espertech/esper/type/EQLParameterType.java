package com.espertech.esper.type;

import java.io.StringWriter;

/**
 * Interface for parameter types that can represent themselves as an EQL syntax.
 */
public interface EQLParameterType
{
    /**
     * Returns the EQL representation of the parameter.
     * @param writer for output to
     */
    public void toEQL(StringWriter writer);
}
