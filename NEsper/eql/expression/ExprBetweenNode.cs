using System;
using System.Collections.Generic;
using System.Text;

using net.esper.eql.core;
using net.esper.events;
using net.esper.util;

namespace net.esper.eql.expression
{
	/// <summary>
    /// Represents the between-clause function in an expression tree.
    /// </summary>
	
    public class ExprBetweenNode : ExprNode
	{
        /// <summary>
        /// Returns the type that the node's evaluate method returns an instance of.
        /// </summary>
        /// <value>The type.</value>
        /// <returns> type returned when evaluated
        /// </returns>
        /// <throws>ExprValidationException thrown when validation failed </throws>
		override public Type ReturnType
		{
			get { return typeof(bool?); }
		}
		
		private readonly bool isNotBetween;
		
		private bool isAlwaysFalse;
		private ExprBetweenNode.ExprBetweenComp computer;
		
		/// <summary> Ctor.</summary>
		/// <param name="isNotBetween">is true to indicate this is a "not between", or false for a "between"
		/// </param>
		
		public ExprBetweenNode(bool isNotBetween)
		{
			this.isNotBetween = isNotBetween;
		}

        /// <summary>
        /// Validate node.
        /// </summary>
        /// <param name="streamTypeService">serves stream event type info</param>
        /// <param name="autoImportService">for resolving class names in library method invocations</param>
        /// <throws>ExprValidationException thrown when validation failed </throws>
		public override void Validate(StreamTypeService streamTypeService, AutoImportService autoImportService)
		{
			if (this.ChildNodes.Count != 3)
			{
				throw new ExprValidationException("The Between operator requires exactly 3 child expressions");
			}
			
			// Must be either numeric or string
            Type typeOne = TypeHelper.GetBoxedType(this.ChildNodes[0].ReturnType);
            Type typeTwo = TypeHelper.GetBoxedType(this.ChildNodes[1].ReturnType);
			Type typeThree = TypeHelper.GetBoxedType(this.ChildNodes[2].ReturnType);
			
			if (typeOne == null)
			{
				throw new ExprValidationException("Null value not allowed in between-clause");
			}
			
			Type compareType = null;
			if ((typeTwo == null) || (typeThree == null))
			{
				isAlwaysFalse = true;
			}
			else
			{
				if ((typeOne != typeof(String)) || (typeTwo != typeof(String)) || (typeThree != typeof(String)))
				{
					if (!TypeHelper.IsNumeric(typeOne))
					{
						throw new ExprValidationException("Implicit conversion from datatype '" + typeOne.FullName + "' to numeric is not allowed");
					}
					if (!TypeHelper.IsNumeric(typeTwo))
					{
						throw new ExprValidationException("Implicit conversion from datatype '" + typeTwo.FullName + "' to numeric is not allowed");
					}
					if (!TypeHelper.IsNumeric(typeThree))
					{
						throw new ExprValidationException("Implicit conversion from datatype '" + typeThree.FullName + "' to numeric is not allowed");
					}
				}
				
				Type intermedType = TypeHelper.GetCompareToCoercionType(typeOne, typeTwo);
				compareType = TypeHelper.GetCompareToCoercionType(intermedType, typeThree);
				computer = makeComputer(compareType);
			}
		}

        /// <summary>
        /// Evaluate event tuple and return result.
        /// </summary>
        /// <param name="eventsPerStream">event tuple</param>
        /// <returns>
        /// evaluation result, a bool value for OR/AND-type evalution nodes.
        /// </returns>
		public override Object Evaluate(EventBean[] eventsPerStream)
		{
			if (isAlwaysFalse)
			{
				return false;
			}
			
			// Evaluate first child which is the base value to compare to
			IList<ExprNode> exprNodeList = this.ChildNodes ;
			
			Object value = exprNodeList[0].Evaluate(eventsPerStream);
			if (value == null)
			{
				return false;
			}
			Object lower = exprNodeList[1].Evaluate(eventsPerStream);
			Object higher = exprNodeList[2].Evaluate(eventsPerStream);
			
			bool result = computer.isBetween(value, lower, higher);
			if (isNotBetween)
			{
				return !result;
			}
			
			return result;
		}

