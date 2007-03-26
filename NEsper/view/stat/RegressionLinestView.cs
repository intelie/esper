using System;

using net.esper.events;

namespace net.esper.view.stat
{
	/// <summary>
    /// A view that calculates regression on two fields. The view uses internally a {@link RegressionBean}
	/// instance for the calculations, it also returns this bean as the result.
	/// This class accepts most of its behaviour from its parent, {@link net.esper.view.stat.BaseBivariateStatisticsView}. It adds
	/// the usage of the regression bean and the appropriate schema.
	/// </summary>
	public sealed class RegressionLinestView : BaseBivariateStatisticsView
	{
		private EventType eventType;

        /// <summary>
        /// Initializes a new instance of the <see cref="RegressionLinestView"/> class.
        /// </summary>
		public RegressionLinestView()
		{
			statisticsBean = new RegressionBean();
		}
		
		/// <summary> Constructor.</summary>
		/// <param name="xFieldName">is the field name of the field providing X data points
		/// </param>
		/// <param name="yFieldName">is the field name of the field providing X data points
		/// </param>
		public RegressionLinestView(String xFieldName, String yFieldName):base(new RegressionBean(), xFieldName, yFieldName)
		{
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
            get
            {
                if (eventType == null)
                {
                    eventType = viewServiceContext.EventAdapterService.AddBeanType(typeof(RegressionBean).FullName, typeof(RegressionBean));
                }
                return eventType;
            }
            set { }
		}

        /// <summary>
        /// Returns a <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
        /// </returns>
		public override String ToString()
		{
			return this.GetType().FullName + " fieldX=" + this.FieldNameX + " fieldY=" + this.FieldNameY;
		}
	}
}
