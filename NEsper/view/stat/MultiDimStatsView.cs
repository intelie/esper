using System;
using System.Collections.Generic;

using org.apache.commons.logging;

using net.esper.compat;
using net.esper.collection;
using net.esper.core;
using net.esper.events;
using net.esper.view;
using net.esper.view.stat.olap;

namespace net.esper.view.stat
{
	/// <summary> This view compiles OLAP cubes for the specified fields. New data from the parent view is entered into
	/// one or more fact cubes (see MultidimCube). Old data from the parent view is removed from a fact cube.
	/// The dimensions of the fact cube are specified as parameters to the view. The fact cube can be one-dimensional,
	/// two-dimensional (tabular) or 3-dimenstional (tabular with pages).
	/// Parameters are:
	/// A mandatory array of derived measure names, such as "count", "stddev" etc., (see ViewFieldEnum)
	/// A mandatory measure field name. This field supplies the fact values in the cells of the cube.
	/// A mandatory column field name. This field supplies the members of dimension zero (column, 1-dim).
	/// An optional row field name. This field supplies the members of dimension one (row, 2-dim).
	/// An optional page field name. This field supplies the members of dimension two (page, 3-dim).
	/// 
	/// The view post new data to child views that contains a Map with the Cube (see Cube). It does not post old data.
	/// </summary>

