using System;

using net.esper.compat;
using net.esper.events;
using net.esper.eql.join.exec;
using net.esper.eql.join.table;

namespace net.esper.eql.join.plan
{
	/// <summary>
	///  Plan to perform an indexed table lookup.
	/// </summary>
	
	public class IndexedTableLookupPlan:TableLookupPlan
	{
		/// <summary> Returns property names to use for lookup in index.</summary>
		/// <returns> property names.
		/// </returns>
	
		virtual public String[] KeyProperties
		{
			get
			{
				return keyProperties;
			}
		}
		
		private String[] keyProperties;
		
		/// <summary> Ctor.</summary>
		/// <param name="lookupStream">- stream that generates event to look up for
		/// </param>
		/// <param name="indexedStream">- stream to index table lookup
		/// </param>
		/// <param name="indexNum">- index number for the table containing the full unindexed contents
		/// </param>
		/// <param name="keyProperties">- properties to use in lookup event to access index
		/// </param>
		public IndexedTableLookupPlan(int lookupStream, int indexedStream, int indexNum, String[] keyProperties):base(lookupStream, indexedStream, indexNum)
		{
			this.keyProperties = keyProperties;
		}
		
		public override TableLookupStrategy MakeStrategy(EventTable[][] indexesPerStream, EventType[] eventTypes)
		{
			PropertyIndexedEventTable index = (PropertyIndexedEventTable) indexesPerStream[this.IndexedStream][this.IndexNum];
			return new IndexedTableLookupStrategy(eventTypes[this.LookupStream], keyProperties, index);
		}
		
		public override String ToString()
		{
			return
                "IndexedTableLookupPlan " + base.ToString() + 
                " keyProperties=" + CollectionHelper.Render(keyProperties);
		}
	}
}
