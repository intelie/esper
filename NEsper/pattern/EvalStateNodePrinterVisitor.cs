using System;

using org.apache.commons.logging;

namespace net.esper.pattern
{
	/// <summary>
    /// Visitor class for printing out an evaluation state tree where each node is printed indented according
	/// to its depth in the tree.
	/// </summary>
	
    public sealed class EvalStateNodePrinterVisitor : EvalStateNodeVisitor
	{
		private int level;

        /// <summary>
        /// Invoked by each child node as part of accepting this visitor.
        /// </summary>
        /// <param name="node">is the node in the composite tree accepting the visitor</param>
        /// <param name="data">is any additional useful to implementations</param>
        /// <returns>
        /// any additional data useful to implementations or null
        /// </returns>
		public Object visit(EvalStateNode node, Object data)
		{
			log.Debug("visit " + indent(level++) + node.ToString());
			node.ChildrenAccept(this, data);
			level--;
			return data;
		}

        /// <summary>
        /// Indents the specified level.
        /// </summary>
        /// <param name="level">The level.</param>
        /// <returns></returns>
		private static String indent(int level)
		{
			System.Text.StringBuilder buffer = new System.Text.StringBuilder();
			for (int i = 0; i < level; i++)
			{
				buffer.Append("  ");
			}
			return buffer.ToString();
		}
		
		private static readonly Log log = LogFactory.GetLog(typeof(EvalStateNodePrinterVisitor));
	}
}