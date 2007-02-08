using System;

using net.esper.eql.core;

namespace net.esper.support.eql
{
	
	public class SupportAggregationResultFuture : AggregationResultFuture
	{
		private Object[] values;
		
		public SupportAggregationResultFuture(Object[] values)
		{
			this.values = values;
		}
		
		public virtual Object getValue(int column)
		{
			return values[column];
		}
	}
}
