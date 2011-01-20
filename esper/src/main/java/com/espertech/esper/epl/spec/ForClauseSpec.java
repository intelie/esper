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

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class ForClauseSpec implements MetaDefItem, Serializable
{
    private static final long serialVersionUID = -8529660985454535028L;
    
    private List<ForClauseItemSpec> clauses;

    public ForClauseSpec()
    {
        clauses = new ArrayList<ForClauseItemSpec>();
    }

    public List<ForClauseItemSpec> getClauses()
    {
        return clauses;
    }

    public void setClauses(List<ForClauseItemSpec> clauses)
    {
        this.clauses = clauses;
    }
}