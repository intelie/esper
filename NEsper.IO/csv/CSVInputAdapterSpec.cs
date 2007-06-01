using System;
using System.Collections.Generic;

using net.esper.adapter;

namespace net.esper.adapter.csv
{
	/// <summary>A spec for CSVAdapters.</summary>

	public class CSVInputAdapterSpec
	{
		private bool usingEngineThread;
		private String timestampColumn;
		private String eventTypeAlias;
		private AdapterInputSource adapterInputSource;
		private int? eventsPerSec;
		private String[] propertyOrder;
		private bool looping;
		private IDictionary<String, Type> propertyTypes;
		
		/// <summary>Ctor.</summary>
		/// <param name="adapterInputSource">the source for the CSV data</param>
		/// <param name="eventTypeAlias">the alias for the event type created from the CSV data</param>

		public CSVInputAdapterSpec(AdapterInputSource adapterInputSource, String eventTypeAlias)
		{
			this.adapterInputSource = adapterInputSource;
			this.eventTypeAlias = eventTypeAlias;
		}

		/// <summary>
		/// Gets or sets the number of events per seconds.
		/// </summary>

		public int? EventsPerSec
		{
			get { return this.eventsPerSec; }
			set { this.eventsPerSec = value; }
		}

		/// <summary>
		/// Gets or sets the property order of the properties in the CSV file
		/// </summary>

		public String[] PropertyOrder
		{
			get { return this.propertyOrder ; }
			set { this.propertyOrder = value; }
		}

		/// <summary>
		/// Gets or sets the flag that indicates if the adapter is looping
		/// </summary>

		public bool IsLooping
		{
			get { return this.looping ; }
			set { this.looping = value; }
		}
		
		/// <summary>
		/// Gets or sets the propertyTypes value
		/// </summary>
		/// <returns>
		/// a mapping between the names and types of the properties in the
		/// CSV file; this will also be the form of the Map event created
		/// from the data
		/// </returns>

		public IDictionary<String, Type> PropertyTypes
		{
			get { return propertyTypes; }
			set { this.propertyTypes = value; }
		}

		/// <summary>
		/// Gets or sets a flag indicating whether to use the engine timer thread for work or not.
		/// Setting the value to true uses the engine timer thread for work.  Setting the value
		/// to false, uses the current thread.
		/// </summary>

		public bool IsUsingEngineThread
		{
			get { return usingEngineThread; }
			set { this.usingEngineThread = value ; }
		}

		/// <summary>
		/// Gets or sets the timestamp column name.
		/// </summary>
		/// <returns>the name of the column to use for timestamps</returns>

		public String TimestampColumn
		{
			get { return this.timestampColumn; }
			set { this.timestampColumn = value; }
		}

		/// <summary>
		/// Gets or sets the adapter input source.
		/// </summary>
		
		public AdapterInputSource AdapterInputSource
		{
			get { return adapterInputSource; }
			set { this.adapterInputSource = value; }
		}

		/// <summary>
		/// Gets or sets the event type alias.
		/// </summary>
		
		public String EventTypeAlias
		{
			get { return eventTypeAlias; }
			set { this.eventTypeAlias = value; }
		}
	}
}