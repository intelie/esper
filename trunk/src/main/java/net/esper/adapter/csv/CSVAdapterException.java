package net.esper.adapter.csv;

/**
 * An exception thrown by the CSVAdapter in processing CSV files
 * and sending CSV entries as events to the runtime engine.
 */
public class CSVAdapterException extends RuntimeException
{
	/**
	 * Ctor.
	 * @param cause - inner exception
	 */
	protected CSVAdapterException(Throwable cause)
	{
		super(cause);
	}
	
	/**
	 * Ctor.
	 * @param message - error message
	 */
	protected CSVAdapterException(String message)
	{
		super(message);
	}

	/**
	 * @param message - error message
	 * @param cause - inner exception
	 */
	protected CSVAdapterException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
