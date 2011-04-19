/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.join.plan;

import com.espertech.esper.epl.expression.ExprConstantNode;
import com.espertech.esper.epl.expression.ExprNode;

public class QueryGraphValueEntryHashKeyedExpr extends QueryGraphValueEntryHashKeyed
{
    private final boolean requiresKey;

    public QueryGraphValueEntryHashKeyedExpr(ExprNode keyExpr, boolean requiresKey) {
        super(keyExpr);
        this.requiresKey = requiresKey;
    }

    public boolean isRequiresKey() {
        return requiresKey;
    }

    public boolean isConstant() {
        return super.getKeyExpr() instanceof ExprConstantNode;
    }
}