        /// <summary>
        /// Return true if a expression node semantically equals the current node, or false if not.
        /// Concrete implementations should compare the type and any additional information
        /// that impact the evaluation of a node.
        /// </summary>
        /// <param name="node">to compare to</param>
        /// <returns>
        /// true if semantically equal, or false if not equals
        /// </returns>
		public override bool EqualsNode(ExprNode node)
		{
			if (!(node is ExprBetweenNode))
			{
				return false;
			}
			
			ExprBetweenNode other = (ExprBetweenNode) node;
			return other.isNotBetween == this.isNotBetween;
		}

        /// <summary>
        /// Returns the expression node rendered as a string.
        /// </summary>
        /// <value></value>
        /// <returns> string rendering of expression
        /// </returns>
		public override String ExpressionString
		{
            get
            {
                StringBuilder buffer = new StringBuilder();

                IList<ExprNode> exprNodeList = this.ChildNodes ;

                buffer.Append(exprNodeList[0].ExpressionString);
                if (isNotBetween)
                {
                    buffer.Append(" not between ");
                }
                else
                {
                    buffer.Append(" between ");
                }

                buffer.Append(exprNodeList[1].ExpressionString);
                buffer.Append(" and ");
                buffer.Append(exprNodeList[2].ExpressionString);

                return buffer.ToString();
            }
		}
		
		private ExprBetweenNode.ExprBetweenComp makeComputer(Type compareType)
		{
			ExprBetweenNode.ExprBetweenComp computer = null;
			
			if (compareType == typeof(string))
			{
				computer = new ExprBetweenCompString();
			}
			else if (compareType == typeof(long))
			{
				computer = new ExprBetweenCompLong();
			}
			else
			{
				computer = new ExprBetweenCompDouble();
			}
			return computer;
		}
		
		private interface ExprBetweenComp
		{
			bool isBetween(Object value, Object lower, Object upper);
		}
		
		private class ExprBetweenCompString : ExprBetweenNode.ExprBetweenComp
		{
			public virtual bool isBetween(Object value, Object lower, Object upper)
			{
				if ((value == null) || (lower == null) || ((upper == null)))
				{
					return false;
				}
				
				String valueStr = (String) value;
				String lowerStr = (String) lower;
				String upperStr = (String) upper;
				
				if (String.CompareOrdinal(upperStr, lowerStr) < 0)
				{
					if (String.CompareOrdinal(valueStr, lowerStr) > 0)
					{
						return false;
					}
					if (String.CompareOrdinal(valueStr, upperStr) < 0)
					{
						return false;
					}
				}
				else
				{
					if (String.CompareOrdinal(valueStr, lowerStr) < 0)
					{
						return false;
					}
					if (String.CompareOrdinal(valueStr, upperStr) > 0)
					{
						return false;
					}
				}
				return true;
			}
		}
		
		private class ExprBetweenCompDouble : ExprBetweenNode.ExprBetweenComp
		{
			public virtual bool isBetween(Object value, Object lower, Object upper)
			{
				if ((value == null) || (lower == null) || ((upper == null)))
				{
					return false;
				}
				
				double valueD = Convert.ToDouble(value);
				double lowerD = Convert.ToDouble(lower);
				double upperD = Convert.ToDouble(upper);
				
				if (lowerD > upperD)
				{
					if (valueD <= lowerD && valueD >= upperD)
					{
						return true;
					}
					return false;
				}
				if (valueD >= lowerD && valueD <= upperD)
				{
					return true;
				}
				return false;
			}
		}
		
		private class ExprBetweenCompLong : ExprBetweenNode.ExprBetweenComp
		{
			public virtual bool isBetween(Object value, Object lower, Object upper)
			{
				if ((value == null) || (lower == null) || ((upper == null)))
				{
					return false;
				}
				long valueD = Convert.ToInt64(value);
				long lowerD = Convert.ToInt64(lower);
				long upperD = Convert.ToInt64(upper);
				
				if (lowerD > upperD)
				{
					if (valueD <= lowerD && valueD >= upperD)
					{
						return true;
					}
					return false;
				}
				if (valueD >= lowerD && valueD <= upperD)
				{
					return true;
				}
				return false;
			}
		}
	}
}
