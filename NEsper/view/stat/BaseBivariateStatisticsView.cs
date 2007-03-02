using System;
using System.Collections.Generic;

using net.esper.collection;
using net.esper.events;
using net.esper.view;

namespace net.esper.view.stat
{
	
	/// <summary> View for computing statistics that require 2 input variable arrays containing X and Y datapoints.
	/// Subclasses compute correlation or regression values, for instance.
	/// </summary>

	public abstract class BaseBivariateStatisticsView : ViewSupport, ContextAwareView
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

		internal ViewServiceContext viewServiceContext;

		/// <summary>
		/// Returns the context instances used by the view.
		/// </summary>
		/// <value></value>
		/// <returns> context instance
		/// </returns>
		/// <param name="viewServiceContext">with service handles
		/// </param>

		virtual public ViewServiceContext ViewServiceContext
		{
			get { return viewServiceContext; }
			set { this.viewServiceContext = value; }
		}

		/// <summary> Returns the field name of the field supplying X data points.</summary>
		/// <returns> X field name
		/// </returns>
		/// <summary> Sets the field name of the X data column.</summary>
		/// <param name="fieldNameX">is the field name from which to get X data values.
		/// </param>

		virtual public String FieldNameX
		{
			get { return fieldNameX; }
			set { this.fieldNameX = value; }
		}

		/// <summary> Returns the field name of the field supplying Y data points.</summary>
		/// <returns> Y field name
		/// </returns>
		/// <summary> Sets the field name of the Y data column.</summary>
		/// <param name="fieldNameY">is the field name from which to get Y data values.
		/// </param>

		virtual public String FieldNameY
		{
			get { return fieldNameY; }
			set { this.fieldNameY = value; }
		}

		/// <summary>
		/// Constructor
		/// </summary>
		
	    public BaseBivariateStatisticsView()
    	{
    	}
	    
		/// <summary>
		/// Constructor requires the name of the two fields to use in the parent view to compute the statistics.
		/// </summary>
		/// <param name="statisticsBean">is the base class prodiving sum of X and Y and squares for use by subclasses</param>
		/// <param name="fieldNameX">is the name of the field within the parent view to get the X values from</param>
		/// <param name="fieldNameY">is the name of the field within the parent view to get the Y values from</param>

		public BaseBivariateStatisticsView( BaseStatisticsBean statisticsBean, String fieldNameX, String fieldNameY )
		{
			this.statisticsBean = statisticsBean;
			this.fieldNameX = fieldNameX;
			this.fieldNameY = fieldNameY;
		}

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

		public override String AttachesTo( Viewable parentView )
		{
			return PropertyCheckHelper.checkNumeric( parentView.EventType, fieldNameX, fieldNameY );
		}

		public override void Update( EventBean[] newData, EventBean[] oldData )
		{
			// If we have child views, keep a reference to the old values, so we can fire them as old data event.
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

			// If there are child view, fire update method
			if ( this.HasViews )
			{
				// Make a copy of the current values since if we change the values subsequently, the handed-down
				// values should not change
				BaseStatisticsBean newValues = (BaseStatisticsBean) statisticsBean.Clone();
				EventBean newValuesEvent = viewServiceContext.EventAdapterService.AdapterForBean( newValues );
				EventBean oldValuesEvent = viewServiceContext.EventAdapterService.AdapterForBean( oldValues );
				UpdateChildren( new EventBean[] { newValuesEvent }, new EventBean[] { oldValuesEvent } );
			}
		}

		public override IEnumerator<EventBean> GetEnumerator()
		{
			return new SingleEventIterator( viewServiceContext.EventAdapterService.AdapterForBean( statisticsBean ) );
		}
	}
}
