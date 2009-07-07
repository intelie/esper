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
import com.espertech.esper.util.MetaDefItem;

import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

/**
 * Specification for the update statement.
 */
public class UpdateDesc implements MetaDefItem, Serializable
{
    private final String optionalStreamName;
    private final List<OnTriggerSetAssignment> assignments;
    private ExprNode optionalWhereClause;

    public UpdateDesc(String optionalStreamName, List<OnTriggerSetAssignment> assignments, ExprNode optionalWhereClause) {
        this.optionalStreamName = optionalStreamName;
        this.assignments = assignments;
        this.optionalWhereClause = optionalWhereClause;
    }

    /**
     * Returns a list of all assignment
     * @return list of assignments
     */
    public List<OnTriggerSetAssignment> getAssignments()
    {
        return assignments;
    }

    public String getOptionalStreamName() {
        return optionalStreamName;
    }

    public ExprNode getOptionalWhereClause() {
        return optionalWhereClause;
    }

    public void setOptionalWhereClause(ExprNode optionalWhereClause) {
        this.optionalWhereClause = optionalWhereClause;
    }
}
