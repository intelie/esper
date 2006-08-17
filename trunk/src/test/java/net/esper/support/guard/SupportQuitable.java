package net.esper.support.guard;

import net.esper.pattern.guard.Quitable;

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
