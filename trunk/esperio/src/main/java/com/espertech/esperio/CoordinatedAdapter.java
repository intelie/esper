package com.espertech.esperio;

import com.espertech.esper.client.EPException;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.schedule.ScheduleSlot;

/**
 * An Adapter that can be coordinated by an AdapterCoordinator.
 */
public interface CoordinatedAdapter extends InputAdapter
{
	/**
	 * Get the next event in line to be sent into the runtime , or null if there is no available event.
	 * @return an instance of SendableEvent that wraps the next event to send, or null if none
	 * @throws EPException in case of errors creating the event
	 */
	public SendableEvent read() throws EPException;

	/**
	 * Set the usingEngineThread value
	 * @param usingEngineThread - the value to set
	 */
	public void setUsingEngineThread(boolean usingEngineThread);

	/**
	 * Set the usingExternalTimer value
	 * @param usingExternalTimer - the value to set
	 */
	public void setUsingExternalTimer(boolean usingExternalTimer);

	/**
	 * Disallow subsequent state changes and throw an IllegalStateTransitionException
	 * if they are attempted.
	 */
	public void disallowStateTransitions();

	/**
	 * Set the scheduleSlot for this Adapter.
	 * @param scheduleSlot - the scheduleSlot to set
	 */
	public void setScheduleSlot(ScheduleSlot scheduleSlot);

	/**
	 * Set the epService
	 * @param epService - the value to set
	 */
	public void setEPService(EPServiceProvider epService);
}