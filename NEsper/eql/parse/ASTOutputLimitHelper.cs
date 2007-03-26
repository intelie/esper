using System;
using OutputLimitSpec = net.esper.eql.spec.OutputLimitSpec;
using DisplayLimit = net.esper.eql.spec.OutputLimitSpec.DisplayLimit;
using EqlTokenTypes = net.esper.eql.generated.EqlTokenTypes;
using AST = antlr.collections.AST;
namespace net.esper.eql.parse
{
	
	/// <summary> Builds an output limit spec from an output limit AST node.
	/// 
	/// </summary>
	public class ASTOutputLimitHelper : EqlTokenTypes
	{
		/// <summary> Build an output limit spec from the AST node supplied.</summary>
		/// <param name="node">parse node
		/// </param>
		/// <returns> output limit spec
		/// </returns>
		public static OutputLimitSpec buildSpec(AST node)
		{
			AST child = node.getFirstChild();
			
			DisplayLimit displayLimit = DisplayLimit.ALL;
			if (child.Type == EqlTokenTypes.FIRST)
			{
				displayLimit = DisplayLimit.FIRST;
				child = child.getNextSibling();
			}
			else if (child.Type == EqlTokenTypes.LAST)
			{
				displayLimit = DisplayLimit.LAST;
				child = child.getNextSibling();
			}
			else if (child.Type == EqlTokenTypes.ALL)
			{
				child = child.getNextSibling();
			}
			
			switch (node.Type)
			{
				
				case EqlTokenTypes.EVENT_LIMIT_EXPR: 
					return new OutputLimitSpec(Int32.Parse(child.getText()), displayLimit);
				
				case EqlTokenTypes.SEC_LIMIT_EXPR: 
					return new OutputLimitSpec(Double.Parse(child.getText()), displayLimit);
				
				case EqlTokenTypes.MIN_LIMIT_EXPR: 
					// 60 seconds to a minute
					return new OutputLimitSpec(60 * Double.Parse(child.getText()), displayLimit);
				
				default: 
					throw new ArgumentException("Node type " + node.Type + " not a recognized output limit type");
				
			}
		}
	}
}