package net.esper.adapter.csv;

import java.util.ArrayList;
import java.util.List;

/**
 * A utility for synchronizing the sending of several CSVAdapters.
 */
public class CSVAdapterGroup
{
	private final List<CSVAdapter> adapters;
	private final CSVTimer timer;
	private boolean cancelled;
	
	protected CSVAdapterGroup()
	{
		adapters = new ArrayList<CSVAdapter>();
		timer = new CSVTimer();
		cancelled = false;
	}
	
	/**
	 * Create a new CSVAdapter and add it to the group.
	 * @param adapterSpec - describes the parameters for this adapter
	 * @param mapSpec - describes the format of the events to create and send into the EPRuntime
	 * @throws CSVAdapterException in case of errors opening the CSV file
	 */
	protected void addNewAdapter(CSVAdapterSpec adapterSpec, MapEventSpec mapSpec) throws CSVAdapterException
	{
		CSVAdapter adapter = new CSVAdapter(adapterSpec, mapSpec, timer);
		adapters.add(adapter);
	}
	
	/**
	 * Start the event sending of all the adapters in the group.
	 * @throws CSVAdapterException in case of errors reading the file or sending the events
	 */
	protected void start() throws CSVAdapterException
	{
		if(cancelled)
		{
			throw new CSVAdapterException("CSVAdapterGroup already cancelled");
		}
		timer.start();
	}
	
	/**
	 * Cancel the sending of events by all adapters in the group and release any resources.
	 * @throws CSVAdapterException in case of errors in closing the CSV file or associated resources
	 */
	protected void cancel() throws CSVAdapterException
	{
		if(cancelled)
		{
			throw new CSVAdapterException("CSVAdapterGroup already cancelled");
		}
		cancelled = true;
		timer.cancel();
		for(CSVAdapter adapter : adapters)
		{
			adapter.close();
		}
	}
}
