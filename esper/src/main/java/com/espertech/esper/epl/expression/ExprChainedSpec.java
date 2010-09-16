/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.expression;

import java.io.Serializable;
import java.util.List;

public class ExprChainedSpec implements Serializable
{
    private String name;
    private List<ExprNode> parameters;

    public ExprChainedSpec(String name, List<ExprNode> parameters)
    {
        this.name = name;
        this.parameters = parameters;
    }

    public String getName()
    {
        return name;
    }

    public List<ExprNode> getParameters()
    {
        return parameters;
    }

    public void setParameters(List<ExprNode> parameters) {
        this.parameters = parameters;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        ExprChainedSpec that = (ExprChainedSpec) o;

        if (name != null ? !name.equals(that.name) : that.name != null)
        {
            return false;
        }
        if (ExprNodeUtility.deepEquals(parameters, that.parameters)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (parameters != null ? parameters.hashCode() : 0);
        return result;
    }
}
