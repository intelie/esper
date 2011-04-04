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
 * Split-stream description.
 */
public class OnTriggerSplitStream implements Serializable
{
    private static final long serialVersionUID = 7836326460852522622L;
    private InsertIntoDesc insertInto;
    private SelectClauseSpecRaw selectClause;
    private ExprNode whereClause;

    /**
     * Ctor.
     * @param insertInto the insert-into clause
     * @param selectClause the select-clause
     * @param whereClause where-expression or null
     */
    public OnTriggerSplitStream(InsertIntoDesc insertInto, SelectClauseSpecRaw selectClause, ExprNode whereClause)
    {
        this.insertInto = insertInto;
        this.selectClause = selectClause;
        this.whereClause = whereClause;
    }

    /**
     * Returns the insert-into clause.
     * @return insert-into
     */
    public InsertIntoDesc getInsertInto()
    {
        return insertInto;
    }

    /**
     * Returns the select clause.
     * @return select
     */
    public SelectClauseSpecRaw getSelectClause()
    {
        return selectClause;
    }

    /**
     * Returns the where clause or null if not defined
     * @return where clause
     */
    public ExprNode getWhereClause()
    {
        return whereClause;
    }
}
