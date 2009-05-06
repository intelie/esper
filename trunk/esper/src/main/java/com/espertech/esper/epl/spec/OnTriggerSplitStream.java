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

public class OnTriggerSplitStream
{
    private InsertIntoDesc insertInto;
    private SelectClauseSpecRaw selectClause;
    private ExprNode whereClause;

    public OnTriggerSplitStream(InsertIntoDesc insertInto, SelectClauseSpecRaw selectClause, ExprNode whereClause)
    {
        this.insertInto = insertInto;
        this.selectClause = selectClause;
        this.whereClause = whereClause;
    }

    public InsertIntoDesc getInsertInto()
    {
        return insertInto;
    }

    public SelectClauseSpecRaw getSelectClause()
    {
        return selectClause;
    }

    public ExprNode getWhereClause()
    {
        return whereClause;
    }
}
