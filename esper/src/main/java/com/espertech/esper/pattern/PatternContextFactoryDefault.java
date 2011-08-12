/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.pattern;

import com.espertech.esper.core.StatementContext;

/**
 * Default pattern context factory.
 */
public class PatternContextFactoryDefault implements PatternContextFactory
{
    public PatternContext createContext(StatementContext statementContext,
                                        int streamId,
                                        EvalRootNode rootNode,
                                        boolean hasArrayProperties,
                                        boolean hasConsumingFilter)
    {
        EvalFilterConsumptionHandler consumptionHandler = null;
        if (hasConsumingFilter) {
            consumptionHandler = new EvalFilterConsumptionHandler();
        }

        PatternContext context = new PatternContext(statementContext, streamId, consumptionHandler);
        recursiveAssignContext(context, rootNode);
        return context;
    }

    public static void recursiveAssignContext(PatternContext context, EvalNode parent) {
        parent.setContext(context);
        for (EvalNode child : parent.getChildNodes()) {
            recursiveAssignContext(context, child);
        }
    }
}
