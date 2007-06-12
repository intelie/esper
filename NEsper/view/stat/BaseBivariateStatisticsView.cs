using System;
using System.Collections.Generic;

using net.esper.core;
using net.esper.collection;
using net.esper.events;
using net.esper.view;

namespace net.esper.view.stat
{
	/// <summary> View for computing statistics that require 2 input variable arrays containing X and Y datapoints.
	/// Subclasses compute correlation or regression values, for instance.
	/// </summary>

	public abstract class BaseBivariateStatisticsView
		: ViewSupport
		, CloneableView
	{
		/// <summary>
		/// This bean can be overridden by subclasses providing extra values such as correlation, regression.
		/// </summary>

		internal BaseStatisticsBean statisticsBean;

		private String fieldNameX;
		private String fieldNameY;
		private EventPropertyGetter fieldXGetter;
		private EventPropertyGetter fieldYGetter;

		/// <summary>
		/// Services required by implementing classes.
		/// </summary>

		internal StatementContext statementContext;

        /// <summary>
        /// Gets or sets the field name of the field supplying X data points.
        /// </summary>
        /// <value>The field name X.</value>

		virtual public String FieldNameX
		{
			get { return fieldNameX; }
		}

        /// <summary>
        /// Gets or sets the field name of the field supplying Y data points.
        /// </summary>
        /// <value>The field name Y.</value>

		virtual public String FieldNameY
		{
			get { return fieldNameY; }
		}

	    /// <summary>
	    /// Constructor requires the name of the two fields to use in the parent view to compute the statistics.
	    /// </summary>
	    /// <param name="statisticsBean">
	    /// is the base class prodiving sum of X and Y and squares for use by subclasses
	    /// </param>
	    /// <param name="fieldNameX">
	    /// is the name of the field within the parent view to get the X values from
	    /// </param>
	    /// <param name="fieldNameY">
	    /// is the name of the field within the parent view to get the Y values from
	    /// </param>
	    /// <param name="statementContext">contains required view services</param>
	    public BaseBivariateStatisticsView(StatementContext statementContext,
	                                       BaseStatisticsBean statisticsBean,
	                                       String fieldNameX,
	                                       String fieldNameY)
	    {
	        this.statementContext = statementContext;
	        this.statisticsBean = statisticsBean;
	        this.fieldNameX = fieldNameX;
	        this.fieldNameY = fieldNameY;
	    }

        /// <summary>
        /// Duplicates the view.
        /// <p>
        /// Expected to return a same view in initialized state for grouping.
        /// </p>
        /// </summary>
        /// <param name="statementContext">is services for the view</param>
        /// <returns>duplicated view</returns>
	    abstract public View CloneView(StatementContext statementContext);

        /// <summary>
        /// Gets or sets the view's parent viewable.
        /// </summary>
		public override Viewable Parent
		{
            set
            {
                Viewable parent = value;

                base.Parent = parent;
                if (parent != null)
                {
                    fieldXGetter = parent.EventType.GetGetter(fieldNameX);
                    fieldYGetter = parent.EventType.GetGetter(fieldNameY);
                }
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
		public override void Update( EventBean[] newData, EventBean[] oldData )
		{
			// If we have child views, keep a reference to the old values, so we can fireStatementStopped them as old data event.
			BaseStatisticsBean oldValues = null;
			if ( this.HasViews )
			{
				oldValues = (BaseStatisticsBean) statisticsBean.Clone();
			}

			// add data points to the bean
			if ( newData != null )
			{
				for ( int i = 0 ; i < newData.Length ; i++ )
				{
					double X = Convert.ToDouble( fieldXGetter.GetValue( newData[i] ) ) ;
					double Y = Convert.ToDouble( fieldYGetter.GetValue( newData[i] ) ) ;
					statisticsBean.AddPoint( X, Y );
				}
			}

			// remove data points from the bean
			if ( oldData != null )
			{
				for ( int i = 0 ; i < oldData.Length ; i++ )
				{
					double X = Convert.ToDouble( fieldXGetter.GetValue( oldData[i] ) ) ;
					double Y = Convert.ToDouble( fieldYGetter.GetValue( oldData[i] ) ) ;
					statisticsBean.RemovePoint( X, Y );
				}
			}

			// If there are child view, fireStatementStopped update method
			if ( this.HasViews )
			{
				// Make a copy of the current values since if we change the values subsequently, the handed-down
				// values should not change
				BaseStatisticsBean newValues = (BaseStatisticsBean) statisticsBean.Clone();
				EventBean newValuesEvent = statementContext.EventAdapterService.AdapterForBean( newValues );
				EventBean oldValuesEvent = statementContext.EventAdapterService.AdapterForBean( oldValues );
				UpdateChildren( new EventBean[] { newValuesEvent }, new EventBean[] { oldValuesEvent } );
			}
		}

        /// <summary>
        /// Returns an enumerator that iterates through the collection.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.Collections.Generic.IEnumerator`1"></see> that can be used to iterate through the collection.
        /// </returns>
		public override IEnumerator<EventBean> GetEnumerator()
		{
            yield return statementContext.EventAdapterService.AdapterForBean(statisticsBean);
		}
	}
}
