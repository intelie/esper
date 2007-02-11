using System;
namespace net.esper.eql.expression
{
	/// <summary>
    /// Visitor interface for use with expression node trees.
    /// </summary>
	
    public interface ExprNodeVisitor
	{
		/// <summary> Allows visitor to indicate whether to visit a given node.
		/// Implicitly if a visitor doesn't visit a node it would also not visit any descendent child nodes of that node.
		/// </summary>
		/// <param name="exprNode">is the node in questions
		/// </param>
		/// <returns> true if the visitor wants to visit the child node (next call is visit), or false to skip child
		/// </returns>
		bool isVisit(ExprNode exprNode);
		
		/// <summary> Visit the given expression node.</summary>
		/// <param name="exprNode">is the expression node to visit
		/// </param>
		void  visit(ExprNode exprNode);
	}
}