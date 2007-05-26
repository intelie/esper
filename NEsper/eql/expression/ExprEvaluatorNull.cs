using System;

using net.esper.events;

namespace net.esper.eql.expression
{
	/// <summary>
	/// Description of ExprEvalutorNull.
	/// </summary>
	
	public class ExprEvaluatorNull : ExprEvaluator
	{
        /// <summary>
        /// Evaluate event tuple and return result.
        /// </summary>
        /// <param name="eventsPerStream">event tuple</param>
        /// <returns>
        /// evaluation result, a bool value for OR/AND-type evalution nodes.
        /// </returns>
		public object Evaluate(EventBean[] eventsPerStream)
		{
			return null ;
		}
	}
}
