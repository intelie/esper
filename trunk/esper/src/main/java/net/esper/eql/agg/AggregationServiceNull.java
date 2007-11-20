/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.eql.agg;

import net.esper.collection.MultiKeyUntyped;
import net.esper.event.EventBean;
import net.esper.eql.agg.AggregationService;

/**
 * A null object implementation of the AggregationService
 * interface.
 */
public class AggregationServiceNull implements AggregationService {

    public void applyEnter(EventBean[] eventsPerStream,
                           MultiKeyUntyped optionalGroupKeyPerRow) {
    }

    public void applyLeave(EventBean[] eventsPerStream,
                           MultiKeyUntyped optionalGroupKeyPerRow) {
    }

    public void setCurrentRow(MultiKeyUntyped groupKey) {
    }

    public Object getValue(int column) {
        return null;
    }

}
