using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.collection;
using net.esper.core;
using net.esper.events;
using net.esper.view;

namespace net.esper.view.stat
{
    /// <summary>
    /// View for computing a weighted average. The view uses 2 fields within the parent view to compute
    /// the weighted average. The X field and weight field. In a price-volume example it calculates the
    /// volume-weighted average price as (sum(price * volume) / sum(volume)).
    /// <c>
    ///     Example: weighted_avg("price", "volume")
    /// </c>
    /// </summary>
    public sealed class WeightedAverageView
		: ViewSupport
		, CloneableView
    {
        /// <summary>
        /// Gets or sets the name of the field supplying the X values.
        /// </summary>
        /// <value>The field name X.</value>
        /// <returns> field name supplying X data points</returns>

        public String FieldNameX
        {
            get { return fieldNameX; }
        }

        /// <summary>
        /// Gets or sets the name of the field supplying the weight values.
        /// </summary>
        /// <value>The field name weight.</value>
        /// <returns> field name supplying weight
        /// </returns>

        public String FieldNameWeight
        {
            get { return fieldNameWeight; }
        }

        private readonly EventType eventType;
        private readonly StatementContext statementContext;
        private readonly String fieldNameX;
        private readonly String fieldNameWeight;
        private EventPropertyGetter fieldXGetter;
        private EventPropertyGetter fieldWeightGetter;

        private double sumXtimesW = Double.NaN;
        private double sumW = Double.NaN;
        private double currentValue = Double.NaN;

	   /**
	     * Constructor requires the name of the field to use in the parent view to compute the weighted average on,
	     * as well as the name of the field in the parent view to get the weight from.
	     * @param fieldNameX is the name of the field within the parent view to use to get numeric data points for this view to
	     * compute the average for.
	     * @param fieldNameWeight is the field name for the weight to apply to each data point
	     * @param statementContext contains required view services
	     */
	    public WeightedAverageView(StatementContext statementContext, String fieldNameX, String fieldNameWeight)
	    {
	        this.fieldNameX = fieldNameX;
	        this.fieldNameWeight = fieldNameWeight;
	        this.statementContext = statementContext;
	        eventType = CreateEventType(statementContext);
	    }

	    public View CloneView(StatementContext statementContext)
	    {
	        return new WeightedAverageView(statementContext, fieldNameX, fieldNameWeight);
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
                    fieldXGetter = parent.EventType.GetGetter(fieldNameX);
                    fieldWeightGetter = parent.EventType.GetGetter(fieldNameWeight);
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
            double oldValue = currentValue;

            // add data points to the bean
            if (newData != null)
            {
                for (int i = 0; i < newData.Length; i++)
                {
                	double point = Convert.ToDouble(fieldXGetter.GetValue(newData[i]));
                	double weight = Convert.ToDouble(fieldWeightGetter.GetValue(newData[i]));

                    if (Double.IsNaN(sumXtimesW))
                    {
                        sumXtimesW = point * weight;
                        sumW = weight;
                    }
                    else
                    {
                        sumXtimesW += point * weight;
                        sumW += weight;
                    }
                }
            }

            // remove data points from the bean
            if (oldData != null)
            {
                for (int i = 0; i < oldData.Length; i++)
                {
                	double point = Convert.ToDouble(fieldXGetter.GetValue(oldData[i]));
                	double weight = Convert.ToDouble(fieldWeightGetter.GetValue(oldData[i]));

                    sumXtimesW -= point * weight;
                    sumW -= weight;
                }
            }

            if (sumW != 0)
            {
                currentValue = sumXtimesW / sumW;
            }
            else
            {
                currentValue = Double.NaN;
            }

            // If there are child view, fireStatementStopped update method
            if (this.HasViews)
            {
                EDataDictionary newDataMap = new EDataDictionary();
                newDataMap[ViewFieldEnum.WEIGHTED_AVERAGE__AVERAGE.Name] = currentValue;
                EventBean newDataEvent = statementContext.EventAdapterService.CreateMapFromValues(newDataMap, eventType);

                EDataDictionary oldDataMap = new EDataDictionary();
                oldDataMap[ViewFieldEnum.WEIGHTED_AVERAGE__AVERAGE.Name] = oldValue;
                EventBean oldDataEvent = statementContext.EventAdapterService.CreateMapFromValues(oldDataMap, eventType);

                UpdateChildren(new EventBean[] { newDataEvent }, new EventBean[] { oldDataEvent });
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
        	EDataDictionary newDataMap = new EDataDictionary();
        	newDataMap[ViewFieldEnum.WEIGHTED_AVERAGE__AVERAGE.Name] = currentValue ;
            yield return statementContext.EventAdapterService.CreateMapFromValues(newDataMap, eventType);
        }

        /// <summary>
        /// Returns a <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </returns>
        public override String ToString()
        {
            return this.GetType().FullName + 
                " fieldName=" + fieldNameX +
                " fieldNameWeight=" + fieldNameWeight;
        }
    }
}
