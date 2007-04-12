/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.eql.join;

import net.esper.event.EventBean;
import net.esper.collection.MultiKey;
import java.util.Set;

/**
 * Processes a join result set constisting of sets of tuples of events.
 */
public interface JoinSetProcessor
{
    /**
     * Process join result set.
     * @param newEvents - set of event tuples representing new data
     * @param oldEvents - set of event tuples representing old data
     */
    public void process(Set<MultiKey<EventBean>> newEvents, Set<MultiKey<EventBean>> oldEvents);
}