using System;
using OuterJoinType = net.esper.type.OuterJoinType;
using ExprIdentNode = net.esper.eql.expression.ExprIdentNode;
namespace net.esper.eql.spec
{
	
	/// <summary> Contains the ON-clause criteria in an outer join.</summary>
	public class OuterJoinDesc
	{
		/// <summary> Returns the type of outer join (left/right/full).</summary>
		/// <returns> outer join type
		/// </returns>
		virtual public OuterJoinType OuterJoinType
		{
			get
			{
				return outerJoinType;
			}
			
		}
		/// <summary> Returns left hand identifier node.</summary>
		/// <returns> left hand
		/// </returns>
		virtual public ExprIdentNode LeftNode
		{
			get
			{
				return leftNode;
			}
			
		}
		/// <summary> Returns right hand identifier node.</summary>
		/// <returns> right hand
		/// </returns>
		virtual public ExprIdentNode RightNode
		{
			get
			{
				return rightNode;
			}
			
		}
		private OuterJoinType outerJoinType;
		private ExprIdentNode leftNode;
		private ExprIdentNode rightNode;
		
		/// <summary> Ctor.</summary>
		/// <param name="outerJoinType">type of the outer join
		/// </param>
		/// <param name="leftNode">left hand identifier node
		/// </param>
		/// <param name="rightNode">right hand identifier node
		/// </param>
		public OuterJoinDesc(OuterJoinType outerJoinType, ExprIdentNode leftNode, ExprIdentNode rightNode)
		{
			this.outerJoinType = outerJoinType;
			this.leftNode = leftNode;
			this.rightNode = rightNode;
		}
	}
}