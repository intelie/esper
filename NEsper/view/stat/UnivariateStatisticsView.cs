using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.collection;
using net.esper.events;
using net.esper.view;

namespace net.esper.view.stat
{
    /// <summary> View for computing statistics, which the view exposes via fields representing the sum, count, standard deviation
    /// for sample and for population and variance.
    /// </summary>

    public sealed class UnivariateStatisticsView : ViewSupport, ContextAwareView
    {
        public ViewServiceContext ViewServiceContext
        {
            get
            {
                return viewServiceContext;
            }

            set
            {
                this.viewServiceContext = value;

                EDictionary<String, Type> eventTypeMap = new EHashDictionary<String, Type>();
                eventTypeMap[ViewFieldEnum.UNIVARIATE_STATISTICS__COUNT.Name] = typeof(long);
                eventTypeMap[ViewFieldEnum.UNIVARIATE_STATISTICS__SUM.Name] = typeof(double);
                eventTypeMap[ViewFieldEnum.UNIVARIATE_STATISTICS__STDDEV.Name] = typeof(double);
                eventTypeMap[ViewFieldEnum.UNIVARIATE_STATISTICS__STDDEVPA.Name] = typeof(double);
                eventTypeMap[ViewFieldEnum.UNIVARIATE_STATISTICS__VARIANCE.Name] = typeof(double);
                eventTypeMap[ViewFieldEnum.UNIVARIATE_STATISTICS__AVERAGE.Name] = typeof(double);
                eventType = value.EventAdapterService.CreateAnonymousMapType(eventTypeMap);
            }

        }

        /// <summary> Returns field name of the field to report statistics on.</summary>
        /// <returns> field name
        /// </returns>
        /// <summary> Set the field name of the field to report statistics on.</summary>
        /// <param name="fieldName">is the field to report statistics on
        /// </param>
        public String FieldName
        {
            get
            {
                return fieldName;
            }

            set
            {
                this.fieldName = value;
            }

        }
        private ViewServiceContext viewServiceContext;
        private EventType eventType;
        private String fieldName;
        private EventPropertyGetter fieldGetter;
        private readonly BaseStatisticsBean baseStatisticsBean = new BaseStatisticsBean();

        /// <summary>
        /// Default constructor - required by all views to adhere to the Java bean specification.
        /// </summary>

        public UnivariateStatisticsView()
        {
        }

        /// <summary> Constructor requires the name of the field to use in the parent view to compute the statistics.</summary>
        /// <param name="fieldName">is the name of the field within the parent view to use to get numeric data points for this view to
        /// compute the statistics on.
        /// </param>

        public UnivariateStatisticsView(String fieldName)
        {
            this.fieldName = fieldName;
        }

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

        public override String AttachesTo(Viewable parentView)
        {
            return PropertyCheckHelper.checkNumeric(parentView.EventType, fieldName);
        }

        public override void Update(EventBean[] newData, EventBean[] oldData)
        {
            // If we have child views, keep a reference to the old values, so we can fire them as old data event.
            EventBean oldDataMap = null;
            if (this.HasViews())
            {
                oldDataMap = populateMap(baseStatisticsBean, viewServiceContext.EventAdapterService, eventType);
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

            // If there are child view, fire update method
            if (this.HasViews())
            {
                EventBean newDataMap = populateMap(baseStatisticsBean, viewServiceContext.EventAdapterService, eventType);
                updateChildren(new EventBean[] { newDataMap }, new EventBean[] { oldDataMap });
            }
        }

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
            return new SingleEventIterator(populateMap(baseStatisticsBean, viewServiceContext.EventAdapterService, eventType));
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

        private static EventBean populateMap(
        	BaseStatisticsBean baseStatisticsBean,
        	EventAdapterService eventAdapterService,
        	EventType eventType)
        {
        	EDataDictionary result = new EDataDictionary() ;
            result[ViewFieldEnum.UNIVARIATE_STATISTICS__COUNT.Name]    = baseStatisticsBean.N;
            result[ViewFieldEnum.UNIVARIATE_STATISTICS__SUM.Name]      = baseStatisticsBean.XSum;
            result[ViewFieldEnum.UNIVARIATE_STATISTICS__STDDEV.Name]   = baseStatisticsBean.XStandardDeviationSample;
            result[ViewFieldEnum.UNIVARIATE_STATISTICS__STDDEVPA.Name] = baseStatisticsBean.XStandardDeviationPop;
            result[ViewFieldEnum.UNIVARIATE_STATISTICS__VARIANCE.Name] = baseStatisticsBean.XVariance;
            result[ViewFieldEnum.UNIVARIATE_STATISTICS__AVERAGE.Name]  = baseStatisticsBean.XAverage;
            return eventAdapterService.CreateMapFromValues(result, eventType);
        }
    }
}
