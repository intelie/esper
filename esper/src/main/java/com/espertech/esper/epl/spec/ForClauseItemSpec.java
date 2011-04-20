/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.spec;

import com.espertech.esper.util.MetaDefItem;
import com.espertech.esper.epl.expression.ExprNode;

import java.io.Serializable;
import java.util.List;

public class ForClauseItemSpec implements MetaDefItem, Serializable
{
    private static final long serialVersionUID = 4374267047749646423L;
    
    private String keyword;
    private List<ExprNode> expressions;

    public ForClauseItemSpec(String keyword, List<ExprNode> expressions)
    {
        this.keyword = keyword;
        this.expressions = expressions;
    }

    public String getKeyword()
    {
        return keyword;
    }

    public void setKeyword(String keyword)
    {
        this.keyword = keyword;
    }

    public List<ExprNode> getExpressions()
    {
        return expressions;
    }

    public void setExpressions(List<ExprNode> expressions)
    {
        this.expressions = expressions;
    }
}