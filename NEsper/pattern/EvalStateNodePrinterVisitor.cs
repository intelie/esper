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
		
		public Object visit(EvalStateNode node, Object data)
		{
			log.Debug("visit " + indent(level++) + node.ToString());
			node.ChildrenAccept(this, data);
			level--;
			return data;
		}
		
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