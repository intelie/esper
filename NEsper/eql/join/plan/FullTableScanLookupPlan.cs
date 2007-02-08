using System;
using FullTableScanLookupStrategy = net.esper.eql.join.exec.FullTableScanLookupStrategy;
using TableLookupStrategy = net.esper.eql.join.exec.TableLookupStrategy;
using EventTable = net.esper.eql.join.table.EventTable;
using UnindexedEventTable = net.esper.eql.join.table.UnindexedEventTable;
using EventType = net.esper.events.EventType;
namespace net.esper.eql.join.plan
{
	
	/// <summary> Plan for a full table scan.</summary>
	public class FullTableScanLookupPlan:TableLookupPlan
	{
		/// <summary> Ctor.</summary>
		/// <param name="lookupStream">- stream that generates event to look up for
		/// </param>
		/// <param name="indexedStream">- stream to full table scan
		/// </param>
		/// <param name="indexNum">- index number for the table containing the full unindexed contents
		/// </param>
		public FullTableScanLookupPlan(int lookupStream, int indexedStream, int indexNum):base(lookupStream, indexedStream, indexNum)
		{
		}
		
		public override TableLookupStrategy MakeStrategy(EventTable[][] indexesPerStream, EventType[] eventTypes)
		{
			UnindexedEventTable index = (UnindexedEventTable) indexesPerStream[this.IndexedStream][this.IndexNum];
			return new FullTableScanLookupStrategy(index);
		}
		
		public override String ToString()
		{
			return "FullTableScanLookupPlan " + base.ToString();
		}
	}
}