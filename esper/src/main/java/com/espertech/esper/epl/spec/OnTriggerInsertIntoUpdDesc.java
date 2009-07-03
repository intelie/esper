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

import java.util.List;
import java.util.ArrayList;

/**
 * Specification for the on-insert into update statement.
 */
public class OnTriggerInsertIntoUpdDesc extends OnTriggerDesc
{
    private final List<OnTriggerSetAssignment> assignments;
    private ExprNode optionalWhereClause;

    public OnTriggerInsertIntoUpdDesc(List<OnTriggerSetAssignment> assignments, ExprNode optionalWhereClause) {
        super(OnTriggerType.ON_INSERT_INTO_UPD);
        this.assignments = assignments;
        this.optionalWhereClause = optionalWhereClause;
    }

    /**
     * Returns a list of all variables assignment by the on-set
     * @return list of assignments
     */
    public List<OnTriggerSetAssignment> getAssignments()
    {
        return assignments;
    }

    public ExprNode getOptionalWhereClause() {
        return optionalWhereClause;
    }

    public void setOptionalWhereClause(ExprNode optionalWhereClause) {
        this.optionalWhereClause = optionalWhereClause;
    }
}
