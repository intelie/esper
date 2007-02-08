using System;
using TableLookupStrategy = net.esper.eql.join.exec.TableLookupStrategy;
using EventTable = net.esper.eql.join.table.EventTable;
using EventType = net.esper.events.EventType;
namespace net.esper.eql.join.plan
{
	
	/// <summary> Abstract specification on how to perform a table lookup.</summary>
	public abstract class TableLookupPlan
	{
		/// <summary> Returns the lookup stream.</summary>
		/// <returns> lookup stream
		/// </returns>
		virtual public int LookupStream
		{
			get
			{
				return lookupStream;
			}
			
		}
		/// <summary> Returns indexed stream.</summary>
		/// <returns> indexed stream
		/// </returns>
		virtual public int IndexedStream
		{
			get
			{
				return indexedStream;
			}
			
		}
		/// <summary> Returns index number to use for looking up in.</summary>
		/// <returns> index number
		/// </returns>
		virtual public int IndexNum
		{
			get
			{
				return indexNum;
			}
			
		}
		private int lookupStream;
		private int indexedStream;
		private int indexNum;
		
		/// <summary> Instantiates the lookup plan into a execution strategy for the lookup.</summary>
		/// <param name="indexesPerStream">- tables for each stream
		/// </param>
		/// <param name="eventTypes">- types of events in stream
		/// </param>
		/// <returns> lookup strategy instance
		/// </returns>
		public abstract TableLookupStrategy MakeStrategy(EventTable[][] indexesPerStream, EventType[] eventTypes);
		
		/// <summary> Ctor.</summary>
		/// <param name="lookupStream">- stream number of stream that supplies event to be used to look up
		/// </param>
		/// <param name="indexedStream">- - stream number of stream that is being access via index/table
		/// </param>
		/// <param name="indexNum">- index to use for lookup
		/// </param>
		internal TableLookupPlan(int lookupStream, int indexedStream, int indexNum)
		{
			this.lookupStream = lookupStream;
			this.indexedStream = indexedStream;
			this.indexNum = indexNum;
		}
		
		public override String ToString()
		{
			return "lookupStream=" + lookupStream + " indexedStream=" + indexedStream + " indexNum=" + indexNum;
		}
	}
}