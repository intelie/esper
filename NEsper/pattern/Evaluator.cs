using System;
namespace net.esper.pattern
{
	
	
	/// <summary> Interface for nodes in an expression evaluation state tree that are being informed by a child that the
	/// event expression fragments (subtrees) which the child represents has turned true (evaluateTrue method)
	/// or false (evaluateFalse).
	/// </summary>
	public interface Evaluator
	{
		/// <summary> Indicate a change in truth value to true.</summary>
		/// <param name="matchEvent">is the container for events that caused the change in truth value
		/// </param>
		/// <param name="fromNode">is the node that indicates the change
		/// </param>
		/// <param name="isQuitted">is an indication of whether the node continues listenening or Stops listening
		/// </param>
		void  EvaluateTrue(MatchedEventMap matchEvent, EvalStateNode fromNode, bool isQuitted);
		
		/// <summary> Indicate a change in truth value to false.</summary>
		/// <param name="fromNode">is the node that indicates the change
		/// </param>
		void  EvaluateFalse(EvalStateNode fromNode);
	}
}
