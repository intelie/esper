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
