using System;
using ExprNode = net.esper.eql.expression.ExprNode;
namespace net.esper.eql.spec
{
	
	/// <summary> Represents a single item in a SELECT-clause, potentially unnamed
	/// as no "as" tag may have been supplied in the syntax.
	/// <p>
	/// Compare to {@link SelectExprElementNamedSpec} which carries a determined name.
	/// </summary>
	public class SelectExprElementUnnamedSpec
	{
		/// <summary> Returns the expression node representing the item in the select clause.</summary>
		/// <returns> expression node for item
		/// </returns>
		virtual public ExprNode SelectExpression
		{
			get
			{
				return selectExpression;
			}
			
		}
		/// <summary> Returns the name of the item in the select clause.</summary>
		/// <returns> name of item
		/// </returns>
		virtual public String OptionalAsName
		{
			get
			{
				return optionalAsName;
			}
			
		}
		private ExprNode selectExpression;
		private String optionalAsName;
		
		/// <summary> Ctor.</summary>
		/// <param name="selectExpression">- the expression node to evaluate for matching events
		/// </param>
		/// <param name="optionalAsName">- the name of the item, null if not name supplied
		/// </param>
		public SelectExprElementUnnamedSpec(ExprNode selectExpression, String optionalAsName)
		{
			this.selectExpression = selectExpression;
			this.optionalAsName = optionalAsName;
		}
	}
}