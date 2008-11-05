/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.type;

import java.io.StringWriter;

/**
 * Represents a variable as a parameter.
 */
public class VariableParameter implements EPLParameterType
{
    private String variableName;

    /**
     * Ctor.
     * @param variableName the name of the variable
     */
    public VariableParameter(String variableName)
    {
        this.variableName = variableName;
    }

    /**
     * Returns the variable name.
     * @return variable name
     */
    public String getVariableName()
    {
        return variableName;
    }

    public void toEPL(StringWriter writer)
    {
        writer.write(variableName);
    }
}
