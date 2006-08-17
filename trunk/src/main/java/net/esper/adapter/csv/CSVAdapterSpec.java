package net.esper.adapter.csv;


/**
 * A container for the parameters to the CSVAdapter.
 */
public class CSVAdapterSpec
{
	private final String path;
	private final int eventsPerSec;
	private final boolean isLooping;
	
	/**
	 * Ctor.
	 * @param path - the path name for the CSV file to be processed
	 * @param isLooping - true if processing should start over from the beginning after the end of the CSV file is reached
	 * @param eventsPerSec - the number of events to be sent into the epRuntime per second; 
	 * 		  				  or -1 to use the timestamp values in the CSV file
	 */
	protected CSVAdapterSpec(String path, 
						  boolean isLooping, 
						  int eventsPerSec)
	{
		this.path = path;
		this.eventsPerSec = eventsPerSec;
		this.isLooping = isLooping;
	}

	/**
	 * @return the eventsPerSec
	 */
	protected int getEventsPerSec()
	{
		return eventsPerSec;
	}

	/**
	 * @return isLooping flag
	 */
	protected boolean isLooping()
	{
		return isLooping;
	}

	/**
	 * @return the path name for the CSV file
	 */
	protected String getPath()
	{
		return path;
	}
}
