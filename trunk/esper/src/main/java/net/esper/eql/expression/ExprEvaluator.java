/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.eql.expression;

import net.esper.event.EventBean;

/**
 * Interface for evaluating of an event tuple.
 */
public interface ExprEvaluator
{
    /**
     * Evaluate event tuple and return result.
     * @param eventsPerStream - event tuple
     * @param isNewData - indicates whether we are dealing with new data (istream) or old data (rstream)
     * @return evaluation result, a boolean value for OR/AND-type evalution nodes.
     */
    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData);
}