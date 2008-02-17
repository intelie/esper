package com.espertech.esper.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A spin-locking implementation of a latch for use in guaranteeing delivery between
 * a single event produced by a single statement and consumable by another statement.
 */
public class InsertIntoLatchSpin
{
    private static final Log log = LogFactory.getLog(InsertIntoLatchSpin.class);

    // The earlier latch is the latch generated before this latch
    private InsertIntoLatchSpin earlier;
    private long msecTimeout;
    private Object payload;

    private volatile boolean isCompleted;

    /**
     * Ctor.
     * @param earlier the latch before this latch that this latch should be waiting for
     * @param msecTimeout the timeout after which delivery occurs
     * @param payload the payload is an event to deliver
     */
    public InsertIntoLatchSpin(InsertIntoLatchSpin earlier, long msecTimeout, Object payload)
    {
        this.earlier = earlier;
        this.msecTimeout = msecTimeout;
        this.payload = payload;
    }

    /**
     * Ctor - use for the first and unused latch to indicate completion.
     */
    public InsertIntoLatchSpin()
    {
        isCompleted = true;
        earlier = null;
        msecTimeout = 0;
    }

    /**
     * Returns true if the dispatch completed for this future.
     * @return true for completed, false if not
     */
    public boolean isCompleted()
    {
        return isCompleted;
    }

    /**
     * Blocking call that returns only when the earlier latch completed.
     * @return payload of the latch
     */
    public Object await()
    {
        if (!earlier.isCompleted)
        {
            long spinStartTime = System.currentTimeMillis();

            while(!earlier.isCompleted)
            {
                Thread.yield();

                long spinDelta = System.currentTimeMillis() - spinStartTime;
                if (spinDelta > msecTimeout)
                {
                    log.info("Spin wait timeout exceeded in insert-into dispatch");
                    break;
                }
            }
        }

        return payload;
    }

    /**
     * Called to indicate that the latch completed and a later latch can start.
     */
    public void done()
    {
        isCompleted = true;
        earlier = null;
    }
}
