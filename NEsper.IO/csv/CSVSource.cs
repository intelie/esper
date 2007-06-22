using System;
using System.IO;

using net.esper.adapter;
using net.esper.compat;

namespace net.esper.adapter.csv
{
	/// <summary>
	/// A wrapper for a Stream or a TextReader.
	/// </summary>

	public class CSVSource
	{
		private readonly AdapterInputSource source;
		private TextReader reader;
		private Stream stream;
		
		/// <summary>Ctor.</summary>
		/// <param name="source">the AdapterInputSource from which to obtain the uderlying resource</param>

		public CSVSource(AdapterInputSource source)
		{
			stream = source.GetAsStream() ;
			if ( stream == null )
			{
				reader = source.GetAsReader() ;
			}
			this.source = source;
		}
		
		/// <summary>Close the underlying resource.</summary>
		/// <throws>IOException to indicate an io error</throws>

		public void Close()
		{
			if(stream != null)
			{
				stream.Close();
			}
			else
			{
				reader.Close();
			}
		}
		
		/// <summary>Read from the underlying resource.</summary>
		/// <returns>the result of the read</returns>
		/// <throws>IOException for io errors</throws>

		public int Read() 
		{
			if(stream != null)
			{
				return stream.ReadByte() ;
			}
			else
			{
				return reader.Read();
			}
		}
		
		/// <summary>Return true if the underlying resource is resettable.</summary>
		/// <returns>true if resettable, false otherwise</returns>

		public bool IsResettable
		{
			get { return source.IsResettable; }
		}
		
		/// <summary>Reset to the last mark position.</summary>
		/// <throws>IOException for io errors</throws>

		public void ResetToMark()
		{
//			if(stream != null)
//			{
//				stream.Reset();
//			}
//			else
//			{
//				reader.Reset();
//			}
		}
		
		/// <summary>Set the mark position.</summary>
		/// <param name="readAheadLimit">is the maximum number of read-ahead events</param>
		/// <throws>IOException when an io error occurs</throws>

		public void Mark(int readAheadLimit)
		{
			throw new NotSupportedException() ;
			
//			if(stream != null)
//			{
//				stream.mark(readAheadLimit);
//			}
//			else
//			{
//				reader.mark(readAheadLimit);
//			}
		}
		
		/// <summary>Reset to the beginning of the resource.</summary>

		public void Reset()
		{
			if(!IsResettable)
			{
				throw new UnsupportedOperationException("Reset not supported: underlying source cannot be reset");
			}
			
			if(stream != null)
			{
				stream = source.GetAsStream();
			}
			else
			{
				reader = source.GetAsReader();
			}
		}
	}
}
