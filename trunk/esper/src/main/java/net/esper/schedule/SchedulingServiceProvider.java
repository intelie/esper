/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.schedule;

import net.esper.util.ManagedReadWriteLock;

/**
 * Static factory for implementations of the SchedulingService interface.
 */
public final class SchedulingServiceProvider
{
    /**
     * Creates an implementation of the SchedulingService interface.
     * @return implementation
     */
    public static SchedulingService newService()
    {
        return new SchedulingServiceImpl();
    }
}
