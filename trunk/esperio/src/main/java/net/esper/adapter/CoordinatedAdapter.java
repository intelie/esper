package net.esper.adapter;

import net.esper.client.EPException;
import net.esper.client.EPServiceProvider;
import net.esper.schedule.ScheduleSlot;

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
