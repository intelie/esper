package net.esper.adapter.csv;

import java.util.Map;

import net.esper.adapter.AdapterInputSource;
import net.esper.adapter.InputAdapter;
import net.esper.adapter.AdapterFactory;
import net.esper.client.EPException;

/**
 * An implementation of CSVAdapterManager.
 */
public class CSVAdapterManagerImpl implements CSVAdapterManager
{
	private final AdapterFactory adapterFactory;
	/**
	 * Ctor.
Adapter
	 */
	public CSVAdapterManagerImpl(AdapterFactory adapterFactory)
	{
		this.adapterFactory = adapterFactory;
	}
	
	/* (non-Javadoc)
Adapter
	 */
	public void start(AdapterInputSource adapterInputSource, String eventTypeAlias) throws EPException
	{
		CSVInputAdapterSpec adapterSpec = new CSVInputAdapterSpec(adapterInputSource, eventTypeAlias);
		adapterSpec.setUsingEngineThread(false);
		InputAdapter adapter = adapterFactory.createAdapter(adapterSpec);
		adapter.start();
	}

	/* (non-Javadoc)
Adapter
	 */
	public void start(AdapterInputSource adapterInputSource, String eventTypeAlias, Map<String, Class> propertyTypes) throws EPException
	{
		CSVInputAdapterSpec adapterSpec = new CSVInputAdapterSpec(adapterInputSource, eventTypeAlias);
		adapterSpec.setPropertyTypes(propertyTypes);
		adapterSpec.setUsingEngineThread(false);
		InputAdapter adapter = adapterFactory.createAdapter(adapterSpec);
		adapter.start();
	}
	
	
}
