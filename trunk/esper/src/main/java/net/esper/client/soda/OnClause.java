package net.esper.client.soda;

import java.io.Serializable;
import java.io.StringWriter;

/**
 * A clause to delete from a named window based on a triggering event arriving and correlated to the named window events to be deleted.
 */
public abstract class OnClause implements Serializable
{
    private static final long serialVersionUID = 0L;

    /**
     * Creates an on-delete clause for deleting from a named window.
     * @param windowName is the named window name
     * @param asNameAlias is the alias name of the named window
     * @return on-delete clause
     */
    public static OnDeleteClause createOnDelete(String windowName, String asNameAlias)
    {
        return OnDeleteClause.create(windowName, asNameAlias);
    }

    /**
     * Creates an on-select clause for selecting from a named window.
     * @param windowName is the named window name
     * @param asNameAlias is the alias name of the named window
     * @return on-select clause
     */
    public static OnSelectClause createOnSelect(String windowName, String asNameAlias)
    {
        return OnSelectClause.create(windowName, asNameAlias);
    }

    /**
     * Creates an on-set clause for setting variable values.
     * @param variableName is the name of the first variable to set, additional ones can be added to the clause itself
     * @param expression is the assignment expression
     * @return on-set clause
     */
    public static OnSetClause createOnSet(String variableName, Expression expression)
    {
        return OnSetClause.create(variableName, expression);
    }
}
