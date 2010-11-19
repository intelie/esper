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

public class OnMergeMatchedDeleteAction implements OnMergeMatchedAction
{
    private static final long serialVersionUID = 0L;

    private Expression optionalCondition;

    public OnMergeMatchedDeleteAction(Expression optionalCondition) {
        this.optionalCondition = optionalCondition;
    }

    public OnMergeMatchedDeleteAction() {
    }

    public Expression getOptionalCondition() {
        return optionalCondition;
    }

    public void setOptionalCondition(Expression optionalCondition) {
        this.optionalCondition = optionalCondition;
    }

    @Override
    public void toEPL(StringWriter writer) {
        writer.write("when matched");
        if (optionalCondition != null) {
            writer.write(" and ");
            optionalCondition.toEPL(writer, ExpressionPrecedenceEnum.MINIMUM);
        }
        writer.write(" then delete");
    }
}