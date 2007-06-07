/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.eql.join;

import net.esper.event.EventBean;

/**
 * Strategy for executing a join.
 */
public interface JoinExecutionStrategy
{
    /**
     * Execute join. The first dimension in the 2-dim arrays is the stream that generated the events,
     * and the second dimension is the actual events generated.
     * @param newDataPerStream - new events for each stream
     * @param oldDataPerStream - old events for each stream
     */
    public void join(EventBean[][] newDataPerStream,
                     EventBean[][] oldDataPerStream);
}