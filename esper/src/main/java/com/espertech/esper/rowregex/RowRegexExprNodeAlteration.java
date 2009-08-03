/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.rowregex;

import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.util.MetaDefItem;

import java.io.Serializable;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RowRegexExprNodeAlteration extends RowRegexExprNode
{
    private static final Log log = LogFactory.getLog(RowRegexExprNodeAlteration.class);

    public RowRegexExprNodeAlteration()
    {        
    }

    public String toExpressionString() {
        StringBuilder builder = new StringBuilder();
        String delimiter = "";
        for (RowRegexExprNode node : this.getChildNodes())
        {
            builder.append(delimiter);
            builder.append(node.toExpressionString());
            delimiter = "|";
        }
        return builder.toString();
    }
}
