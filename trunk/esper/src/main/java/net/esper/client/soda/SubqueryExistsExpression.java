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
 * Exists-expression for a set of values returned by a lookup.
 */
public class SubqueryExistsExpression extends ExpressionBase
{
    private EPStatementObjectModel model;

    /**
     * Ctor - for use to create an expression tree, without child expression.
     * @param model is the lookup statement object model
     */
    public SubqueryExistsExpression(EPStatementObjectModel model)
    {
        this.model = model;
    }

    public void toEQL(StringWriter writer)
    {
        writer.write("exists (");
        writer.write(model.toEQL());
        writer.write(')');
    }

    /**
     * Returns the lookup statement object model.
     * @return lookup model
     */
    public EPStatementObjectModel getModel()
    {
        return model;
    }

    /**
     * Sets the lookup statement object model.
     * @param model is the lookup model to set
     */
    public void setModel(EPStatementObjectModel model)
    {
        this.model = model;
    }
}
