/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.spec;

import com.espertech.esper.epl.expression.ExprNode;

import java.io.Serializable;

/**
 * Specification for the merge statement insert/update/delete-part.
 */
public abstract class OnTriggerMergeEntry implements Serializable
{
    private static final long serialVersionUID = 1528981268472654745L;

    private ExprNode optionalMatchCond;

    protected OnTriggerMergeEntry(ExprNode optionalMatchCond) {
        this.optionalMatchCond = optionalMatchCond;
    }

    public ExprNode getOptionalMatchCond() {
        return optionalMatchCond;
    }

    public void setOptionalMatchCond(ExprNode optionalMatchCond) {
        this.optionalMatchCond = optionalMatchCond;
    }
}

