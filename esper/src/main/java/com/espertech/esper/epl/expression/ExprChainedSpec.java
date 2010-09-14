/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.expression;

import java.util.List;

public class ExprChainedSpec
{
    private String optClassOrPropertyName;
    private String name;
    private List<ExprNode> parameters;
    private String eventproperty;

    public ExprChainedSpec(String name, List<ExprNode> parameters, String optClassOrPropertyName)
    {
        this.name = name;
        this.parameters = parameters;
        this.optClassOrPropertyName = optClassOrPropertyName;
    }

    public ExprChainedSpec(String eventproperty)
    {
        this.eventproperty = eventproperty;
    }

    public String getEventproperty()
    {
        return eventproperty;
    }

    public String getName()
    {
        return name;
    }

    public String getOptClassOrPropertyName()
    {
        return optClassOrPropertyName;
    }

    public List<ExprNode> getParameters()
    {
        return parameters;
    }
}
