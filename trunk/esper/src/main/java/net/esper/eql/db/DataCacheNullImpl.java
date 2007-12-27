/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.eql.db;

import net.esper.event.EventBean;
import net.esper.eql.join.table.EventTable;

import java.util.List;

/**
 * Null implementation for a data cache that doesn't ever hit.
 */
public class DataCacheNullImpl implements DataCache
{
    public EventTable getCached(Object[] lookupKeys)
    {
        return null;
    }

    public void put(Object[] lookupKeys, EventTable rows)
    {

    }

    public boolean isActive()
    {
        return false;
    }
}
