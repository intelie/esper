/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.spec;

import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.util.MetaDefItem;

import java.util.List;

/**
 * Spec for defining a row limit.
 */
public class RowLimitSpec implements MetaDefItem
{
    private final Integer numRows;
	private final Integer optionalOffset;

    private final String numRowsVariable;
    private final String optionalOffsetVariable;

    public RowLimitSpec(Integer numRows, Integer optionalOffset, String numRowsVariable, String optionalOffsetVariable)
    {
        this.numRows = numRows;
        this.optionalOffset = optionalOffset;
        this.numRowsVariable = numRowsVariable;
        this.optionalOffsetVariable = optionalOffsetVariable;
    }

    public Integer getNumRows()
    {
        return numRows;
    }

    public Integer getOptionalOffset()
    {
        return optionalOffset;
    }

    public String getNumRowsVariable()
    {
        return numRowsVariable;
    }

    public String getOptionalOffsetVariable()
    {
        return optionalOffsetVariable;
    }
}
