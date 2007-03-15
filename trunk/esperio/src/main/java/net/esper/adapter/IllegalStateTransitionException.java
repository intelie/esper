package net.esper.adapter;

import net.esper.client.EPException;

/**
 * Thrown when an illegal Adapter state transition is attempted.
 */
public class IllegalStateTransitionException extends EPException
{
	/**
	 * @param message - an explanation of the cause of the exception
	 */
	public IllegalStateTransitionException(String message)
	{
		super(message);
	}
}
