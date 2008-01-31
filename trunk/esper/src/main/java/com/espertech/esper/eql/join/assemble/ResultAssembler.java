/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.eql.join.assemble;

import com.espertech.esper.event.EventBean;
import com.espertech.esper.eql.join.rep.Node;

/**
 * Interface for indicating a result in the form of a single row of multiple events, which could
 * represent either a full result over all streams or a partial result over a subset of streams.
 */
public interface ResultAssembler
{
    /**
     * Publish a result row.
     * @param row is the result to publish
     * @param fromStreamNum is the originitor that publishes the row
     * @param myEvent is optional and is the event that led to the row result
     * @param myNode is optional and is the result node of the event that led to the row result
     */
    public void result(EventBean[] row, int fromStreamNum, EventBean myEvent, Node myNode);
}
