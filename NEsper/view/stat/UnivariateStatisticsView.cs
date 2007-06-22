using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.collection;
using net.esper.core;
using net.esper.events;
using net.esper.view;

namespace net.esper.view.stat
{
    /// <summary> View for computing statistics, which the view exposes via fields representing the sum, count, standard deviation
    /// for sample and for population and variance.
    /// </summary>

    public sealed class UnivariateStatisticsView
		: ViewSupport
		, CloneableView
    {
        /// <summary>
        /// Gets or sets field name of the field to report statistics on.
        /// </summary>
        /// <value>The name of the field.</value>
        /// <returns> field name
        /// </returns>
        public String FieldName
        {
            get { return fieldName; }
        }

        private readonly StatementContext statementContext;
        private readonly EventType eventType;
        private readonly String fieldName;
        private EventPropertyGetter fieldGetter;
        private readonly BaseStatisticsBean baseStatisticsBean = new BaseStatisticsBean();

        /// <summary>
        /// Constructor requires the name of the field to use in the parent view to compute the statistics.
        /// </summary>
        /// <param name="statementContext">contains required view services</param>
        /// <param name="fieldName">is the name of the field within the parent view to use to get numeric data points for this view to
        /// compute the statistics on.</param>
	    public UnivariateStatisticsView(StatementContext statementContext, String fieldName)
	    {
	        this.statementContext = statementContext;
	        this.fieldName = fieldName;
	        eventType = CreateEventType(statementContext);
	    }

        /// <summary>
        /// Duplicates the view.
        /// <p>
        /// Expected to return a same view in initialized state for grouping.
        /// </p>
        /// </summary>
        /// <param name="statementContext">is services for the view</param>
        /// <returns>duplicated view</returns>
	    public View CloneView(StatementContext statementContext)
	    {
	        return new UnivariateStatisticsView(statementContext, fieldName);
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
                Viewable parent = value;

                base.Parent = value;
                if (parent != null)
                {
                    fieldGetter = parent.EventType.GetGetter(fieldName);
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
        public override void Update(EventBean[] newData, EventBean[] oldData)
        {
            // If we have child views, keep a reference to the old values, so we can fireStatementStopped them as old data event.
            EventBean oldDataMap = null;
            if (this.HasViews)
            {
                oldDataMap = PopulateMap(baseStatisticsBean, statementContext.EventAdapterService, eventType);
            }

            // add data points to the bean
            if (newData != null)
            {
                for (int i = 0; i < newData.Length; i++)
                {
                    double point = Convert.ToDouble(fieldGetter.GetValue(newData[i])) ;
                    baseStatisticsBean.AddPoint(point, 0);
                }
            }

            // remove data points from the bean
            if (oldData != null)
            {
                for (int i = 0; i < oldData.Length; i++)
                {
					double point = Convert.ToDouble( fieldGetter.GetValue( oldData[i] ) );
                    baseStatisticsBean.RemovePoint(point, 0);
                }
            }

            // If there are child view, fireStatementStopped update method
            if (this.HasViews)
            {
                EventBean newDataMap = PopulateMap(baseStatisticsBean, statementContext.EventAdapterService, eventType);
                UpdateChildren(new EventBean[] { newDataMap }, new EventBean[] { oldDataMap });
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
            yield return PopulateMap(baseStatisticsBean, statementContext.EventAdapterService, eventType);
        }

        /// <summary>
        /// Returns a <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </returns>

        public override String ToString()
        {
            return this.GetType().FullName + " fieldName=" + fieldName;
        }

        private static EventBean PopulateMap(
        	BaseStatisticsBean baseStatisticsBean,
        	EventAdapterService eventAdapterService,
        	EventType eventType)
        {
        	DataDictionary result = new DataDictionary() ;
            result[ViewFieldEnum.UNIVARIATE_STATISTICS__COUNT.Name]    = baseStatisticsBean.N;
            result[ViewFieldEnum.UNIVARIATE_STATISTICS__SUM.Name]      = baseStatisticsBean.XSum;
            result[ViewFieldEnum.UNIVARIATE_STATISTICS__STDDEV.Name]   = baseStatisticsBean.XStandardDeviationSample;
            result[ViewFieldEnum.UNIVARIATE_STATISTICS__STDDEVPA.Name] = baseStatisticsBean.XStandardDeviationPop;
            result[ViewFieldEnum.UNIVARIATE_STATISTICS__VARIANCE.Name] = baseStatisticsBean.XVariance;
            result[ViewFieldEnum.UNIVARIATE_STATISTICS__AVERAGE.Name]  = baseStatisticsBean.XAverage;
            return eventAdapterService.CreateMapFromValues(result, eventType);
        }


	    /// <summary>Creates the event type for this view.</summary>
	    /// <param name="statementContext">is the event adapter service</param>
	    /// <returns>event type of view</returns>
	    public static EventType CreateEventType(StatementContext statementContext)
	    {
	        EDictionary<String, Type> eventTypeMap = new HashDictionary<String, Type>();
	        eventTypeMap.Put(ViewFieldEnum.UNIVARIATE_STATISTICS__COUNT.Name, typeof(long));
	        eventTypeMap.Put(ViewFieldEnum.UNIVARIATE_STATISTICS__SUM.Name, typeof(double));
	        eventTypeMap.Put(ViewFieldEnum.UNIVARIATE_STATISTICS__STDDEV.Name, typeof(double));
	        eventTypeMap.Put(ViewFieldEnum.UNIVARIATE_STATISTICS__STDDEVPA.Name, typeof(double));
	        eventTypeMap.Put(ViewFieldEnum.UNIVARIATE_STATISTICS__VARIANCE.Name, typeof(double));
	        eventTypeMap.Put(ViewFieldEnum.UNIVARIATE_STATISTICS__AVERAGE.Name, typeof(double));
	        return statementContext.EventAdapterService.CreateAnonymousMapType(eventTypeMap);
	    }
    }
}
