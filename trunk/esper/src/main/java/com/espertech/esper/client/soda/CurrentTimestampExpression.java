/**************************************************************************************
 * Copyright (C) 2007 Thomas Bernhardt. All rights reserved.                          *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.client.soda;

import java.io.StringWriter;

/**
 * Current timestamp supplies the current engine time in an expression.
 */
public class CurrentTimestampExpression extends ExpressionBase
{
    /**
     * Ctor.
     */
    public CurrentTimestampExpression()
    {
    }

    public void toEQL(StringWriter writer)
    {
        writer.write("current_timestamp()");
    }
}
