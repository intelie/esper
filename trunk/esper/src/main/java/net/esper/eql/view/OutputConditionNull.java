package net.esper.eql.view;

/**
 * An empty output condition that is always satisfied.
 */
public class OutputConditionNull implements OutputCondition {

	private static final boolean DO_OUTPUT = true;
	private static final boolean FORCE_UPDATE = false;
	
	private final OutputCallback outputCallback;
	
	/**
	 * Ctor.
	 * @param outputCallback is the callback to make once the condition is satisfied
	 */
	public OutputConditionNull(OutputCallback outputCallback)
	{
        if(outputCallback == null)
        {
        	throw new NullPointerException("Output condition requires a non-null callback");
        }
		this.outputCallback = outputCallback;
	}
	
	public void updateOutputCondition(int newEventsCount, int oldEventsCount) {
		outputCallback.continueOutputProcessing(DO_OUTPUT, FORCE_UPDATE);
	}

}
