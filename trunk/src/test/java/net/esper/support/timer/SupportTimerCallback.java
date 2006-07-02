package net.esper.support.timer;

import net.esper.timer.TimerCallback;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SupportTimerCallback implements TimerCallback
{
    private int numInvoked;

    public void timerCallback()
    {
        numInvoked++;
        log.debug(".timerCallback numInvoked=" + numInvoked);
    }

    public int getCount()
    {
        return numInvoked;
    }

    public int getAndResetCount()
    {
        int count = numInvoked;
        numInvoked = 0;
        return count;
    }

    private static final Log log = LogFactory.getLog(SupportTimerCallback.class);
}
