/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.example.terminal.common;

import java.io.Serializable;

public class TerminalInfo implements Serializable
{
    private String id;

    public TerminalInfo(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }
}
