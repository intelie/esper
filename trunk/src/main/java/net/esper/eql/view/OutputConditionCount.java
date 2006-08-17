package net.esper.eql.view;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Output limit condition that is satisfied when either
 * the total number of new events arrived or the total number
 * of old events arrived is greater than a preset value.
 */
public final class OutputConditionCount implements OutputCondition
{
	public static final boolean DO_OUTPUT = true;
	public static final boolean FORCE_UPDATE = false;
    
    private final int eventRate;
    private int newEventsCount;
    private int oldEventsCount;
    private final OutputCallback outputCallback;


    /**
     * Constructor.
     * @param eventRate is the number of old or new events that
     * must arrive in order for the condition to be satisfied
     * @param outputCallback is the callback that is made when the conditoin is satisfied
     */
    public OutputConditionCount(int eventRate, OutputCallback outputCallback)
    {
        if (eventRate < 1)
        {
            throw new IllegalArgumentException("Limiting output by event count requires an event count of at least 1");
        }
		if(outputCallback ==  null)
		{
			throw new NullPointerException("Output condition by count requires a non-null callback");
		}
        this.eventRate = eventRate;
        this.outputCallback = outputCallback;
    }

    /**
     * Returns the number of new events.
     * @return number of new events
     */
    public int getNewEventsCount() {
		return newEventsCount;
	}

    /**
     * Returns the number of old events.
     * @return number of old events
     */
	public int getOldEventsCount() {
		return oldEventsCount;
	}

    /**
     * Returns the event rate.
     * @return event rate
     */
    public final long getEventRate()
    {
        return eventRate;
    }
    
    public final void updateOutputCondition(int newDataCount, int oldDataCount)
    {
        this.newEventsCount += newDataCount;
        this.oldEventsCount += oldDataCount;
        
        if (log.isDebugEnabled())
        {
            log.debug(".updateBatchCondition, " +
                    "  newEventsCount==" + newEventsCount +
                    "  oldEventsCount==" + oldEventsCount);
        } 

        if (isSatisfied())
        {
        	log.debug(".updateOutputCondition() condition satisfied");
            this.newEventsCount = 0;
            this.oldEventsCount = 0;
            outputCallback.continueOutputProcessing(DO_OUTPUT, FORCE_UPDATE);    
        }
    }

    public final String toString()
    {
        return this.getClass().getName() +
                " eventRate=" + eventRate;
    }

    private boolean isSatisfied()
    {
    	return (newEventsCount >= eventRate) || (oldEventsCount >= eventRate);
    }
    
    private static final Log log = LogFactory.getLog(OutputConditionCount.class);




}
