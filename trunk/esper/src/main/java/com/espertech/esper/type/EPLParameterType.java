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
