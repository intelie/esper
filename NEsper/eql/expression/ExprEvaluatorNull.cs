using System;

using net.esper.events;

namespace net.esper.eql.expression
{
	/// <summary>
	/// Description of ExprEvalutorNull.
	/// </summary>
	
	public class ExprEvaluatorNull : ExprEvaluator
	{
		public object Evaluate(EventBean[] eventsPerStream)
		{
			return null ;
		}
	}
}
