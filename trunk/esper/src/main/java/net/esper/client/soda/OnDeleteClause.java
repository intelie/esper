package net.esper.client.soda;

import java.io.Serializable;
import java.io.StringWriter;

/**
 * A clause to delete from a named window based on a triggering event arriving and correlated to the named window events to be deleted.
 */
public class OnDeleteClause implements Serializable
{
    private static final long serialVersionUID = 0L;

    private String windowName;
    private String optionalAsName;
    private Expression joinExpr;

    /**
     * Creates a on-delete clause for deleting from a named window.
     * @param windowName is the named window name
     * @param asNameAlias is the alias name of the named window
     * @param joinExpr is the where-clause expression for the on-delete
     * @return on-delete clause
     */
    public static OnDeleteClause create(String windowName, String asNameAlias, Expression joinExpr)
    {
        return new OnDeleteClause(windowName, asNameAlias, joinExpr);
    }

    /**
     * Ctor.
     * @param windowName is the named window name
     * @param optionalAsName is the alias name of the named window
     * @param joinExpr is the where-clause expression for the on-delete
     */
    public OnDeleteClause(String windowName, String optionalAsName, Expression joinExpr)
    {
        this.windowName = windowName;
        this.optionalAsName = optionalAsName;
        this.joinExpr = joinExpr;
    }

    /**
     * Renders the clause in textual representation.
     * @param writer to output to
     */
    public void toEQL(StringWriter writer)
    {
        writer.write(" delete from ");
        writer.write(windowName);
        if (optionalAsName != null)
        {
            writer.write(" as ");
            writer.write(optionalAsName);
        }
        if (joinExpr != null)
        {
            writer.write(" where ");
            joinExpr.toEQL(writer);
        }
    }

    public String getWindowName()
    {
        return windowName;
    }

    public void setWindowName(String windowName)
    {
        this.windowName = windowName;
    }

    public String getOptionalAsName()
    {
        return optionalAsName;
    }

    public void setOptionalAsName(String optionalAsName)
    {
        this.optionalAsName = optionalAsName;
    }

    public Expression getJoinExpr()
    {
        return joinExpr;
    }

    public void setJoinExpr(Expression joinExpr)
    {
        this.joinExpr = joinExpr;
    }
}
