package net.esper.eql.view;


/**
 * A condition that must be satisfied before output processing
 * is allowed to continue. Once the condition is satisfied, it
 * makes a callback to continue output processing.
 */
public interface OutputCondition {
	
	/**
	 * Update the output condition.
	 * @param newEventsCount - number of new events incoming
	 * @param oldEventsCount  - number of old events incoming
	 */ 
	public void updateOutputCondition(int newEventsCount, int oldEventsCount);
}
