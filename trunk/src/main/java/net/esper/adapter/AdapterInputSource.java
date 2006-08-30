package net.esper.adapter;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import net.esper.adapter.csv.CSVReader;
import net.esper.client.EPException;

/**
 * An input source for adapters that accepts either URLs or 
 * classpath resource names.
 */
public class AdapterInputSource
{
	private final URL url;
	private final String classpathResource;
	
	/**
	 * Ctor.
	 * @param classpathResource - the name of the resource on the classpath to use as the source for an adapter
	 */
	public AdapterInputSource(String classpathResource)
	{
		if(classpathResource == null)
		{
			throw new NullPointerException("Cannot create AdapterInputStream from a null classpathResource");
		}
		this.classpathResource = classpathResource;
		this.url = null;
	}
	
	/**
	 * Ctor.
	 * @param url - the URL for the resource to use as source for an adapter
	 */
	public AdapterInputSource(URL url)
	{
		if(url == null)
		{
			throw new NullPointerException("Cannot create AdapterInputStream from a null URL");
		}
		this.url = url;
		this.classpathResource = null;
	}
	
	/**
	 * Open the resource as an input stream
	 * @return a new input stream generated from the resource
	 */
	public InputStream openStream()
	{
		if(url != null)
		{
			try
			{
				return url.openStream();
			} 
			catch (IOException e)
			{
				throw new EPException(e);
			}
		}
		else
		{
			return resolvePathAsStream(classpathResource);
		}
	}
	
	private InputStream resolvePathAsStream(String path)
    {
    	InputStream stream = null;
    	ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    	if (classLoader!=null) {
    		stream = classLoader.getResourceAsStream( path );
    	}
    	if ( stream == null ) {
    		stream = CSVReader.class.getResourceAsStream( path );
    	}
    	if ( stream == null ) {
    		stream = CSVReader.class.getClassLoader().getResourceAsStream( path );
    	}
    	if ( stream == null ) {
    		throw new EPException( path + " not found" );
    	}
    	
    	return stream;
    }
}
