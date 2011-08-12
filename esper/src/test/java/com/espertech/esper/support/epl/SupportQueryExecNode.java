/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.support.epl;

import com.espertech.esper.epl.join.exec.base.ExecNode;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.util.IndentWriter;

import java.util.List;

public class SupportQueryExecNode extends ExecNode
{
    private final String id;
    
    private EventBean[] lastPrefillPath;

    public SupportQueryExecNode(String id)
    {
        this.id = id;
    }

    public void process(EventBean lookupEvent, EventBean[] prefillPath, List<EventBean[]> result, ExprEvaluatorContext exprEvaluatorContext)
    {
        lastPrefillPath = prefillPath;
    }

    public String getId()
    {
        return id;
    }

    public EventBean[] getLastPrefillPath()
    {
        return lastPrefillPath;
    }

    public void print(IndentWriter writer)
    {
        writer.println("SupportQueryExecNode");
    }
}
