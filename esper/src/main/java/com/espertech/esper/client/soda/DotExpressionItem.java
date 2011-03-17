/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.client.soda;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.List;

/**
 * Dot-expresson items is for use in "(inner_expression).dot_expression".
 */
public class DotExpressionItem implements Serializable
{
    private String name;
    private List<Expression> parameters;
    private boolean property; // relevant if there are no parameters

    /**
     * Ctor.
     */
    public DotExpressionItem() {
    }

    public DotExpressionItem(String name, List<Expression> parameters, boolean isProperty) {
        this.name = name;
        this.parameters = parameters;
        this.property = isProperty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Expression> getParameters() {
        return parameters;
    }

    public void setParameters(List<Expression> parameters) {
        this.parameters = parameters;
    }

    public boolean isProperty() {
        return property;
    }

    public void setProperty(boolean property) {
        this.property = property;
    }

    public ExpressionPrecedenceEnum getPrecedence()
    {
        return ExpressionPrecedenceEnum.MINIMUM;
    }

    protected static void render(List<DotExpressionItem> chain, StringWriter writer, boolean prefixDot) {
        String delimiterOuter = prefixDot ? "." : "";
        for (DotExpressionItem item : chain)
        {
            writer.write(delimiterOuter);
            writer.write(item.name);

            if (!item.isProperty() || !item.parameters.isEmpty()) {
                writer.write("(");
                if (!item.parameters.isEmpty()) {
                    String delimiter = "";
                    for (Expression param : item.parameters) {
                        writer.write(delimiter);
                        delimiter = ", ";
                        param.toEPL(writer, ExpressionPrecedenceEnum.MINIMUM);
                    }
                }
                writer.write(")");
            }
            delimiterOuter = ".";
        }
    }
}
