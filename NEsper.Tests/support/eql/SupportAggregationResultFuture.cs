using System;

using net.esper.eql.core;

namespace net.esper.support.eql
{
	public class SupportAggregationResultFuture : AggregationResultFuture
	{
		private Object[] values;

        /// <summary>
        /// Initializes a new instance of the <see cref="SupportAggregationResultFuture"/> class.
        /// </summary>
        /// <param name="values">The values.</param>
		public SupportAggregationResultFuture(Object[] values)
		{
			this.values = values;
		}

        /// <summary>
        /// Returns current aggregation state, for use by expression node representing an aggregation function.
        /// </summary>
        /// <param name="column">is assigned to the aggregation expression node and passed as an column (index) into a row</param>
        /// <returns>current aggragation state</returns>
		public virtual Object GetValue(int column)
		{
			return values[column];
		}
	}
}
