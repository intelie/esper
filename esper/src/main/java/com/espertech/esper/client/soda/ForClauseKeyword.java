/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.client.soda;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public enum ForClauseKeyword
{
    GROUPED_DELIVERY("grouped_delivery"),
    DISCRETE_DELIVERY("discrete_delivery");

    private final String name;

    private ForClauseKeyword(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}