	public sealed class MultiDimStatsView
		: ViewSupport
		, CloneableView
	{
		private static MultidimCubeCellFactory<BaseStatisticsBean> multidimCubeCellFactory;

		private EventType eventType;

		internal class AnonMultidimCubeCellFactory : MultidimCubeCellFactory<BaseStatisticsBean>
		{
			public BaseStatisticsBean NewCell()
			{
				return new BaseStatisticsBean();
			}

			public BaseStatisticsBean[] NewCells( int numElements )
			{
				return new BaseStatisticsBean[numElements];
			}
		}

		static MultiDimStatsView()
		{
			multidimCubeCellFactory = new AnonMultidimCubeCellFactory();
		}

		private readonly StatementContext statementContext;
		private readonly String[] derivedMeasures;
		private readonly String measureField;
		private readonly String columnField;
		private readonly String rowField;
		private readonly String pageField;

		private EventPropertyGetter measureFieldGetter;
		private EventPropertyGetter columnFieldGetter;
		private EventPropertyGetter rowFieldGetter;
		private EventPropertyGetter pageFieldGetter;

		private MultidimCube<BaseStatisticsBean> multidimCube;

		/**
	     * Constructor.
	     * @param derivedMeasures is an array of ViewFieldEnum names defining the measures to derive
	     * @param measureField defines the field supplying measures
	     * @param columnField defines the field supplying column dimension members
	     * @param rowField defines an optional field supplying row dimension members
	     * @param pageField defines an optional field supplying page dimension members
	     * @param statementContext contains required view services
	     */
	    public MultiDimStatsView(StatementContext statementContext,
	                             String[] derivedMeasures,
								 String measureField,
								 String columnField,
								 String rowField,
								 String pageField)
	    {
	        this.statementContext = statementContext;
	        this.derivedMeasures = derivedMeasures;
	        this.measureField = measureField;
	        this.columnField = columnField;
	        this.rowField = rowField;
	        this.pageField = pageField;
	        eventType = CreateEventType(statementContext);
	    }

	    public View CloneView(StatementContext statementContext)
	    {
	        return new MultiDimStatsView(statementContext, derivedMeasures, measureField, columnField, rowField, pageField);
	    }

		/// <summary>
        /// Gets or sets the names of measures to derive from facts.
        /// </summary>

        public String[] DerivedMeasures
		{
            get { return derivedMeasures; }
		}

		/// <summary>
        /// Gets or sets the name of the field to extract the measure values from.
        /// </summary>

        public string MeasureField
		{
			get { return measureField; }
		}

		/// <summary>
        /// Get or sets the name of the field to extract the column values from.
        /// </summary>

        public string ColumnField
		{
            get { return columnField; }
		}

        /// <summary>
        /// Gets or sets the row field.
        /// </summary>
        /// <value>The row field.</value>
        public string RowField
		{
            get { return rowField; }
		}

		/// <summary> Gets or sets the name of the field to extract the page values from.</summary>
		/// <returns> field for page values
		/// </returns>

        public string PageField
		{
            get { return pageField; }
		}

		/// <summary> For unit testing, returns fact cube.</summary>
		/// <returns> fact cube
		/// </returns>
		public MultidimCube<BaseStatisticsBean> FactCube
		{
			get { return multidimCube; }
		}

        /// <summary>
        /// Gets or sets the View's parent Viewable.
        /// </summary>
        /// <value></value>
        /// <returns> viewable
        /// </returns>
        public override Viewable Parent
        {
            set
            {
                base.Parent = value;

                Viewable parent = value;

                if (parent == null)
                {
                    return;
                }

                measureFieldGetter = parent.EventType.GetGetter(measureField);
                columnFieldGetter = parent.EventType.GetGetter(columnField);

                if (rowField != null)
                {
                    rowFieldGetter = parent.EventType.GetGetter(rowField);
                }

                if (pageField != null)
                {
                    pageFieldGetter = parent.EventType.GetGetter(pageField);
                }

                // Construct fact cube according to the number of dimensions supplied
                if (pageField != null)
                {
                    String[] dimensionNames = new String[] { measureField, columnField, rowField, pageField };
                    multidimCube = new MultidimCubeImpl<BaseStatisticsBean>(dimensionNames, multidimCubeCellFactory);
                    multidimCube.SetMembers(2, parent.EventType.GetPropertyType(pageField));
                    multidimCube.SetMembers(1, parent.EventType.GetPropertyType(rowField));
                }
                else if (rowField != null)
                {
                    String[] dimensionNames = new String[] { measureField, columnField, rowField };
                    multidimCube = new MultidimCubeImpl<BaseStatisticsBean>(dimensionNames, multidimCubeCellFactory);
                    multidimCube.SetMembers(1, parent.EventType.GetPropertyType(rowField));
                }
                else
                {
                    String[] dimensionNames = new String[] { measureField, columnField };
                    multidimCube = new MultidimCubeImpl<BaseStatisticsBean>(dimensionNames, multidimCubeCellFactory);
                }

                multidimCube.SetMembers(0, parent.EventType.GetPropertyType(columnField));
            }
        }

        /// <summary>
        /// Notify that data has been added or removed from the Viewable parent.
        /// The last object in the newData array of objects would be the newest object added to the parent view.
        /// The first object of the oldData array of objects would be the oldest object removed from the parent view.
        /// <para>
        /// If the call to update contains new (inserted) data, then the first argument will be a non-empty list and the
        /// second will be empty. Similarly, if the call is a notification of deleted data, then the first argument will be
        /// empty and the second will be non-empty. Either the newData or oldData will be non-null.
        /// This method won't be called with both arguments being null, but either one could be null.
        /// The same is true for zero-length arrays. Either newData or oldData will be non-empty.
        /// If both are non-empty, then the update is a modification notification.
        /// </para>
        /// 	<para>
        /// When update() is called on a view by the parent object, the data in newData will be in the collection of the
        /// parent, and its data structures will be arranged to reflect that.
        /// The data in oldData will not be in the parent's data structures, and any access to the parent will indicate that
        /// that data is no longer there.
        /// </para>
        /// </summary>
        /// <param name="newData">is the new data that has been added to the parent view</param>
        /// <param name="oldData">is the old data that has been removed from the parent view</param>
        public override void Update(EventBean[] newData, EventBean[] oldData)
		{
			if ( log.IsDebugEnabled )
			{
				log.Debug( ".update Received update, " +
							"  newData.Length==" + ( ( newData == null ) ? 0 : newData.Length ) +
							"  oldData.Length==" + ( ( oldData == null ) ? 0 : oldData.Length ) );
			}

			if ( newData != null )
			{
				foreach ( EventBean newValue in newData )
				{
                    ProcessElement(newValue, true);
				}
			}

			if ( oldData != null )
			{
				foreach ( EventBean oldValue in oldData )
				{
					ProcessElement( oldValue, false );
				}
			}

			if ( HasViews )
			{
				EventBean postNewData = PopulateEvent();
				UpdateChildren( new EventBean[] { postNewData }, null );
			}
		}

        /// <summary>
        /// Provides metadata information about the type of object the event collection contains.
        /// </summary>
        /// <value></value>
        /// <returns>
        /// metadata for the objects in the collection
        /// </returns>
        public override EventType EventType
		{
            get { return eventType; }
		}

        /// <summary>
        /// Returns an enumerator that iterates through the collection.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.Collections.Generic.IEnumerator`1"></see> that can be used to iterate through the collection.
        /// </returns>
        public override IEnumerator<EventBean> GetEnumerator()
		{
            yield return PopulateEvent();
		}

        /// <summary>
        /// Processes the element.
        /// </summary>
        /// <param name="element">The element.</param>
        /// <param name="isNewData">if set to <c>true</c> [is new data].</param>
		private void ProcessElement( EventBean element, Boolean isNewData )
		{
			MultiKeyUntyped coordinates = null;

			// Extract member values for each dimension
			Object columnMember = columnFieldGetter.GetValue( element );
			if ( pageFieldGetter != null )
			{
				Object rowMember = rowFieldGetter.GetValue( element );
				Object pageMember = pageFieldGetter.GetValue( element ) ;
				coordinates = new MultiKeyUntyped( columnMember, rowMember, pageMember );
			}
			else if ( rowFieldGetter != null )
			{
				Object rowMember = rowFieldGetter.GetValue( element );
				coordinates = new MultiKeyUntyped( columnMember, rowMember );
			}
			else
			{
				coordinates = new MultiKeyUntyped( columnMember );
			}

			// Extract measure value
			double measureValue = Convert.ToDouble( measureFieldGetter.GetValue( element ) ) ;

			// Add or remove from cube
			BaseStatisticsBean cell = multidimCube.GetCellAddMembers( coordinates );
			if ( isNewData )
			{
				cell.AddPoint( measureValue );
			}
			else
			{
				cell.RemovePoint( measureValue );
			}
		}

		private EventBean PopulateEvent()
		{
			CubeImpl cube = new CubeImpl( multidimCube, derivedMeasures );

			EDataDictionary result = new EDataDictionary();
			result[ViewFieldEnum.MULTIDIM_OLAP__CUBE.Name] = cube ;
			EventBean eventBean = statementContext.EventAdapterService.CreateMapFromValues( result, eventType );
			return eventBean;
		}

	    /**
	     * Creates the event type for this view.
	     * @param statementContext is the event adapter service
	     * @return event type of view
	     */
	    public static EventType CreateEventType(StatementContext statementContext)
	    {
	        EDictionary<String, Type> schemaMap = new EHashDictionary<String, Type>();
	        schemaMap.Put(ViewFieldEnum.MULTIDIM_OLAP__CUBE.Name, typeof(Cube));
	        EventType eventType = statementContext.EventAdapterService.CreateAnonymousMapType(schemaMap);
	        return eventType;
	    }

		private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
}
