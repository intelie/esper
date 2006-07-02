package net.esper.eql.view;

/**
 * A wrapper for the callback from the output limit condition to the output handler.
 */
public interface OutputCallback {

	/**
     * Invoked to perform output processing.
	 * @param forceUpdate is a flag to indicate that even if there is no data, 
	 * child views should still be updated.
	 */
	public void continueOutputProcessing(boolean forceUpdate);	
	
}
