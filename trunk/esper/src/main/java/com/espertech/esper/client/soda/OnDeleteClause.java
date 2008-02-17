package com.espertech.esper.client.soda;

import java.io.StringWriter;

/**
 * A clause to delete from a named window based on a triggering event arriving and correlated to the named window events to be deleted.
 */
public class OnDeleteClause extends OnClause
{
    private static final long serialVersionUID = 0L;

    private String windowName;
    private String optionalAsName;

    /**
     * Creates an on-delete clause.
     * @param windowName is the named window name
     * @param optionalAsName is the optional alias
     * @return on-delete clause 
     */
    public static OnDeleteClause create(String windowName, String optionalAsName)
    {
        return new OnDeleteClause(windowName, optionalAsName);
    }

    /**
     * Ctor.
     * @param windowName is the named window name
     * @param optionalAsName is the alias name of the named window
     */
    public OnDeleteClause(String windowName, String optionalAsName)
    {
        this.windowName = windowName;
        this.optionalAsName = optionalAsName;
    }

    /**
     * Renders the clause in textual representation.
     * @param writer to output to
     */
    public void toEPL(StringWriter writer)
    {
        writer.write(windowName);
        if (optionalAsName != null)
        {
            writer.write(" as ");
            writer.write(optionalAsName);
        }
    }

    /**
     * Returns the name of the named window to delete from.
     * @return named window name
     */
    public String getWindowName()
    {
        return windowName;
    }

    /**
     * Sets the name of the named window.
     * @param windowName window name
     */
    public void setWindowName(String windowName)
    {
        this.windowName = windowName;
    }

    /**
     * Returns the as-alias for the named window.
     * @return alias
     */
    public String getOptionalAsName()
    {
        return optionalAsName;
    }

    /**
     * Sets the as-alias for the named window.
     * @param optionalAsName alias to set for window
     */
    public void setOptionalAsName(String optionalAsName)
    {
        this.optionalAsName = optionalAsName;
    }
}
