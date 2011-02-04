package com.espertech.esper.support.guard;

import com.espertech.esper.pattern.PatternContext;
import com.espertech.esper.pattern.guard.Quitable;

public class SupportQuitable implements Quitable
{
    private final PatternContext patternContext;

    public int quitCounter = 0;

    public SupportQuitable(PatternContext patternContext) {
        this.patternContext = patternContext;
    }

    public void guardQuit()
    {
        quitCounter++;
    }

    public int getAndResetQuitCounter()
    {
        return quitCounter;
    }

    @Override
    public PatternContext getContext() {
        return patternContext;
    }
}
