/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.client.soda;

import java.io.StringWriter;

/**
 * Pattern 'every' expression that controls the lifecycle of pattern sub-expressions. 
 */
public class PatternEveryExpr extends PatternExprBase
{
    /**
     * Ctor - for use to create a pattern expression tree, without pattern child expression.
     */
    public PatternEveryExpr()
    {
    }

    /**
     * Ctor.
     * @param inner is the pattern expression to control lifecycle on
     */
    public PatternEveryExpr(PatternExpr inner)
    {
        addChild(inner);
    }

    public void toEQL(StringWriter writer)
    {
        writer.write("every (");
        this.getChildren().get(0).toEQL(writer);
        writer.write(')');
    }
}
