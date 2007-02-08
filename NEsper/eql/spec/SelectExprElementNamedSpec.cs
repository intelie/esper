using System;
using ExprNode = net.esper.eql.expression.ExprNode;
namespace net.esper.eql.spec
{
	
	/// <summary> Represents a single item in a SELECT-clause, with a name assigned
	/// either by the engine or by the user specifying an "as" tag name.
	/// </summary>
	public class SelectExprElementNamedSpec
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
		virtual public String AssignedName
		{
			get
			{
				return assignedName;
			}
			
		}
		private ExprNode selectExpression;
		private String assignedName;
		
		/// <summary> Ctor.</summary>
		/// <param name="selectExpression">- the expression node to evaluate for matching events
		/// </param>
		/// <param name="assignedName">- cannot be null as a name is always assigned or
		/// system-determined
		/// </param>
		public SelectExprElementNamedSpec(ExprNode selectExpression, String assignedName)
		{
			this.selectExpression = selectExpression;
			this.assignedName = assignedName;
		}
	}
}