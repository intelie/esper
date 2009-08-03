/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.agg;

import com.espertech.esper.collection.MultiKeyUntyped;
import com.espertech.esper.collection.ArrayDequeJDK6Backport;
import com.espertech.esper.client.EventBean;

public interface AggregationServiceMatchRecognize extends AggregationResultFuture
{
    public void applyEnter(EventBean[] eventsPerStream, int streamId);

    /**
     * Clear current aggregation state.
     */
    public void clearResults();
}