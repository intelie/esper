/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.core;

import com.espertech.esper.event.EventType;

/**
 * Interface for a prepared query that can be executed multiple times.
 */
public interface EPPreparedQuery
{
    /**
     * Execute the prepared query returning query results.
     * @return query result
     */
    public EPQueryResult execute();

    /**
     * Returns the event type, representing the columns of the select-clause
     * @return event type
     */
    public EventType getEventType();
}
