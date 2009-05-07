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
import java.util.List;

public class OnInsertSplitStreamItem implements Serializable
{
    private static final long serialVersionUID = 0L;

    private InsertIntoClause insertInto;
    private SelectClause selectClause;
    private Expression whereClause;

    public static OnInsertSplitStreamItem create(InsertIntoClause insertInto, SelectClause selectClause, Expression whereClause)
    {
        return new OnInsertSplitStreamItem(insertInto, selectClause, whereClause);
    }

    public OnInsertSplitStreamItem(InsertIntoClause insertInto, SelectClause selectClause, Expression whereClause)
    {
        this.insertInto = insertInto;
        this.selectClause = selectClause;
        this.whereClause = whereClause;
    }

    public InsertIntoClause getInsertInto()
    {
        return insertInto;
    }

    public void setInsertInto(InsertIntoClause insertInto)
    {
        this.insertInto = insertInto;
    }

    public SelectClause getSelectClause()
    {
        return selectClause;
    }

    public void setSelectClause(SelectClause selectClause)
    {
        this.selectClause = selectClause;
    }

    public Expression getWhereClause()
    {
        return whereClause;
    }

    public void setWhereClause(Expression whereClause)
    {
        this.whereClause = whereClause;
    }
}
