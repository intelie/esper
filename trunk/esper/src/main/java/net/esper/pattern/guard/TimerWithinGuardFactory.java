/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.pattern.guard;

import net.esper.pattern.PatternContext;
import net.esper.eql.parse.TimePeriodParameter;

/**
 * Factory for {@link TimerWithinGuard} instances.
 */
public class TimerWithinGuardFactory implements GuardFactory
{
    private final long milliseconds;

    /**
     * Creates a timer guard.
     * @param seconds number of seconds before guard expiration
     */
    public TimerWithinGuardFactory(int seconds)
    {
        this((long)seconds);
    }

    /**
     * Creates a timer guard.
     * @param seconds number of seconds before guard expiration
     */
    public TimerWithinGuardFactory(double seconds)
    {
        this.milliseconds = Math.round(seconds * 1000d);
    }

    /**
     * Creates a timer guard.
     * @param seconds number of seconds before guard expiration
     */
    public TimerWithinGuardFactory(long seconds)
    {
        this.milliseconds = seconds * 1000;
    }

    /**
     * Creates a timer guard.
     * @param timePeriodParameter number of milliseconds before guard expiration
     */
    public TimerWithinGuardFactory(TimePeriodParameter timePeriodParameter)
    {
        double milliseconds = timePeriodParameter.getNumSeconds() * 1000d;
        this.milliseconds = Math.round(milliseconds);
    }

    public Guard makeGuard(PatternContext context, Quitable quitable)
    {
        return new TimerWithinGuard(milliseconds, context, quitable);
    }
}
