/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.pattern.guard;

import net.esper.pattern.PatternContext;

/**
 * Interface for a factory for {@link Guard} instances.
 */
public interface GuardFactory
{
    /**
     * Constructs a guard instance.
     * @param context - services for use by guard
     * @param quitable - to use for indicating the guard has quit
     * @return guard instance
     */
    public Guard makeGuard(PatternContext context, Quitable quitable);
}
