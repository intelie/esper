using System;
using System.Collections.Generic;

using net.esper.compat;

namespace net.esper.eql.spec
{
	/// <summary> Descriptor generated by INSERT-INTO clauses specified in expressions to insert the
	/// results of statement as a stream to further statements.
	/// </summary>

	public class InsertIntoDesc
	{
		private readonly bool isStream;
		private readonly String eventTypeAlias;
		private IList<String> columnNames;

		/// <summary> Returns true if insert (new data) events are fed, or false for remove (old data) events are fed.</summary>
		/// <returns> true for insert stream, false for remove stream
		/// </returns>
		
		virtual public bool IsStream
		{
			get { return isStream; }
		}
		
		/// <summary> Returns name of event type to use for insert-into stream.</summary>
		/// <returns> event type alias name
		/// </returns>
		
		virtual public String EventTypeAlias
		{
			get { return eventTypeAlias; }
		}

		/// <summary> Returns a list of column names specified optionally in the insert-into clause, or empty if none specified.</summary>
		/// <returns> column names or empty list if none supplied
		/// </returns>

		virtual public IList<String> ColumnNames
		{
			get { return columnNames; }
		}

		/// <summary> Ctor.</summary>
		/// <param name="isStream">is true if insert (new data) events are fed, or false for remove (old data) events are fed
		/// </param>
		/// <param name="eventTypeAlias">is the event type alias name
		/// </param>
		
		public InsertIntoDesc(bool isStream, String eventTypeAlias)
		{
			this.isStream = isStream;
			this.eventTypeAlias = eventTypeAlias;
			this.columnNames = new List < String >();
		}
		
		/// <summary> Add a column name to the insert-into clause.</summary>
		/// <param name="columnName">to add
		/// </param>

		public virtual void Add(String columnName)
		{
			columnNames.Add(columnName);
		}
	}
}
