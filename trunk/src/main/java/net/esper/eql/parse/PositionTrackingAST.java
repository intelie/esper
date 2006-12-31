package net.esper.eql.parse;

import antlr.CommonAST;
import antlr.Token;
import antlr.collections.AST;

/**
 * An AST that keeps track of the token's line and 
 * column information.
 */
public class PositionTrackingAST extends CommonAST
{
	private int line;
	private int column;
	
    public void initialize(Token t)
    {
            super.initialize(t);
            line = t.getLine();
            column = t.getColumn();
    }

    public void initialize(AST t)
    {
            super.initialize(t);
            line = t.getLine();
            column = t.getColumn();
    }

    /**
     * Get the column value
     * @return column
     */
	public int getColumn()
	{
		return column;
	}

	/**
	 * Get the line value
	 * @return line
	 */
	public int getLine()
	{
		return line;
	}

	/**
	 * Set the column.
	 * @param column - the column to set
	 */
	public void setColumn(int column)
	{
		this.column = column;
	}

	/**
	 * Set the line.
	 * @param line - the line to set
	 */
	public void setLine(int line)
	{
		this.line = line;
	}
}
