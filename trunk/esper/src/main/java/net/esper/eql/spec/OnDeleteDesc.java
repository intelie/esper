package net.esper.eql.spec;

import net.esper.eql.expression.ExprNode;

/**
 * Specification for the on-delete statement.
 */
public class OnDeleteDesc
{
    private String windowName;
    private String optionalAsName;
    private ExprNode joinExpr;

    /**
     * Ctor.
     * @param windowName the window name
     * @param optionalAsName the optional alias
     * @param joinExpr the optional where clause
     */
    public OnDeleteDesc(String windowName, String optionalAsName, ExprNode joinExpr)
    {
        this.windowName = windowName;
        this.optionalAsName = optionalAsName;
        this.joinExpr = joinExpr;
    }

    /**
     * Returns the window name.
     * @return window name
     */
    public String getWindowName()
    {
        return windowName;
    }

    /**
     * Returns the alias, or null if none defined.
     * @return alias
     */
    public String getOptionalAsName()
    {
        return optionalAsName;
    }

    /**
     * Returns the where clause, or null if none defined.
     * @return where clause
     */
    public ExprNode getJoinExpr()
    {
        return joinExpr;
    }

    /**
     * Sets the where clause, or null if none define.
     * @param joinExpr where clause
     */
    public void setJoinExpr(ExprNode joinExpr)
    {
        this.joinExpr = joinExpr;
    }
}
