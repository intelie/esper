package net.esper.eql.view;

/**
 * A wrapper for the callback from the output limit condition to the output handler.
 */
public interface OutputCallback 
{
	/**
     * Invoked to perform output processing.
	 * @param doOutput - true if the batched events should actually be output as well as processed, false if they should just be processed
	 * @param forceUpdate - true if output should be made even when no updating events have arrived
	 */
	public void continueOutputProcessing(boolean doOutput, boolean forceUpdate);	
	
}
