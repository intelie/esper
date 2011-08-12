/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.regression.client;

import com.espertech.esper.pattern.MatchedEventMap;
import com.espertech.esper.pattern.guard.GuardSupport;
import com.espertech.esper.pattern.guard.Quitable;

import java.io.Serializable;

public class MyCountToPatternGuard extends GuardSupport implements Serializable
{
    private final int numCountTo;
    private final Quitable quitable;

    private int counter;

    public MyCountToPatternGuard(int numCountTo, Quitable quitable)
    {
        this.numCountTo = numCountTo;
        this.quitable = quitable;
    }

    public void startGuard()
    {
        counter = 0;
    }

    public void stopGuard()
    {
        // No action required when a sub-expression quits, or when the pattern is stopped
    }

    public boolean inspect(MatchedEventMap matchEvent)
    {
        counter++;
        if (counter > numCountTo)
        {
            quitable.guardQuit();
            return false;
        }
        return true;
    }
}
