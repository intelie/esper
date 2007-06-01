using System;
using System.IO;
using System.Net;

using net.esper.adapter.csv;
using net.esper.client;
using net.esper.compat;

namespace net.esper.adapter
{
	/// <summary>
	/// An input source for adapters.
	/// </summary>
	public class AdapterInputSource
	{
		private readonly Uri url;
		private readonly String classpathResource;
		private readonly File file;
		private readonly Stream inputStream;
		private readonly Reader reader;
		
		/// <summary>
		/// Ctor.
		/// </summary>
		/// <param name="classpathResource">the name of the resource on the classpath to use as the source for an adapter</param>
		
		public AdapterInputSource(String classpathResource)
		{
			if(classpathResource == null)
			{
				throw new NullPointerException("Cannot create AdapterInputStream from a null classpathResource");
			}
			this.classpathResource = classpathResource;
			this.url = null;
			this.file = null;
			this.inputStream = null;
			this.Reader = null;
		}
		
		/// <summary>
		/// Ctor.
		/// </summary>
		/// <param name="url">the URL for the resource to use as source for an adapter</param>
		public AdapterInputSource(Uri url)
		{
			if(url == null)
			{
				throw new NullPointerException("Cannot create AdapterInputStream from a null URL");
			}
			this.url = url;
			this.classpathResource = null;
			this.file = null;
			this.inputStream = null;
			this.Reader = null;
		}
		
		/// <summary>
		/// Ctor.
		/// </summary>
		/// <param name="file">the file to use as a source</param>
		public AdapterInputSource(File file)
		{
			if(file == null)
			{
				throw new NullPointerException("file cannot be null");
			}
			this.file = file;
			this.url = null;
			this.classpathResource = null;
			this.inputStream = null;
			this.Reader = null;
		}
		
		/// <summary>
		/// Ctor.
		/// </summary>
		/// <param name="inputStream">the stream to use as a source</param>
		public AdapterInputSource(Stream inputStream)
		{
			if(inputStream == null)
			{
				throw new NullPointerException("stream cannot be null");
			}
			this.inputStream = inputStream;
			this.file = null;
			this.url = null;
			this.classpathResource = null;
			this.Reader = null;
		}

	    /// <summary>
	    /// Ctor.
	    /// </summary>
	    /// <param name="reader">reader is any reader for reading a file or string</param>
	    public AdapterInputSource(Reader reader)
		{
			if(reader == null)
			{
				throw new NullPointerException("reader cannot be null");
			}
			this.Reader = reader;
			this.url = null;
			this.classpathResource = null;
			this.file = null;
			this.inputStream  = null;
		}
		
		/// <summary>
		/// Get the resource as an input stream. If this resource was specified as an InputStream, 
		/// return that InputStream, otherwise, create and return a new InputStream from the 
		/// resource. If the source cannot be converted to a stream, return null.
		/// </summary>
		/// <returns>a stream from the resource</returns>
		public Stream GetAsStream()
		{
			if(reader != null)
			{
				return null;
			}
			if(inputStream != null)
			{
				return inputStream;
			}
			if(file != null)
			{
				try
				{
					return file.toURL().openStream();
				} 
				catch (IOException e)
				{
					throw new EPException(e);
				}
			}
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
				return ResolvePathAsStream(classpathResource);
			}
		}
		
		/// <summary>
		/// Return the reader if it was set, null otherwise.
		/// </summary>
		/// <returns>the Reader</returns>
		public Reader GetAsReader()
		{
			return reader;
		}
		
		/// <summary>
		/// Return true if calling getStream() will return a new InputStream created from the
		/// resource, which, assuming that the resource hasn't been changed, will have the same
		/// information as all the previous InputStreams returned by getStream() before they were
		/// manipulated; return false if the call will return the same instance of InputStream that 
		/// has already been obtained.
		/// </summary>
		/// <returns>true if each call to getStream() will create a new InputStream from the
		/// resource, false if each call will get the same instance of the InputStream
		/// </returns>
		public bool IsResettable
		{
			get { return inputStream == null && reader == null; }
		}
		
		private Stream ResolvePathAsStream(String path)
	    {
	    	Stream stream = null;
	    	ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	    	if (classLoader!=null) {
	    		stream = classLoader.getResourceAsStream( path );
	    	}
	    	if ( stream == null ) {
	    		stream = typeof(CSVReader).getResourceAsStream( path );
	    	}
	    	if ( stream == null ) {
	    		stream = typeof(CSVReader).getClassLoader().getResourceAsStream( path );
	    	}
	    	if ( stream == null ) {
	    		throw new EPException( path + " not found" );
	    	}
	    	
	    	return stream;
	    }
	}
}