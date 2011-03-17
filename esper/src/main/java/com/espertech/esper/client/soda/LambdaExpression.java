/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.client.soda;

import java.io.StringWriter;
import java.util.List;

public class LambdaExpression extends ExpressionBase
{
    private List<String> parameters;

    /**
     * Ctor.
     */
    public LambdaExpression() {
    }

    public LambdaExpression(List<String> parameters) {
        this.parameters = parameters;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }

    public ExpressionPrecedenceEnum getPrecedence() {
        return ExpressionPrecedenceEnum.MINIMUM;
    }

    public void toPrecedenceFreeEPL(StringWriter writer) {
        if (parameters.size() > 1) {
            writer.append("(");
            String delimiter = "";
            for (String parameter : parameters) {
                writer.append(delimiter);
                writer.append(parameter);
                delimiter = ", ";
            }
            writer.append(")");
        }
        else {
            writer.append(parameters.get(0));
        }
        writer.append(" => ");
        this.getChildren().get(0).toEPL(writer, getPrecedence());
    }
}
