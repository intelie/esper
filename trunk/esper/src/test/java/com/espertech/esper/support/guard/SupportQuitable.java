package com.espertech.esper.support.guard;

import com.espertech.esper.pattern.guard.Quitable;

public class SupportQuitable implements Quitable
{
    public int quitCounter = 0;

    public void guardQuit()
    {
        quitCounter++;
    }

    public int getAndResetQuitCounter()
    {
        return quitCounter;
    }
}
