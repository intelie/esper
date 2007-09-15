/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.eql.join.table;

import net.esper.event.EventBean;

/**
 * Table of events allowing add and remove. Lookup in table is coordinated
 * through the underlying implementation.
 */
public interface EventTable
{
    /**
     * Add events to table.
     * @param events to add
     */
    public void add(EventBean[] events);

    /**
     * Remove events from table.
     * @param events to remove
     */
    public void remove(EventBean[] events);
}
