using System;

using net.esper.core;
using net.esper.events;

namespace net.esper.view.stat
{	
	/// <summary>
    /// A view that calculates correlation on two fields. The view uses internally a <seealso cref="CorrelationBean"/>
	/// instance for the calculations, it also returns this bean as the result.
	/// This class accepts most of its behaviour from its parent, <seealso cref="net.esper.view.stat.BaseBivariateStatisticsView"/>. It adds
	/// the usage of the correlation bean and the appropriate schema.
	/// </summary>
	public sealed class CorrelationView
		: BaseBivariateStatisticsView
		, CloneableView
	{
		private EventType eventType;

	    /**
	     * Constructor.
	     * @param xFieldName is the field name of the field providing X data points
	     * @param yFieldName is the field name of the field providing X data points
	     * @param statementContext contains required view services
	     */
	    public CorrelationView(StatementContext statementContext, String xFieldName, String yFieldName)
	        : base(statementContext, new CorrelationBean(), xFieldName, yFieldName)
	    {
	    }

	    public override View CloneView(StatementContext statementContext)
	    {
	        return new CorrelationView(statementContext, this.FieldNameX, this.FieldNameY);
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
		
	    /**
	     * Creates the event type for this view.
	     * @param statementContext is the event adapter service
	     * @return event type of view
	     */
	    public static EventType CreateEventType(StatementContext statementContext)
	    {
	        return statementContext.EventAdapterService.AddBeanType(typeof(CorrelationBean).FullName, typeof(CorrelationBean));
	    }
	}
}
