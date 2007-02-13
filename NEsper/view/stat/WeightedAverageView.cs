using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.collection;
using net.esper.events;
using net.esper.view;

namespace net.esper.view.stat
{
    /// <summary>
    /// View for computing a weighted average. The view uses 2 fields within the parent view to compute
    /// the weighted average. The X field and weight field. In a price-volume example it calculates the
    /// volume-weighted average price as (sum(price * volume) / sum(volume)).
    ///     Example: weighted_avg("price", "volume")
    /// </summary>
    public sealed class WeightedAverageView : ViewSupport, ContextAwareView
    {
        public ViewServiceContext ViewServiceContext
        {
            get { return viewServiceContext; }
            set
            {
                this.viewServiceContext = value;

                EDictionary<String, Type> schemaMap = new EHashDictionary<String, Type>();
                schemaMap[ViewFieldEnum.WEIGHTED_AVERAGE__AVERAGE.Name] = typeof(double);
                eventType = value.EventAdapterService.CreateAnonymousMapType(schemaMap);
            }
        }

        /// <summary> Returns the name of the field supplying the X values.</summary>
        /// <returns> field name supplying X data points
        /// </returns>
        /// <summary> Sets the name of the field supplying the X values.</summary>
        /// <param name="fieldNameX">field name supplying X data points
        /// </param>

        public String FieldNameX
        {
            get { return fieldNameX; }
            set { this.fieldNameX = value; }
        }

        /// <summary> Returns the name of the field supplying the weight values.</summary>
        /// <returns> field name supplying weight
        /// </returns>
        /// <summary> Sets the name of the field supplying the weight values.</summary>
        /// <param name="fieldNameWeight">field name supplying weight
        /// </param>

        public String FieldNameWeight
        {
            get { return fieldNameWeight; }
            set { this.fieldNameWeight = value; }
        }

        private EventType eventType;
        private ViewServiceContext viewServiceContext;
        private String fieldNameX;
        private String fieldNameWeight;
        private EventPropertyGetter fieldXGetter;
        private EventPropertyGetter fieldWeightGetter;

        private double sumXtimesW = Double.NaN;
        private double sumW = Double.NaN;
        private double currentValue = Double.NaN;

        /// <summary>
        /// Default constructor - required by all views to adhere to the Java bean specification.
        /// </summary>

        public WeightedAverageView()
        {
        }

        /// <summary> Constructor requires the name of the field to use in the parent view to compute the weighted average on,
        /// as well as the name of the field in the parent view to get the weight from.
        /// </summary>
        /// <param name="fieldNameX">is the name of the field within the parent view to use to get numeric data points for this view to
        /// compute the average for.
        /// </param>
        /// <param name="fieldNameWeight">is the field name for the weight to apply to each data point
        /// </param>

        public WeightedAverageView(String fieldNameX, String fieldNameWeight)
        {
            this.fieldNameX = fieldNameX;
            this.fieldNameWeight = fieldNameWeight;
        }

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

        public override String AttachesTo(Viewable parentView)
        {
            return PropertyCheckHelper.checkNumeric(parentView.EventType, fieldNameX, fieldNameWeight);
        }

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

            // If there are child view, fire update method
            if (this.HasViews)
            {
                EDataDictionary newDataMap = new EDataDictionary();
                newDataMap[ViewFieldEnum.WEIGHTED_AVERAGE__AVERAGE.Name] = currentValue;
                EventBean newDataEvent = viewServiceContext.EventAdapterService.CreateMapFromValues(newDataMap, eventType);

                EDataDictionary oldDataMap = new EDataDictionary();
                oldDataMap[ViewFieldEnum.WEIGHTED_AVERAGE__AVERAGE.Name] = oldValue;
                EventBean oldDataEvent = viewServiceContext.EventAdapterService.CreateMapFromValues(oldDataMap, eventType);

                updateChildren(new EventBean[] { newDataEvent }, new EventBean[] { oldDataEvent });
            }
        }

        public override EventType EventType
        {
            get { return eventType; }
            set { }
        }

        public override IEnumerator<EventBean> GetEnumerator()
        {
        	EDataDictionary newDataMap = new EDataDictionary();
        	newDataMap[ViewFieldEnum.WEIGHTED_AVERAGE__AVERAGE.Name] = currentValue ;
            return new SingleEventIterator(viewServiceContext.EventAdapterService.CreateMapFromValues(newDataMap, eventType));
        }

        public override String ToString()
        {
            return this.GetType().FullName + 
                " fieldName=" + fieldNameX +
                " fieldNameWeight=" + fieldNameWeight;
        }
    }
}
