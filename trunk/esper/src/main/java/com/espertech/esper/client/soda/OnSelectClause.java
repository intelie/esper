/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.client.soda;

import java.io.StringWriter;

/**
 * A clause to delete from a named window based on a triggering event arriving and correlated to the named window events to be deleted.
 */
public class OnSelectClause extends OnClause
{
    private static final long serialVersionUID = 0L;

    private String windowName;
    private String optionalAsName;

    /**
     * Creates an on-select clause.
     * @param windowName is the named window name
     * @param optionalAsName is the optional alias
     * @return on-select clause
     */
    public static OnSelectClause create(String windowName, String optionalAsName)
    {
        return new OnSelectClause(windowName, optionalAsName);
    }

    /**
     * Ctor.
     * @param windowName is the named window name
     * @param optionalAsName is the alias name of the named window
     */
    public OnSelectClause(String windowName, String optionalAsName)
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
