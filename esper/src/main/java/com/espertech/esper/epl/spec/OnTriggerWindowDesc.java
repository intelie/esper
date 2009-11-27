/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.spec;

/**
 * Specification for the on-select and on-delete and on-update (via subclass) (no split-stream) statement.
 */
public class OnTriggerWindowDesc extends OnTriggerDesc
{
    private String windowName;
    private String optionalAsName;
    private static final long serialVersionUID = 4146264160256741899L;

    /**
     * Ctor.
     * @param windowName the window name
     * @param optionalAsName the optional name
     * @param onTriggerType for indicationg on-delete, on-select or on-update
     */
    public OnTriggerWindowDesc(String windowName, String optionalAsName, OnTriggerType onTriggerType)
    {
        super(onTriggerType);
        this.windowName = windowName;
        this.optionalAsName = optionalAsName;
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
     * Returns the name, or null if none defined.
     * @return name
     */
    public String getOptionalAsName()
    {
        return optionalAsName;
    }
}
