using System;
namespace net.esper.pattern
{
	
	/// <summary> Interface for visiting each element in the evaluation node tree for an event expression (see Visitor pattern).</summary>
	public interface EvalStateNodeVisitor
	{
		/// <summary> Invoked by each child node as part of accepting this visitor.</summary>
		/// <param name="node">is the node in the composite tree accepting the visitor
		/// </param>
		/// <param name="data">is any additional useful to implementations
		/// </param>
		/// <returns> any additional data useful to implementations or null
		/// </returns>
		Object visit(EvalStateNode node, Object data);
	}
}