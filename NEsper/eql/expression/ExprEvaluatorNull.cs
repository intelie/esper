using System;

namespace net.esper.eql.expression
{
	/// <summary>
	/// Description of ExprEvalutorNull.
	/// </summary>
	
	public class ExprEvaluatorNull : ExprEvaluator
	{
		public object Evaluate(net.esper.events.EventBean[] eventsPerStream)
		{
			return null ;
		}
	}
}
