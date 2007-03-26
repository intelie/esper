using System;

using antlr;
using antlr.collections;

using net.esper.eql.generated;

namespace net.esper.eql.parse
{
	/// <summary>
    /// For selection of the AST tree walk rule to use.
    /// </summary>
	
    public interface WalkRuleSelector
	{
		/// <summary> Implementations can invoke a walk rule of their choice on the walker and AST passed in.</summary>
		/// <param name="walker">to invoke walk rule on
		/// </param>
		/// <param name="ast">AST to walk
		/// </param>
		/// <throws>  RecognitionException - throw on walk errors </throws>
		void  invokeWalkRule(EQLBaseWalker walker, AST ast);
	}
}