package net.esper.eql.spec;

import net.esper.eql.expression.ExprNode;
import net.esper.util.MetaDefItem;

/**
 * Specification for the on-delete statement.
 */
public class OnTriggerDesc implements MetaDefItem
{
    private String windowName;
    private String optionalAsName;
    private ExprNode joinExpr;
    private boolean isOnDelete;

    /**
     * Ctor.
     * @param windowName the window name
     * @param optionalAsName the optional alias
     * @param joinExpr the optional where clause
     * @param isOnDelete is true for on-delete clauses, and false for on-select clauses
     */
    public OnTriggerDesc(String windowName, String optionalAsName, ExprNode joinExpr, boolean isOnDelete)
    {
        this.windowName = windowName;
        this.optionalAsName = optionalAsName;
        this.joinExpr = joinExpr;
        this.isOnDelete = isOnDelete;
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

    /**
     * Returns true for on-delete clauses, and false for on-select clauses.
     * @return true for on-delete
     */
    public boolean isOnDelete()
    {
        return isOnDelete;
    }
}
