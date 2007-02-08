using System;

using net.esper.eql.expression;
using net.esper.events;

namespace net.esper.support.eql
{
	
	public class SupportExprEvaluator : ExprEvaluator
	{
		public virtual Object Evaluate(EventBean[] eventsPerStream)
		{
			return eventsPerStream[0]["boolPrimitive"];
		}
	}
}
