using System;

using net.esper.core;
using net.esper.events;

namespace net.esper.view.stat
{
	/// <summary>
    /// A view that calculates regression on two fields. The view uses internally a <seealso cref="RegressionBean"/>
	/// instance for the calculations, it also returns this bean as the result.
	/// This class accepts most of its behaviour from its parent, <seealso cref="net.esper.view.stat.BaseBivariateStatisticsView"/>. It adds
	/// the usage of the regression bean and the appropriate schema.
	/// </summary>
	public sealed class RegressionLinestView
		: BaseBivariateStatisticsView
		, CloneableView
	{
		private EventType eventType;

	   /// <summary>Constructor.</summary>
	   /// <param name="xFieldName">
	   /// is the field name of the field providing X data points
	   /// </param>
	   /// <param name="yFieldName">
	   /// is the field name of the field providing X data points
	   /// </param>
	   /// <param name="statementContext">contains required view services</param>
	    public RegressionLinestView(StatementContext statementContext, String xFieldName, String yFieldName)
	        : base(statementContext, new RegressionBean(), xFieldName, yFieldName)
	    {
	    }

        /// <summary>
        /// Clones the view.
        /// </summary>
        /// <param name="statementContext">The statement context.</param>
        /// <returns></returns>
	    public override View CloneView(StatementContext statementContext)
	    {
	        return new RegressionLinestView(statementContext, this.FieldNameX, this.FieldNameY);
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
                    eventType = CreateEventType(statementContext);
                }
                return eventType;
            }
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

		/// <summary>Creates the event type for this view.</summary>
		/// <param name="statementContext">is the event adapter service</param>
		/// <returns>event type of view</returns>
	    public static EventType CreateEventType(StatementContext statementContext)
	    {
	        return statementContext.EventAdapterService.AddBeanType(typeof(RegressionBean).FullName, typeof(RegressionBean));
	    }
	}
}
