/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.client.soda;

import java.io.Serializable;
import java.io.StringWriter;

public class RowLimitClause implements Serializable
{
    private static final long serialVersionUID = 0L;

    private Integer numRows;
    private Integer optionalOffsetRows;
    private String numRowsVariable;
    private String optionalOffsetRowsVariable;

    /**
     * Creates a row limit clause.
     * @return clause
     */
    public static RowLimitClause create(String numRowsVariable)
    {
        return new RowLimitClause(null, null, numRowsVariable, null);
    }

    /**
     * Creates a row limit clause.
     * @return clause
     */
    public static RowLimitClause create(String numRowsVariable, String offsetVariable)
    {
        return new RowLimitClause(null, null, numRowsVariable, offsetVariable);
    }

    /**
     * Creates a row limit clause.
     * @return clause
     */
    public static RowLimitClause create(int numRows)
    {
        return new RowLimitClause(numRows, null, null, null);
    }

    /**
     * Creates a row limit clause.
     * @return clause
     */
    public static RowLimitClause create(int numRows, int offset)
    {
        return new RowLimitClause(numRows, offset, null, null);
    }

    /**
     * Ctor.
     */
    public RowLimitClause(Integer numRows, Integer optionalOffsetRows, String numRowsVariable, String optionalOffsetRowsVariable)
    {
        this.numRows = numRows;
        this.optionalOffsetRows = optionalOffsetRows;
        this.numRowsVariable = numRowsVariable;
        this.optionalOffsetRowsVariable = optionalOffsetRowsVariable;
    }

    public Integer getNumRows()
    {
        return numRows;
    }

    public void setNumRows(Integer numRows)
    {
        this.numRows = numRows;
    }

    public Integer getOptionalOffsetRows()
    {
        return optionalOffsetRows;
    }

    public void setOptionalOffsetRows(Integer optionalOffsetRows)
    {
        this.optionalOffsetRows = optionalOffsetRows;
    }

    public String getNumRowsVariable()
    {
        return numRowsVariable;
    }

    public void setNumRowsVariable(String numRowsVariable)
    {
        this.numRowsVariable = numRowsVariable;
    }

    public String getOptionalOffsetRowsVariable()
    {
        return optionalOffsetRowsVariable;
    }

    public void setOptionalOffsetRowsVariable(String optionalOffsetRowsVariable)
    {
        this.optionalOffsetRowsVariable = optionalOffsetRowsVariable;
    }

    /**
     * Renders the clause in textual representation.
     * @param writer to output to
     */
    public void toEPL(StringWriter writer)
    {
        if (numRows != null)
        {
            writer.write(Integer.toString(numRows));
        }
        else
        {
            writer.write(numRowsVariable);
        }

        if (optionalOffsetRows != null)
        {
            writer.write(" offset ");
            writer.write(Integer.toString(optionalOffsetRows));
        }
        else if (optionalOffsetRowsVariable != null)
        {
            writer.write(" offset ");
            writer.write(optionalOffsetRowsVariable);
        }
    }
}
